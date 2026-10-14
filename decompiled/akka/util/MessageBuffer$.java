/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.MessageBuffer;

public final class MessageBuffer$ {
    public static final MessageBuffer$ MODULE$ = new MessageBuffer$();

    public MessageBuffer empty() {
        return new MessageBuffer(null, null);
    }

    private MessageBuffer$() {
    }
}

