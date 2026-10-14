/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPResponse;
import org.apache.geronimo.mail.util.Base64;

public class IMAPTaggedResponse
extends IMAPResponse {
    protected String status;
    protected String tag;
    protected String message;

    public IMAPTaggedResponse(String tag, String status, String message, byte[] response) {
        super(response);
        this.tag = tag;
        this.status = status;
        this.message = message;
    }

    public IMAPTaggedResponse(byte[] response) {
        super(response);
        this.tag = "";
        this.status = "CONTINUATION";
        this.message = this.message;
    }

    public boolean isOK() {
        return this.status.equals("OK");
    }

    public boolean isBAD() {
        return this.status.equals("BAD");
    }

    public boolean isNO() {
        return this.status.equals("NO");
    }

    public String getMessage() {
        return this.message;
    }

    public byte[] decodeChallengeResponse() {
        return Base64.decode(this.response, 2, this.response.length - 2);
    }

    public boolean isContinuation() {
        return this.status.equals("CONTINUATION");
    }

    public boolean hasStatus(String name) {
        int statusStart = this.message.indexOf(91);
        if (statusStart == -1) {
            return false;
        }
        int statusEnd = this.message.indexOf(93);
        String statusString = this.message.substring(statusStart, statusEnd).toUpperCase();
        return statusString.indexOf(name) != -1;
    }
}

