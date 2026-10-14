/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPMessageSize
extends IMAPFetchDataItem {
    public int size;

    public IMAPMessageSize(IMAPResponseTokenizer source2) throws MessagingException {
        super(5);
        this.size = source2.readInteger();
    }
}

