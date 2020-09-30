package dev.ethp.adminsu.base.extension;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

import dev.ethp.adminsu.api.extension.Extension;
import dev.ethp.adminsu.api.extension.ExtensionConstructor;
import dev.ethp.adminsu.api.extension.ExtensionException;
import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.extension.ExtensionInstanceWithPlugin;
import dev.ethp.adminsu.api.extension.ExtensionManager;
import dev.ethp.adminsu.api.platform.SuApi;

import dev.ethp.adminsu.base.platform.PlatformAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract implementation of {@link ExtensionManager}
 *
 * @param <API>    The admin-su API.
 * @param <Plugin> The base plugin type for the platform.
 * @param <Player> The base player type for the platform.
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractExtensionManager<API extends SuApi<Plugin, Player>, Plugin, Player>
		implements ExtensionManager<Plugin> {

	private final @NotNull PlatformAdapter<Plugin, ?> platform;
	private final @NotNull Map<String, AbstractExtensionInstance<Plugin>> extensions;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new abstract extension loader.
	 *
	 * @param platform The platform abstraction.
	 */
	protected AbstractExtensionManager(@NotNull PlatformAdapter<Plugin, ?> platform) {
		this.platform = platform;
		this.extensions = new LinkedHashMap<>();
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Attempts to register a new extension.
	 * If the extension could not be constructed, this will register a dummy unsupported extension in its place.
	 *
	 * @param plugin            The plugin registering the extension.
	 * @param name              The extension name.
	 * @param constructor       A constructor for the extension with its first parameter being the registering plugin type.
	 * @param <ExtensionPlugin> The plugin that is registering the extension.
	 * @param <Constructor>     A functional interface that constructs the extension object.
	 * @return The registered extension.
	 */
	public <ExtensionPlugin extends Plugin, Constructor extends ExtensionConstructor<? extends Extension, ExtensionPlugin>>
	@NotNull ExtensionInstance tryRegister(@NotNull ExtensionPlugin plugin, @NotNull String name, @NotNull Constructor constructor) {
		AbstractExtensionInstance<Plugin> instance;

		try {
			Extension ext = constructor.construct(plugin);
			instance = new LoadedExtensionInstance<API, Plugin>(
					this.platform,
					plugin,
					name,
					ext
			);

			// Register the services.
			ext.register(((LoadedExtensionInstance<?, ?>) instance).getRegistry());
		} catch (Throwable ex) {
			instance = new UnsupportedExtensionInstance<API, Plugin>(
					this.platform,
					plugin,
					name
			);

			this.platform.getPlugin().getLogger().log(
					Level.INFO,
					"Extension " + instance.getCanonicalName() + " not supported: " + ex.getMessage()
			);
		}

		// Register and return.
		this.extensions.put(instance.getCanonicalName(), instance);
		return instance;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public <ExtensionPlugin extends Plugin, Constructor extends ExtensionConstructor<? extends Extension, ExtensionPlugin>>
	@NotNull ExtensionInstance register(@NotNull ExtensionPlugin plugin, @NotNull String name, @NotNull Constructor constructor) {
		LoadedExtensionInstance<API, Plugin> instance = new LoadedExtensionInstance<>(
				this.platform,
				plugin,
				name,
				constructor.construct(plugin)
		);

		// Register and return.
		instance.unwrap().register(instance.getRegistry());
		this.extensions.put(instance.getCanonicalName(), instance);
		return instance;
	}

	@Override
	public void unregister(@NotNull ExtensionInstance extension) {
		if (extension.isSupported() && extension.isEnabled()) {
			extension.disable();
		}

		// Unregister.
		this.extensions.remove(extension.getCanonicalName());
	}

	@Override
	public void unregisterAll(@NotNull Plugin plugin) {
		List<ExtensionInstance> remove = this.stream()
				.filter(ext -> ext instanceof ExtensionInstanceWithPlugin)
				.filter(ext -> ((ExtensionInstanceWithPlugin<?>) ext).getPlugin() == plugin)
				.collect(Collectors.toList());

		// Perform it after collecting to prevent concurrent modification exception.
		remove.forEach(this::unregister);
	}

	@Override
	public void enableAll(Function<ExtensionInstance, Boolean> predicate) {
		for (ExtensionInstance extension : this.extensions.values()) {
			if (extension.isSupported() && !extension.isEnabled() && predicate.apply(extension)) {
				try {
					extension.enable();
				} catch (ExtensionException ignored) {
					// This is handled by the enable() function in LoadedExtensionInstance.
				}
			}
		}
	}

	@Override
	public void disableAll(Function<ExtensionInstance, Boolean> predicate) {
		for (ExtensionInstance extension : this.extensions.values()) {
			if (extension.isSupported() && extension.isEnabled() && predicate.apply(extension)) {
				try {
					extension.disable();
				} catch (ExtensionException ignored) {
					// This is handled by the disable() function in LoadedExtensionInstance.
				}
			}
		}
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public @NotNull Iterator<ExtensionInstance> iterator() {
		// We can trust a cast to a supertype.
		// No need to worry, Java.
		return (Iterator<ExtensionInstance>) (Iterator<?>) this.extensions.values().iterator();
	}

}
