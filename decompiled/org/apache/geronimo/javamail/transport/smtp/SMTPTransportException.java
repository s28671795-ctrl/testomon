/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

class SMTPTransportException
extends Exception {
    SMTPTransportException() {
    }

    SMTPTransportException(String s2) {
        super(s2);
    }

    SMTPTransportException(String s2, Exception t2) {
        super(s2, t2);
    }

    SMTPTransportException(Exception t2) {
        super("SMTP Transport error", t2);
    }
}

