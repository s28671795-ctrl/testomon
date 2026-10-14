/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalStableApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.impl.BoundedBuffer;
import akka.stream.impl.Buffer;
import akka.stream.impl.FixedSizeBuffer$;
import scala.reflect.ClassTag$;

public final class Buffer$ {
    public static final Buffer$ MODULE$ = new Buffer$();
    private static final int FixedQueueSize = 128;
    private static final int FixedQueueMask = 127;

    public int FixedQueueSize() {
        return FixedQueueSize;
    }

    public int FixedQueueMask() {
        return FixedQueueMask;
    }

    public <T> Buffer<T> apply(int size, Attributes effectiveAttributes) {
        return this.apply(size, ((ActorAttributes.MaxFixedBufferSize)effectiveAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.MaxFixedBufferSize.class))).size());
    }

    @InternalStableApi
    public <T> Buffer<T> apply(int size, int max) {
        if (size < this.FixedQueueSize() || size < max) {
            return FixedSizeBuffer$.MODULE$.apply(size);
        }
        return new BoundedBuffer(size);
    }

    private Buffer$() {
    }
}

