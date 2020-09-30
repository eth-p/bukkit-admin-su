package dev.ethp.adminsu.bukkit.impl;

import dev.ethp.adminsu.base.platform.PlatformAdapter;

import dev.ethp.adminsu.bukkit.api.extension.ExtensionManager;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


/**
 * Bukkit platform implementation for {@link ExtensionManager}.
 */
public final class ImplExtensionManager extends ExtensionManager {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ImplExtensionManager(@NotNull PlatformAdapter<Plugin, ?> platform) {
		super(platform);
	}
	
}
