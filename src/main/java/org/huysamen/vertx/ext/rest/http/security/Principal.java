package org.huysamen.vertx.ext.rest.http.security;

import java.util.Set;

/**
 * Defines the contract for a principal to be used by a {@link org.huysamen.vertx.ext.rest.http.security.SecurityContext}
 * for authorisation and authentication.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public interface Principal {

    /**
     * Returns the name of the principal.
     */
    public String name();

    /**
     * Returns the roles assigned to the principal.
     */
    public Set<String> roles();
}
