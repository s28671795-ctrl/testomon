/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Compressor;
import java.io.InputStream;

public interface Framer {
    public void writePayload(InputStream var1);

    public void flush();

    public boolean isClosed();

    public void close();

    public void dispose();

    public Framer setMessageCompression(boolean var1);

    public Framer setCompressor(Compressor var1);

    public void setMaxOutboundMessageSize(int var1);
}

