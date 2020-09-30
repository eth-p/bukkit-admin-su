package dev.ethp.adminsu.base.platform;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

/**
 * A wrapper around the Plugin object on a platform.
 */
public interface PluginAdapter {

	// -------------------------------------------------------------------------------------------------------------
	// Getters:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the plugin identifier.
	 *
	 * @return The plugin identifier.
	 */
	@NotNull String getIdentifier();

	/**
	 * Gets the plugin version.
	 *
	 * @return The plugin version.
	 */
	@NotNull String getVersion();

	/**
	 * Gets the plugin website.
	 *
	 * @return The plugin website.
	 */
	Optional<String> getWebsite();

	/**
	 * Gets the plugin authors.
	 *
	 * @return A list of plugin authors.
	 */
	@NotNull List<@NotNull String> getAuthors();

	/**
	 * Gets the plugin's logger.
	 *
	 * @return The plugin logger.
	 */
	@NotNull Logger getLogger();

}
