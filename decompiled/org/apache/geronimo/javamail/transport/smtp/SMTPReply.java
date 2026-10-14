/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import java.util.ArrayList;
import java.util.List;
import org.apache.geronimo.javamail.transport.smtp.MalformedSMTPReplyException;

class SMTPReply {
    public static final int SERVICE_READY = 220;
    public static final int SERVICE_CLOSING = 221;
    public static final int AUTHENTICATION_COMPLETE = 235;
    public static final int COMMAND_ACCEPTED = 250;
    public static final int ADDRESS_NOT_LOCAL = 251;
    public static final int AUTHENTICATION_CHALLENGE = 334;
    public static final int START_MAIL_INPUT = 354;
    public static final int SERVICE_NOT_AVAILABLE = 421;
    public static final int MAILBOX_BUSY = 450;
    public static final int PROCESSING_ERROR = 451;
    public static final int INSUFFICIENT_STORAGE = 452;
    public static final int COMMAND_SYNTAX_ERROR = 500;
    public static final int PARAMETER_SYNTAX_ERROR = 501;
    public static final int COMMAND_NOT_IMPLEMENTED = 502;
    public static final int INVALID_COMMAND_SEQUENCE = 503;
    public static final int COMMAND_PARAMETER_NOT_IMPLEMENTED = 504;
    public static final int MAILBOX_NOT_FOUND = 550;
    public static final int USER_NOT_LOCAL = 551;
    public static final int MAILBOX_FULL = 552;
    public static final int INVALID_MAILBOX = 553;
    public static final int TRANSACTION_FAILED = 553;
    private final String reply;
    private final int code;
    private final String message;
    private List lines;
    private boolean continued;

    SMTPReply(String s2) throws MalformedSMTPReplyException {
        this.reply = s2;
        if (s2 == null || s2.length() < 3) {
            this.code = -1;
            this.message = s2;
            return;
        }
        try {
            this.continued = false;
            this.code = Integer.parseInt(s2.substring(0, 3));
            if (s2.length() > 4) {
                if (s2.charAt(3) == '-') {
                    this.continued = true;
                }
                this.message = s2.substring(4);
            } else {
                this.message = "";
            }
        }
        catch (NumberFormatException e) {
            throw new MalformedSMTPReplyException("error in parsing code", e);
        }
    }

    public void addLine(String line) {
        if (this.lines == null) {
            this.lines = new ArrayList();
            this.lines.add(this.message);
        }
        this.continued = line.charAt(3) == '-';
        this.lines.add(line.substring(4));
    }

    public List getLines() {
        if (this.lines == null) {
            this.lines = new ArrayList();
            this.lines.add(this.message);
        }
        return this.lines;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getReply() {
        return this.reply;
    }

    boolean isError() {
        return this.code >= 400;
    }

    public boolean isContinued() {
        return this.continued;
    }

    public String toString() {
        return "CODE = " + this.getCode() + " : MSG = " + this.getMessage();
    }
}

