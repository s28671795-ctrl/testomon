/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import javax.mail.MessagingException;

public class CommandFailedException
extends MessagingException {
    public CommandFailedException() {
    }

    public CommandFailedException(String message) {
        super(message);
    }

    public CommandFailedException(String message, Exception cause) {
        super(message, cause);
    }
}

