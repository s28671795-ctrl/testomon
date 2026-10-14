/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodySection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchBodyPart;

public class IMAPBody
extends IMAPFetchBodyPart {
    byte[] content = null;

    public IMAPBody(byte[] data) throws MessagingException {
        this(new IMAPBodySection(0), data);
    }

    public IMAPBody(IMAPBodySection section, byte[] data) throws MessagingException {
        super(2, section);
        this.content = data;
    }

    public byte[] getContent() {
        return this.content;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.content);
    }
}

