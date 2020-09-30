package dev.ethp.adminsu.api.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import dev.ethp.adminsu.api.SuStatus;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link Service admin-su service} that allows the player's su status to be fetched from a remote source.
 *
 * @since 2.0.0
 */
public interface SuProviderService extends Service {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Asynchronously loads the player's su status.
	 *
	 * @param player The player whose status should be loaded.
	 * @return The player's su status, or empty if they don't have one.
	 */
	@NotNull CompletableFuture<Optional<SuStatus>> loadPlayerSu(@NotNull UUID player);

	/**
	 * Asynchronously saves the player's su status.
	 *
	 * @param player The player whose status should be saved.
	 * @param status The status to save.
	 * @return A completable future for the task.
	 */
	@NotNull CompletableFuture<Void> savePlayerSu(@NotNull UUID player, @NotNull SuStatus status);

}
