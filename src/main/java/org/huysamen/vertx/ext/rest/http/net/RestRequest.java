package org.huysamen.vertx.ext.rest.http.net;

import io.vertx.core.http.HttpServerRequest;
import org.huysamen.vertx.ext.rest.http.type.MediaType;

import java.util.List;

/**
 * This defines the contract required by a {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher} to fulfil a
 * request. This class wraps the {@link io.vertx.core.http.HttpServerRequest} received from the Vert.x framework to
 * allow for additional hooks so that filters can be applied.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestRequest extends HttpServerRequest {

    /**
     * The list of {@link org.huysamen.vertx.ext.rest.http.type.MediaType} values that this request accepts.
     */
    public List<MediaType> accepts();

    /**
     * The {@link org.huysamen.vertx.ext.rest.http.type.MediaType} that this request has as content type.
     */
    public MediaType contentType();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Interface Overrides
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The request needs to return an instance of {@link org.huysamen.vertx.ext.rest.http.net.RestResponse} rather than
     * the {@link io.vertx.core.http.HttpServerResponse} provided by Vert.x.
     */
    public RestResponse response();
}
