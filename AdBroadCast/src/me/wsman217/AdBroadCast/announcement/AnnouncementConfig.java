package me.wsman217.AdBroadCast.announcement;

import java.io.File;
import java.util.logging.Level;

import me.wsman217.AdBroadCast.Main;

public class AnnouncementConfig {
	private Main plugin;

	public AnnouncementConfig(Main plugin) {
		this.plugin = plugin;
	}

	public void load() {
		File config = new File(plugin.getDataFolder(), "config.yml");
		if (!config.exists()) {
			plugin.saveDefaultConfig();
			plugin.getLogger().log(Level.INFO, "Creating Default Config File");
		}
		plugin.reloadConfig();
		plugin.setPrefix(plugin.getConfig().getString("prefix"));
		plugin.setMessages(plugin.getConfig().getStringList("messages"));
		plugin.setInterval(plugin.getConfig().getInt("interval"));
		plugin.setActive(plugin.getConfig().getBoolean("enabled"));
		plugin.setRandom(plugin.getConfig().getBoolean("random"));
		plugin.getLogger().log(Level.INFO, "Config Loaded");
	}

	public void save() {
		plugin.getConfig().set("prefix", plugin.getPrefix());
		plugin.getConfig().set("messages", plugin.getMessages());
		plugin.getConfig().set("interval", Integer.valueOf(plugin.getInterval()));
		plugin.getConfig().set("enabled", Boolean.valueOf(plugin.isActive()));
		plugin.getConfig().set("random", Boolean.valueOf(plugin.isRandom()));
		plugin.saveConfig();
	}
}
