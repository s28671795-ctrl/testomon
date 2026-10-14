/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.mq;

import com.incoresoft.middleware.mq.Message;

public class ResponseMessage
implements Message {
    private final byte[] data;
    private final String replyTo;

    public ResponseMessage(String replyTo, byte[] bytes) {
        this.replyTo = replyTo;
        this.data = bytes;
    }

    @Override
    public String subject() {
        return "";
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public String getReplyTo() {
        return this.replyTo;
    }

    public static ResponseMessage empty() {
        return new ResponseMessage(null, null);
    }
}

