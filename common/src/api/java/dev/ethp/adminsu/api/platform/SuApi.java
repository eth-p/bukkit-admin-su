package dev.ethp.adminsu.api.platform;

import dev.ethp.adminsu.api.extension.ExtensionManager;

import dev.ethp.adminsu.api.SuPlayer;

import org.jetbrains.annotations.NotNull;

/**
 * The main API for interacting with admin-su.
 * <p>
 * This is the platform-agnostic base type.
 *
 * @param <Plugin> The base plugin type for the platform.
 * @param <Player> The base player type for the platform.
 * @since 2.0.0
 */
public interface SuApi<Plugin, Player> {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the admin-su extension manager.
	 *
	 * @return The extension manager.
	 */
	@NotNull ExtensionManager<Plugin> getExtensionManager();

	/**
	 * Gets admin-su information relating to a player.
	 *
	 * @param player The player.
	 * @return The player information.
	 */
	@NotNull SuPlayer getPlayer(Player player);


}
