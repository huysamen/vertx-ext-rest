package org.huysamen.vertx.ext.rest.http.filter;

/**
 * This class defines the options available when creating a response filter.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class ResponseFilterOptions {

    private int priority;
    private String label;

    public ResponseFilterOptions() {
        priority = Integer.MAX_VALUE;
        label = "";
    }

    public ResponseFilterOptions priority(final int priority) {
        this.priority = priority;
        return this;
    }

    public int priority() {
        return priority;
    }

    public ResponseFilterOptions label(final String label) {
        this.label = label;
        return this;
    }

    public String label() {
        return label;
    }
}
