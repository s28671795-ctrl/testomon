/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context;

public interface HttpConstants {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int FOUND = 302;
    public static final int SEE_OTHER = 303;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int BAD_REQUEST = 400;
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int DEFAULT_HTTPS_PORT = 443;
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final int DEFAULT_CONNECT_TIMEOUT = 500;
    public static final int DEFAULT_READ_TIMEOUT = 5000;
    public static final String LOCATION_HEADER = "Location";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BASIC_HEADER_PREFIX = "Basic ";
    public static final String BEARER_HEADER_PREFIX = "Bearer ";
    public static final String DIGEST_HEADER_PREFIX = "Digest ";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String APPLICATION_FORM_ENCODED_HEADER_VALUE = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String HTML_CONTENT_TYPE = "text/html; charset=utf-8";
    public static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
    public static final String AJAX_HEADER_NAME = "X-Requested-With";
    public static final String FACES_PARTIAL_AJAX_PARAMETER = "javax.faces.partial.ajax";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS_HEADER = "Access-Control-Expose-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE_HEADER = "Access-Control-Max-Age";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";

    public static enum HTTP_METHOD {
        GET,
        POST,
        HEAD,
        TRACE,
        PUT,
        DELETE,
        OPTIONS,
        PATCH;

    }
}

