package org.huysamen.vertx.ext.rest.http.net.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import org.huysamen.vertx.ext.rest.http.net.RestResponse;
import org.huysamen.vertx.ext.rest.http.net.RestResponseContext;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.net.RestResponse} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestResponseImpl implements RestResponse {

    private final HttpServerResponse response;
    private final RestResponseContext responseContext;

    public RestResponseImpl(final HttpServerResponse response) {
        this.response = response;
        this.responseContext = new RestResponseContextImpl(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // RestResponse Contract
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public HttpServerResponse response() {
        return response;
    }

    @Override
    public RestResponseContext responseContext() {
        return responseContext;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HttpServerResponse Contract
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Overloading
    //

    @Override
    public void end(final String chunk) {
        if (responseContext != null) {
            responseContext.execute(chunk);
        } else {
            response.end(chunk);
        }
    }

    @Override
    public void end(final String chunk, final String encoding) {
        if (responseContext != null) {
            responseContext.execute(chunk, encoding);
        } else {
            response.end(chunk, encoding);
        }
    }

    @Override
    public void end(final Buffer chunk) {
        if (responseContext != null) {
            responseContext.execute(chunk);
        } else {
            response.end(chunk);
        }
    }

    @Override
    public void end() {
        if (responseContext != null) {
            responseContext.execute();
        } else {
            response.end();
        }
    }


    // Pass through
    //

    @Override
    public HttpServerResponse exceptionHandler(final Handler<Throwable> handler) {
        response.exceptionHandler(handler);
        return this;
    }

    @Override
    public HttpServerResponse write(final Buffer buffer) {
        response.write(buffer);
        return this;
    }

    @Override
    public HttpServerResponse setWriteQueueMaxSize(final int i) {
        response.setWriteQueueMaxSize(i);
        return this;
    }

    @Override
    public HttpServerResponse drainHandler(final Handler<Void> handler) {
        response.drainHandler(handler);
        return this;
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public HttpServerResponse setStatusCode(final int i) {
        response.setStatusCode(i);
        return this;
    }

    @Override
    public String getStatusMessage() {
        return response.getStatusMessage();
    }

    @Override
    public HttpServerResponse setStatusMessage(final String s) {
        response.setStatusMessage(s);
        return this;
    }

    @Override
    public HttpServerResponse setChunked(final boolean b) {
        response.setChunked(b);
        return this;
    }

    @Override
    public boolean isChunked() {
        return response.isChunked();
    }

    @Override
    public MultiMap headers() {
        return response.headers();
    }

    @Override
    public HttpServerResponse putHeader(final String s, final String s1) {
        response.putHeader(s, s1);
        return this;
    }

    @Override
    public HttpServerResponse putHeader(final CharSequence charSequence, final CharSequence charSequence1) {
        response.putHeader(charSequence, charSequence1);
        return this;
    }

    @Override
    public HttpServerResponse putHeader(final String s, final Iterable<String> iterable) {
        response.putHeader(s, iterable);
        return this;
    }

    @Override
    public HttpServerResponse putHeader(final CharSequence charSequence, final Iterable<CharSequence> iterable) {
        response.putHeader(charSequence, iterable);
        return this;
    }

    @Override
    public MultiMap trailers() {
        return response.trailers();
    }

    @Override
    public HttpServerResponse putTrailer(final String s, final String s1) {
        response.putTrailer(s, s1);
        return this;
    }

    @Override
    public HttpServerResponse putTrailer(final CharSequence charSequence, final CharSequence charSequence1) {
        response.putTrailer(charSequence, charSequence1);
        return this;
    }

    @Override
    public HttpServerResponse putTrailer(final String s, final Iterable<String> iterable) {
        response.putTrailer(s, iterable);
        return this;
    }

    @Override
    public HttpServerResponse putTrailer(final CharSequence charSequence, final Iterable<CharSequence> iterable) {
        response.putTrailer(charSequence, iterable);
        return this;
    }

    @Override
    public HttpServerResponse closeHandler(final Handler<Void> handler) {
        response.closeHandler(handler);
        return this;
    }

    @Override
    public HttpServerResponse write(final String s, final String s1) {
        response.write(s, s1);
        return this;
    }

    @Override
    public HttpServerResponse write(final String s) {
        response.write(s);
        return this;
    }

    @Override
    public HttpServerResponse sendFile(final String s) {
        response.sendFile(s);
        return this;
    }

    @Override
    public HttpServerResponse sendFile(final String s, final String s1) {
        response.sendFile(s, s1);
        return this;
    }

    @Override
    public HttpServerResponse sendFile(final String s, final Handler<AsyncResult<Void>> handler) {
        response.sendFile(s, handler);
        return this;
    }

    @Override
    public HttpServerResponse sendFile(final String s, final String s1, final Handler<AsyncResult<Void>> handler) {
        response.sendFile(s, s1, handler);
        return this;
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public boolean writeQueueFull() {
        return response.writeQueueFull();
    }
}
