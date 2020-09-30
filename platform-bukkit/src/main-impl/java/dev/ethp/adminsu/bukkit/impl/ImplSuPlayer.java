package dev.ethp.adminsu.bukkit.impl;

import java.util.Date;
import java.util.UUID;

import dev.ethp.adminsu.api.SuPlayer;
import dev.ethp.adminsu.api.SuStatus;
import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.service.SuPermissionService;
import dev.ethp.adminsu.base.extension.ExtensionInstanceWithRegistry;
import dev.ethp.adminsu.base.platform.Unwrap;

import dev.ethp.adminsu.common.constants.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


/**
 * Bukkit platform implementation for {@link SuPlayer}.
 */
public final class ImplSuPlayer implements SuPlayer, Unwrap<Player> {

	private final Player player;
	private final ImplApi api;

	public final String METADATA_COMPATIBLE = "su";
	public final String METADATA_EXTENDED = "__admin-su__";


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ImplSuPlayer(ImplApi api, Player player) {
		this.api = api;
		this.player = player;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Notifies {@link SuPermissionService} services of a status change.
	 *
	 * @param status The new status.
	 * @return True if a service handled the status change.
	 */
	protected boolean updatePermissions(SuStatus status) {
		final UUID uuid = this.player.getUniqueId();
		boolean updated = false;

		for (ExtensionInstance ext : this.api.extenions) {
			if (!(ext instanceof ExtensionInstanceWithRegistry)) continue;
			for (SuPermissionService service : ((ExtensionInstanceWithRegistry) ext).getService(SuPermissionService.class)) {
				try {
					service.updatePermissions(uuid, status);
				} catch (Throwable t) {
					throw new RuntimeException("Failed to call SuPermissionService in " + ext.getCanonicalName(), t);
				}
				
				// Let's just assume it worked.
				// Giving op to people that figure out how to break an external plugin would be bad, m'kay?
				updated = true;
			}
		}

		return updated;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: SuPlayer
	// -------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("ConstantConditions")
	@Override
	public @NotNull SuStatus getStatus() {
		for (MetadataValue metadata : this.player.getMetadata(METADATA_EXTENDED)) {
			if (metadata.getOwningPlugin() == this.api.plugin) {
				return (SuStatus) metadata.value();
			}
		}

		return new SuStatus(false);
	}

	@Override
	public boolean canSu() {
		return this.player.hasPermission(Permissions.COMMAND_SU_TOGGLE.getPermission());
	}

	@Override
	public void enter() {
		// Set metadata.
		final Plugin plugin = this.api.plugin;
		final SuStatus status = new SuStatus(true, new Date());

		this.player.setMetadata(METADATA_EXTENDED, new FixedMetadataValue(plugin, status));
		this.player.setMetadata(METADATA_COMPATIBLE, new FixedMetadataValue(plugin, status.check()));

		// Notify services.
		boolean updated = this.updatePermissions(status);
		if (!updated) this.player.setOp(status.check());
	}

	@Override
	public void exit() {
		// Set metadata.
		final Plugin plugin = this.api.plugin;
		final SuStatus status = new SuStatus(false);

		this.player.setMetadata(METADATA_EXTENDED, new FixedMetadataValue(plugin, status));
		this.player.setMetadata(METADATA_COMPATIBLE, new FixedMetadataValue(plugin, status.check()));

		// Notify services.
		boolean updated = this.updatePermissions(status);
		if (!updated) this.player.setOp(status.check());
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Unwrap
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public Player unwrap() {
		return this.player;
	}
}
