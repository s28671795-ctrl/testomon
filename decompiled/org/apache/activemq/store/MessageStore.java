/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.Service;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.IndexListener;
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStoreStatistics;
import org.apache.activemq.usage.MemoryUsage;

public interface MessageStore
extends Service {
    public void addMessage(ConnectionContext var1, Message var2) throws IOException;

    public void addMessage(ConnectionContext var1, Message var2, boolean var3) throws IOException;

    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext var1, Message var2) throws IOException;

    public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext var1, Message var2, boolean var3) throws IOException;

    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext var1, Message var2) throws IOException;

    public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext var1, Message var2, boolean var3) throws IOException;

    public Message getMessage(MessageId var1) throws IOException;

    public void removeMessage(ConnectionContext var1, MessageAck var2) throws IOException;

    public void removeAsyncMessage(ConnectionContext var1, MessageAck var2) throws IOException;

    public void removeAllMessages(ConnectionContext var1) throws IOException;

    public void recover(MessageRecoveryListener var1) throws Exception;

    public ActiveMQDestination getDestination();

    public void setMemoryUsage(MemoryUsage var1);

    public int getMessageCount() throws IOException;

    public long getMessageSize() throws IOException;

    public MessageStoreStatistics getMessageStoreStatistics();

    public void resetBatching();

    public void recoverNextMessages(int var1, MessageRecoveryListener var2) throws Exception;

    public void dispose(ConnectionContext var1);

    public void setBatch(MessageId var1) throws Exception;

    public boolean isEmpty() throws Exception;

    public void setPrioritizedMessages(boolean var1);

    public boolean isPrioritizedMessages();

    public void updateMessage(Message var1) throws IOException;

    public void registerIndexListener(IndexListener var1);
}

