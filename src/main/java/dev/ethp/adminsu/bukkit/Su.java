package dev.ethp.adminsu.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 * AdminSu API
 * 
 * This allows plugins to enable, disable, or check if the user is in admin mode.
 * If you simply wish to check for admin mode, you can use the Bukkit metadata "su" key:
 * 
 * <code><pre>
 *     Player player = ...;
 *     for (MetadataValue meta : player.getMetadata("su")) {
 *         return meta.asBoolean();
 *     }
 *     return false;
 * </pre></code>
 */
public class Su {

	private Su() {
	}

	/**
	 * Enables admin mode for a player.
	 * @param player The target player.
	 */
	static public void enable(Player player) {
		setAdminMode(player, true);
	}

	/**
	 * Disables admin mode for a player.
	 * @param player The target player.
	 */
	static public void disable(Player player) {
		setAdminMode(player, false);
	}

	/**
	 * Checks if a player is in admin mode.
	 *
	 * @param player The player to check.
	 * @return True if the player is in admin mode.
	 */
	static public boolean check(Player player) {
		for (MetadataValue meta : player.getMetadata(METADATA_KEY)) {
			org.bukkit.plugin.Plugin plugin = meta.getOwningPlugin();
			if (plugin != null && plugin.getClass().getName().equals(Plugin.class.getName())) {
				return meta.asBoolean();
			}
		}
		return false;
	}
	
	static final String METADATA_KEY = "su";
	static void setAdminMode(Player player, boolean enabled) {
		if (Plugin.INSTANCE == null) throw new IllegalStateException("The admin-su plugin is not loaded yet.");
		
		// Set the player's metadata.
		player.setMetadata(METADATA_KEY, new FixedMetadataValue(Plugin.INSTANCE, enabled));
		
		// Set the player's op.
		if (Plugin.INSTANCE.isAdminOp()) {
			player.setOp(enabled);
		}
	}

}
