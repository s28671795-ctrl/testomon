/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.HTTP;

import com.incoresoft.Utils.HTTP.HttpRequest;

public class HTTP {
    public static HttpRequest get(String url2) {
        return new HttpRequest("GET", url2);
    }

    public static HttpRequest post(String url2) {
        return new HttpRequest("POST", url2);
    }

    public static HttpRequest patch(String url2) {
        return new HttpRequest("POST", url2).header("X-HTTP-Method-Override", "PATCH");
    }

    public static HttpRequest delete(String url2) {
        return new HttpRequest("DELETE", url2);
    }
}

