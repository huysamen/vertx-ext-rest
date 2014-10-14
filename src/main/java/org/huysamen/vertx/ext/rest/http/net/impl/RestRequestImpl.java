package org.huysamen.vertx.ext.rest.http.net.impl;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import org.apache.commons.lang3.StringUtils;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;
import org.huysamen.vertx.ext.rest.http.net.RestResponse;
import org.huysamen.vertx.ext.rest.http.type.MediaType;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.net.RestRequest} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestRequestImpl implements RestRequest {

    private static final Logger LOG = LoggerFactory.getLogger(RestRequestImpl.class);

    private final HttpServerRequest request;
    private final RestResponse response;

    private List<MediaType> accepts;
    private MediaType contentType;

    public RestRequestImpl(final HttpServerRequest request) {
        this.request = request;
        this.response = new RestResponseImpl(request.response());
        this.accepts = new ArrayList<>();

        if (request.headers().contains("Accept")) {
            final String[] acceptHeaders = StringUtils.split(request.headers().get("Accept"));

            if (acceptHeaders != null) {
                for (final String header : acceptHeaders) {
                    try {
                        accepts.add(new MediaType(header));
                    } catch (final IllegalArgumentException e) {
                        LOG.warn("Could not parse 'Accept' header [" + e.getMessage() + "]");
                    }
                }
            }
        }

        if (request.headers().contains("Content-Type")) {
            try {
                contentType = new MediaType(request.headers().get("Content-Type"));
            } catch (final IllegalArgumentException e) {
                LOG.warn("Could not parse 'Content-Type' header [" + e.getMessage() + "]");
            }
        }

        if (accepts.isEmpty()) {
            accepts.add(new MediaType("*/*"));
        }

        if (contentType == null) {
            contentType = MediaType.ANY;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // RestRequest Contract
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<MediaType> accepts() {
        return Collections.unmodifiableList(accepts);
    }

    @Override
    public MediaType contentType() {
        return contentType;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HttpServerRequest Contract
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Overloading
    //

    @Override
    public RestResponse response() {
        return response;
    }


    // Pass through
    //

    @Override
    public HttpServerRequest exceptionHandler(final Handler<Throwable> handler) {
        request.exceptionHandler(handler);
        return this;
    }

    @Override
    public HttpServerRequest handler(final Handler<Buffer> handler) {
        request.handler(handler);
        return this;
    }

    @Override
    public HttpServerRequest pause() {
        request.pause();
        return this;
    }

    @Override
    public HttpServerRequest resume() {
        request.resume();
        return this;
    }

    @Override
    public HttpServerRequest endHandler(final Handler<Void> handler) {
        request.endHandler(handler);
        return this;
    }

    @Override
    public HttpVersion version() {
        return request.version();
    }

    @Override
    public HttpMethod method() {
        return request.method();
    }

    @Override
    public String uri() {
        return request.uri();
    }

    @Override
    public String path() {
        return request.path();
    }

    @Override
    public String query() {
        return request.query();
    }

    @Override
    public MultiMap headers() {
        return request.headers();
    }

    @Override
    public MultiMap params() {
        return request.params();
    }

    @Override
    public SocketAddress remoteAddress() {
        return request.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return request.localAddress();
    }

    @Override
    public X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException {
        return request.peerCertificateChain();
    }

    @Override
    public String absoluteURI() {
        return request.absoluteURI();
    }

    @Override
    public HttpServerRequest bodyHandler(final Handler<Buffer> handler) {
        request.bodyHandler(handler);
        return this;
    }

    @Override
    public NetSocket netSocket() {
        return request.netSocket();
    }

    @Override
    public HttpServerRequest setExpectMultipart(final boolean b) {
        request.setExpectMultipart(b);
        return this;
    }

    @Override
    public boolean isExpectMultipart() {
        return request.isExpectMultipart();
    }

    @Override
    public HttpServerRequest uploadHandler(final Handler<HttpServerFileUpload> handler) {
        request.uploadHandler(handler);
        return this;
    }

    @Override
    public MultiMap formAttributes() {
        return request.formAttributes();
    }
}
