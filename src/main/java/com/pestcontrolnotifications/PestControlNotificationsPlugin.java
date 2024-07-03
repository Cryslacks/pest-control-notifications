package com.pestcontrolnotifications;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Pest Control Notifications"
)
public class PestControlNotificationsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PestControlNotificationsConfig config;

	@Inject
	private Notifier notifier;

	private static final int REGION_HUB = 10537;
	private static final int REGION_ISLAND = 10536;
	private static final int VARBIT_PC_ACTIVITY = 5662;
	private static final int DELAY_TEN_SECONDS = 17;

	private boolean notifyStart = false;
	private boolean notifyEnd = false;
	private boolean notifyActivity = false;
	private boolean notifyRed = false;
	private boolean notifyYellow = false;
	private boolean notifyPurple = false;
	private boolean notifyBlue = false;

	private int notificationDelay = 17;
	private int activityLimit = 0;
	private int lastRegion = -1;

	@Override
	protected void startUp() throws Exception
	{
		this.notifyStart = config.notifyStart();
		this.notifyEnd = config.notifyEnd();
		this.notifyActivity = config.notifyActivity();
		this.notifyRed = config.notifyRedportal();
		this.notifyYellow = config.notifyYellowportal();
		this.notifyPurple = config.notifyPurpleportal();
		this.notifyBlue = config.notifyBlueportal();
		this.activityLimit = config.activityLimit();
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		int regionID = WorldPoint.fromLocalInstance(this.client, this.client.getLocalPlayer().getLocalLocation()).getRegionID();

		if (regionID == REGION_HUB || regionID == REGION_ISLAND) {
			int activity = client.getVarbitValue(VARBIT_PC_ACTIVITY);
			int activityPercentage = activity * 2; // Each activity is on average equal to 2%

			if (regionID == REGION_ISLAND && lastRegion == REGION_HUB && notifyStart) {
				notifier.notify("Pest Control game started!");
			}

			if (regionID == REGION_HUB && lastRegion == REGION_ISLAND && notifyEnd) {
				notifier.notify("Pest Control game ended!");
			}

			if (activity > 0 && notifyActivity && activityPercentage <= activityLimit && regionID == REGION_ISLAND && notificationDelay <= 0) {
				notifier.notify("Pest Control activity warning!");
				notificationDelay = DELAY_TEN_SECONDS;
			} else {
				notificationDelay--;
			}

			this.lastRegion = regionID;
		}
	}

	@Subscribe
	private void onChatMessage(ChatMessage chatMessage) {
		String msg = chatMessage.getMessage();
		if (chatMessage.getMessageNode().getType() == ChatMessageType.GAMEMESSAGE) {
			if (notifyBlue && msg.equals("The blue, eastern portal shield has dropped!")) {
				notifier.notify("Pest Control blue portal shield dropped!");
			} else if (notifyYellow && msg.equals("The yellow, south-eastern portal shield has dropped!")) {
				notifier.notify("Pest Control yellow portal shield dropped!");
			} else if (notifyRed && msg.equals("The red, south-western portal shield has dropped!")) {
				notifier.notify("Pest Control red portal shield dropped!");
			} else if (notifyPurple && msg.equals("The purple, western portal shield has dropped!")) {
				notifier.notify("Pest Control purple portal shield dropped!");
			}
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("pestcontrolnotifications"))
		{
			this.notifyStart = config.notifyStart();
			this.notifyEnd = config.notifyEnd();
			this.notifyActivity = config.notifyActivity();
			this.notifyRed = config.notifyRedportal();
			this.notifyYellow = config.notifyYellowportal();
			this.notifyPurple = config.notifyPurpleportal();
			this.notifyBlue = config.notifyBlueportal();
			this.activityLimit = config.activityLimit();
		}
	}

	@Provides
	PestControlNotificationsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PestControlNotificationsConfig.class);
	}
}
