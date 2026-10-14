/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

class MalformedSMTPReplyException
extends Exception {
    MalformedSMTPReplyException() {
    }

    MalformedSMTPReplyException(String msg) {
        super(msg);
    }

    MalformedSMTPReplyException(String msg, Exception t2) {
        super(msg, t2);
    }
}

