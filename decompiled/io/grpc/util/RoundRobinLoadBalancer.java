/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.util.MultiChildLoadBalancer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Internal
public class RoundRobinLoadBalancer
extends MultiChildLoadBalancer {
    private final AtomicInteger sequence = new AtomicInteger(new Random().nextInt());
    protected LoadBalancer.SubchannelPicker currentPicker = new EmptyPicker();

    public RoundRobinLoadBalancer(LoadBalancer.Helper helper) {
        super(helper);
    }

    @Override
    protected void updateOverallBalancingState() {
        List<MultiChildLoadBalancer.ChildLbState> activeList = this.getReadyChildren();
        if (activeList.isEmpty()) {
            boolean isConnecting = false;
            for (MultiChildLoadBalancer.ChildLbState childLbState : this.getChildLbStates()) {
                ConnectivityState state = childLbState.getCurrentState();
                if (state != ConnectivityState.CONNECTING && state != ConnectivityState.IDLE) continue;
                isConnecting = true;
                break;
            }
            if (isConnecting) {
                this.updateBalancingState(ConnectivityState.CONNECTING, new EmptyPicker());
            } else {
                this.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, this.createReadyPicker(this.getChildLbStates()));
            }
        } else {
            this.updateBalancingState(ConnectivityState.READY, this.createReadyPicker(activeList));
        }
    }

    private void updateBalancingState(ConnectivityState state, LoadBalancer.SubchannelPicker picker) {
        if (state != this.currentConnectivityState || !picker.equals(this.currentPicker)) {
            this.getHelper().updateBalancingState(state, picker);
            this.currentConnectivityState = state;
            this.currentPicker = picker;
        }
    }

    protected LoadBalancer.SubchannelPicker createReadyPicker(Collection<MultiChildLoadBalancer.ChildLbState> children) {
        ArrayList<LoadBalancer.SubchannelPicker> pickerList = new ArrayList<LoadBalancer.SubchannelPicker>();
        for (MultiChildLoadBalancer.ChildLbState child : children) {
            LoadBalancer.SubchannelPicker picker = child.getCurrentPicker();
            pickerList.add(picker);
        }
        return new ReadyPicker(pickerList, this.sequence);
    }

    @VisibleForTesting
    static final class EmptyPicker
    extends LoadBalancer.SubchannelPicker {
        EmptyPicker() {
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public int hashCode() {
            return this.getClass().hashCode();
        }

        public boolean equals(Object o) {
            return o instanceof EmptyPicker;
        }
    }

    @VisibleForTesting
    static class ReadyPicker
    extends LoadBalancer.SubchannelPicker {
        private final List<LoadBalancer.SubchannelPicker> subchannelPickers;
        private final AtomicInteger index;
        private final int hashCode;

        public ReadyPicker(List<LoadBalancer.SubchannelPicker> list, AtomicInteger index) {
            Preconditions.checkArgument(!list.isEmpty(), "empty list");
            this.subchannelPickers = list;
            this.index = Preconditions.checkNotNull(index, "index");
            int sum = 0;
            for (LoadBalancer.SubchannelPicker picker : this.subchannelPickers) {
                sum += picker.hashCode();
            }
            this.hashCode = sum;
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return this.subchannelPickers.get(this.nextIndex()).pickSubchannel(args2);
        }

        public String toString() {
            return MoreObjects.toStringHelper(ReadyPicker.class).add("subchannelPickers", this.subchannelPickers).toString();
        }

        private int nextIndex() {
            int i = this.index.getAndIncrement() & Integer.MAX_VALUE;
            return i % this.subchannelPickers.size();
        }

        @VisibleForTesting
        List<LoadBalancer.SubchannelPicker> getSubchannelPickers() {
            return this.subchannelPickers;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ReadyPicker)) {
                return false;
            }
            ReadyPicker other = (ReadyPicker)o;
            if (other == this) {
                return true;
            }
            return this.hashCode == other.hashCode && this.index == other.index && this.subchannelPickers.size() == other.subchannelPickers.size() && new HashSet<LoadBalancer.SubchannelPicker>(this.subchannelPickers).containsAll(other.subchannelPickers);
        }
    }
}

