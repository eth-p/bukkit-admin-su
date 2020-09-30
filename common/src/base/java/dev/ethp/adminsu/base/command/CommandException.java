package dev.ethp.adminsu.base.command;

import java.util.Optional;

import dev.ethp.adminsu.base.i18n.Message;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An {@link Exception exception} indicating that a command has failed.
 */
public class CommandException extends RuntimeException {

	private @Nullable String label;
	
	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new exception.
	 *
	 * @param message The exception message.
	 */
	public CommandException(@NotNull String message) {
		super(message);
	}

	/**
	 * Creates a new exception.
	 *
	 * @param message The exception message.
	 */
	public CommandException(@NotNull Message message) {
		this(message.toString());
	}

	/**
	 * Creates a new exception with a cause.
	 *
	 * @param message The exception message.
	 * @param cause   The exception cause.
	 */
	public CommandException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new exception with a cause.
	 *
	 * @param message The exception message.
	 * @param cause   The exception cause.
	 */
	public CommandException(@NotNull Message message, @NotNull Throwable cause) {
		this(message.toString(), cause);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Internal:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Sets the command label.
	 *
	 * @param label The command label.
	 */
	void setLabel(@NotNull String label) {
		this.label = label;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the command label to display, if applicable.
	 * This may be set if the command was executed as a subcommand.
	 *
	 * @return The command label.
	 */
	public Optional<String> getLabel() {
		return Optional.ofNullable(this.label);
	}
	
}
