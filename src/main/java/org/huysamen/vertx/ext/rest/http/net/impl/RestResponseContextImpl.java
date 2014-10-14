package org.huysamen.vertx.ext.rest.http.net.impl;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.huysamen.vertx.ext.rest.http.filter.ResponseFilter;
import org.huysamen.vertx.ext.rest.http.net.RestResponse;
import org.huysamen.vertx.ext.rest.http.net.RestResponseContext;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.net.RestResponseContext} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestResponseContextImpl implements RestResponseContext {

    private final RestResponse response;

    private Queue<ResponseFilter> filters;
    private Set<String> filterLabels = new HashSet<>();
    private String chunk;
    private String encoding;
    private Buffer buffer;

    public RestResponseContextImpl(final RestResponse response) {
        this.response = response;
    }

    @Override
    public RestResponse response() {
        return response;
    }

    @Override
    public void responseFilters(final Queue<ResponseFilter> responseFilters) {
        filters = responseFilters;
    }

    @Override
    public void responseFilterLabels(final Set<String> labels) {
        filterLabels.addAll(labels);
    }

    @Override
    public void execute(final String chunk) {
        setEndVariables(chunk, null, null);
        proceed();
    }

    @Override
    public void execute(final String chunk, final String encoding) {
        setEndVariables(chunk, encoding, null);
        proceed();
    }

    @Override
    public void execute(final Buffer chunk) {
        setEndVariables(null, null, chunk);
        proceed();
    }

    @Override
    public void execute() {
        setEndVariables(null, null, null);
        proceed();
    }

    @Override
    public void proceed() {
        if (!filters.isEmpty()) {
            ResponseFilter next = filters.poll();

            while (StringUtils.isNotBlank(next.label) && !filterLabels.contains(next.label)) {
                if (!filters.isEmpty()) {
                    next = filters.poll();
                } else {
                    handleResponseEnd();
                    return;
                }
            }

            next.handler.handle(this);
        } else {
            handleResponseEnd();
        }
    }

    @Override
    public void abort(final Handler<RestResponse> abortHandler) {

    }

    private void setEndVariables(final String chunk, final String encoding, final Buffer buffer) {
        this.chunk = chunk;
        this.encoding = encoding;
        this.buffer = buffer;
    }

    private void handleResponseEnd() {
        if (StringUtils.isNotBlank(chunk) && StringUtils.isBlank(encoding) && buffer == null) {
            response.response().end(chunk);
        } else if (StringUtils.isNotBlank(chunk) && StringUtils.isNotBlank(encoding) && buffer == null) {
            response.response().end(chunk, encoding);
        } else if (buffer != null) {
            response.response().end(buffer);
        } else {
            response.response().end();
        }
    }
}
