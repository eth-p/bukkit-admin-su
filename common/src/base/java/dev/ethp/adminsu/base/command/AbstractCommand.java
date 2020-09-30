package dev.ethp.adminsu.base.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.ethp.adminsu.base.permission.NoPermissionException;
import dev.ethp.adminsu.base.permission.PermissionKey;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An abstract implementation of {@link Command} for admin-su.
 */
public abstract class AbstractCommand implements Command {

	private final @Nullable PermissionKey permission;

	/**
	 * The platform adapter.
	 */
	protected final @NotNull PlatformAdapter<?, ?> platform;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	protected AbstractCommand(@NotNull PlatformAdapter<?, ?> platform, @Nullable PermissionKey permission) {
		this.platform = platform;
		this.permission = permission;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods: Protected
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Asserts that the executor is allowed to run the command.
	 *
	 * @param executor The executor.
	 */
	protected void assertPermission(@NotNull PlayerAdapter executor) {
		if (!canExecute(executor)) throw new NoPermissionException(executor, this.permission);
	}

	/**
	 * Asserts that a player is actually a player.
	 *
	 * @param player The player.
	 */
	protected void assertPlayer(@NotNull PlayerAdapter player) {
		if (player.isConsole()) throw new CommandRequiresPlayerException();
	}

	/**
	 * Gets command completions as a sorted and filtered list.
	 *
	 * @param completions The command completions list.
	 * @param args        The current arguments.
	 * @return The final completion list.
	 */
	protected List<String> sortedCompletions(@NotNull List<String> args, @NotNull Stream<String> completions) {
		Stream<String> stream = completions;

		// Filter by argument.
		if (args.size() > 0) {
			String arg = args.get(args.size() - 1);
			stream = stream.filter(item -> item.startsWith(arg));
		}

		// Return sorted.
		return stream
				.sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Gets command completions as a sorted and filtered (case insensitive) list.
	 *
	 * @param completions The command completions list.
	 * @param args        The current arguments.
	 * @return The final completion list.
	 */
	protected List<String> sortedCompletionsInsensitive(@NotNull List<String> args, @NotNull Stream<String> completions) {
		Stream<String> stream = completions;

		// Filter by argument.
		if (args.size() > 0) {
			final String arg = args.get(args.size() - 1).toLowerCase();
			stream = stream.filter(item -> item.toLowerCase().startsWith(arg));
		}

		// Return sorted.
		return stream
				.sorted()
				.collect(Collectors.toList());
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Checks if a player can execute the command.
	 *
	 * @return True if the player can execute the command.
	 */
	public boolean canExecute(@NotNull PlayerAdapter playerAdapter) {
		return this.permission == null || playerAdapter.hasPermission(this.permission);
	}

	/**
	 * Executes something on the main thread.
	 *
	 * @param <T>      The return type.
	 * @param function The function to execute.
	 * @return The return value.
	 */
	public <T> T sync(Supplier<T> function) {
		try {
			return CompletableFuture.supplyAsync(function, this.platform.getServerExecutor()).get();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Unable to run sync method.", ex);
		} catch (ExecutionException ex) {
			rethrow(ex.getCause());
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Executes something on the main thread.
	 *
	 * @param function The function to execute.
	 */
	public void sync(Runnable function) {
		this.sync((Supplier<Void>) () -> {
			function.run();
			return null;
		});
	}

	/**
	 * Cast a CheckedException as an unchecked one.
	 *
	 * @param throwable to cast
	 * @param <T>       the type of the Throwable
	 * @return this method will never return a Throwable instance, it will just throw it.
	 * @throws T the throwable as an unchecked throwable
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> RuntimeException rethrow(Throwable throwable) throws T {
		throw (T) throwable; // rely on vacuous cast
	}


}
