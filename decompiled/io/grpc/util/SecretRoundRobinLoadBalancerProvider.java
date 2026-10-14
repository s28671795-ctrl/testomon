/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.NameResolver;
import io.grpc.util.RoundRobinLoadBalancer;
import java.util.Map;

final class SecretRoundRobinLoadBalancerProvider {
    private SecretRoundRobinLoadBalancerProvider() {
    }

    public static final class Provider
    extends LoadBalancerProvider {
        private static final String NO_CONFIG = "no service config";

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
            return "round_robin";
        }

        @Override
        public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
            return new RoundRobinLoadBalancer(helper);
        }

        @Override
        public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> rawLoadBalancingPolicyConfig) {
            return NameResolver.ConfigOrError.fromConfig(NO_CONFIG);
        }
    }
}

