package dev.ethp.adminsu.common.command;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.ethp.adminsu.api.SuPlayer;
import dev.ethp.adminsu.api.extension.ExtensionInstance;
import dev.ethp.adminsu.api.platform.SuApi;
import dev.ethp.adminsu.api.service.SuPermissionService;
import dev.ethp.adminsu.base.command.AbstractCommand;
import dev.ethp.adminsu.base.command.CommandException;
import dev.ethp.adminsu.base.command.CommandUsageException;
import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageSet;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;
import dev.ethp.adminsu.base.platform.Unwrap;

import dev.ethp.adminsu.common.constants.Messages;
import dev.ethp.adminsu.common.constants.Permissions;

import org.jetbrains.annotations.NotNull;

/**
 * /su check [player]
 * <p>
 * Checks if another player is in su.
 */
public class CheckCommand extends AbstractCommand {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public CheckCommand(PlatformAdapter<?, ?> platform) {
		super(platform, Permissions.COMMAND_SU_CHECK);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void execute(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		if (args.size() != 1) throw new CommandUsageException(Messages.COMMAND_CHECK_USAGE);
		final MessageSet i18n = this.platform.getMessages();

		// Find the players mentioned in the query.
		String query = args.get(0);
		List<PlayerAdapter> players = sync(() -> this.platform.findPlayers(query));

		if (players.size() == 0) {
			throw new CommandException(
					i18n.getMessage(Messages.ERROR_PLAYER_NOT_FOUND)
							.param("query", query)
			);
		}

		// Find a single player from the list of players returned.
		System.out.println(players);
		Optional<PlayerAdapter> targetOpt = sync(() -> this.platform.findPlayer(players, query));
		if (!targetOpt.isPresent()) {
			throw new CommandException(
					i18n.getMessage(Messages.ERROR_PLAYER_MULTIPLE_FOUND)
							.param("query", query)
			);
		}

		// Check the player can be admin.
		PlayerAdapter targetPlayer = targetOpt.get();
		if (!targetPlayer.hasPermission(Permissions.COMMAND_SU_TOGGLE)) {
			throw new CommandException(
					i18n.getMessage(Messages.COMMAND_CHECK_IMPOSSIBLE)
							.param("player", target.getName())
							.param("player_nickname", target.getDisplayName())
			);
		}

		// Check the player.
		(i18n.getMessage(this.platform.getPlayerSu(targetPlayer).isSu() ? Messages.COMMAND_CHECK_ENABLED : Messages.COMMAND_CHECK_DISABLED))
				.param("player", target.getName())
				.param("player_nickname", target.getDisplayName())
				.send(executor);
	}

	@Override
	public @NotNull List<String> complete(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target, @NotNull List<String> args) {
		if (args.size() != 1) return Collections.emptyList();
		return sortedCompletionsInsensitive(args,
				this.platform.getPlayers().stream()
						.filter(player -> player.hasPermission(Permissions.COMMAND_SU_TOGGLE))
						.map(PlayerAdapter::getName)
		);
	}

}
