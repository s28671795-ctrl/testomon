/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.util.ForwardingLoadBalancerHelper;
import io.grpc.util.ForwardingSubchannel;

@Internal
public final class HealthProducerHelper
extends ForwardingLoadBalancerHelper {
    private final LoadBalancer.Helper delegate;

    public HealthProducerHelper(LoadBalancer.Helper helper) {
        this.delegate = Preconditions.checkNotNull(helper, "helper");
    }

    @Override
    public LoadBalancer.Subchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args2) {
        boolean alreadyParent;
        LoadBalancer.SubchannelStateListener healthConsumerListener = args2.getOption(LoadBalancer.HEALTH_CONSUMER_LISTENER_ARG_KEY);
        LoadBalancer.Subchannel delegateSubchannel = super.createSubchannel(args2);
        boolean bl = alreadyParent = healthConsumerListener != null && delegateSubchannel.getAttributes().get(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY) == null;
        if (!alreadyParent) {
            return delegateSubchannel;
        }
        return new HealthProducerSubchannel(delegateSubchannel, healthConsumerListener);
    }

    @Override
    protected LoadBalancer.Helper delegate() {
        return this.delegate;
    }

    @VisibleForTesting
    static final class HealthProducerSubchannel
    extends ForwardingSubchannel {
        private final LoadBalancer.Subchannel delegate;
        private final LoadBalancer.SubchannelStateListener healthListener;

        HealthProducerSubchannel(LoadBalancer.Subchannel delegate, LoadBalancer.SubchannelStateListener healthListener) {
            this.delegate = Preconditions.checkNotNull(delegate, "delegate");
            this.healthListener = Preconditions.checkNotNull(healthListener, "healthListener");
        }

        @Override
        public LoadBalancer.Subchannel delegate() {
            return this.delegate;
        }

        @Override
        public void start(final LoadBalancer.SubchannelStateListener listener) {
            this.delegate.start(new LoadBalancer.SubchannelStateListener(){

                @Override
                public void onSubchannelState(ConnectivityStateInfo newState) {
                    listener.onSubchannelState(newState);
                    healthListener.onSubchannelState(newState);
                }
            });
        }

        @Override
        public Attributes getAttributes() {
            return super.getAttributes().toBuilder().set(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY, Boolean.TRUE).build();
        }
    }
}

