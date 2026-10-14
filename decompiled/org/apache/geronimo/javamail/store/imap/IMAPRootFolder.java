/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.IMAPStore;

public class IMAPRootFolder
extends IMAPFolder {
    public IMAPRootFolder(IMAPStore store) {
        super(store, "", '/');
        this.folderType = 2;
    }

    public Folder getFolder(String name) throws MessagingException {
        return new IMAPFolder((IMAPStore)this.store, name, '\u0000');
    }

    public Folder getParent() {
        return null;
    }

    public boolean exists() throws MessagingException {
        return true;
    }

    public boolean hasNewMessages() {
        return false;
    }

    public int getMessagesCount() {
        return 0;
    }

    public int getNewMessagesCount() {
        return 0;
    }

    public int getUnreadMessagesCount() {
        return 0;
    }

    public int getDeletedMessagesCount() {
        return 0;
    }

    public boolean create(int newType) throws MessagingException {
        throw new MethodNotSupportedException("Default IMAP folder cannot be created");
    }

    public boolean delete(boolean recurse) throws MessagingException {
        throw new MethodNotSupportedException("Default IMAP folder cannot be deleted");
    }

    public boolean rename(boolean recurse) throws MessagingException {
        throw new MethodNotSupportedException("Default IMAP folder cannot be renamed");
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
        throw new MethodNotSupportedException("Messages cannot be appended to Default IMAP folder");
    }

    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Messages cannot be expunged from Default IMAP folder");
    }
}

