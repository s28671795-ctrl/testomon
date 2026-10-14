/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.JsonUtil;
import io.grpc.internal.ServiceConfigUtil;
import io.grpc.internal.TimeProvider;
import io.grpc.util.OutlierDetectionLoadBalancer;
import java.util.List;
import java.util.Map;

@Internal
public final class OutlierDetectionLoadBalancerProvider
extends LoadBalancerProvider {
    @Override
    public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
        return new OutlierDetectionLoadBalancer(helper, TimeProvider.SYSTEM_TIME_PROVIDER);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getPolicyName() {
        return "outlier_detection_experimental";
    }

    @Override
    public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> rawConfig) {
        try {
            return this.parseLoadBalancingPolicyConfigInternal(rawConfig);
        }
        catch (RuntimeException e) {
            return NameResolver.ConfigOrError.fromError(Status.UNAVAILABLE.withCause(e).withDescription("Failed parsing configuration for " + this.getPolicyName()));
        }
    }

    private NameResolver.ConfigOrError parseLoadBalancingPolicyConfigInternal(Map<String, ?> rawConfig) {
        List<ServiceConfigUtil.LbConfig> childConfigCandidates;
        Map<String, ?> rawFailurePercentageEjection;
        Map<String, ?> rawSuccessRateEjection;
        Long intervalNanos = JsonUtil.getStringAsDuration(rawConfig, "interval");
        Long baseEjectionTimeNanos = JsonUtil.getStringAsDuration(rawConfig, "baseEjectionTime");
        Long maxEjectionTimeNanos = JsonUtil.getStringAsDuration(rawConfig, "maxEjectionTime");
        Integer maxEjectionPercentage = JsonUtil.getNumberAsInteger(rawConfig, "maxEjectionPercentage");
        OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.Builder configBuilder = new OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.Builder();
        if (intervalNanos != null) {
            configBuilder.setIntervalNanos(intervalNanos);
        }
        if (baseEjectionTimeNanos != null) {
            configBuilder.setBaseEjectionTimeNanos(baseEjectionTimeNanos);
        }
        if (maxEjectionTimeNanos != null) {
            configBuilder.setMaxEjectionTimeNanos(maxEjectionTimeNanos);
        }
        if (maxEjectionPercentage != null) {
            configBuilder.setMaxEjectionPercent(maxEjectionPercentage);
        }
        if ((rawSuccessRateEjection = JsonUtil.getObject(rawConfig, "successRateEjection")) != null) {
            OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.SuccessRateEjection.Builder successRateEjectionBuilder = new OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.SuccessRateEjection.Builder();
            Integer stdevFactor = JsonUtil.getNumberAsInteger(rawSuccessRateEjection, "stdevFactor");
            Integer enforcementPercentage = JsonUtil.getNumberAsInteger(rawSuccessRateEjection, "enforcementPercentage");
            Integer minimumHosts = JsonUtil.getNumberAsInteger(rawSuccessRateEjection, "minimumHosts");
            Integer requestVolume = JsonUtil.getNumberAsInteger(rawSuccessRateEjection, "requestVolume");
            if (stdevFactor != null) {
                successRateEjectionBuilder.setStdevFactor(stdevFactor);
            }
            if (enforcementPercentage != null) {
                successRateEjectionBuilder.setEnforcementPercentage(enforcementPercentage);
            }
            if (minimumHosts != null) {
                successRateEjectionBuilder.setMinimumHosts(minimumHosts);
            }
            if (requestVolume != null) {
                successRateEjectionBuilder.setRequestVolume(requestVolume);
            }
            configBuilder.setSuccessRateEjection(successRateEjectionBuilder.build());
        }
        if ((rawFailurePercentageEjection = JsonUtil.getObject(rawConfig, "failurePercentageEjection")) != null) {
            OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.FailurePercentageEjection.Builder failurePercentageEjectionBuilder = new OutlierDetectionLoadBalancer.OutlierDetectionLoadBalancerConfig.FailurePercentageEjection.Builder();
            Integer threshold = JsonUtil.getNumberAsInteger(rawFailurePercentageEjection, "threshold");
            Integer enforcementPercentage = JsonUtil.getNumberAsInteger(rawFailurePercentageEjection, "enforcementPercentage");
            Integer minimumHosts = JsonUtil.getNumberAsInteger(rawFailurePercentageEjection, "minimumHosts");
            Integer requestVolume = JsonUtil.getNumberAsInteger(rawFailurePercentageEjection, "requestVolume");
            if (threshold != null) {
                failurePercentageEjectionBuilder.setThreshold(threshold);
            }
            if (enforcementPercentage != null) {
                failurePercentageEjectionBuilder.setEnforcementPercentage(enforcementPercentage);
            }
            if (minimumHosts != null) {
                failurePercentageEjectionBuilder.setMinimumHosts(minimumHosts);
            }
            if (requestVolume != null) {
                failurePercentageEjectionBuilder.setRequestVolume(requestVolume);
            }
            configBuilder.setFailurePercentageEjection(failurePercentageEjectionBuilder.build());
        }
        if ((childConfigCandidates = ServiceConfigUtil.unwrapLoadBalancingConfigList(JsonUtil.getListOfObjects(rawConfig, "childPolicy"))) == null || childConfigCandidates.isEmpty()) {
            return NameResolver.ConfigOrError.fromError(Status.INTERNAL.withDescription("No child policy in outlier_detection_experimental LB policy: " + rawConfig));
        }
        NameResolver.ConfigOrError selectedConfig = ServiceConfigUtil.selectLbPolicyFromList(childConfigCandidates, LoadBalancerRegistry.getDefaultRegistry());
        if (selectedConfig.getError() != null) {
            return selectedConfig;
        }
        configBuilder.setChildPolicy((ServiceConfigUtil.PolicySelection)selectedConfig.getConfig());
        return NameResolver.ConfigOrError.fromConfig(configBuilder.build());
    }
}

