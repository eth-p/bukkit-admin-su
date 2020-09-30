package dev.ethp.adminsu.common.constants;

import dev.ethp.adminsu.base.permission.PermissionKey;

/**
 * All available admin-su permissions.
 */
public enum Permissions implements PermissionKey {

	COMMAND_SU_CHECK("adminsu.command.check"),
	COMMAND_SU_TOGGLE("adminsu.command.toggle"),
	COMMAND_SU_RELOAD("adminsu.command.reload"),
	SU_SPY("adminsu.spy");


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Permission
	// -------------------------------------------------------------------------------------------------------------

	private final String permission;

	Permissions(String permission) {
		this.permission = permission;
	}

	@Override
	public String getPermission() {
		return permission;
	}
	
}
