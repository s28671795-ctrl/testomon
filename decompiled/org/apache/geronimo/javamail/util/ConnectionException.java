/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import javax.mail.MessagingException;

public class ConnectionException
extends MessagingException {
    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}

