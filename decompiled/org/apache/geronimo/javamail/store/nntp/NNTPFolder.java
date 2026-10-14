/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Session;
import org.apache.geronimo.javamail.store.nntp.NNTPStore;
import org.apache.geronimo.javamail.transport.nntp.NNTPConnection;

public class NNTPFolder
extends Folder {
    protected NNTPConnection connection;
    protected Session session;
    protected String name;
    protected String fullName;
    protected Folder parent;
    protected boolean folderOpen = false;
    protected int messageCount = 0;
    protected Flags permanentFlags;

    protected NNTPFolder(NNTPStore store) {
        super(store);
        this.connection = store.getConnection();
        this.session = store.getSession();
        this.permanentFlags = new Flags();
        this.permanentFlags.add(Flags.Flag.SEEN);
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Folder getParent() throws MessagingException {
        return this.parent;
    }

    public boolean exists() throws MessagingException {
        return true;
    }

    public Folder[] list(String pattern) throws MessagingException {
        throw new MethodNotSupportedException("NNTP group folders cannot contain sub folders");
    }

    public Folder[] listSubscribed(String pattern) throws MessagingException {
        throw new MethodNotSupportedException("NNTP group folders cannot contain sub folders");
    }

    public char getSeparator() throws MessagingException {
        return '\u0000';
    }

    public int getType() throws MessagingException {
        return 1;
    }

    public boolean create(int type) throws MessagingException {
        throw new MethodNotSupportedException("Sub folders cannot be created in NNTP");
    }

    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    public Folder getFolder(String name) throws MessagingException {
        throw new MethodNotSupportedException("NNTP Group folders do not support sub folders");
    }

    public boolean delete(boolean recurse) throws MessagingException {
        throw new MethodNotSupportedException("Deleting of NNTP folders is not supported");
    }

    public boolean renameTo(Folder f) throws MessagingException {
        throw new MethodNotSupportedException("Renaming of NNTP folders is not supported.");
    }

    public void open(int mode) throws MessagingException {
        if (mode == 2) {
            throw new IllegalWriteException("Newsgroup folders cannot be opened read/write");
        }
        this.checkClosed();
        this.mode = mode;
        this.openFolder();
        this.folderOpen = true;
        this.notifyConnectionListeners(1);
    }

    protected void openFolder() throws MessagingException {
    }

    protected void closeFolder() throws MessagingException {
    }

    public void close(boolean expunge) throws MessagingException {
        this.checkOpen();
        this.closeFolder();
        this.folderOpen = false;
        this.notifyConnectionListeners(3);
    }

    public boolean isOpen() {
        return this.folderOpen;
    }

    public Flags getPermanentFlags() {
        return new Flags(this.permanentFlags);
    }

    public int getMessageCount() throws MessagingException {
        return this.messageCount;
    }

    public Message getMessage(int msgNum) throws MessagingException {
        throw new MethodNotSupportedException("Root NNTP folder does not contain messages");
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
        throw new MethodNotSupportedException("Root NNTP folder does not contain messages");
    }

    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Root NNTP folder does not contain messages");
    }

    protected void checkOpen() throws IllegalStateException {
        if (!this.folderOpen) {
            throw new IllegalStateException("Folder is not Open");
        }
    }

    protected void checkClosed() throws IllegalStateException {
        if (this.folderOpen) {
            throw new IllegalStateException("Folder is Open");
        }
    }

    public void notifyMessageChangedListeners(int type, Message m4) {
        super.notifyMessageChangedListeners(type, m4);
    }

    public boolean isSubscribed() {
        return false;
    }

    public void setSubscribed(boolean flag) throws MessagingException {
        throw new MessagingException("Root NNTP folder cannot be subscribed to");
    }

    public boolean isSeen(int article) {
        return false;
    }

    public void setSeen(int article, boolean flag) throws MessagingException {
        throw new MessagingException("Root NNTP folder does not contain articles");
    }
}

