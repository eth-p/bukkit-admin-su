package dev.ethp.adminsu.common.command;

import java.util.Collections;
import java.util.List;

import dev.ethp.adminsu.base.command.AbstractCommand;
import dev.ethp.adminsu.base.command.CommandUsageException;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import dev.ethp.adminsu.common.constants.Messages;
import dev.ethp.adminsu.common.constants.Permissions;

import org.jetbrains.annotations.NotNull;
import static dev.ethp.adminsu.common.command.ToggleCommand.perform;

/**
 * /su enable
 * <p>
 * Enables the player's su mode.
 */
public class EnableCommand extends AbstractCommand {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public EnableCommand(PlatformAdapter<?, ?> platform) {
		super(platform, Permissions.COMMAND_SU_TOGGLE);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		this.assertPlayer(target);
		this.assertPermission(target);
		
		if (args.size() != 0) throw new CommandUsageException(Messages.COMMAND_ENABLE_USAGE);
		sync(() -> perform(this.platform, target, b -> true));
	}

	@Override
	public @NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		return Collections.emptyList();
	}

}
