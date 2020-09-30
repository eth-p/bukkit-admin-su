package dev.ethp.adminsu.base.extension;

import dev.ethp.adminsu.api.extension.Extension;
import dev.ethp.adminsu.api.extension.ExtensionException;
import dev.ethp.adminsu.api.extension.ExtensionUnsupportedException;
import dev.ethp.adminsu.api.platform.SuApi;
import dev.ethp.adminsu.api.service.Service;

import dev.ethp.adminsu.base.platform.PlatformAdapter;

import org.jetbrains.annotations.NotNull;


/**
 * An "instance" of an extension that isn't supported.
 * This exists to be a placeholder, and will throw if anything is done with it.
 *
 * @param <API>            The admin-su API.
 * @param <Plugin> The base plugin type for the platform.
 */
final class UnsupportedExtensionInstance<API extends SuApi<Plugin, ?>, Plugin>
		extends AbstractExtensionInstance<Plugin> {

	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new unsupported extension instance.
	 *
	 * @param platform The platform adapter.
	 * @param plugin   The registering plugin.
	 * @param name     The extension name.
	 */
	public UnsupportedExtensionInstance(
			@NotNull PlatformAdapter<Plugin, ?> platform,
			@NotNull Plugin plugin,
			@NotNull String name) {
		super(platform, plugin, name);
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a failure exception for the unsupported extensions.
	 *
	 * @return The failure exception.
	 */
	protected final ExtensionUnsupportedException fail() {
		return new ExtensionUnsupportedException(this, "Extension " + this.getCanonicalName() + " is not supported.");
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -----------------------------------------------------------------------------------------------------------------
	
	@Override
	public boolean isSupported() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean provides(Class<? extends Service> service) {
		return false;
	}

	@Override
	public void enable() throws ExtensionException {
		throw fail();
	}

	@Override
	public void disable() throws ExtensionException {
		throw fail();
	}

}
