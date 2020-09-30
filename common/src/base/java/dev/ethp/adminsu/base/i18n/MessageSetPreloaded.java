package dev.ethp.adminsu.base.i18n;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link MessageSet} that preloads messages with keys stored in an enum or collection.
 */
public class MessageSetPreloaded implements MessageSet {

	private @NotNull Map<String, Message> map;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------------------------------------------

	private MessageSetPreloaded(@NotNull MessageSet messages, @NotNull Iterator<MessageKey> keys) {
		Map<String, Message> map = this.map = new HashMap<>();

		while (keys.hasNext()) {
			MessageKey key = keys.next();
			map.put(key.getMessageKey(), messages.getMessage(key));
		}
	}

	/**
	 * Creates a new preloaded message set.
	 *
	 * @param messages The message set to preload from.
	 * @param keys     The message keys.
	 * @param <E>      The enum type.
	 */
	<E extends Enum<E> & MessageKey> MessageSetPreloaded(@NotNull MessageSet messages, @NotNull Class<E> keys) {
		this(messages, Arrays.stream(keys.getEnumConstants())
				.map(field -> ((MessageKey) field))
				.iterator());
	}


	/**
	 * Creates a new preloaded message set.
	 *
	 * @param messages The message set to preload from.
	 * @param keys     The message keys.
	 */
	MessageSetPreloaded(@NotNull MessageSet messages, @NotNull Iterable<MessageKey> keys) {
		this(messages, keys.iterator());
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Clears the cache.
	 */
	public void invalidate() {
		this.map.clear();
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: MessageSet
	// -------------------------------------------------------------------------------------------------------------

	public @NotNull Message getMessage(MessageKey key) {
		final Message resolved = this.map.get(key.getMessageKey());
		if (resolved != null) return resolved;

		return new Message("{{MISSING:" + key.getMessageKey() + "}}");
	}

}
