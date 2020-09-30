package dev.ethp.adminsu.api.extension;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for constructing extensions.
 *
 * @param <Type>   The extension type.
 * @param <Plugin> The plugin registering the extension.
 * @since 2.0.0
 */
public interface ExtensionConstructor<Type extends Extension, Plugin> {

	/**
	 * Attempts to construct an extension instance.
	 *
	 * @param plugin The registering plugin.
	 * @return The extension instance.
	 */
	@NotNull Type construct(@NotNull Plugin plugin);

}
