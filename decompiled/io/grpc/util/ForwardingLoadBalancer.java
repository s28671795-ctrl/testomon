/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.base.MoreObjects;
import io.grpc.ConnectivityStateInfo;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancer;
import io.grpc.Status;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
public abstract class ForwardingLoadBalancer
extends LoadBalancer {
    protected abstract LoadBalancer delegate();

    @Override
    public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        this.delegate().handleResolvedAddresses(resolvedAddresses);
    }

    @Override
    public void handleNameResolutionError(Status error) {
        this.delegate().handleNameResolutionError(error);
    }

    @Override
    @Deprecated
    public void handleSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        this.delegate().handleSubchannelState(subchannel, stateInfo);
    }

    @Override
    public void shutdown() {
        this.delegate().shutdown();
    }

    @Override
    public boolean canHandleEmptyAddressListFromNameResolution() {
        return this.delegate().canHandleEmptyAddressListFromNameResolution();
    }

    @Override
    public void requestConnection() {
        this.delegate().requestConnection();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

