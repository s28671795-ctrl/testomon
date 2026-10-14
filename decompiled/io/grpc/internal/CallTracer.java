/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.InternalChannelz;
import io.grpc.internal.LongCounter;
import io.grpc.internal.LongCounterFactory;
import io.grpc.internal.TimeProvider;

final class CallTracer {
    private final TimeProvider timeProvider;
    private final LongCounter callsStarted = LongCounterFactory.create();
    private final LongCounter callsSucceeded = LongCounterFactory.create();
    private final LongCounter callsFailed = LongCounterFactory.create();
    private volatile long lastCallStartedNanos;
    static final Factory DEFAULT_FACTORY = new Factory(){

        @Override
        public CallTracer create() {
            return new CallTracer(TimeProvider.SYSTEM_TIME_PROVIDER);
        }
    };

    CallTracer(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public void reportCallStarted() {
        this.callsStarted.add(1L);
        this.lastCallStartedNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportCallEnded(boolean success) {
        if (success) {
            this.callsSucceeded.add(1L);
        } else {
            this.callsFailed.add(1L);
        }
    }

    void updateBuilder(InternalChannelz.ChannelStats.Builder builder) {
        builder.setCallsStarted(this.callsStarted.value()).setCallsSucceeded(this.callsSucceeded.value()).setCallsFailed(this.callsFailed.value()).setLastCallStartedNanos(this.lastCallStartedNanos);
    }

    void updateBuilder(InternalChannelz.ServerStats.Builder builder) {
        builder.setCallsStarted(this.callsStarted.value()).setCallsSucceeded(this.callsSucceeded.value()).setCallsFailed(this.callsFailed.value()).setLastCallStartedNanos(this.lastCallStartedNanos);
    }

    public static Factory getDefaultFactory() {
        return DEFAULT_FACTORY;
    }

    public static interface Factory {
        public CallTracer create();
    }
}

