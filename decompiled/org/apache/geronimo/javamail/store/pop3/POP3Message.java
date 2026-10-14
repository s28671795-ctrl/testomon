/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import org.apache.geronimo.javamail.store.pop3.POP3Folder;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Connection;

public class POP3Message
extends MimeMessage {
    protected int msgSize = -1;
    protected int headerSize = -1;
    protected String uid;
    protected byte[] messageData;

    protected POP3Message(Folder folder, int msgnum) {
        super(folder, msgnum);
        this.session = this.session;
        this.headers = null;
    }

    protected InputStream getContentStream() throws MessagingException {
        this.loadContent();
        return super.getContentStream();
    }

    public void writeTo(OutputStream out) throws IOException, MessagingException {
        this.loadContent();
        out.write(this.messageData);
    }

    public void setFlags(Flags newFlags, boolean set) throws MessagingException {
        Flags oldFlags = (Flags)this.flags.clone();
        super.setFlags(newFlags, set);
        if (!this.flags.equals(oldFlags)) {
            ((POP3Folder)this.folder).notifyMessageChangedListeners(1, this);
        }
    }

    protected void loadHeaders(InputStream in) throws MessagingException {
        try {
            this.headerSize = in.available();
            this.headers = new InternetHeaders(in);
            this.headerSize -= in.available();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadContent() throws MessagingException {
        if (this.content == null) {
            POP3Connection connection = this.getConnection();
            try {
                this.messageData = connection.retrieveMessageData(this.msgnum);
            }
            finally {
                this.releaseConnection(connection);
            }
            ByteArrayInputStream in = new ByteArrayInputStream(this.messageData);
            this.loadHeaders(in);
            this.loadContent(in);
        }
    }

    protected void loadContent(ByteArrayInputStream stream) throws MessagingException {
        this.content = new byte[stream.available()];
        try {
            stream.read(this.content);
        }
        catch (IOException e) {
            throw new MessagingException("Error loading content info", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getSize() throws MessagingException {
        if (this.msgSize < 0) {
            this.loadHeaders();
            POP3Connection connection = this.getConnection();
            try {
                this.msgSize = connection.retrieveMessageSize(this.msgnum) - this.headerSize;
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        return this.msgSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadHeaders() throws MessagingException {
        if (this.headers == null) {
            POP3Connection connection = this.getConnection();
            try {
                this.loadHeaders(connection.retrieveMessageHeaders(this.msgnum));
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String getUID() throws MessagingException {
        if (this.uid == null) {
            POP3Connection connection = this.getConnection();
            try {
                this.uid = connection.retrieveMessageUid(this.msgnum);
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        return this.uid;
    }

    public String[] getHeader(String name) throws MessagingException {
        this.loadHeaders();
        return super.getHeader(name);
    }

    public String getHeader(String name, String delimiter) throws MessagingException {
        this.loadHeaders();
        return super.getHeader(name, delimiter);
    }

    public Enumeration getAllHeaders() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaders();
    }

    public Enumeration getMatchingHeaders(String[] names) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaders(names);
    }

    public Enumeration getNonMatchingHeaders(String[] names) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaders(names);
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaderLines();
    }

    public Enumeration getMatchingHeaderLines(String[] names) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaderLines(names);
    }

    public Enumeration getNonMatchingHeaderLines(String[] names) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaderLines(names);
    }

    public void addHeader(String name, String value) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void setHeader(String name, String value) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void removeHeader(String name) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void addHeaderLine(String line) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    protected POP3Connection getConnection() throws MessagingException {
        return ((POP3Folder)this.folder).getMessageConnection();
    }

    protected void releaseConnection(POP3Connection connection) throws MessagingException {
        ((POP3Folder)this.folder).releaseMessageConnection(connection);
    }
}

