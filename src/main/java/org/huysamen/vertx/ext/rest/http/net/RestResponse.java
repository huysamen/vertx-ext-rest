package org.huysamen.vertx.ext.rest.http.net;

import io.vertx.core.http.HttpServerResponse;

/**
 * This defines the contract required by a {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher} to fulfil a
 * response. This class wraps the {@link io.vertx.core.http.HttpServerResponse} received from the Vert.x framework to
 * allow for additional hooks so that filters can be applied.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestResponse extends HttpServerResponse {

    /**
     * Returns the underlying {@link io.vertx.core.http.HttpServerResponse}.
     */
    public HttpServerResponse response();

    /**
     * Returns the {@link org.huysamen.vertx.ext.rest.http.net.RestRequestContext} associated with this response.
     */
    public RestResponseContext responseContext();
}
