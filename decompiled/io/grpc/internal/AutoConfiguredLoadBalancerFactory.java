/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ChannelLogger;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.ServiceConfigUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public final class AutoConfiguredLoadBalancerFactory {
    private final LoadBalancerRegistry registry;
    private final String defaultPolicy;

    public AutoConfiguredLoadBalancerFactory(String defaultPolicy) {
        this(LoadBalancerRegistry.getDefaultRegistry(), defaultPolicy);
    }

    @VisibleForTesting
    AutoConfiguredLoadBalancerFactory(LoadBalancerRegistry registry, String defaultPolicy) {
        this.registry = Preconditions.checkNotNull(registry, "registry");
        this.defaultPolicy = Preconditions.checkNotNull(defaultPolicy, "defaultPolicy");
    }

    public AutoConfiguredLoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
        return new AutoConfiguredLoadBalancer(helper);
    }

    private LoadBalancerProvider getProviderOrThrow(String policy, String choiceReason) throws PolicyException {
        LoadBalancerProvider provider = this.registry.getProvider(policy);
        if (provider == null) {
            throw new PolicyException("Trying to load '" + policy + "' because " + choiceReason + ", but it's unavailable");
        }
        return provider;
    }

    @Nullable
    NameResolver.ConfigOrError parseLoadBalancerPolicy(Map<String, ?> serviceConfig) {
        try {
            List<ServiceConfigUtil.LbConfig> loadBalancerConfigs = null;
            if (serviceConfig != null) {
                List<Map<String, ?>> rawLbConfigs = ServiceConfigUtil.getLoadBalancingConfigsFromServiceConfig(serviceConfig);
                loadBalancerConfigs = ServiceConfigUtil.unwrapLoadBalancingConfigList(rawLbConfigs);
            }
            if (loadBalancerConfigs != null && !loadBalancerConfigs.isEmpty()) {
                return ServiceConfigUtil.selectLbPolicyFromList(loadBalancerConfigs, this.registry);
            }
            return null;
        }
        catch (RuntimeException e) {
            return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("can't parse load balancer configuration").withCause(e));
        }
    }

    private static final class FailingPicker
    extends LoadBalancer.SubchannelPicker {
        private final Status failure;

        FailingPicker(Status failure) {
            this.failure = failure;
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return LoadBalancer.PickResult.withError(this.failure);
        }
    }

    private static final class EmptyPicker
    extends LoadBalancer.SubchannelPicker {
        private EmptyPicker() {
        }

        @Override
        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public String toString() {
            return MoreObjects.toStringHelper(EmptyPicker.class).toString();
        }
    }

    @VisibleForTesting
    static final class PolicyException
    extends Exception {
        private static final long serialVersionUID = 1L;

        private PolicyException(String msg) {
            super(msg);
        }
    }

    @VisibleForTesting
    public final class AutoConfiguredLoadBalancer {
        private final LoadBalancer.Helper helper;
        private LoadBalancer delegate;
        private LoadBalancerProvider delegateProvider;

        AutoConfiguredLoadBalancer(LoadBalancer.Helper helper) {
            this.helper = helper;
            this.delegateProvider = AutoConfiguredLoadBalancerFactory.this.registry.getProvider(AutoConfiguredLoadBalancerFactory.this.defaultPolicy);
            if (this.delegateProvider == null) {
                throw new IllegalStateException("Could not find policy '" + AutoConfiguredLoadBalancerFactory.this.defaultPolicy + "'. Make sure its implementation is either registered to LoadBalancerRegistry or included in META-INF/services/io.grpc.LoadBalancerProvider from your jar files.");
            }
            this.delegate = this.delegateProvider.newLoadBalancer(helper);
        }

        Status tryAcceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
            Object lbConfig;
            ServiceConfigUtil.PolicySelection policySelection = (ServiceConfigUtil.PolicySelection)resolvedAddresses.getLoadBalancingPolicyConfig();
            if (policySelection == null) {
                LoadBalancerProvider defaultProvider;
                try {
                    defaultProvider = AutoConfiguredLoadBalancerFactory.this.getProviderOrThrow(AutoConfiguredLoadBalancerFactory.this.defaultPolicy, "using default policy");
                }
                catch (PolicyException e) {
                    Status s2 = Status.INTERNAL.withDescription(e.getMessage());
                    this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new FailingPicker(s2));
                    this.delegate.shutdown();
                    this.delegateProvider = null;
                    this.delegate = new NoopLoadBalancer();
                    return Status.OK;
                }
                policySelection = new ServiceConfigUtil.PolicySelection(defaultProvider, null);
            }
            if (this.delegateProvider == null || !policySelection.provider.getPolicyName().equals(this.delegateProvider.getPolicyName())) {
                this.helper.updateBalancingState(ConnectivityState.CONNECTING, new EmptyPicker());
                this.delegate.shutdown();
                this.delegateProvider = policySelection.provider;
                LoadBalancer old = this.delegate;
                this.delegate = this.delegateProvider.newLoadBalancer(this.helper);
                this.helper.getChannelLogger().log(ChannelLogger.ChannelLogLevel.INFO, "Load balancer changed from {0} to {1}", old.getClass().getSimpleName(), this.delegate.getClass().getSimpleName());
            }
            if ((lbConfig = policySelection.config) != null) {
                this.helper.getChannelLogger().log(ChannelLogger.ChannelLogLevel.DEBUG, "Load-balancing config: {0}", policySelection.config);
            }
            return this.getDelegate().acceptResolvedAddresses(LoadBalancer.ResolvedAddresses.newBuilder().setAddresses(resolvedAddresses.getAddresses()).setAttributes(resolvedAddresses.getAttributes()).setLoadBalancingPolicyConfig(lbConfig).build());
        }

        void handleNameResolutionError(Status error) {
            this.getDelegate().handleNameResolutionError(error);
        }

        @Deprecated
        void handleSubchannelState(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
            this.getDelegate().handleSubchannelState(subchannel, stateInfo);
        }

        void requestConnection() {
            this.getDelegate().requestConnection();
        }

        void shutdown() {
            this.delegate.shutdown();
            this.delegate = null;
        }

        @VisibleForTesting
        public LoadBalancer getDelegate() {
            return this.delegate;
        }

        @VisibleForTesting
        void setDelegate(LoadBalancer lb) {
            this.delegate = lb;
        }

        @VisibleForTesting
        LoadBalancerProvider getDelegateProvider() {
            return this.delegateProvider;
        }
    }

    private static final class NoopLoadBalancer
    extends LoadBalancer {
        private NoopLoadBalancer() {
        }

        @Override
        @Deprecated
        public void handleResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        }

        @Override
        public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
            return Status.OK;
        }

        @Override
        public void handleNameResolutionError(Status error) {
        }

        @Override
        public void shutdown() {
        }
    }
}

