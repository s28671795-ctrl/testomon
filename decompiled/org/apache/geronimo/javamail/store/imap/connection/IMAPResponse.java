/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.UnsupportedEncodingException;

public class IMAPResponse {
    protected byte[] response;

    protected IMAPResponse(byte[] response) {
        this.response = response;
    }

    public byte[] getResponseData() {
        return this.response;
    }

    public String toString() {
        try {
            return new String(this.response, "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(this.response);
        }
    }
}

