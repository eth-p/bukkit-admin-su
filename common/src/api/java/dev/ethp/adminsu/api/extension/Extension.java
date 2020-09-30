package dev.ethp.adminsu.api.extension;

import dev.ethp.adminsu.api.service.Service;

/**
 * An extension for admin-su.
 *
 * @since 2.0.0
 */
public interface Extension {

	/**
	 * Called to have the extension register any services or listeners.
	 *
	 * @param registry The registry.
	 */
	default void register(ExtensionRegistry registry) {
		if (this instanceof Service) {
			registry.register((Service) this);
		}
	}

	/**
	 * Enables the extension.
	 */
	void enable();

	/**
	 * Disables the extension.
	 */
	void disable();

}
