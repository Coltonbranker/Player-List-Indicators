package com.PlayerListIndicators;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.util.Text;



@Singleton
public class PlayerListIndicatorsService
{
    private final Client client;
    private final PlayerListIndicatorsConfig config;

    private List<String> listOne = new ArrayList<>();
    private List<String> listTwo = new ArrayList<>();
    private List<String> listThree = new ArrayList<>();
    private List<String> listFour = new ArrayList<>();


    @Inject
    private PlayerListIndicatorsService(Client client, PlayerListIndicatorsConfig config)
    {
        this.config = config;
        this.client = client;
    }

    public void forEachPlayer(final BiConsumer<Player, Color> consumer)
    {
        listOne = Text.fromCSV(config.getListOne());
        listTwo = Text.fromCSV(config.getListTwo());
        listThree = Text.fromCSV(config.getListThree());
        listFour = Text.fromCSV(config.getListFour());

        if (!config.highlightListOne() && !config.highlightListTwo()
                && !config.highlightListThree() && !config.highlightListFour())
        {
            return;
        }

        final Player localPlayer = client.getLocalPlayer();

        for (Player player : client.getPlayers())
        {
            if (player == null || player.getName() == null)
            {
                continue;
            }

            boolean isFriendsChatMember = player.isFriendsChatMember();


            if (config.highlightListOne() && listOne.contains(player.getName()))
            {
                consumer.accept(player, config.getListOneColor());
            }
            else if (config.highlightListTwo() && listTwo.contains(player.getName()))
            {
                consumer.accept(player, config.getListTwoColor());
            }
            else if (config.highlightListThree() && listThree.contains(player.getName()))
            {
                consumer.accept(player, config.getListThreeColor());
            }
            else if (config.highlightListFour() && listFour.contains(player.getName()))
            {
                consumer.accept(player, config.getListFourColor());
            }
        }
    }
}