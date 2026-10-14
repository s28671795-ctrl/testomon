/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import org.apache.geronimo.javamail.store.pop3.POP3Message;
import org.apache.geronimo.javamail.store.pop3.POP3Store;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Connection;
import org.apache.geronimo.javamail.store.pop3.connection.POP3StatusResponse;

public class POP3Folder
extends Folder {
    protected boolean isFolderOpen = false;
    protected int mode;
    protected int msgCount;
    private POP3Message[] messageCache;
    protected String fullName;
    protected boolean exists = false;
    protected int folderType;

    public POP3Folder(POP3Store store, String name) {
        super(store);
        this.fullName = name;
        if (name.equalsIgnoreCase("INBOX")) {
            this.exists = true;
        }
        this.folderType = 1;
    }

    public String getName() {
        return this.fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Folder getParent() throws MessagingException {
        return this.store.getDefaultFolder();
    }

    public boolean exists() throws MessagingException {
        return this.exists;
    }

    public Folder[] list(String pattern) throws MessagingException {
        throw new MethodNotSupportedException("Only INBOX is supported in POP3, no sub folders");
    }

    public char getSeparator() throws MessagingException {
        return '\u0000';
    }

    public int getType() throws MessagingException {
        return this.folderType;
    }

    public boolean create(int type) throws MessagingException {
        return false;
    }

    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    public Folder getFolder(String name) throws MessagingException {
        throw new MethodNotSupportedException("Only INBOX is supported in POP3, no sub folders");
    }

    public boolean delete(boolean recurse) throws MessagingException {
        throw new MethodNotSupportedException("Only INBOX is supported in POP3 and INBOX cannot be deleted");
    }

    public boolean renameTo(Folder f) throws MessagingException {
        throw new MethodNotSupportedException("Only INBOX is supported in POP3 and INBOX cannot be renamed");
    }

    public void open(int mode) throws MessagingException {
        this.checkClosed();
        POP3Connection connection = this.getConnection();
        try {
            POP3StatusResponse res = connection.retrieveMailboxStatus();
            this.mode = mode;
            this.isFolderOpen = true;
            this.msgCount = res.getNumMessages();
            this.messageCache = new POP3Message[this.msgCount];
        }
        catch (Exception e) {
            throw new MessagingException("Unable to execute STAT command", e);
        }
        finally {
            this.releaseConnection(connection);
        }
        this.notifyConnectionListeners(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close(boolean expunge) throws MessagingException {
        this.checkOpen();
        POP3Connection connection = this.getConnection();
        try {
            connection.reset();
            this.expungeDeletedMessages(connection);
        }
        finally {
            this.releaseConnection(connection);
            this.cleanupFolder(false);
        }
    }

    protected void expungeDeletedMessages(POP3Connection connection) throws MessagingException {
        if (this.mode == 2) {
            for (int i = 0; i < this.messageCache.length; ++i) {
                POP3Message msg = this.messageCache[i];
                if (msg == null || !msg.isSet(Flags.Flag.DELETED)) continue;
                try {
                    connection.deleteMessage(i + 1);
                    continue;
                }
                catch (MessagingException e) {
                    throw new MessagingException("Exception deleting message number " + (i + 1), e);
                }
            }
        }
    }

    protected void cleanupFolder(boolean disconnected) throws MessagingException {
        this.messageCache = null;
        this.isFolderOpen = false;
        this.notifyConnectionListeners(3);
    }

    synchronized POP3Connection getMessageConnection() throws MessagingException {
        return this.getConnection();
    }

    void releaseMessageConnection(POP3Connection connection) throws MessagingException {
        this.releaseConnection(connection);
    }

    public boolean isOpen() {
        if (!this.isFolderOpen) {
            return false;
        }
        try {
            if (!((POP3Store)this.store).isConnected()) {
                this.close(false);
            }
        }
        catch (MessagingException messagingException) {
            // empty catch block
        }
        return this.isFolderOpen;
    }

    public Flags getPermanentFlags() {
        return new Flags();
    }

    public int getMessageCount() throws MessagingException {
        return this.msgCount;
    }

    public Message getMessage(int msgNum) throws MessagingException {
        this.checkOpen();
        if (msgNum < 1 || msgNum > this.getMessageCount()) {
            throw new MessagingException("Invalid Message number");
        }
        POP3Message msg = this.messageCache[msgNum - 1];
        if (msg == null) {
            this.messageCache[msgNum - 1] = msg = new POP3Message(this, msgNum);
        }
        return msg;
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
        throw new MethodNotSupportedException("Message appending is not supported in POP3");
    }

    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Expunge is not supported in POP3");
    }

    public int getMode() throws IllegalStateException {
        this.checkOpen();
        return this.mode;
    }

    public synchronized void fetch(Message[] msgs, FetchProfile fp) throws MessagingException {
        this.checkOpen();
        for (int i = 0; i < msgs.length; ++i) {
            Message msg = msgs[i];
            if (fp.contains(FetchProfile.Item.ENVELOPE)) {
                msg.getHeader("Subject");
                msg.getSize();
            }
            if (fp.contains(FetchProfile.Item.CONTENT_INFO)) {
                ((POP3Message)msg).loadContent();
                msg.getSize();
            }
            if (fp.contains(FetchProfile.Item.FLAGS)) {
                msg.getFlags();
            }
            if (fp.getHeaderNames().length <= 0) continue;
            msg.getHeader("Subject");
        }
    }

    public synchronized String getUID(Message msg) throws MessagingException {
        this.checkOpen();
        return ((POP3Message)msg).getUID();
    }

    private void checkOpen() throws IllegalStateException {
        if (!this.isFolderOpen) {
            throw new IllegalStateException("Folder is not Open");
        }
    }

    private void checkClosed() throws IllegalStateException {
        if (this.isFolderOpen) {
            throw new IllegalStateException("Folder is Open");
        }
    }

    public void notifyMessageChangedListeners(int type, Message m4) {
        super.notifyMessageChangedListeners(type, m4);
    }

    protected synchronized POP3Connection getConnection() throws MessagingException {
        return ((POP3Store)this.store).getFolderConnection(this);
    }

    protected void releaseConnection(POP3Connection connection) throws MessagingException {
        ((POP3Store)this.store).releaseFolderConnection(this, connection);
    }
}

