package dev.ethp.adminsu.base.extension;

import java.util.Objects;

import dev.ethp.adminsu.api.extension.ExtensionInstanceWithPlugin;

import dev.ethp.adminsu.base.platform.PlatformAdapter;

import org.jetbrains.annotations.NotNull;


/**
 * An abstract implementation of {@link ExtensionInstanceWithPlugin}.
 *
 * @param <Plugin> The base plugin type for the platform.
 * @since 2.0.0
 */
public abstract class AbstractExtensionInstance<Plugin> implements ExtensionInstanceWithPlugin<Plugin> {

	private final @NotNull String namespace;
	private final @NotNull String name;
	private final @NotNull Plugin plugin;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new extension instance.
	 *
	 * @param plugin The registering plugin.
	 * @param name   The extension name.
	 */
	public AbstractExtensionInstance(
			@NotNull PlatformAdapter<Plugin, ?> platform,
			@NotNull Plugin plugin,
			@NotNull String name) {
		this.plugin = plugin;
		this.namespace = platform.plugin(plugin).getIdentifier();
		this.name = name;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public final @NotNull String getName() {
		return this.name;
	}

	@Override
	public final @NotNull String getNamespace() {
		return this.namespace;
	}

	@Override
	public final @NotNull String getCanonicalName() {
		return ExtensionInstanceWithPlugin.super.getCanonicalName();
	}

	@Override
	public final @NotNull Plugin getPlugin() {
		return this.plugin;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: Object
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractExtensionInstance)) return false;
		AbstractExtensionInstance<?> that = (AbstractExtensionInstance<?>) o;
		return that.getNamespace().equals(this.getNamespace());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(this.getNamespace());
	}

}
