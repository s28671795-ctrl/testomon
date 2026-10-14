/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.annotations.Beta;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@Beta
public final class URISupport {
    private URISupport() {
    }

    public static URI setFragment(URI prototype, String fragment) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), prototype.getQuery(), URISupport.trimOrNullFragment(fragment));
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal fragment text", e);
        }
    }

    public static URI setHost(URI prototype, String host2) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), StringSupport.trimOrNull(host2), prototype.getPort(), prototype.getPath(), prototype.getQuery(), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal host", e);
        }
    }

    public static URI setPath(URI prototype, String path) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), URISupport.trimOrNullPath(path), prototype.getQuery(), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal path", e);
        }
    }

    public static URI setPort(URI prototype, int port) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), port, prototype.getPath(), prototype.getQuery(), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal port", e);
        }
    }

    public static URI setQuery(URI prototype, String query) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), URISupport.trimOrNullQuery(query), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal query", e);
        }
    }

    public static URI setQuery(URI prototype, List<Pair<String, String>> parameters) {
        try {
            return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), URISupport.buildQuery(parameters), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal query", e);
        }
    }

    public static URI setScheme(URI prototype, String scheme2) {
        try {
            return new URI(StringSupport.trimOrNull(scheme2), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), prototype.getQuery(), prototype.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal scheme", e);
        }
    }

    public static URI fileURIFromAbsolutePath(String path) throws URISyntaxException {
        StringBuilder uriPath = new StringBuilder(path.length() + 8);
        uriPath.append("file://");
        if (!path.startsWith("/")) {
            uriPath.append('/');
        }
        uriPath.append(path);
        return new URI(uriPath.toString());
    }

    public static String buildQuery(List<Pair<String, String>> parameters) {
        if (parameters == null || parameters.size() == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean firstParam = true;
        for (Pair<String, String> parameter : parameters) {
            if (firstParam) {
                firstParam = false;
            } else {
                builder.append("&");
            }
            builder.append(URISupport.doURLEncode(parameter.getFirst()));
            builder.append("=");
            if (parameter.getSecond() == null) continue;
            builder.append(URISupport.doURLEncode(parameter.getSecond()));
        }
        return builder.toString();
    }

    @Nullable
    public static String getRawQueryStringParameter(@Nullable String queryString, @Nullable String paramName) {
        String[] candidates;
        String trimmedQuery = URISupport.trimOrNullQuery(queryString);
        String trimmedName = StringSupport.trimOrNull(paramName);
        if (trimmedQuery == null || trimmedName == null) {
            return null;
        }
        String encodedName = URISupport.doURLEncode(trimmedName);
        for (String candidate : candidates = trimmedQuery.split("&")) {
            if (!candidate.startsWith(encodedName + "=") && !candidate.equals(encodedName)) continue;
            return candidate;
        }
        return null;
    }

    public static List<Pair<String, String>> parseQueryString(String queryString) {
        String[] paramPairs;
        String trimmedQuery = URISupport.trimOrNullQuery(queryString);
        if (trimmedQuery == null) {
            return new LazyList<Pair<String, String>>();
        }
        ArrayList<Pair<String, String>> queryParams2 = new ArrayList<Pair<String, String>>();
        for (String paramPair : paramPairs = trimmedQuery.split("&")) {
            String[] param2 = paramPair.split("=");
            if (param2.length == 1) {
                queryParams2.add(new Pair<String, Object>(URISupport.doURLDecode(param2[0]), null));
                continue;
            }
            queryParams2.add(new Pair<String, String>(URISupport.doURLDecode(param2[0]), URISupport.doURLDecode(param2[1])));
        }
        return queryParams2;
    }

    public static String trimOrNullPath(String path) {
        String trimmedPath = StringSupport.trimOrNull(path);
        if (trimmedPath == null) {
            return null;
        }
        if (trimmedPath.startsWith("?")) {
            trimmedPath = trimmedPath.substring(1);
        }
        if (trimmedPath.endsWith("?") || trimmedPath.endsWith("#")) {
            trimmedPath = trimmedPath.substring(0, trimmedPath.length() - 1);
        }
        return trimmedPath;
    }

    public static String trimOrNullQuery(String query) {
        String trimmedQuery = StringSupport.trimOrNull(query);
        if (trimmedQuery == null) {
            return null;
        }
        if (trimmedQuery.startsWith("?")) {
            trimmedQuery = trimmedQuery.substring(1);
        }
        if (trimmedQuery.endsWith("#")) {
            trimmedQuery = trimmedQuery.substring(0, trimmedQuery.length() - 1);
        }
        return trimmedQuery;
    }

    public static String trimOrNullFragment(String fragment) {
        String trimmedFragment = StringSupport.trimOrNull(fragment);
        if (trimmedFragment == null) {
            return null;
        }
        if (trimmedFragment.startsWith("#")) {
            trimmedFragment = trimmedFragment.substring(1);
        }
        return trimmedFragment;
    }

    public static String doURLDecode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String doURLEncode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

