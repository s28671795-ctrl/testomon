/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.WriteCallback;

public interface RemoteEndpoint {
    public void sendBytes(ByteBuffer var1) throws IOException;

    public void sendBytes(ByteBuffer var1, WriteCallback var2);

    public void sendPartialBytes(ByteBuffer var1, boolean var2) throws IOException;

    public void sendPartialBytes(ByteBuffer var1, boolean var2, WriteCallback var3);

    public void sendString(String var1) throws IOException;

    public void sendString(String var1, WriteCallback var2);

    public void sendPartialString(String var1, boolean var2) throws IOException;

    public void sendPartialString(String var1, boolean var2, WriteCallback var3) throws IOException;

    public void sendPing(ByteBuffer var1) throws IOException;

    public void sendPing(ByteBuffer var1, WriteCallback var2);

    public void sendPong(ByteBuffer var1) throws IOException;

    public void sendPong(ByteBuffer var1, WriteCallback var2);

    public BatchMode getBatchMode();

    public void setBatchMode(BatchMode var1);

    public int getMaxOutgoingFrames();

    public void setMaxOutgoingFrames(int var1);

    public SocketAddress getRemoteAddress();

    public void flush() throws IOException;
}

