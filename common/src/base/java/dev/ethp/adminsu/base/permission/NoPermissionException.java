package dev.ethp.adminsu.base.permission;

import java.util.Optional;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageKey;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An {@link Exception exception} indicating that a player is lacking a permission.
 */
public class NoPermissionException extends RuntimeException {

	private final @Nullable PermissionKey permission;
	private final @Nullable Message permissionMessage;
	private final @NotNull PlayerAdapter player;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new NoPermissionException.
	 *
	 * @param player     The player lacking the permission.
	 * @param permission The permission.
	 */
	public NoPermissionException(@NotNull PlayerAdapter player, @Nullable PermissionKey permission) {
		super(permission == null ?
				("Player " + player.getName() + " does not have permission.") :
				("Player " + player.getName() + " is missing permission " + permission.getPermission())
		);

		this.player = player;
		this.permission = permission;
		this.permissionMessage = null;
	}

	/**
	 * Creates a new NoPermissionException.
	 *
	 * @param player     The player lacking the permission.
	 * @param permission The permission.
	 * @param message    The localized message to show to the player.
	 */
	public NoPermissionException(@NotNull PlayerAdapter player, @Nullable PermissionKey permission, @NotNull Message message) {
		super(message.chain()
				.param("player", player.getName())
				.param("permission", Optional.ofNullable(permission).map(PermissionKey::getPermission))
				.toString()
		);

		this.player = player;
		this.permission = permission;
		this.permissionMessage = message;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the permission message.
	 *
	 * @return The permission message.
	 */
	public Optional<Message> getPermissionMessage() {
		return Optional.ofNullable(this.permissionMessage);
	}

	/**
	 * Gets the player missing the permission.
	 *
	 * @return The player.
	 */
	public @NotNull PlayerAdapter getPlayer() {
		return this.player;
	}

	/**
	 * Gets the lacking permission.
	 *
	 * @return The permission.
	 */
	public Optional<PermissionKey> getPermission() {
		return Optional.ofNullable(this.permission);
	}

}
