package org.huysamen.vertx.ext.rest.route;

import io.vertx.core.Handler;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.type.MediaType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a single resource that can be matched to incoming requests.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class Resource {

    public final MediaType produces;
    public final Handler<RestRequest> handler;
    public final Set<String> requestFilters;
    public final Set<String> responseFilters;
    public final Set<String> rolesAllowed;
    public final boolean requiresAllRoles;

    public Resource(final ResourceOptions options, final Handler<RestRequest> handler) {
        this.produces = options.produces();
        this.handler = handler;
        this.requestFilters = Collections.unmodifiableSet(new HashSet<>(options.requestFilters()));
        this.responseFilters = Collections.unmodifiableSet(new HashSet<>(options.responseFilters()));
        this.rolesAllowed = Collections.unmodifiableSet(new HashSet<>(options.rolesAllowed()));
        this.requiresAllRoles = options.requiresAllRoles();
    }
}
