package dev.ethp.adminsu.common.i18n;

import dev.ethp.adminsu.base.i18n.CommonMessages;
import dev.ethp.adminsu.base.i18n.Message;
import dev.ethp.adminsu.base.i18n.MessageSet;

import dev.ethp.adminsu.common.constants.Messages;

import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link CommonMessages} that uses the localized plugin messages.
 */
public class DefaultCommonMessages implements CommonMessages {

	protected final MessageSet messages;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new instance.
	 *
	 * @param messages The localized messages.
	 */
	public DefaultCommonMessages(MessageSet messages) {
		this.messages = messages;
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: CommonMessages
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public @NotNull Message ERROR_NO_PERMISSION() {
		return this.messages.getMessage(Messages.ERROR_PERMISSION);
	}

	@Override
	public @NotNull Message ERROR_NO_CONSOLE() {
		return this.messages.getMessage(Messages.ERROR_CONSOLE);
	}
	
	@Override
	public @NotNull Message ERROR_UNKNOWN_COMMAND() {
		return this.messages.getMessage(Messages.ERROR_UNKNOWN_COMMAND);
	}

	@Override
	public @NotNull Message ERROR_UNCAUGHT_EXCEPTION() {
		return this.messages.getMessage(Messages.ERROR_INTERNAL);
	}

}
