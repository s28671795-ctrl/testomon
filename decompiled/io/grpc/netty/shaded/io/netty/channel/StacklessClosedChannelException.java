/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.util.internal.ThrowableUtil;
import java.nio.channels.ClosedChannelException;

final class StacklessClosedChannelException
extends ClosedChannelException {
    private static final long serialVersionUID = -2214806025529435136L;

    private StacklessClosedChannelException() {
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    static StacklessClosedChannelException newInstance(Class<?> clazz, String method2) {
        return ThrowableUtil.unknownStackTrace(new StacklessClosedChannelException(), clazz, method2);
    }
}

