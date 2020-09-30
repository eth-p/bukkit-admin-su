package dev.ethp.adminsu.bukkit.impl;

import dev.ethp.adminsu.api.SuPlayer;

import dev.ethp.adminsu.bukkit.PluginMain;
import dev.ethp.adminsu.bukkit.api.AdminSuBukkit;
import dev.ethp.adminsu.bukkit.api.extension.ExtensionManager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * Bukkit platform implementation for {@link AdminSuBukkit}.
 */
public final class ImplApi extends AdminSuBukkit {

	public final PluginMain plugin;

	public final BukkitPlatformAdapter adapter;
	public final ImplExtensionManager extenions;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ImplApi(PluginMain plugin) {
		this.plugin = plugin;

		this.adapter = new BukkitPlatformAdapter(this.plugin);
		this.extenions = new ImplExtensionManager(this.adapter);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Initializes the API, and registers it as the bukkit API instance.
	 */
	public void initialize() {
		this.initialize(this);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull ExtensionManager getExtensionManager() {
		return this.extenions;
	}

	@Override
	public @NotNull SuPlayer getPlayer(Player player) {
		return new ImplSuPlayer(this, player);
	}


}
