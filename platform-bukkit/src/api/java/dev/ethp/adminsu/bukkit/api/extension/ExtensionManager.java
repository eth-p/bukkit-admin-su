package dev.ethp.adminsu.bukkit.api.extension;

import dev.ethp.adminsu.base.extension.AbstractExtensionManager;
import dev.ethp.adminsu.base.platform.PlatformAdapter;

import dev.ethp.adminsu.bukkit.api.AdminSuBukkit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link dev.ethp.adminsu.api.extension.ExtensionManager extension manager} for the Bukkit version of admin-su.
 */
public abstract class ExtensionManager
		extends AbstractExtensionManager<AdminSuBukkit, Plugin, Player>
		implements dev.ethp.adminsu.api.extension.ExtensionManager<Plugin> {

	public ExtensionManager(@NotNull PlatformAdapter<Plugin, ?> platform) {
		super(platform);
	}

}
