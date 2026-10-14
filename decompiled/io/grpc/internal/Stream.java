/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Compressor;
import java.io.InputStream;

public interface Stream {
    public void request(int var1);

    public void writeMessage(InputStream var1);

    public void flush();

    public boolean isReady();

    public void optimizeForDirectExecutor();

    public void setCompressor(Compressor var1);

    public void setMessageCompression(boolean var1);
}

