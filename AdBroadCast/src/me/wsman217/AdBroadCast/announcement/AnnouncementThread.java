package me.wsman217.AdBroadCast.announcement;

import org.bukkit.scheduler.BukkitRunnable;

import me.wsman217.AdBroadCast.Main;

public class AnnouncementThread extends BukkitRunnable {
	private Main plugin;

	public AnnouncementThread(Main plugin) {
		this.plugin = plugin;
	}

	public void run() {
		if (plugin.isRandom() == true) {
			plugin.broadcastRandomMessage();
		} else {
			plugin.broadcastNextMessage();
		}
	}
}
