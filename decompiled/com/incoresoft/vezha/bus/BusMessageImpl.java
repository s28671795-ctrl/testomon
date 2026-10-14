/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.bus;

import com.incoresoft.middleware.eventbus.BusMessage;
import java.util.Objects;

public class BusMessageImpl
implements BusMessage {
    private final byte[] data;
    private final String replySubject;

    public BusMessageImpl(byte[] data, String replySubject) {
        this.data = Objects.requireNonNull(data);
        this.replySubject = replySubject;
    }

    public BusMessageImpl(byte[] data) {
        this(data, null);
    }

    @Override
    public byte[] data() {
        return this.data;
    }

    @Override
    public String replySubject() {
        return this.replySubject;
    }
}

