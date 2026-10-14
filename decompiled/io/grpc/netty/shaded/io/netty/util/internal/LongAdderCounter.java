/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal;

import io.grpc.netty.shaded.io.netty.util.internal.LongCounter;
import io.grpc.netty.shaded.io.netty.util.internal.SuppressJava6Requirement;
import java.util.concurrent.atomic.LongAdder;

@SuppressJava6Requirement(reason="Usage guarded by java version check")
final class LongAdderCounter
extends LongAdder
implements LongCounter {
    LongAdderCounter() {
    }

    @Override
    public long value() {
        return this.longValue();
    }
}

