package dev.ethp.adminsu.bukkit.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.permission.PermissionKey;
import dev.ethp.adminsu.base.platform.PluginAdapter;
import dev.ethp.adminsu.base.platform.Unwrap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform implementation for {@link dev.ethp.adminsu.base.platform.PluginAdapter}.
 */
public final class BukkitPluginAdapter implements PluginAdapter, Unwrap<Plugin> {

	private final @NotNull Plugin plugin;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public BukkitPluginAdapter(@NotNull Plugin plugin) {
		this.plugin = plugin;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: PluginAdapter
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull String getIdentifier() {
		return this.plugin.getName();
	}

	@Override
	public @NotNull Logger getLogger() {
		return this.plugin.getLogger();
	}

	@Override
	public @NotNull String getVersion() {
		return this.plugin.getDescription().getVersion();
	}

	@Override
	public Optional<String> getWebsite() {
		return Optional.ofNullable(this.plugin.getDescription().getWebsite());
	}

	@Override
	public @NotNull List<@NotNull String> getAuthors() {
		return this.plugin.getDescription().getAuthors();
	}

	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Unwrap
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public Plugin unwrap() {
		return this.plugin;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Object
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BukkitPluginAdapter)) return false;
		BukkitPluginAdapter that = (BukkitPluginAdapter) o;
		return plugin.equals(that.plugin);
	}

	@Override
	public int hashCode() {
		return Objects.hash(plugin);
	}

}
