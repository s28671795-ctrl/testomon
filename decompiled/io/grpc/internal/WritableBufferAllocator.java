/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.WritableBuffer;

public interface WritableBufferAllocator {
    public WritableBuffer allocate(int var1);
}

