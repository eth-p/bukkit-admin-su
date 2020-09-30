package dev.ethp.adminsu.common.util;

/**
 * A utility class for working with Java {@link Throwable} objects.
 */
public class ThrowableUtil {
	private ThrowableUtil() {
	}

	/**
	 * Rethrows a checked exception.
	 *
	 * @param throwable The throwable to throw.
	 * @param <T>       The type of the Throwable.
	 * @return Never returns, only throws.
	 * @throws T The throwable as an unchecked exception.
	 * @see <a href="https://stackoverflow.com/a/4555351/11188567">https://stackoverflow.com/a/4555351/11188567</a>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> RuntimeException rethrow(Throwable throwable) throws T {
		throw (T) throwable;
	}

}
