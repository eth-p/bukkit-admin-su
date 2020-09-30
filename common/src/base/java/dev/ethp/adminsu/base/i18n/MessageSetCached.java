package dev.ethp.adminsu.base.i18n;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link MessageSet} that caches retrieved messages.
 */
public class MessageSetCached implements MessageSet {

	private @NotNull MessageSet inner;
	private @NotNull Map<String, Message> map;

	// -------------------------------------------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new cached message set.
	 *
	 * @param messages The underlying message set.
	 */
	MessageSetCached(@NotNull MessageSet messages) {
		this.inner = messages;
		this.map = new HashMap<>();
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
		final String keyString = key.getMessageKey();
		final Message cached = this.map.get(keyString);
		if (cached != null) return cached;

		final Message resolved = this.inner.getMessage(key);
		this.map.put(keyString, resolved);
		return resolved;
	}

}
