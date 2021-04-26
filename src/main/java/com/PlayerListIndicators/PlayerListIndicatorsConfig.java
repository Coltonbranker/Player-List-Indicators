package com.PlayerListIndicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("playerindicators")
public interface PlayerListIndicatorsConfig extends Config
{
	@ConfigSection(
			name = "Highlight Options",
			description = "Toggle highlighted players by list number and choose their highlight colors",
			position = 99
	)
	String highlightSection = "section";

	@ConfigItem(
			position = 0,
			keyName = "drawListOne",
			name = "Highlight list one",
			description = "Configures whether or not list one should be highlighted",
			section = highlightSection
	)
	default boolean highlightListOne()
	{
		return true;
	}

	@ConfigItem(
			position = 1,
			keyName = "listOneColor",
			name = "List one",
			description = "Color of list one",
			section = highlightSection
	)
	default Color getListOneColor()
	{
		return new Color(0, 184, 212);
	}

	@ConfigItem(
			position = 2,
			keyName = "drawListTwo",
			name = "Highlight list two",
			description = "Configures whether or not list two should be highlighted",
			section = highlightSection
	)
	default boolean highlightListTwo()
	{
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = "listTwoColor",
			name = "List two",
			description = "Color of list two",
			section = highlightSection
	)
	default Color getListTwoColor()
	{
		return new Color(0, 200, 83);
	}

	@ConfigItem(
			position = 4,
			keyName = "drawListThree",
			name = "Highlight list three",
			description = "Configures if list three should be highlighted",
			section = highlightSection
	)
	default boolean highlightListThree()
	{
		return true;
	}

	@ConfigItem(
			position = 5,
			keyName = "listThreeColor",
			name = "List three",
			description = "Color of list three",
			section = highlightSection
	)
	default Color getListThreeColor()
	{
		return new Color(170, 0, 255);
	}

	@ConfigItem(
			position = 6,
			keyName = "drawListFour",
			name = "Highlight list four",
			description = "Configures whether or not list four should be highlighted",
			section = highlightSection
	)
	default boolean highlightListFour()
	{
		return true;
	}

	@ConfigItem(
			position = 7,
			keyName = "listFourColor",
			name = "List four",
			description = "Color of list four",
			section = highlightSection
	)
	default Color getListFourColor()
	{
		return new Color(19, 110, 247);
	}

	@ConfigItem(
			position = 8,
			keyName = "drawPlayerTiles",
			name = "Draw tiles under players",
			description = "Configures whether or not tiles under highlighted players should be drawn"
	)
	default boolean drawTiles()
	{
		return false;
	}

	@ConfigItem(
			position = 9,
			keyName = "playerNamePosition",
			name = "Name position",
			description = "Configures the position of drawn player names, or if they should be disabled"
	)
	default PlayerNameLocation playerNamePosition()
	{
		return PlayerNameLocation.ABOVE_HEAD;
	}

	@ConfigItem(
			position = 10,
			keyName = "drawMinimapNames",
			name = "Draw names on minimap",
			description = "Configures whether or not minimap names for players with rendered names should be drawn"
	)
	default boolean drawMinimapNames()
	{
		return false;
	}

	@ConfigItem(
			position = 11,
			keyName = "colorPlayerMenu",
			name = "Colorize player menu",
			description = "Color right click menu for players"
	)
	default boolean colorPlayerMenu()
	{
		return true;
	}




	@ConfigSection(
			name = "Item Lists",
			description = "The player name lists",
			position = 100,
			closedByDefault = false
	)
	String itemLists = "itemLists";

	@ConfigItem(
			keyName = "listOne",
			name = "List One",
			description = "Configures names on list one. Format: (item), (item)",
			position = 13,
			section = itemLists
	)
	default String getListOne()
	{
		return "Add names here";
	}

	@ConfigItem(
			keyName = "listOne",
			name = "",
			description = ""
	)
	void setListOne(String key);

	@ConfigItem(
			keyName = "listTwo",
			name = "List Two",
			description = "Configures names on list two. Format: (item), (item)",
			position = 14,
			section = itemLists
	)
	default String getListTwo()
	{
		return "Add names here";
	}

	@ConfigItem(
			keyName = "listTwo",
			name = "",
			description = ""
	)
	void setListTwo(String key);

	@ConfigItem(
			keyName = "listThree",
			name = "List Three",
			description = "Configures names on list three. Format: (item), (item)",
			position = 15,
			section = itemLists
	)
	default String getListThree()
	{
		return "Add names here";
	}

	@ConfigItem(
			keyName = "listThree",
			name = "",
			description = ""
	)
	void setListThree(String key);

	@ConfigItem(
			keyName = "listFour",
			name = "List Four",
			description = "Configures names on list four. Format: (item), (item)",
			position = 16,
			section = itemLists
	)
	default String getListFour()
	{
		return "Add names here";
	}

	@ConfigItem(
			keyName = "listFour",
			name = "",
			description = ""
	)
	void setListFour(String key);


}
