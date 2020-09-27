package dev.ethp.adminsu.bukkit;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtil extends JavaPlugin {

	/**
	 * Asserts the a command sender is a player.
	 * A message will be sent if the sender is not a player.
	 *
	 * @param player The command sender.
	 * @return False if the sender is the console.
	 */
	public static boolean expectPlayer(CommandSender player) {
		if (player instanceof Player) return true;
		Plugin.INSTANCE.i18n().ERROR_CONSOLE.send(player);
		return false;
	}

	/**
	 * Expects that a player has a permission.
	 * A message will be sent if they do not have the permission.
	 *
	 * @param player     The player.
	 * @param permission The permission.
	 * @return False if the player does not have a permission.
	 */
	public static boolean expectPermission(CommandSender player, String permission) {
		if (!(player instanceof Player)) return true;
		if (player.hasPermission(permission)) return true;
		Plugin.INSTANCE.i18n().ERROR_PERMISSION.send(player);
		return false;
	}

	/**
	 * Finds a player.
	 * <p>
	 * Order:
	 *
	 * <ul>
	 *     <li>Exact username.</li>
	 *     <li>Exact nickname.</li>
	 *     <li>Partial username.</li>
	 *     <li>Partial nickname.</li>
	 * </ul>
	 *
	 * @param server The server.
	 * @param query  The query.
	 * @return A list of matching players. Complete matches will always be on top.
	 */
	public static List<Player> findPlayer(Server server, String query) {
		Player foundRealExact = null;
		Player foundNickExact = null;
		LinkedList<Player> foundReal = new LinkedList<>();
		LinkedList<Player> foundNick = new LinkedList<>();
		final String search = query.toLowerCase();

		// Search and compare.
		for (Player player : server.getOnlinePlayers()) {
			String name = player.getName().toLowerCase();
			String nick = ChatColor.stripColor(player.getDisplayName().toLowerCase());

			// Name - Full
			if (name.equals(search)) {
				foundRealExact = player;
			}

			// Nick - Full
			if (nick.equals(search)) {
				foundNickExact = player;
			}

			// Name - Partial
			if (name.contains(search)) {
				foundReal.add(player);
			}

			// Nick - Partial
			if (nick.contains(search)) {
				foundNick.add(player);
			}
		}

		// Merge.
		foundReal.addAll(foundNick);
		if (foundNickExact != null) foundReal.addFirst(foundNickExact);
		if (foundRealExact != null) foundReal.addFirst(foundRealExact);
		return foundReal;
	}

	/**
	 * Reduces a list of found players to a single matche, if possible.
	 * If it cannot be reduced to one match, this returns nothing.
	 *
	 * @param players The found players.
	 * @param query   The query.
	 * @return The reduced matches.
	 */
	public static Optional<Player> reducePlayers(List<Player> players, String query) {
		if (players.size() == 0) return Optional.empty();
		if (players.size() == 1) return Optional.of(players.get(0));

		int i = 0;
		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(query)) return Optional.of(player);
			if (ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(query)) return Optional.of(player);

			if (++i >= 2) break;
		}

		return Optional.empty();
	}

}
