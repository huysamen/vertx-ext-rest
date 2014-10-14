package org.huysamen.vertx.ext.rest.core;

import io.vertx.core.Handler;
import io.vertx.core.ServiceHelper;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.huysamen.vertx.ext.rest.http.filter.RequestFilterOptions;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilterOptions;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.net.RestRequestContext;
import org.huysamen.vertx.ext.rest.http.net.RestResponseContext;
import org.huysamen.vertx.ext.rest.route.ResourceOptions;

/**
 * The contract for a REST Route Matcher.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestRouteMatcher {

    /**
     * Accept handler given to the HTTP server.
     *
     * @param request The incoming {@link io.vertx.core.http.HttpServerRequest}
     */
    public RestRouteMatcher accept(final HttpServerRequest request);

    /**
     * Register a new resource handler with the default options.
     *
     * @param path The URI path on which to create the new resource
     * @param method The {@link io.vertx.core.http.HttpMethod} to match
     * @param handler The handler to call
     */
    public RestRouteMatcher resource(final String path, final HttpMethod method, final Handler<RestRequest> handler);

    /**
     * Register a new resource handler with the specified options.
     *
     * @param path The URI path on which to create the new resource
     * @param method The {@link io.vertx.core.http.HttpMethod} to match
     * @param options The {@link org.huysamen.vertx.ext.rest.route.ResourceOptions} for the resource
     * @param handler The handler to call
     */
    public RestRouteMatcher resource(final String path, final HttpMethod method, final ResourceOptions options, final Handler<RestRequest> handler);

    /**
     * Register a request filter in the matcher with the default {@link org.huysamen.vertx.ext.rest.http.filter.RequestFilterOptions}.
     * <p></p>
     * Pre-matching request filters will be matched to any and all incoming requests. Labeled pre-matching filters will
     * be executed regardless of their name, as this filtering happens before a resource is matched, i.e. we do not know
     * which labels are applicable.
     * <p></p>
     * Normal request filters, if not labeled, will be matching to all incoming requests, if the matched resource does
     * not explicitly specify which request filter labels is accepts. If a resource specifies which filters can be
     * applied, only those filters will be applied.
     * <p></p>
     * All filters declare a priority, which is it's natural ordering in the filter chain.
     * Filters with a lower priority value are executed first. Filters with the same order will be chosen arbitrarily,
     * so please take caution if your filter chain execution order is of importance.
     *
     * @param handler The handler for the filter
     */
    public RestRouteMatcher requestFilter(final Handler<RestRequestContext> handler);

    /**
     * Register a request filter in the matcher with the specified {@link org.huysamen.vertx.ext.rest.http.filter.RequestFilterOptions}.
     * <p></p>
     * See {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher#requestFilter(io.vertx.core.Handler)} for a detailed
     * description.
     *
     * @param options The {@link org.huysamen.vertx.ext.rest.http.filter.RequestFilterOptions} to use
     * @param handler The handler for the filter
     */
    public RestRouteMatcher requestFilter(final RequestFilterOptions options, final Handler<RestRequestContext> handler);

    /**
     * Register a response filter in the matcher with the default {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilterOptions}.
     * <p></p>
     * Response filters are applied after the request handler has executed. If the filter is labeled, it will only be
     * applied if the resource method allows the label.
     * <p></p>
     * All filters declare a priority, which is it's reverse natural ordering in the filter chain. Filters with a higher
     * priority value are executed first. Filters with the same order will be chosen arbitrarily, so please take caution
     * if your filter chain execution order is of importance.
     *
     * @param handler The handler for the filter
     */
    public RestRouteMatcher responseFilter(final Handler<RestResponseContext> handler);

    /**
     * Register a response filter in the matcher with the specified {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilterOptions}.
     * <p></p>
     * See {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher#responseFilter(io.vertx.core.Handler)} for a
     * detailed description.
     *
     * @param options The {@link org.huysamen.vertx.ext.rest.http.filter.ResponseFilterOptions} to use
     * @param handler The handler for this filter
     */
    public RestRouteMatcher responseFilter(final ResponseFilterOptions options, final Handler<RestResponseContext> handler);

    /**
     * The handler to call when a resource could not be matched to the incoming request.
     *
     * @param handler The handler to call
     */
    public RestRouteMatcher notFound(final Handler<RestRequest> handler);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Factory Boilerplate
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcherFactory} instance created by the service
     * loader.
     */
    public static final RestRouteMatcherFactory factory = ServiceHelper.loadFactory(RestRouteMatcherFactory.class);

    /**
     * Create a {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher} instance.
     *
     * @return The newly created instance
     */
    public static RestRouteMatcher restRouteMatcher() {
        return factory.restRouteMatcher();
    }
}
