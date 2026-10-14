/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import org.apache.geronimo.javamail.store.imap.IMAPAttachedMessage;
import org.apache.geronimo.javamail.store.imap.IMAPMessage;
import org.apache.geronimo.javamail.store.imap.IMAPMultipartDataSource;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;

public class IMAPMimeBodyPart
extends MimeBodyPart {
    protected IMAPMessage message;
    protected IMAPBodyStructure bodyStructure;
    protected String section;
    boolean headersLoaded = false;

    public IMAPMimeBodyPart(IMAPMessage message, IMAPBodyStructure bodyStructure, String section) {
        this.message = message;
        this.bodyStructure = bodyStructure;
        this.section = section;
    }

    public int getSize() throws MessagingException {
        return this.bodyStructure.bodySize;
    }

    public int getLineCount() throws MessagingException {
        return this.bodyStructure.lines;
    }

    public String getContentType() throws MessagingException {
        return this.bodyStructure.mimeType.toString();
    }

    public boolean isMimeType(String type) throws MessagingException {
        return this.bodyStructure.mimeType.match(type);
    }

    public String getDisposition() throws MessagingException {
        return this.bodyStructure.disposition.getDisposition();
    }

    public void setDisposition(String disposition) throws MessagingException {
        throw new IllegalWriteException("IMAP message parts are read-only");
    }

    public String getEncoding() throws MessagingException {
        return this.bodyStructure.transferEncoding;
    }

    public String getContentID() throws MessagingException {
        return this.bodyStructure.contentID;
    }

    public void setContentID(String id) throws MessagingException {
        throw new IllegalWriteException("IMAP message parts are read-only");
    }

    public String getContentMD5() throws MessagingException {
        return this.bodyStructure.md5Hash;
    }

    public void setContentMD5(String id) throws MessagingException {
        throw new IllegalWriteException("IMAP message parts are read-only");
    }

    public String getDescription() throws MessagingException {
        String description = this.bodyStructure.contentDescription;
        if (description != null) {
            try {
                return MimeUtility.decodeText(MimeUtility.unfold(description));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return description;
    }

    public void setDescription(String d, String charset) throws MessagingException {
        throw new IllegalWriteException("IMAP message parts are read-only");
    }

    public String getFileName() throws MessagingException {
        String filename = this.bodyStructure.disposition.getParameter("filename");
        if (filename == null) {
            filename = this.bodyStructure.mimeType.getParameter("name");
        }
        return filename;
    }

    public void setFileName(String name) throws MessagingException {
        throw new IllegalWriteException("IMAP message parts are read-only");
    }

    protected InputStream getContentStream() throws MessagingException {
        if (this.content == null) {
            this.message.checkValidity();
            this.loadContent();
        }
        return super.getContentStream();
    }

    public synchronized DataHandler getDataHandler() throws MessagingException {
        if (this.dh == null) {
            if (this.bodyStructure.isMultipart()) {
                this.dh = new DataHandler(new IMAPMultipartDataSource(this.message, this, this.section, this.bodyStructure));
                return this.dh;
            }
            if (this.bodyStructure.isAttachedMessage()) {
                this.dh = new DataHandler(new IMAPAttachedMessage(this.message, this.section, this.bodyStructure.nestedEnvelope, this.bodyStructure.nestedBody), this.bodyStructure.mimeType.toString());
                return this.dh;
            }
        }
        return super.getDataHandler();
    }

    public void setDataHandler(DataHandler content) throws MessagingException {
        throw new IllegalWriteException("IMAP body parts are read-only");
    }

    public void setContent(Object o, String type) throws MessagingException {
        throw new IllegalWriteException("IMAP body parts are read-only");
    }

    public void setContent(Multipart mp) throws MessagingException {
        throw new IllegalWriteException("IMAP body parts are read-only");
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
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setHeader(String name, String value) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void removeHeader(String name) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void addHeaderLine(String line) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized void loadHeaders() throws MessagingException {
        if (this.headers != null) {
            return;
        }
        IMAPConnection connection = this.message.getConnection();
        try {
            this.headers = connection.fetchHeaders(this.message.getSequenceNumber(), this.section);
        }
        finally {
            this.message.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadContent() throws MessagingException {
        if (this.content != null) {
            return;
        }
        IMAPConnection connection = this.message.getConnection();
        try {
            this.content = connection.fetchContent(this.message.getSequenceNumber(), this.section);
        }
        finally {
            this.message.releaseConnection(connection);
        }
    }
}

