/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.Date;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPInternalDate
extends IMAPFetchDataItem {
    protected Date date;

    public IMAPInternalDate(IMAPResponseTokenizer source2) throws MessagingException {
        super(4);
        this.date = source2.readDate();
    }

    public Date getDate() {
        return this.date;
    }
}

