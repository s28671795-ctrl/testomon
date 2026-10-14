/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.apache.activemq.Service;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.store.TransactionStore;
import org.apache.activemq.usage.SystemUsage;

public interface PersistenceAdapter
extends Service {
    public Set<ActiveMQDestination> getDestinations();

    public MessageStore createQueueMessageStore(ActiveMQQueue var1) throws IOException;

    public TopicMessageStore createTopicMessageStore(ActiveMQTopic var1) throws IOException;

    public JobSchedulerStore createJobSchedulerStore() throws IOException, UnsupportedOperationException;

    public void removeQueueMessageStore(ActiveMQQueue var1);

    public void removeTopicMessageStore(ActiveMQTopic var1);

    public TransactionStore createTransactionStore() throws IOException;

    public void beginTransaction(ConnectionContext var1) throws IOException;

    public void commitTransaction(ConnectionContext var1) throws IOException;

    public void rollbackTransaction(ConnectionContext var1) throws IOException;

    public long getLastMessageBrokerSequenceId() throws IOException;

    public void deleteAllMessages() throws IOException;

    public void setUsageManager(SystemUsage var1);

    public void setBrokerName(String var1);

    public void setDirectory(File var1);

    public File getDirectory();

    public void checkpoint(boolean var1) throws IOException;

    public long size();

    public long getLastProducerSequenceId(ProducerId var1) throws IOException;

    public void allowIOResumption();
}

