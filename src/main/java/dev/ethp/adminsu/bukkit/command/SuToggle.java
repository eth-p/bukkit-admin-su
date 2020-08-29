package dev.ethp.adminsu.bukkit.command;

import java.util.function.Function;

import dev.ethp.adminsu.bukkit.Plugin;
import dev.ethp.adminsu.bukkit.Su;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE_BROADCASTED;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPermission;
import static dev.ethp.adminsu.bukkit.PluginUtil.expectPlayer;

public class SuToggle implements CommandExecutor {

	private final Plugin adminsu;

	public SuToggle(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!expectPlayer(sender)) return true;
		if (!expectPermission(sender, PERMISSION_SU_TOGGLE)) return true;

		if (args.length != 0) {
			adminsu.i18n().COMMAND_TOGGLE_USAGE.param("command", label).send(sender);
			return true;
		}

		perform(adminsu, sender, b -> !b);
		return true;
	}

	/**
	 * Performs the admin mode change.
	 *
	 * @param adminsu     The plugin.
	 * @param sender      The player to change.
	 * @param transformer A function that determines the mode to change into.
	 */
	static void perform(Plugin adminsu, CommandSender sender, Function<Boolean, Boolean> transformer) {
		if (!expectPlayer(sender)) return;
		if (!expectPermission(sender, PERMISSION_SU_TOGGLE)) return;

		Player player = (Player) sender;

		boolean wasAdmin = Su.check(player);
		boolean isAdmin = transformer.apply(wasAdmin);

		// Perform the toggle.
		if (isAdmin) {
			Su.enable(player);
		} else {
			Su.disable(player);
		}
		
		// Tell every other player.
		String broadcast = (isAdmin ? adminsu.i18n().SU_ENABLED_BROADCAST : adminsu.i18n().SU_DISABLED_BROADCAST)
				.param("player", player.getName())
				.param("player_nickname", player.getDisplayName())
				.toString();
				
		for (Player recipient : adminsu.getServer().getOnlinePlayers()) {
			if (recipient.equals(sender)) continue;
			if (recipient.hasPermission(PERMISSION_SU_TOGGLE_BROADCASTED)) {
				recipient.sendMessage(broadcast);
			}
		}

		// Tell the player.
		(isAdmin ? adminsu.i18n().SU_ENABLED : adminsu.i18n().SU_DISABLED)
				.param("player", player.getName())
				.param("player_nickname", player.getDisplayName())
				.send(player);
	}

}
