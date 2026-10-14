/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.IMAPMessage;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;
import org.apache.geronimo.javamail.store.imap.connection.IMAPEnvelope;

public class IMAPAttachedMessage
extends IMAPMessage {
    protected IMAPMessage parent;

    public IMAPAttachedMessage(IMAPMessage parent, String section, IMAPEnvelope envelope, IMAPBodyStructure bodyStructure) {
        super((IMAPFolder)parent.getFolder(), parent.store, parent.getMessageNumber(), parent.sequenceNumber);
        this.parent = parent;
        this.section = section;
        this.envelope = envelope;
        this.bodyStructure = bodyStructure;
    }

    protected void checkValidity() throws MessagingException {
        this.parent.checkValidity();
    }

    public boolean isExpunged() {
        return this.parent.isExpunged();
    }

    public int getSize() {
        return this.bodyStructure.bodySize;
    }

    public Flags getFlags() throws MessagingException {
        return this.parent.getFlags();
    }

    public boolean isSet(Flags.Flag flag) throws MessagingException {
        return this.parent.isSet(flag);
    }

    public void setFlags(Flags flag, boolean set) throws MessagingException {
        throw new MethodNotSupportedException("Flags cannot be set on message attachements");
    }
}

