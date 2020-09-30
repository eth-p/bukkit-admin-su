package dev.ethp.adminsu.api;

import org.jetbrains.annotations.NotNull;

/**
 * Information about a player's su status.
 *
 * @since 2.0.0
 */
public interface SuPlayer {

	/**
	 * Gets the player's su status.
	 *
	 * @return The player's su status.
	 */
	@NotNull SuStatus getStatus();

	/**
	 * Checks if the player is currently in su mode.
	 * This is an alias to {@code getStatus().check()}.
	 *
	 * @return True if the player is in su.
	 */
	default boolean isSu() {
		return this.getStatus().check();
	}

	/**
	 * Checks if the player can enter su mode.
	 *
	 * @return True if the player has permission.
	 */
	boolean canSu();

	/**
	 * Causes the player to enter su mode.
	 *
	 * @throws IllegalStateException If the player cannot be in su mode.
	 * @see #canSu()
	 */
	void enter();

	/**
	 * Causes the player to exit su mode.
	 *
	 * @throws IllegalStateException If the player cannot be in su mode.
	 * @see #canSu()
	 */
	void exit();

}
