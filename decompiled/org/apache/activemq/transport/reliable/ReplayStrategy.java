/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import org.apache.activemq.transport.reliable.ReliableTransport;

public interface ReplayStrategy {
    public boolean onDroppedPackets(ReliableTransport var1, int var2, int var3, int var4) throws IOException;

    public void onReceivedPacket(ReliableTransport var1, long var2);
}

