package dev.ethp.adminsu.base.extension;

import java.util.Collection;

import dev.ethp.adminsu.api.extension.Extension;
import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.extension.ExtensionRegistry;
import dev.ethp.adminsu.api.service.Service;

import org.jetbrains.annotations.NotNull;


/**
 * A loaded instance of an {@link Extension extension}.
 * This has an extension registry for services.
 */
public interface ExtensionInstanceWithRegistry extends ExtensionInstance {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the extension's registry.
	 *
	 * @return The extension registry.
	 */
	@NotNull ExtensionRegistry getRegistry();

	/**
	 * Gets an iterator of any services being implemented.
	 *
	 * @param type The service type class.
	 * @param <T>  The service type.
	 *              
	 * @return An iterator of implementations of the service.
	 */
	<T extends Service> @NotNull Collection<@NotNull T> getService(Class<T> type);

}
