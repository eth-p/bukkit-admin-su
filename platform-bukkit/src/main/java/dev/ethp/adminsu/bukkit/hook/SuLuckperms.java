package dev.ethp.adminsu.bukkit.hook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import dev.ethp.adminsu.bukkit.HookImpl;
import dev.ethp.adminsu.bukkit.Plugin;
import dev.ethp.adminsu.bukkit.Su;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.*;
import org.bukkit.entity.Player;

public class SuLuckperms implements HookImpl {

	private final List<ContextCalculator<Player>> calculators = new ArrayList<>();
	private ContextManager manager;

	public SuLuckperms() {
	}

	private void register(Supplier<ContextCalculator<Player>> calculator) {
		ContextCalculator<Player> calculatorInstance = calculator.get();

		calculators.add(calculatorInstance);
		manager.registerCalculator(calculatorInstance);
	}

	@Override
	public void hook(Plugin adminsu) throws NoClassDefFoundError {
		LuckPerms luckPerms = adminsu.getServer().getServicesManager().load(LuckPerms.class);
		if (luckPerms == null) throw new IllegalStateException("LuckPerms API not loaded.");
		this.manager = luckPerms.getContextManager();
	
		// Register.
		register(SuContext::new);
	}

	@Override
	public void unhook(Plugin adminsu) throws NoClassDefFoundError {
		// Unregister all context calculators.
		if (manager != null) {
			for (ContextCalculator<Player> calculator : calculators) {
				manager.unregisterCalculator(calculator);
			}
			
			calculators.clear();
		}
	}

}

class SuContext implements ContextCalculator<Player> {
	private static final String KEY = "su";

	@Override
	public void calculate(Player target, ContextConsumer consumer) {
		consumer.accept(Su.check(target) ? ImmutableContextSet.of(KEY, "true") : ImmutableContextSet.empty());
	}

	@Override
	public ContextSet estimatePotentialContexts() {
		return ImmutableContextSet.of(KEY, "true");
	}
}
