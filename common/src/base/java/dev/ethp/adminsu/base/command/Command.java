package dev.ethp.adminsu.base.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import dev.ethp.adminsu.base.permission.NoPermissionException;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a server command.
 */
public interface Command {

	/**
	 * Executes the command.
	 *
	 * @param label    The command label.
	 * @param executor The player executing the command and receiving the command action.
	 * @param target   The player who the command is performed on.
	 * @param args     The command arguments.
	 * @throws NoPermissionException When the player is missing the permission.
	 */
	void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args);

	/**
	 * Provides tab completion for the command.
	 *
	 * @param label    The command label.
	 * @param executor The player executing the command and receiving the command action.
	 * @param target   The player who the command is performed on.
	 * @param args     The command arguments.
	 * @return The tab completions.
	 * @throws NoPermissionException When the player is missing the permission.
	 */
	@NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args);

}
