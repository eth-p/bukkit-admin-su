package dev.ethp.adminsu.base.extension;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import dev.ethp.adminsu.api.extension.ExtensionRegistry;
import dev.ethp.adminsu.api.service.Service;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract implementation of {@link ExtensionRegistry}
 */
public abstract class AbstractExtensionRegistry implements ExtensionRegistry {

	private final @NotNull Map<Class<? extends Service>, Set<Service>> services;


	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Creates a new abstract extension registry.
	 */
	protected AbstractExtensionRegistry() {
		this.services = new HashMap<>();
	}


	// -------------------------------------------------------------------------------------------------------------
	// Methods:
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the registered instances of a service type.
	 *
	 * @param service The service type class.
	 * @param <T>     The service type.
	 * @return The registered instances.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Service> Set<T> getService(Class<? extends T> service) {
		Set<Service> registered = this.services.get(service);
		return registered == null ? Collections.emptySet() : Collections.unmodifiableSet((Set<T>) registered);
	}

	/**
	 * Finds all services implemented by a type.
	 *
	 * @param collected A set to store the service types to.
	 * @param type      The type to search.
	 */
	@SuppressWarnings("unchecked")
	private void findServicesInType(Set<Class<? extends Service>> collected, Class<? extends Service> type) {
		// Search through the interfaces.
		for (Class<?> iface : type.getInterfaces()) {
			if (iface != Service.class && Service.class.isAssignableFrom(iface)) {
				collected.add((Class<? extends Service>) iface);
			}
		}

		// Search the parent class.
		Class<?> parent = type.getSuperclass();
		if (parent != Object.class && parent != null) {
			findServicesInType(collected, (Class<? extends Service>) parent);
		}
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation:
	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void register(@NotNull Service service) {
		// Find services implemented.
		HashSet<Class<? extends Service>> services = new HashSet<>();
		this.findServicesInType(services, service.getClass());

		// Register the services.
		services.stream()
				.map(type -> this.services.computeIfAbsent(type, k -> new LinkedHashSet<>()))
				.forEach(registered -> registered.add(service));
	}


}
