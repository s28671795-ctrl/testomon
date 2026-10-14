/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import java.io.InputStream;
import javax.annotation.Nullable;

public interface StreamListener {
    public void messagesAvailable(MessageProducer var1);

    public void onReady();

    public static interface MessageProducer {
        @Nullable
        public InputStream next();
    }
}

