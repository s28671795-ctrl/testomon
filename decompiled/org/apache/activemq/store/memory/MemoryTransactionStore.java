/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store.memory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.XATransactionId;
import org.apache.activemq.store.InlineListenableFuture;
import org.apache.activemq.store.ListenableFuture;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.ProxyMessageStore;
import org.apache.activemq.store.ProxyTopicMessageStore;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.store.TransactionRecoveryListener;
import org.apache.activemq.store.TransactionStore;

public class MemoryTransactionStore
implements TransactionStore {
    protected ConcurrentMap<Object, Tx> inflightTransactions = new ConcurrentHashMap<Object, Tx>();
    protected Map<TransactionId, Tx> preparedTransactions = Collections.synchronizedMap(new LinkedHashMap());
    protected final PersistenceAdapter persistenceAdapter;
    private boolean doingRecover;

    public MemoryTransactionStore(PersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    public MessageStore proxy(MessageStore messageStore) {
        ProxyMessageStore proxyMessageStore = new ProxyMessageStore(messageStore){

            @Override
            public void addMessage(ConnectionContext context, Message send) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), send);
            }

            @Override
            public void addMessage(ConnectionContext context, Message send, boolean canOptimize) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), send);
            }

            @Override
            public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), message);
                return new InlineListenableFuture();
            }

            @Override
            public ListenableFuture<Object> asyncAddQueueMessage(ConnectionContext context, Message message, boolean canoptimize) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), message);
                return new InlineListenableFuture();
            }

            @Override
            public void removeMessage(ConnectionContext context, MessageAck ack) throws IOException {
                MemoryTransactionStore.this.removeMessage(this.getDelegate(), ack);
            }

            @Override
            public void removeAsyncMessage(ConnectionContext context, MessageAck ack) throws IOException {
                MemoryTransactionStore.this.removeMessage(this.getDelegate(), ack);
            }
        };
        this.onProxyQueueStore(proxyMessageStore);
        return proxyMessageStore;
    }

    protected void onProxyQueueStore(ProxyMessageStore proxyMessageStore) {
    }

    public TopicMessageStore proxy(TopicMessageStore messageStore) {
        ProxyTopicMessageStore proxyTopicMessageStore = new ProxyTopicMessageStore(messageStore){

            @Override
            public void addMessage(ConnectionContext context, Message send) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), send);
            }

            @Override
            public void addMessage(ConnectionContext context, Message send, boolean canOptimize) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), send);
            }

            @Override
            public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), message);
                return new InlineListenableFuture();
            }

            @Override
            public ListenableFuture<Object> asyncAddTopicMessage(ConnectionContext context, Message message, boolean canOptimize) throws IOException {
                MemoryTransactionStore.this.addMessage(context, this.getDelegate(), message);
                return new InlineListenableFuture();
            }

            @Override
            public void removeMessage(ConnectionContext context, MessageAck ack) throws IOException {
                MemoryTransactionStore.this.removeMessage(this.getDelegate(), ack);
            }

            @Override
            public void removeAsyncMessage(ConnectionContext context, MessageAck ack) throws IOException {
                MemoryTransactionStore.this.removeMessage(this.getDelegate(), ack);
            }

            @Override
            public void acknowledge(ConnectionContext context, String clientId, String subscriptionName, MessageId messageId, MessageAck ack) throws IOException {
                MemoryTransactionStore.this.acknowledge((TopicMessageStore)this.getDelegate(), clientId, subscriptionName, messageId, ack);
            }
        };
        this.onProxyTopicStore(proxyTopicMessageStore);
        return proxyTopicMessageStore;
    }

    protected void onProxyTopicStore(ProxyTopicMessageStore proxyTopicMessageStore) {
    }

    @Override
    public void prepare(TransactionId txid) throws IOException {
        Tx tx = (Tx)this.inflightTransactions.remove(txid);
        if (tx == null) {
            return;
        }
        this.preparedTransactions.put(txid, tx);
    }

    public Tx getTx(Object txid) {
        Tx tx = (Tx)this.inflightTransactions.get(txid);
        if (tx == null) {
            tx = new Tx();
            this.inflightTransactions.put(txid, tx);
        }
        return tx;
    }

    public Tx getPreparedTx(TransactionId txid) {
        Tx tx = this.preparedTransactions.get(txid);
        if (tx == null) {
            tx = new Tx();
            this.preparedTransactions.put(txid, tx);
        }
        return tx;
    }

    @Override
    public void commit(TransactionId txid, boolean wasPrepared, Runnable preCommit, Runnable postCommit) throws IOException {
        Tx tx;
        if (preCommit != null) {
            preCommit.run();
        }
        if ((tx = wasPrepared ? this.preparedTransactions.remove(txid) : (Tx)this.inflightTransactions.remove(txid)) != null) {
            tx.commit();
        }
        if (postCommit != null) {
            postCommit.run();
        }
    }

    @Override
    public void rollback(TransactionId txid) throws IOException {
        this.preparedTransactions.remove(txid);
        this.inflightTransactions.remove(txid);
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void recover(TransactionRecoveryListener listener) throws IOException {
        this.inflightTransactions.clear();
        this.doingRecover = true;
        try {
            for (TransactionId txid : this.preparedTransactions.keySet()) {
                Tx tx = this.preparedTransactions.get(txid);
                listener.recover((XATransactionId)txid, tx.getMessages(), tx.getAcks());
                this.onRecovered(tx);
            }
        }
        finally {
            this.doingRecover = false;
        }
    }

    protected void onRecovered(Tx tx) {
    }

    void addMessage(ConnectionContext context, final MessageStore destination, final Message message) throws IOException {
        if (this.doingRecover) {
            return;
        }
        if (message.getTransactionId() != null) {
            Tx tx = this.getTx(message.getTransactionId());
            tx.add(new AddMessageCommand(){
                MessageStore messageStore;
                {
                    this.messageStore = destination;
                }

                @Override
                public Message getMessage() {
                    return message;
                }

                @Override
                public MessageStore getMessageStore() {
                    return destination;
                }

                @Override
                public void run(ConnectionContext ctx) throws IOException {
                    destination.addMessage(ctx, message);
                }

                @Override
                public void setMessageStore(MessageStore messageStore) {
                    this.messageStore = messageStore;
                }
            });
        } else {
            destination.addMessage(context, message);
        }
    }

    final void removeMessage(final MessageStore destination, final MessageAck ack) throws IOException {
        if (this.doingRecover) {
            return;
        }
        if (ack.isInTransaction()) {
            Tx tx = this.getTx(ack.getTransactionId());
            tx.add(new RemoveMessageCommand(){

                @Override
                public MessageAck getMessageAck() {
                    return ack;
                }

                @Override
                public void run(ConnectionContext ctx) throws IOException {
                    destination.removeMessage(ctx, ack);
                }

                @Override
                public MessageStore getMessageStore() {
                    return destination;
                }
            });
        } else {
            destination.removeMessage(null, ack);
        }
    }

    public void acknowledge(final TopicMessageStore destination, final String clientId, final String subscriptionName, final MessageId messageId, final MessageAck ack) throws IOException {
        if (this.doingRecover) {
            return;
        }
        if (ack.isInTransaction()) {
            Tx tx = this.getTx(ack.getTransactionId());
            tx.add(new RemoveMessageCommand(){

                @Override
                public MessageAck getMessageAck() {
                    return ack;
                }

                @Override
                public void run(ConnectionContext ctx) throws IOException {
                    destination.acknowledge(ctx, clientId, subscriptionName, messageId, ack);
                }

                @Override
                public MessageStore getMessageStore() {
                    return destination;
                }
            });
        } else {
            destination.acknowledge(null, clientId, subscriptionName, messageId, ack);
        }
    }

    public void delete() {
        this.inflightTransactions.clear();
        this.preparedTransactions.clear();
        this.doingRecover = false;
    }

    public static interface RemoveMessageCommand {
        public MessageAck getMessageAck();

        public void run(ConnectionContext var1) throws IOException;

        public MessageStore getMessageStore();
    }

    public static interface AddMessageCommand {
        public Message getMessage();

        public MessageStore getMessageStore();

        public void run(ConnectionContext var1) throws IOException;

        public void setMessageStore(MessageStore var1);
    }

    public class Tx {
        public ArrayList<AddMessageCommand> messages = new ArrayList();
        public final ArrayList<RemoveMessageCommand> acks = new ArrayList();

        public void add(AddMessageCommand msg) {
            this.messages.add(msg);
        }

        public void add(RemoveMessageCommand ack) {
            this.acks.add(ack);
        }

        public Message[] getMessages() {
            Message[] rc = new Message[this.messages.size()];
            int count = 0;
            for (AddMessageCommand cmd : this.messages) {
                rc[count++] = cmd.getMessage();
            }
            return rc;
        }

        public MessageAck[] getAcks() {
            MessageAck[] rc = new MessageAck[this.acks.size()];
            int count = 0;
            for (RemoveMessageCommand cmd : this.acks) {
                rc[count++] = cmd.getMessageAck();
            }
            return rc;
        }

        public void commit() throws IOException {
            ConnectionContext ctx = new ConnectionContext();
            MemoryTransactionStore.this.persistenceAdapter.beginTransaction(ctx);
            try {
                for (AddMessageCommand addMessageCommand : this.messages) {
                    addMessageCommand.run(ctx);
                }
                for (RemoveMessageCommand removeMessageCommand : this.acks) {
                    removeMessageCommand.run(ctx);
                }
            }
            catch (IOException e) {
                MemoryTransactionStore.this.persistenceAdapter.rollbackTransaction(ctx);
                throw e;
            }
            MemoryTransactionStore.this.persistenceAdapter.commitTransaction(ctx);
        }
    }
}

