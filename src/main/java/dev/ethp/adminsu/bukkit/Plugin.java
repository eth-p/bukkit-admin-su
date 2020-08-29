package dev.ethp.adminsu.bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dev.ethp.adminsu.bukkit.hook.SuLuckperms;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

	static Plugin INSTANCE;
	
	private boolean opMode;
	private PluginMessages messages;
	private List<Hook> hooks = new ArrayList<>();
	
	@Override
	public void onLoad() {
		Plugin.INSTANCE = this;
	}

	@Override
	public void onEnable() {
		opMode = true;
		messages = new PluginMessages(this);
		Hook luckperms;
		
		// Commands.
		Objects.requireNonNull(this.getCommand("adminsu")).setExecutor(new AdminsuCommand(this));
		
		// Hooks.
		hooks.add(luckperms = new Hook(this, "LuckPerms", SuLuckperms::new));
		
		// Enable hooks.
		for (Hook hook : hooks) {
			if (hook.isSupported()) {
				hook.enable();
			}
		}
		
		// Check for admin mode.
		if (luckperms.isSupported() && luckperms.isEnabled()) {
			opMode = false;
		}
	}

	@Override
	public void onDisable() {
		// Disable hooks.
		for (Hook hook : hooks) {
			if (hook.isEnabled()) {
				hook.disable();
			}
		}
		
		hooks.clear();
	}

	/**
	 * Returns true if admin mode involves op instead of setting a permission.
	 * @return True if a supported permission plugin are not found.
	 */
	public boolean isAdminOp() {
		return this.opMode;
	}

	/**
	 * Reloads the plugin config.
	 */
	public void reload() {
		this.onDisable();
		this.onEnable();
	}
	
	public final List<Hook> hooks() {
		return Collections.unmodifiableList(this.hooks);
	} 
	
	public final PluginMessages i18n() {
		return this.messages;
	}
	
}
