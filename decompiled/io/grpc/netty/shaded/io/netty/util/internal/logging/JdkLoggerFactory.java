/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal.logging;

import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLogger;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLoggerFactory;
import io.grpc.netty.shaded.io.netty.util.internal.logging.JdkLogger;
import java.util.logging.Logger;

public class JdkLoggerFactory
extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new JdkLoggerFactory();

    @Deprecated
    public JdkLoggerFactory() {
    }

    @Override
    public InternalLogger newInstance(String name) {
        return new JdkLogger(Logger.getLogger(name));
    }
}

