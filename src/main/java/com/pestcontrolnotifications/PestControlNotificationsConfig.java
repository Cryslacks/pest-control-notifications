package com.pestcontrolnotifications;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("pestcontrolnotifications")
public interface PestControlNotificationsConfig extends Config
{
	@ConfigItem(
			keyName = "notifyStart",
			name = "Round start",
			description = "Notifies the player when the round starts.",
			position = 0
	)
	default boolean notifyStart()
	{
		return false;
	}

	@ConfigItem(
			keyName = "notifyEnd",
			name = "Round end",
			description = "Notifies the player when the round ending.",
			position = 1
	)
	default boolean notifyEnd()
	{
		return false;
	}

	@ConfigItem(
			keyName = "notifyActivity",
			name = "Activity",
			description = "Notifies the player when the activity reaches a set threshold.",
			position = 2
	)
	default boolean notifyActivity()
	{
		return false;
	}

	@ConfigItem(
			keyName = "activityLimit",
			name = "Activity limit",
			description = "Configures the limit in which a notification is fired, declared in percentage",
			position = 3
	)
	default int activityLimit()
	{
		return 20;
	}
	@ConfigItem(
			keyName = "notifyRedportal",
			name = "Red portal shield",
			description = "Notifies the player when the shield for red portal is down.",
			position = 4
	)
	default boolean notifyRedportal()
	{
		return false;
	}

	@ConfigItem(
			keyName = "notifyYellowportal",
			name = "Yellow portal shield",
			description = "Notifies the player when the shield for yellow portal is down.",
			position = 5
	)
	default boolean notifyYellowportal()
	{
		return false;
	}

	@ConfigItem(
			keyName = "notifyPurpleportal",
			name = "Purple portal shield",
			description = "Notifies the player when the shield for purple portal is down.",
			position = 6
	)
	default boolean notifyPurpleportal()
	{
		return false;
	}

	@ConfigItem(
			keyName = "notifyBlueportal",
			name = "Blue portal shield",
			description = "Notifies the player when the shield for blue portal is down.",
			position = 7
	)
	default boolean notifyBlueportal()
	{
		return false;
	}
}
