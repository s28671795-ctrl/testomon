/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.activemq.transport.Transport;

public interface WSTransport
extends Transport {
    public int getMaxFrameSize();

    public String getSubProtocol();

    public void setTransportSink(WSTransportSink var1);

    public void onWebSocketText(String var1) throws IOException;

    public void onWebSocketBinary(ByteBuffer var1) throws IOException;

    public void onWebSocketClosed() throws IOException;

    public static interface WSTransportSink {
        public void onSocketOutboundText(String var1) throws IOException;

        public void onSocketOutboundBinary(ByteBuffer var1) throws IOException;
    }
}

