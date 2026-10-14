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
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.MessageStoreStatistics;
import org.apache.activemq.usage.MemoryUsage;

public class ProxyMessageStore
implements MessageStore {
    final MessageStore delegate;

    public ProxyMessageStore(MessageStore delegate) {
        this.delegate = delegate;
    }

    public MessageStore getDelegate() {
        return this.delegate;
    }

    @Override
    public void addMessage(ConnectionContext context, Message message) throws IOException {
        this.delegate.addMessage(context, message);
    }

    @Override
    public void addMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        this.delegate.addMessage(context, message, canOptimizeHint);
    }

    @Override
    public Message getMessage(MessageId identity) throws IOException {
        return this.delegate.getMessage(identity);
    }

    @Override
    public void recover(MessageRecoveryListener listener) throws Exception {
        this.delegate.recover(listener);
    }

    @Override
    public void removeAllMessages(ConnectionContext context) throws IOException {
        this.delegate.removeAllMessages(context);
    }

    @Override
    public void removeMessage(ConnectionContext context, MessageAck ack) throws IOException {
        this.delegate.removeMessage(context, ack);
    }

    @Override
    public void start() throws Exception {
        this.delegate.start();
    }

    @Override
    public void stop() throws Exception {
        this.delegate.stop();
    }

    @Override
    public void dispose(ConnectionContext context) {
        this.delegate.dispose(context);
    }

    @Override
    public ActiveMQDestination getDestination() {
        return this.delegate.getDestination();
    }

    @Override
    public void setMemoryUsage(MemoryUsage memoryUsage) {
        this.delegate.setMemoryUsage(memoryUsage);
    }

    @Override
    public int getMessageCount() throws IOException {
        return this.delegate.getMessageCount();
    }

    @Override
    public long getMessageSize() throws IOException {
        return this.delegate.getMessageSize();
    }

    @Override
    public void recoverNextMessages(int maxReturned, MessageRecoveryListener listener) throws Exception {
        this.delegate.recoverNextMessages(maxReturned, listener);
    }

    @Override
    public void resetBatching() {
        this.delegate.resetBatching();
    }

    @Override
    public void setBatch(MessageId messageId) throws Exception {
        this.delegate.setBatch(messageId);
    }

    @Override
    public boolean isEmpty() throws Exception {
        return this.delegate.isEmpty();
    }

    @Override
    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message) throws IOException {
        return this.delegate.asyncAddQueueMessage(context, message);
    }

    @Override
    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        return this.delegate.asyncAddQueueMessage(context, message, canOptimizeHint);
    }

    @Override
    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message) throws IOException {
        return this.delegate.asyncAddTopicMessage(context, message);
    }

    @Override
    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message, boolean canOptimizeHint) throws IOException {
        return this.delegate.asyncAddTopicMessage(context, message, canOptimizeHint);
    }

    @Override
    public void removeAsyncMessage(ConnectionContext context, MessageAck ack) throws IOException {
        this.delegate.removeAsyncMessage(context, ack);
    }

    @Override
    public void setPrioritizedMessages(boolean prioritizedMessages) {
        this.delegate.setPrioritizedMessages(prioritizedMessages);
    }

    @Override
    public boolean isPrioritizedMessages() {
        return this.delegate.isPrioritizedMessages();
    }

    @Override
    public void updateMessage(Message message) throws IOException {
        this.delegate.updateMessage(message);
    }

    @Override
    public void registerIndexListener(IndexListener indexListener) {
        this.delegate.registerIndexListener(indexListener);
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public MessageStoreStatistics getMessageStoreStatistics() {
        return this.delegate.getMessageStoreStatistics();
    }
}

