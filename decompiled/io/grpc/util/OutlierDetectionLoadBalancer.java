/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.ServiceConfigUtil;
import io.grpc.internal.TimeProvider;
import io.grpc.util.ForwardingClientStreamTracer;
import io.grpc.util.ForwardingLoadBalancerHelper;
import io.grpc.util.ForwardingSubchannel;
import io.grpc.util.GracefulSwitchLoadBalancer;
import io.grpc.util.HealthProducerHelper;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

@Internal
public final class OutlierDetectionLoadBalancer
extends LoadBalancer {
    @VisibleForTesting
    final EndpointTrackerMap endpointTrackerMap;
    @VisibleForTesting
    final Map<SocketAddress, EndpointTracker> addressMap = new HashMap<SocketAddress, EndpointTracker>();
    private final SynchronizationContext syncContext;
    private final LoadBalancer.Helper childHelper;
    private final GracefulSwitchLoadBalancer switchLb;
    private TimeProvider timeProvider;
    private final ScheduledExecutorService timeService;
    private SynchronizationContext.ScheduledHandle detectionTimerHandle;
    private Long detectionTimerStartNanos;
    private final ChannelLogger logger;
    private static final Attributes.Key<EndpointTracker> ENDPOINT_TRACKER_KEY = Attributes.Key.create("endpointTrackerKey");

    public OutlierDetectionLoadBalancer(LoadBalancer.Helper helper, TimeProvider timeProvider) {
        this.logger = helper.getChannelLogger();
        this.childHelper = new ChildHelper(Preconditions.checkNotNull(helper, "helper"));
        this.switchLb = new GracefulSwitchLoadBalancer(this.childHelper);
        this.endpointTrackerMap = new EndpointTrackerMap();
        this.syncContext = Preconditions.checkNotNull(helper.getSynchronizationContext(), "syncContext");
        this.timeService = Preconditions.checkNotNull(helper.getScheduledExecutorService(), "timeService");
        this.timeProvider = timeProvider;
        this.logger.log(ChannelLogger.ChannelLogLevel.DEBUG, "OutlierDetection lb created.");
    }

    @Override
    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        this.logger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Received resolution result: {0}", resolvedAddresses);
        OutlierDetectionLoadBalancerConfig config = (OutlierDetectionLoadBalancerConfig)resolvedAddresses.getLoadBalancingPolicyConfig();
        HashSet<Set<SocketAddress>> endpoints = new HashSet<Set<SocketAddress>>();
        HashMap<SocketAddress, ImmutableSet<SocketAddress>> addressEndpointMap = new HashMap<SocketAddress, ImmutableSet<SocketAddress>>();
        for (EquivalentAddressGroup equivalentAddressGroup : resolvedAddresses.getAddresses()) {
            ImmutableSet<SocketAddress> endpoint = ImmutableSet.copyOf(equivalentAddressGroup.getAddresses());
            endpoints.add(endpoint);
            for (SocketAddress address : equivalentAddressGroup.getAddresses()) {
                if (addressEndpointMap.containsKey(address)) {
                    this.logger.log(ChannelLogger.ChannelLogLevel.WARNING, "Unexpected duplicated address {0} belongs to multiple endpoints", address);
                }
                addressEndpointMap.put(address, endpoint);
            }
        }
        this.endpointTrackerMap.keySet().retainAll(endpoints);
        this.endpointTrackerMap.updateTrackerConfigs(config);
        this.endpointTrackerMap.putNewTrackers(config, endpoints);
        this.addressMap.clear();
        for (Map.Entry entry : addressEndpointMap.entrySet()) {
            this.addressMap.put((SocketAddress)entry.getKey(), (EndpointTracker)this.endpointTrackerMap.get(entry.getValue()));
        }
        this.switchLb.switchTo(config.childPolicy.getProvider());
        if (config.outlierDetectionEnabled()) {
            Long initialDelayNanos = this.detectionTimerStartNanos == null ? config.intervalNanos : Long.valueOf(Math.max(0L, config.intervalNanos - (this.timeProvider.currentTimeNanos() - this.detectionTimerStartNanos)));
            if (this.detectionTimerHandle != null) {
                this.detectionTimerHandle.cancel();
                this.endpointTrackerMap.resetCallCounters();
            }
            this.detectionTimerHandle = this.syncContext.scheduleWithFixedDelay(new DetectionTimer(config, this.logger), initialDelayNanos, config.intervalNanos, TimeUnit.NANOSECONDS, this.timeService);
        } else if (this.detectionTimerHandle != null) {
            this.detectionTimerHandle.cancel();
            this.detectionTimerStartNanos = null;
            this.endpointTrackerMap.cancelTracking();
        }
        this.switchLb.handleResolvedAddresses(resolvedAddresses.toBuilder().setLoadBalancingPolicyConfig(config.childPolicy.getConfig()).build());
        return Status.OK;
    }

    @Override
    public void handleNameResolutionError(Status error) {
        this.switchLb.handleNameResolutionError(error);
    }

    @Override
    public void shutdown() {
        this.switchLb.shutdown();
    }

    private static List<EndpointTracker> trackersWithVolume(EndpointTrackerMap trackerMap, int volume) {
        ArrayList<EndpointTracker> trackersWithVolume = new ArrayList<EndpointTracker>();
        for (EndpointTracker tracker : trackerMap.values()) {
            if (tracker.inactiveVolume() < (long)volume) continue;
            trackersWithVolume.add(tracker);
        }
        return trackersWithVolume;
    }

    private static boolean hasSingleAddress(List<EquivalentAddressGroup> addressGroups) {
        int addressCount = 0;
        for (EquivalentAddressGroup addressGroup : addressGroups) {
            if ((addressCount += addressGroup.getAddresses().size()) <= 1) continue;
            return false;
        }
        return true;
    }

    public static final class OutlierDetectionLoadBalancerConfig {
        public final Long intervalNanos;
        public final Long baseEjectionTimeNanos;
        public final Long maxEjectionTimeNanos;
        public final Integer maxEjectionPercent;
        public final SuccessRateEjection successRateEjection;
        public final FailurePercentageEjection failurePercentageEjection;
        public final ServiceConfigUtil.PolicySelection childPolicy;

        private OutlierDetectionLoadBalancerConfig(Long intervalNanos, Long baseEjectionTimeNanos, Long maxEjectionTimeNanos, Integer maxEjectionPercent, SuccessRateEjection successRateEjection, FailurePercentageEjection failurePercentageEjection, ServiceConfigUtil.PolicySelection childPolicy) {
            this.intervalNanos = intervalNanos;
            this.baseEjectionTimeNanos = baseEjectionTimeNanos;
            this.maxEjectionTimeNanos = maxEjectionTimeNanos;
            this.maxEjectionPercent = maxEjectionPercent;
            this.successRateEjection = successRateEjection;
            this.failurePercentageEjection = failurePercentageEjection;
            this.childPolicy = childPolicy;
        }

        boolean outlierDetectionEnabled() {
            return this.successRateEjection != null || this.failurePercentageEjection != null;
        }

        public static class FailurePercentageEjection {
            public final Integer threshold;
            public final Integer enforcementPercentage;
            public final Integer minimumHosts;
            public final Integer requestVolume;

            FailurePercentageEjection(Integer threshold, Integer enforcementPercentage, Integer minimumHosts, Integer requestVolume) {
                this.threshold = threshold;
                this.enforcementPercentage = enforcementPercentage;
                this.minimumHosts = minimumHosts;
                this.requestVolume = requestVolume;
            }

            public static class Builder {
                Integer threshold = 85;
                Integer enforcementPercentage = 100;
                Integer minimumHosts = 5;
                Integer requestVolume = 50;

                public Builder setThreshold(Integer threshold) {
                    Preconditions.checkArgument(threshold != null);
                    Preconditions.checkArgument(threshold >= 0 && threshold <= 100);
                    this.threshold = threshold;
                    return this;
                }

                public Builder setEnforcementPercentage(Integer enforcementPercentage) {
                    Preconditions.checkArgument(enforcementPercentage != null);
                    Preconditions.checkArgument(enforcementPercentage >= 0 && enforcementPercentage <= 100);
                    this.enforcementPercentage = enforcementPercentage;
                    return this;
                }

                public Builder setMinimumHosts(Integer minimumHosts) {
                    Preconditions.checkArgument(minimumHosts != null);
                    Preconditions.checkArgument(minimumHosts >= 0);
                    this.minimumHosts = minimumHosts;
                    return this;
                }

                public Builder setRequestVolume(Integer requestVolume) {
                    Preconditions.checkArgument(requestVolume != null);
                    Preconditions.checkArgument(requestVolume >= 0);
                    this.requestVolume = requestVolume;
                    return this;
                }

                public FailurePercentageEjection build() {
                    return new FailurePercentageEjection(this.threshold, this.enforcementPercentage, this.minimumHosts, this.requestVolume);
                }
            }
        }

        public static class SuccessRateEjection {
            public final Integer stdevFactor;
            public final Integer enforcementPercentage;
            public final Integer minimumHosts;
            public final Integer requestVolume;

            SuccessRateEjection(Integer stdevFactor, Integer enforcementPercentage, Integer minimumHosts, Integer requestVolume) {
                this.stdevFactor = stdevFactor;
                this.enforcementPercentage = enforcementPercentage;
                this.minimumHosts = minimumHosts;
                this.requestVolume = requestVolume;
            }

            public static final class Builder {
                Integer stdevFactor = 1900;
                Integer enforcementPercentage = 100;
                Integer minimumHosts = 5;
                Integer requestVolume = 100;

                public Builder setStdevFactor(Integer stdevFactor) {
                    Preconditions.checkArgument(stdevFactor != null);
                    this.stdevFactor = stdevFactor;
                    return this;
                }

                public Builder setEnforcementPercentage(Integer enforcementPercentage) {
                    Preconditions.checkArgument(enforcementPercentage != null);
                    Preconditions.checkArgument(enforcementPercentage >= 0 && enforcementPercentage <= 100);
                    this.enforcementPercentage = enforcementPercentage;
                    return this;
                }

                public Builder setMinimumHosts(Integer minimumHosts) {
                    Preconditions.checkArgument(minimumHosts != null);
                    Preconditions.checkArgument(minimumHosts >= 0);
                    this.minimumHosts = minimumHosts;
                    return this;
                }

                public Builder setRequestVolume(Integer requestVolume) {
                    Preconditions.checkArgument(requestVolume != null);
                    Preconditions.checkArgument(requestVolume >= 0);
                    this.requestVolume = requestVolume;
                    return this;
                }

                public SuccessRateEjection build() {
                    return new SuccessRateEjection(this.stdevFactor, this.enforcementPercentage, this.minimumHosts, this.requestVolume);
                }
            }
        }

        public static class Builder {
            Long intervalNanos = 10000000000L;
            Long baseEjectionTimeNanos = 30000000000L;
            Long maxEjectionTimeNanos = 300000000000L;
            Integer maxEjectionPercent = 10;
            SuccessRateEjection successRateEjection;
            FailurePercentageEjection failurePercentageEjection;
            ServiceConfigUtil.PolicySelection childPolicy;

            public Builder setIntervalNanos(Long intervalNanos) {
                Preconditions.checkArgument(intervalNanos != null);
                this.intervalNanos = intervalNanos;
                return this;
            }

            public Builder setBaseEjectionTimeNanos(Long baseEjectionTimeNanos) {
                Preconditions.checkArgument(baseEjectionTimeNanos != null);
                this.baseEjectionTimeNanos = baseEjectionTimeNanos;
                return this;
            }

            public Builder setMaxEjectionTimeNanos(Long maxEjectionTimeNanos) {
                Preconditions.checkArgument(maxEjectionTimeNanos != null);
                this.maxEjectionTimeNanos = maxEjectionTimeNanos;
                return this;
            }

            public Builder setMaxEjectionPercent(Integer maxEjectionPercent) {
                Preconditions.checkArgument(maxEjectionPercent != null);
                this.maxEjectionPercent = maxEjectionPercent;
                return this;
            }

            public Builder setSuccessRateEjection(SuccessRateEjection successRateEjection) {
                this.successRateEjection = successRateEjection;
                return this;
            }

            public Builder setFailurePercentageEjection(FailurePercentageEjection failurePercentageEjection) {
                this.failurePercentageEjection = failurePercentageEjection;
                return this;
            }

            public Builder setChildPolicy(ServiceConfigUtil.PolicySelection childPolicy) {
                Preconditions.checkState(childPolicy != null);
                this.childPolicy = childPolicy;
                return this;
            }

            public OutlierDetectionLoadBalancerConfig build() {
                Preconditions.checkState(this.childPolicy != null);
                return new OutlierDetectionLoadBalancerConfig(this.intervalNanos, this.baseEjectionTimeNanos, this.maxEjectionTimeNanos, this.maxEjectionPercent, this.successRateEjection, this.failurePercentageEjection, this.childPolicy);
            }
        }
    }

    static class FailurePercentageOutlierEjectionAlgorithm
    implements OutlierEjectionAlgorithm {
        private final OutlierDetectionLoadBalancerConfig config;
        private final ChannelLogger logger;

        FailurePercentageOutlierEjectionAlgorithm(OutlierDetectionLoadBalancerConfig config, ChannelLogger logger2) {
            this.config = config;
            this.logger = logger2;
        }

        @Override
        public void ejectOutliers(EndpointTrackerMap trackerMap, long ejectionTimeNanos) {
            List trackersWithVolume = OutlierDetectionLoadBalancer.trackersWithVolume(trackerMap, this.config.failurePercentageEjection.requestVolume);
            if (trackersWithVolume.size() < this.config.failurePercentageEjection.minimumHosts || trackersWithVolume.size() == 0) {
                return;
            }
            for (EndpointTracker tracker : trackersWithVolume) {
                if (trackerMap.ejectionPercentage() >= (double)this.config.maxEjectionPercent.intValue()) {
                    return;
                }
                if (tracker.inactiveVolume() < (long)this.config.failurePercentageEjection.requestVolume.intValue()) continue;
                double maxFailureRate = (double)this.config.failurePercentageEjection.threshold.intValue() / 100.0;
                if (!(tracker.failureRate() > maxFailureRate)) continue;
                this.logger.log(ChannelLogger.ChannelLogLevel.DEBUG, "FailurePercentage algorithm detected outlier: {0}, failureRate={1}", tracker, tracker.failureRate());
                if (new Random().nextInt(100) >= this.config.failurePercentageEjection.enforcementPercentage) continue;
                tracker.ejectSubchannels(ejectionTimeNanos);
            }
        }
    }

    static class SuccessRateOutlierEjectionAlgorithm
    implements OutlierEjectionAlgorithm {
        private final OutlierDetectionLoadBalancerConfig config;
        private final ChannelLogger logger;

        SuccessRateOutlierEjectionAlgorithm(OutlierDetectionLoadBalancerConfig config, ChannelLogger logger2) {
            Preconditions.checkArgument(config.successRateEjection != null, "success rate ejection config is null");
            this.config = config;
            this.logger = logger2;
        }

        @Override
        public void ejectOutliers(EndpointTrackerMap trackerMap, long ejectionTimeNanos) {
            List trackersWithVolume = OutlierDetectionLoadBalancer.trackersWithVolume(trackerMap, this.config.successRateEjection.requestVolume);
            if (trackersWithVolume.size() < this.config.successRateEjection.minimumHosts || trackersWithVolume.size() == 0) {
                return;
            }
            ArrayList<Double> successRates = new ArrayList<Double>();
            for (EndpointTracker tracker : trackersWithVolume) {
                successRates.add(tracker.successRate());
            }
            double mean = SuccessRateOutlierEjectionAlgorithm.mean(successRates);
            double stdev = SuccessRateOutlierEjectionAlgorithm.standardDeviation(successRates, mean);
            double requiredSuccessRate = mean - stdev * (double)((float)this.config.successRateEjection.stdevFactor.intValue() / 1000.0f);
            for (EndpointTracker tracker : trackersWithVolume) {
                if (trackerMap.ejectionPercentage() >= (double)this.config.maxEjectionPercent.intValue()) {
                    return;
                }
                if (!(tracker.successRate() < requiredSuccessRate)) continue;
                this.logger.log(ChannelLogger.ChannelLogLevel.DEBUG, "SuccessRate algorithm detected outlier: {0}. Parameters: successRate={1}, mean={2}, stdev={3}, requiredSuccessRate={4}", tracker, tracker.successRate(), mean, stdev, requiredSuccessRate);
                if (new Random().nextInt(100) >= this.config.successRateEjection.enforcementPercentage) continue;
                tracker.ejectSubchannels(ejectionTimeNanos);
            }
        }

        @VisibleForTesting
        static double mean(Collection<Double> values2) {
            double totalValue = 0.0;
            for (double value : values2) {
                totalValue += value;
            }
            return totalValue / (double)values2.size();
        }

        @VisibleForTesting
        static double standardDeviation(Collection<Double> values2, double mean) {
            double squaredDifferenceSum = 0.0;
            for (double value : values2) {
                double difference = value - mean;
                squaredDifferenceSum += difference * difference;
            }
            double variance = squaredDifferenceSum / (double)values2.size();
            return Math.sqrt(variance);
        }
    }

    static interface OutlierEjectionAlgorithm {
        public void ejectOutliers(EndpointTrackerMap var1, long var2);

        @Nullable
        public static List<OutlierEjectionAlgorithm> forConfig(OutlierDetectionLoadBalancerConfig config, ChannelLogger logger2) {
            ImmutableList.Builder algoListBuilder = ImmutableList.builder();
            if (config.successRateEjection != null) {
                algoListBuilder.add(new SuccessRateOutlierEjectionAlgorithm(config, logger2));
            }
            if (config.failurePercentageEjection != null) {
                algoListBuilder.add(new FailurePercentageOutlierEjectionAlgorithm(config, logger2));
            }
            return algoListBuilder.build();
        }
    }

    static class EndpointTrackerMap
    extends ForwardingMap<Set<SocketAddress>, EndpointTracker> {
        private final Map<Set<SocketAddress>, EndpointTracker> trackerMap = new HashMap<Set<SocketAddress>, EndpointTracker>();

        EndpointTrackerMap() {
        }

        @Override
        protected Map<Set<SocketAddress>, EndpointTracker> delegate() {
            return this.trackerMap;
        }

        void updateTrackerConfigs(OutlierDetectionLoadBalancerConfig config) {
            for (EndpointTracker tracker : this.trackerMap.values()) {
                tracker.setConfig(config);
            }
        }

        void putNewTrackers(OutlierDetectionLoadBalancerConfig config, Set<Set<SocketAddress>> endpoints) {
            endpoints.forEach((? super T e) -> this.trackerMap.putIfAbsent((Set<SocketAddress>)e, new EndpointTracker(config)));
        }

        void resetCallCounters() {
            for (EndpointTracker tracker : this.trackerMap.values()) {
                tracker.resetCallCounters();
            }
        }

        void cancelTracking() {
            for (EndpointTracker tracker : this.trackerMap.values()) {
                if (tracker.subchannelsEjected()) {
                    tracker.unejectSubchannels();
                }
                tracker.resetEjectionTimeMultiplier();
            }
        }

        void swapCounters() {
            for (EndpointTracker tracker : this.trackerMap.values()) {
                tracker.swapCounters();
            }
        }

        void maybeUnejectOutliers(Long detectionTimerStartNanos) {
            for (EndpointTracker tracker : this.trackerMap.values()) {
                if (!tracker.subchannelsEjected()) {
                    tracker.decrementEjectionTimeMultiplier();
                }
                if (!tracker.subchannelsEjected() || !tracker.maxEjectionTimeElapsed(detectionTimerStartNanos)) continue;
                tracker.unejectSubchannels();
            }
        }

        double ejectionPercentage() {
            if (this.trackerMap.isEmpty()) {
                return 0.0;
            }
            int totalEndpoints = 0;
            int ejectedEndpoints = 0;
            for (EndpointTracker tracker : this.trackerMap.values()) {
                ++totalEndpoints;
                if (!tracker.subchannelsEjected()) continue;
                ++ejectedEndpoints;
            }
            return (double)ejectedEndpoints / (double)totalEndpoints * 100.0;
        }
    }

    static class EndpointTracker {
        private OutlierDetectionLoadBalancerConfig config;
        private volatile CallCounter activeCallCounter = new CallCounter();
        private CallCounter inactiveCallCounter = new CallCounter();
        private Long ejectionTimeNanos;
        private int ejectionTimeMultiplier;
        private final Set<OutlierDetectionSubchannel> subchannels = new HashSet<OutlierDetectionSubchannel>();

        EndpointTracker(OutlierDetectionLoadBalancerConfig config) {
            this.config = config;
        }

        void setConfig(OutlierDetectionLoadBalancerConfig config) {
            this.config = config;
        }

        boolean addSubchannel(OutlierDetectionSubchannel subchannel) {
            if (this.subchannelsEjected() && !subchannel.isEjected()) {
                subchannel.eject();
            } else if (!this.subchannelsEjected() && subchannel.isEjected()) {
                subchannel.uneject();
            }
            subchannel.setEndpointTracker(this);
            return this.subchannels.add(subchannel);
        }

        boolean removeSubchannel(OutlierDetectionSubchannel subchannel) {
            subchannel.clearEndpointTracker();
            return this.subchannels.remove(subchannel);
        }

        boolean containsSubchannel(OutlierDetectionSubchannel subchannel) {
            return this.subchannels.contains(subchannel);
        }

        @VisibleForTesting
        Set<OutlierDetectionSubchannel> getSubchannels() {
            return ImmutableSet.copyOf(this.subchannels);
        }

        void incrementCallCount(boolean success) {
            if (this.config.successRateEjection == null && this.config.failurePercentageEjection == null) {
                return;
            }
            if (success) {
                this.activeCallCounter.successCount.getAndIncrement();
            } else {
                this.activeCallCounter.failureCount.getAndIncrement();
            }
        }

        @VisibleForTesting
        long activeVolume() {
            return this.activeCallCounter.successCount.get() + this.activeCallCounter.failureCount.get();
        }

        long inactiveVolume() {
            return this.inactiveCallCounter.successCount.get() + this.inactiveCallCounter.failureCount.get();
        }

        double successRate() {
            return (double)this.inactiveCallCounter.successCount.get() / (double)this.inactiveVolume();
        }

        double failureRate() {
            return (double)this.inactiveCallCounter.failureCount.get() / (double)this.inactiveVolume();
        }

        void resetCallCounters() {
            this.activeCallCounter.reset();
            this.inactiveCallCounter.reset();
        }

        void decrementEjectionTimeMultiplier() {
            this.ejectionTimeMultiplier = this.ejectionTimeMultiplier == 0 ? 0 : this.ejectionTimeMultiplier - 1;
        }

        void resetEjectionTimeMultiplier() {
            this.ejectionTimeMultiplier = 0;
        }

        void swapCounters() {
            this.inactiveCallCounter.reset();
            CallCounter tempCounter = this.activeCallCounter;
            this.activeCallCounter = this.inactiveCallCounter;
            this.inactiveCallCounter = tempCounter;
        }

        void ejectSubchannels(long ejectionTimeNanos) {
            this.ejectionTimeNanos = ejectionTimeNanos;
            ++this.ejectionTimeMultiplier;
            for (OutlierDetectionSubchannel subchannel : this.subchannels) {
                subchannel.eject();
            }
        }

        void unejectSubchannels() {
            Preconditions.checkState(this.ejectionTimeNanos != null, "not currently ejected");
            this.ejectionTimeNanos = null;
            for (OutlierDetectionSubchannel subchannel : this.subchannels) {
                subchannel.uneject();
            }
        }

        boolean subchannelsEjected() {
            return this.ejectionTimeNanos != null;
        }

        public boolean maxEjectionTimeElapsed(long currentTimeNanos) {
            long maxEjectionDurationSecs = Math.max(this.config.baseEjectionTimeNanos, this.config.maxEjectionTimeNanos);
            long maxEjectionTimeNanos = this.ejectionTimeNanos + Math.min(this.config.baseEjectionTimeNanos * (long)this.ejectionTimeMultiplier, maxEjectionDurationSecs);
            return currentTimeNanos > maxEjectionTimeNanos;
        }

        public String toString() {
            return "EndpointTracker{subchannels=" + this.subchannels + '}';
        }

        private static class CallCounter {
            AtomicLong successCount = new AtomicLong();
            AtomicLong failureCount = new AtomicLong();

            private CallCounter() {
            }

            void reset() {
                this.successCount.set(0L);
                this.failureCount.set(0L);
            }
        }
    }

    class OutlierDetectionPicker
    extends LoadBalancer.SubchannelPicker {
        private final LoadBalancer.SubchannelPicker delegate;

        OutlierDetectionPicker(LoadBalancer.SubchannelPicker delegate) {
            this.delegate = delegate;
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            LoadBalancer.PickResult pickResult = this.delegate.pickSubchannel(args2);
            LoadBalancer.Subchannel subchannel = pickResult.getSubchannel();
            if (subchannel != null) {
                return LoadBalancer.PickResult.withSubchannel(subchannel, new ResultCountingClientStreamTracerFactory((EndpointTracker)subchannel.getAttributes().get(ENDPOINT_TRACKER_KEY), pickResult.getStreamTracerFactory()));
            }
            return pickResult;
        }

        class ResultCountingClientStreamTracerFactory
        extends ClientStreamTracer.Factory {
            private final EndpointTracker tracker;
            @Nullable
            private final ClientStreamTracer.Factory delegateFactory;

            ResultCountingClientStreamTracerFactory(@Nullable EndpointTracker tracker, ClientStreamTracer.Factory delegateFactory) {
                this.tracker = tracker;
                this.delegateFactory = delegateFactory;
            }

            @Override
            public ClientStreamTracer newClientStreamTracer(ClientStreamTracer.StreamInfo info, Metadata headers) {
                if (this.delegateFactory != null) {
                    final ClientStreamTracer delegateTracer = this.delegateFactory.newClientStreamTracer(info, headers);
                    return new ForwardingClientStreamTracer(){

                        @Override
                        protected ClientStreamTracer delegate() {
                            return delegateTracer;
                        }

                        @Override
                        public void streamClosed(Status status) {
                            ResultCountingClientStreamTracerFactory.this.tracker.incrementCallCount(status.isOk());
                            this.delegate().streamClosed(status);
                        }
                    };
                }
                return new ClientStreamTracer(){

                    @Override
                    public void streamClosed(Status status) {
                        ResultCountingClientStreamTracerFactory.this.tracker.incrementCallCount(status.isOk());
                    }
                };
            }
        }
    }

    class OutlierDetectionSubchannel
    extends ForwardingSubchannel {
        private final LoadBalancer.Subchannel delegate;
        private EndpointTracker endpointTracker;
        private boolean ejected;
        private ConnectivityStateInfo lastSubchannelState;
        private LoadBalancer.SubchannelStateListener subchannelStateListener;
        private final ChannelLogger logger;

        OutlierDetectionSubchannel(LoadBalancer.CreateSubchannelArgs args2, LoadBalancer.Helper helper) {
            LoadBalancer.SubchannelStateListener healthConsumerListener = args2.getOption(LoadBalancer.HEALTH_CONSUMER_LISTENER_ARG_KEY);
            if (healthConsumerListener != null) {
                this.subchannelStateListener = healthConsumerListener;
                OutlierDetectionSubchannelStateListener upstreamListener = new OutlierDetectionSubchannelStateListener(healthConsumerListener);
                this.delegate = helper.createSubchannel(args2.toBuilder().addOption(LoadBalancer.HEALTH_CONSUMER_LISTENER_ARG_KEY, upstreamListener).build());
            } else {
                this.delegate = helper.createSubchannel(args2);
            }
            this.logger = this.delegate.getChannelLogger();
        }

        @Override
        public void start(LoadBalancer.SubchannelStateListener listener) {
            if (this.subchannelStateListener != null) {
                super.start(listener);
            } else {
                this.subchannelStateListener = listener;
                super.start(new OutlierDetectionSubchannelStateListener(listener));
            }
        }

        @Override
        public void shutdown() {
            if (this.endpointTracker != null) {
                this.endpointTracker.removeSubchannel(this);
            }
            super.shutdown();
        }

        @Override
        public Attributes getAttributes() {
            if (this.endpointTracker != null) {
                return this.delegate.getAttributes().toBuilder().set(ENDPOINT_TRACKER_KEY, this.endpointTracker).build();
            }
            return this.delegate.getAttributes();
        }

        @Override
        public void updateAddresses(List<EquivalentAddressGroup> addressGroups) {
            SocketAddress address;
            if (OutlierDetectionLoadBalancer.hasSingleAddress(this.getAllAddresses()) && OutlierDetectionLoadBalancer.hasSingleAddress(addressGroups)) {
                SocketAddress address2;
                if (OutlierDetectionLoadBalancer.this.endpointTrackerMap.containsValue(this.endpointTracker)) {
                    this.endpointTracker.removeSubchannel(this);
                }
                if (OutlierDetectionLoadBalancer.this.addressMap.containsKey(address2 = addressGroups.get(0).getAddresses().get(0))) {
                    OutlierDetectionLoadBalancer.this.addressMap.get(address2).addSubchannel(this);
                }
            } else if (OutlierDetectionLoadBalancer.hasSingleAddress(this.getAllAddresses()) && !OutlierDetectionLoadBalancer.hasSingleAddress(addressGroups)) {
                if (OutlierDetectionLoadBalancer.this.addressMap.containsKey(this.getAddresses().getAddresses().get(0))) {
                    EndpointTracker tracker = OutlierDetectionLoadBalancer.this.addressMap.get(this.getAddresses().getAddresses().get(0));
                    tracker.removeSubchannel(this);
                    tracker.resetCallCounters();
                }
            } else if (!OutlierDetectionLoadBalancer.hasSingleAddress(this.getAllAddresses()) && OutlierDetectionLoadBalancer.hasSingleAddress(addressGroups) && OutlierDetectionLoadBalancer.this.addressMap.containsKey(address = addressGroups.get(0).getAddresses().get(0))) {
                EndpointTracker tracker = OutlierDetectionLoadBalancer.this.addressMap.get(address);
                tracker.addSubchannel(this);
            }
            this.delegate.updateAddresses(addressGroups);
        }

        void setEndpointTracker(EndpointTracker endpointTracker) {
            this.endpointTracker = endpointTracker;
        }

        void clearEndpointTracker() {
            this.endpointTracker = null;
        }

        void eject() {
            this.ejected = true;
            this.subchannelStateListener.onSubchannelState(ConnectivityStateInfo.forTransientFailure(Status.UNAVAILABLE.withDescription("The subchannel has been ejected by outlier detection")));
            this.logger.log(ChannelLogger.ChannelLogLevel.INFO, "Subchannel ejected: {0}", this);
        }

        void uneject() {
            this.ejected = false;
            if (this.lastSubchannelState != null) {
                this.subchannelStateListener.onSubchannelState(this.lastSubchannelState);
                this.logger.log(ChannelLogger.ChannelLogLevel.INFO, "Subchannel unejected: {0}", this);
            }
        }

        boolean isEjected() {
            return this.ejected;
        }

        @Override
        protected LoadBalancer.Subchannel delegate() {
            return this.delegate;
        }

        @Override
        public String toString() {
            return "OutlierDetectionSubchannel{addresses=" + this.delegate.getAllAddresses() + '}';
        }

        class OutlierDetectionSubchannelStateListener
        implements LoadBalancer.SubchannelStateListener {
            private final LoadBalancer.SubchannelStateListener delegate;

            OutlierDetectionSubchannelStateListener(LoadBalancer.SubchannelStateListener delegate) {
                this.delegate = delegate;
            }

            @Override
            public void onSubchannelState(ConnectivityStateInfo newState) {
                OutlierDetectionSubchannel.this.lastSubchannelState = newState;
                if (!OutlierDetectionSubchannel.this.ejected) {
                    this.delegate.onSubchannelState(newState);
                }
            }
        }
    }

    class ChildHelper
    extends ForwardingLoadBalancerHelper {
        private LoadBalancer.Helper delegate;

        ChildHelper(LoadBalancer.Helper delegate) {
            this.delegate = new HealthProducerHelper(delegate);
        }

        @Override
        protected LoadBalancer.Helper delegate() {
            return this.delegate;
        }

        @Override
        public LoadBalancer.Subchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args2) {
            OutlierDetectionSubchannel subchannel = new OutlierDetectionSubchannel(args2, this.delegate);
            List<EquivalentAddressGroup> addressGroups = args2.getAddresses();
            if (OutlierDetectionLoadBalancer.hasSingleAddress(addressGroups) && OutlierDetectionLoadBalancer.this.addressMap.containsKey(addressGroups.get(0).getAddresses().get(0))) {
                EndpointTracker tracker = OutlierDetectionLoadBalancer.this.addressMap.get(addressGroups.get(0).getAddresses().get(0));
                tracker.addSubchannel(subchannel);
                if (tracker.ejectionTimeNanos != null) {
                    subchannel.eject();
                }
            }
            return subchannel;
        }

        @Override
        public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
            this.delegate.updateBalancingState(newState, new OutlierDetectionPicker(newPicker));
        }
    }

    class DetectionTimer
    implements Runnable {
        OutlierDetectionLoadBalancerConfig config;
        ChannelLogger logger;

        DetectionTimer(OutlierDetectionLoadBalancerConfig config, ChannelLogger logger2) {
            this.config = config;
            this.logger = logger2;
        }

        @Override
        public void run() {
            OutlierDetectionLoadBalancer.this.detectionTimerStartNanos = OutlierDetectionLoadBalancer.this.timeProvider.currentTimeNanos();
            OutlierDetectionLoadBalancer.this.endpointTrackerMap.swapCounters();
            for (OutlierEjectionAlgorithm algo : OutlierEjectionAlgorithm.forConfig(this.config, this.logger)) {
                algo.ejectOutliers(OutlierDetectionLoadBalancer.this.endpointTrackerMap, OutlierDetectionLoadBalancer.this.detectionTimerStartNanos);
            }
            OutlierDetectionLoadBalancer.this.endpointTrackerMap.maybeUnejectOutliers(OutlierDetectionLoadBalancer.this.detectionTimerStartNanos);
        }
    }
}

