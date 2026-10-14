/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.Map;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.PendingNode;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.management.SizeStatisticImpl;

public class PendingMessageHelper {
    private final Map<MessageId, PendingNode> map;
    private final SizeStatisticImpl messageSize;

    public PendingMessageHelper(Map<MessageId, PendingNode> map, SizeStatisticImpl messageSize) {
        this.map = map;
        this.messageSize = messageSize;
    }

    public void addToMap(MessageReference message, PendingNode node) {
        PendingNode previous = this.map.put(message.getMessageId(), node);
        if (previous != null) {
            try {
                this.messageSize.addSize(-previous.getMessage().getSize());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            this.messageSize.addSize(message.getSize());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public PendingNode removeFromMap(MessageReference message) {
        PendingNode removed = this.map.remove(message.getMessageId());
        if (removed != null) {
            try {
                this.messageSize.addSize(-removed.getMessage().getSize());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return removed;
    }
}

