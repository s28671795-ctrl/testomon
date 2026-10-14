/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.CommunicationException;

public class HttpCommunicationException
extends CommunicationException {
    private static final long serialVersionUID = -7972641539531738263L;
    private final int code;
    private final String body;

    public HttpCommunicationException(int code, String body2) {
        super("Failed to retrieve data / failed code : " + code + " and body : " + body2);
        this.code = code;
        this.body = body2;
    }

    public HttpCommunicationException(Throwable t2) {
        super(t2);
        this.code = 0;
        this.body = null;
    }

    public HttpCommunicationException(String message) {
        super(message);
        this.code = 0;
        this.body = null;
    }

    public int getCode() {
        return this.code;
    }

    public String getBody() {
        return this.body;
    }
}

