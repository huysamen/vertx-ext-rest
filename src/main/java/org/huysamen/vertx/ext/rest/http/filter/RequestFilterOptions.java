package org.huysamen.vertx.ext.rest.http.filter;

/**
 * This class defines the options available when creating a request filter.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class RequestFilterOptions {

    private RequestFilterType type;
    private int priority;
    private String label;

    public RequestFilterOptions() {
        type = RequestFilterType.REQUEST;
        priority = Integer.MAX_VALUE;
        label = "";
    }

    public RequestFilterOptions type(final RequestFilterType type) {
        this.type = type;

        if (type.equals(RequestFilterType.PRE_MATCH)) {
            priority = 0;
        }

        return this;
    }

    public RequestFilterType type() {
        return type;
    }

    public RequestFilterOptions priority(final int priority) {
        if (priority < 1) {
            throw new IllegalArgumentException("Request filters need a priority greater than 1");
        }

        if (type.equals(RequestFilterType.PRE_MATCH)) {
            throw new IllegalArgumentException("Pre-match filters cannot have their priority set");
        }

        this.priority = priority;

        return this;
    }

    public int priority() {
        return priority;
    }

    public RequestFilterOptions label(final String label) {
        this.label = label;
        return this;
    }

    public String label() {
        return label;
    }
}
