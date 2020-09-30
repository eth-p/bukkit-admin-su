package dev.ethp.adminsu.base.i18n;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for getting common messages for a platform-independent plugin.
 */
public interface CommonMessages {

	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the "no permission" message.
	 *
	 * @return The no permission message.
	 */
	@NotNull Message ERROR_NO_PERMISSION();

	/**
	 * Gets the "unknown command" message.
	 *
	 * @return The unknown command message.
	 */
	@NotNull Message ERROR_UNKNOWN_COMMAND();

	/**
	 * Gets the "command can only be executed by a player" message.
	 *
	 * @return The not available on console message.
	 */
	@NotNull Message ERROR_NO_CONSOLE();

	/**
	 * Gets the "internal command error" message.
	 *
	 * @return The command error message.
	 */
	@NotNull Message ERROR_UNCAUGHT_EXCEPTION();

}
