/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancer;
import io.grpc.NameResolver;
import java.util.Map;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
public abstract class LoadBalancerProvider
extends LoadBalancer.Factory {
    private static final NameResolver.ConfigOrError UNKNOWN_CONFIG = NameResolver.ConfigOrError.fromConfig(new UnknownConfig());

    public abstract boolean isAvailable();

    public abstract int getPriority();

    public abstract String getPolicyName();

    public NameResolver.ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> rawLoadBalancingPolicyConfig) {
        return UNKNOWN_CONFIG;
    }

    public final String toString() {
        return MoreObjects.toStringHelper(this).add("policy", this.getPolicyName()).add("priority", this.getPriority()).add("available", this.isAvailable()).toString();
    }

    public final boolean equals(Object other) {
        return this == other;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    private static final class UnknownConfig {
        UnknownConfig() {
        }

        public String toString() {
            return "service config is unused";
        }
    }
}

