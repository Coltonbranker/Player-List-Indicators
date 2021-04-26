package com.PlayerListIndicators;

import com.google.inject.Provides;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.Value;
import net.runelite.api.FriendsChatRank;
import static net.runelite.api.FriendsChatRank.UNRANKED;
import net.runelite.api.Client;
import static net.runelite.api.MenuAction.*;
import net.runelite.api.MenuEntry;
import net.runelite.api.Player;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.FriendChatManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;

@PluginDescriptor(
		name = "Player List Indicators",
		description = "Allows player names to be put into lists and have each list a different colour. Based on Player Indicators.",
		tags = {"highlight", "minimap", "overlay", "players"}
)
public class PlayerListIndicatorsPlugin extends Plugin
{

	private List<String> listOne = new ArrayList<>();
	private List<String> listTwo = new ArrayList<>();
	private List<String> listThree = new ArrayList<>();
	private List<String> listFour = new ArrayList<>();

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PlayerListIndicatorsConfig config;

	@Inject
	private PlayerListIndicatorsOverlay playerListIndicatorsOverlay;

	@Inject
	private PlayerListIndicatorsTileOverlay playerListIndicatorsTileOverlay;

	@Inject
	private PlayerListIndicatorsMinimapOverlay playerListIndicatorsMinimapOverlay;

	@Inject
	private Client client;

	@Inject
	private FriendChatManager friendChatManager;

	@Provides
	PlayerListIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlayerListIndicatorsConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(playerListIndicatorsOverlay);
		overlayManager.add(playerListIndicatorsTileOverlay);
		overlayManager.add(playerListIndicatorsMinimapOverlay);

		// gets the lists from the text boxs in the config
		listOne = Text.fromCSV(config.getListOne());
		listTwo = Text.fromCSV(config.getListTwo());
		listThree = Text.fromCSV(config.getListThree());
		listFour = Text.fromCSV(config.getListFour());

	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(playerListIndicatorsOverlay);
		overlayManager.remove(playerListIndicatorsTileOverlay);
		overlayManager.remove(playerListIndicatorsMinimapOverlay);

		listOne = null;
		listTwo = null;
		listThree = null;
		listFour = null;
	}


	@Subscribe
	public void onClientTick(ClientTick clientTick)
	{
		if (client.isMenuOpen())
		{
			return;
		}

		MenuEntry[] menuEntries = client.getMenuEntries();
		boolean modified = false;

		for (MenuEntry entry : menuEntries)
		{
			int type = entry.getType();

			if (type >= MENU_ACTION_DEPRIORITIZE_OFFSET)
			{
				type -= MENU_ACTION_DEPRIORITIZE_OFFSET;
			}

			if (type == WALK.getId()
					|| type == SPELL_CAST_ON_PLAYER.getId()
					|| type == ITEM_USE_ON_PLAYER.getId()
					|| type == PLAYER_FIRST_OPTION.getId()
					|| type == PLAYER_SECOND_OPTION.getId()
					|| type == PLAYER_THIRD_OPTION.getId()
					|| type == PLAYER_FOURTH_OPTION.getId()
					|| type == PLAYER_FIFTH_OPTION.getId()
					|| type == PLAYER_SIXTH_OPTION.getId()
					|| type == PLAYER_SEVENTH_OPTION.getId()
					|| type == PLAYER_EIGTH_OPTION.getId()
					|| type == RUNELITE_PLAYER.getId())
			{
				Player[] players = client.getCachedPlayers();
				Player player = null;

				int identifier = entry.getIdentifier();

				// 'Walk here' identifiers are offset by 1 because the default
				// identifier for this option is 0, which is also a player index.
				if (type == WALK.getId())
				{
					identifier--;
				}

				if (identifier >= 0 && identifier < players.length)
				{
					player = players[identifier];
				}

				if (player == null)
				{
					continue;
				}

				Decorations decorations = getDecorations(player);

				if (decorations == null)
				{
					continue;
				}

				String oldTarget = entry.getTarget();
				String newTarget = decorateTarget(oldTarget, decorations);

				entry.setTarget(newTarget);
				modified = true;
			}
		}

		if (modified)
		{
			client.setMenuEntries(menuEntries);
		}
	}

	private Decorations getDecorations(Player player)
	{
		int image = -1;
		Color color = null;

		if (config.highlightListOne() && listOne.contains(player.getName()))
		{
			color = config.getListOneColor();
		}
		else if (config.highlightListTwo() && listTwo.contains(player.getName()))
		{
			color = config.getListTwoColor();

		}
		else if (config.highlightListThree() && listThree.contains(player.getName()))
		{
			color = config.getListThreeColor();
		}
		else if (config.highlightListFour() && listFour.contains(player.getName()))
		{
			color = config.getListFourColor();
		}

		if (image == -1 && color == null)
		{
			return null;
		}

		return new Decorations(image, color);
	}

	private String decorateTarget(String oldTarget, Decorations decorations)
	{
		String newTarget = oldTarget;

		if (decorations.getColor() != null && config.colorPlayerMenu())
		{
			// strip out existing <col...
			int idx = oldTarget.indexOf('>');
			if (idx != -1)
			{
				newTarget = oldTarget.substring(idx + 1);
			}

			newTarget = ColorUtil.prependColorTag(newTarget, decorations.getColor());
		}



		return newTarget;
	}

	@Value
	private static class Decorations
	{
		private final int image;
		private final Color color;
	}
}