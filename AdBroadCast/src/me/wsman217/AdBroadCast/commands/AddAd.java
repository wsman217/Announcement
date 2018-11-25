package me.wsman217.AdBroadCast.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wsman217.AdBroadCast.Main;
import me.wsman217.AdBroadCast.announcement.AnnouncementConfig;

public class AddAd implements CommandExecutor {

	public Main plugin;
	public AnnouncementConfig config;

	private String errorMessage = "You are not allowed to use that command.";

	public AddAd(Main plugin, AnnouncementConfig config) {
		this.plugin = plugin;
		this.config = config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
				return onHelpCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("add"))
				return onAddCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("remove"))
				return onRemoveCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("list"))
				return onListCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("enable"))
				return onEnableCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("disable"))
				return onDisableCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("prefix"))
				return onPrefixCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("broadcast"))
				return onBroadcastCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("interval"))
				return onIntervalCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("reload"))
				return onReloadCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("say"))
				return onSayCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("random"))
				return onRandomCommand(sender, cmd, label, args);
		}
		sender.sendMessage(ChatColor.WHITE + "Use " + ChatColor.AQUA + "/advertisement help " + ChatColor.WHITE
				+ "for a list of commands.");
		return true;
	}

	public boolean onHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> commands = new ArrayList<String>();
		if (sender.hasPermission("advertisement.add")) {
			commands.add(ChatColor.WHITE + "/advertisement add " + ChatColor.GRAY + "<message> " + ChatColor.WHITE + "- "
					+ ChatColor.AQUA + "Add a message to the announcement list.");
		}
		if (sender.hasPermission("advertisement.remove")) {
			commands.add(ChatColor.WHITE + "/advertisement remove " + ChatColor.GRAY + "<id> " + ChatColor.WHITE + "- "
					+ ChatColor.AQUA + "Remove a message from the announcement list.");
		}
		if (sender.hasPermission("advertisement.list")) {
			commands.add(
					ChatColor.WHITE + "/advertisement list - " + ChatColor.AQUA + "List the current announcement messages.");
		}
		if (sender.hasPermission("advertisement.enable")) {
			commands.add(ChatColor.WHITE + "/advertisement enable - " + ChatColor.AQUA + "Enable announcement messages.");
		}
		if (sender.hasPermission("advertisement.disable")) {
			commands.add(ChatColor.WHITE + "/advertisement disable - " + ChatColor.AQUA + "Disable announcement messages.");
		}
		if (sender.hasPermission("advertisement.prefix")) {
			commands.add(ChatColor.WHITE + "/advertisement prefix " + ChatColor.GRAY + "<prefix:off> " + ChatColor.WHITE
					+ "- " + ChatColor.AQUA + "Change the announcement prefix.");
		}
		if (sender.hasPermission("advertisement.broadcast")) {
			commands.add(ChatColor.WHITE + "/advertisement broadcast" + ChatColor.GRAY + " <id> " + ChatColor.WHITE + "- "
					+ ChatColor.AQUA + "Manually broadcast an announcement message.");
			commands.add(ChatColor.WHITE + "/advertisement say " + ChatColor.GRAY + "<message> " + ChatColor.WHITE + "- "
					+ ChatColor.AQUA + "Broadcast message with announcement prefix.");
		}
		if (sender.hasPermission("advertisement.interval")) {
			commands.add(ChatColor.WHITE + "/advertisement interval " + ChatColor.GRAY + "<seconds> " + ChatColor.WHITE
					+ "- " + ChatColor.AQUA + "Change the interval time between messages.");
		}
		if (sender.hasPermission("advertisement.reload")) {
			commands.add(
					ChatColor.WHITE + "/advertisement reload - " + ChatColor.AQUA + "Reload announcement configuration.");
		}
		if (sender.hasPermission("advertisement.random")) {
			commands.add(ChatColor.WHITE + "/advertisement random - " + ChatColor.AQUA + "Toggle random mode on and off.");
		}
		if (commands.size() > 0) {
			sender.sendMessage(
					ChatColor.WHITE + "-------- " + ChatColor.AQUA + " Help" + ChatColor.WHITE + " --------");
			for (String s : commands) {
				sender.sendMessage(s);
			}
		} else {
			sender.sendMessage(ChatColor.RED + errorMessage);
		}
		return true;
	}

	public boolean onAddCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.add")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			String message = buildMessage(args);
			plugin.addMessage(message);
			config.save();
			sender.sendMessage(ChatColor.AQUA + "Announcement " + ChatColor.RESET
					+ ChatColor.translateAlternateColorCodes('&', message) + ChatColor.AQUA + " Added!");
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Error: Please enter a message.");
		return true;
	}

	public boolean onRemoveCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.remove")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length == 2) {
			if (isInteger(args[1])) {
				int remove = Integer.parseInt(args[1]);
				if (plugin.removeMessage(remove)) {
					sender.sendMessage(ChatColor.AQUA + "Announcement Removed!");
					config.save();
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Error: No message matching this ID.");
				return true;
			}

			sender.sendMessage(ChatColor.RED + "Error: Argument is not a number.");
			return true;
		}
		if (args.length > 2) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		if (args.length == 1) {
			sender.sendMessage(ChatColor.RED + "Error: Enter message ID.");
			return true;
		}
		return false;
	}

	private boolean onEnableCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.enable")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		if (plugin.isActive()) {
			sender.sendMessage(ChatColor.AQUA + "Announcements Already Enabled!");
			return true;
		}
		plugin.enableAnnouncement();
		sender.sendMessage(ChatColor.AQUA + "Announcements Enabled!");
		return true;
	}

	private boolean onDisableCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.disable")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		if (!plugin.isActive()) {
			sender.sendMessage(ChatColor.AQUA + "Announcements Already Disabled!");
			return true;
		}
		plugin.disableAnnouncement();
		sender.sendMessage(ChatColor.AQUA + "Announcements Disabled!");
		return true;
	}

	private boolean onListCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.list")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		if (plugin.getMessageCount() > 0) {
			sender.sendMessage(ChatColor.AQUA + "Announcement Messages:");
			for (int i = 1; i <= plugin.getMessageCount(); i++) {
				sender.sendMessage("" + ChatColor.WHITE + i + ": "
						+ ChatColor.translateAlternateColorCodes('&', plugin.getMessage(i)));
			}
			return true;
		}
		sender.sendMessage(ChatColor.AQUA + "No Messages!");
		return true;
	}

	private boolean onPrefixCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.prefix")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("off")) {
				plugin.setPrefix("");
				sender.sendMessage(ChatColor.AQUA + "Advertisement Prefix Disabled!");
			} else {
				plugin.setPrefix(buildMessage(args));
				sender.sendMessage(ChatColor.AQUA + "Advertisement Prefix Updated!");
			}
			config.save();
			return true;
		}
		if (plugin.getPrefix().equals("")) {
			sender.sendMessage(ChatColor.AQUA + "Advertisement Prefix Currently Disabled!");
		} else {
			sender.sendMessage(ChatColor.AQUA + "Advertisement Prefix " + ChatColor.WHITE + "- " + ChatColor.RESET
					+ ChatColor.translateAlternateColorCodes('&', plugin.getPrefix()));
		}
		return true;
	}

	private boolean onBroadcastCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.broadcast")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length == 2) {
			if (isInteger(args[1])) {
				int id = Integer.parseInt(args[1]);
				if (plugin.isMessage(id)) {
					plugin.broadcastMessage(id);
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Error: No message matching this ID.");
				return true;
			}
		} else if (args.length > 2) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		} else if (args.length == 1) {
			sender.sendMessage(ChatColor.RED + "Error: Not enough arguments.");
			return true;
		}
		return false;
	}

	private boolean onIntervalCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.interval")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length == 2) {
			if (isInteger(args[1])) {
				int interval = Integer.parseInt(args[1]);
				if (interval >= 1) {
					plugin.setInterval(interval);
					config.save();
					plugin.restartAnnouncement();
					sender.sendMessage(ChatColor.AQUA + "Announcement Interval Updated!");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Error: Interval must be 1 or greater.");
				return true;
			}

			sender.sendMessage(ChatColor.RED + "Error: Argument is not a valid number.");
			return true;
		}
		if (args.length > 2) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		int seconds = plugin.getInterval();
		if (seconds == 1) {
			sender.sendMessage(ChatColor.AQUA + "Announcement Interval" + ChatColor.WHITE + " - 1 Second");
			return true;
		}
		sender.sendMessage(ChatColor.AQUA + "Announcement Interval" + ChatColor.WHITE + " - " + seconds + " Seconds");
		return true;
	}

	private boolean onReloadCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.reload")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		config.load();
		plugin.restartAnnouncement();
		sender.sendMessage(ChatColor.AQUA + "Announcement Configuration Reloaded!");
		return true;
	}

	private boolean onSayCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.broadcast")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			plugin.sayMessage(buildMessage(args));
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Error: Please enter a message.");
		return true;
	}

	private boolean onRandomCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("ad.random")) {
			sender.sendMessage(ChatColor.RED + errorMessage);
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Error: Too many arguments.");
			return true;
		}
		if (plugin.isRandom()) {
			plugin.setRandom(false);
			sender.sendMessage(ChatColor.AQUA + "Announcement Random Mode Disabled!");
			return true;
		}
		plugin.setRandom(true);
		sender.sendMessage(ChatColor.AQUA + "Announcement Random Mode Enabled!");
		return true;
	}

	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private String buildMessage(String[] args) {
		String message = "";
		for (int i = 1; i < args.length; i++) {
			if (!message.equals("")) {
				message = message + " ";
			}
			message = message + args[i];
		}
		return message;
	}

}