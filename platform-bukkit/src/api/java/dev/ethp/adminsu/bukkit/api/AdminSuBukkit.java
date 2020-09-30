package dev.ethp.adminsu.bukkit.api;

import dev.ethp.adminsu.api.SuPlayer;
import dev.ethp.adminsu.api.platform.SuApi;

import dev.ethp.adminsu.bukkit.api.extension.ExtensionManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The admin-su API implementation for Bukkit.
 *
 * @since 2.0.0
 */
public abstract class AdminSuBukkit implements SuApi<Plugin, Player> {

	protected AdminSuBukkit() {
	}

	// -------------------------------------------------------------------------------------------------------------
	// Initialize:
	// -------------------------------------------------------------------------------------------------------------

	static AdminSuBukkit IMPLEMENTATION;

	protected void initialize(@NotNull AdminSuBukkit impl) {
		AdminSuBukkit.IMPLEMENTATION = impl;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Static:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the admin-su API instance.
	 *
	 * @return The API instance.
	 * @throws IllegalStateException If admin-su hasn't loaded yet.
	 * @since 2.0.0
	 */
	static public @NotNull AdminSuBukkit api() {
		if (AdminSuBukkit.IMPLEMENTATION == null) throw new IllegalStateException("admin-su not loaded yet");
		return AdminSuBukkit.IMPLEMENTATION;
	}

	/**
	 * Gets admin-su information relating to a player.
	 *
	 * @param player The player.
	 * @return The player information.
	 * @throws IllegalStateException If admin-su hasn't loaded yet.
	 * @since 2.0.0
	 */
	static public @NotNull SuPlayer player(@NotNull Player player) {
		return api().getPlayer(player);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public abstract @NotNull ExtensionManager getExtensionManager();

}
