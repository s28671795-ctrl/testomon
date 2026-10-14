/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.async.methods;

import java.net.URI;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.message.BasicHttpRequest;

@Deprecated
public final class BasicHttpRequests {
    public static BasicHttpRequest create(String method2, String uri) {
        return BasicHttpRequests.create(Method.normalizedValueOf(method2), uri);
    }

    public static BasicHttpRequest create(String method2, URI uri) {
        return BasicHttpRequests.create(Method.normalizedValueOf(method2), uri);
    }

    public static BasicHttpRequest delete(String uri) {
        return BasicHttpRequests.delete(URI.create(uri));
    }

    public static BasicHttpRequest delete(URI uri) {
        return BasicHttpRequests.create(Method.DELETE, uri);
    }

    public static BasicHttpRequest delete(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.DELETE, host2, path);
    }

    public static BasicHttpRequest get(String uri) {
        return BasicHttpRequests.get(URI.create(uri));
    }

    public static BasicHttpRequest get(URI uri) {
        return BasicHttpRequests.create(Method.GET, uri);
    }

    public static BasicHttpRequest get(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.GET, host2, path);
    }

    public static BasicHttpRequest head(String uri) {
        return BasicHttpRequests.head(URI.create(uri));
    }

    public static BasicHttpRequest head(URI uri) {
        return BasicHttpRequests.create(Method.HEAD, uri);
    }

    public static BasicHttpRequest head(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.HEAD, host2, path);
    }

    public static BasicHttpRequest options(String uri) {
        return BasicHttpRequests.options(URI.create(uri));
    }

    public static BasicHttpRequest options(URI uri) {
        return BasicHttpRequests.create(Method.OPTIONS, uri);
    }

    public static BasicHttpRequest options(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.OPTIONS, host2, path);
    }

    public static BasicHttpRequest patch(String uri) {
        return BasicHttpRequests.patch(URI.create(uri));
    }

    public static BasicHttpRequest patch(URI uri) {
        return BasicHttpRequests.create(Method.PATCH, uri);
    }

    public static BasicHttpRequest patch(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.PATCH, host2, path);
    }

    public static BasicHttpRequest post(String uri) {
        return BasicHttpRequests.post(URI.create(uri));
    }

    public static BasicHttpRequest post(URI uri) {
        return BasicHttpRequests.create(Method.POST, uri);
    }

    public static BasicHttpRequest post(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.POST, host2, path);
    }

    public static BasicHttpRequest put(String uri) {
        return BasicHttpRequests.put(URI.create(uri));
    }

    public static BasicHttpRequest put(URI uri) {
        return BasicHttpRequests.create(Method.PUT, uri);
    }

    public static BasicHttpRequest put(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.PUT, host2, path);
    }

    public static BasicHttpRequest trace(String uri) {
        return BasicHttpRequests.trace(URI.create(uri));
    }

    public static BasicHttpRequest trace(URI uri) {
        return BasicHttpRequests.create(Method.TRACE, uri);
    }

    public static BasicHttpRequest trace(HttpHost host2, String path) {
        return BasicHttpRequests.create(Method.TRACE, host2, path);
    }

    public static BasicHttpRequest create(Method method2, String uri) {
        return BasicHttpRequests.create(method2, URI.create(uri));
    }

    public static BasicHttpRequest create(Method method2, URI uri) {
        return new BasicHttpRequest(method2, uri);
    }

    public static BasicHttpRequest create(Method method2, HttpHost host2, String path) {
        return new BasicHttpRequest(method2, host2, path);
    }
}

