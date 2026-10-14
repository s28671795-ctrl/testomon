/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Decompressor;
import io.grpc.internal.GzipInflatingBuffer;
import io.grpc.internal.ReadableBuffer;

public interface Deframer {
    public void setMaxInboundMessageSize(int var1);

    public void setDecompressor(Decompressor var1);

    public void setFullStreamDecompressor(GzipInflatingBuffer var1);

    public void request(int var1);

    public void deframe(ReadableBuffer var1);

    public void closeWhenComplete();

    public void close();
}

