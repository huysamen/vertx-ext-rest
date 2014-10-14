package org.huysamen.vertx.ext.rest.http.net;

import io.vertx.core.Handler;
import org.huysamen.vertx.ext.rest.http.filter.RequestFilter;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilter;
import org.huysamen.vertx.ext.rest.route.Resource;

import java.util.Queue;

/**
 * This class defines the contract to be used when implementing a request context to be used with {@link org.huysamen.vertx.ext.rest.http.net.RestRequest}.
 * It provides additional execution possibilities such as applying filters.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestRequestContext {

    /**
     * Return the underlying {@link org.huysamen.vertx.ext.rest.http.net.RestRequest} for this context.
     */
    public RestRequest request();

    /**
     * This method is used to start the execution chain for a request.
     *
     * @param requestFilters The list of {@link org.huysamen.vertx.ext.rest.http.filter.RequestFilter} to apply
     * @param responseFilters The list of {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilter} to apply
     * @param resource The resource matched to the request, or <code>null</code> if none were matched
     */
    public void execute(final Queue<RequestFilter> requestFilters, final Queue<ResponseFilter> responseFilters, final Resource resource);

    /**
     * This method is used by {@link org.huysamen.vertx.ext.rest.http.filter.RequestFilter} instances to proceed to the
     * next filter in the filter chain. If all filters have been applied, this method will call the request handler
     * that was registered on the {@link org.huysamen.vertx.ext.rest.route.Resource}. If no resource was matched, the
     * handler for "no match found" is invoked.
     */
    public void proceed();

    /**
     * This method instructs the context that a request needs to be aborted. No further request filters will be applied,
     * but {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilter} instances will be executed.
     *
     * @param handler The abort handler to call
     */
    public void abort(final Handler<RestRequest> handler);
}
