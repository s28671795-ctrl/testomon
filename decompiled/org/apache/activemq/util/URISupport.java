/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URISupport {
    public static Map<String, String> parseQuery(String uri) throws URISyntaxException {
        try {
            uri = uri.substring(uri.lastIndexOf("?") + 1);
            HashMap<String, String> rc = new HashMap<String, String>();
            if (uri != null && !uri.isEmpty()) {
                String[] parameters = uri.split("&");
                for (int i = 0; i < parameters.length; ++i) {
                    int p = parameters[i].indexOf("=");
                    if (p >= 0) {
                        String name = URLDecoder.decode(parameters[i].substring(0, p), "UTF-8");
                        String value = URLDecoder.decode(parameters[i].substring(p + 1), "UTF-8");
                        rc.put(name, value);
                        continue;
                    }
                    rc.put(parameters[i], null);
                }
            }
            return rc;
        }
        catch (UnsupportedEncodingException e) {
            throw (URISyntaxException)new URISyntaxException(e.toString(), "Invalid encoding").initCause(e);
        }
    }

    public static Map<String, String> parseParameters(URI uri) throws URISyntaxException {
        if (!URISupport.isCompositeURI(uri)) {
            return uri.getQuery() == null ? URISupport.emptyMap() : URISupport.parseQuery(URISupport.stripPrefix(uri.getQuery(), "?"));
        }
        CompositeData data = URISupport.parseComposite(uri);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.putAll(data.getParameters());
        if (parameters.isEmpty()) {
            parameters = URISupport.emptyMap();
        }
        return parameters;
    }

    public static URI applyParameters(URI uri, Map<String, String> queryParameters) throws URISyntaxException {
        return URISupport.applyParameters(uri, queryParameters, "");
    }

    public static URI applyParameters(URI uri, Map<String, String> queryParameters, String optionPrefix) throws URISyntaxException {
        if (queryParameters != null && !queryParameters.isEmpty()) {
            StringBuffer newQuery = uri.getRawQuery() != null ? new StringBuffer(uri.getRawQuery()) : new StringBuffer();
            for (Map.Entry<String, String> param2 : queryParameters.entrySet()) {
                if (!param2.getKey().startsWith(optionPrefix)) continue;
                if (newQuery.length() != 0) {
                    newQuery.append('&');
                }
                String key = param2.getKey().substring(optionPrefix.length());
                newQuery.append(key).append('=').append(param2.getValue());
            }
            uri = URISupport.createURIWithQuery(uri, newQuery.toString());
        }
        return uri;
    }

    private static Map<String, String> emptyMap() {
        return Collections.EMPTY_MAP;
    }

    public static URI removeQuery(URI uri) throws URISyntaxException {
        return URISupport.createURIWithQuery(uri, null);
    }

    public static URI createURIWithQuery(URI uri, String query) throws URISyntaxException {
        String schemeSpecificPart = uri.getRawSchemeSpecificPart();
        int questionMark = schemeSpecificPart.lastIndexOf("?");
        if (questionMark < schemeSpecificPart.lastIndexOf(")")) {
            questionMark = -1;
        }
        if (questionMark > 0) {
            schemeSpecificPart = schemeSpecificPart.substring(0, questionMark);
        }
        if (query != null && query.length() > 0) {
            schemeSpecificPart = schemeSpecificPart + "?" + query;
        }
        return new URI(uri.getScheme(), schemeSpecificPart, uri.getFragment());
    }

    public static CompositeData parseComposite(URI uri) throws URISyntaxException {
        CompositeData rc = new CompositeData();
        rc.scheme = uri.getScheme();
        String ssp = URISupport.stripPrefix(uri.getRawSchemeSpecificPart().trim(), "//").trim();
        URISupport.parseComposite(uri, rc, ssp);
        rc.fragment = uri.getFragment();
        return rc;
    }

    public static boolean isCompositeURI(URI uri) {
        String ssp = URISupport.stripPrefix(uri.getRawSchemeSpecificPart().trim(), "//").trim();
        return ssp.indexOf(40) == 0 && URISupport.checkParenthesis(ssp);
    }

    public static int indexOfParenthesisMatch(String str, int first) throws URISyntaxException {
        int index = -1;
        if (first < 0 || first > str.length()) {
            throw new IllegalArgumentException("Invalid position for first parenthesis: " + first);
        }
        if (str.charAt(first) != '(') {
            throw new IllegalArgumentException("character at indicated position is not a parenthesis");
        }
        int depth = 1;
        char[] array = str.toCharArray();
        for (index = first + 1; index < array.length; ++index) {
            char current = array[index];
            if (current == '(') {
                ++depth;
                continue;
            }
            if (current == ')' && --depth == 0) break;
        }
        if (depth != 0) {
            throw new URISyntaxException(str, "URI did not contain a matching parenthesis.");
        }
        return index;
    }

    private static void parseComposite(URI uri, CompositeData rc, String ssp) throws URISyntaxException {
        String params;
        String componentString;
        int p;
        if (!URISupport.checkParenthesis(ssp)) {
            throw new URISyntaxException(uri.toString(), "Not a matching number of '(' and ')' parenthesis");
        }
        int initialParen = ssp.indexOf("(");
        if (initialParen == 0) {
            rc.host = ssp.substring(0, initialParen);
            p = rc.host.indexOf("/");
            if (p >= 0) {
                rc.path = rc.host.substring(p);
                rc.host = rc.host.substring(0, p);
            }
            p = URISupport.indexOfParenthesisMatch(ssp, initialParen);
            componentString = ssp.substring(initialParen + 1, p);
            params = ssp.substring(p + 1).trim();
        } else {
            componentString = ssp;
            params = "";
        }
        String[] components = URISupport.splitComponents(componentString);
        CompositeData.access$402(rc, new URI[components.length]);
        for (int i = 0; i < components.length; ++i) {
            ((CompositeData)rc).components[i] = new URI(components[i].trim());
        }
        p = params.indexOf("?");
        if (p >= 0) {
            if (p > 0) {
                rc.path = URISupport.stripPrefix(params.substring(0, p), "/");
            }
            rc.parameters = URISupport.parseQuery(params.substring(p + 1));
        } else {
            if (params.length() > 0) {
                rc.path = URISupport.stripPrefix(params, "/");
            }
            rc.parameters = URISupport.emptyMap();
        }
    }

    private static String[] splitComponents(String str) {
        ArrayList<String> l = new ArrayList<String>();
        int last = 0;
        int depth = 0;
        char[] chars = str.toCharArray();
        block5: for (int i = 0; i < chars.length; ++i) {
            switch (chars[i]) {
                case '(': {
                    ++depth;
                    continue block5;
                }
                case ')': {
                    --depth;
                    continue block5;
                }
                case ',': {
                    if (depth != 0) continue block5;
                    String s2 = str.substring(last, i);
                    l.add(s2);
                    last = i + 1;
                    continue block5;
                }
            }
        }
        String s3 = str.substring(last);
        if (s3.length() != 0) {
            l.add(s3);
        }
        String[] rc = new String[l.size()];
        l.toArray(rc);
        return rc;
    }

    public static String stripPrefix(String value, String prefix) {
        if (value.startsWith(prefix)) {
            return value.substring(prefix.length());
        }
        return value;
    }

    public static URI stripScheme(URI uri) throws URISyntaxException {
        return new URI(URISupport.stripPrefix(uri.getSchemeSpecificPart().trim(), "//"));
    }

    public static String createQueryString(Map<String, ? extends Object> options) throws URISyntaxException {
        try {
            if (options.size() > 0) {
                StringBuffer rc = new StringBuffer();
                boolean first = true;
                for (String key : options.keySet()) {
                    if (first) {
                        first = false;
                    } else {
                        rc.append("&");
                    }
                    String value = (String)options.get(key);
                    rc.append(URLEncoder.encode(key, "UTF-8"));
                    rc.append("=");
                    rc.append(URLEncoder.encode(value, "UTF-8"));
                }
                return rc.toString();
            }
            return "";
        }
        catch (UnsupportedEncodingException e) {
            throw (URISyntaxException)new URISyntaxException(e.toString(), "Invalid encoding").initCause(e);
        }
    }

    public static URI createRemainingURI(URI originalURI, Map<String, String> params) throws URISyntaxException {
        String s2 = URISupport.createQueryString(params);
        if (s2.length() == 0) {
            s2 = null;
        }
        return URISupport.createURIWithQuery(originalURI, s2);
    }

    public static URI changeScheme(URI bindAddr, String scheme2) throws URISyntaxException {
        return new URI(scheme2, bindAddr.getUserInfo(), bindAddr.getHost(), bindAddr.getPort(), bindAddr.getPath(), bindAddr.getQuery(), bindAddr.getFragment());
    }

    public static boolean checkParenthesis(String str) {
        boolean result = true;
        if (str != null) {
            int open = 0;
            int closed = 0;
            int i = 0;
            while ((i = str.indexOf(40, i)) >= 0) {
                ++i;
                ++open;
            }
            i = 0;
            while ((i = str.indexOf(41, i)) >= 0) {
                ++i;
                ++closed;
            }
            result = open == closed;
        }
        return result;
    }

    public static class CompositeData {
        private String host;
        private String scheme;
        private String path;
        private URI[] components;
        private Map<String, String> parameters;
        private String fragment;

        public URI[] getComponents() {
            return this.components;
        }

        public String getFragment() {
            return this.fragment;
        }

        public Map<String, String> getParameters() {
            return this.parameters;
        }

        public String getScheme() {
            return this.scheme;
        }

        public String getPath() {
            return this.path;
        }

        public String getHost() {
            return this.host;
        }

        public URI toURI() throws URISyntaxException {
            StringBuffer sb = new StringBuffer();
            if (this.scheme != null) {
                sb.append(this.scheme);
                sb.append(':');
            }
            if (this.host != null && this.host.length() != 0) {
                sb.append(this.host);
            } else {
                sb.append('(');
                for (int i = 0; i < this.components.length; ++i) {
                    if (i != 0) {
                        sb.append(',');
                    }
                    sb.append(this.components[i].toString());
                }
                sb.append(')');
            }
            if (this.path != null) {
                sb.append('/');
                sb.append(this.path);
            }
            if (!this.parameters.isEmpty()) {
                sb.append("?");
                sb.append(URISupport.createQueryString(this.parameters));
            }
            if (this.fragment != null) {
                sb.append("#");
                sb.append(this.fragment);
            }
            return new URI(sb.toString());
        }

        static /* synthetic */ URI[] access$402(CompositeData x0, URI[] x1) {
            x0.components = x1;
            return x1;
        }
    }
}

