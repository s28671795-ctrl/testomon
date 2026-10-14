/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.mail.Flags;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import org.apache.geronimo.javamail.store.nntp.NNTPFolder;
import org.apache.geronimo.javamail.store.nntp.NNTPStore;
import org.apache.geronimo.javamail.transport.nntp.NNTPConnection;
import org.apache.geronimo.javamail.transport.nntp.NNTPReply;
import org.apache.geronimo.javamail.transport.nntp.StringListInputStream;

public class NNTPMessage
extends MimeMessage {
    String messageID = null;
    protected Session session;
    protected NNTPStore store;
    protected NNTPConnection connection;
    protected boolean headersLoaded = false;
    protected boolean contentLoaded = false;

    NNTPMessage(NNTPFolder folder, NNTPStore store, int msgnum, String messageID) throws MessagingException {
        super(folder, msgnum);
        this.messageID = messageID;
        this.store = store;
        this.session = store.getSession();
        this.connection = store.getConnection();
        this.flags = folder.getPermanentFlags();
        if (folder.isSeen(msgnum)) {
            this.flags.add(Flags.Flag.SEEN);
        } else {
            this.flags.remove(Flags.Flag.SEEN);
        }
    }

    public int getSize() throws MessagingException {
        this.loadContent();
        return super.getSize();
    }

    public int getLineCount() throws MessagingException {
        String[] headers = this.getHeader("Lines");
        if (headers.length == 1) {
            try {
                return Integer.parseInt(headers[0].trim());
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return -1;
    }

    protected InputStream getContentStream() throws MessagingException {
        this.loadArticle();
        return super.getContentStream();
    }

    public String[] getHeader(String name) throws MessagingException {
        this.loadHeaders();
        return this.headers.getHeader(name);
    }

    public String getHeader(String name, String delimiter) throws MessagingException {
        this.loadHeaders();
        return this.headers.getHeader(name, delimiter);
    }

    public Enumeration getAllHeaders() throws MessagingException {
        this.loadHeaders();
        return this.headers.getAllHeaders();
    }

    public Enumeration getMatchingHeaders(String[] names) throws MessagingException {
        this.loadHeaders();
        return this.headers.getMatchingHeaders(names);
    }

    public Enumeration getNonMatchingHeaders(String[] names) throws MessagingException {
        this.loadHeaders();
        return this.headers.getNonMatchingHeaders(names);
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
        this.loadHeaders();
        return this.headers.getAllHeaderLines();
    }

    public Enumeration getMatchingHeaderLines(String[] names) throws MessagingException {
        this.loadHeaders();
        return this.headers.getMatchingHeaderLines(names);
    }

    public Enumeration getNonMatchingHeaderLines(String[] names) throws MessagingException {
        this.loadHeaders();
        return this.headers.getNonMatchingHeaderLines(names);
    }

    public void addHeader(String name, String value) throws MessagingException {
        throw new IllegalWriteException("NNTP messages are read-only");
    }

    public void setHeader(String name, String value) throws MessagingException {
        throw new IllegalWriteException("NNTP messages are read-only");
    }

    public void removeHeader(String name) throws MessagingException {
        throw new IllegalWriteException("NNTP messages are read-only");
    }

    public void addHeaderLine(String line) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("NNTP messages are read-only");
    }

    public void loadHeaders() throws MessagingException {
        if (this.headersLoaded) {
            return;
        }
        NNTPReply reply = this.connection.sendCommand("HEAD " + this.messageID, 221);
        if (reply.getCode() == 221) {
            try {
                this.updateHeaders(new StringListInputStream(reply.getData()));
            }
            catch (IOException e) {
                throw new MessagingException("Error retrieving article headers from server", e);
            }
        } else {
            throw new MessagingException("Error retrieving article headers from server: " + reply);
        }
    }

    public void updateHeaders(InputStream in) throws MessagingException {
        this.headers = new InternetHeaders(in);
        this.headersLoaded = true;
    }

    public void loadContent() throws MessagingException {
        if (this.contentLoaded) {
            return;
        }
        NNTPReply reply = this.connection.sendCommand("BODY " + this.messageID, 222);
        if (reply.getCode() == 222) {
            try {
                StringListInputStream in = new StringListInputStream(reply.getData());
                this.updateContent(in);
            }
            catch (IOException e) {
                throw new MessagingException("Error retrieving article body from server", e);
            }
        } else {
            throw new MessagingException("Error retrieving article body from server: " + reply);
        }
    }

    public void loadArticle() throws MessagingException {
        if (this.headersLoaded) {
            this.loadContent();
            return;
        }
        NNTPReply reply = this.connection.sendCommand("ARTICLE " + this.messageID, 220);
        if (reply.getCode() == 220) {
            try {
                StringListInputStream in = new StringListInputStream(reply.getData());
                this.updateHeaders(in);
                this.updateContent(in);
            }
            catch (IOException e) {
                throw new MessagingException("Error retrieving article from server", e);
            }
        } else {
            throw new MessagingException("Error retrieving article from server: " + reply);
        }
    }

    public void updateContent(InputStream in) throws MessagingException {
        try {
            int read;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            this.content = out.toByteArray();
            this.contentLoaded = true;
        }
        catch (IOException e) {
            throw new MessagingException("Error retrieving message body from server", e);
        }
    }

    public String getMessageId() {
        return this.messageID;
    }

    public void setFlags(Flags flag, boolean newvalue) throws MessagingException {
        if (flag.contains(Flags.Flag.SEEN)) {
            ((NNTPFolder)this.folder).setSeen(this.msgnum, newvalue);
        }
        super.setFlags(flag, newvalue);
    }
}

