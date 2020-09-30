package dev.ethp.adminsu.common.command;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import dev.ethp.adminsu.api.SuPlayer;
import dev.ethp.adminsu.base.command.AbstractCommand;
import dev.ethp.adminsu.base.command.CommandRequiresPlayerException;
import dev.ethp.adminsu.base.command.CommandUsageException;
import dev.ethp.adminsu.base.i18n.MessageSet;
import dev.ethp.adminsu.base.permission.NoPermissionException;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import dev.ethp.adminsu.common.constants.Messages;
import dev.ethp.adminsu.common.constants.Permissions;

import org.jetbrains.annotations.NotNull;
import static dev.ethp.adminsu.common.constants.Permissions.COMMAND_SU_TOGGLE;

/**
 * /su toggle
 * <p>
 * Toggles the player's su mode state.
 */
public class ToggleCommand extends AbstractCommand {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ToggleCommand(PlatformAdapter<?, ?> platform) {
		super(platform, COMMAND_SU_TOGGLE);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Static:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Performs the su mode change.
	 *
	 * @param platform    The platform adapter.
	 * @param target      The player to affect.
	 * @param transformer A function that determines the mode to change into.
	 */
	static void perform(PlatformAdapter<?, ?> platform, PlayerAdapter target, Function<Boolean, Boolean> transformer) {
		if (target.isConsole()) throw new CommandRequiresPlayerException();
		if (!target.hasPermission(COMMAND_SU_TOGGLE)) throw new NoPermissionException(target, COMMAND_SU_TOGGLE);
		final MessageSet i18n = platform.getMessages();

		SuPlayer targetSu = platform.getPlayerSu(target);
		boolean previousState = targetSu.isSu();
		boolean currentState = transformer.apply(previousState);

		// Perform the toggle.
		if (currentState) {
			targetSu.enter();
		} else {
			targetSu.exit();
		}

		// Tell every other player.
		String broadcast = i18n.getMessage(currentState ? Messages.SU_ENABLED_SPY : Messages.SU_DISABLED_SPY )
				.param("player", target.getName())
				.param("player_nickname", target.getDisplayName())
				.toString();

		final UUID targetUUID = target.getUUID();
		for (PlayerAdapter recipient : platform.getPlayers()) {
			if (recipient.getUUID().equals(targetUUID)) continue;
			if (recipient.hasPermission(Permissions.SU_SPY)) {
				recipient.sendMessage(broadcast);
			}
		}

		// Tell the player.
		i18n.getMessage(currentState ? Messages.SU_ENABLED : Messages.SU_DISABLED)
				.param("player", target.getName())
				.param("player_nickname", target.getDisplayName())
				.send(target);
	}

	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		this.assertPlayer(target);
		this.assertPermission(target);

		if (args.size() != 0) throw new CommandUsageException(Messages.COMMAND_TOGGLE_USAGE);
		sync(() -> perform(this.platform, target, b -> !b));
	}

	@Override
	public @NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		return Collections.emptyList();
	}


}
