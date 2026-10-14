/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import javax.mail.Address;
import javax.mail.SendFailedException;

public class SMTPSendFailedException
extends SendFailedException {
    protected String cmd;
    protected int rc;

    SMTPSendFailedException(String cmd, int rc, String err, Exception ex, Address[] vs, Address[] vus, Address[] inv) {
        super(err, ex, vs, vus, inv);
        this.cmd = cmd;
        this.rc = rc;
    }

    public String getCommand() {
        return this.cmd;
    }

    public int getReturnCode() {
        return this.rc;
    }
}

