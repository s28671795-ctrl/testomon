/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerThread
extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(ProducerThread.class);
    int messageCount = 1000;
    boolean runIndefinitely = false;
    Destination destination;
    protected Session session;
    int sleep = 0;
    boolean persistent = true;
    int messageSize = 0;
    int textMessageSize;
    long msgTTL = 0L;
    String msgGroupID = null;
    int transactionBatchSize;
    int transactions = 0;
    AtomicInteger sentCount = new AtomicInteger(0);
    String message;
    String messageText = null;
    String payloadUrl = null;
    byte[] payload = null;
    boolean running = false;
    CountDownLatch finished;
    CountDownLatch paused = new CountDownLatch(0);

    public ProducerThread(Session session, Destination destination) {
        this.destination = destination;
        this.session = session;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        MessageProducer producer = null;
        String threadName = Thread.currentThread().getName();
        try {
            ProducerThread producerThread;
            producer = this.session.createProducer(this.destination);
            producer.setDeliveryMode(this.persistent ? 2 : 1);
            producer.setTimeToLive(this.msgTTL);
            this.initPayLoad();
            this.running = true;
            LOG.info(threadName + " Started to calculate elapsed time ...\n");
            long tStart = System.currentTimeMillis();
            if (this.runIndefinitely) {
                while (this.running) {
                    producerThread = this;
                    synchronized (producerThread) {
                        this.paused.await();
                    }
                    this.sendMessage(producer, threadName);
                    this.sentCount.incrementAndGet();
                }
            } else {
                this.sentCount.set(0);
                while (this.sentCount.get() < this.messageCount && this.running) {
                    producerThread = this;
                    synchronized (producerThread) {
                        this.paused.await();
                    }
                    this.sendMessage(producer, threadName);
                    this.sentCount.incrementAndGet();
                }
            }
            LOG.info(threadName + " Produced: " + this.getSentCount() + " messages");
            long tEnd = System.currentTimeMillis();
            long elapsed = (tEnd - tStart) / 1000L;
            LOG.info(threadName + " Elapsed time in second : " + elapsed + " s");
            LOG.info(threadName + " Elapsed time in milli second : " + (tEnd - tStart) + " milli seconds");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (this.finished != null) {
                this.finished.countDown();
            }
            if (producer != null) {
                try {
                    producer.close();
                }
                catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(MessageProducer producer, String threadName) throws Exception {
        Message message = this.createMessage(this.sentCount.get());
        producer.send(message);
        if (LOG.isDebugEnabled()) {
            LOG.debug(threadName + " Sent: " + (message instanceof TextMessage ? ((TextMessage)message).getText() : message.getJMSMessageID()));
        }
        if (this.transactionBatchSize > 0 && this.sentCount.get() > 0 && this.sentCount.get() % this.transactionBatchSize == 0) {
            LOG.info(threadName + " Committing transaction: " + this.transactions++);
            this.session.commit();
        }
        if (this.sleep > 0) {
            Thread.sleep(this.sleep);
        }
    }

    private void initPayLoad() {
        if (this.messageSize > 0) {
            this.payload = new byte[this.messageSize];
            for (int i = 0; i < this.payload.length; ++i) {
                this.payload[i] = 46;
            }
        }
    }

    protected Message createMessage(int i) throws Exception {
        Message answer;
        if (this.payload != null) {
            answer = this.session.createBytesMessage();
            answer.writeBytes(this.payload);
        } else {
            if (this.textMessageSize > 0) {
                if (this.messageText == null) {
                    this.messageText = this.readInputStream(this.getClass().getResourceAsStream("demo.txt"), this.textMessageSize, i);
                }
            } else {
                this.messageText = this.payloadUrl != null ? this.readInputStream(new URL(this.payloadUrl).openStream(), -1, i) : (this.message != null ? this.message : this.createDefaultMessage(i));
            }
            answer = this.session.createTextMessage(this.messageText);
        }
        if (this.msgGroupID != null && !this.msgGroupID.isEmpty()) {
            answer.setStringProperty("JMSXGroupID", this.msgGroupID);
        }
        return answer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String readInputStream(InputStream is, int size, int messageNumber) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(is);){
            int count;
            char[] buffer = size > 0 ? new char[size] : new char[1024];
            StringBuilder builder = new StringBuilder();
            while ((count = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, count);
                if (size <= 0) continue;
            }
            String string = builder.toString();
            return string;
        }
    }

    private String createDefaultMessage(int messageNumber) {
        return "test message: " + messageNumber;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getSleep() {
        return this.sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public int getSentCount() {
        return this.sentCount.get();
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getMsgTTL() {
        return this.msgTTL;
    }

    public void setMsgTTL(long msgTTL) {
        this.msgTTL = msgTTL;
    }

    public int getTransactionBatchSize() {
        return this.transactionBatchSize;
    }

    public void setTransactionBatchSize(int transactionBatchSize) {
        this.transactionBatchSize = transactionBatchSize;
    }

    public String getMsgGroupID() {
        return this.msgGroupID;
    }

    public void setMsgGroupID(String msgGroupID) {
        this.msgGroupID = msgGroupID;
    }

    public int getTextMessageSize() {
        return this.textMessageSize;
    }

    public void setTextMessageSize(int textMessageSize) {
        this.textMessageSize = textMessageSize;
    }

    public int getMessageSize() {
        return this.messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public CountDownLatch getFinished() {
        return this.finished;
    }

    public void setFinished(CountDownLatch finished) {
        this.finished = finished;
    }

    public String getPayloadUrl() {
        return this.payloadUrl;
    }

    public void setPayloadUrl(String payloadUrl) {
        this.payloadUrl = payloadUrl;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRunIndefinitely() {
        return this.runIndefinitely;
    }

    public void setRunIndefinitely(boolean runIndefinitely) {
        this.runIndefinitely = runIndefinitely;
    }

    public synchronized void pauseProducer() {
        this.paused = new CountDownLatch(1);
    }

    public synchronized void resumeProducer() {
        this.paused.countDown();
    }

    public void resetCounters() {
        this.sentCount.set(0);
    }
}

