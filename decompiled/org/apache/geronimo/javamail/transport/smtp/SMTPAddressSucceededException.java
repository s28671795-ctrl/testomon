/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressSucceededException
extends MessagingException {
    InternetAddress addr;
    protected String cmd;
    protected int rc;

    SMTPAddressSucceededException(InternetAddress addr, String cmd, int rc, String err) {
        super(err);
        this.cmd = cmd;
        this.rc = rc;
        this.addr = addr;
    }

    public String getCommand() {
        return this.cmd;
    }

    public int getReturnCode() {
        return this.rc;
    }

    public InternetAddress getAddress() {
        return this.addr;
    }
}

