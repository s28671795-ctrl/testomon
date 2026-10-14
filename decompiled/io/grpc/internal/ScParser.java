/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.AutoConfiguredLoadBalancerFactory;
import io.grpc.internal.ManagedChannelServiceConfig;
import java.util.Map;

@VisibleForTesting
public final class ScParser
extends NameResolver.ServiceConfigParser {
    private final boolean retryEnabled;
    private final int maxRetryAttemptsLimit;
    private final int maxHedgedAttemptsLimit;
    private final AutoConfiguredLoadBalancerFactory autoLoadBalancerFactory;

    public ScParser(boolean retryEnabled, int maxRetryAttemptsLimit, int maxHedgedAttemptsLimit, AutoConfiguredLoadBalancerFactory autoLoadBalancerFactory) {
        this.retryEnabled = retryEnabled;
        this.maxRetryAttemptsLimit = maxRetryAttemptsLimit;
        this.maxHedgedAttemptsLimit = maxHedgedAttemptsLimit;
        this.autoLoadBalancerFactory = Preconditions.checkNotNull(autoLoadBalancerFactory, "autoLoadBalancerFactory");
    }

    @Override
    public NameResolver.ConfigOrError parseServiceConfig(Map<String, ?> rawServiceConfig) {
        try {
            Object loadBalancingPolicySelection;
            NameResolver.ConfigOrError choiceFromLoadBalancer = this.autoLoadBalancerFactory.parseLoadBalancerPolicy(rawServiceConfig);
            if (choiceFromLoadBalancer == null) {
                loadBalancingPolicySelection = null;
            } else {
                if (choiceFromLoadBalancer.getError() != null) {
                    return NameResolver.ConfigOrError.fromError(choiceFromLoadBalancer.getError());
                }
                loadBalancingPolicySelection = choiceFromLoadBalancer.getConfig();
            }
            return NameResolver.ConfigOrError.fromConfig(ManagedChannelServiceConfig.fromServiceConfig(rawServiceConfig, this.retryEnabled, this.maxRetryAttemptsLimit, this.maxHedgedAttemptsLimit, loadBalancingPolicySelection));
        }
        catch (RuntimeException e) {
            return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to parse service config").withCause(e));
        }
    }
}

