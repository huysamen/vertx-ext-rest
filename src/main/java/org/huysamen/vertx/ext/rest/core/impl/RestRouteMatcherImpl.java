package org.huysamen.vertx.ext.rest.core.impl;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.huysamen.vertx.ext.rest.core.RestRouteMatcher;
import org.huysamen.vertx.ext.rest.http.filter.RequestFilter;
import org.huysamen.vertx.ext.rest.http.filter.RequestFilterOptions;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilter;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilterOptions;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.net.RestRequestContext;
import org.huysamen.vertx.ext.rest.http.net.RestResponseContext;
import org.huysamen.vertx.ext.rest.http.net.impl.RestRequestContextImpl;
import org.huysamen.vertx.ext.rest.route.ResourceOptions;
import org.huysamen.vertx.ext.rest.route.ResourceTrie;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestRouteMatcherImpl implements RestRouteMatcher {

    private final Queue<RequestFilter> requestFilters = new PriorityQueue<>();
    private final Queue<ResponseFilter> responseFilters = new PriorityQueue<>();
    private final Map<HttpMethod, ResourceTrie> resources = new HashMap<HttpMethod, ResourceTrie>() {
        {
            put(HttpMethod.OPTIONS, new ResourceTrie());
            put(HttpMethod.GET, new ResourceTrie());
            put(HttpMethod.HEAD, new ResourceTrie());
            put(HttpMethod.POST, new ResourceTrie());
            put(HttpMethod.PUT, new ResourceTrie());
            put(HttpMethod.DELETE, new ResourceTrie());
            put(HttpMethod.TRACE, new ResourceTrie());
            put(HttpMethod.CONNECT, new ResourceTrie());
            put(HttpMethod.PATCH, new ResourceTrie());
        }
    };

    private Handler<RestRequest> notFoundHandler;

    public RestRouteMatcherImpl() {
        notFoundHandler = (request) -> request.response().setStatusCode(404).end();
    }

    @Override
    public RestRouteMatcher accept(final HttpServerRequest httpServerRequest) {
        route(new RestRequestContextImpl(httpServerRequest, notFoundHandler));
        return this;
    }

    @Override
    public RestRouteMatcher resource(final String path, final HttpMethod method, final Handler<RestRequest> handler) {
        return resource(path, method, new ResourceOptions(), handler);
    }

    @Override
    public RestRouteMatcher resource(final String path, final HttpMethod method, final ResourceOptions options, final Handler<RestRequest> handler) {
        resources.get(method).addResource(path, options, handler);
        return this;
    }

    @Override
    public RestRouteMatcher requestFilter(final Handler<RestRequestContext> handler) {
        return requestFilter(new RequestFilterOptions(), handler);
    }

    @Override
    public RestRouteMatcher requestFilter(final RequestFilterOptions options, final Handler<RestRequestContext> handler) {
        requestFilters.add(new RequestFilter(options, handler));
        return this;
    }

    @Override
    public RestRouteMatcher responseFilter(final Handler<RestResponseContext> handler) {
        return responseFilter(new ResponseFilterOptions(), handler);
    }

    @Override
    public RestRouteMatcher responseFilter(final ResponseFilterOptions options, final Handler<RestResponseContext> handler) {
        responseFilters.add(new ResponseFilter(options, handler));
        return null;
    }

    @Override
    public RestRouteMatcher notFound(final Handler<RestRequest> handler) {
        notFoundHandler = handler;
        return this;
    }

    protected void route(final RestRequestContext context) {
        context.execute(
                new PriorityQueue<>(requestFilters),
                new PriorityQueue<>(responseFilters),
                resources.get(context.request().method()).match(context.request()));
    }
}
