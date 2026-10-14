/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractRegion;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.TempQueueRegion;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTempRegion
extends AbstractRegion {
    private static final Logger LOG = LoggerFactory.getLogger(TempQueueRegion.class);
    private Map<CachedDestination, Destination> cachedDestinations = new HashMap<CachedDestination, Destination>();
    private final boolean doCacheTempDestinations;
    private final int purgeTime;
    private Timer purgeTimer;
    private TimerTask purgeTask;

    public AbstractTempRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
        this.doCacheTempDestinations = broker.getBrokerService().isCacheTempDestinations();
        this.purgeTime = broker.getBrokerService().getTimeBeforePurgeTempDestinations();
        if (this.doCacheTempDestinations) {
            this.purgeTimer = new Timer("ActiveMQ Temp destination purge timer", true);
            this.purgeTask = new TimerTask(){

                @Override
                public void run() {
                    AbstractTempRegion.this.doPurge();
                }
            };
            this.purgeTimer.schedule(this.purgeTask, this.purgeTime, (long)this.purgeTime);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (this.purgeTimer != null) {
            this.purgeTimer.cancel();
        }
    }

    @Override
    protected synchronized Destination createDestination(ConnectionContext context, ActiveMQDestination destination) throws Exception {
        Destination result = this.cachedDestinations.remove(new CachedDestination(destination));
        if (result == null) {
            result = this.destinationFactory.createDestination(context, destination, this.destinationStatistics);
        }
        return result;
    }

    @Override
    protected final synchronized void dispose(ConnectionContext context, Destination dest) throws Exception {
        if (this.doCacheTempDestinations) {
            this.cachedDestinations.put(new CachedDestination(dest.getActiveMQDestination()), dest);
        } else {
            try {
                dest.dispose(context);
                dest.stop();
            }
            catch (Exception e) {
                LOG.warn("Failed to dispose of {}", (Object)dest, (Object)e);
            }
        }
    }

    private void doDispose(Destination dest) {
        ConnectionContext context = new ConnectionContext();
        try {
            dest.dispose(context);
            dest.stop();
        }
        catch (Exception e) {
            LOG.warn("Failed to dispose of {}", (Object)dest, (Object)e);
        }
    }

    private synchronized void doPurge() {
        long currentTime = System.currentTimeMillis();
        if (this.cachedDestinations.size() > 0) {
            HashSet<CachedDestination> tmp = new HashSet<CachedDestination>(this.cachedDestinations.keySet());
            for (CachedDestination key : tmp) {
                Destination dest;
                if (key.timeStamp + (long)this.purgeTime >= currentTime || (dest = this.cachedDestinations.remove(key)) == null) continue;
                this.doDispose(dest);
            }
        }
    }

    static class CachedDestination {
        long timeStamp;
        ActiveMQDestination destination;

        CachedDestination(ActiveMQDestination destination) {
            this.destination = destination;
            this.timeStamp = System.currentTimeMillis();
        }

        public int hashCode() {
            return this.destination.hashCode();
        }

        public boolean equals(Object o) {
            if (o instanceof CachedDestination) {
                CachedDestination other = (CachedDestination)o;
                return other.destination.equals(this.destination);
            }
            return false;
        }
    }
}

