package org.huysamen.vertx.ext.rest.route;

import org.huysamen.vertx.ext.rest.http.type.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The possible options available when registering resources in the {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher}.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class ResourceOptions {

    private MediaType consumes = MediaType.ANY;
    private MediaType produces = MediaType.ANY;
    private final Set<String> requestFilters;
    private final Set<String> responseFilters;
    private final Set<String> rolesAllowed;
    private boolean requiresAllRoles = false;

    public ResourceOptions() {
        rolesAllowed = new HashSet<>();
        requestFilters = new HashSet<>();
        responseFilters = new HashSet<>();
    }

    public ResourceOptions consumes(final String type) {
        consumes = new MediaType(type);
        return this;
    }

    public MediaType consumes() {
        return consumes;
    }

    public ResourceOptions produces(final String type) {
        produces = new MediaType(type);
        return this;
    }

    public MediaType produces() {
        return produces;
    }

    public ResourceOptions requestFilters(final String... filters) {
        requestFilters.addAll(Arrays.asList(filters));
        return this;
    }

    public Set<String> requestFilters() {
        return Collections.unmodifiableSet(requestFilters);
    }

    public ResourceOptions responseFilters(final String... filters) {
        responseFilters.addAll(Arrays.asList(filters));
        return this;
    }

    public Set<String> responseFilters() {
        return Collections.unmodifiableSet(responseFilters);
    }

    public ResourceOptions rolesAllowed(final String... roles) {
        rolesAllowed.addAll(Arrays.asList(roles));
        return this;
    }

    public Set<String> rolesAllowed() {
        return Collections.unmodifiableSet(rolesAllowed);
    }

    public ResourceOptions requiresAllRole(final boolean requiresAllRoles) {
        this.requiresAllRoles = requiresAllRoles;
        return this;
    }

    public boolean requiresAllRoles() {
        return requiresAllRoles;
    }
}
