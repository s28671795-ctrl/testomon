/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Internal;
import io.grpc.netty.shaded.io.grpc.netty.GracefulServerCloseCommand;
import java.util.concurrent.TimeUnit;

@Internal
public final class InternalGracefulServerCloseCommand {
    private InternalGracefulServerCloseCommand() {
    }

    public static Object create(String goAwayDebugString) {
        return new GracefulServerCloseCommand(goAwayDebugString);
    }

    public static Object create(String goAwayDebugString, long graceTime, TimeUnit graceTimeUnit) {
        return new GracefulServerCloseCommand(goAwayDebugString, graceTime, graceTimeUnit);
    }
}

