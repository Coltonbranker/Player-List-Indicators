package com.PlayerListIndicators;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PlayerListIndicatorsTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PlayerListIndicatorsPlugin.class);
		RuneLite.main(args);
	}
}