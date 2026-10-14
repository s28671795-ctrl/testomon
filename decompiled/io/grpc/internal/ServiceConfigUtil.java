/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.JsonUtil;
import io.grpc.internal.RetriableStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class ServiceConfigUtil {
    private ServiceConfigUtil() {
    }

    @Nullable
    public static Map<String, ?> getHealthCheckedService(@Nullable Map<String, ?> serviceConfig) {
        if (serviceConfig == null) {
            return null;
        }
        return JsonUtil.getObject(serviceConfig, "healthCheckConfig");
    }

    @Nullable
    public static String getHealthCheckedServiceName(@Nullable Map<String, ?> healthCheckedServiceConfig) {
        if (healthCheckedServiceConfig == null) {
            return null;
        }
        return JsonUtil.getString(healthCheckedServiceConfig, "serviceName");
    }

    @Nullable
    static RetriableStream.Throttle getThrottlePolicy(@Nullable Map<String, ?> serviceConfig) {
        if (serviceConfig == null) {
            return null;
        }
        Map<String, ?> throttling = JsonUtil.getObject(serviceConfig, "retryThrottling");
        if (throttling == null) {
            return null;
        }
        float maxTokens = JsonUtil.getNumberAsDouble(throttling, "maxTokens").floatValue();
        float tokenRatio = JsonUtil.getNumberAsDouble(throttling, "tokenRatio").floatValue();
        Preconditions.checkState(maxTokens > 0.0f, "maxToken should be greater than zero");
        Preconditions.checkState(tokenRatio > 0.0f, "tokenRatio should be greater than zero");
        return new RetriableStream.Throttle(maxTokens, tokenRatio);
    }

    @Nullable
    static Integer getMaxAttemptsFromRetryPolicy(Map<String, ?> retryPolicy) {
        return JsonUtil.getNumberAsInteger(retryPolicy, "maxAttempts");
    }

    @Nullable
    static Long getInitialBackoffNanosFromRetryPolicy(Map<String, ?> retryPolicy) {
        return JsonUtil.getStringAsDuration(retryPolicy, "initialBackoff");
    }

    @Nullable
    static Long getMaxBackoffNanosFromRetryPolicy(Map<String, ?> retryPolicy) {
        return JsonUtil.getStringAsDuration(retryPolicy, "maxBackoff");
    }

    @Nullable
    static Double getBackoffMultiplierFromRetryPolicy(Map<String, ?> retryPolicy) {
        return JsonUtil.getNumberAsDouble(retryPolicy, "backoffMultiplier");
    }

    @Nullable
    static Long getPerAttemptRecvTimeoutNanosFromRetryPolicy(Map<String, ?> retryPolicy) {
        return JsonUtil.getStringAsDuration(retryPolicy, "perAttemptRecvTimeout");
    }

    private static Set<Status.Code> getListOfStatusCodesAsSet(Map<String, ?> obj, String key) {
        List<?> statuses = JsonUtil.getList(obj, key);
        if (statuses == null) {
            return null;
        }
        return ServiceConfigUtil.getStatusCodesFromList(statuses);
    }

    private static Set<Status.Code> getStatusCodesFromList(List<?> statuses) {
        EnumSet<Status.Code> codes = EnumSet.noneOf(Status.Code.class);
        for (Object status : statuses) {
            Status.Code code;
            if (status instanceof Double) {
                Double statusD = (Double)status;
                int codeValue = statusD.intValue();
                Verify.verify((double)codeValue == statusD, "Status code %s is not integral", status);
                code = Status.fromCodeValue(codeValue).getCode();
                Verify.verify(code.value() == statusD.intValue(), "Status code %s is not valid", status);
            } else if (status instanceof String) {
                try {
                    code = Status.Code.valueOf((String)status);
                }
                catch (IllegalArgumentException iae) {
                    throw new VerifyException("Status code " + status + " is not valid", iae);
                }
            } else {
                throw new VerifyException("Can not convert status code " + status + " to Status.Code, because its type is " + status.getClass());
            }
            codes.add(code);
        }
        return Collections.unmodifiableSet(codes);
    }

    static Set<Status.Code> getRetryableStatusCodesFromRetryPolicy(Map<String, ?> retryPolicy) {
        String retryableStatusCodesKey = "retryableStatusCodes";
        Set<Status.Code> codes = ServiceConfigUtil.getListOfStatusCodesAsSet(retryPolicy, retryableStatusCodesKey);
        Verify.verify(codes != null, "%s is required in retry policy", (Object)retryableStatusCodesKey);
        Verify.verify(!codes.contains((Object)Status.Code.OK), "%s must not contain OK", (Object)retryableStatusCodesKey);
        return codes;
    }

    @Nullable
    static Integer getMaxAttemptsFromHedgingPolicy(Map<String, ?> hedgingPolicy) {
        return JsonUtil.getNumberAsInteger(hedgingPolicy, "maxAttempts");
    }

    @Nullable
    static Long getHedgingDelayNanosFromHedgingPolicy(Map<String, ?> hedgingPolicy) {
        return JsonUtil.getStringAsDuration(hedgingPolicy, "hedgingDelay");
    }

    static Set<Status.Code> getNonFatalStatusCodesFromHedgingPolicy(Map<String, ?> hedgingPolicy) {
        String nonFatalStatusCodesKey = "nonFatalStatusCodes";
        Set<Status.Code> codes = ServiceConfigUtil.getListOfStatusCodesAsSet(hedgingPolicy, nonFatalStatusCodesKey);
        if (codes == null) {
            return Collections.unmodifiableSet(EnumSet.noneOf(Status.Code.class));
        }
        Verify.verify(!codes.contains((Object)Status.Code.OK), "%s must not contain OK", (Object)nonFatalStatusCodesKey);
        return codes;
    }

    @Nullable
    static String getServiceFromName(Map<String, ?> name) {
        return JsonUtil.getString(name, "service");
    }

    @Nullable
    static String getMethodFromName(Map<String, ?> name) {
        return JsonUtil.getString(name, "method");
    }

    @Nullable
    static Map<String, ?> getRetryPolicyFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getObject(methodConfig, "retryPolicy");
    }

    @Nullable
    static Map<String, ?> getHedgingPolicyFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getObject(methodConfig, "hedgingPolicy");
    }

    @Nullable
    static List<Map<String, ?>> getNameListFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getListOfObjects(methodConfig, "name");
    }

    @Nullable
    static Long getTimeoutFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getStringAsDuration(methodConfig, "timeout");
    }

    @Nullable
    static Boolean getWaitForReadyFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getBoolean(methodConfig, "waitForReady");
    }

    @Nullable
    static Integer getMaxRequestMessageBytesFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getNumberAsInteger(methodConfig, "maxRequestMessageBytes");
    }

    @Nullable
    static Integer getMaxResponseMessageBytesFromMethodConfig(Map<String, ?> methodConfig) {
        return JsonUtil.getNumberAsInteger(methodConfig, "maxResponseMessageBytes");
    }

    @Nullable
    static List<Map<String, ?>> getMethodConfigFromServiceConfig(Map<String, ?> serviceConfig) {
        return JsonUtil.getListOfObjects(serviceConfig, "methodConfig");
    }

    @VisibleForTesting
    public static List<Map<String, ?>> getLoadBalancingConfigsFromServiceConfig(Map<String, ?> serviceConfig) {
        String policy;
        ArrayList lbConfigs = new ArrayList();
        String loadBalancingConfigKey = "loadBalancingConfig";
        if (serviceConfig.containsKey(loadBalancingConfigKey)) {
            lbConfigs.addAll(JsonUtil.getListOfObjects(serviceConfig, loadBalancingConfigKey));
        }
        if (lbConfigs.isEmpty() && (policy = JsonUtil.getString(serviceConfig, "loadBalancingPolicy")) != null) {
            policy = policy.toLowerCase(Locale.ROOT);
            Map fakeConfig = Collections.singletonMap(policy, Collections.emptyMap());
            lbConfigs.add(fakeConfig);
        }
        return Collections.unmodifiableList(lbConfigs);
    }

    public static LbConfig unwrapLoadBalancingConfig(Map<String, ?> lbConfig) {
        if (lbConfig.size() != 1) {
            throw new RuntimeException("There are " + lbConfig.size() + " fields in a LoadBalancingConfig object. Exactly one is expected. Config=" + lbConfig);
        }
        String key = lbConfig.entrySet().iterator().next().getKey();
        return new LbConfig(key, JsonUtil.getObject(lbConfig, key));
    }

    public static List<LbConfig> unwrapLoadBalancingConfigList(List<Map<String, ?>> list) {
        if (list == null) {
            return null;
        }
        ArrayList<LbConfig> result = new ArrayList<LbConfig>();
        for (Map<String, ?> rawChildPolicy : list) {
            result.add(ServiceConfigUtil.unwrapLoadBalancingConfig(rawChildPolicy));
        }
        return Collections.unmodifiableList(result);
    }

    public static NameResolver.ConfigOrError selectLbPolicyFromList(List<LbConfig> lbConfigs, LoadBalancerRegistry lbRegistry) {
        ArrayList<String> policiesTried = new ArrayList<String>();
        for (LbConfig lbConfig : lbConfigs) {
            NameResolver.ConfigOrError parsedLbPolicyConfig;
            String policy = lbConfig.getPolicyName();
            LoadBalancerProvider provider = lbRegistry.getProvider(policy);
            if (provider == null) {
                policiesTried.add(policy);
                continue;
            }
            if (!policiesTried.isEmpty()) {
                Logger.getLogger(ServiceConfigUtil.class.getName()).log(Level.FINEST, "{0} specified by Service Config are not available", policiesTried);
            }
            if ((parsedLbPolicyConfig = provider.parseLoadBalancingPolicyConfig(lbConfig.getRawConfigValue())).getError() != null) {
                return parsedLbPolicyConfig;
            }
            return NameResolver.ConfigOrError.fromConfig(new PolicySelection(provider, parsedLbPolicyConfig.getConfig()));
        }
        return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("None of " + policiesTried + " specified by Service Config are available."));
    }

    public static final class PolicySelection {
        final LoadBalancerProvider provider;
        @Nullable
        final Object config;

        public PolicySelection(LoadBalancerProvider provider, @Nullable Object config) {
            this.provider = Preconditions.checkNotNull(provider, "provider");
            this.config = config;
        }

        public LoadBalancerProvider getProvider() {
            return this.provider;
        }

        @Nullable
        public Object getConfig() {
            return this.config;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            PolicySelection that = (PolicySelection)o;
            return Objects.equal(this.provider, that.provider) && Objects.equal(this.config, that.config);
        }

        public int hashCode() {
            return Objects.hashCode(this.provider, this.config);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("provider", this.provider).add("config", this.config).toString();
        }
    }

    public static final class LbConfig {
        private final String policyName;
        private final Map<String, ?> rawConfigValue;

        public LbConfig(String policyName, Map<String, ?> rawConfigValue) {
            this.policyName = Preconditions.checkNotNull(policyName, "policyName");
            this.rawConfigValue = Preconditions.checkNotNull(rawConfigValue, "rawConfigValue");
        }

        public String getPolicyName() {
            return this.policyName;
        }

        public Map<String, ?> getRawConfigValue() {
            return this.rawConfigValue;
        }

        public boolean equals(Object o) {
            if (o instanceof LbConfig) {
                LbConfig other = (LbConfig)o;
                return this.policyName.equals(other.policyName) && this.rawConfigValue.equals(other.rawConfigValue);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.policyName, this.rawConfigValue);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("policyName", this.policyName).add("rawConfigValue", this.rawConfigValue).toString();
        }
    }
}

