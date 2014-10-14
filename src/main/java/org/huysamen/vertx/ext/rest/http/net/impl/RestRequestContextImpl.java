package org.huysamen.vertx.ext.rest.http.net.impl;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import org.apache.commons.lang3.StringUtils;
import org.huysamen.vertx.ext.rest.http.filter.RequestFilter;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilter;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.net.RestRequestContext;
import org.huysamen.vertx.ext.rest.route.Resource;

import java.util.Queue;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.net.RestRequestContext} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestRequestContextImpl implements RestRequestContext {

    private final RestRequest request;
    private final Handler<RestRequest> notFoundHandler;

    private Queue<RequestFilter> filters;
    private Resource resource;

    public RestRequestContextImpl(final HttpServerRequest request, final Handler<RestRequest> notFoundHandler) {
        this.request = new RestRequestImpl(request);
        this.notFoundHandler = notFoundHandler;
    }

    @Override
    public RestRequest request() {
        return request;
    }

    @Override
    public void execute(
            final Queue<RequestFilter> requestFilters,
            final Queue<ResponseFilter> responseFilters,
            final Resource resource) {

        this.filters = requestFilters;
        this.resource = resource;
        this.request.response().responseContext().responseFilters(responseFilters);

        if (resource != null) {
            this.request.response().responseContext().responseFilterLabels(resource.responseFilters);
            this.request.response().headers().set("Content-Type", resource.produces.toResponseString());
        }

        proceed();
    }

    @Override
    public void proceed() {
        if (!filters.isEmpty()) {
            switch (filters.peek().type) {
                case PRE_MATCH:
                    filters.poll().handler.handle(this);
                    break;

                case REQUEST:
                    if (resource == null) {
                        notFoundHandler.handle(request);
                    } else {
                        RequestFilter next = filters.poll();

                        while (StringUtils.isNotBlank(next.label) && !resource.requestFilters.contains(next.label)) {
                            if (!filters.isEmpty()) {
                                next = filters.poll();
                            } else {
                                resource.handler.handle(request);
                                return;
                            }
                        }

                        next.handler.handle(this);
                    }
                    break;
            }
        } else {
            if (resource == null) {
                notFoundHandler.handle(request);
            } else {
                resource.handler.handle(request);
            }
        }
    }

    @Override
    public void abort(final Handler<RestRequest> handler) {
        handler.handle(request);
    }
}
