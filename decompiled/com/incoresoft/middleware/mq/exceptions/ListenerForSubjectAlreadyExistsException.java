/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq.exceptions;

public class ListenerForSubjectAlreadyExistsException
extends RuntimeException {
    private final String subject;

    public ListenerForSubjectAlreadyExistsException(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }
}

