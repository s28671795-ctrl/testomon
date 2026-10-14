/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.List;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPOkResponse
extends IMAPUntaggedResponse {
    protected List status;
    protected String message;

    public IMAPOkResponse(String keyword, List status, String message, byte[] response) {
        super(keyword, response);
        this.status = status;
        this.message = message;
    }

    public String getResponseCode() {
        return this.getKeyword();
    }

    public List getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}

