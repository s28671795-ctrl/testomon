/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.Channel;
import io.grpc.ChannelLogger;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancer;
import java.util.List;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
public abstract class ForwardingSubchannel
extends LoadBalancer.Subchannel {
    protected abstract LoadBalancer.Subchannel delegate();

    @Override
    public void start(LoadBalancer.SubchannelStateListener listener) {
        this.delegate().start(listener);
    }

    @Override
    public void shutdown() {
        this.delegate().shutdown();
    }

    @Override
    public void requestConnection() {
        this.delegate().requestConnection();
    }

    @Override
    public List<EquivalentAddressGroup> getAllAddresses() {
        return this.delegate().getAllAddresses();
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate().getAttributes();
    }

    @Override
    public Channel asChannel() {
        return this.delegate().asChannel();
    }

    @Override
    public ChannelLogger getChannelLogger() {
        return this.delegate().getChannelLogger();
    }

    @Override
    public Object getInternalSubchannel() {
        return this.delegate().getInternalSubchannel();
    }

    @Override
    public void updateAddresses(List<EquivalentAddressGroup> addrs) {
        this.delegate().updateAddresses(addrs);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

