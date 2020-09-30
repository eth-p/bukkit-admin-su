package dev.ethp.adminsu.bukkit.extension.placeholderapi;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import dev.ethp.adminsu.bukkit.api.AdminSuBukkit;
import dev.ethp.adminsu.bukkit.PluginMain;
import dev.ethp.adminsu.common.constants.Permissions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A PlaceholderAPI expansion for admin-su.
 */
public final class SuPlaceholder extends PlaceholderExpansion {
	private final PluginMain adminsu;

	public SuPlaceholder(PluginMain adminsu) {
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
	public @NotNull String getIdentifier() {
		return "adminsu";
	}

	@Override
	public @NotNull String getAuthor() {
		return this.adminsu.getDescription().getAuthors().toString();
	}

	@Override
	public @NotNull String getVersion() {
		return this.adminsu.getDescription().getVersion();
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, String identifier) {
		boolean suCapable = player.hasPermission(Permissions.COMMAND_SU_TOGGLE.getPermission());
		boolean suEnabled = AdminSuBukkit.player(player).isSu();

		if (identifier.equals("capable")) {
			return String.valueOf(suCapable);
		}

		if (identifier.equals("enabled")) {
			return String.valueOf(suEnabled);
		}

		if (identifier.equals("enabled_for_total_seconds")) {
			Optional<Date> since = AdminSuBukkit.player(player).getStatus().since();
			if (!since.isPresent()) return "";

			Date now = new Date();
			return String.valueOf(ChronoUnit.SECONDS.between(since.get().toInstant(), now.toInstant()));
		}

		if (identifier.equals("enabled_for")) {
			Optional<Date> since = AdminSuBukkit.player(player).getStatus().since();
			if (!since.isPresent()) return "";

			Date now = new Date();

			// Calculate a time string.
			long seconds = ChronoUnit.SECONDS.between(since.get().toInstant(), now.toInstant());
			long minutes = seconds / 60;
			long hours = minutes / 60;
			long days = hours / 24;

			List<String> components = new ArrayList<>(4);
			if (days > 0) components.add(String.valueOf(days));
			if (hours > 0) components.add(String.valueOf(hours % 24));
			if (minutes > 0) components.add(String.valueOf(minutes % 60));
			components.add(String.valueOf(seconds % 60));
			
			final int componentCount = components.size();
			for (int i = 1; i < componentCount; i++) {
				String component = components.get(i);
				if (component.length() < 2) components.set(i, "0" + components.get(i));
			}
			
			return String.join(":", components);
		}

		return null;
	}
}
