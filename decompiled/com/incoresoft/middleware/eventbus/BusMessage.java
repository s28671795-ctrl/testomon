/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventbus;

public interface BusMessage {
    default public String replySubject() {
        return null;
    }

    public byte[] data();
}

