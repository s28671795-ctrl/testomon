/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store.memory;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.AbstractMessageStore;
import org.apache.activemq.store.IndexListener;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStoreStatistics;

public class MemoryMessageStore
extends AbstractMessageStore {
    protected final Map<MessageId, Message> messageTable;
    protected MessageId lastBatchId;
    protected long sequenceId;

    public MemoryMessageStore(ActiveMQDestination destination) {
        this(destination, new LinkedHashMap<MessageId, Message>());
    }

    public MemoryMessageStore(ActiveMQDestination destination, Map<MessageId, Message> messageTable) {
        super(destination);
        this.messageTable = Collections.synchronizedMap(messageTable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void addMessage(ConnectionContext context, Message message) throws IOException {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            this.messageTable.put(message.getMessageId(), message);
            MemoryMessageStore.incMessageStoreStatistics(this.getMessageStoreStatistics(), message);
            message.incrementReferenceCount();
            message.getMessageId().setFutureOrSequenceLong(this.sequenceId++);
            if (this.indexListener != null) {
                this.indexListener.onAdd(new IndexListener.MessageContext(context, message, null));
            }
        }
    }

    @Override
    public Message getMessage(MessageId identity) throws IOException {
        return this.messageTable.get(identity);
    }

    @Override
    public void removeMessage(ConnectionContext context, MessageAck ack) throws IOException {
        this.removeMessage(ack.getLastMessageId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMessage(MessageId msgId) throws IOException {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            Message removed = this.messageTable.remove(msgId);
            if (removed != null) {
                removed.decrementReferenceCount();
                MemoryMessageStore.decMessageStoreStatistics(this.getMessageStoreStatistics(), removed);
            }
            if (this.lastBatchId != null && this.lastBatchId.equals(msgId) || this.messageTable.isEmpty()) {
                this.lastBatchId = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void recover(MessageRecoveryListener listener) throws Exception {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            for (Message message : this.messageTable.values()) {
                listener.recoverMessage(message);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeAllMessages(ConnectionContext context) throws IOException {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            this.messageTable.clear();
            this.getMessageStoreStatistics().reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void delete() {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            this.messageTable.clear();
            this.getMessageStoreStatistics().reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void recoverNextMessages(int maxReturned, MessageRecoveryListener listener) throws Exception {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            boolean pastLackBatch = this.lastBatchId == null;
            for (Map.Entry<MessageId, Message> entry : this.messageTable.entrySet()) {
                if (pastLackBatch) {
                    Message msg = entry.getValue();
                    this.lastBatchId = entry.getKey();
                    if (msg.getClass() == MessageId.class) {
                        listener.recoverMessageReference((MessageId)((Object)msg));
                        continue;
                    }
                    listener.recoverMessage(msg);
                    continue;
                }
                pastLackBatch = entry.getKey().equals(this.lastBatchId);
            }
        }
    }

    @Override
    public void resetBatching() {
        this.lastBatchId = null;
    }

    @Override
    public void setBatch(MessageId messageId) {
        this.lastBatchId = messageId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateMessage(Message message) {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            Message original = this.messageTable.get(message.getMessageId());
            if (original == null) {
                this.getMessageStoreStatistics().getMessageCount().increment();
            } else {
                this.getMessageStoreStatistics().getMessageSize().addSize(-original.getSize());
            }
            this.messageTable.put(message.getMessageId(), message);
            this.getMessageStoreStatistics().getMessageSize().addSize(message.getSize());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void recoverMessageStoreStatistics() throws IOException {
        Map<MessageId, Message> map = this.messageTable;
        synchronized (map) {
            long size = 0L;
            int count = 0;
            for (Message message : this.messageTable.values()) {
                size += (long)message.getSize();
            }
            this.getMessageStoreStatistics().reset();
            this.getMessageStoreStatistics().getMessageCount().setCount(count);
            this.getMessageStoreStatistics().getMessageSize().setTotalSize(size);
        }
    }

    protected static final void incMessageStoreStatistics(MessageStoreStatistics stats, Message message) {
        if (stats != null && message != null) {
            stats.getMessageCount().increment();
            stats.getMessageSize().addSize(message.getSize());
        }
    }

    protected static final void decMessageStoreStatistics(MessageStoreStatistics stats, Message message) {
        if (stats != null && message != null) {
            stats.getMessageCount().decrement();
            stats.getMessageSize().addSize(-message.getSize());
        }
    }
}

