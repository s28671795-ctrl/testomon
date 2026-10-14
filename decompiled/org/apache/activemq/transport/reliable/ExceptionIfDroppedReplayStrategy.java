/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import org.apache.activemq.transport.reliable.ReliableTransport;
import org.apache.activemq.transport.reliable.ReplayStrategy;

public class ExceptionIfDroppedReplayStrategy
implements ReplayStrategy {
    private int maximumDifference = 5;

    public ExceptionIfDroppedReplayStrategy() {
    }

    public ExceptionIfDroppedReplayStrategy(int maximumDifference) {
        this.maximumDifference = maximumDifference;
    }

    @Override
    public boolean onDroppedPackets(ReliableTransport transport, int expectedCounter, int actualCounter, int nextAvailableCounter) throws IOException {
        int difference = actualCounter - expectedCounter;
        long count = Math.abs(difference);
        if (count > (long)this.maximumDifference) {
            throw new IOException("Packets dropped on: " + transport + " count: " + count + " expected: " + expectedCounter + " but was: " + actualCounter);
        }
        return difference > 0;
    }

    @Override
    public void onReceivedPacket(ReliableTransport transport, long expectedCounter) {
    }

    public int getMaximumDifference() {
        return this.maximumDifference;
    }

    public void setMaximumDifference(int maximumDifference) {
        this.maximumDifference = maximumDifference;
    }
}

