package dev.ethp.adminsu.base.command;


/**
 * A {@link CommandException command exception} indicating that it requires a player to execute the command.
 */
public class CommandRequiresPlayerException extends CommandException {

	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new exception.
	 */
	public CommandRequiresPlayerException() {
		super("Command requires player to execute");
	}

}
