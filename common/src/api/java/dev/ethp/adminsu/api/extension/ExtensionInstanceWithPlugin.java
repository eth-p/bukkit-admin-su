package dev.ethp.adminsu.api.extension;

import org.jetbrains.annotations.NotNull;


/**
 * A loaded instance of an {@link Extension extension}.
 * This has an external plugin that registered the extension.
 *
 * @param <Plugin> The base plugin type for the platform.
 * @since 2.0.0
 */
public interface ExtensionInstanceWithPlugin<Plugin> extends ExtensionInstance {

	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the plugin that registered the extension.
	 *
	 * @return The registering plugin.
	 */
	@NotNull Plugin getPlugin();


}
