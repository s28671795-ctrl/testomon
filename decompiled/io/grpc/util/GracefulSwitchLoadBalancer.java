/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancer;
import io.grpc.Status;
import io.grpc.util.ForwardingLoadBalancer;
import io.grpc.util.ForwardingLoadBalancerHelper;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/5999")
@NotThreadSafe
public final class GracefulSwitchLoadBalancer
extends ForwardingLoadBalancer {
    private final LoadBalancer defaultBalancer;
    @VisibleForTesting
    static final LoadBalancer.SubchannelPicker BUFFER_PICKER = new LoadBalancer.SubchannelPicker(){

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public String toString() {
            return "BUFFER_PICKER";
        }
    };
    private final LoadBalancer.Helper helper;
    @Nullable
    private LoadBalancer.Factory currentBalancerFactory;
    private LoadBalancer currentLb;
    @Nullable
    private LoadBalancer.Factory pendingBalancerFactory;
    private LoadBalancer pendingLb;
    private ConnectivityState pendingState;
    private LoadBalancer.SubchannelPicker pendingPicker;
    private boolean currentLbIsReady;

    public GracefulSwitchLoadBalancer(LoadBalancer.Helper helper) {
        this.currentLb = this.defaultBalancer = new LoadBalancer(){

            @Override
            public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
                throw new IllegalStateException("GracefulSwitchLoadBalancer must switch to a load balancing policy before handling ResolvedAddresses");
            }

            @Override
            public void handleNameResolutionError(Status error) {
                GracefulSwitchLoadBalancer.this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withError(error)));
            }

            @Override
            public void shutdown() {
            }
        };
        this.pendingLb = this.defaultBalancer;
        this.helper = Preconditions.checkNotNull(helper, "helper");
    }

    public void switchTo(LoadBalancer.Factory newBalancerFactory) {
        Preconditions.checkNotNull(newBalancerFactory, "newBalancerFactory");
        if (newBalancerFactory.equals(this.pendingBalancerFactory)) {
            return;
        }
        this.pendingLb.shutdown();
        this.pendingLb = this.defaultBalancer;
        this.pendingBalancerFactory = null;
        this.pendingState = ConnectivityState.CONNECTING;
        this.pendingPicker = BUFFER_PICKER;
        if (newBalancerFactory.equals(this.currentBalancerFactory)) {
            return;
        }
        class PendingHelper
        extends ForwardingLoadBalancerHelper {
            LoadBalancer lb;

            PendingHelper() {
            }

            @Override
            protected LoadBalancer.Helper delegate() {
                return GracefulSwitchLoadBalancer.this.helper;
            }

            @Override
            public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
                if (this.lb == GracefulSwitchLoadBalancer.this.pendingLb) {
                    Preconditions.checkState(GracefulSwitchLoadBalancer.this.currentLbIsReady, "there's pending lb while current lb has been out of READY");
                    GracefulSwitchLoadBalancer.this.pendingState = newState;
                    GracefulSwitchLoadBalancer.this.pendingPicker = newPicker;
                    if (newState == ConnectivityState.READY) {
                        GracefulSwitchLoadBalancer.this.swap();
                    }
                } else if (this.lb == GracefulSwitchLoadBalancer.this.currentLb) {
                    GracefulSwitchLoadBalancer.this.currentLbIsReady = newState == ConnectivityState.READY;
                    if (!GracefulSwitchLoadBalancer.this.currentLbIsReady && GracefulSwitchLoadBalancer.this.pendingLb != GracefulSwitchLoadBalancer.this.defaultBalancer) {
                        GracefulSwitchLoadBalancer.this.swap();
                    } else {
                        GracefulSwitchLoadBalancer.this.helper.updateBalancingState(newState, newPicker);
                    }
                }
            }
        }
        PendingHelper pendingHelper = new PendingHelper();
        this.pendingLb = pendingHelper.lb = newBalancerFactory.newLoadBalancer(pendingHelper);
        this.pendingBalancerFactory = newBalancerFactory;
        if (!this.currentLbIsReady) {
            this.swap();
        }
    }

    private void swap() {
        this.helper.updateBalancingState(this.pendingState, this.pendingPicker);
        this.currentLb.shutdown();
        this.currentLb = this.pendingLb;
        this.currentBalancerFactory = this.pendingBalancerFactory;
        this.pendingLb = this.defaultBalancer;
        this.pendingBalancerFactory = null;
    }

    @Override
    protected LoadBalancer delegate() {
        return this.pendingLb == this.defaultBalancer ? this.currentLb : this.pendingLb;
    }

    @Override
    @Deprecated
    public void handleSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        throw new UnsupportedOperationException("handleSubchannelState() is not supported by " + this.getClass().getName());
    }

    @Override
    public void shutdown() {
        this.pendingLb.shutdown();
        this.currentLb.shutdown();
    }

    public String delegateType() {
        return this.delegate().getClass().getSimpleName();
    }
}

