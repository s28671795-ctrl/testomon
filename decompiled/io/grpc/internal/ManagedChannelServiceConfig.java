/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.grpc.CallOptions;
import io.grpc.InternalConfigSelector;
import io.grpc.LoadBalancer;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.HedgingPolicy;
import io.grpc.internal.RetriableStream;
import io.grpc.internal.RetryPolicy;
import io.grpc.internal.ServiceConfigUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

final class ManagedChannelServiceConfig {
    @Nullable
    private final MethodInfo defaultMethodConfig;
    private final Map<String, MethodInfo> serviceMethodMap;
    private final Map<String, MethodInfo> serviceMap;
    @Nullable
    private final RetriableStream.Throttle retryThrottling;
    @Nullable
    private final Object loadBalancingConfig;
    @Nullable
    private final Map<String, ?> healthCheckingConfig;

    ManagedChannelServiceConfig(@Nullable MethodInfo defaultMethodConfig, Map<String, MethodInfo> serviceMethodMap, Map<String, MethodInfo> serviceMap, @Nullable RetriableStream.Throttle retryThrottling, @Nullable Object loadBalancingConfig, @Nullable Map<String, ?> healthCheckingConfig) {
        this.defaultMethodConfig = defaultMethodConfig;
        this.serviceMethodMap = Collections.unmodifiableMap(new HashMap<String, MethodInfo>(serviceMethodMap));
        this.serviceMap = Collections.unmodifiableMap(new HashMap<String, MethodInfo>(serviceMap));
        this.retryThrottling = retryThrottling;
        this.loadBalancingConfig = loadBalancingConfig;
        this.healthCheckingConfig = healthCheckingConfig != null ? Collections.unmodifiableMap(new HashMap(healthCheckingConfig)) : null;
    }

    static ManagedChannelServiceConfig empty() {
        return new ManagedChannelServiceConfig(null, new HashMap<String, MethodInfo>(), new HashMap<String, MethodInfo>(), null, null, null);
    }

    static ManagedChannelServiceConfig fromServiceConfig(Map<String, ?> serviceConfig, boolean retryEnabled, int maxRetryAttemptsLimit, int maxHedgedAttemptsLimit, @Nullable Object loadBalancingConfig) {
        RetriableStream.Throttle retryThrottling = null;
        if (retryEnabled) {
            retryThrottling = ServiceConfigUtil.getThrottlePolicy(serviceConfig);
        }
        HashMap<String, MethodInfo> serviceMethodMap = new HashMap<String, MethodInfo>();
        HashMap<String, MethodInfo> serviceMap = new HashMap<String, MethodInfo>();
        Map<String, ?> healthCheckingConfig = ServiceConfigUtil.getHealthCheckedService(serviceConfig);
        List<Map<String, ?>> methodConfigs = ServiceConfigUtil.getMethodConfigFromServiceConfig(serviceConfig);
        if (methodConfigs == null) {
            return new ManagedChannelServiceConfig(null, serviceMethodMap, serviceMap, retryThrottling, loadBalancingConfig, healthCheckingConfig);
        }
        MethodInfo defaultMethodConfig = null;
        for (Map<String, ?> methodConfig : methodConfigs) {
            MethodInfo info = new MethodInfo(methodConfig, retryEnabled, maxRetryAttemptsLimit, maxHedgedAttemptsLimit);
            List<Map<String, ?>> nameList = ServiceConfigUtil.getNameListFromMethodConfig(methodConfig);
            if (nameList == null || nameList.isEmpty()) continue;
            for (Map<String, ?> name : nameList) {
                String serviceName = ServiceConfigUtil.getServiceFromName(name);
                String methodName = ServiceConfigUtil.getMethodFromName(name);
                if (Strings.isNullOrEmpty(serviceName)) {
                    Preconditions.checkArgument(Strings.isNullOrEmpty(methodName), "missing service name for method %s", (Object)methodName);
                    Preconditions.checkArgument(defaultMethodConfig == null, "Duplicate default method config in service config %s", serviceConfig);
                    defaultMethodConfig = info;
                    continue;
                }
                if (Strings.isNullOrEmpty(methodName)) {
                    Preconditions.checkArgument(!serviceMap.containsKey(serviceName), "Duplicate service %s", (Object)serviceName);
                    serviceMap.put(serviceName, info);
                    continue;
                }
                String fullMethodName = MethodDescriptor.generateFullMethodName(serviceName, methodName);
                Preconditions.checkArgument(!serviceMethodMap.containsKey(fullMethodName), "Duplicate method name %s", (Object)fullMethodName);
                serviceMethodMap.put(fullMethodName, info);
            }
        }
        return new ManagedChannelServiceConfig(defaultMethodConfig, serviceMethodMap, serviceMap, retryThrottling, loadBalancingConfig, healthCheckingConfig);
    }

    @Nullable
    Map<String, ?> getHealthCheckingConfig() {
        return this.healthCheckingConfig;
    }

    @Nullable
    InternalConfigSelector getDefaultConfigSelector() {
        if (this.serviceMap.isEmpty() && this.serviceMethodMap.isEmpty() && this.defaultMethodConfig == null) {
            return null;
        }
        return new ServiceConfigConvertedSelector(this);
    }

    @Nullable
    @VisibleForTesting
    Object getLoadBalancingConfig() {
        return this.loadBalancingConfig;
    }

    @Nullable
    RetriableStream.Throttle getRetryThrottling() {
        return this.retryThrottling;
    }

    @Nullable
    MethodInfo getMethodConfig(MethodDescriptor<?, ?> method2) {
        MethodInfo methodInfo = this.serviceMethodMap.get(method2.getFullMethodName());
        if (methodInfo == null) {
            String serviceName = method2.getServiceName();
            methodInfo = this.serviceMap.get(serviceName);
        }
        if (methodInfo == null) {
            methodInfo = this.defaultMethodConfig;
        }
        return methodInfo;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ManagedChannelServiceConfig that = (ManagedChannelServiceConfig)o;
        return Objects.equal(this.defaultMethodConfig, that.defaultMethodConfig) && Objects.equal(this.serviceMethodMap, that.serviceMethodMap) && Objects.equal(this.serviceMap, that.serviceMap) && Objects.equal(this.retryThrottling, that.retryThrottling) && Objects.equal(this.loadBalancingConfig, that.loadBalancingConfig);
    }

    public int hashCode() {
        return Objects.hashCode(this.defaultMethodConfig, this.serviceMethodMap, this.serviceMap, this.retryThrottling, this.loadBalancingConfig);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("defaultMethodConfig", this.defaultMethodConfig).add("serviceMethodMap", this.serviceMethodMap).add("serviceMap", this.serviceMap).add("retryThrottling", this.retryThrottling).add("loadBalancingConfig", this.loadBalancingConfig).toString();
    }

    static final class ServiceConfigConvertedSelector
    extends InternalConfigSelector {
        final ManagedChannelServiceConfig config;

        private ServiceConfigConvertedSelector(ManagedChannelServiceConfig config) {
            this.config = config;
        }

        @Override
        public InternalConfigSelector.Result selectConfig(LoadBalancer.PickSubchannelArgs args2) {
            return InternalConfigSelector.Result.newBuilder().setConfig(this.config).build();
        }
    }

    static final class MethodInfo {
        static final CallOptions.Key<MethodInfo> KEY = CallOptions.Key.create("io.grpc.internal.ManagedChannelServiceConfig.MethodInfo");
        final Long timeoutNanos;
        final Boolean waitForReady;
        final Integer maxInboundMessageSize;
        final Integer maxOutboundMessageSize;
        final RetryPolicy retryPolicy;
        final HedgingPolicy hedgingPolicy;

        MethodInfo(Map<String, ?> methodConfig, boolean retryEnabled, int maxRetryAttemptsLimit, int maxHedgedAttemptsLimit) {
            this.timeoutNanos = ServiceConfigUtil.getTimeoutFromMethodConfig(methodConfig);
            this.waitForReady = ServiceConfigUtil.getWaitForReadyFromMethodConfig(methodConfig);
            this.maxInboundMessageSize = ServiceConfigUtil.getMaxResponseMessageBytesFromMethodConfig(methodConfig);
            if (this.maxInboundMessageSize != null) {
                Preconditions.checkArgument(this.maxInboundMessageSize >= 0, "maxInboundMessageSize %s exceeds bounds", (Object)this.maxInboundMessageSize);
            }
            this.maxOutboundMessageSize = ServiceConfigUtil.getMaxRequestMessageBytesFromMethodConfig(methodConfig);
            if (this.maxOutboundMessageSize != null) {
                Preconditions.checkArgument(this.maxOutboundMessageSize >= 0, "maxOutboundMessageSize %s exceeds bounds", (Object)this.maxOutboundMessageSize);
            }
            Map<String, ?> retryPolicyMap = retryEnabled ? ServiceConfigUtil.getRetryPolicyFromMethodConfig(methodConfig) : null;
            this.retryPolicy = retryPolicyMap == null ? null : MethodInfo.retryPolicy(retryPolicyMap, maxRetryAttemptsLimit);
            Map<String, ?> hedgingPolicyMap = retryEnabled ? ServiceConfigUtil.getHedgingPolicyFromMethodConfig(methodConfig) : null;
            this.hedgingPolicy = hedgingPolicyMap == null ? null : MethodInfo.hedgingPolicy(hedgingPolicyMap, maxHedgedAttemptsLimit);
        }

        public int hashCode() {
            return Objects.hashCode(this.timeoutNanos, this.waitForReady, this.maxInboundMessageSize, this.maxOutboundMessageSize, this.retryPolicy, this.hedgingPolicy);
        }

        public boolean equals(Object other) {
            if (!(other instanceof MethodInfo)) {
                return false;
            }
            MethodInfo that = (MethodInfo)other;
            return Objects.equal(this.timeoutNanos, that.timeoutNanos) && Objects.equal(this.waitForReady, that.waitForReady) && Objects.equal(this.maxInboundMessageSize, that.maxInboundMessageSize) && Objects.equal(this.maxOutboundMessageSize, that.maxOutboundMessageSize) && Objects.equal(this.retryPolicy, that.retryPolicy) && Objects.equal(this.hedgingPolicy, that.hedgingPolicy);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("timeoutNanos", this.timeoutNanos).add("waitForReady", this.waitForReady).add("maxInboundMessageSize", this.maxInboundMessageSize).add("maxOutboundMessageSize", this.maxOutboundMessageSize).add("retryPolicy", this.retryPolicy).add("hedgingPolicy", this.hedgingPolicy).toString();
        }

        private static RetryPolicy retryPolicy(Map<String, ?> retryPolicy, int maxAttemptsLimit) {
            int maxAttempts = Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromRetryPolicy(retryPolicy), "maxAttempts cannot be empty");
            Preconditions.checkArgument(maxAttempts >= 2, "maxAttempts must be greater than 1: %s", maxAttempts);
            maxAttempts = Math.min(maxAttempts, maxAttemptsLimit);
            long initialBackoffNanos = Preconditions.checkNotNull(ServiceConfigUtil.getInitialBackoffNanosFromRetryPolicy(retryPolicy), "initialBackoff cannot be empty");
            Preconditions.checkArgument(initialBackoffNanos > 0L, "initialBackoffNanos must be greater than 0: %s", initialBackoffNanos);
            long maxBackoffNanos = Preconditions.checkNotNull(ServiceConfigUtil.getMaxBackoffNanosFromRetryPolicy(retryPolicy), "maxBackoff cannot be empty");
            Preconditions.checkArgument(maxBackoffNanos > 0L, "maxBackoff must be greater than 0: %s", maxBackoffNanos);
            double backoffMultiplier = Preconditions.checkNotNull(ServiceConfigUtil.getBackoffMultiplierFromRetryPolicy(retryPolicy), "backoffMultiplier cannot be empty");
            Preconditions.checkArgument(backoffMultiplier > 0.0, "backoffMultiplier must be greater than 0: %s", (Object)backoffMultiplier);
            Long perAttemptRecvTimeout = ServiceConfigUtil.getPerAttemptRecvTimeoutNanosFromRetryPolicy(retryPolicy);
            Preconditions.checkArgument(perAttemptRecvTimeout == null || perAttemptRecvTimeout >= 0L, "perAttemptRecvTimeout cannot be negative: %s", (Object)perAttemptRecvTimeout);
            Set<Status.Code> retryableCodes = ServiceConfigUtil.getRetryableStatusCodesFromRetryPolicy(retryPolicy);
            Preconditions.checkArgument(perAttemptRecvTimeout != null || !retryableCodes.isEmpty(), "retryableStatusCodes cannot be empty without perAttemptRecvTimeout");
            return new RetryPolicy(maxAttempts, initialBackoffNanos, maxBackoffNanos, backoffMultiplier, perAttemptRecvTimeout, retryableCodes);
        }

        private static HedgingPolicy hedgingPolicy(Map<String, ?> hedgingPolicy, int maxAttemptsLimit) {
            int maxAttempts = Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromHedgingPolicy(hedgingPolicy), "maxAttempts cannot be empty");
            Preconditions.checkArgument(maxAttempts >= 2, "maxAttempts must be greater than 1: %s", maxAttempts);
            maxAttempts = Math.min(maxAttempts, maxAttemptsLimit);
            long hedgingDelayNanos = Preconditions.checkNotNull(ServiceConfigUtil.getHedgingDelayNanosFromHedgingPolicy(hedgingPolicy), "hedgingDelay cannot be empty");
            Preconditions.checkArgument(hedgingDelayNanos >= 0L, "hedgingDelay must not be negative: %s", hedgingDelayNanos);
            return new HedgingPolicy(maxAttempts, hedgingDelayNanos, ServiceConfigUtil.getNonFatalStatusCodesFromHedgingPolicy(hedgingPolicy));
        }
    }
}

