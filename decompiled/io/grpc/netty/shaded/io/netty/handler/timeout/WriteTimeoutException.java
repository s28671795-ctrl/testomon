/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.timeout;

import io.grpc.netty.shaded.io.netty.handler.timeout.TimeoutException;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;

public final class WriteTimeoutException
extends TimeoutException {
    private static final long serialVersionUID = -144786655770296065L;
    public static final WriteTimeoutException INSTANCE = PlatformDependent.javaVersion() >= 7 ? new WriteTimeoutException(true) : new WriteTimeoutException();

    public WriteTimeoutException() {
    }

    public WriteTimeoutException(String message) {
        super(message, false);
    }

    private WriteTimeoutException(boolean shared) {
        super(null, shared);
    }
}

