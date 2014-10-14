package org.huysamen.vertx.ext.rest.http.type;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * This class implements the Media Type (MIME) to enable easier resource routing.
 *
 * @author <a href="http://nico.huysamen.org">Nicolaas Frederick Huysamen</a>
 */
public class MediaType {

    public static final MediaType ANY = new MediaType("*/*");

    private String type = "*";
    private String subType = "*";
    private double quality = 1.0;

    private final Map<String, String> parameters = new TreeMap<>();

    public MediaType(String header) {
        header = StringUtils.trim(header);

        if (StringUtils.isBlank(header)) {
            // Our work here is done
            return;
        }

        // Must have type and subtype
        if (!StringUtils.contains(header, "/")) {
            throw new IllegalArgumentException("Accept header must specify type and subtype");
        }

        // Add ';' if not present to ease parsing
        if (!StringUtils.contains(header, ";")) {
            header += ";";
        }

        // RFC 2616 Section 3.7: The type, subtype, and parameter attribute names are case-insensitive.
        type = trimmedSubstringBefore(header, "/").toLowerCase();
        subType = trimmedSubstringBetween(header, "/", ";").toLowerCase();

        // If no subtype, accept all
        if (StringUtils.isBlank(subType)) {
            subType = "*";
        }

        if ("*".equals(type) && !"*".equals(subType)) {
            throw new IllegalArgumentException("Cannot create MediaType with wildcard type and specific sub-type");
        }

        trimmedTokensAfter(header, ";")
                .stream()
                .forEach((parameter) -> {
                    // RFC 2616 Section 3.7: The type, subtype, and parameter attribute names are case-insensitive.
                    parameter[0] = parameter[0].toLowerCase();

                    // First, we check and parse the quality score
                    if (StringUtils.equals(parameter[0], "q")) {
                        try {
                            quality = Double.parseDouble(parameter[1]);
                        } catch (final Exception e) {
                            throw new IllegalArgumentException("Cannot parse media type quality score into double format [" + parameter[1] + "]");
                        }
                    } else {
                        parameters.put(parameter[0], parameter[1]);
                    }
                });
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public double getQuality() {
        return quality;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String toResponseString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(type).append("/").append(subType);

        for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
            sb.append("; ").append(parameter.getKey()).append("=").append(parameter.getValue());
        }

        return sb.toString();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Equals & HashCode
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MediaType mediaType = (MediaType) o;

        if (!parameters.equals(mediaType.parameters)) return false;
        if (!subType.equals(mediaType.subType)) return false;
        if (!type.equals(mediaType.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + subType.hashCode();
        result = 31 * result + parameters.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(type).append("/").append(subType).append("; q=" + quality);

        for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
            sb.append("; ").append(parameter.getKey()).append("=").append(parameter.getValue());
        }

        return sb.toString();
    }

    private static String trimmedSubstringBefore(final String input, final String token) {
        return StringUtils.trim(StringUtils.substringBefore(input, token));
    }

    private static String trimmedSubstringBetween(final String input, final String open, final String close) {
        return StringUtils.trim(StringUtils.substringBetween(input, open, close));
    }

    private static List<String[]> trimmedTokensAfter(final String input, final String token) {
        final String parameterString = StringUtils.trim(StringUtils.substringAfter(input, token));
        final List<String[]> parameterList = new ArrayList<>();

        if (StringUtils.isBlank(parameterString)) {
            return parameterList;
        }

        final String[] parameters = StringUtils.split(parameterString, token);

        if (parameters == null || parameters.length == 0) {
            return parameterList;
        }

        for (final String parameter : parameters) {
            final String[] tuple = StringUtils.split(parameter, "=");

            if (tuple == null || tuple.length != 2) {
                throw new IllegalArgumentException("Incorrect parameter format specified in media type [" + parameter + "]");
            }

            // RFC 2616 Section 3.7: The type, subtype, and parameter attribute names are case-insensitive.
            final String key = StringUtils.trim(tuple[0]).toLowerCase();
            final String value = StringUtils.trim(tuple[1]);

            if (StringUtils.isAnyBlank(key, value)) {
                throw new IllegalArgumentException("Incorrect parameter format specified in media type [" + parameter + "]");
            }

            parameterList.add(new String[] {key, value});
        }

        return parameterList;
    }
}
