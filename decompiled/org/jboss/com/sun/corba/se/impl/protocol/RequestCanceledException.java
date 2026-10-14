/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol;

public class RequestCanceledException
extends RuntimeException {
    private static final long serialVersionUID = 3280453653794663325L;
    private int requestId = 0;

    public RequestCanceledException(int requestId) {
        this.requestId = requestId;
    }

    public int getRequestId() {
        return this.requestId;
    }
}

