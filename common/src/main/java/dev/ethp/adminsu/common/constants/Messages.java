package dev.ethp.adminsu.common.constants;

import dev.ethp.adminsu.base.i18n.MessageKey;

/**
 * All available admin-su localization messages.
 */
public enum Messages implements MessageKey {

	ERROR_CONSOLE("error.console"),
	ERROR_PERMISSION("error.permission"),
	ERROR_INTERNAL("error.internal"),
	ERROR_UNKNOWN_COMMAND("error.unknown-command"),
	ERROR_PLAYER_NOT_FOUND("error.player-not-found"),
	ERROR_PLAYER_MULTIPLE_FOUND("error.player-multiple-found"),

	SU_ENABLED("su.enabled"),
	SU_ENABLED_SPY("su.enabled-spy"),
	SU_DISABLED("su.disabled"),
	SU_DISABLED_SPY("su.disabled-spy"),

	COMMAND_TOGGLE_USAGE("command.toggle.usage"),
	COMMAND_ENABLE_USAGE("command.enable.usage"),
	COMMAND_DISABLE_USAGE("command.disable.usage"),
	COMMAND_CHECK_USAGE("command.check.usage"),
	COMMAND_CHECK_ENABLED("command.check.enabled"),
	COMMAND_CHECK_DISABLED("command.check.disabled"),
	COMMAND_CHECK_IMPOSSIBLE("command.check.impossible"),
	COMMAND_RELOAD_USAGE("command.reload.usage"),
	COMMAND_RELOAD_SUCCESS("command.reload.success"),
	COMMAND_RELOAD_ERROR("command.reload.error"),
	COMMAND_ABOUT_USAGE("command.about.usage"),
	COMMAND_ABOUT_DEVELOPERS("command.about.developers"),
	COMMAND_ABOUT_DEVELOPERS_SEPARATOR("command.about.developers.separator"),
	COMMAND_ABOUT_WEBSITE("command.about.website"),
	COMMAND_ABOUT_FEATURE_HEADER("command.about.feature-header"),
	COMMAND_ABOUT_FEATURE_ENABLED("command.about.feature-enabled"),
	COMMAND_ABOUT_FEATURE_DISABLED("command.about.feature-disabled"),
	COMMAND_ABOUT_FEATURE_UNSUPPORTED("command.about.feature-unsupported"),
	COMMAND_ABOUT_WARNING_OPMODE("command.about.warning-opmode"),
	COMMAND_ABOUT_PLUGIN_HEADER("command.about.plugin-header");


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: Permission
	// -------------------------------------------------------------------------------------------------------------

	private final String key;

	Messages(String key) {
		this.key = key;
	}

	@Override
	public String getMessageKey() {
		return key;
	}

}
