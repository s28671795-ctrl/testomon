/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.base.MoreObjects;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ConnectivityState;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MetricRecorder;
import io.grpc.NameResolver;
import io.grpc.NameResolverRegistry;
import io.grpc.SynchronizationContext;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
public abstract class ForwardingLoadBalancerHelper
extends LoadBalancer.Helper {
    protected abstract LoadBalancer.Helper delegate();

    @Override
    public LoadBalancer.Subchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args2) {
        return this.delegate().createSubchannel(args2);
    }

    @Override
    public ManagedChannel createOobChannel(EquivalentAddressGroup eag, String authority) {
        return this.delegate().createOobChannel(eag, authority);
    }

    @Override
    public ManagedChannel createOobChannel(List<EquivalentAddressGroup> eag, String authority) {
        return this.delegate().createOobChannel(eag, authority);
    }

    @Override
    public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
        this.delegate().updateOobChannelAddresses(channel, eag);
    }

    @Override
    public void updateOobChannelAddresses(ManagedChannel channel, List<EquivalentAddressGroup> eag) {
        this.delegate().updateOobChannelAddresses(channel, eag);
    }

    @Override
    @Deprecated
    public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target) {
        return this.delegate().createResolvingOobChannelBuilder(target);
    }

    @Override
    public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target, ChannelCredentials creds) {
        return this.delegate().createResolvingOobChannelBuilder(target, creds);
    }

    @Override
    public ManagedChannel createResolvingOobChannel(String target) {
        return this.delegate().createResolvingOobChannel(target);
    }

    @Override
    public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
        this.delegate().updateBalancingState(newState, newPicker);
    }

    @Override
    public void refreshNameResolution() {
        this.delegate().refreshNameResolution();
    }

    @Override
    @Deprecated
    public void ignoreRefreshNameResolutionCheck() {
        this.delegate().ignoreRefreshNameResolutionCheck();
    }

    @Override
    public String getAuthority() {
        return this.delegate().getAuthority();
    }

    @Override
    public String getChannelTarget() {
        return this.delegate().getChannelTarget();
    }

    @Override
    public ChannelCredentials getChannelCredentials() {
        return this.delegate().getChannelCredentials();
    }

    @Override
    public ChannelCredentials getUnsafeChannelCredentials() {
        return this.delegate().getUnsafeChannelCredentials();
    }

    @Override
    public SynchronizationContext getSynchronizationContext() {
        return this.delegate().getSynchronizationContext();
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.delegate().getScheduledExecutorService();
    }

    @Override
    public ChannelLogger getChannelLogger() {
        return this.delegate().getChannelLogger();
    }

    @Override
    public NameResolver.Args getNameResolverArgs() {
        return this.delegate().getNameResolverArgs();
    }

    @Override
    public NameResolverRegistry getNameResolverRegistry() {
        return this.delegate().getNameResolverRegistry();
    }

    @Override
    public MetricRecorder getMetricRecorder() {
        return this.delegate().getMetricRecorder();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

