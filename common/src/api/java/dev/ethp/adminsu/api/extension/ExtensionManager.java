package dev.ethp.adminsu.api.extension;

import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import org.jetbrains.annotations.NotNull;

/**
 * A container for managing and loading admin-su {@link Extension extensions}.
 *
 * @param <Plugin> The base plugin type for the platform.
 * @since 2.0.0
 */
public interface ExtensionManager<Plugin> extends Iterable<ExtensionInstance> {

	// -------------------------------------------------------------------------------------------------------------
	// Registration:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Registers a new extension.
	 *
	 * @param plugin            The plugin registering the extension.
	 * @param name              The extension name.
	 * @param constructor       A constructor for the extension with its first parameter being the registering plugin type.
	 * @param <ExtensionPlugin> The plugin that is registering the extension.
	 * @param <Constructor>     A functional interface that constructs the extension object.
	 * @return The extension instance.
	 * @throws ExtensionException If the extension could not be registered.
	 */
	<ExtensionPlugin extends Plugin, Constructor extends ExtensionConstructor<? extends Extension, ExtensionPlugin>>
	@NotNull ExtensionInstance register(@NotNull ExtensionPlugin plugin, @NotNull String name, @NotNull Constructor constructor);

	/**
	 * Unregisters an extension.
	 *
	 * @param extension The extension to unregister.
	 */
	void unregister(@NotNull ExtensionInstance extension);

	/**
	 * Unregisters all extension from a plugin.
	 *
	 * @param plugin The plugin to unregister.
	 */
	void unregisterAll(@NotNull Plugin plugin);


	// -------------------------------------------------------------------------------------------------------------
	// Enabling:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Attempts to enable all extensions according to a predicate.
	 * Unsupported extensions will be skipped.
	 *
	 * @param predicate The predicate.
	 */
	void enableAll(Function<ExtensionInstance, Boolean> predicate);

	/**
	 * Attempts to enable all supported extensions.
	 */
	default void enableAll() {
		this.enableAll(extension -> true);
	}

	/**
	 * Attempts to disable all extensions according to a predicate.
	 * Unsupported extensions will be skipped.
	 *
	 * @param predicate The predicate.
	 */
	void disableAll(Function<ExtensionInstance, Boolean> predicate);

	/**
	 * Attempts to disable all supported extensions.
	 */
	default void disableAll() {
		this.disableAll(extension -> true);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Collection:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets all the registered extensions in a stream.
	 *
	 * @return The extensions.
	 */
	default Stream<ExtensionInstance> stream() {
		return StreamSupport.stream(this.spliterator(), false);
	}

}
