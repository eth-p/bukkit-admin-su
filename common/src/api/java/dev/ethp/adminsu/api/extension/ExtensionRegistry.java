package dev.ethp.adminsu.api.extension;

import dev.ethp.adminsu.api.service.Service;

import org.jetbrains.annotations.NotNull;

/**
 * The service registry for an extension.
 *
 * @since 2.0.0
 */
public interface ExtensionRegistry {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Registers a service for admin-su.
	 *
	 * @param service The service to register.
	 * @since 2.0.0
	 */
	void register(@NotNull Service service);
	
}
