/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.exceptions.http;

public class HttpRequestFailedException
extends RuntimeException {
    public HttpRequestFailedException(String message) {
        super(message);
    }

    public HttpRequestFailedException(Throwable cause) {
        super(cause);
    }
}

