package org.huysamen.vertx.ext.rest.core.impl;

import org.huysamen.vertx.ext.rest.core.RestRouteMatcher;
import org.huysamen.vertx.ext.rest.core.RestRouteMatcherFactory;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcherFactory} contract, used by the service
 * loader to provide an instance of {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher}.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RestRouteMatcherFactoryImpl implements RestRouteMatcherFactory {

    @Override
    public RestRouteMatcher restRouteMatcher() {
        return new RestRouteMatcherImpl();
    }
}
