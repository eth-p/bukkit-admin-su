package dev.ethp.adminsu.bukkit.impl;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import dev.ethp.adminsu.api.platform.SuApi;
import dev.ethp.adminsu.base.i18n.CommonMessages;
import dev.ethp.adminsu.base.i18n.MessageSet;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;
import dev.ethp.adminsu.base.platform.PluginAdapter;

import dev.ethp.adminsu.bukkit.PluginMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform implementation for {@link PlatformAdapter}. 
 */
public final class BukkitPlatformAdapter implements PlatformAdapter<Plugin, Player> {

	private final PluginMain plugin;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public BukkitPlatformAdapter(PluginMain plugin) {
		this.plugin = plugin;
	}

	
	// -------------------------------------------------------------------------------------------------------------
	// Implementation: PlatformAdapter
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull PlayerAdapter player(@NotNull Player player) {
		return new BukkitPlayerAdapter(player);
	}

	@Override
	public @NotNull PluginAdapter plugin(@NotNull Plugin plugin) {
		return new BukkitPluginAdapter(plugin);
	}

	@Override
	public @NotNull String stripColor(@NotNull String message) {
		return ChatColor.stripColor(message);
	}

	@Override
	public @NotNull List<@NotNull PlayerAdapter> getPlayers() {
		return this.plugin.getServer().getOnlinePlayers().stream()
				.map(BukkitPlayerAdapter::new)
				.collect(Collectors.toList());
	}

	@Override
	public @NotNull SuApi<Plugin, Player> getApi() {
		return this.plugin.getApi();
	}

	@Override
	public @NotNull Executor getServerExecutor() {
		// If we're already running on the main thread, we want to avoid scheduling something.
		// Doing so would cause the "async" method to block the main thread while waiting for the
		// runTask executor's result, which is dependent on the main thread continuing.
		//
		// TL;DR: If we don't do this, we get a deadlock :)
		if (Bukkit.isPrimaryThread()) return Runnable::run;
		
		// Return an executor that runs on the next server tick.
		return runnable -> this.plugin.getServer().getScheduler().runTask(this.plugin, runnable);
	}

	@Override
	public void reload() {
		this.plugin.reload();
	}

	@Override
	public @NotNull MessageSet getMessages() {
		return this.plugin.getMessages();
	}

	@Override
	public @NotNull CommonMessages getCommonMessages() {
		return this.plugin.getCommonMessages();
	}

	@Override
	public @NotNull PluginAdapter getPlugin() {
		return plugin(this.plugin);
	}
	
}
