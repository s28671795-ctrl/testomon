/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPQuotaRootResponse
extends IMAPUntaggedResponse {
    public String mailbox;
    public List roots;

    public IMAPQuotaRootResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("QUOTAROOT", data);
        this.mailbox = source2.readEncodedString();
        this.roots = source2.readStrings();
    }
}

