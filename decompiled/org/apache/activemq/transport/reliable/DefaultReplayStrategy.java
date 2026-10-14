/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import org.apache.activemq.transport.reliable.ReliableTransport;
import org.apache.activemq.transport.reliable.ReplayStrategy;

public class DefaultReplayStrategy
implements ReplayStrategy {
    private int maximumDifference = 5;

    public DefaultReplayStrategy() {
    }

    public DefaultReplayStrategy(int maximumDifference) {
        this.maximumDifference = maximumDifference;
    }

    @Override
    public boolean onDroppedPackets(ReliableTransport transport, int expectedCounter, int actualCounter, int nextAvailableCounter) throws IOException {
        int difference = actualCounter - expectedCounter;
        long count = Math.abs(difference);
        if (count > (long)this.maximumDifference) {
            int upperLimit = actualCounter - 1;
            if (upperLimit < expectedCounter) {
                upperLimit = expectedCounter;
            }
            transport.requestReplay(expectedCounter, upperLimit);
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

