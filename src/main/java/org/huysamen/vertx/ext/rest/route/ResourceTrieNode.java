package org.huysamen.vertx.ext.rest.route;

import io.vertx.core.Handler;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.type.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A single node in a {@link org.huysamen.vertx.ext.rest.route.ResourceTrie}.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class ResourceTrieNode {

    protected final Map<String, ResourceTrieNode> children = new HashMap<>();
    protected final Map<MediaType, Map<MediaType, Resource>> resources = new HashMap<>();
    protected List<String> parameters;

    protected void addHandler(final ResourceOptions options, final List<String> parameters, final Handler<RestRequest> handler) {
        if (resources.containsKey(options.consumes())) {
            throw new IllegalArgumentException("A resource on this path that consumes the MediaType [" + options.consumes().toString() + "] is already defined");
        }

        this.parameters = parameters;

        Map<MediaType, Resource> produces = resources.get(options.consumes());

        if (produces == null) {
            produces = new HashMap<>();
            resources.put(options.consumes(), produces);
        }

        if (produces.containsKey(options.produces())) {
            throw new IllegalArgumentException("A resource on this path that produces the MediaType [" + options.produces().toString() + "] is already defined");
        }

        produces.put(options.produces(), new Resource(options, handler));
    }

    protected Resource match(final RestRequest request) {
        final Map<MediaType, Resource> r = resources.get(request.contentType());

        if (r == null) {
            return null;
        }

        for (final MediaType accept : request.accepts()) {
            if (r.containsKey(accept)) {
                return r.get(accept);
            }
        }

        return null;
    }
}
