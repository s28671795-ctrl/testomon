/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.memory.buffer.MessageBuffer;
import org.apache.activemq.memory.buffer.MessageQueue;
import org.apache.activemq.memory.buffer.OrderBasedMessageBuffer;
import org.apache.activemq.memory.list.MessageList;

public class DestinationBasedMessageList
implements MessageList {
    private MessageBuffer messageBuffer;
    private Map<ActiveMQDestination, MessageQueue> queueIndex = new HashMap<ActiveMQDestination, MessageQueue>();
    private DestinationMap subscriptionIndex = new DestinationMap();
    private Object lock = new Object();

    public DestinationBasedMessageList(int maximumSize) {
        this(new OrderBasedMessageBuffer(maximumSize));
    }

    public DestinationBasedMessageList(MessageBuffer buffer) {
        this.messageBuffer = buffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(MessageReference node) {
        ActiveMQMessage message = (ActiveMQMessage)node.getMessageHardRef();
        ActiveMQDestination destination = message.getDestination();
        MessageQueue queue = null;
        Object object = this.lock;
        synchronized (object) {
            queue = this.queueIndex.get(destination);
            if (queue == null) {
                queue = this.messageBuffer.createMessageQueue();
                this.queueIndex.put(destination, queue);
                this.subscriptionIndex.put(destination, queue);
            }
        }
        queue.add(node);
    }

    public List<MessageReference> getMessages(Subscription sub) {
        return this.getMessages(sub.getConsumerInfo().getDestination());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<MessageReference> getMessages(ActiveMQDestination destination) {
        Set set = null;
        Object object = this.lock;
        synchronized (object) {
            set = this.subscriptionIndex.get(destination);
        }
        ArrayList<MessageReference> answer = new ArrayList<MessageReference>();
        for (MessageQueue queue : set) {
            queue.appendMessages(answer);
        }
        return answer;
    }

    @Override
    public Message[] browse(ActiveMQDestination destination) {
        List<MessageReference> result = this.getMessages(destination);
        return result.toArray(new Message[result.size()]);
    }

    @Override
    public void clear() {
        this.messageBuffer.clear();
    }
}

