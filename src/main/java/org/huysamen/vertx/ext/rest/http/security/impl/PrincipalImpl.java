package org.huysamen.vertx.ext.rest.http.security.impl;

import org.huysamen.vertx.ext.rest.http.security.Principal;

import java.util.Set;

/**
 * Implementation of the {@link org.huysamen.vertx.ext.rest.http.security.Principal} contract.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class PrincipalImpl implements Principal {

    private String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Set<String> roles() {
        return null;
    }
}
