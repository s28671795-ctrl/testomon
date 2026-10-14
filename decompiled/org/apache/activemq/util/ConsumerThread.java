/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.concurrent.CountDownLatch;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerThread
extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerThread.class);
    int messageCount = 1000;
    int receiveTimeOut = 3000;
    Destination destination;
    Session session;
    boolean durable;
    boolean breakOnNull = true;
    int sleep;
    int batchSize;
    int received = 0;
    int transactions = 0;
    boolean running = false;
    CountDownLatch finished;
    boolean bytesAsText;

    public ConsumerThread(Session session, Destination destination) {
        this.destination = destination;
        this.session = session;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        this.running = true;
        MessageConsumer consumer = null;
        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + " wait until " + this.messageCount + " messages are consumed");
        try {
            consumer = this.durable && this.destination instanceof Topic ? this.session.createDurableSubscriber((Topic)this.destination, this.getName()) : this.session.createConsumer(this.destination);
            while (this.running && this.received < this.messageCount) {
                Message msg = consumer.receive(this.receiveTimeOut);
                if (msg != null) {
                    LOG.info(threadName + " Received " + (msg instanceof TextMessage ? ((TextMessage)msg).getText() : msg.getJMSMessageID()));
                    if (this.bytesAsText && msg instanceof BytesMessage) {
                        long length = ((BytesMessage)msg).getBodyLength();
                        byte[] bytes = new byte[(int)length];
                        ((BytesMessage)msg).readBytes(bytes);
                        LOG.info("BytesMessage as text string: " + new String(bytes));
                    }
                    ++this.received;
                } else if (this.breakOnNull) {
                    break;
                }
                if (this.session.getTransacted()) {
                    if (this.batchSize > 0 && this.received > 0 && this.received % this.batchSize == 0) {
                        LOG.info(threadName + " Committing transaction: " + this.transactions++);
                        this.session.commit();
                    }
                } else if (this.session.getAcknowledgeMode() == 2 && this.batchSize > 0 && this.received > 0 && this.received % this.batchSize == 0) {
                    LOG.info("Acknowledging last " + this.batchSize + " messages; messages so far = " + this.received);
                    msg.acknowledge();
                }
                if (this.sleep <= 0) continue;
                Thread.sleep(this.sleep);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (this.finished != null) {
                this.finished.countDown();
            }
            if (consumer != null) {
                LOG.info(threadName + " Consumed: " + this.getReceived() + " messages");
                try {
                    consumer.close();
                }
                catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        LOG.info(threadName + " Consumer thread finished");
    }

    public int getReceived() {
        return this.received;
    }

    public boolean isDurable() {
        return this.durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setBreakOnNull(boolean breakOnNull) {
        this.breakOnNull = breakOnNull;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public boolean isBreakOnNull() {
        return this.breakOnNull;
    }

    public int getReceiveTimeOut() {
        return this.receiveTimeOut;
    }

    public void setReceiveTimeOut(int receiveTimeOut) {
        this.receiveTimeOut = receiveTimeOut;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getSleep() {
        return this.sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public CountDownLatch getFinished() {
        return this.finished;
    }

    public void setFinished(CountDownLatch finished) {
        this.finished = finished;
    }

    public boolean isBytesAsText() {
        return this.bytesAsText;
    }

    public void setBytesAsText(boolean bytesAsText) {
        this.bytesAsText = bytesAsText;
    }
}

