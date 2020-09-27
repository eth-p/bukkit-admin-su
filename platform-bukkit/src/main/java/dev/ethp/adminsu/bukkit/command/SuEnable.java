package dev.ethp.adminsu.bukkit.command;

import dev.ethp.adminsu.bukkit.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPermission;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPlayer;

public class SuEnable implements CommandExecutor {

	private final Plugin adminsu;

	public SuEnable(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!expectPlayer(sender)) return true;
		if (!expectPermission(sender, PERMISSION_SU_TOGGLE)) return true;

		if (args.length != 0) {
			adminsu.i18n().COMMAND_ENABLE_USAGE.param("command", label).send(sender);
			return true;
		}

		SuToggle.perform(adminsu, sender, b -> true);
		return true;
	}

}
