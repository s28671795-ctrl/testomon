/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.mq;

public interface Message {
    public String subject();

    public byte[] getData();

    public String getReplyTo();
}

