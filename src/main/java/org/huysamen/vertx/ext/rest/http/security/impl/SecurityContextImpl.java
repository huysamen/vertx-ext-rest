package org.huysamen.vertx.ext.rest.http.security.impl;

import org.huysamen.vertx.ext.rest.http.security.Principal;
import org.huysamen.vertx.ext.rest.http.security.SecurityContext;

import java.util.Arrays;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.security.SecurityContext} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class SecurityContextImpl implements SecurityContext {

    private Principal principal;

    @Override
    public Principal principal() {
        return principal;
    }

    @Override
    public boolean isUserInAnyRoles(final String... role) {
        return Arrays.stream(role).parallel().anyMatch(principal.roles()::contains);
    }

    @Override
    public boolean isUserInAllRoles(final String... role) {
        return Arrays.stream(role).parallel().allMatch(principal.roles()::contains);
    }
}
