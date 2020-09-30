package dev.ethp.adminsu.base.i18n;

import java.util.Objects;
import java.util.Optional;

import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * A class for building chained messages.
 */
public class Message implements Cloneable {

	private String text;
	private StringBuilder textBuilder;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new message.
	 *
	 * @param text The message text.
	 */
	public Message(String text) {
		this.text = Objects.requireNonNull(text);
	}

	private Message(StringBuilder builder) {
		this.textBuilder = builder;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Clones this message and prepares it for efficient chaining.
	 *
	 * @return A cloned copy of the message.
	 */
	public Message chain() {
		return new Message(new StringBuilder(this.text));
	}

	/**
	 * Prepend a message to this one.
	 *
	 * @param other The message to prepend.
	 * @return The new message.
	 */
	public Message prepend(final Message other) {
		if (this.textBuilder != null) {
			this.textBuilder = new StringBuilder((other.textBuilder != null ? other.textBuilder.toString() : other.text));
			this.textBuilder.append(this.textBuilder.toString());
			return this;
		}

		return new Message(other.text + this.text);
	}

	/**
	 * Appends another message to this one.
	 *
	 * @param other The message to append.
	 * @return The new message.
	 */
	public Message append(final Message other) {
		if (this.textBuilder != null) {
			this.textBuilder.append(other.textBuilder != null ? other.textBuilder.toString() : other.text);
			return this;
		}

		return new Message(this.text + other.text);
	}

	/**
	 * Appends a string to this message.
	 *
	 * @param other The string to append.
	 * @return The new message.
	 */
	public Message append(final String other) {
		if (this.textBuilder != null) {
			this.textBuilder.append(other);
			return this;
		}

		return new Message(this.text + other);
	}

	/**
	 * Replaces a parameter in the message.
	 *
	 * @param name  The parameter name.
	 * @param value The parameter value.
	 * @return The replaced message.
	 */
	public Message param(String name, String value) {
		final String paramPattern = "{" + name + "}";
		if (this.textBuilder == null) {
			return new Message(this.text.replace(paramPattern, value));
		}

		// Find and replace loop in the builder.
		final int paramPatternLength = paramPattern.length();
		final int paramValueLength = value.length();

		int index = 0;
		while ((index = this.textBuilder.indexOf(paramPattern, index)) != -1) {
			this.textBuilder.replace(index, paramPatternLength, value);
			index += paramValueLength;
		}

		return this;
	}

	/**
	 * Optionally replaces a parameter in the message.
	 *
	 * @param name  The parameter name.
	 * @param value The parameter value.
	 * @return The replaced message.
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public <T> Message param(String name, @NotNull Optional<T> value) {
		return value.isPresent() ? this.param(name, value.get().toString()) : this;
	}

	/**
	 * Sends the message to a player.
	 *
	 * @param player The player to send the message to.
	 */
	public void send(PlayerAdapter player) {
		player.sendMessage(this.toString());
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: Cloneable
	// -----------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	public Object clone() {
		if (this.textBuilder != null) {
			return new Message(new StringBuilder(this.textBuilder));
		}

		return new Message(this.text);
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Implementation: Object
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		if (this.textBuilder != null) {
			return this.textBuilder.toString();
		}

		return this.text;
	}

}
