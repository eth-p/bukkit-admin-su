package dev.ethp.adminsu.bukkit.impl;

import java.util.UUID;

import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform implementation for {@link PlayerAdapter}.
 */
public final class BukkitPlayerAdapter extends BukkitPlayerAdapterBase<Player> {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public BukkitPlayerAdapter(@NotNull Player player) {
		super(player);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: PlayerAdapter
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull String getDisplayName() {
		return this.player.getDisplayName();
	}

	@Override
	public @NotNull UUID getUUID() {
		return this.player.getUniqueId();
	}
	
	
}
