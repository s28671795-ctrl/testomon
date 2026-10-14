/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPEnvelope
extends IMAPFetchDataItem {
    public Date date;
    public String subject;
    public InternetAddress[] from;
    public InternetAddress[] sender;
    public InternetAddress[] replyTo;
    public InternetAddress[] to;
    public InternetAddress[] cc;
    public InternetAddress[] bcc;
    public String inReplyTo;
    public String messageID;

    public IMAPEnvelope(IMAPResponseTokenizer source2) throws MessagingException {
        super(1);
        source2.checkLeftParen();
        this.date = source2.readDateOrNil();
        this.subject = source2.readStringOrNil();
        this.from = source2.readAddressList();
        this.sender = source2.readAddressList();
        this.replyTo = source2.readAddressList();
        this.to = source2.readAddressList();
        this.cc = source2.readAddressList();
        this.bcc = source2.readAddressList();
        this.inReplyTo = source2.readStringOrNil();
        this.messageID = source2.readStringOrNil();
        source2.checkRightParen();
    }
}

