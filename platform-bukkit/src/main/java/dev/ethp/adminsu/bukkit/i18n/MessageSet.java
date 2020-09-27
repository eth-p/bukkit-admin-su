package dev.ethp.adminsu.bukkit.i18n;

import java.io.*;
import java.util.Objects;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class for loading message sets.
 */
public final class MessageSet {

	public final Configuration messages;

	/**
	 * Gets a message from the message set.
	 *
	 * @param key The message key.
	 * @return The message, or a placeholder containing the message key.
	 */
	public Message get(String key) {
		String value = messages.getString(key);
		return new Message(
				value == null ?
						"{{MISSING:" + key + "}}" :
						ChatColor.translateAlternateColorCodes('&', value)
		);
	}

	/**
	 * Creates a new message set.
	 *
	 * @param plugin The owning plugin.
	 * @param name   The set name, without the yaml extension.
	 */
	public MessageSet(Plugin plugin, String name) {
		final File file = new File(plugin.getDataFolder(), name + ".yml");

		// Load the resource and filesystem sets.
		Configuration defaults = loadResource(plugin, file);
		FileConfiguration filesystem = loadFilesystem(plugin, file);

		if (!plugin.getDescription().getVersion().equals(filesystem.getString("__version__"))) {
			if (!file.exists()) {
				install(plugin, file);
			} else {
				update(plugin, file, defaults, filesystem);
			}
		}

		// Set the message set.
		filesystem.setDefaults(defaults);
		messages = filesystem;
	}

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
	static private void update(Plugin plugin, File file, Configuration defaults, FileConfiguration messages) {
		for (String key : defaults.getKeys(true)) {
			if (!messages.contains(key, true)) {
				messages.set(key, defaults.get(key));
			}
		}

		try {
			messages.save(file);
		} catch (Exception ex) {
			plugin.getLogger().log(Level.WARNING, "Unable to save message set: " + file.getName(), ex);
		}
	}

}
