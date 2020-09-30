package dev.ethp.adminsu.api.extension;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * An {@link Exception exception} related to extensions.
 * 
 * @since 2.0.0
 */
public class ExtensionException extends RuntimeException {

	private final @Nullable ExtensionInstance extension;


	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new ExtensionException with a message.
	 *
	 * @param message The message.
	 */
	public ExtensionException(@NotNull String message) {
		super(message);
		this.extension = null;
	}

	/**
	 * Creates a new ExtensionException with an extension and a message.
	 *
	 * @param extension The causing extension.
	 * @param message   The message.
	 */
	public ExtensionException(@NotNull ExtensionInstance extension, @NotNull String message) {
		super(message);
		this.extension = extension;
	}

	/**
	 * Creates a new ExtensionException with a message and a cause.
	 *
	 * @param message The message.
	 * @param cause   The causing throwable.
	 */
	public ExtensionException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
		this.extension = null;
	}

	/**
	 * Creates a new ExtensionException with an extension, message, and cause.
	 *
	 * @param extension The causing extension.
	 * @param message   The message.
	 * @param cause     The causing throwable.
	 */
	public ExtensionException(@NotNull ExtensionInstance extension, @NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
		this.extension = extension;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Methods:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the extension instance that caused the exception.
	 *
	 * @return The extension.
	 */
	public @Nullable ExtensionInstance getExtension() {
		return this.extension;
	}

}
