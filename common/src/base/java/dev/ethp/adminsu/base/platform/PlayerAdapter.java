package dev.ethp.adminsu.base.platform;

import java.util.UUID;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.permission.PermissionKey;

import org.jetbrains.annotations.NotNull;

/**
 * A wrapper around the Player object on a platform.
 */
public interface PlayerAdapter {

	// -------------------------------------------------------------------------------------------------------------
	// Getters:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Checks if the player is actually the console.
	 *
	 * @return The console.
	 */
	boolean isConsole();

	/**
	 * Checks if the player has a permission.
	 *
	 * @param permission The permission to check.
	 * @return True if the player has the permission.
	 */
	boolean hasPermission(PermissionKey permission);

	/**
	 * Gets the player's name.
	 *
	 * @return The player's name.
	 */
	@NotNull String getName();

	/**
	 * Gets the player's display name.
	 *
	 * @return The player's display name.
	 */
	default @NotNull String getDisplayName() {
		return this.getName();
	}

	/**
	 * Gets the player's UUID.
	 *
	 * @return The player UUID.
	 * @throws UnsupportedOperationException If the player is the console.
	 */
	@NotNull UUID getUUID();


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Sends the player a message.
	 *
	 * @param message The message to send.
	 */
	void sendMessage(String message);

	/**
	 * Sends the player multiple messages.
	 *
	 * @param messages The message to send.
	 */
	default void sendMessage(String... messages) {
		for (String message : messages) {
			this.sendMessage(message);
		}
	}

	/**
	 * Sends the player a message.
	 *
	 * @param message The message to send.
	 */
	default void sendMessage(Message message) {
		this.sendMessage(message.toString());
	}

	/**
	 * Sends the player multiple messages.
	 *
	 * @param messages The message to send.
	 */
	default void sendMessage(Message... messages) {
		for (Message message : messages) {
			this.sendMessage(message);
		}
	}


}
