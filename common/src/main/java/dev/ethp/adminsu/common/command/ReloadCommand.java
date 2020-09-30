package dev.ethp.adminsu.common.command;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.service.SuPermissionService;
import dev.ethp.adminsu.base.command.AbstractCommand;
import dev.ethp.adminsu.base.command.CommandUsageException;
import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageSet;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import dev.ethp.adminsu.common.constants.Messages;
import dev.ethp.adminsu.common.constants.Permissions;

import org.jetbrains.annotations.NotNull;

/**
 * /su reload
 * <p>
 * Reloads admin-su configuration.
 */
public class ReloadCommand extends AbstractCommand {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ReloadCommand(PlatformAdapter<?, ?> platform) {
		super(platform, Permissions.COMMAND_SU_RELOAD);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		if (!args.isEmpty()) throw new CommandUsageException(Messages.COMMAND_RELOAD_USAGE);
		final MessageSet i18n = this.platform.getMessages();

		try {
			this.sync(this.platform::reload);
			i18n.getMessage(Messages.COMMAND_RELOAD_SUCCESS)
					.send(executor);
		} catch (Throwable t) {
			i18n.getMessage(Messages.COMMAND_RELOAD_ERROR)
					.send(executor);
		}

	}

	@Override
	public @NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		return Collections.emptyList();
	}

}
