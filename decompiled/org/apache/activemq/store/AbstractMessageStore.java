/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.IndexListener;
import org.apache.activemq.store.InlineListenableFuture;
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.MessageStoreStatistics;
import org.apache.activemq.usage.MemoryUsage;

public abstract class AbstractMessageStore
implements MessageStore {
    public static final ListenableFuture<Object> FUTURE = new InlineListenableFuture();
    protected final ActiveMQDestination destination;
    protected boolean prioritizedMessages;
    protected IndexListener indexListener;
    protected final MessageStoreStatistics messageStoreStatistics = new MessageStoreStatistics();

    public AbstractMessageStore(ActiveMQDestination destination) {
        this.destination = destination;
    }

    @Override
    public void dispose(ConnectionContext context) {
    }

    @Override
    public void start() throws Exception {
        this.recoverMessageStoreStatistics();
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    @Override
    public void setMemoryUsage(MemoryUsage memoryUsage) {
    }

    @Override
    public void setBatch(MessageId messageId) throws IOException, Exception {
    }

    @Override
    public boolean isEmpty() throws Exception {
        return this.getMessageCount() == 0;
    }

    @Override
    public void setPrioritizedMessages(boolean prioritizedMessages) {
        this.prioritizedMessages = prioritizedMessages;
    }

    @Override
    public boolean isPrioritizedMessages() {
        return this.prioritizedMessages;
    }

    @Override
    public void addMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        this.addMessage(context, message);
    }

    @Override
    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message) throws IOException {
        this.addMessage(context, message);
        return FUTURE;
    }

    @Override
    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        this.addMessage(context, message, canOptimizeHint);
        return FUTURE;
    }

    @Override
    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        this.addMessage(context, message, canOptimizeHint);
        return FUTURE;
    }

    @Override
    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message) throws IOException {
        this.addMessage(context, message);
        return new InlineListenableFuture();
    }

    @Override
    public void removeAsyncMessage(ConnectionContext context, MessageAck ack) throws IOException {
        this.removeMessage(context, ack);
    }

    @Override
    public void updateMessage(Message message) throws IOException {
        throw new IOException("update is not supported by: " + this);
    }

    @Override
    public void registerIndexListener(IndexListener indexListener) {
        this.indexListener = indexListener;
    }

    public IndexListener getIndexListener() {
        return this.indexListener;
    }

    @Override
    public int getMessageCount() throws IOException {
        return (int)this.getMessageStoreStatistics().getMessageCount().getCount();
    }

    @Override
    public long getMessageSize() throws IOException {
        return this.getMessageStoreStatistics().getMessageSize().getTotalSize();
    }

    @Override
    public MessageStoreStatistics getMessageStoreStatistics() {
        return this.messageStoreStatistics;
    }

    protected void recoverMessageStoreStatistics() throws IOException {
    }
}

