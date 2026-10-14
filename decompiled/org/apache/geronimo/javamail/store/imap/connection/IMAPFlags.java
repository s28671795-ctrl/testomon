/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.Flags;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPFlags
extends IMAPFetchDataItem {
    public Flags flags;

    public IMAPFlags(IMAPResponseTokenizer source2) throws MessagingException {
        super(9);
        this.flags = source2.readFlagList();
    }
}

