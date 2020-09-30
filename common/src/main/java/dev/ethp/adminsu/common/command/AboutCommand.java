package dev.ethp.adminsu.common.command;

import java.util.Collections;
import java.util.List;

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
 * /su about
 * <p>
 * Print information about admin-su.
 */
public class AboutCommand extends AbstractCommand {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public AboutCommand(PlatformAdapter<?, ?> platform) {
		super(platform, null);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public boolean canExecute(@NotNull PlayerAdapter executor) {
		return executor.hasPermission(Permissions.COMMAND_SU_TOGGLE)
				|| executor.hasPermission(Permissions.COMMAND_SU_CHECK)
				|| executor.hasPermission(Permissions.COMMAND_SU_RELOAD);
	}

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		if (!args.isEmpty()) throw new CommandUsageException(Messages.COMMAND_ABOUT_USAGE);
		final MessageSet i18n = this.platform.getMessages();

		i18n.getMessage(Messages.COMMAND_ABOUT_PLUGIN_HEADER)
				.param("name", this.platform.getPlugin().getIdentifier())
				.param("version", this.platform.getPlugin().getVersion())
				.send(executor);

		i18n.getMessage(Messages.COMMAND_ABOUT_DEVELOPERS)
				.param("developers", String.join(i18n.getMessage(Messages.COMMAND_ABOUT_DEVELOPERS_SEPARATOR).toString(), this.platform.getPlugin().getAuthors()))
				.send(executor);

		i18n.getMessage(Messages.COMMAND_ABOUT_WEBSITE)
				.param("website", this.platform.getPlugin().getWebsite())
				.param("website", "https://github.com/eth-p/bukkit-admin-su") // Just in case.
				.send(executor);

		i18n.getMessage(Messages.COMMAND_ABOUT_FEATURE_HEADER)
				.send(executor);

		// Features.
		Message featureEnabled = i18n.getMessage(Messages.COMMAND_ABOUT_FEATURE_ENABLED);
		Message featureDisabled = i18n.getMessage(Messages.COMMAND_ABOUT_FEATURE_DISABLED);
		Message featureUnsupported = i18n.getMessage(Messages.COMMAND_ABOUT_FEATURE_UNSUPPORTED);

		final String pluginId = this.platform.getPlugin().getIdentifier();
		for (ExtensionInstance instance : this.platform.getApi().getExtensionManager()) {
			Message message = (instance.isSupported() ? (instance.isEnabled() ? featureEnabled : featureDisabled) : featureUnsupported);
			String name = instance.getCanonicalName();
			message.param("feature", name.startsWith(pluginId) ? name.substring(pluginId.length() + 1) : pluginId)
					.send(executor);
		}

		// Warnings.
		boolean usingPermissionsService = this.platform.getApi().getExtensionManager().stream()
				.filter(ExtensionInstance::isSupported)
				.filter(ExtensionInstance::isEnabled)
				.anyMatch(extension -> extension.provides(SuPermissionService.class));

		if (!usingPermissionsService) {
			i18n.getMessage(Messages.COMMAND_ABOUT_WARNING_OPMODE)
					.send(executor);
		}
	}

	@Override
	public @NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		return Collections.emptyList();
	}

}
