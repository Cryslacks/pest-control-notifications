package com.pestcontrolnotifications;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PestControlNotificationsTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PestControlNotificationsPlugin.class);
		RuneLite.main(args);
	}
}