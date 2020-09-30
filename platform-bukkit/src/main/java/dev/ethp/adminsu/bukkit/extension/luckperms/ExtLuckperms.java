package dev.ethp.adminsu.bukkit.extension.luckperms;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import dev.ethp.adminsu.api.SuStatus;
import dev.ethp.adminsu.api.extension.Extension;
import dev.ethp.adminsu.api.service.SuPermissionService;

import dev.ethp.adminsu.bukkit.PluginMain;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An admin-su {@link Extension extension} that provides a LuckPerms context when a player is in su mode.
 *
 * <ul>
 *     <li><code>su=true</code></li>
 *     <li><code></code> (nothing when not in su)</li>
 * </ul>
 */
public final class ExtLuckperms implements Extension, SuPermissionService {

	private final List<ContextCalculator<Player>> calculators = new ArrayList<>();
	private final PluginMain adminsu;

	private ContextManager manager;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public ExtLuckperms(@NotNull PluginMain adminsu) {
		this.adminsu = adminsu;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	private void register(Supplier<ContextCalculator<Player>> calculator) {
		ContextCalculator<Player> calculatorInstance = calculator.get();

		calculators.add(calculatorInstance);
		manager.registerCalculator(calculatorInstance);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Extension
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void enable() {
		LuckPerms luckPerms = this.adminsu.getServer().getServicesManager().load(LuckPerms.class);
		if (luckPerms == null) throw new IllegalStateException("LuckPerms API not loaded.");
		this.manager = luckPerms.getContextManager();

		// Register.
		register(SuContext::new);
	}

	@Override
	public void disable() throws NoClassDefFoundError {
		// Unregister all context calculators.
		if (manager != null) {
			for (ContextCalculator<Player> calculator : calculators) {
				manager.unregisterCalculator(calculator);
			}

			calculators.clear();
		}
	}
	
	
	// -------------------------------------------------------------------------------------------------------------
	// Implementation: SuPermissionService
	// -------------------------------------------------------------------------------------------------------------
	
	@Override
	public @NotNull CompletableFuture<Void> updatePermissions(@NotNull UUID player, @NotNull SuStatus status) {
		// Do nothing.
		// LuckPerms handles this independently with dynamically calculated state information.
		return CompletableFuture.completedFuture(null);
	}
	
}
