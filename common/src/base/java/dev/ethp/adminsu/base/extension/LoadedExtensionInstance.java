package dev.ethp.adminsu.base.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;

import javax.imageio.spi.ServiceRegistry;

import dev.ethp.adminsu.api.extension.Extension;
import dev.ethp.adminsu.api.extension.ExtensionException;
import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.extension.ExtensionUnsupportedException;
import dev.ethp.adminsu.api.platform.SuApi;
import dev.ethp.adminsu.api.service.Service;

import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PluginAdapter;
import dev.ethp.adminsu.base.platform.Unwrap;

import org.jetbrains.annotations.NotNull;


/**
 * An instance of a loaded admin-su extension.
 *
 * @param <API>    The admin-su API.
 * @param <Plugin> The base plugin type for the platform.
 */
final class LoadedExtensionInstance<API extends SuApi<Plugin, ?>, Plugin>
		extends AbstractExtensionInstance<Plugin>
		implements ExtensionInstanceWithRegistry, Unwrap<Extension> {

	static private final boolean LOG_TO_PLUGIN = true;
	static private final boolean LOG_TO_ADMINSU = true;

	private final PlatformAdapter<Plugin, ?> platform;
	private final AbstractExtensionRegistry registry;
	private final Extension instance;

	private boolean enabled;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new loaded extension instance.
	 *
	 * @param platform The platform adapter.
	 * @param plugin   The registering plugin.
	 * @param name     The extension name.
	 */
	public LoadedExtensionInstance(
			@NotNull PlatformAdapter<Plugin, ?> platform,
			@NotNull Plugin plugin,
			@NotNull String name,
			@NotNull Extension instance) {
		super(platform, plugin, name);
		this.instance = instance;
		this.platform = platform;
		this.enabled = false;
		this.registry = new AbstractExtensionRegistry() {
		};
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Static:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Logs an error.
	 *
	 * @param ex The extension exception.
	 */
	@SuppressWarnings("unchecked")
	static private <PlatformPlugin> void logError(ExtensionException ex) {
		LoadedExtensionInstance<?, PlatformPlugin> ext = (LoadedExtensionInstance<?, PlatformPlugin>) ex.getExtension();
		assert ext != null;

		// Log to admin-su logger.
		if (LOG_TO_ADMINSU) {
			ext.platform.getPlugin().getLogger().log(
					Level.SEVERE,
					ex.getMessage(),
					ex.getCause()
			);
		}

		// Log to registering plugin logger.
		PluginAdapter plugin = ext.platform.plugin(ext.getPlugin());
		if (LOG_TO_PLUGIN && !(LOG_TO_ADMINSU && plugin.getLogger() != ext.platform.getPlugin().getLogger())) {
			plugin.getLogger().log(
					Level.SEVERE,
					"[admin-su] " + ex.getMessage(),
					ex.getCause()
			);
		}
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: ExtensionInstance
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public synchronized boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean provides(Class<? extends Service> service) {
		return !this.getRegistry().getService(service).isEmpty();
	}

	@Override
	public synchronized void enable() throws ExtensionException {
		if (this.enabled) {
			throw new ExtensionException(this, "Extension " + this.getCanonicalName() + " is already enabled.");
		}

		try {
			this.instance.enable();
			this.enabled = true;
		} catch (Throwable t) {
			ExtensionException ex = new ExtensionException(this, "Failed to enable extension " + this.getCanonicalName());
			logError(ex);
			throw ex;
		}
	}

	@Override
	public synchronized void disable() throws ExtensionException {
		if (!this.enabled) {
			throw new ExtensionException(this, "Extension " + this.getCanonicalName() + " is already disabled.");
		}

		try {
			this.enabled = false;
			this.instance.disable();
		} catch (Throwable t) {
			ExtensionException ex = new ExtensionException(this, "Failed to enable extension " + this.getCanonicalName());
			logError(ex);
			throw ex;
		}
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: ExtensionInstanceWithRegistry
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the extension registry of the instance.
	 *
	 * @return The extension registry.
	 */
	public @NotNull AbstractExtensionRegistry getRegistry() {
		return this.registry;
	}

	@Override
	public @NotNull <T extends Service> Collection<@NotNull T> getService(Class<T> type) {
		return this.registry.getService(type);
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: Unwrap
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull Extension unwrap() throws ExtensionUnsupportedException {
		return this.instance;
	}
}
