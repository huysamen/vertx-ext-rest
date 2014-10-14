package org.huysamen.vertx.ext.rest.http.filter;

import io.vertx.core.Handler;
import org.huysamen.vertx.ext.rest.http.net.RestResponseContext;

/**
 * This is just a wrapper class used by {@link org.huysamen.vertx.ext.rest.core.impl.RestRouteMatcherFactoryImpl} to
 * store and order response filters.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class ResponseFilter implements Comparable<ResponseFilter> {

    public final int priority;
    public final String label;
    public final Handler<RestResponseContext> handler;

    public ResponseFilter(final ResponseFilterOptions options, final Handler<RestResponseContext> handler) {
        this.priority = options.priority();
        this.label = options.label();
        this.handler = handler;
    }

    @Override
    public int compareTo(final ResponseFilter filter) {
        return Integer.compare(filter.priority, priority);
    }
}
