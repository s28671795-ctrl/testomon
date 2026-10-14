/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5029")
@ThreadSafe
public abstract class ChannelLogger {
    public abstract void log(ChannelLogLevel var1, String var2);

    public abstract void log(ChannelLogLevel var1, String var2, Object ... var3);

    public static enum ChannelLogLevel {
        DEBUG,
        INFO,
        WARNING,
        ERROR;

    }
}

