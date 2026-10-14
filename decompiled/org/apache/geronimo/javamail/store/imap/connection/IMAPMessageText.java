/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodySection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchBodyPart;

public class IMAPMessageText
extends IMAPFetchBodyPart {
    protected byte[] data;

    public IMAPMessageText(byte[] data) throws MessagingException {
        this(new IMAPBodySection(4), data);
    }

    public IMAPMessageText(IMAPBodySection section, byte[] data) throws MessagingException {
        super(7, section);
        this.data = data;
    }

    public byte[] getContent() {
        return this.data;
    }
}

