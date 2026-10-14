/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store.memory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.NoLocalSubscriptionAware;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.ProxyMessageStore;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.store.TransactionStore;
import org.apache.activemq.store.memory.MemoryMessageStore;
import org.apache.activemq.store.memory.MemoryTopicMessageStore;
import org.apache.activemq.store.memory.MemoryTransactionStore;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryPersistenceAdapter
implements PersistenceAdapter,
NoLocalSubscriptionAware {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryPersistenceAdapter.class);
    MemoryTransactionStore transactionStore;
    ConcurrentMap<ActiveMQDestination, TopicMessageStore> topics = new ConcurrentHashMap<ActiveMQDestination, TopicMessageStore>();
    ConcurrentMap<ActiveMQDestination, MessageStore> queues = new ConcurrentHashMap<ActiveMQDestination, MessageStore>();
    private boolean useExternalMessageReferences;

    @Override
    public Set<ActiveMQDestination> getDestinations() {
        HashSet<ActiveMQDestination> rc = new HashSet<ActiveMQDestination>(this.queues.size() + this.topics.size());
        Iterator iter = this.queues.keySet().iterator();
        while (iter.hasNext()) {
            rc.add((ActiveMQDestination)iter.next());
        }
        iter = this.topics.keySet().iterator();
        while (iter.hasNext()) {
            rc.add((ActiveMQDestination)iter.next());
        }
        return rc;
    }

    public static MemoryPersistenceAdapter newInstance(File file) {
        return new MemoryPersistenceAdapter();
    }

    @Override
    public MessageStore createQueueMessageStore(ActiveMQQueue destination) throws IOException {
        MessageStore rc = (MessageStore)this.queues.get(destination);
        if (rc == null) {
            rc = new MemoryMessageStore(destination);
            if (this.transactionStore != null) {
                rc = this.transactionStore.proxy(rc);
            }
            this.queues.put(destination, rc);
        }
        return rc;
    }

    @Override
    public TopicMessageStore createTopicMessageStore(ActiveMQTopic destination) throws IOException {
        TopicMessageStore rc = (TopicMessageStore)this.topics.get(destination);
        if (rc == null) {
            rc = new MemoryTopicMessageStore(destination);
            if (this.transactionStore != null) {
                rc = this.transactionStore.proxy(rc);
            }
            this.topics.put(destination, rc);
        }
        return rc;
    }

    @Override
    public void removeQueueMessageStore(ActiveMQQueue destination) {
        this.queues.remove(destination);
    }

    @Override
    public void removeTopicMessageStore(ActiveMQTopic destination) {
        this.topics.remove(destination);
    }

    @Override
    public TransactionStore createTransactionStore() throws IOException {
        if (this.transactionStore == null) {
            this.transactionStore = new MemoryTransactionStore(this);
        }
        return this.transactionStore;
    }

    @Override
    public void beginTransaction(ConnectionContext context) {
    }

    @Override
    public void commitTransaction(ConnectionContext context) {
    }

    @Override
    public void rollbackTransaction(ConnectionContext context) {
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public long getLastMessageBrokerSequenceId() throws IOException {
        return 0L;
    }

    @Override
    public void deleteAllMessages() throws IOException {
        MemoryMessageStore store;
        Iterator iter = this.topics.values().iterator();
        while (iter.hasNext()) {
            store = this.asMemoryMessageStore(iter.next());
            if (store == null) continue;
            store.delete();
        }
        iter = this.queues.values().iterator();
        while (iter.hasNext()) {
            store = this.asMemoryMessageStore(iter.next());
            if (store == null) continue;
            store.delete();
        }
        if (this.transactionStore != null) {
            this.transactionStore.delete();
        }
    }

    public boolean isUseExternalMessageReferences() {
        return this.useExternalMessageReferences;
    }

    public void setUseExternalMessageReferences(boolean useExternalMessageReferences) {
        this.useExternalMessageReferences = useExternalMessageReferences;
    }

    protected MemoryMessageStore asMemoryMessageStore(Object value) {
        MessageStore delegate;
        if (value instanceof MemoryMessageStore) {
            return (MemoryMessageStore)value;
        }
        if (value instanceof ProxyMessageStore && (delegate = ((ProxyMessageStore)value).getDelegate()) instanceof MemoryMessageStore) {
            return (MemoryMessageStore)delegate;
        }
        LOG.warn("Expected an instance of MemoryMessageStore but was: " + value);
        return null;
    }

    @Override
    public void setUsageManager(SystemUsage usageManager) {
    }

    public String toString() {
        return "MemoryPersistenceAdapter";
    }

    @Override
    public void setBrokerName(String brokerName) {
    }

    @Override
    public void setDirectory(File dir) {
    }

    @Override
    public File getDirectory() {
        return null;
    }

    @Override
    public void checkpoint(boolean sync) throws IOException {
    }

    @Override
    public long size() {
        return 0L;
    }

    public void setCreateTransactionStore(boolean create) throws IOException {
        if (create) {
            this.createTransactionStore();
        }
    }

    @Override
    public long getLastProducerSequenceId(ProducerId id) {
        return -1L;
    }

    @Override
    public void allowIOResumption() {
    }

    @Override
    public JobSchedulerStore createJobSchedulerStore() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPersistNoLocal() {
        return true;
    }
}

