package dev.ethp.adminsu.bukkit.command;

import dev.ethp.adminsu.bukkit.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_RELOAD;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPermission;

public class Reload implements CommandExecutor {

	private final Plugin adminsu;

	public Reload(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!expectPermission(sender, PERMISSION_RELOAD)) return false;

		adminsu.reload();
		adminsu.i18n().COMMAND_RELOAD_SUCCESS.send(sender);
		return true;
	}

}
