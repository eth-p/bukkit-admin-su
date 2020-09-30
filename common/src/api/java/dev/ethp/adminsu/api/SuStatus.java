package dev.ethp.adminsu.api;

import java.util.Date;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

/**
 * An object representing a player's su status.
 *
 * @since 2.0.0
 */
public final class SuStatus {

	private final boolean enabled;
	private final Date since;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public SuStatus(boolean enabled) {
		this.enabled = enabled;
		this.since = null;
	}

	public SuStatus(boolean enabled, @NotNull Date since) {
		this.enabled = enabled;
		this.since = enabled ? since : null;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Checks if the player is in su mode.
	 *
	 * @return True if the player is su.
	 */
	public boolean check() {
		return this.enabled;
	}

	/**
	 * Gets when the player entered su mode.
	 *
	 * @return The date when the player entered su, or empty if not applicable.
	 */
	public @NotNull Optional<Date> since() {
		return Optional.ofNullable(this.since);
	}

}
