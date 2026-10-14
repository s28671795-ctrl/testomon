/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.async.methods;

import java.net.URI;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.Method;

@Deprecated
public final class SimpleHttpRequests {
    public static SimpleHttpRequest create(String method2, String uri) {
        return SimpleHttpRequests.create(Method.normalizedValueOf(method2), uri);
    }

    public static SimpleHttpRequest create(String method2, URI uri) {
        return SimpleHttpRequests.create(Method.normalizedValueOf(method2), uri);
    }

    public static SimpleHttpRequest delete(String uri) {
        return SimpleHttpRequests.delete(URI.create(uri));
    }

    public static SimpleHttpRequest delete(URI uri) {
        return SimpleHttpRequests.create(Method.DELETE, uri);
    }

    public static SimpleHttpRequest delete(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.DELETE, host2, path);
    }

    public static SimpleHttpRequest get(String uri) {
        return SimpleHttpRequests.get(URI.create(uri));
    }

    public static SimpleHttpRequest get(URI uri) {
        return SimpleHttpRequests.create(Method.GET, uri);
    }

    public static SimpleHttpRequest get(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.GET, host2, path);
    }

    public static SimpleHttpRequest head(String uri) {
        return SimpleHttpRequests.head(URI.create(uri));
    }

    public static SimpleHttpRequest head(URI uri) {
        return SimpleHttpRequests.create(Method.HEAD, uri);
    }

    public static SimpleHttpRequest head(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.HEAD, host2, path);
    }

    public static SimpleHttpRequest options(String uri) {
        return SimpleHttpRequests.options(URI.create(uri));
    }

    public static SimpleHttpRequest options(URI uri) {
        return SimpleHttpRequests.create(Method.OPTIONS, uri);
    }

    public static SimpleHttpRequest options(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.OPTIONS, host2, path);
    }

    public static SimpleHttpRequest patch(String uri) {
        return SimpleHttpRequests.patch(URI.create(uri));
    }

    public static SimpleHttpRequest patch(URI uri) {
        return SimpleHttpRequests.create(Method.PATCH, uri);
    }

    public static SimpleHttpRequest patch(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.PATCH, host2, path);
    }

    public static SimpleHttpRequest post(String uri) {
        return SimpleHttpRequests.post(URI.create(uri));
    }

    public static SimpleHttpRequest post(URI uri) {
        return SimpleHttpRequests.create(Method.POST, uri);
    }

    public static SimpleHttpRequest post(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.POST, host2, path);
    }

    public static SimpleHttpRequest put(String uri) {
        return SimpleHttpRequests.put(URI.create(uri));
    }

    public static SimpleHttpRequest put(URI uri) {
        return SimpleHttpRequests.create(Method.PUT, uri);
    }

    public static SimpleHttpRequest put(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.PUT, host2, path);
    }

    public static SimpleHttpRequest trace(String uri) {
        return SimpleHttpRequests.trace(URI.create(uri));
    }

    public static SimpleHttpRequest trace(URI uri) {
        return SimpleHttpRequests.create(Method.TRACE, uri);
    }

    public static SimpleHttpRequest trace(HttpHost host2, String path) {
        return SimpleHttpRequests.create(Method.TRACE, host2, path);
    }

    public static SimpleHttpRequest create(Method method2, String uri) {
        return SimpleHttpRequests.create(method2, URI.create(uri));
    }

    public static SimpleHttpRequest create(Method method2, URI uri) {
        return new SimpleHttpRequest(method2, uri);
    }

    public static SimpleHttpRequest create(Method method2, HttpHost host2, String path) {
        return new SimpleHttpRequest(method2, host2, path);
    }
}

