package dev.ethp.adminsu.api.extension;

import org.jetbrains.annotations.NotNull;


/**
 * An {@link Exception exception} that occurs when trying to interact with an unsupported extension.
 * 
 * @since 2.0.0
 */
public class ExtensionUnsupportedException extends ExtensionException {

	// -----------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new ExtensionUnsupportedException with a message.
	 * 
	 * @param message The message.
	 */
	public ExtensionUnsupportedException(@NotNull String message) {
		super(message);
	}

	/**
	 * Creates a new ExtensionUnsupportedException with an extension and a message.
	 *
	 * @param extension The causing extension.
	 * @param message The message.
	 */
	public ExtensionUnsupportedException(@NotNull ExtensionInstance extension, @NotNull String message) {
		super(extension, message);
	}

	/**
	 * Creates a new ExtensionUnsupportedException with a message and a cause.
	 *
	 * @param message The message.
	 * @param cause The causing throwable.   
	 */
	public ExtensionUnsupportedException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Creates a new ExtensionUnsupportedException with an extension, message, and cause.
	 *
	 * @param extension The causing extension.
	 * @param message The message.
	 * @param cause The causing throwable.   
	 */
	public ExtensionUnsupportedException(@NotNull ExtensionInstance extension, @NotNull String message, @NotNull Throwable cause) {
		super(extension, message, cause);
	}


}
