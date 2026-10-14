/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

final class PickFirstLoadBalancer
extends LoadBalancer {
    private final LoadBalancer.Helper helper;
    private LoadBalancer.Subchannel subchannel;
    private ConnectivityState currentState = ConnectivityState.IDLE;

    PickFirstLoadBalancer(LoadBalancer.Helper helper) {
        this.helper = Preconditions.checkNotNull(helper, "helper");
    }

    @Override
    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        List<EquivalentAddressGroup> servers = resolvedAddresses.getAddresses();
        if (servers.isEmpty()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
            this.handleNameResolutionError(unavailableStatus);
            return unavailableStatus;
        }
        if (resolvedAddresses.getLoadBalancingPolicyConfig() instanceof PickFirstLoadBalancerConfig) {
            PickFirstLoadBalancerConfig config = (PickFirstLoadBalancerConfig)resolvedAddresses.getLoadBalancingPolicyConfig();
            if (config.shuffleAddressList != null && config.shuffleAddressList.booleanValue()) {
                servers = new ArrayList<EquivalentAddressGroup>(servers);
                Collections.shuffle(servers, config.randomSeed != null ? new Random(config.randomSeed) : new Random());
            }
        }
        if (this.subchannel == null) {
            final LoadBalancer.Subchannel subchannel = this.helper.createSubchannel(LoadBalancer.CreateSubchannelArgs.newBuilder().setAddresses(servers).build());
            subchannel.start(new LoadBalancer.SubchannelStateListener(){

                @Override
                public void onSubchannelState(ConnectivityStateInfo stateInfo) {
                    PickFirstLoadBalancer.this.processSubchannelState(subchannel, stateInfo);
                }
            });
            this.subchannel = subchannel;
            this.updateBalancingState(ConnectivityState.CONNECTING, new Picker(LoadBalancer.PickResult.withSubchannel(subchannel)));
            subchannel.requestConnection();
        } else {
            this.subchannel.updateAddresses(servers);
        }
        return Status.OK;
    }

    @Override
    public void handleNameResolutionError(Status error) {
        if (this.subchannel != null) {
            this.subchannel.shutdown();
            this.subchannel = null;
        }
        this.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(error)));
    }

    private void processSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        LoadBalancer.SubchannelPicker picker;
        ConnectivityState newState = stateInfo.getState();
        if (newState == ConnectivityState.SHUTDOWN) {
            return;
        }
        if (newState == ConnectivityState.TRANSIENT_FAILURE || newState == ConnectivityState.IDLE) {
            this.helper.refreshNameResolution();
        }
        if (this.currentState == ConnectivityState.TRANSIENT_FAILURE) {
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
                picker = new RequestConnectionPicker(subchannel);
                break;
            }
            case CONNECTING: {
                picker = new Picker(LoadBalancer.PickResult.withNoResult());
                break;
            }
            case READY: {
                picker = new Picker(LoadBalancer.PickResult.withSubchannel(subchannel));
                break;
            }
            case TRANSIENT_FAILURE: {
                picker = new Picker(LoadBalancer.PickResult.withError(stateInfo.getStatus()));
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported state:" + (Object)((Object)newState));
            }
        }
        this.updateBalancingState(newState, picker);
    }

    private void updateBalancingState(ConnectivityState state, LoadBalancer.SubchannelPicker picker) {
        this.currentState = state;
        this.helper.updateBalancingState(state, picker);
    }

    @Override
    public void shutdown() {
        if (this.subchannel != null) {
            this.subchannel.shutdown();
        }
    }

    @Override
    public void requestConnection() {
        if (this.subchannel != null) {
            this.subchannel.requestConnection();
        }
    }

    public static final class PickFirstLoadBalancerConfig {
        @Nullable
        public final Boolean shuffleAddressList;
        @Nullable
        final Long randomSeed;

        public PickFirstLoadBalancerConfig(@Nullable Boolean shuffleAddressList) {
            this(shuffleAddressList, null);
        }

        PickFirstLoadBalancerConfig(@Nullable Boolean shuffleAddressList, @Nullable Long randomSeed) {
            this.shuffleAddressList = shuffleAddressList;
            this.randomSeed = randomSeed;
        }
    }

    private final class RequestConnectionPicker
    extends LoadBalancer.SubchannelPicker {
        private final LoadBalancer.Subchannel subchannel;
        private final AtomicBoolean connectionRequested = new AtomicBoolean(false);

        RequestConnectionPicker(LoadBalancer.Subchannel subchannel) {
            this.subchannel = Preconditions.checkNotNull(subchannel, "subchannel");
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            if (this.connectionRequested.compareAndSet(false, true)) {
                PickFirstLoadBalancer.this.helper.getSynchronizationContext().execute(new Runnable(){

                    @Override
                    public void run() {
                        RequestConnectionPicker.this.subchannel.requestConnection();
                    }
                });
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
}

