/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MailDateFormat;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.apache.geronimo.javamail.store.imap.IMAPAttachedMessage;
import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.IMAPMultipartDataSource;
import org.apache.geronimo.javamail.store.imap.IMAPStore;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPEnvelope;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPInternalDate;
import org.apache.geronimo.javamail.store.imap.connection.IMAPInternetHeader;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMessageSize;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUid;

public class IMAPMessage
extends MimeMessage {
    private static final byte[] CRLF = new byte[]{13, 10};
    protected IMAPStore store;
    protected int sequenceNumber;
    protected long uid = -1L;
    protected String section;
    protected IMAPEnvelope envelope;
    protected IMAPBodyStructure bodyStructure;
    protected Date receivedDate;
    protected int size;
    protected boolean allHeadersRetrieved = false;
    protected static MailDateFormat dateFormat = new MailDateFormat();

    IMAPMessage(IMAPFolder folder, IMAPStore store, int msgnum, int sequenceNumber) {
        super(folder, msgnum);
        this.sequenceNumber = sequenceNumber;
        this.store = store;
        this.flags = null;
        this.headers = new InternetHeaders();
    }

    public void setExpunged(boolean value) {
        super.setExpunged(value);
        if (this.isExpunged()) {
            this.sequenceNumber = -1;
        }
    }

    public synchronized Flags getFlags() throws MessagingException {
        this.loadFlags();
        return super.getFlags();
    }

    public synchronized boolean isSet(Flags.Flag flag) throws MessagingException {
        this.loadFlags();
        return super.isSet(flag);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void setFlags(Flags flag, boolean set) throws MessagingException {
        this.checkValidity();
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                this.flags = connection.setFlags(this.sequenceNumber, flag, set);
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    protected InputStream getContentStream() throws MessagingException {
        if (this.content == null) {
            this.checkValidity();
            this.loadContent();
        }
        return super.getContentStream();
    }

    public void writeTo(OutputStream out) throws IOException, MessagingException {
        if (this.content == null) {
            this.checkValidity();
            this.loadContent();
        }
        this.loadHeaders();
        Enumeration e = this.headers.getAllHeaderLines();
        while (e.hasMoreElements()) {
            String line = (String)e.nextElement();
            out.write(line.getBytes("ISO8859-1"));
            out.write(CRLF);
        }
        out.write(CRLF);
        out.write(CRLF);
        out.write(this.content);
    }

    public Address[] getFrom() throws MessagingException {
        this.loadEnvelope();
        InternetAddress[] addresses = this.envelope.from;
        if (addresses == null) {
            return null;
        }
        return (Address[])addresses.clone();
    }

    public Address getSender() throws MessagingException {
        this.loadEnvelope();
        InternetAddress[] addresses = this.envelope.sender;
        if (addresses == null) {
            return null;
        }
        return addresses[0];
    }

    public Address[] getRecipients(Message.RecipientType type) throws MessagingException {
        this.loadEnvelope();
        InternetAddress[] addresses = null;
        if (type == Message.RecipientType.TO) {
            addresses = this.envelope.to;
        } else if (type == Message.RecipientType.CC) {
            addresses = this.envelope.cc;
        } else if (type == Message.RecipientType.BCC) {
            addresses = this.envelope.bcc;
        } else {
            return super.getRecipients(type);
        }
        if (addresses == null) {
            return null;
        }
        return (Address[])addresses.clone();
    }

    public Address[] getReplyTo() throws MessagingException {
        this.loadEnvelope();
        InternetAddress[] addresses = this.envelope.replyTo;
        if (addresses == null) {
            return null;
        }
        return (Address[])addresses.clone();
    }

    public String getSubject() throws MessagingException {
        this.loadEnvelope();
        if (this.envelope.subject == null) {
            return null;
        }
        try {
            return MimeUtility.decodeText(this.envelope.subject);
        }
        catch (UnsupportedEncodingException e) {
            return this.envelope.subject;
        }
    }

    public Date getSentDate() throws MessagingException {
        this.loadEnvelope();
        return this.envelope.date;
    }

    public Date getReceivedDate() throws MessagingException {
        this.loadEnvelope();
        return this.receivedDate;
    }

    public int getSize() throws MessagingException {
        this.loadEnvelope();
        return this.size;
    }

    public int getLineCount() throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.lines;
    }

    public String getInReplyTo() throws MessagingException {
        this.loadEnvelope();
        return this.envelope.inReplyTo;
    }

    public String getContentType() throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.mimeType.toString();
    }

    public boolean isMimeType(String type) throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.mimeType.match(type);
    }

    public String getDisposition() throws MessagingException {
        this.loadBodyStructure();
        if (this.bodyStructure.disposition != null) {
            return this.bodyStructure.disposition.getDisposition();
        }
        return null;
    }

    public String getEncoding() throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.transferEncoding;
    }

    public String getContentID() throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.contentID;
    }

    public String getContentMD5() throws MessagingException {
        this.loadBodyStructure();
        return this.bodyStructure.md5Hash;
    }

    public String getDescription() throws MessagingException {
        this.loadBodyStructure();
        if (this.bodyStructure.contentDescription == null) {
            return null;
        }
        try {
            return MimeUtility.decodeText(this.bodyStructure.contentDescription);
        }
        catch (UnsupportedEncodingException e) {
            return this.bodyStructure.contentDescription;
        }
    }

    public String[] getContentLanguage() throws MessagingException {
        this.loadBodyStructure();
        if (!this.bodyStructure.languages.isEmpty()) {
            return this.bodyStructure.languages.toArray(new String[this.bodyStructure.languages.size()]);
        }
        return null;
    }

    public String getMessageID() throws MessagingException {
        this.loadEnvelope();
        return this.envelope.messageID;
    }

    public void setFrom(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void addFrom(Address[] address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setSender(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setRecipients(Message.RecipientType type, Address[] addresses) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setRecipients(Message.RecipientType type, String address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void addRecipients(Message.RecipientType type, Address[] address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setReplyTo(Address[] address) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setSubject(String subject) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setSubject(String subject, String charset) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setSentDate(Date sent) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setDisposition(String disposition) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setContentID(String cid) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setContentMD5(String md5) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setDescription(String description) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setDescription(String description, String charset) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    public void setContentLanguage(String[] languages) throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
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

    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("IMAP messages are read-only");
    }

    protected void updateHeader(String header, InternetAddress[] addresses) throws MessagingException {
        if (addresses != null) {
            this.headers.addHeader(header, InternetAddress.toString(addresses));
        }
    }

    protected void updateHeader(String header, Address address) throws MessagingException {
        if (address != null) {
            this.headers.setHeader(header, address.toString());
        }
    }

    protected void updateHeader(String header, String value) throws MessagingException {
        if (value != null) {
            this.headers.setHeader(header, value);
        }
    }

    public synchronized DataHandler getDataHandler() throws MessagingException {
        this.checkValidity();
        this.loadBodyStructure();
        if (this.dh == null) {
            if (this.bodyStructure.isMultipart()) {
                this.dh = new DataHandler(new IMAPMultipartDataSource(this, this, this.section, this.bodyStructure));
                return this.dh;
            }
            if (this.bodyStructure.isAttachedMessage()) {
                this.dh = new DataHandler(new IMAPAttachedMessage(this, this.section, this.bodyStructure.nestedEnvelope, this.bodyStructure.nestedBody), this.bodyStructure.mimeType.toString());
                return this.dh;
            }
        }
        return super.getDataHandler();
    }

    public void setDataHandler(DataHandler content) throws MessagingException {
        throw new IllegalWriteException("IMAP body parts are read-only");
    }

    public void updateHeaders(InputStream in) throws MessagingException {
        this.headers = new InternetHeaders(in);
        this.allHeadersRetrieved = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadFlags() throws MessagingException {
        this.checkValidity();
        if (this.flags != null) {
            return;
        }
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                this.flags = connection.fetchFlags(this.sequenceNumber);
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized void loadHeaders() throws MessagingException {
        if (this.allHeadersRetrieved) {
            return;
        }
        this.checkValidity();
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                this.headers = connection.fetchHeaders(this.sequenceNumber, this.section);
                this.allHeadersRetrieved = true;
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized void loadEnvelope() throws MessagingException {
        if (this.envelope != null) {
            return;
        }
        this.checkValidity();
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                List fetches = connection.fetchEnvelope(this.sequenceNumber);
                for (int i = 0; i < fetches.size(); ++i) {
                    IMAPFetchResponse fetch = (IMAPFetchResponse)fetches.get(i);
                    this.updateMessageInformation(fetch);
                }
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    protected synchronized void updateEnvelope(IMAPEnvelope envelope) throws MessagingException {
        this.envelope = envelope;
        this.updateHeader("From", envelope.from);
        if (envelope.sender != null) {
            this.updateHeader("Sender", envelope.sender[0]);
        }
        this.updateHeader("To", envelope.to);
        this.updateHeader("Cc", envelope.cc);
        this.updateHeader("Bcc", envelope.bcc);
        this.updateHeader("Reply-To", envelope.replyTo);
        this.updateHeader("Subject", envelope.subject);
        this.updateHeader("Message-ID", envelope.messageID);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized void loadBodyStructure() throws MessagingException {
        if (this.bodyStructure != null) {
            return;
        }
        this.checkValidity();
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                this.bodyStructure = connection.fetchBodyStructure(this.sequenceNumber);
            }
            finally {
                this.releaseConnection(connection);
            }
            this.updateBodyStructure(this.bodyStructure);
        }
    }

    protected synchronized void updateBodyStructure(IMAPBodyStructure structure) throws MessagingException {
        this.bodyStructure = structure;
        if (this.bodyStructure.lines != -1) {
            this.updateHeader("Lines", Integer.toString(this.bodyStructure.lines));
        }
        if (this.bodyStructure.languages != null) {
            if (this.bodyStructure.languages.size() == 1) {
                this.updateHeader("Content-Language", (String)this.bodyStructure.languages.get(0));
            } else {
                StringBuffer buf = new StringBuffer(this.bodyStructure.languages.size() * 20);
                buf.append(this.bodyStructure.languages.get(0));
                for (int i = 1; i < this.bodyStructure.languages.size(); ++i) {
                    buf.append(',').append(this.bodyStructure.languages.get(i));
                }
                this.updateHeader("Content-Language", buf.toString());
            }
        }
        this.updateHeader("Content-Type", this.bodyStructure.mimeType.toString());
        if (this.bodyStructure.disposition != null) {
            this.updateHeader("Content-Disposition", this.bodyStructure.disposition.toString());
        }
        this.updateHeader("Content-Transfer-Encoding", this.bodyStructure.transferEncoding);
        this.updateHeader("Content-ID", this.bodyStructure.contentID);
        this.updateHeader("Content-Description", this.bodyStructure.contentDescription);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadContent() throws MessagingException {
        if (this.content != null) {
            return;
        }
        Folder folder = this.folder;
        synchronized (folder) {
            IMAPConnection connection = this.getConnection();
            try {
                this.content = connection.fetchContent(this.getSequenceNumber(), this.section);
            }
            finally {
                this.releaseConnection(connection);
            }
        }
    }

    int getSequenceNumber() {
        return this.sequenceNumber;
    }

    void setSequenceNumber(int s2) {
        this.sequenceNumber = s2;
    }

    long getUID() {
        return this.uid;
    }

    void setUID(long uid) {
        this.uid = uid;
    }

    protected IMAPConnection getConnection() throws MessagingException {
        return ((IMAPFolder)this.folder).getMessageConnection();
    }

    protected void releaseConnection(IMAPConnection connection) throws MessagingException {
        ((IMAPFolder)this.folder).releaseMessageConnection(connection);
    }

    protected void checkValidity() throws MessagingException {
        this.checkValidity(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void checkValidity(boolean update) throws MessagingException {
        if (update) {
            Folder folder = this.folder;
            synchronized (folder) {
                IMAPConnection connection = this.getConnection();
                try {
                    connection.updateMailboxStatus();
                }
                finally {
                    this.releaseConnection(connection);
                }
            }
        }
        if (this.isExpunged()) {
            throw new MessageRemovedException("Illegal opertion on a deleted message");
        }
    }

    protected boolean evaluateFetch(FetchProfile profile) {
        if (profile.contains(UIDFolder.FetchProfileItem.UID) && this.uid == -1L) {
            return true;
        }
        if (profile.contains(FetchProfile.Item.ENVELOPE) && this.envelope == null) {
            return true;
        }
        if (profile.contains(FetchProfile.Item.FLAGS) && this.flags == null) {
            return true;
        }
        if (profile.contains(FetchProfile.Item.CONTENT_INFO) && this.bodyStructure == null) {
            return true;
        }
        if (profile.contains(IMAPFolder.FetchProfileItem.HEADERS) && !this.allHeadersRetrieved) {
            return true;
        }
        if (profile.contains(IMAPFolder.FetchProfileItem.SIZE) && this.bodyStructure.bodySize < 0) {
            return true;
        }
        String[] requestedHeaders = profile.getHeaderNames();
        for (int i = 0; i < requestedHeaders.length; ++i) {
            if (this.headers.getHeader(requestedHeaders[i]) != null) continue;
            return true;
        }
        return false;
    }

    void updateMessageInformation(IMAPFetchResponse response) throws MessagingException {
        List items = response.getDataItems();
        block8: for (int i = 0; i < items.size(); ++i) {
            IMAPFetchDataItem item = (IMAPFetchDataItem)items.get(i);
            switch (item.getType()) {
                case 1: {
                    this.updateEnvelope((IMAPEnvelope)item);
                    continue block8;
                }
                case 4: {
                    this.receivedDate = ((IMAPInternalDate)item).getDate();
                    continue block8;
                }
                case 5: {
                    this.size = ((IMAPMessageSize)item).size;
                    continue block8;
                }
                case 6: {
                    this.uid = ((IMAPUid)item).uid;
                    ((IMAPFolder)this.folder).addToUidCache(new Long(this.uid), this);
                    continue block8;
                }
                case 3: {
                    this.updateBodyStructure((IMAPBodyStructure)item);
                    continue block8;
                }
                case 8: {
                    IMAPInternetHeader h2 = (IMAPInternetHeader)item;
                    if (h2.isComplete()) {
                        this.headers = h2.headers;
                        this.allHeadersRetrieved = true;
                        continue block8;
                    }
                    this.mergeHeaders(h2.headers);
                }
            }
        }
    }

    protected synchronized void mergeHeaders(InternetHeaders newHeaders) {
        Enumeration e = this.headers.getAllHeaders();
        while (e.hasMoreElements()) {
            Header header = (Header)e.nextElement();
            if (newHeaders.getHeader(header.getName()) != null) continue;
            String name = header.getName();
            String[] a = this.headers.getHeader(name);
            for (int i = 0; i < a.length; ++i) {
                newHeaders.addHeader(name, a[i]);
            }
        }
        this.headers = newHeaders;
    }
}

