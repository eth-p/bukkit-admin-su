package dev.ethp.adminsu.base.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.ethp.adminsu.base.permission.NoPermissionException;
import dev.ethp.adminsu.base.permission.PermissionKey;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract implementation of {@link Command} that contains subcommands.
 */
public abstract class AbstractCommandOfSubcommands extends AbstractCommand implements Command {

	private final @NotNull Map<String, Subcommand> subcommands;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	protected AbstractCommandOfSubcommands(@NotNull PlatformAdapter<?, ?> platform, @Nullable PermissionKey permission) {
		super(platform, permission);
		this.subcommands = new HashMap<>();
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods: Protected
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Registers a subcommand.
	 *
	 * @param subcommand The subcommand to register.
	 * @param command    The corresponding command object.
	 */
	protected void register(String subcommand, Command command) {
		this.register(subcommand, command, false);
	}

	/**
	 * Registers a subcommand.
	 *
	 * @param subcommand The subcommand to register.
	 * @param command    The corresponding command object.
	 * @param hidden     Whether it should be hidden from tab completion.
	 */
	protected void register(String subcommand, Command command, boolean hidden) {
		final String subcommandName = subcommand.toLowerCase();
		this.subcommands.put(subcommandName, new Subcommand(subcommandName, command, hidden));
	}


	// -------------------------------------------------------------------------------------------------------------
	// Abstract:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the default subcommand to run.
	 *
	 * @param label    The command label.
	 * @param executor The player executing the command and receiving the command action.
	 * @param target   The player who the command is performed on.
	 * @return The default subcommand.
	 */
	protected abstract @NotNull String getDefault(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target);


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Command
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		String subcommandName = (args.size() == 0) ? this.getDefault(label, executor, target) : args.get(0);
		Subcommand subcommand = this.subcommands.get(subcommandName.toLowerCase());

		// Throw if subcommand is invalid.
		if (subcommand == null) {
			throw new CommandException(this.platform.getCommonMessages().ERROR_UNKNOWN_COMMAND()
					.param("command", subcommandName)
			);
		}

		// Throw if player doesn't have permission.
		if (!subcommand.canExecute(executor)) {
			throw new NoPermissionException(executor, null);
		}

		// Execute the subcommand.
		try {
			subcommand.command.execute(label + " " + subcommandName, executor, target, args.size() == 0 ? args : args.subList(1, args.size()));
		} catch (CommandException ex) {
			if (!ex.getLabel().isPresent()) ex.setLabel(label + " " + subcommandName);
			throw ex;
		}
	}

	@Override
	public @NotNull List<String>
	complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		// If there are no args, show the subcommands.
		if (args.size() <= 1) {
			return sortedCompletions(args, this.subcommands.values().stream()
					.filter(subcommand -> !subcommand.hidden)
					.filter(subcommand -> subcommand.canExecute(executor))
					.map(subcommand -> subcommand.name)
			);
		}

		// Check if the player can execute the subcommand.
		Subcommand subcommand = this.subcommands.get(args.get(0).toLowerCase());
		if (subcommand == null || !subcommand.canExecute(executor)) {
			return Collections.emptyList();
		}

		// Pass the arguments to the subcommand executor.
		return subcommand.command.complete(label, executor, target, args.size() == 0 ? args : args.subList(1, args.size()));
	}


	// -------------------------------------------------------------------------------------------------------------
	// Classes:
	// -------------------------------------------------------------------------------------------------------------

	private static class Subcommand {
		public final @NotNull String name;
		public final @NotNull Command command;
		public final boolean hidden;

		public Subcommand(@NotNull String name, @NotNull Command command, boolean hidden) {
			this.name = name;
			this.command = command;
			this.hidden = hidden;
		}

		public boolean canExecute(@NotNull PlayerAdapter executor) {
			if (!(this.command instanceof AbstractCommand)) return true;
			return ((AbstractCommand) this.command).canExecute(executor);
		}

	}

}
