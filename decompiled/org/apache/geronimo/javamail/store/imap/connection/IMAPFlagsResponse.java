/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.Flags;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPFlagsResponse
extends IMAPUntaggedResponse {
    protected Flags flags = new Flags();

    public IMAPFlagsResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("FLAGS", data);
        this.flags = source2.readFlagList();
    }

    public Flags getFlags() {
        return this.flags;
    }
}

