package org.huysamen.vertx.ext.rest.core;

/**
 * Java service factory to create an instance of {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher}.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface RestRouteMatcherFactory {

    /**
     * Create an instance of a {@link org.huysamen.vertx.ext.rest.core.RestRouteMatcher}.
     *
     * @return The newly created instance
     */
    public RestRouteMatcher restRouteMatcher();
}
