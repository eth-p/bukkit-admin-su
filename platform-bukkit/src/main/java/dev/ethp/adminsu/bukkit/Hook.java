package dev.ethp.adminsu.bukkit;

import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * A wrapper around a plugin hook that hides the dirty details of missing classes and exceptions.
 */
public final class Hook {

	private final Plugin adminsu;
	private boolean enabled;
	private final String hookName;
	private final HookImpl hook;

	/**
	 * Creates a new hook.
	 *
	 * @param adminsu The AdminSu plugin.
	 * @param name    The hook name.
	 * @param hook    The hook supplier.
	 */
	public <T extends Supplier<HookImpl>> Hook(Plugin adminsu, String name, T hook) {
		this.hookName = name;
		this.enabled = false;
		this.adminsu = adminsu;

		HookImpl impl = null;
		try {
			impl = hook.get();
		} catch (Throwable t) {
			this.adminsu.getLogger().log(Level.INFO, "Not enabling " + this.name() + " support. Could not load plugin.");
		} finally {
			this.hook = impl;
		}
	}

	/**
	 * The name of the hook.
	 *
	 * @return The hook name.
	 */
	public String name() {
		return this.hookName;
	}

	/**
	 * Whether or not the hook is supported.
	 *
	 * @return True if the hook is supported.
	 */
	public boolean isSupported() {
		return this.hook != null;
	}

	/**
	 * Whether or not the hook is enabled.
	 *
	 * @return True if the hook is enabled.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Attempts to enable the hook.
	 */
	public void enable() {
		try {
			this.hook.hook(this.adminsu);
			this.enabled = true;
			this.adminsu.getLogger().log(Level.INFO, "Enabled " + this.name() + " support.");
		} catch (Throwable t) {
			this.adminsu.getLogger().log(Level.WARNING, "Failed to enable support for " + this.name() + ".", t);
		}
	}

	/**
	 * Disables the hook.
	 */
	public void disable() {
		try {
			this.enabled = false;
			this.hook.unhook(this.adminsu);
			this.adminsu.getLogger().log(Level.INFO, "Unregistered " + this.name() + " successfully.");
		} catch (Throwable t) {
			this.adminsu.getLogger().log(Level.WARNING, "Failed to disable support for " + this.name() + ".", t);
		}
	}

}
