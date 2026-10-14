/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.Rights;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPMyRightsResponse
extends IMAPUntaggedResponse {
    public String mailbox;
    public Rights rights;

    public IMAPMyRightsResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("MYRIGHTS", data);
        this.mailbox = source2.readEncodedString();
        this.rights = new Rights(source2.readString());
    }
}

