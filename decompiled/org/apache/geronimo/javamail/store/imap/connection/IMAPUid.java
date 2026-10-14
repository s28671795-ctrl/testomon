/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPUid
extends IMAPFetchDataItem {
    public long uid;
    public int messageNumber;

    public IMAPUid(int messageNumber, IMAPResponseTokenizer source2) throws MessagingException {
        super(6);
        this.messageNumber = messageNumber;
        this.uid = source2.readLong();
    }
}

