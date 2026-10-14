/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3;

import javax.mail.Folder;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.pop3.POP3Folder;
import org.apache.geronimo.javamail.store.pop3.POP3Store;

public class POP3RootFolder
extends POP3Folder {
    protected Folder inbox;

    public POP3RootFolder(POP3Store store) {
        super(store, "");
        this.folderType = 2;
        this.exists = true;
        this.msgCount = 0;
    }

    public Folder getParent() {
        return null;
    }

    public char getSeparator() throws MessagingException {
        return '/';
    }

    public Folder[] list(String pattern) throws MessagingException {
        return new Folder[]{this.getInbox()};
    }

    public Folder getFolder(String name) throws MessagingException {
        if (!name.equalsIgnoreCase("INBOX")) {
            throw new MessagingException("Only the INBOX folder is supported");
        }
        return this.getInbox();
    }

    public boolean isOpen() {
        return false;
    }

    public void open(int mode) throws MessagingException {
        throw new MessagingException("POP3 root folder cannot be opened");
    }

    public void open(boolean expunge) throws MessagingException {
        throw new MessagingException("POP3 root folder cannot be close");
    }

    protected Folder getInbox() throws MessagingException {
        if (this.inbox == null) {
            this.inbox = new POP3Folder((POP3Store)this.store, "INBOX");
        }
        return this.inbox;
    }
}

