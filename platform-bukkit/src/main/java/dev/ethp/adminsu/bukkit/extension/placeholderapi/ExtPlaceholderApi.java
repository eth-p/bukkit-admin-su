package dev.ethp.adminsu.bukkit.extension.placeholderapi;

import dev.ethp.adminsu.api.extension.Extension;

import dev.ethp.adminsu.bukkit.PluginMain;

import org.jetbrains.annotations.NotNull;

/**
 * An admin-su {@link Extension extension} that provides PlaceholderAPI placeholders.
 *
 * <ul>
 *     <li><b><code>%adminsu_capable%</code></b> <code>true/false</code> -- If the player can use /su</li>
 *     <li><b><code>%adminsu_enabled%</code></b> <code>true/false</code> -- If the player is in /su</li>
 *     <li><b><code>%adminsu_enabled_for%</code></b> <code>true/false</code> -- How long the player is in /su</li>
 *     <li><b><code>%adminsu_enabled_for_total_seconds%</code></b> <code>true/false</code> -- How many total seconds the player has been in /su</li>
 * </ul>
 */
public final class ExtPlaceholderApi implements Extension {

	private SuPlaceholder expansion;
	private final PluginMain adminsu;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ExtPlaceholderApi(@NotNull PluginMain adminsu) {
		this.adminsu = adminsu;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------------------------------------------

	protected void preconditions() throws IllegalStateException {
		if (adminsu.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
			throw new IllegalStateException("PlaceholderAPI not loaded.");
		}
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Extension
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void enable() {
		preconditions();

		expansion = new SuPlaceholder(adminsu);
		expansion.register();
	}

	@Override
	public void disable() {
		if (expansion != null) {
			expansion.unregister();
			expansion = null;
		}
	}

}
