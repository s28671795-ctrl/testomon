/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.Properties;
import net.sf.ehcache.distribution.RMIAsynchronousCacheReplicator;
import net.sf.ehcache.distribution.RMISynchronousCacheReplicator;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;
import net.sf.ehcache.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMICacheReplicatorFactory
extends CacheEventListenerFactory {
    protected static final int DEFAULT_ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS = 1000;
    protected static final int DEFAULT_ASYNCHRONOUS_REPLICATION_MAXIMUM_BATCH_SIZE = 1000;
    private static final Logger LOG = LoggerFactory.getLogger(RMICacheReplicatorFactory.class.getName());
    private static final String REPLICATE_PUTS = "replicatePuts";
    private static final String REPLICATE_PUTS_VIA_COPY = "replicatePutsViaCopy";
    private static final String REPLICATE_UPDATES = "replicateUpdates";
    private static final String REPLICATE_UPDATES_VIA_COPY = "replicateUpdatesViaCopy";
    private static final String REPLICATE_REMOVALS = "replicateRemovals";
    private static final String REPLICATE_ASYNCHRONOUSLY = "replicateAsynchronously";
    private static final String ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS = "asynchronousReplicationIntervalMillis";
    private static final String ASYNCHRONOUS_REPLICATION_MAXIMUM_BATCH_SIZE = "asynchronousReplicationMaximumBatchSize";
    private static final int MINIMUM_REASONABLE_INTERVAL = 10;

    @Override
    public final CacheEventListener createCacheEventListener(Properties properties) {
        boolean replicatePuts = this.extractReplicatePuts(properties);
        boolean replicatePutsViaCopy = this.extractReplicatePutsViaCopy(properties);
        boolean replicateUpdates = this.extractReplicateUpdates(properties);
        boolean replicateUpdatesViaCopy = this.extractReplicateUpdatesViaCopy(properties);
        boolean replicateRemovals = this.extractReplicateRemovals(properties);
        boolean replicateAsynchronously = this.extractReplicateAsynchronously(properties);
        int replicationIntervalMillis = this.extractReplicationIntervalMilis(properties);
        int maximumBatchSize = this.extractMaximumBatchSize(properties);
        if (replicateAsynchronously) {
            return new RMIAsynchronousCacheReplicator(replicatePuts, replicatePutsViaCopy, replicateUpdates, replicateUpdatesViaCopy, replicateRemovals, replicationIntervalMillis, maximumBatchSize);
        }
        return new RMISynchronousCacheReplicator(replicatePuts, replicatePutsViaCopy, replicateUpdates, replicateUpdatesViaCopy, replicateRemovals);
    }

    protected int extractReplicationIntervalMilis(Properties properties) {
        int asynchronousReplicationIntervalMillis;
        block5: {
            String asynchronousReplicationIntervalMillisString = PropertyUtil.extractAndLogProperty(ASYNCHRONOUS_REPLICATION_INTERVAL_MILLIS, properties);
            if (asynchronousReplicationIntervalMillisString != null) {
                try {
                    int asynchronousReplicationIntervalMillisCandidate = Integer.parseInt(asynchronousReplicationIntervalMillisString);
                    if (asynchronousReplicationIntervalMillisCandidate < 10) {
                        LOG.debug("Trying to set the asynchronousReplicationIntervalMillis to an unreasonable number. Using the default instead.");
                        asynchronousReplicationIntervalMillis = 1000;
                        break block5;
                    }
                    asynchronousReplicationIntervalMillis = asynchronousReplicationIntervalMillisCandidate;
                }
                catch (NumberFormatException e) {
                    LOG.warn("Number format exception trying to set asynchronousReplicationIntervalMillis. Using the default instead. String value was: '" + asynchronousReplicationIntervalMillisString + "'");
                    asynchronousReplicationIntervalMillis = 1000;
                }
            } else {
                asynchronousReplicationIntervalMillis = 1000;
            }
        }
        return asynchronousReplicationIntervalMillis;
    }

    protected int extractMaximumBatchSize(Properties properties) {
        String maximumBatchSizeString = PropertyUtil.extractAndLogProperty(ASYNCHRONOUS_REPLICATION_MAXIMUM_BATCH_SIZE, properties);
        if (maximumBatchSizeString == null) {
            return 1000;
        }
        try {
            return Integer.parseInt(maximumBatchSizeString);
        }
        catch (NumberFormatException e) {
            LOG.warn("Number format exception trying to set maximumBatchSize. Using the default instead. String value was: '" + maximumBatchSizeString + "'");
            return 1000;
        }
    }

    protected boolean extractReplicateAsynchronously(Properties properties) {
        String replicateAsynchronouslyString = PropertyUtil.extractAndLogProperty(REPLICATE_ASYNCHRONOUSLY, properties);
        boolean replicateAsynchronously = replicateAsynchronouslyString != null ? PropertyUtil.parseBoolean(replicateAsynchronouslyString) : true;
        return replicateAsynchronously;
    }

    protected boolean extractReplicateRemovals(Properties properties) {
        String replicateRemovalsString = PropertyUtil.extractAndLogProperty(REPLICATE_REMOVALS, properties);
        boolean replicateRemovals = replicateRemovalsString != null ? PropertyUtil.parseBoolean(replicateRemovalsString) : true;
        return replicateRemovals;
    }

    protected boolean extractReplicateUpdatesViaCopy(Properties properties) {
        String replicateUpdatesViaCopyString = PropertyUtil.extractAndLogProperty(REPLICATE_UPDATES_VIA_COPY, properties);
        boolean replicateUpdatesViaCopy = replicateUpdatesViaCopyString != null ? PropertyUtil.parseBoolean(replicateUpdatesViaCopyString) : true;
        return replicateUpdatesViaCopy;
    }

    protected boolean extractReplicatePutsViaCopy(Properties properties) {
        String replicatePutsViaCopyString = PropertyUtil.extractAndLogProperty(REPLICATE_PUTS_VIA_COPY, properties);
        boolean replicatePutsViaCopy = replicatePutsViaCopyString != null ? PropertyUtil.parseBoolean(replicatePutsViaCopyString) : true;
        return replicatePutsViaCopy;
    }

    protected boolean extractReplicateUpdates(Properties properties) {
        String replicateUpdatesString = PropertyUtil.extractAndLogProperty(REPLICATE_UPDATES, properties);
        boolean replicateUpdates = replicateUpdatesString != null ? PropertyUtil.parseBoolean(replicateUpdatesString) : true;
        return replicateUpdates;
    }

    protected boolean extractReplicatePuts(Properties properties) {
        String replicatePutsString = PropertyUtil.extractAndLogProperty(REPLICATE_PUTS, properties);
        boolean replicatePuts = replicatePutsString != null ? PropertyUtil.parseBoolean(replicatePutsString) : true;
        return replicatePuts;
    }
}

