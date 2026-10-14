/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import java.util.concurrent.TimeUnit;

class GracefulServerCloseCommand
extends WriteQueue.AbstractQueuedCommand {
    private final String goAwayDebugString;
    private final long graceTime;
    private final TimeUnit graceTimeUnit;

    public GracefulServerCloseCommand(String goAwayDebugString) {
        this(goAwayDebugString, -1L, null);
    }

    public GracefulServerCloseCommand(String goAwayDebugString, long graceTime, TimeUnit graceTimeUnit) {
        this.goAwayDebugString = Preconditions.checkNotNull(goAwayDebugString, "goAwayDebugString");
        this.graceTime = graceTime;
        this.graceTimeUnit = graceTimeUnit;
    }

    public String getGoAwayDebugString() {
        return this.goAwayDebugString;
    }

    public long getGraceTime() {
        return this.graceTime;
    }

    public TimeUnit getGraceTimeUnit() {
        return this.graceTimeUnit;
    }
}

