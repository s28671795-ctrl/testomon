/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPServerStatusResponse
extends IMAPUntaggedResponse {
    protected String message;

    public IMAPServerStatusResponse(String keyword, String message, byte[] response) {
        super(keyword, response);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

