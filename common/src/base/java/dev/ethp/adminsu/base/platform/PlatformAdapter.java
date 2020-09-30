package dev.ethp.adminsu.base.platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import java.util.stream.Stream;

import dev.ethp.adminsu.api.SuPlayer;
import dev.ethp.adminsu.api.platform.SuApi;
import dev.ethp.adminsu.base.i18n.CommonMessages;
import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageSet;

import org.jetbrains.annotations.NotNull;

/**
 * An adapter for interacting with different platforms.
 *
 * @param <Plugin> The base plugin type for the platform.
 * @param <Player> The base player type for the platform.
 */
public interface PlatformAdapter<Plugin, Player> {

	// -------------------------------------------------------------------------------------------------------------
	// Methods: Adapter
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a {@link PlayerAdapter player adapter} from a platform player.
	 *
	 * @param player The player.
	 * @return The player adapter.
	 */
	@NotNull PlayerAdapter player(@NotNull Player player);

	/**
	 * Creates a {@link PluginAdapter player adapter} from a platform plugin.
	 *
	 * @param plugin The plugin.
	 * @return The plugin adapter.
	 */
	@NotNull PluginAdapter plugin(@NotNull Plugin plugin);


	// -------------------------------------------------------------------------------------------------------------
	// Methods: Utility
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Strips color from a chat string.
	 *
	 * @param message The message.
	 * @return The stripped message.
	 */
	@NotNull String stripColor(@NotNull String message);

	/**
	 * Gets all online players.
	 *
	 * @return The list of online players.
	 */
	@NotNull List<@NotNull PlayerAdapter> getPlayers();

	/**
	 * Finds a player by name.
	 * <p>
	 * Order:
	 *
	 * <ul>
	 *     <li>Exact username.</li>
	 *     <li>Exact nickname. (if available)</li>
	 *     <li>Partial username.</li>
	 *     <li>Partial nickname. (if available)</li>
	 * </ul>
	 *
	 * @param query The player name.
	 * @return A list of matching players. Complete matches will always be on top.
	 * @apiNote This is not async-safe, and must be called from the server's main thread.
	 */
	default @NotNull List<@NotNull PlayerAdapter> findPlayers(@NotNull String query) {
		PlayerAdapter foundRealExact = null;
		PlayerAdapter foundNickExact = null;
		List<PlayerAdapter> foundReal = new ArrayList<>();
		List<PlayerAdapter> foundNick = new ArrayList<>();
		final String search = query.toLowerCase();

		// Search and compare.
		for (PlayerAdapter player : this.getPlayers()) {
			String name = player.getName().toLowerCase();
			String nick = this.stripColor(player.getDisplayName().toLowerCase());

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

		// Remove duplicates.
		if (foundNickExact != null) {
			foundNick.remove(foundNickExact);
			foundReal.remove(foundNickExact);
		}

		if (foundRealExact != null) {
			foundNick.remove(foundRealExact);
			foundReal.remove(foundRealExact);
		}
		
		// Merge.
		List<PlayerAdapter> result = new ArrayList<>(foundNick.size() + foundReal.size() + 2);
		if (foundRealExact != null) result.add(foundRealExact);
		if (foundNickExact != null) result.add(foundNickExact);
		result.addAll(foundReal);
		result.addAll(foundNick);
		
		// Remove duplicates.
		return new ArrayList<>(new LinkedHashSet<PlayerAdapter>(result));
	}

	/**
	 * Reduces a list of found players to a single match, if possible.
	 * If it cannot be reduced to one match, this returns nothing.
	 *
	 * @param players The players to reduce.
	 * @param query   The player name.
	 * @return The single match.
	 */
	default @NotNull Optional<PlayerAdapter> findPlayer(@NotNull List<@NotNull PlayerAdapter> players, @NotNull String query) {
		if (players.size() == 0) return Optional.empty();
		if (players.size() == 1) {
			final PlayerAdapter player = players.get(0);
			final String search = query.toLowerCase();

			if (player.getName().toLowerCase().contains(search)) return Optional.of(player);
			if (player.getDisplayName().toLowerCase().contains(search)) return Optional.of(player);
			return Optional.empty();
		}

		for (PlayerAdapter player : players) {
			if (player.getName().equalsIgnoreCase(query)) return Optional.of(player);
			if (this.stripColor(player.getDisplayName()).equalsIgnoreCase(query)) return Optional.of(player);
		}

		return Optional.empty();
	}

	/**
	 * Reduces the list of online players to a single match, if possible.
	 * If it cannot be reduced to one match, this returns nothing.
	 *
	 * @param query The player name.
	 * @return The single match.
	 */
	default @NotNull Optional<PlayerAdapter> findPlayer(@NotNull String query) {
		return this.findPlayer(this.getPlayers(), query);
	}

	/**
	 * Attempts to get a player's {@link SuPlayer} object.
	 *
	 * @param player The player.
	 * @return The player's {@link SuPlayer} object.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	default @NotNull SuPlayer getPlayerSu(@NotNull PlayerAdapter player) {
		return ((SuApi) this.getApi()).getPlayer(((Unwrap) player).unwrap());
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods: Plugin
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the API instance for the platform.
	 *
	 * @return The API instance.
	 */
	@NotNull SuApi<Plugin, Player> getApi();

	/**
	 * Gets the main thread executor for the platform.
	 *
	 * @return The main thread executor.
	 */
	@NotNull Executor getServerExecutor();

	/**
	 * Reloads the admin-su configuration.
	 */
	void reload();

	/**
	 * Gets the localized messages of admin-su.
	 *
	 * @return The admin-su messages.
	 */
	@NotNull MessageSet getMessages();

	/**
	 * Gets the localized common messages of the platform (or admin-su).
	 *
	 * @return The common messages.
	 */
	@NotNull CommonMessages getCommonMessages();

	/**
	 * Gets the plugin implementing admin-su.
	 *
	 * @return The admin-su plugin.
	 */
	@NotNull PluginAdapter getPlugin();

}
