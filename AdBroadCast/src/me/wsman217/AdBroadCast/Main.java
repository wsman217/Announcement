package me.wsman217.AdBroadCast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.wsman217.AdBroadCast.announcement.AnnouncementConfig;
import me.wsman217.AdBroadCast.announcement.AnnouncementThread;
import me.wsman217.AdBroadCast.commands.AddAd;
import me.wsman217.AdBroadCast.commands.ChatColorCodes;
import me.wsman217.AdBroadCast.messages.Messages;

public class Main extends JavaPlugin {
	public Messages messages;

	public String prefix;
	public int interval;
	public boolean random = false;
	public int lastAnnounced = 0;
	public boolean active;
	public List<String> Announcements = new ArrayList<String>();

	public AnnouncementConfig config;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		
		config = new AnnouncementConfig(this);
		config.load();
		
		AddAd aa = new AddAd(this, config);
		
		System.out.println("AdBroadCast is ENABLED");
		System.out.println("Author: wsman217");

		messages = new Messages(this);
		getCommand("advertisement").setExecutor(aa);
		getCommand("ColorCodes").setExecutor(new ChatColorCodes(this));

	}

	@Override
	public void onDisable() {
		System.out.println("AdBroadCast is DISABLED");
		System.out.println("Author: wsman217");
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<String> getMessages() {
		return Announcements;
	}

	public void setMessages(List<String> Announcements) {
		this.Announcements = Announcements;
	}

	public void broadcastRandomMessage() {
		Random rand = new Random();
		int id = rand.nextInt(Announcements.size());
		broadcastMessage(id + 1);
	}

	public void broadcastNextMessage() {
		if (lastAnnounced >= Announcements.size()) {
			lastAnnounced = 0;
		}
		broadcastMessage(lastAnnounced + 1);
		lastAnnounced += 1;
	}

	@SuppressWarnings("deprecation")
	public void enableAnnouncement() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AnnouncementThread(this), 20 * getInterval(),
				20 * getInterval());
		active = true;
		config.save();
	}

	public void disableAnnouncement() {
		getServer().getScheduler().cancelTasks(this);
		active = false;
		config.save();
	}

	@SuppressWarnings("deprecation")
	public void restartAnnouncement() {
		if (active) {
			getServer().getScheduler().cancelTasks(this);
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new AnnouncementThread(this), 20 * getInterval(),
					20 * getInterval());
		}
	}

	public boolean isRandom() {
		return random;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void addMessage(String message) {
		Announcements.add(message);
	}

	public boolean removeMessage(int id) {
		if (isMessage(id)) {
			Announcements.remove(id - 1);
			return true;
		}
		return false;
	}

	public String getMessage(int id) {
		return (String) Announcements.get(id - 1);
	}

	public int getMessageCount() {
		return Announcements.size();
	}

	public boolean isMessage(int id) {
		if ((id <= Announcements.size()) && (id > 0)) {
			return true;
		}
		return false;
	}

	public void broadcastMessage(int id) {
		if (isMessage(id)) {
			String m = getMessage(id);
			if (m.startsWith("/")) {
				getServer().dispatchCommand(getServer().getConsoleSender(), m.substring(1));
			} else {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("announcement.receive")) {
						if (prefix.equals("")) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&r " + m));
						}
					}
				}
			}
		}
	}

	public void sayMessage(String m) {
		if (m.startsWith("/")) {
			getServer().dispatchCommand(getServer().getConsoleSender(), m.substring(1));
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasPermission("announcement.receive")) {
					if (prefix.equals("")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&r " + m));
					}
				}
			}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean enabled) {
		if (enabled) {
			enableAnnouncement();
		}
	}
	
	  public void setRandom(boolean random)
	  {
	    this.random = random;
	  }
}
