package dev.ethp.adminsu.api.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import dev.ethp.adminsu.api.SuStatus;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link Service admin-su service} that updates player permissions when they enter or exit su mode.
 *
 * @since 2.0.0
 */
public interface SuPermissionService extends Service {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Updates player permissions according to their su status.
	 *
	 * @param player The player whose permissions should be updated.
	 * @param status The player's su status.
	 * @return A completable future for the task.
	 */
	@NotNull CompletableFuture<Void> updatePermissions(@NotNull UUID player, @NotNull SuStatus status);

}
