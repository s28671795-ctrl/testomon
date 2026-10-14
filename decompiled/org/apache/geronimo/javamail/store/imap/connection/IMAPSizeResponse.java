/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPSizeResponse
extends IMAPUntaggedResponse {
    protected int size;

    public IMAPSizeResponse(String keyword, int size, byte[] response) {
        super(keyword, response);
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}

