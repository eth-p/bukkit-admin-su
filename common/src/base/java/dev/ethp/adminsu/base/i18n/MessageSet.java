package dev.ethp.adminsu.base.i18n;

import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a set of localized messages.
 */
public interface MessageSet {

	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets a localized message.
	 *
	 * @param key The message key.
	 * @return The message.
	 */
	@NotNull Message getMessage(MessageKey key);


	// -------------------------------------------------------------------------------------------------------------
	// Static:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a cached message set around another message set.
	 * This uses a hashmap to speed up message lookups.
	 *
	 * @param set The set to wrap.
	 * @return The cached set.
	 */
	static MessageSetCached cached(@NotNull MessageSet set) {
		if (set instanceof MessageSetCached) return (MessageSetCached) set;
		return new MessageSetCached(set);
	}

	/**
	 * Creates a preloaded message set around another message set.
	 *
	 * @param set The set to wrap.
	 * @return The cached set.
	 */
	static <E extends Enum<E> & MessageKey> MessageSetPreloaded preloaded(@NotNull MessageSet set, @NotNull Class<E> keys) {
		return new MessageSetPreloaded(set, keys);
	}

}
