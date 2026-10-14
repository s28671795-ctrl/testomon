/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;
import org.apache.geronimo.javamail.store.imap.IMAPMessage;
import org.apache.geronimo.javamail.store.imap.IMAPMimeBodyPart;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;

public class IMAPMultipartDataSource
extends MimePartDataSource
implements MultipartDataSource {
    protected BodyPart[] parts;

    IMAPMultipartDataSource(IMAPMessage message, MimePart parent, String section, IMAPBodyStructure bodyStructure) {
        super(parent);
        this.parts = new BodyPart[bodyStructure.parts.length];
        String sectionBase = section == null ? "" : section + ".";
        for (int i = 0; i < this.parts.length; ++i) {
            this.parts[i] = new IMAPMimeBodyPart(message, bodyStructure.parts[i], sectionBase + (i + 1));
        }
    }

    public int getCount() {
        return this.parts.length;
    }

    public BodyPart getBodyPart(int index) throws MessagingException {
        return this.parts[index];
    }
}

