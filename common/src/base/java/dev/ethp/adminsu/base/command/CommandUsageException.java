package dev.ethp.adminsu.base.command;

import dev.ethp.adminsu.base.i18n.MessageKey;

import org.jetbrains.annotations.NotNull;


/**
 * A {@link CommandException command exception} indicating that it is being used incorrectly.
 */
public class CommandUsageException extends CommandException {

	private final @NotNull MessageKey usage;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new exception.
	 *
	 * @param usage   The usage message key.
	 * @param message The exception message.
	 */
	public CommandUsageException(@NotNull MessageKey usage, @NotNull String message) {
		super(message);
		this.usage = usage;
	}

	/**
	 * Creates a new exception.
	 *
	 * @param usage The usage message key.
	 */
	public CommandUsageException(@NotNull MessageKey usage) {
		super("Invalid command usage");
		this.usage = usage;
	}


	/**
	 * Gets the command usage message localization key.
	 *
	 * @return The localization key.
	 */
	public @NotNull MessageKey getUsage() {
		return this.usage;
	}


}
