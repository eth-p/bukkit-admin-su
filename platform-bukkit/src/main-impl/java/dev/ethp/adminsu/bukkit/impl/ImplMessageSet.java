package dev.ethp.adminsu.bukkit.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;
import java.util.logging.Level;

import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageKey;
import dev.ethp.adminsu.base.i18n.MessageSet;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform implementation for {@link MessageSet}.
 */
public final class ImplMessageSet implements MessageSet {

	public final Configuration messages;
	private final String prefix;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new message set.
	 *
	 * @param plugin The owning plugin.
	 * @param name   The set name, without the yaml extension.
	 */
	public ImplMessageSet(Plugin plugin, String name) {
		final File file = new File(plugin.getDataFolder(), name + ".yml");

		// Load the resource and filesystem sets.
		Configuration defaults = loadResource(plugin, file);
		FileConfiguration filesystem = loadFilesystem(plugin, file);

		if (!plugin.getDescription().getVersion().equals(filesystem.getString("__version__"))) {
			if (!file.exists()) {
				install(plugin, file);
			} else {
				update(plugin, file, defaults, filesystem, plugin.getDescription().getVersion());
			}
		}

		// Set the message set.
		filesystem.setDefaults(defaults);
		this.messages = filesystem;
		this.prefix = ChatColor.translateAlternateColorCodes('&', this.getRawMessage("prefix").toString());
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets a raw message from the message set.
	 *
	 * @param key The message key.
	 * @return The message, or a placeholder containing the message key.
	 */
	public @NotNull Message getRawMessage(String key) {
		String value = messages.getString(key);
		return new Message(value == null ? "{{MISSING:" + key + "}}" : value);
	}

	// -------------------------------------------------------------------------------------------------------------
	// Internal:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Loads the message set from the filesystem.
	 *
	 * @return The filesystem message set, or an empty one.
	 */
	static private FileConfiguration loadFilesystem(Plugin plugin, File file) {
		YamlConfiguration messages = new YamlConfiguration();
		if (!file.exists()) {
			return messages;
		}

		try {
			messages.load(file);
		} catch (Throwable t) {
			plugin.getLogger().log(Level.WARNING, "Unable to load message set: " + file.getName(), t);
		}

		return messages;
	}

	/**
	 * Loads the message set from the jar.
	 *
	 * @return The jar message set.
	 */
	static private Configuration loadResource(Plugin plugin, File file) {
		return YamlConfiguration.loadConfiguration(readResource(plugin, file));
	}

	static private Reader readResource(Plugin plugin, File file) {
		return new InputStreamReader(Objects.requireNonNull(plugin.getResource(file.getName())));
	}

	/**
	 * Installs the message set to the plugin's data directory.
	 * This will set the version field to the current plugin version.
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	static private void install(Plugin plugin, File file) {
		try {
			file.getParentFile().mkdirs();

			BufferedReader reader = new BufferedReader(readResource(plugin, file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			char[] buffer = new char[4096];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) != -1) {
				writer.write(buffer, 0, read);
			}

			writer.write("\n\n__version__: \"" + plugin.getDescription().getVersion() + "\"\n");
			writer.close();
			reader.close();
		} catch (Exception ex) {
			plugin.getLogger().log(Level.WARNING, "Unable to save message set: " + file.getName(), ex);
		}
	}

	/**
	 * Updates the message set in the plugin's data directory.
	 * This will set the version field to the current plugin version.
	 */
	static private void update(Plugin plugin, File file, Configuration defaults, FileConfiguration messages, String version) {
		boolean requiresUpdate = false;
		for (String key : defaults.getKeys(true)) {
			if (!messages.contains(key, true)) {
				messages.set(key, defaults.get(key));
				requiresUpdate = true;
			}
		}

		if (requiresUpdate) {
			try {
				messages.set("__version__", version);
				messages.save(file);
			} catch (Exception ex) {
				plugin.getLogger().log(Level.WARNING, "Unable to save message set: " + file.getName(), ex);
			}
		}
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: MessageSet
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets a message from the message set.
	 *
	 * @param key The message key.
	 * @return The message, or a placeholder containing the message key.
	 */
	@Override
	public @NotNull Message getMessage(MessageKey key) {
		String value = messages.getString(key.getMessageKey());
		return new Message(
				value == null ?
						"{{MISSING:" + key + "}}" :
						ChatColor.translateAlternateColorCodes('&', value)
		).param("prefix", this.prefix);
	}

}
