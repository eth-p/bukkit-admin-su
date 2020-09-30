package dev.ethp.adminsu.base.command;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.permission.NoPermissionException;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * A class for dispatching commands with common error handling.
 *
 * @param <Player> The base player type for the platform.
 */
public class CommandDispatcher<Player> {

	protected final @NotNull PlatformAdapter<?, Player> platform;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public CommandDispatcher(@NotNull PlatformAdapter<?, Player> platform) {
		this.platform = platform;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: CommandDispatch
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Dispatches command execution.
	 *
	 * @param command  The command to execute.
	 * @param label    The command label.
	 * @param executor The player executing the command.
	 * @param target   The player being affected by the command.
	 * @param args     The command arguments.
	 */
	public void dispatchExecute(@NotNull Command command, @NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		// Check permission in advance.
		if (command instanceof AbstractCommand) {
			if (!((AbstractCommand) command).canExecute(executor)) {
				this.platform.getCommonMessages().ERROR_NO_PERMISSION().send(executor);
				return;
			}
		}

		try {
			command.execute(label, executor, target, args);
		} catch (NoPermissionException ex) {
			// Print permission error.
			Message message = ex.getPermissionMessage()
					.orElseGet(() -> this.platform.getCommonMessages().ERROR_NO_PERMISSION());

			message.chain()
					.param("player", executor.getName())
					.param("target", target.getName())
					.param("permission", ex.getPermission())
					.send(executor);

		} catch (CommandUsageException ex) {
			// Print command usage.
			this.platform.getMessages().getMessage(ex.getUsage())
					.param("command", ex.getLabel().orElse(label))
					.send(executor);

		} catch (CommandRequiresPlayerException ex) {
			// Print command usage.
			this.platform.getCommonMessages().ERROR_NO_CONSOLE()
					.param("command", ex.getLabel().orElse(label))
					.send(executor);

		} catch (CommandException ex) {
			// Expected error.
			executor.sendMessage(ex.getMessage());

		} catch (Throwable t) {
			// Unexpected error.
			this.platform.getPlugin().getLogger().log(
					Level.WARNING,
					"Unable to execute command /" + label + " " + String.join(" ", args),
					t
			);

			this.platform.getCommonMessages().ERROR_UNCAUGHT_EXCEPTION()
					.param("command", label)
					.param("args", String.join(" ", args))
					.send(executor);
		}
	}

	/**
	 * Dispatches command completions.
	 *
	 * @param command  The command to generation completions for.
	 * @param label    The command label.
	 * @param executor The player executing the command.
	 * @param target   The player being affected by the command.
	 * @param args     The command arguments.
	 */
	public @NotNull List<@NotNull String>
	dispatchComplete(@NotNull Command command, @NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		// Check permission in advance.
		if (command instanceof AbstractCommand) {
			if (!((AbstractCommand) command).canExecute(executor)) {
				return Collections.emptyList();
			}
		}

		// Return a mapped completable future.
		try {
			return command.complete(label, executor, target, args);
		} catch (CommandException | NoPermissionException ignored) {
			// Expected exception.
			return Collections.emptyList();

		} catch (Throwable t) {
			// Log the error.
			this.platform.getPlugin().getLogger().log(
					Level.WARNING,
					"Unable to generate tab completion for /" + label + " " + String.join(" ", args),
					t
			);

			// Return an error placeholder.
			return Collections.singletonList("<ERROR>");
		}
	}
}
