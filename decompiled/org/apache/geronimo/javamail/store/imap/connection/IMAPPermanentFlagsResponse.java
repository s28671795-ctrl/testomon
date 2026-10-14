/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.Flags;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPPermanentFlagsResponse
extends IMAPUntaggedResponse {
    public Flags flags;

    public IMAPPermanentFlagsResponse(byte[] response, IMAPResponseTokenizer source2) throws MessagingException {
        super("PERMANENTFLAGS", response);
        this.flags = source2.readFlagList();
    }
}

