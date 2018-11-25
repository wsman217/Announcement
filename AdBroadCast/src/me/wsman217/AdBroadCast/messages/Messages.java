package me.wsman217.AdBroadCast.messages;

import org.bukkit.ChatColor;

import me.wsman217.AdBroadCast.Main;

public class Messages {
	public final String PLAYER_HELP_MSG, CONSOLE_HELP_MSG;

	public Messages(Main plugin) {
		PLAYER_HELP_MSG = ChatColor.translateAlternateColorCodes('&',
				plugin.getConfig().getString("Player_Help_Message",
						"&f[AdBroadCast]: &7List of arguments to follow &b/ad\n"
								+ "add: &7<message> &f- Add a message to the announcement list.\n"
								+ "&bremove: &7<id> &f- Remove a message from the announcement list.\n"
								+ "&blist: &f- List the current announcement messages.\n"
								+ "&benable: &f- Enable announcement messages.\n"
								+ "&bdisable: &f- Disable announcement messages.\n"
								+ "&bprefix: &7<prefix:off> &f- Change the announcement prefix.\n"
								+ "&bbroadcast: &7<id> &f- Manually broadcast an announcement message.\n"
								+ "&bsay: &7<message> &f- Broadcast message with announcement prefix.\n"
								+ "&binterval: &7<seconds> &f- Change the interval time between messages.\n"
								+ "&breload: &f- Reload announcement configuration.\n"
								+ "&brandom: &f- Toggle random mode on and off."));

		CONSOLE_HELP_MSG = plugin.getConfig().getString("Console_Help_Msg",
				"[AdBroadCast]: List of arguments to follow /ad\n"
						+ "add: <message> - Add a message to the announcement list.\n"
						+ "remove: <id> - Remove a message from the announcement list.\n"
						+ "list: - List the current announcement messages."
						+ "enable: - Enable announcement messages.\n" + "disable: - Disable announcement messages.\n"
						+ "prefix: <prefix:off> - Change the announcement prefix.\n"
						+ "broadcast: <id> - BroadCast message with announcement prefix.\n"
						+ "say: <message> - BroadCast a message with announcement prefix.\n"
						+ "interval: <seconds> - Change teh interval time between messages.\n"
						+ "reload: - Reload announcement configuration.\n"
						+ "random: - Toggle random mode on and off.");

	}
}
