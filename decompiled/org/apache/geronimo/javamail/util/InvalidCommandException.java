/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import javax.mail.MessagingException;

public class InvalidCommandException
extends MessagingException {
    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Exception cause) {
        super(message, cause);
    }
}

