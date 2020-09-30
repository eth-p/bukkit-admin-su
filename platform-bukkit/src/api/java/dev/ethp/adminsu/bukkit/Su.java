package dev.ethp.adminsu.bukkit;

import dev.ethp.adminsu.bukkit.api.AdminSuBukkit;

import org.bukkit.entity.Player;

/**
 * AdminSu API
 * <p>
 * This allows plugins to enable, disable, or check if the user is in admin mode.
 * If you simply wish to check for admin mode, you can use the Bukkit metadata "su" key:
 *
 * <pre><code>
 *     Player player = ...;
 *     for (MetadataValue meta : player.getMetadata("su")) {
 *         return meta.asBoolean();
 *     }
 *     return false;
 * </code></pre>
 *
 * @since 1.0
 * @deprecated Superseded by {@link AdminSuBukkit AdminSuBukkit}.
 */
@Deprecated
public class Su {

	private Su() {
	}

	/**
	 * Enables admin mode for a player.
	 *
	 * @param player The target player.
	 * @since 1.0
	 * @deprecated Superseded by {@link AdminSuBukkit AdminSuBukkit}.
	 */
	@Deprecated
	static public void enable(Player player) {
		AdminSuBukkit.player(player).enter();
	}

	/**
	 * Disables admin mode for a player.
	 *
	 * @param player The target player.
	 * @since 1.0
	 * @deprecated Superseded by {@link AdminSuBukkit AdminSuBukkit}.
	 */
	@Deprecated
	static public void disable(Player player) {
		AdminSuBukkit.player(player).exit();
	}

	/**
	 * Checks if a player is in admin mode.
	 *
	 * @param player The player to check.
	 * @return True if the player is in admin mode.
	 * @since 1.0
	 * @deprecated Superseded by {@link AdminSuBukkit AdminSuBukkit}.
	 */
	static public boolean check(Player player) {
		return AdminSuBukkit.player(player).isSu();
	}

}
