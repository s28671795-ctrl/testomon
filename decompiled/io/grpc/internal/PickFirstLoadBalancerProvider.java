/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.JsonUtil;
import io.grpc.internal.PickFirstLeafLoadBalancer;
import io.grpc.internal.PickFirstLoadBalancer;
import java.util.Map;

public final class PickFirstLoadBalancerProvider
extends LoadBalancerProvider {
    public static final String GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS = "GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS";
    private static final String SHUFFLE_ADDRESS_LIST_KEY = "shuffleAddressList";
    static boolean enableNewPickFirst = GrpcUtil.getFlag("GRPC_EXPERIMENTAL_ENABLE_NEW_PICK_FIRST", false);

    public static boolean isEnabledHappyEyeballs() {
        return GrpcUtil.getFlag(GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS, false);
    }

    @VisibleForTesting
    public static boolean isEnableNewPickFirst() {
        return enableNewPickFirst;
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
        return "pick_first";
    }

    @Override
    public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
        if (enableNewPickFirst) {
            return new PickFirstLeafLoadBalancer(helper);
        }
        return new PickFirstLoadBalancer(helper);
    }

    @Override
    public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> rawLbPolicyConfig) {
        try {
            Object config = PickFirstLoadBalancerProvider.getLbPolicyConfig(rawLbPolicyConfig);
            return NameResolver.ConfigOrError.fromConfig(config);
        }
        catch (RuntimeException e) {
            return NameResolver.ConfigOrError.fromError(Status.UNAVAILABLE.withCause(e).withDescription("Failed parsing configuration for " + this.getPolicyName()));
        }
    }

    private static Object getLbPolicyConfig(Map<String, ?> rawLbPolicyConfig) {
        Boolean shuffleAddressList = JsonUtil.getBoolean(rawLbPolicyConfig, SHUFFLE_ADDRESS_LIST_KEY);
        if (enableNewPickFirst) {
            return new PickFirstLeafLoadBalancer.PickFirstLeafLoadBalancerConfig(shuffleAddressList);
        }
        return new PickFirstLoadBalancer.PickFirstLoadBalancerConfig(shuffleAddressList);
    }

    @VisibleForTesting
    public static boolean isEnabledNewPickFirst() {
        return enableNewPickFirst;
    }
}

