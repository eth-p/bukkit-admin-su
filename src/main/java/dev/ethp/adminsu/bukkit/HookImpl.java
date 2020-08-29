package dev.ethp.adminsu.bukkit;

/**
 * An interface for interfacing with external plugins.
 */
public interface HookImpl {

	/**
	 * Enables a hook/interface with an external plugin.
	 * @param adminsu The Adminsu plugin.
	 */
	void hook(Plugin adminsu) throws java.lang.NoClassDefFoundError;

	/**
	 * Disables a hook/interface with an external plugin.
	 * @param adminsu The Adminsu plugin.
	 */
	void unhook(Plugin adminsu) throws java.lang.NoClassDefFoundError;

}
