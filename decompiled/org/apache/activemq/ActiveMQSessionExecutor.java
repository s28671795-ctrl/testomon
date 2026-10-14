/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.List;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.FifoMessageDispatchChannel;
import org.apache.activemq.MessageDispatchChannel;
import org.apache.activemq.SimplePriorityMessageDispatchChannel;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.util.JMSExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQSessionExecutor
implements Task {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQSessionExecutor.class);
    private final ActiveMQSession session;
    private final MessageDispatchChannel messageQueue;
    private boolean dispatchedBySessionPool;
    private volatile TaskRunner taskRunner;
    private boolean startedOrWarnedThatNotStarted;

    ActiveMQSessionExecutor(ActiveMQSession session) {
        this.session = session;
        this.messageQueue = this.session.connection != null && this.session.connection.isMessagePrioritySupported() ? new SimplePriorityMessageDispatchChannel() : new FifoMessageDispatchChannel();
    }

    void setDispatchedBySessionPool(boolean value) {
        this.dispatchedBySessionPool = value;
        this.wakeup();
    }

    void execute(MessageDispatch message) throws InterruptedException {
        if (!this.startedOrWarnedThatNotStarted) {
            ActiveMQConnection connection = this.session.connection;
            long aboutUnstartedConnectionTimeout = connection.getWarnAboutUnstartedConnectionTimeout();
            if (connection.isStarted() || aboutUnstartedConnectionTimeout < 0L) {
                this.startedOrWarnedThatNotStarted = true;
            } else {
                long elapsedTime = System.currentTimeMillis() - connection.getTimeCreated();
                if (elapsedTime > aboutUnstartedConnectionTimeout) {
                    LOG.warn("Received a message on a connection which is not yet started. Have you forgotten to call Connection.start()? Connection: " + connection + " Received: " + message);
                    this.startedOrWarnedThatNotStarted = true;
                }
            }
        }
        if (!this.session.isSessionAsyncDispatch() && !this.dispatchedBySessionPool) {
            this.dispatch(message);
        } else {
            this.messageQueue.enqueue(message);
            this.wakeup();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void wakeup() {
        if (!this.dispatchedBySessionPool) {
            if (this.session.isSessionAsyncDispatch()) {
                try {
                    TaskRunner taskRunner = this.taskRunner;
                    if (taskRunner == null) {
                        ActiveMQSessionExecutor activeMQSessionExecutor = this;
                        synchronized (activeMQSessionExecutor) {
                            if (this.taskRunner == null) {
                                if (!this.isRunning()) {
                                    return;
                                }
                                this.taskRunner = this.session.connection.getSessionTaskRunner().createTaskRunner(this, "ActiveMQ Session: " + this.session.getSessionId());
                            }
                            taskRunner = this.taskRunner;
                        }
                    }
                    taskRunner.wakeup();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                while (this.iterate()) {
                }
            }
        }
    }

    void executeFirst(MessageDispatch message) {
        this.messageQueue.enqueueFirst(message);
        this.wakeup();
    }

    public boolean hasUncomsumedMessages() {
        return !this.messageQueue.isClosed() && this.messageQueue.isRunning() && !this.messageQueue.isEmpty();
    }

    void dispatch(MessageDispatch message) {
        for (ActiveMQMessageConsumer consumer : this.session.consumers) {
            ConsumerId consumerId = message.getConsumerId();
            if (!consumerId.equals(consumer.getConsumerId())) continue;
            consumer.dispatch(message);
            break;
        }
    }

    synchronized void start() {
        if (!this.messageQueue.isRunning()) {
            this.messageQueue.start();
            if (this.hasUncomsumedMessages()) {
                this.wakeup();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void stop() throws JMSException {
        block6: {
            try {
                if (!this.messageQueue.isRunning()) break block6;
                ActiveMQSessionExecutor activeMQSessionExecutor = this;
                synchronized (activeMQSessionExecutor) {
                    this.messageQueue.stop();
                    if (this.taskRunner != null) {
                        this.taskRunner.shutdown();
                        this.taskRunner = null;
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw JMSExceptionSupport.create(e);
            }
        }
    }

    boolean isRunning() {
        return this.messageQueue.isRunning();
    }

    void close() {
        this.messageQueue.close();
    }

    void clear() {
        this.messageQueue.clear();
    }

    MessageDispatch dequeueNoWait() {
        return this.messageQueue.dequeueNoWait();
    }

    protected void clearMessagesInProgress() {
        this.messageQueue.clear();
    }

    public boolean isEmpty() {
        return this.messageQueue.isEmpty();
    }

    @Override
    public boolean iterate() {
        for (ActiveMQMessageConsumer consumer : this.session.consumers) {
            if (!consumer.iterate()) continue;
            return true;
        }
        MessageDispatch message = this.messageQueue.dequeueNoWait();
        if (message == null) {
            return false;
        }
        this.dispatch(message);
        return !this.messageQueue.isEmpty();
    }

    List<MessageDispatch> getUnconsumedMessages() {
        return this.messageQueue.removeAll();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void waitForQueueRestart() throws InterruptedException {
        Object object = this.messageQueue.getMutex();
        synchronized (object) {
            while (!this.messageQueue.isRunning() && !this.messageQueue.isClosed()) {
                this.messageQueue.getMutex().wait();
            }
        }
    }
}

