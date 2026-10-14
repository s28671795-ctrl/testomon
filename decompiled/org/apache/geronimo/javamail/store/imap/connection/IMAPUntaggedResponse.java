/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPResponse;

public class IMAPUntaggedResponse
extends IMAPResponse {
    protected String keyword;

    public IMAPUntaggedResponse(String keyword, byte[] response) {
        super(response);
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public boolean isKeyword(String keyword) {
        return this.keyword.equals(keyword);
    }
}

