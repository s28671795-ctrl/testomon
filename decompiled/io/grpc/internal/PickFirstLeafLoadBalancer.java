/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.grpc.Attributes;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.PickFirstLoadBalancerProvider;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class PickFirstLeafLoadBalancer
extends LoadBalancer {
    private static final Logger log = Logger.getLogger(PickFirstLeafLoadBalancer.class.getName());
    @VisibleForTesting
    static final int CONNECTION_DELAY_INTERVAL_MS = 250;
    private final LoadBalancer.Helper helper;
    private final Map<SocketAddress, SubchannelData> subchannels = new HashMap<SocketAddress, SubchannelData>();
    private Index addressIndex;
    private int numTf = 0;
    private boolean firstPass = true;
    @Nullable
    private SynchronizationContext.ScheduledHandle scheduleConnectionTask;
    private ConnectivityState rawConnectivityState = ConnectivityState.IDLE;
    private ConnectivityState concludedState = ConnectivityState.IDLE;
    private final boolean enableHappyEyeballs = PickFirstLoadBalancerProvider.isEnabledHappyEyeballs();

    PickFirstLeafLoadBalancer(LoadBalancer.Helper helper) {
        this.helper = Preconditions.checkNotNull(helper, "helper");
    }

    @Override
    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        if (this.rawConnectivityState == ConnectivityState.SHUTDOWN) {
            return Status.FAILED_PRECONDITION.withDescription("Already shut down");
        }
        List<EquivalentAddressGroup> servers = resolvedAddresses.getAddresses();
        if (servers.isEmpty()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
            this.handleNameResolutionError(unavailableStatus);
            return unavailableStatus;
        }
        for (EquivalentAddressGroup eag : servers) {
            if (eag != null) continue;
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned address list with null endpoint. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
            this.handleNameResolutionError(unavailableStatus);
            return unavailableStatus;
        }
        this.firstPass = true;
        if (resolvedAddresses.getLoadBalancingPolicyConfig() instanceof PickFirstLeafLoadBalancerConfig) {
            PickFirstLeafLoadBalancerConfig config = (PickFirstLeafLoadBalancerConfig)resolvedAddresses.getLoadBalancingPolicyConfig();
            if (config.shuffleAddressList != null && config.shuffleAddressList.booleanValue()) {
                servers = new ArrayList<EquivalentAddressGroup>(servers);
                Collections.shuffle(servers, config.randomSeed != null ? new Random(config.randomSeed) : new Random());
            }
        }
        ImmutableCollection newImmutableAddressGroups = ((ImmutableList.Builder)ImmutableList.builder().addAll(servers)).build();
        if (this.addressIndex == null) {
            this.addressIndex = new Index((List<EquivalentAddressGroup>)((Object)newImmutableAddressGroups));
        } else if (this.rawConnectivityState == ConnectivityState.READY) {
            SocketAddress previousAddress = this.addressIndex.getCurrentAddress();
            this.addressIndex.updateGroups((ImmutableList<EquivalentAddressGroup>)newImmutableAddressGroups);
            if (this.addressIndex.seekTo(previousAddress)) {
                SubchannelData subchannelData = this.subchannels.get(previousAddress);
                subchannelData.getSubchannel().updateAddresses(this.addressIndex.getCurrentEagAsList());
                return Status.OK;
            }
            this.addressIndex.reset();
        } else {
            this.addressIndex.updateGroups((ImmutableList<EquivalentAddressGroup>)newImmutableAddressGroups);
        }
        HashSet<SocketAddress> oldAddrs = new HashSet<SocketAddress>(this.subchannels.keySet());
        HashSet<SocketAddress> newAddrs = new HashSet<SocketAddress>();
        for (EquivalentAddressGroup endpoint : newImmutableAddressGroups) {
            newAddrs.addAll(endpoint.getAddresses());
        }
        for (SocketAddress oldAddr : oldAddrs) {
            if (newAddrs.contains(oldAddr)) continue;
            this.subchannels.remove(oldAddr).getSubchannel().shutdown();
        }
        if (oldAddrs.size() == 0 || this.rawConnectivityState == ConnectivityState.CONNECTING || this.rawConnectivityState == ConnectivityState.READY) {
            this.rawConnectivityState = ConnectivityState.CONNECTING;
            this.updateBalancingState(ConnectivityState.CONNECTING, new Picker(LoadBalancer.PickResult.withNoResult()));
            this.cancelScheduleTask();
            this.requestConnection();
        } else if (this.rawConnectivityState == ConnectivityState.IDLE) {
            RequestConnectionPicker picker = new RequestConnectionPicker(this);
            this.updateBalancingState(ConnectivityState.IDLE, picker);
        } else if (this.rawConnectivityState == ConnectivityState.TRANSIENT_FAILURE) {
            this.cancelScheduleTask();
            this.requestConnection();
        }
        return Status.OK;
    }

    @Override
    public void handleNameResolutionError(Status error) {
        for (SubchannelData subchannelData : this.subchannels.values()) {
            subchannelData.getSubchannel().shutdown();
        }
        this.subchannels.clear();
        this.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(error)));
    }

    void processSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        ConnectivityState newState = stateInfo.getState();
        SubchannelData subchannelData = this.subchannels.get(this.getAddress(subchannel));
        if (subchannelData == null || subchannelData.getSubchannel() != subchannel) {
            return;
        }
        if (newState == ConnectivityState.SHUTDOWN) {
            return;
        }
        if (newState == ConnectivityState.IDLE) {
            this.helper.refreshNameResolution();
        }
        subchannelData.updateState(newState);
        if (this.rawConnectivityState == ConnectivityState.TRANSIENT_FAILURE || this.concludedState == ConnectivityState.TRANSIENT_FAILURE) {
            if (newState == ConnectivityState.CONNECTING) {
                return;
            }
            if (newState == ConnectivityState.IDLE) {
                this.requestConnection();
                return;
            }
        }
        switch (newState) {
            case IDLE: {
                this.addressIndex.reset();
                this.rawConnectivityState = ConnectivityState.IDLE;
                this.updateBalancingState(ConnectivityState.IDLE, new RequestConnectionPicker(this));
                break;
            }
            case CONNECTING: {
                this.rawConnectivityState = ConnectivityState.CONNECTING;
                this.updateBalancingState(ConnectivityState.CONNECTING, new Picker(LoadBalancer.PickResult.withNoResult()));
                break;
            }
            case READY: {
                this.shutdownRemaining(subchannelData);
                this.addressIndex.seekTo(this.getAddress(subchannel));
                this.rawConnectivityState = ConnectivityState.READY;
                this.updateHealthCheckedState(subchannelData);
                break;
            }
            case TRANSIENT_FAILURE: {
                if (this.addressIndex.isValid() && this.subchannels.get(this.addressIndex.getCurrentAddress()).getSubchannel() == subchannel && this.addressIndex.increment()) {
                    this.cancelScheduleTask();
                    this.requestConnection();
                }
                if (!this.isPassComplete()) break;
                this.rawConnectivityState = ConnectivityState.TRANSIENT_FAILURE;
                this.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(stateInfo.getStatus())));
                if (++this.numTf < this.addressIndex.size() && !this.firstPass) break;
                this.firstPass = false;
                this.numTf = 0;
                this.helper.refreshNameResolution();
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported state:" + (Object)((Object)newState));
            }
        }
    }

    private void updateHealthCheckedState(SubchannelData subchannelData) {
        if (subchannelData.state != ConnectivityState.READY) {
            return;
        }
        if (subchannelData.getHealthState() == ConnectivityState.READY) {
            this.updateBalancingState(ConnectivityState.READY, new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withSubchannel(subchannelData.subchannel)));
        } else if (subchannelData.getHealthState() == ConnectivityState.TRANSIENT_FAILURE) {
            this.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(subchannelData.healthListener.healthStateInfo.getStatus())));
        } else if (this.concludedState != ConnectivityState.TRANSIENT_FAILURE) {
            this.updateBalancingState(subchannelData.getHealthState(), new Picker(LoadBalancer.PickResult.withNoResult()));
        }
    }

    private void updateBalancingState(ConnectivityState state, LoadBalancer.SubchannelPicker picker) {
        if (state == this.concludedState && (state == ConnectivityState.IDLE || state == ConnectivityState.CONNECTING)) {
            return;
        }
        this.concludedState = state;
        this.helper.updateBalancingState(state, picker);
    }

    @Override
    public void shutdown() {
        log.log(Level.FINE, "Shutting down, currently have {} subchannels created", this.subchannels.size());
        this.rawConnectivityState = ConnectivityState.SHUTDOWN;
        this.concludedState = ConnectivityState.SHUTDOWN;
        this.cancelScheduleTask();
        for (SubchannelData subchannelData : this.subchannels.values()) {
            subchannelData.getSubchannel().shutdown();
        }
        this.subchannels.clear();
    }

    private void shutdownRemaining(SubchannelData activeSubchannelData) {
        this.cancelScheduleTask();
        for (SubchannelData subchannelData : this.subchannels.values()) {
            if (subchannelData.getSubchannel().equals(activeSubchannelData.subchannel)) continue;
            subchannelData.getSubchannel().shutdown();
        }
        this.subchannels.clear();
        activeSubchannelData.updateState(ConnectivityState.READY);
        this.subchannels.put(this.getAddress(activeSubchannelData.subchannel), activeSubchannelData);
    }

    @Override
    public void requestConnection() {
        if (this.addressIndex == null || !this.addressIndex.isValid() || this.rawConnectivityState == ConnectivityState.SHUTDOWN) {
            return;
        }
        SocketAddress currentAddress = this.addressIndex.getCurrentAddress();
        LoadBalancer.Subchannel subchannel = this.subchannels.containsKey(currentAddress) ? this.subchannels.get(currentAddress).getSubchannel() : this.createNewSubchannel(currentAddress, this.addressIndex.getCurrentEagAttributes());
        ConnectivityState subchannelState = this.subchannels.get(currentAddress).getState();
        switch (subchannelState) {
            case IDLE: {
                subchannel.requestConnection();
                this.subchannels.get(currentAddress).updateState(ConnectivityState.CONNECTING);
                this.scheduleNextConnection();
                break;
            }
            case CONNECTING: {
                if (this.enableHappyEyeballs) {
                    this.scheduleNextConnection();
                    break;
                }
                subchannel.requestConnection();
                break;
            }
            case TRANSIENT_FAILURE: {
                this.addressIndex.increment();
                this.requestConnection();
                break;
            }
            case READY: {
                log.warning("Requesting a connection even though we have a READY subchannel");
                break;
            }
        }
    }

    private void scheduleNextConnection() {
        if (!this.enableHappyEyeballs || this.scheduleConnectionTask != null && this.scheduleConnectionTask.isPending()) {
            return;
        }
        SynchronizationContext synchronizationContext = null;
        try {
            synchronizationContext = this.helper.getSynchronizationContext();
        }
        catch (NullPointerException e) {
            return;
        }
        class StartNextConnection
        implements Runnable {
            StartNextConnection() {
            }

            @Override
            public void run() {
                PickFirstLeafLoadBalancer.this.scheduleConnectionTask = null;
                if (PickFirstLeafLoadBalancer.this.addressIndex.increment()) {
                    PickFirstLeafLoadBalancer.this.requestConnection();
                }
            }
        }
        this.scheduleConnectionTask = synchronizationContext.schedule(new StartNextConnection(), 250L, TimeUnit.MILLISECONDS, this.helper.getScheduledExecutorService());
    }

    private void cancelScheduleTask() {
        if (this.scheduleConnectionTask != null) {
            this.scheduleConnectionTask.cancel();
            this.scheduleConnectionTask = null;
        }
    }

    private LoadBalancer.Subchannel createNewSubchannel(SocketAddress addr, Attributes attrs) {
        HealthListener hcListener = new HealthListener();
        LoadBalancer.Subchannel subchannel = this.helper.createSubchannel(LoadBalancer.CreateSubchannelArgs.newBuilder().setAddresses(Lists.newArrayList(new EquivalentAddressGroup(addr, attrs))).addOption(HEALTH_CONSUMER_LISTENER_ARG_KEY, hcListener).build());
        if (subchannel == null) {
            log.warning("Was not able to create subchannel for " + addr);
            throw new IllegalStateException("Can't create subchannel");
        }
        SubchannelData subchannelData = new SubchannelData(subchannel, ConnectivityState.IDLE, hcListener);
        hcListener.subchannelData = subchannelData;
        this.subchannels.put(addr, subchannelData);
        Attributes scAttrs = subchannel.getAttributes();
        if (scAttrs.get(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY) == null) {
            hcListener.healthStateInfo = ConnectivityStateInfo.forNonError(ConnectivityState.READY);
        }
        subchannel.start(stateInfo -> this.processSubchannelState(subchannel, stateInfo));
        return subchannel;
    }

    private boolean isPassComplete() {
        if (this.addressIndex == null || this.addressIndex.isValid() || this.subchannels.size() < this.addressIndex.size()) {
            return false;
        }
        for (SubchannelData sc : this.subchannels.values()) {
            if (sc.isCompletedConnectivityAttempt()) continue;
            return false;
        }
        return true;
    }

    private SocketAddress getAddress(LoadBalancer.Subchannel subchannel) {
        return subchannel.getAddresses().getAddresses().get(0);
    }

    @VisibleForTesting
    ConnectivityState getConcludedConnectivityState() {
        return this.concludedState;
    }

    public static final class PickFirstLeafLoadBalancerConfig {
        @Nullable
        public final Boolean shuffleAddressList;
        @Nullable
        final Long randomSeed;

        public PickFirstLeafLoadBalancerConfig(@Nullable Boolean shuffleAddressList) {
            this(shuffleAddressList, null);
        }

        PickFirstLeafLoadBalancerConfig(@Nullable Boolean shuffleAddressList, @Nullable Long randomSeed) {
            this.shuffleAddressList = shuffleAddressList;
            this.randomSeed = randomSeed;
        }
    }

    private static final class SubchannelData {
        private final LoadBalancer.Subchannel subchannel;
        private ConnectivityState state;
        private final HealthListener healthListener;
        private boolean completedConnectivityAttempt = false;

        public SubchannelData(LoadBalancer.Subchannel subchannel, ConnectivityState state, HealthListener subchannelHealthListener) {
            this.subchannel = subchannel;
            this.state = state;
            this.healthListener = subchannelHealthListener;
        }

        public LoadBalancer.Subchannel getSubchannel() {
            return this.subchannel;
        }

        public ConnectivityState getState() {
            return this.state;
        }

        public boolean isCompletedConnectivityAttempt() {
            return this.completedConnectivityAttempt;
        }

        private void updateState(ConnectivityState newState) {
            this.state = newState;
            if (newState == ConnectivityState.READY || newState == ConnectivityState.TRANSIENT_FAILURE) {
                this.completedConnectivityAttempt = true;
            } else if (newState == ConnectivityState.IDLE) {
                this.completedConnectivityAttempt = false;
            }
        }

        private ConnectivityState getHealthState() {
            return this.healthListener.healthStateInfo.getState();
        }
    }

    @VisibleForTesting
    static final class Index {
        private List<EquivalentAddressGroup> addressGroups;
        private int groupIndex;
        private int addressIndex;

        public Index(List<EquivalentAddressGroup> groups2) {
            this.addressGroups = groups2 != null ? groups2 : Collections.emptyList();
        }

        public boolean isValid() {
            return this.groupIndex < this.addressGroups.size();
        }

        public boolean isAtBeginning() {
            return this.groupIndex == 0 && this.addressIndex == 0;
        }

        public boolean increment() {
            if (!this.isValid()) {
                return false;
            }
            EquivalentAddressGroup group = this.addressGroups.get(this.groupIndex);
            ++this.addressIndex;
            if (this.addressIndex >= group.getAddresses().size()) {
                ++this.groupIndex;
                this.addressIndex = 0;
                return this.groupIndex < this.addressGroups.size();
            }
            return true;
        }

        public void reset() {
            this.groupIndex = 0;
            this.addressIndex = 0;
        }

        public SocketAddress getCurrentAddress() {
            if (!this.isValid()) {
                throw new IllegalStateException("Index is past the end of the address group list");
            }
            return this.addressGroups.get(this.groupIndex).getAddresses().get(this.addressIndex);
        }

        public Attributes getCurrentEagAttributes() {
            if (!this.isValid()) {
                throw new IllegalStateException("Index is off the end of the address group list");
            }
            return this.addressGroups.get(this.groupIndex).getAttributes();
        }

        public List<EquivalentAddressGroup> getCurrentEagAsList() {
            return Collections.singletonList(new EquivalentAddressGroup(this.getCurrentAddress(), this.getCurrentEagAttributes()));
        }

        public void updateGroups(ImmutableList<EquivalentAddressGroup> newGroups) {
            this.addressGroups = newGroups != null ? newGroups : Collections.emptyList();
            this.reset();
        }

        public boolean seekTo(SocketAddress needle) {
            for (int i = 0; i < this.addressGroups.size(); ++i) {
                EquivalentAddressGroup group = this.addressGroups.get(i);
                int j = group.getAddresses().indexOf(needle);
                if (j == -1) continue;
                this.groupIndex = i;
                this.addressIndex = j;
                return true;
            }
            return false;
        }

        public int size() {
            return this.addressGroups != null ? this.addressGroups.size() : 0;
        }
    }

    private final class RequestConnectionPicker
    extends LoadBalancer.SubchannelPicker {
        private final PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer;
        private final AtomicBoolean connectionRequested = new AtomicBoolean(false);

        RequestConnectionPicker(PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer2) {
            this.pickFirstLeafLoadBalancer = Preconditions.checkNotNull(pickFirstLeafLoadBalancer2, "pickFirstLeafLoadBalancer");
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            if (this.connectionRequested.compareAndSet(false, true)) {
                PickFirstLeafLoadBalancer.this.helper.getSynchronizationContext().execute(this.pickFirstLeafLoadBalancer::requestConnection);
            }
            return LoadBalancer.PickResult.withNoResult();
        }
    }

    private static final class Picker
    extends LoadBalancer.SubchannelPicker {
        private final LoadBalancer.PickResult result;

        Picker(LoadBalancer.PickResult result) {
            this.result = Preconditions.checkNotNull(result, "result");
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return this.result;
        }

        public String toString() {
            return MoreObjects.toStringHelper(Picker.class).add("result", this.result).toString();
        }
    }

    private final class HealthListener
    implements LoadBalancer.SubchannelStateListener {
        private ConnectivityStateInfo healthStateInfo = ConnectivityStateInfo.forNonError(ConnectivityState.IDLE);
        private SubchannelData subchannelData;

        private HealthListener() {
        }

        @Override
        public void onSubchannelState(ConnectivityStateInfo newState) {
            log.log(Level.FINE, "Received health status {0} for subchannel {1}", new Object[]{newState, this.subchannelData.subchannel});
            this.healthStateInfo = newState;
            try {
                SubchannelData curSubChanData = (SubchannelData)PickFirstLeafLoadBalancer.this.subchannels.get(PickFirstLeafLoadBalancer.this.addressIndex.getCurrentAddress());
                if (curSubChanData != null && curSubChanData.healthListener == this) {
                    PickFirstLeafLoadBalancer.this.updateHealthCheckedState(this.subchannelData);
                }
            }
            catch (IllegalStateException e) {
                log.fine("Health listener received state change after subchannel was removed");
            }
        }
    }
}

