/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.selector.SelectorParser;

public class ActiveMQQueueBrowser
implements QueueBrowser,
Enumeration {
    private final ActiveMQSession session;
    private final ActiveMQDestination destination;
    private final String selector;
    private ActiveMQMessageConsumer consumer;
    private boolean closed;
    private final ConsumerId consumerId;
    private final AtomicBoolean browseDone = new AtomicBoolean(true);
    private final boolean dispatchAsync;
    private Object semaphore = new Object();

    protected ActiveMQQueueBrowser(ActiveMQSession session, ConsumerId consumerId, ActiveMQDestination destination, String selector, boolean dispatchAsync) throws JMSException {
        if (destination == null) {
            throw new InvalidDestinationException("Don't understand null destinations");
        }
        if (destination.getPhysicalName() == null) {
            throw new InvalidDestinationException("The destination object was not given a physical name.");
        }
        if (selector != null && selector.trim().length() != 0) {
            SelectorParser.parse(selector);
        }
        this.session = session;
        this.consumerId = consumerId;
        this.destination = destination;
        this.selector = selector;
        this.dispatchAsync = dispatchAsync;
    }

    private ActiveMQMessageConsumer createConsumer() throws JMSException {
        this.browseDone.set(false);
        ActiveMQPrefetchPolicy prefetchPolicy = this.session.connection.getPrefetchPolicy();
        return new ActiveMQMessageConsumer(this.session, this.consumerId, this.destination, null, this.selector, prefetchPolicy.getQueueBrowserPrefetch(), prefetchPolicy.getMaximumPendingMessageLimit(), false, true, this.dispatchAsync, null){

            @Override
            public void dispatch(MessageDispatch md) {
                if (md.getMessage() == null) {
                    ActiveMQQueueBrowser.this.browseDone.set(true);
                } else {
                    super.dispatch(md);
                }
                ActiveMQQueueBrowser.this.notifyMessageAvailable();
            }
        };
    }

    private void destroyConsumer() {
        if (this.consumer == null) {
            return;
        }
        try {
            if (this.session.getTransacted() && this.session.getTransactionContext().isInLocalTransaction()) {
                this.session.commit();
            }
            this.consumer.close();
            this.consumer = null;
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Enumeration getEnumeration() throws JMSException {
        this.checkClosed();
        if (this.consumer == null) {
            this.consumer = this.createConsumer();
        }
        return this;
    }

    private void checkClosed() throws IllegalStateException {
        if (this.closed) {
            throw new IllegalStateException("The Consumer is closed");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasMoreElements() {
        while (true) {
            ActiveMQQueueBrowser activeMQQueueBrowser = this;
            synchronized (activeMQQueueBrowser) {
                if (this.consumer == null) {
                    return false;
                }
            }
            if (this.consumer.getMessageSize() > 0) {
                return true;
            }
            if (this.browseDone.get() || !this.session.isRunning()) {
                this.destroyConsumer();
                return false;
            }
            this.waitForMessage();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object nextElement() {
        while (true) {
            ActiveMQQueueBrowser activeMQQueueBrowser = this;
            synchronized (activeMQQueueBrowser) {
                if (this.consumer == null) {
                    return null;
                }
            }
            try {
                Message answer = this.consumer.receiveNoWait();
                if (answer != null) {
                    return answer;
                }
            }
            catch (JMSException e) {
                this.session.connection.onClientInternalException(e);
                return null;
            }
            if (this.browseDone.get() || !this.session.isRunning()) {
                this.destroyConsumer();
                return null;
            }
            this.waitForMessage();
        }
    }

    @Override
    public synchronized void close() throws JMSException {
        this.browseDone.set(true);
        this.destroyConsumer();
        this.closed = true;
    }

    @Override
    public Queue getQueue() throws JMSException {
        return (Queue)((Object)this.destination);
    }

    @Override
    public String getMessageSelector() throws JMSException {
        return this.selector;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void waitForMessage() {
        try {
            this.consumer.sendPullCommand(-1L);
            Object object = this.semaphore;
            synchronized (object) {
                this.semaphore.wait(2000L);
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (JMSException jMSException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void notifyMessageAvailable() {
        Object object = this.semaphore;
        synchronized (object) {
            this.semaphore.notifyAll();
        }
    }

    public String toString() {
        return "ActiveMQQueueBrowser { value=" + this.consumerId + " }";
    }
}

