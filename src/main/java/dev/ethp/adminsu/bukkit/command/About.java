package dev.ethp.adminsu.bukkit.command;

import dev.ethp.adminsu.bukkit.Hook;
import dev.ethp.adminsu.bukkit.Plugin;
import dev.ethp.adminsu.bukkit.PluginMessages;
import dev.ethp.adminsu.bukkit.i18n.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import static dev.ethp.adminsu.bukkit.PluginPermissions.*;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPermission;

public class About implements CommandExecutor {

	private final Plugin adminsu;

	public About(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission(PERMISSION_SU_CHECK) && !sender.hasPermission(PERMISSION_SU_TOGGLE)) {
			adminsu.i18n().ERROR_PERMISSION
					.param("command", label)
					.send(sender);
			return false;
		}

		final PluginMessages i18n = adminsu.i18n();
		i18n.PREFIX.append(ChatColor.GOLD).append("admin-su").send(sender);
		i18n.PREFIX.append(ChatColor.YELLOW).append("Developer: ").append(ChatColor.RESET).append("eth-p").send(sender);
		i18n.PREFIX.append(ChatColor.YELLOW).append("Website:    ").append(ChatColor.RESET).append("https://github.com/eth-p/bukkit-admin-su").send(sender);
		if (sender.hasPermission(PERMISSION_RELOAD)) {
			for (Hook hook : adminsu.hooks()) {
				i18n.PREFIX.append(ChatColor.GOLD).append(
						(hook.isEnabled() ? i18n.COMMAND_ABOUT_FEATURE_ENABLED : i18n.COMMAND_ABOUT_FEATURE_DISABLED)
								.param("feature", hook.name())
				).send(sender);
			}
		}
		if (adminsu.isAdminOp()) {
			i18n.COMMAND_ABOUT_WARNING_OPMODE.send(sender);
		}
		return true;
	}

}
