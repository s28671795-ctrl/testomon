/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.grpc.Attributes;
import io.grpc.ConnectivityState;
import io.grpc.EquivalentAddressGroup;
import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.Status;
import io.grpc.internal.PickFirstLoadBalancerProvider;
import io.grpc.util.ForwardingLoadBalancerHelper;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@Internal
public abstract class MultiChildLoadBalancer
extends LoadBalancer {
    private static final Logger logger = Logger.getLogger(MultiChildLoadBalancer.class.getName());
    private final Map<Object, ChildLbState> childLbStates = new LinkedHashMap<Object, ChildLbState>();
    private final LoadBalancer.Helper helper;
    protected boolean resolvingAddresses;
    protected final LoadBalancerProvider pickFirstLbProvider = new PickFirstLoadBalancerProvider();
    protected ConnectivityState currentConnectivityState;

    protected MultiChildLoadBalancer(LoadBalancer.Helper helper) {
        this.helper = Preconditions.checkNotNull(helper, "helper");
        logger.log(Level.FINE, "Created");
    }

    protected abstract void updateOverallBalancingState();

    protected Map<Object, ChildLbState> createChildLbMap(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        HashMap<Object, ChildLbState> childLbMap = new HashMap<Object, ChildLbState>();
        List<EquivalentAddressGroup> addresses = resolvedAddresses.getAddresses();
        for (EquivalentAddressGroup eag : addresses) {
            Endpoint endpoint = new Endpoint(eag);
            ChildLbState existingChildLbState = this.childLbStates.get(endpoint);
            if (existingChildLbState != null) {
                childLbMap.put(endpoint, existingChildLbState);
                continue;
            }
            childLbMap.put(endpoint, this.createChildLbState(endpoint, null, this.getInitialPicker(), resolvedAddresses));
        }
        return childLbMap;
    }

    protected ChildLbState createChildLbState(Object key, Object policyConfig, LoadBalancer.SubchannelPicker initialPicker, LoadBalancer.ResolvedAddresses resolvedAddresses) {
        return new ChildLbState(key, this.pickFirstLbProvider, policyConfig, initialPicker);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        try {
            this.resolvingAddresses = true;
            AcceptResolvedAddrRetVal acceptRetVal = this.acceptResolvedAddressesInternal(resolvedAddresses);
            if (!acceptRetVal.status.isOk()) {
                Status status = acceptRetVal.status;
                return status;
            }
            this.updateOverallBalancingState();
            this.shutdownRemoved(acceptRetVal.removedChildren);
            Status status = acceptRetVal.status;
            return status;
        }
        finally {
            this.resolvingAddresses = false;
        }
    }

    protected LoadBalancer.ResolvedAddresses getChildAddresses(Object key, LoadBalancer.ResolvedAddresses resolvedAddresses, Object childConfig) {
        Endpoint endpointKey;
        if (key instanceof EquivalentAddressGroup) {
            endpointKey = new Endpoint((EquivalentAddressGroup)key);
        } else {
            Preconditions.checkArgument(key instanceof Endpoint, "key is wrong type");
            endpointKey = (Endpoint)key;
        }
        EquivalentAddressGroup eagToUse = null;
        for (EquivalentAddressGroup currEag : resolvedAddresses.getAddresses()) {
            if (!endpointKey.equals(new Endpoint(currEag))) continue;
            eagToUse = currEag;
            break;
        }
        Preconditions.checkNotNull(eagToUse, key + " no longer present in load balancer children");
        return resolvedAddresses.toBuilder().setAddresses(Collections.singletonList(eagToUse)).setAttributes(Attributes.newBuilder().set(IS_PETIOLE_POLICY, true).build()).setLoadBalancingPolicyConfig(childConfig).build();
    }

    @Override
    public void handleNameResolutionError(Status error) {
        if (this.currentConnectivityState != ConnectivityState.READY) {
            this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, this.getErrorPicker(error));
        }
    }

    protected void handleNameResolutionError(ChildLbState child, Status error) {
        child.lb.handleNameResolutionError(error);
    }

    protected LoadBalancer.SubchannelPicker getInitialPicker() {
        return new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withNoResult());
    }

    protected LoadBalancer.SubchannelPicker getErrorPicker(Status error) {
        return new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withError(error));
    }

    @Override
    public void shutdown() {
        logger.log(Level.FINE, "Shutdown");
        for (ChildLbState state : this.childLbStates.values()) {
            state.shutdown();
        }
        this.childLbStates.clear();
    }

    protected final AcceptResolvedAddrRetVal acceptResolvedAddressesInternal(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        logger.log(Level.FINE, "Received resolution result: {0}", resolvedAddresses);
        Map<Object, ChildLbState> newChildren = this.createChildLbMap(resolvedAddresses);
        if (newChildren.isEmpty()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. " + resolvedAddresses);
            this.handleNameResolutionError(unavailableStatus);
            return new AcceptResolvedAddrRetVal(unavailableStatus, null);
        }
        this.addMissingChildren(newChildren);
        this.updateChildrenWithResolvedAddresses(resolvedAddresses, newChildren);
        return new AcceptResolvedAddrRetVal(Status.OK, this.getRemovedChildren(newChildren.keySet()));
    }

    protected final void addMissingChildren(Map<Object, ChildLbState> newChildren) {
        for (Map.Entry<Object, ChildLbState> entry : newChildren.entrySet()) {
            Object key = entry.getKey();
            if (this.childLbStates.containsKey(key)) continue;
            this.childLbStates.put(key, entry.getValue());
        }
    }

    protected final void updateChildrenWithResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses, Map<Object, ChildLbState> newChildren) {
        for (Map.Entry<Object, ChildLbState> entry : newChildren.entrySet()) {
            Object childConfig = entry.getValue().getConfig();
            ChildLbState childLbState = this.childLbStates.get(entry.getKey());
            LoadBalancer.ResolvedAddresses childAddresses = this.getChildAddresses(entry.getKey(), resolvedAddresses, childConfig);
            childLbState.setResolvedAddresses(childAddresses);
            childLbState.lb.handleResolvedAddresses(childAddresses);
        }
    }

    protected final List<ChildLbState> getRemovedChildren(Set<Object> newChildKeys) {
        ArrayList<ChildLbState> removedChildren = new ArrayList<ChildLbState>();
        for (Object e : ImmutableList.copyOf(this.childLbStates.keySet())) {
            if (newChildKeys.contains(e)) continue;
            ChildLbState childLbState = this.childLbStates.remove(e);
            removedChildren.add(childLbState);
        }
        return removedChildren;
    }

    protected final void shutdownRemoved(List<ChildLbState> removedChildren) {
        for (ChildLbState childLbState : removedChildren) {
            childLbState.shutdown();
        }
    }

    @Nullable
    protected static ConnectivityState aggregateState(@Nullable ConnectivityState overallState, ConnectivityState childState) {
        if (overallState == null) {
            return childState;
        }
        if (overallState == ConnectivityState.READY || childState == ConnectivityState.READY) {
            return ConnectivityState.READY;
        }
        if (overallState == ConnectivityState.CONNECTING || childState == ConnectivityState.CONNECTING) {
            return ConnectivityState.CONNECTING;
        }
        if (overallState == ConnectivityState.IDLE || childState == ConnectivityState.IDLE) {
            return ConnectivityState.IDLE;
        }
        return overallState;
    }

    protected final LoadBalancer.Helper getHelper() {
        return this.helper;
    }

    @VisibleForTesting
    public final ImmutableMap<Object, ChildLbState> getImmutableChildMap() {
        return ImmutableMap.copyOf(this.childLbStates);
    }

    @VisibleForTesting
    public final Collection<ChildLbState> getChildLbStates() {
        return this.childLbStates.values();
    }

    @VisibleForTesting
    public final ChildLbState getChildLbState(Object key) {
        if (key == null) {
            return null;
        }
        if (key instanceof EquivalentAddressGroup) {
            key = new Endpoint((EquivalentAddressGroup)key);
        }
        return this.childLbStates.get(key);
    }

    @VisibleForTesting
    public final ChildLbState getChildLbStateEag(EquivalentAddressGroup eag) {
        return this.getChildLbState(new Endpoint(eag));
    }

    protected final List<ChildLbState> getReadyChildren() {
        ArrayList<ChildLbState> activeChildren = new ArrayList<ChildLbState>();
        for (ChildLbState child : this.getChildLbStates()) {
            if (child.getCurrentState() != ConnectivityState.READY) continue;
            activeChildren.add(child);
        }
        return activeChildren;
    }

    protected static class AcceptResolvedAddrRetVal {
        public final Status status;
        public final List<ChildLbState> removedChildren;

        public AcceptResolvedAddrRetVal(Status status, List<ChildLbState> removedChildren) {
            this.status = status;
            this.removedChildren = removedChildren;
        }
    }

    protected static class Endpoint {
        final String[] addrs;
        final int hashCode;

        public Endpoint(EquivalentAddressGroup eag) {
            Preconditions.checkNotNull(eag, "eag");
            this.addrs = new String[eag.getAddresses().size()];
            int i = 0;
            for (SocketAddress address : eag.getAddresses()) {
                this.addrs[i++] = address.toString();
            }
            Arrays.sort(this.addrs);
            this.hashCode = Arrays.hashCode(this.addrs);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            if (!(other instanceof Endpoint)) {
                return false;
            }
            Endpoint o = (Endpoint)other;
            if (o.hashCode != this.hashCode || o.addrs.length != this.addrs.length) {
                return false;
            }
            return Arrays.equals(o.addrs, this.addrs);
        }

        public String toString() {
            return Arrays.toString(this.addrs);
        }
    }

    public class ChildLbState {
        private final Object key;
        private LoadBalancer.ResolvedAddresses resolvedAddresses;
        private final Object config;
        private final LoadBalancer lb;
        private ConnectivityState currentState;
        private LoadBalancer.SubchannelPicker currentPicker;

        public ChildLbState(Object key, LoadBalancer.Factory policyFactory, Object childConfig, LoadBalancer.SubchannelPicker initialPicker) {
            this.key = key;
            this.currentPicker = initialPicker;
            this.config = childConfig;
            this.lb = policyFactory.newLoadBalancer(this.createChildHelper());
            this.currentState = ConnectivityState.CONNECTING;
        }

        protected ChildLbStateHelper createChildHelper() {
            return new ChildLbStateHelper();
        }

        protected void shutdown() {
            this.lb.shutdown();
            this.currentState = ConnectivityState.SHUTDOWN;
            logger.log(Level.FINE, "Child balancer {0} deleted", this.key);
        }

        public String toString() {
            return "Address = " + this.key + ", state = " + (Object)((Object)this.currentState) + ", picker type: " + this.currentPicker.getClass() + ", lb: " + this.lb;
        }

        public final Object getKey() {
            return this.key;
        }

        @VisibleForTesting
        public final LoadBalancer getLb() {
            return this.lb;
        }

        @VisibleForTesting
        public final LoadBalancer.SubchannelPicker getCurrentPicker() {
            return this.currentPicker;
        }

        protected final LoadBalancer.Subchannel getSubchannels(LoadBalancer.PickSubchannelArgs args2) {
            if (this.getCurrentPicker() == null) {
                return null;
            }
            return this.getCurrentPicker().pickSubchannel(args2).getSubchannel();
        }

        public final ConnectivityState getCurrentState() {
            return this.currentState;
        }

        protected final void setCurrentState(ConnectivityState newState) {
            this.currentState = newState;
        }

        protected final void setCurrentPicker(LoadBalancer.SubchannelPicker newPicker) {
            this.currentPicker = newPicker;
        }

        public final EquivalentAddressGroup getEag() {
            if (this.resolvedAddresses == null || this.resolvedAddresses.getAddresses().isEmpty()) {
                return null;
            }
            return this.resolvedAddresses.getAddresses().get(0);
        }

        protected final void setResolvedAddresses(LoadBalancer.ResolvedAddresses newAddresses) {
            Preconditions.checkNotNull(newAddresses, "Missing address list for child");
            this.resolvedAddresses = newAddresses;
        }

        private Object getConfig() {
            return this.config;
        }

        @VisibleForTesting
        public final LoadBalancer.ResolvedAddresses getResolvedAddresses() {
            return this.resolvedAddresses;
        }

        protected class ChildLbStateHelper
        extends ForwardingLoadBalancerHelper {
            protected ChildLbStateHelper() {
            }

            @Override
            public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
                if (!MultiChildLoadBalancer.this.childLbStates.containsKey(ChildLbState.this.key)) {
                    return;
                }
                ChildLbState.this.currentState = newState;
                ChildLbState.this.currentPicker = newPicker;
                if (!MultiChildLoadBalancer.this.resolvingAddresses) {
                    if (newState == ConnectivityState.IDLE) {
                        ChildLbState.this.lb.requestConnection();
                    }
                    MultiChildLoadBalancer.this.updateOverallBalancingState();
                }
            }

            @Override
            protected LoadBalancer.Helper delegate() {
                return MultiChildLoadBalancer.this.helper;
            }
        }
    }
}

