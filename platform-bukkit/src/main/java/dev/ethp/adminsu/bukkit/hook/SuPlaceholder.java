package dev.ethp.adminsu.bukkit.hook;

import java.util.ArrayList;
import java.util.List;

import dev.ethp.adminsu.bukkit.HookImpl;
import dev.ethp.adminsu.bukkit.Plugin;
import dev.ethp.adminsu.bukkit.Su;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.luckperms.api.context.ContextCalculator;
import org.bukkit.entity.Player;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE;

public class SuPlaceholder implements HookImpl {

	private final List<ContextCalculator<Player>> calculators = new ArrayList<>();
	private SuExpansion expansion;

	public SuPlaceholder() {
	}

	@Override
	public void hook(Plugin adminsu) throws NoClassDefFoundError {
		if (adminsu.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
			throw new IllegalStateException("PlaceholderAPI not loaded.");
		}

		expansion = new SuExpansion(adminsu);
		expansion.register();
	}

	@Override
	public void unhook(Plugin adminsu) throws NoClassDefFoundError {
		// Unregister all context calculators.
		if (expansion != null) {
			expansion.unregister();
			expansion = null;
		}
	}

}

class SuExpansion extends PlaceholderExpansion {
	private final Plugin adminsu;

	public SuExpansion(Plugin adminsu) {
		this.adminsu = adminsu;
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public String getIdentifier() {
		return "adminsu";
	}

	@Override
	public String getAuthor() {
		return this.adminsu.getDescription().getAuthors().toString();
	}

	@Override
	public String getVersion() {
		return this.adminsu.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		boolean suCapable = player.hasPermission(PERMISSION_SU_TOGGLE);
		boolean suEnabled = Su.check(player);

		if (identifier.equals("capable")) {
			return String.valueOf(suCapable);
		}

		if (identifier.equals("enabled")) {
			return String.valueOf(suEnabled);
		}

		if (identifier.equals("text")) {
			return (!suCapable ?
					adminsu.i18n().SU_IMPOSSIBLE_PLACEHOLDER : (suEnabled ?
					adminsu.i18n().SU_ENABLED_PLACEHOLDER :
					adminsu.i18n().SU_DISABLED_PLACEHOLDER)
			).toString();
		}

		return null;
	}
}
