/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import java.io.InputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class SMTPMessage
extends MimeMessage {
    public static final int NOTIFY_NEVER = -1;
    public static final int NOTIFY_SUCCESS = 1;
    public static final int NOTIFY_FAILURE = 2;
    public static final int NOTIFY_DELAY = 4;
    public static final int RETURN_FULL = 1;
    public static final int RETURN_HDRS = 2;
    protected boolean allow8bitMIME = false;
    protected String envelopeFrom = null;
    protected String mailExtension = null;
    protected int notifyOptions = 0;
    protected int returnOption = 0;
    protected boolean sendPartial = false;
    protected String submitter = null;

    public SMTPMessage(Session session) {
        super(session);
    }

    public SMTPMessage(Session session, InputStream source2) throws MessagingException {
        super(session, source2);
    }

    public SMTPMessage(MimeMessage source2) throws MessagingException {
        super(source2);
    }

    public void setAllow8bitMIME(boolean a) {
        this.allow8bitMIME = a;
    }

    public boolean getAllow8bitMIME() {
        return this.allow8bitMIME;
    }

    public void setEnvelopeFrom(String from) {
        this.envelopeFrom = from;
    }

    public String getEnvelopeFrom() {
        return this.envelopeFrom;
    }

    public void setMailExtension(String e) {
        this.mailExtension = e;
    }

    public String getMailExtension() {
        return this.mailExtension;
    }

    public void setNotifyOptions(int options) {
        this.notifyOptions = options;
    }

    public int getNotifyOptions() {
        return this.notifyOptions;
    }

    public void setReturnOption(int option) {
        this.returnOption = option;
    }

    public int getReturnOption() {
        return this.returnOption;
    }

    public void setSendPartial(boolean a) {
        this.sendPartial = a;
    }

    public boolean getSendPartial() {
        return this.sendPartial;
    }

    public void setSubmitter(String s2) {
        this.submitter = s2;
    }

    public String getSubmitter() {
        return this.submitter;
    }
}

