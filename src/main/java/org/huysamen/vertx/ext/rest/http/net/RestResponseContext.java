package org.huysamen.vertx.ext.rest.http.net;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilter;

import java.util.Queue;
import java.util.Set;

/**
 * This class defines the contract to be used when implementing a response context to be used with {@link org.huysamen.vertx.ext.rest.http.net.RestResponse}.
 * It provides additional execution possibilities such as applying filters.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestResponseContext {

    /**
     * Returns the underlying {@link org.huysamen.vertx.ext.rest.http.net.RestResponse} linked to this context.
     */
    public RestResponse response();

    /**
     * Set the queue of {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilter} instances to consider for this
     * response.
     *
     * @param responseFilters The queue of {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilter} instances
     */
    public void responseFilters(final Queue<ResponseFilter> responseFilters);

    /**
     * Register the set of response filter labels that are applicable to this response.
     *
     * @param labels The set of labels
     */
    public void responseFilterLabels(final Set<String> labels);

    /**
     * This method starts the execution of the response handling. This specific method mimics calling
     * {@link io.vertx.core.http.HttpServerResponse#end(String)}.
     */
    public void execute(final String chunk);

    /**
     * This method starts the execution of the response handling. This specific method mimics calling
     * {@link io.vertx.core.http.HttpServerResponse#end(String, String)}.
     */
    public void execute(final String chunk, final String encoding);

    /**
     * This method starts the execution of the response handling. This specific method mimics calling
     * {@link io.vertx.core.http.HttpServerResponse#end(io.vertx.core.buffer.Buffer)}.
     */
    public void execute(final Buffer chunk);

    /**
     * This method starts the execution of the response handling. This specific method mimics calling
     * {@link io.vertx.core.http.HttpServerResponse#end()}.
     */
    public void execute();

    /**
     * This method is used by {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilter} instances to proceed to the
     * next filter in the filter chain. If all filters have been applied, this method will call end the response by
     * calling the appropriate end method on the underlying {@link io.vertx.core.http.HttpServerResponse}.
     */
    public void proceed();

    /**
     * This instructs the context to abort the filter chain. No further filters will be applied and the appropriate end
     * method of the underlying {@link io.vertx.core.http.HttpServerResponse} will be called.
     *
     * @param abortHandler The abort handler to call
     */
    public void abort(final Handler<RestResponse> abortHandler);
}
