package dev.ethp.adminsu.bukkit.extension.luckperms;

import dev.ethp.adminsu.bukkit.api.AdminSuBukkit;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link ContextCalculator context calculator} for adding the {@code su=true} LuckPerms context.
 */
class SuContext implements ContextCalculator<Player> {
	private static final String KEY = "su";

	@Override
	public void calculate(@NotNull Player target, ContextConsumer consumer) {
		consumer.accept(AdminSuBukkit.player(target).isSu() ?
				ImmutableContextSet.of(KEY, "true") :
				ImmutableContextSet.empty()
		);
	}

	@Override
	public ContextSet estimatePotentialContexts() {
		return ImmutableContextSet.of(KEY, "true");
	}
}
