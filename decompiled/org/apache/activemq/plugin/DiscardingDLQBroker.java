/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import java.util.regex.Pattern;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardingDLQBroker
extends BrokerFilter {
    public static Logger log = LoggerFactory.getLogger(DiscardingDLQBroker.class);
    private boolean dropTemporaryTopics = true;
    private boolean dropTemporaryQueues = true;
    private boolean dropAll = true;
    private Pattern[] destFilter;
    private int reportInterval = 1000;
    private long dropCount = 0L;

    public DiscardingDLQBroker(Broker next) {
        super(next);
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext ctx, MessageReference msgRef, Subscription subscription, Throwable poisonCause) {
        log.trace("Discarding DLQ BrokerFilter[pass through] - skipping message: {}", (Object)(msgRef != null ? msgRef.getMessage() : null));
        boolean dropped = true;
        Message msg = null;
        ActiveMQDestination dest = null;
        String destName = null;
        msg = msgRef.getMessage();
        dest = msg.getDestination();
        destName = dest.getPhysicalName();
        if (dest == null || destName == null) {
            this.skipMessage("NULL DESTINATION", msgRef);
        } else if (this.dropAll) {
            this.skipMessage("dropAll", msgRef);
        } else if (this.dropTemporaryTopics && dest.isTemporary() && dest.isTopic()) {
            this.skipMessage("dropTemporaryTopics", msgRef);
        } else if (this.dropTemporaryQueues && dest.isTemporary() && dest.isQueue()) {
            this.skipMessage("dropTemporaryQueues", msgRef);
        } else if (this.destFilter != null && this.matches(destName)) {
            this.skipMessage("dropOnly", msgRef);
        } else {
            dropped = false;
            return this.next.sendToDeadLetterQueue(ctx, msgRef, subscription, poisonCause);
        }
        if (dropped && this.getReportInterval() > 0 && ++this.dropCount % (long)this.getReportInterval() == 0L) {
            log.info("Total of {} messages were discarded, since their destination was the dead letter queue", (Object)this.dropCount);
        }
        return false;
    }

    public boolean matches(String destName) {
        for (int i = 0; this.destFilter != null && i < this.destFilter.length; ++i) {
            if (this.destFilter[i] == null || !this.destFilter[i].matcher(destName).matches()) continue;
            return true;
        }
        return false;
    }

    private void skipMessage(String prefix, MessageReference msgRef) {
        log.debug("Discarding DLQ BrokerFilter[{}] - skipping message: {}", (Object)prefix, (Object)(msgRef != null ? msgRef.getMessage() : null));
    }

    public void setDropTemporaryTopics(boolean dropTemporaryTopics) {
        this.dropTemporaryTopics = dropTemporaryTopics;
    }

    public void setDropTemporaryQueues(boolean dropTemporaryQueues) {
        this.dropTemporaryQueues = dropTemporaryQueues;
    }

    public void setDropAll(boolean dropAll) {
        this.dropAll = dropAll;
    }

    public void setDestFilter(Pattern[] destFilter) {
        this.destFilter = destFilter;
    }

    public void setReportInterval(int reportInterval) {
        this.reportInterval = reportInterval;
    }

    public boolean isDropTemporaryTopics() {
        return this.dropTemporaryTopics;
    }

    public boolean isDropTemporaryQueues() {
        return this.dropTemporaryQueues;
    }

    public boolean isDropAll() {
        return this.dropAll;
    }

    public Pattern[] getDestFilter() {
        return this.destFilter;
    }

    public int getReportInterval() {
        return this.reportInterval;
    }
}

