/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.ByteArrayInputStream;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodySection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchBodyPart;

public class IMAPInternetHeader
extends IMAPFetchBodyPart {
    public InternetHeaders headers;

    public IMAPInternetHeader(byte[] data) throws MessagingException {
        this(new IMAPBodySection(1), data);
    }

    public IMAPInternetHeader(IMAPBodySection section, byte[] data) throws MessagingException {
        super(8, section);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        this.headers = new InternetHeaders(in);
    }

    public boolean isComplete() {
        return this.section.section == 1;
    }
}

