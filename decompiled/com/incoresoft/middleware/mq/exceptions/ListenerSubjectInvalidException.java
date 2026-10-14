/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq.exceptions;

public class ListenerSubjectInvalidException
extends RuntimeException {
    private final String subject;

    public ListenerSubjectInvalidException(String subject, String reason) {
        super("Listener subject is invalid: " + subject + ". Reason: " + reason);
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }
}

