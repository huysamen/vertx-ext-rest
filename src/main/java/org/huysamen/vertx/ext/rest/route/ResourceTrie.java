package org.huysamen.vertx.ext.rest.route;

import io.vertx.core.Handler;
import org.apache.commons.lang3.StringUtils;
import org.huysamen.vertx.ext.rest.http.net.RestRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Trie implementation adapted for route matching.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class ResourceTrie {

    private final ResourceTrieNode root = new ResourceTrieNode();

    public void addResource(final String path, final ResourceOptions options, final Handler<RestRequest> handler) {
        final String[] sections = path.trim().replaceAll("/+", "/").split("/");
        final List<String> parameters = new ArrayList<>();

        ResourceTrieNode parent;
        ResourceTrieNode current = root;

        if (sections.length == 0 || (sections.length == 1 && sections[0].equals(""))) {
            root.addHandler(options, parameters, handler);
            return;
        }

        for (int i = 0; i < sections.length; i++) {
            String section = sections[i].trim().toLowerCase();

            if (StringUtils.isBlank(section)) {
                continue;
            }

            if (section.startsWith(":")) {
                if (parameters.contains(section)) {
                    throw new IllegalArgumentException("Duplicate named path parameters detected");
                }

                parameters.add(section);
                section = "?";
            }

            parent = current;
            current = current.children.get(section);

            if (current == null) {
                current = new ResourceTrieNode();
                parent.children.put(section, current);
            }

            if (i == sections.length - 1) {
                current.addHandler(options, parameters, handler);
            }
        }
    }

    public Resource match(final RestRequest request) {
        final List<String> parameters = new ArrayList<>();
        final String[] sections = request.path().trim().replaceAll("/+", "/").split("/");

        ResourceTrieNode node = root;
        ResourceTrieNode parent;

        if (sections.length == 0 || (sections.length == 1 && sections[0].equals(""))) {
            return root.match(request);
        }

        for (int i = 0; i < sections.length; i++) {
            String section = sections[i];

            if (StringUtils.isBlank(section)) {
                continue;
            }

            parent = node;
            node = node.children.get(section);

            if (node == null) {
                if (parent.children.containsKey("?")) {
                    parameters.add(section);
                    node = parent.children.get("?");
                } else {
                    return null;
                }
            }

            if (i == sections.length - 1) {
                if (node.parameters.size() == parameters.size()) {
                    for (int p = 0; p < node.parameters.size(); p++) {
                        if (request.params().contains(node.parameters.get(p))) {
                            request.params().add(node.parameters.get(p).substring(1), parameters.get(p));
                        } else {
                            request.params().set(node.parameters.get(p).substring(1), parameters.get(p));
                        }
                    }

                    return node.match(request);
                } else {
                    return null;
                }
            }
        }

        return null;
    }
}
