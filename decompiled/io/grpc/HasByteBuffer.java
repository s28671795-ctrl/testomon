/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public interface HasByteBuffer {
    public boolean byteBufferSupported();

    @Nullable
    public ByteBuffer getByteBuffer();
}

