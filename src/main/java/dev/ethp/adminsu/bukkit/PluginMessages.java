package dev.ethp.adminsu.bukkit;

import dev.ethp.adminsu.bukkit.i18n.Message;
import dev.ethp.adminsu.bukkit.i18n.MessageSet;

public class PluginMessages {
	
	public final Message PREFIX;
	public final Message ERROR_CONSOLE;
	public final Message ERROR_PERMISSION;
	public final Message ERROR_PLAYER_NOT_FOUND;
	public final Message ERROR_PLAYER_MULTIPLE_FOUND;
	public final Message ERROR_UNKNOWN_COMMAND;
	public final Message COMMAND_RELOAD_SUCCESS;
	public final Message COMMAND_ENABLE_USAGE;
	public final Message COMMAND_DISABLE_USAGE;
	public final Message COMMAND_TOGGLE_USAGE;
	public final Message COMMAND_CHECK_USAGE;
	public final Message COMMAND_ABOUT_FEATURE_ENABLED;
	public final Message COMMAND_ABOUT_FEATURE_DISABLED;
	public final Message COMMAND_ABOUT_WARNING_OPMODE;
	public final Message SU_ENABLED;
	public final Message SU_ENABLED_BROADCAST;
	public final Message SU_DISABLED;
	public final Message SU_DISABLED_BROADCAST;
	public final Message ADMINSU_CHECK_ENABLED;
	public final Message ADMINSU_CHECK_DISABLED;
	public final Message ADMINSU_CHECK_IMPOSSIBLE;

	public PluginMessages(Plugin adminsu) {
		final MessageSet messages = new MessageSet(adminsu, "messages");

		PREFIX = messages.get("prefix");
		
		ERROR_CONSOLE = messages.get("error.console");
		ERROR_PERMISSION = messages.get("error.permission");
		ERROR_UNKNOWN_COMMAND = messages.get("error.unknown_command");
		ERROR_PLAYER_MULTIPLE_FOUND = messages.get("error.player_multiple_found");
		ERROR_PLAYER_NOT_FOUND = messages.get("error.player_not_found");
		COMMAND_RELOAD_SUCCESS = messages.get("command.reload.success").prepend(PREFIX);
		COMMAND_TOGGLE_USAGE = messages.get("command.toggle.usage");
		COMMAND_ENABLE_USAGE = messages.get("command.enable.usage");
		COMMAND_DISABLE_USAGE = messages.get("command.disable.usage");
		COMMAND_CHECK_USAGE = messages.get("command.check.usage");
		COMMAND_ABOUT_FEATURE_ENABLED = messages.get("command.about.feature_enabled");
		COMMAND_ABOUT_FEATURE_DISABLED = messages.get("command.about.feature_disabled");
		COMMAND_ABOUT_WARNING_OPMODE = messages.get("command.about.warning_opmode");
		ADMINSU_CHECK_IMPOSSIBLE = messages.get("command.check.impossible");
		ADMINSU_CHECK_ENABLED = messages.get("command.check.enabled").prepend(PREFIX);
		ADMINSU_CHECK_DISABLED = messages.get("command.check.disabled").prepend(PREFIX);
		SU_ENABLED = messages.get("su.enabled").prepend(PREFIX);
		SU_DISABLED = messages.get("su.disabled").prepend(PREFIX);
		SU_ENABLED_BROADCAST = messages.get("su.enabled_broadcast").prepend(PREFIX);
		SU_DISABLED_BROADCAST = messages.get("su.disabled_broadcast").prepend(PREFIX);
	}

}
