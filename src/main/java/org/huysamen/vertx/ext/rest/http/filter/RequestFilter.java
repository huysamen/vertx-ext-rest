package org.huysamen.vertx.ext.rest.http.filter;

import io.vertx.core.Handler;
import org.huysamen.vertx.ext.rest.http.net.RestRequestContext;

/**
 * This is just a wrapper class used by {@link org.huysamen.vertx.ext.rest.core.impl.RestRouteMatcherFactoryImpl} to
 * store and order request filters.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RequestFilter implements Comparable<RequestFilter> {

    public final RequestFilterType type;
    public final int priority;
    public final String label;
    public final Handler<RestRequestContext> handler;

    public RequestFilter(final RequestFilterOptions options, final Handler<RestRequestContext> handler) {
        this.type = options.type();
        this.priority = options.priority();
        this.label = options.label();
        this.handler = handler;
    }

    @Override
    public int compareTo(final RequestFilter filter) {
        return Integer.compare(priority, filter.priority);
    }
}
