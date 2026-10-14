/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import org.ldaptive.LdapUtils;
import org.ldaptive.Request;

public class EncodedRequest {
    private final int messageID;
    private final byte[] encoded;

    public EncodedRequest(int id, Request request) {
        this.messageID = id;
        this.encoded = request.encode(this.messageID);
    }

    public int getMessageID() {
        return this.messageID;
    }

    public byte[] getEncoded() {
        return this.encoded;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "messageID=" + this.messageID + ", " + "encoded=" + LdapUtils.hexEncode(this.encoded);
    }
}

