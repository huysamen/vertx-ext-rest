package org.huysamen.vertx.ext.rest.http.security;

/**
 * Defines the security context contract to be used by the framework.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface SecurityContext {

    /**
     * Returns the principal for this request.
     */
    public Principal principal();

    /**
     * Determines if the principal has any of the roles requested.
     *
     * @param roles The requested roles to check
     */
    public boolean isUserInAnyRoles(final String... roles);

    /**
     * Determines if the principal has all of the roles requested.
     *
     * @param roles The requested roles to check
     */
    public boolean isUserInAllRoles(final String... roles);
}
