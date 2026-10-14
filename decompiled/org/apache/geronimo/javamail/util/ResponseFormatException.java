/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import javax.mail.MessagingException;

public class ResponseFormatException
extends MessagingException {
    public ResponseFormatException() {
    }

    public ResponseFormatException(String message) {
        super(message);
    }

    public ResponseFormatException(String message, Exception cause) {
        super(message, cause);
    }
}

