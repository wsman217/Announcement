package me.wsman217.AdBroadCast.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wsman217.AdBroadCast.Main;

public class ChatColorCodes implements CommandExecutor {

	public Main plugin;

	public ChatColorCodes(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("Ad.ChatColor.list")) {
			sender.sendMessage(ChatColor.WHITE + "Availible color codes to use with /ad");
			sender.sendMessage(ChatColor.BLACK + "Black: &0");
			sender.sendMessage(ChatColor.DARK_BLUE + "Dark blue: &1");
			sender.sendMessage(ChatColor.DARK_GREEN + "Dark green: &2");
			sender.sendMessage(ChatColor.DARK_AQUA + "Dark aqua: &3");
			sender.sendMessage(ChatColor.DARK_RED + "Dark red: &4");
			sender.sendMessage(ChatColor.DARK_PURPLE + "Dark purple: &5");
			sender.sendMessage(ChatColor.GOLD + "Gold: &6");
			sender.sendMessage(ChatColor.GRAY + "Gray: &7");
			sender.sendMessage(ChatColor.DARK_GRAY + "Dark gray: &8");
			sender.sendMessage(ChatColor.BLUE + "Blue: &9");
			sender.sendMessage(ChatColor.GREEN + "Green: &a");
			sender.sendMessage(ChatColor.AQUA + "Aqua: &b");
			sender.sendMessage(ChatColor.RED + "Red: &c");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Light purple: &d");
			sender.sendMessage(ChatColor.YELLOW + "Yellow: &e");
			sender.sendMessage(ChatColor.WHITE + "White: &f");

			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.RESET + "Availible formatting codes: ");
				sender.sendMessage("Obfuscated: &k [Ex: " + ChatColor.MAGIC + "This is Sparta!" + ChatColor.RESET + "]");
				sender.sendMessage(ChatColor.BOLD + "Bold: &l" + ChatColor.RESET);
				sender.sendMessage(ChatColor.UNDERLINE + "Underline: &n" + ChatColor.RESET);
				sender.sendMessage(ChatColor.STRIKETHROUGH + "Strikethrough: &m" + ChatColor.RESET);
				sender.sendMessage(ChatColor.ITALIC + "Italic: &o");
				sender.sendMessage(ChatColor.RESET + "Reset: &r");
			} else if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "The console does not display the formats correctly.");
			}
			return true;
		}
		return false;
	}

}
