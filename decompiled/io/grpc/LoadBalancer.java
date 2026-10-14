/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ExperimentalApi;
import io.grpc.Internal;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MetricRecorder;
import io.grpc.NameResolver;
import io.grpc.NameResolverRegistry;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
@NotThreadSafe
public abstract class LoadBalancer {
    @Internal
    public static final Attributes.Key<Map<String, ?>> ATTR_HEALTH_CHECKING_CONFIG = Attributes.Key.create("internal:health-checking-config");
    @Internal
    public static final CreateSubchannelArgs.Key<SubchannelStateListener> HEALTH_CONSUMER_LISTENER_ARG_KEY = CreateSubchannelArgs.Key.create("internal:health-check-consumer-listener");
    @Internal
    public static final Attributes.Key<Boolean> HAS_HEALTH_PRODUCER_LISTENER_KEY = Attributes.Key.create("internal:has-health-check-producer-listener");
    public static final Attributes.Key<Boolean> IS_PETIOLE_POLICY = Attributes.Key.create("io.grpc.IS_PETIOLE_POLICY");
    @Deprecated
    public static final SubchannelPicker EMPTY_PICKER = new SubchannelPicker(){

        @Override
        public PickResult pickSubchannel(PickSubchannelArgs args2) {
            return PickResult.withNoResult();
        }

        public String toString() {
            return "EMPTY_PICKER";
        }
    };
    private int recursionCount;

    public void handleResolvedAddresses(ResolvedAddresses resolvedAddresses) {
        if (this.recursionCount++ == 0) {
            this.acceptResolvedAddresses(resolvedAddresses);
        }
        this.recursionCount = 0;
    }

    public Status acceptResolvedAddresses(ResolvedAddresses resolvedAddresses) {
        if (resolvedAddresses.getAddresses().isEmpty() && !this.canHandleEmptyAddressListFromNameResolution()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
            this.handleNameResolutionError(unavailableStatus);
            return unavailableStatus;
        }
        if (this.recursionCount++ == 0) {
            this.handleResolvedAddresses(resolvedAddresses);
        }
        this.recursionCount = 0;
        return Status.OK;
    }

    public abstract void handleNameResolutionError(Status var1);

    @Deprecated
    public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo stateInfo) {
    }

    public abstract void shutdown();

    public boolean canHandleEmptyAddressListFromNameResolution() {
        return false;
    }

    public void requestConnection() {
    }

    public static final class FixedResultPicker
    extends SubchannelPicker {
        private final PickResult result;

        public FixedResultPicker(PickResult result) {
            this.result = Preconditions.checkNotNull(result, "result");
        }

        @Override
        public PickResult pickSubchannel(PickSubchannelArgs args2) {
            return this.result;
        }

        public String toString() {
            return "FixedResultPicker(" + this.result + ")";
        }
    }

    @Deprecated
    public static final class ErrorPicker
    extends SubchannelPicker {
        private final Status error;

        public ErrorPicker(Status error) {
            this.error = Preconditions.checkNotNull(error, "error");
        }

        @Override
        public PickResult pickSubchannel(PickSubchannelArgs args2) {
            return PickResult.withError(this.error);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("error", this.error).toString();
        }
    }

    @ThreadSafe
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static abstract class Factory {
        public abstract LoadBalancer newLoadBalancer(Helper var1);
    }

    public static interface SubchannelStateListener {
        public void onSubchannelState(ConnectivityStateInfo var1);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static abstract class Subchannel {
        public void start(SubchannelStateListener listener) {
            throw new UnsupportedOperationException("Not implemented");
        }

        public abstract void shutdown();

        public abstract void requestConnection();

        public final EquivalentAddressGroup getAddresses() {
            List<EquivalentAddressGroup> groups2 = this.getAllAddresses();
            Preconditions.checkState(groups2 != null && groups2.size() == 1, "%s does not have exactly one group", groups2);
            return groups2.get(0);
        }

        public List<EquivalentAddressGroup> getAllAddresses() {
            throw new UnsupportedOperationException();
        }

        public abstract Attributes getAttributes();

        @Internal
        public Channel asChannel() {
            throw new UnsupportedOperationException();
        }

        public ChannelLogger getChannelLogger() {
            throw new UnsupportedOperationException();
        }

        public void updateAddresses(List<EquivalentAddressGroup> addrs) {
            throw new UnsupportedOperationException();
        }

        @Internal
        public Object getInternalSubchannel() {
            throw new UnsupportedOperationException();
        }
    }

    @ThreadSafe
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static abstract class Helper {
        public Subchannel createSubchannel(CreateSubchannelArgs args2) {
            throw new UnsupportedOperationException();
        }

        public abstract ManagedChannel createOobChannel(EquivalentAddressGroup var1, String var2);

        public ManagedChannel createOobChannel(List<EquivalentAddressGroup> eag, String authority) {
            throw new UnsupportedOperationException();
        }

        public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
            throw new UnsupportedOperationException();
        }

        public void updateOobChannelAddresses(ManagedChannel channel, List<EquivalentAddressGroup> eag) {
            throw new UnsupportedOperationException();
        }

        public ManagedChannel createResolvingOobChannel(String target) {
            return this.createResolvingOobChannelBuilder(target).build();
        }

        @Deprecated
        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target) {
            throw new UnsupportedOperationException("Not implemented");
        }

        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target, ChannelCredentials creds) {
            throw new UnsupportedOperationException();
        }

        public abstract void updateBalancingState(@Nonnull ConnectivityState var1, @Nonnull SubchannelPicker var2);

        public void refreshNameResolution() {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8088")
        public void ignoreRefreshNameResolutionCheck() {
        }

        public SynchronizationContext getSynchronizationContext() {
            throw new UnsupportedOperationException();
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            throw new UnsupportedOperationException();
        }

        public abstract String getAuthority();

        public String getChannelTarget() {
            throw new UnsupportedOperationException();
        }

        public ChannelCredentials getChannelCredentials() {
            return this.getUnsafeChannelCredentials().withoutBearerTokens();
        }

        public ChannelCredentials getUnsafeChannelCredentials() {
            throw new UnsupportedOperationException();
        }

        public ChannelLogger getChannelLogger() {
            throw new UnsupportedOperationException();
        }

        public NameResolver.Args getNameResolverArgs() {
            throw new UnsupportedOperationException();
        }

        public NameResolverRegistry getNameResolverRegistry() {
            throw new UnsupportedOperationException();
        }

        @Internal
        public MetricRecorder getMetricRecorder() {
            return new MetricRecorder(){};
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static final class CreateSubchannelArgs {
        private final List<EquivalentAddressGroup> addrs;
        private final Attributes attrs;
        private final Object[][] customOptions;

        private CreateSubchannelArgs(List<EquivalentAddressGroup> addrs, Attributes attrs, Object[][] customOptions) {
            this.addrs = Preconditions.checkNotNull(addrs, "addresses are not set");
            this.attrs = Preconditions.checkNotNull(attrs, "attrs");
            this.customOptions = Preconditions.checkNotNull(customOptions, "customOptions");
        }

        public List<EquivalentAddressGroup> getAddresses() {
            return this.addrs;
        }

        public Attributes getAttributes() {
            return this.attrs;
        }

        public <T> T getOption(Key<T> key) {
            Preconditions.checkNotNull(key, "key");
            for (int i = 0; i < this.customOptions.length; ++i) {
                if (!key.equals(this.customOptions[i][0])) continue;
                return (T)this.customOptions[i][1];
            }
            return (T)((Key)key).defaultValue;
        }

        public Builder toBuilder() {
            return CreateSubchannelArgs.newBuilder().setAddresses(this.addrs).setAttributes(this.attrs).copyCustomOptions(this.customOptions);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("addrs", this.addrs).add("attrs", this.attrs).add("customOptions", Arrays.deepToString((Object[])this.customOptions)).toString();
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
        public static final class Key<T> {
            private final String debugString;
            private final T defaultValue;

            private Key(String debugString, T defaultValue) {
                this.debugString = debugString;
                this.defaultValue = defaultValue;
            }

            public static <T> Key<T> create(String debugString) {
                Preconditions.checkNotNull(debugString, "debugString");
                return new Key<Object>(debugString, null);
            }

            public static <T> Key<T> createWithDefault(String debugString, T defaultValue) {
                Preconditions.checkNotNull(debugString, "debugString");
                return new Key<T>(debugString, defaultValue);
            }

            public T getDefault() {
                return this.defaultValue;
            }

            public String toString() {
                return this.debugString;
            }
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
        public static final class Builder {
            private List<EquivalentAddressGroup> addrs;
            private Attributes attrs = Attributes.EMPTY;
            private Object[][] customOptions = new Object[0][2];

            Builder() {
            }

            private Builder copyCustomOptions(Object[][] options) {
                this.customOptions = new Object[options.length][2];
                System.arraycopy(options, 0, this.customOptions, 0, options.length);
                return this;
            }

            public <T> Builder addOption(Key<T> key, T value) {
                Preconditions.checkNotNull(key, "key");
                Preconditions.checkNotNull(value, "value");
                int existingIdx = -1;
                for (int i = 0; i < this.customOptions.length; ++i) {
                    if (!key.equals(this.customOptions[i][0])) continue;
                    existingIdx = i;
                    break;
                }
                if (existingIdx == -1) {
                    Object[][] newCustomOptions = new Object[this.customOptions.length + 1][2];
                    System.arraycopy(this.customOptions, 0, newCustomOptions, 0, this.customOptions.length);
                    this.customOptions = newCustomOptions;
                    existingIdx = this.customOptions.length - 1;
                }
                this.customOptions[existingIdx] = new Object[]{key, value};
                return this;
            }

            public Builder setAddresses(EquivalentAddressGroup addrs) {
                this.addrs = Collections.singletonList(addrs);
                return this;
            }

            public Builder setAddresses(List<EquivalentAddressGroup> addrs) {
                Preconditions.checkArgument(!addrs.isEmpty(), "addrs is empty");
                this.addrs = Collections.unmodifiableList(new ArrayList<EquivalentAddressGroup>(addrs));
                return this;
            }

            public Builder setAttributes(Attributes attrs) {
                this.attrs = Preconditions.checkNotNull(attrs, "attrs");
                return this;
            }

            public CreateSubchannelArgs build() {
                return new CreateSubchannelArgs(this.addrs, this.attrs, this.customOptions);
            }
        }
    }

    @Immutable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static final class PickResult {
        private static final PickResult NO_RESULT = new PickResult(null, null, Status.OK, false);
        @Nullable
        private final Subchannel subchannel;
        @Nullable
        private final ClientStreamTracer.Factory streamTracerFactory;
        private final Status status;
        private final boolean drop;

        private PickResult(@Nullable Subchannel subchannel, @Nullable ClientStreamTracer.Factory streamTracerFactory, Status status, boolean drop) {
            this.subchannel = subchannel;
            this.streamTracerFactory = streamTracerFactory;
            this.status = Preconditions.checkNotNull(status, "status");
            this.drop = drop;
        }

        public static PickResult withSubchannel(Subchannel subchannel, @Nullable ClientStreamTracer.Factory streamTracerFactory) {
            return new PickResult(Preconditions.checkNotNull(subchannel, "subchannel"), streamTracerFactory, Status.OK, false);
        }

        public static PickResult withSubchannel(Subchannel subchannel) {
            return PickResult.withSubchannel(subchannel, null);
        }

        public static PickResult withError(Status error) {
            Preconditions.checkArgument(!error.isOk(), "error status shouldn't be OK");
            return new PickResult(null, null, error, false);
        }

        public static PickResult withDrop(Status status) {
            Preconditions.checkArgument(!status.isOk(), "drop status shouldn't be OK");
            return new PickResult(null, null, status, true);
        }

        public static PickResult withNoResult() {
            return NO_RESULT;
        }

        @Nullable
        public Subchannel getSubchannel() {
            return this.subchannel;
        }

        @Nullable
        public ClientStreamTracer.Factory getStreamTracerFactory() {
            return this.streamTracerFactory;
        }

        public Status getStatus() {
            return this.status;
        }

        public boolean isDrop() {
            return this.drop;
        }

        public boolean hasResult() {
            return this.subchannel != null || !this.status.isOk();
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("subchannel", this.subchannel).add("streamTracerFactory", this.streamTracerFactory).add("status", this.status).add("drop", this.drop).toString();
        }

        public int hashCode() {
            return Objects.hashCode(this.subchannel, this.status, this.streamTracerFactory, this.drop);
        }

        public boolean equals(Object other) {
            if (!(other instanceof PickResult)) {
                return false;
            }
            PickResult that = (PickResult)other;
            return Objects.equal(this.subchannel, that.subchannel) && Objects.equal(this.status, that.status) && Objects.equal(this.streamTracerFactory, that.streamTracerFactory) && this.drop == that.drop;
        }
    }

    @Internal
    public static interface PickDetailsConsumer {
        default public void addOptionalLabel(String key, String value) {
            Preconditions.checkNotNull(key, "key");
            Preconditions.checkNotNull(value, "value");
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static abstract class PickSubchannelArgs {
        public abstract CallOptions getCallOptions();

        public abstract Metadata getHeaders();

        public abstract MethodDescriptor<?, ?> getMethodDescriptor();

        @Internal
        public PickDetailsConsumer getPickDetailsConsumer() {
            return new PickDetailsConsumer(){};
        }
    }

    @ThreadSafe
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static abstract class SubchannelPicker {
        public abstract PickResult pickSubchannel(PickSubchannelArgs var1);

        @Deprecated
        public void requestConnection() {
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
    public static final class ResolvedAddresses {
        private final List<EquivalentAddressGroup> addresses;
        private final Attributes attributes;
        @Nullable
        private final Object loadBalancingPolicyConfig;

        private ResolvedAddresses(List<EquivalentAddressGroup> addresses, Attributes attributes, Object loadBalancingPolicyConfig) {
            this.addresses = Collections.unmodifiableList(new ArrayList(Preconditions.checkNotNull(addresses, "addresses")));
            this.attributes = Preconditions.checkNotNull(attributes, "attributes");
            this.loadBalancingPolicyConfig = loadBalancingPolicyConfig;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return ResolvedAddresses.newBuilder().setAddresses(this.addresses).setAttributes(this.attributes).setLoadBalancingPolicyConfig(this.loadBalancingPolicyConfig);
        }

        public List<EquivalentAddressGroup> getAddresses() {
            return this.addresses;
        }

        public Attributes getAttributes() {
            return this.attributes;
        }

        @Nullable
        public Object getLoadBalancingPolicyConfig() {
            return this.loadBalancingPolicyConfig;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("addresses", this.addresses).add("attributes", this.attributes).add("loadBalancingPolicyConfig", this.loadBalancingPolicyConfig).toString();
        }

        public int hashCode() {
            return Objects.hashCode(this.addresses, this.attributes, this.loadBalancingPolicyConfig);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ResolvedAddresses)) {
                return false;
            }
            ResolvedAddresses that = (ResolvedAddresses)obj;
            return Objects.equal(this.addresses, that.addresses) && Objects.equal(this.attributes, that.attributes) && Objects.equal(this.loadBalancingPolicyConfig, that.loadBalancingPolicyConfig);
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
        public static final class Builder {
            private List<EquivalentAddressGroup> addresses;
            private Attributes attributes = Attributes.EMPTY;
            @Nullable
            private Object loadBalancingPolicyConfig;

            Builder() {
            }

            public Builder setAddresses(List<EquivalentAddressGroup> addresses) {
                this.addresses = addresses;
                return this;
            }

            public Builder setAttributes(Attributes attributes) {
                this.attributes = attributes;
                return this;
            }

            public Builder setLoadBalancingPolicyConfig(@Nullable Object loadBalancingPolicyConfig) {
                this.loadBalancingPolicyConfig = loadBalancingPolicyConfig;
                return this;
            }

            public ResolvedAddresses build() {
                return new ResolvedAddresses(this.addresses, this.attributes, this.loadBalancingPolicyConfig);
            }
        }
    }
}

