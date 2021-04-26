package com.PlayerListIndicators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.FriendsChatRank;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.game.FriendChatManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.Text;

@Singleton
public class PlayerListIndicatorsOverlay extends Overlay
{
    private static final int ACTOR_OVERHEAD_TEXT_MARGIN = 40;
    private static final int ACTOR_HORIZONTAL_TEXT_MARGIN = 10;

    private final PlayerListIndicatorsService playerListIndicatorsService;
    private final PlayerListIndicatorsConfig config;
    private final FriendChatManager friendChatManager;

    @Inject
    private PlayerListIndicatorsOverlay(PlayerListIndicatorsConfig config, PlayerListIndicatorsService playerListIndicatorsService,
                                    FriendChatManager friendChatManager)
    {
        this.config = config;
        this.playerListIndicatorsService = playerListIndicatorsService;
        this.friendChatManager = friendChatManager;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        playerListIndicatorsService.forEachPlayer((player, color) -> renderPlayerOverlay(graphics, player, color));
        return null;
    }

    private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color)
    {
        final PlayerNameLocation drawPlayerNamesConfig = config.playerNamePosition();
        if (drawPlayerNamesConfig == PlayerNameLocation.DISABLED)
        {
            return;
        }

        final int zOffset;
        switch (drawPlayerNamesConfig)
        {
            case MODEL_CENTER:
            case MODEL_RIGHT:
                zOffset = actor.getLogicalHeight() / 2;
                break;
            default:
                zOffset = actor.getLogicalHeight() + ACTOR_OVERHEAD_TEXT_MARGIN;
        }

        final String name = Text.sanitize(actor.getName());
        Point textLocation = actor.getCanvasTextLocation(graphics, name, zOffset);

        if (drawPlayerNamesConfig == PlayerNameLocation.MODEL_RIGHT)
        {
            textLocation = actor.getCanvasTextLocation(graphics, "", zOffset);

            if (textLocation == null)
            {
                return;
            }

            textLocation = new Point(textLocation.getX() + ACTOR_HORIZONTAL_TEXT_MARGIN, textLocation.getY());
        }

        if (textLocation == null)
        {
            return;
        }

        if (config.showFriendsChatRanks() && actor.isFriendsChatMember())
        {
            final FriendsChatRank rank = friendChatManager.getRank(name);

            if (rank != FriendsChatRank.UNRANKED)
            {
                final BufferedImage rankImage = friendChatManager.getRankImage(rank);

                if (rankImage != null)
                {
                    final int imageWidth = rankImage.getWidth();
                    final int imageTextMargin;
                    final int imageNegativeMargin;

                    if (drawPlayerNamesConfig == PlayerNameLocation.MODEL_RIGHT)
                    {
                        imageTextMargin = imageWidth;
                        imageNegativeMargin = 0;
                    }
                    else
                    {
                        imageTextMargin = imageWidth / 2;
                        imageNegativeMargin = imageWidth / 2;
                    }

                    final int textHeight = graphics.getFontMetrics().getHeight() - graphics.getFontMetrics().getMaxDescent();
                    final Point imageLocation = new Point(textLocation.getX() - imageNegativeMargin - 1, textLocation.getY() - textHeight / 2 - rankImage.getHeight() / 2);
                    OverlayUtil.renderImageLocation(graphics, imageLocation, rankImage);

                    // move text
                    textLocation = new Point(textLocation.getX() + imageTextMargin, textLocation.getY());
                }
            }
        }

        OverlayUtil.renderTextLocation(graphics, textLocation, name, color);
    }
}