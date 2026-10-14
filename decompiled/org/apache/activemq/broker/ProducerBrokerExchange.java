/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.state.ProducerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerBrokerExchange {
    private static final Logger LOG = LoggerFactory.getLogger(ProducerBrokerExchange.class);
    private ConnectionContext connectionContext;
    private Destination regionDestination;
    private Region region;
    private ProducerState producerState;
    private boolean mutable = true;
    private AtomicLong lastSendSequenceNumber = new AtomicLong(-1L);
    private boolean auditProducerSequenceIds;
    private boolean isNetworkProducer;
    private BrokerService brokerService;
    private FlowControlInfo flowControlInfo = new FlowControlInfo();

    public ProducerBrokerExchange copy() {
        ProducerBrokerExchange rc = new ProducerBrokerExchange();
        rc.connectionContext = this.connectionContext.copy();
        rc.regionDestination = this.regionDestination;
        rc.region = this.region;
        rc.producerState = this.producerState;
        rc.mutable = this.mutable;
        rc.flowControlInfo = this.flowControlInfo;
        return rc;
    }

    public ConnectionContext getConnectionContext() {
        return this.connectionContext;
    }

    public void setConnectionContext(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    public boolean isMutable() {
        return this.mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public Destination getRegionDestination() {
        return this.regionDestination;
    }

    public void setRegionDestination(Destination regionDestination) {
        this.regionDestination = regionDestination;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ProducerState getProducerState() {
        return this.producerState;
    }

    public void setProducerState(ProducerState producerState) {
        this.producerState = producerState;
    }

    public boolean canDispatch(Message messageSend) {
        boolean canDispatch = true;
        if (this.auditProducerSequenceIds && messageSend.isPersistent()) {
            long producerSequenceId = messageSend.getMessageId().getProducerSequenceId();
            if (this.isNetworkProducer) {
                long lastStoredForMessageProducer = this.getStoredSequenceIdForMessage(messageSend.getMessageId());
                if (producerSequenceId <= lastStoredForMessageProducer) {
                    canDispatch = false;
                    LOG.warn("suppressing duplicate message send [{}] from network producer with producerSequence [{}] less than last stored: {}", LOG.isTraceEnabled() ? messageSend : messageSend.getMessageId(), producerSequenceId, lastStoredForMessageProducer);
                }
            } else if (producerSequenceId <= this.lastSendSequenceNumber.get()) {
                canDispatch = false;
                if (messageSend.isInTransaction()) {
                    LOG.warn("suppressing duplicated message send [{}] with producerSequenceId [{}] <= last stored: {}", LOG.isTraceEnabled() ? messageSend : messageSend.getMessageId(), producerSequenceId, this.lastSendSequenceNumber);
                } else {
                    LOG.debug("suppressing duplicated message send [{}] with producerSequenceId [{}] <= last stored: {}", LOG.isTraceEnabled() ? messageSend : messageSend.getMessageId(), producerSequenceId, this.lastSendSequenceNumber);
                }
            } else {
                this.lastSendSequenceNumber.set(producerSequenceId);
            }
        }
        return canDispatch;
    }

    private long getStoredSequenceIdForMessage(MessageId messageId) {
        try {
            return this.brokerService.getPersistenceAdapter().getLastProducerSequenceId(messageId.getProducerId());
        }
        catch (IOException ignored) {
            LOG.debug("Failed to determine last producer sequence id for: {}", (Object)messageId, (Object)ignored);
            return -1L;
        }
    }

    public void setLastStoredSequenceId(long l) {
        this.auditProducerSequenceIds = true;
        if (this.connectionContext.isNetworkConnection()) {
            this.brokerService = this.connectionContext.getBroker().getBrokerService();
            this.isNetworkProducer = true;
        }
        this.lastSendSequenceNumber.set(l);
        LOG.debug("last stored sequence id set: {}", (Object)l);
    }

    public void incrementSend() {
        this.flowControlInfo.incrementSend();
    }

    public void blockingOnFlowControl(boolean blockingOnFlowControl) {
        this.flowControlInfo.setBlockingOnFlowControl(blockingOnFlowControl);
    }

    public void incrementTimeBlocked(Destination destination, long timeBlocked) {
        this.flowControlInfo.incrementTimeBlocked(timeBlocked);
    }

    public boolean isBlockedForFlowControl() {
        return this.flowControlInfo.isBlockingOnFlowControl();
    }

    public void resetFlowControl() {
        this.flowControlInfo.reset();
    }

    public long getTotalTimeBlocked() {
        return this.flowControlInfo.getTotalTimeBlocked();
    }

    public int getPercentageBlocked() {
        double value = this.flowControlInfo.getTotalSends() == 0L ? 0.0 : (double)(this.flowControlInfo.getSendsBlocked() / this.flowControlInfo.getTotalSends());
        return (int)value * 100;
    }

    public static class FlowControlInfo {
        private AtomicBoolean blockingOnFlowControl = new AtomicBoolean();
        private AtomicLong totalSends = new AtomicLong();
        private AtomicLong sendsBlocked = new AtomicLong();
        private AtomicLong totalTimeBlocked = new AtomicLong();

        public boolean isBlockingOnFlowControl() {
            return this.blockingOnFlowControl.get();
        }

        public void setBlockingOnFlowControl(boolean blockingOnFlowControl) {
            this.blockingOnFlowControl.set(blockingOnFlowControl);
            if (blockingOnFlowControl) {
                this.incrementSendBlocked();
            }
        }

        public long getTotalSends() {
            return this.totalSends.get();
        }

        public void incrementSend() {
            this.totalSends.incrementAndGet();
        }

        public long getSendsBlocked() {
            return this.sendsBlocked.get();
        }

        public void incrementSendBlocked() {
            this.sendsBlocked.incrementAndGet();
        }

        public long getTotalTimeBlocked() {
            return this.totalTimeBlocked.get();
        }

        public void incrementTimeBlocked(long time) {
            this.totalTimeBlocked.addAndGet(time);
        }

        public void reset() {
            this.blockingOnFlowControl.set(false);
            this.totalSends.set(0L);
            this.sendsBlocked.set(0L);
            this.totalTimeBlocked.set(0L);
        }
    }
}

