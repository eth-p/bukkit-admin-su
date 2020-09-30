package dev.ethp.adminsu.bukkit.impl;

import java.util.Objects;
import java.util.UUID;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.permission.PermissionKey;
import dev.ethp.adminsu.base.platform.PlayerAdapter;
import dev.ethp.adminsu.base.platform.Unwrap;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract implementation of {@link PlayerAdapter} for Bukkit platform.
 */
public class BukkitPlayerAdapterBase<T extends CommandSender> implements PlayerAdapter, Unwrap<T> {

	protected final @NotNull T player;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public BukkitPlayerAdapterBase(@NotNull T player) {
		this.player = player;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: PlayerAdapter
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public boolean isConsole() {
		return false;
	}

	@Override
	public boolean hasPermission(PermissionKey permission) {
		return this.player.hasPermission(permission.getPermission());
	}

	@Override
	public @NotNull String getName() {
		return this.player.getName();
	}

	@Override
	public @NotNull UUID getUUID() {
		throw new UnsupportedOperationException(this.player.toString() + " does not have a UUID.");
	}

	@Override
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}

	@Override
	public void sendMessage(String... messages) {
		this.player.sendMessage(messages);
	}

	@Override
	public void sendMessage(Message message) {
		this.sendMessage(message.toString());
	}

	@Override
	public void sendMessage(Message... messages) {
		for (Message message : messages) {
			this.sendMessage(message.toString());
		}
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Unwrap
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public T unwrap() {
		return this.player;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Object
	// -------------------------------------------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BukkitPlayerAdapterBase)) return false;
		BukkitPlayerAdapterBase<?> that = (BukkitPlayerAdapterBase<?>) o;
		return player.equals(that.player);
	}

	@Override
	public int hashCode() {
		return player.hashCode();
	}
}
