package dev.ethp.adminsu.base.platform;

/**
 * An interface for an adapter that can be unwrapped to retrieve its underlying object.
 */
public interface Unwrap<T> {

	/**
	 * Gets the underlying object from the adapter.
	 *
	 * @return The underlying object.
	 */
	T unwrap();

}
