package dev.ethp.adminsu.bukkit.impl;

import dev.ethp.adminsu.base.command.CommandDispatcher;
import dev.ethp.adminsu.base.platform.PlatformAdapter;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform implementation for {@link CommandDispatcher}.
 */
public final class BukkitCommandDispatcher extends CommandDispatcher<Player> {

	public BukkitCommandDispatcher(@NotNull PlatformAdapter<?, Player> platform) {
		super(platform);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	public BukkitPlayerAdapterBase<?> wrap(CommandSender sender) {
		return (sender instanceof Player) ?
				(new BukkitPlayerAdapter((Player) sender)) :
				(new BukkitPlayerAdapterBase<>(sender));
	}

}
