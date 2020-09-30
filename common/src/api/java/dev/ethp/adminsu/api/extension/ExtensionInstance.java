package dev.ethp.adminsu.api.extension;

import dev.ethp.adminsu.api.service.Service;

import org.jetbrains.annotations.NotNull;


/**
 * A loaded instance of an {@link Extension extension}.
 *
 * @since 2.0.0
 */
public interface ExtensionInstance {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the name of the extension.
	 *
	 * @return The extension name.
	 */
	@NotNull String getName();

	/**
	 * Gets the namespace of the extension.
	 * This will likely be the registering plugin's name.
	 *
	 * @return The extension namespace.
	 */
	@NotNull String getNamespace();

	/**
	 * Gets the canonical name of the extension.
	 * This is the namespace and extension name separated by a colon.
	 *
	 * @return The canonical extension name.
	 */
	default @NotNull String getCanonicalName() {
		return this.getNamespace() + ":" + this.getName();
	}

	/**
	 * Checks if the extension is supported.
	 * If the extension could not be loaded when registering, this will be false.
	 *
	 * @return Whether the extension is supported.
	 */
	boolean isSupported();

	/**
	 * Checks if the extension is enabled.
	 *
	 * @return Whether the extension is enabled.
	 */
	boolean isEnabled();

	/**
	 * Attempts to enable the extensions.
	 *
	 * @throws ExtensionException            If the extension could not be enabled.
	 * @throws ExtensionUnsupportedException If the extension is not supported.
	 */
	void enable() throws ExtensionException;

	/**
	 * Attempts to disable the extension.
	 *
	 * @throws ExtensionException            If the extension could not be enabled.
	 * @throws ExtensionUnsupportedException If the extension is not supported.
	 */
	void disable() throws ExtensionException;

	/**
	 * Checks if the extension provides a service to admin-su.
	 *
	 * @param service The service to provide.
	 * @return True if the service is provided.
	 */
	boolean provides(Class<? extends Service> service);

}
