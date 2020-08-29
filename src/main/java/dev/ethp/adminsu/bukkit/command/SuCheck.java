package dev.ethp.adminsu.bukkit.command;

import java.util.List;
import java.util.Optional;

import dev.ethp.adminsu.bukkit.Plugin;
import dev.ethp.adminsu.bukkit.Su;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_CHECK;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE;
import static dev.ethp.adminsu.bukkit.PluginUtil.*;
import static dev.ethp.adminsu.bukkit.PluginUtil.reducePlayers;

public class SuCheck implements CommandExecutor {

	private final Plugin adminsu;

	public SuCheck(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!expectPermission(sender, PERMISSION_SU_CHECK)) return false;
		if (args.length != 1) {
			adminsu.i18n().COMMAND_CHECK_USAGE.param("command", label).send(sender);
			return false;
		}

		// Query:
		String query = args[0];
		List<Player> players = findPlayer(sender.getServer(), query);

		if (players.size() == 0) {
			adminsu.i18n().ERROR_PLAYER_NOT_FOUND
					.param("query", query)
					.send(sender);
			return true;
		}

		Optional<Player> targetOpt = reducePlayers(players, query);
		if (!targetOpt.isPresent()) {
			adminsu.i18n().ERROR_PLAYER_MULTIPLE_FOUND
					.param("query", query)
					.send(sender);
			return true;
		}

		// Check the player can be admin.
		Player target = targetOpt.get();
		if (!target.hasPermission(PERMISSION_SU_TOGGLE)) {
			adminsu.i18n().ADMINSU_CHECK_IMPOSSIBLE
					.param("player", target.getName())
					.param("player_nickname", target.getDisplayName())
					.send(sender);
			return true;
		}
		
		// Check the player.
		(Su.check(target) ? adminsu.i18n().ADMINSU_CHECK_ENABLED : adminsu.i18n().ADMINSU_CHECK_DISABLED)
				.param("player", target.getName())
				.param("player_nickname", target.getDisplayName())
				.send(sender);
		return true;
	}

}
