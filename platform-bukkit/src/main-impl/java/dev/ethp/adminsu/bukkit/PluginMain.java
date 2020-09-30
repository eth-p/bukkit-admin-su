package dev.ethp.adminsu.bukkit;

import java.util.Arrays;
import java.util.Objects;

import dev.ethp.adminsu.base.command.AbstractCommand;
import dev.ethp.adminsu.base.i18n.CommonMessages;
import dev.ethp.adminsu.base.i18n.MessageSet;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import dev.ethp.adminsu.bukkit.api.extension.ExtensionManager;
import dev.ethp.adminsu.bukkit.extension.luckperms.ExtLuckperms;
import dev.ethp.adminsu.bukkit.extension.placeholderapi.ExtPlaceholderApi;
import dev.ethp.adminsu.bukkit.impl.BukkitCommandDispatcher;
import dev.ethp.adminsu.bukkit.impl.ImplApi;
import dev.ethp.adminsu.bukkit.impl.ImplMessageSet;
import dev.ethp.adminsu.common.command.SuCommand;
import dev.ethp.adminsu.common.constants.Messages;
import dev.ethp.adminsu.common.i18n.DefaultCommonMessages;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin main class for Bukkit/Spigot.
 */
public class PluginMain extends JavaPlugin {

	private ImplApi api;
	private MessageSet messages;
	private CommonMessages messagesCommon;
	private BukkitCommandDispatcher dispatcher;

	// -------------------------------------------------------------------------------------------------------------
	// Initialization:
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void onLoad() {
		this.api = new ImplApi(this);
		this.api.initialize();

		this.dispatcher = new BukkitCommandDispatcher(this.api.adapter);
	}

	private void reconfigure() {
		ImplMessageSet messages = new ImplMessageSet(this, "messages");

		this.messages = MessageSet.preloaded(messages, Messages.class);
		this.messagesCommon = new DefaultCommonMessages(this.messages);
	}

	private void reinitialize() {
		final ExtensionManager extensions = this.getExtensions();
		extensions.disableAll();

		// Register commands.

		// Unregister local extensions.
		extensions.unregisterAll(this);

		// Register local extensions.
		extensions.tryRegister(this, "LuckPerms", ExtLuckperms::new);
		extensions.tryRegister(this, "PlaceholderAPI", ExtPlaceholderApi::new);

		// Enable extensions.
		extensions.enableAll();
	}

	private void registerCommand(@NotNull PluginCommand command, @NotNull AbstractCommand impl) {
		command.setExecutor((sender, command1, label, args) -> {
			this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
				final PlayerAdapter executor = this.dispatcher.wrap(sender);
				this.dispatcher.dispatchExecute(impl, label, executor, executor, Arrays.asList(args));
			});
			return true;
		});

		command.setTabCompleter((sender, command1, label, args) -> {
			final PlayerAdapter executor = this.dispatcher.wrap(sender);
			return this.dispatcher.dispatchComplete(impl, label, executor, executor, Arrays.asList(args));
		});
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Reloads the plugin configuration.
	 */
	public void reload() {
		this.reconfigure();
	}

	/**
	 * Gets the localized plugin {@link MessageSet message set}.
	 *
	 * @return The localization messages.
	 */
	public final MessageSet getMessages() {
		return this.messages;
	}

	/**
	 * Gets common messages from the localized plugin messages.
	 *
	 * @return Common messages.
	 */
	public final CommonMessages getCommonMessages() {
		return this.messagesCommon;
	}

	/**
	 * Gets the admin-su extension manager.
	 *
	 * @return The extension manager.
	 */
	public final ExtensionManager getExtensions() {
		return this.api.extenions;
	}

	/**
	 * Gets the admin-su API implementation.
	 *
	 * @return The API implementation.
	 */
	public final ImplApi getApi() {
		return this.api;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: JavaPlugin
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void onEnable() {
		reinitialize();
		reconfigure();
		
		// Commands.
		this.registerCommand(Objects.requireNonNull(this.getCommand("adminsu")), new SuCommand(this.api.adapter));
	}

	@Override
	public void onDisable() {
		this.getExtensions().disableAll();
	}


}
