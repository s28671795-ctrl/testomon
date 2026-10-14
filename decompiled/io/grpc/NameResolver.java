/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.InlineMe;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ExperimentalApi;
import io.grpc.ProxyDetector;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
public abstract class NameResolver {
    public abstract String getServiceAuthority();

    public void start(final Listener listener) {
        if (listener instanceof Listener2) {
            this.start((Listener2)listener);
        } else {
            this.start(new Listener2(){

                @Override
                public void onError(Status error) {
                    listener.onError(error);
                }

                @Override
                public void onResult(ResolutionResult resolutionResult) {
                    listener.onAddresses(resolutionResult.getAddresses(), resolutionResult.getAttributes());
                }
            });
        }
    }

    public void start(Listener2 listener) {
        this.start((Listener)listener);
    }

    public abstract void shutdown();

    public void refresh() {
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static final class ConfigOrError {
        private final Status status;
        private final Object config;

        public static ConfigOrError fromConfig(Object config) {
            return new ConfigOrError(config);
        }

        public static ConfigOrError fromError(Status status) {
            return new ConfigOrError(status);
        }

        private ConfigOrError(Object config) {
            this.config = Preconditions.checkNotNull(config, "config");
            this.status = null;
        }

        private ConfigOrError(Status status) {
            this.config = null;
            this.status = Preconditions.checkNotNull(status, "status");
            Preconditions.checkArgument(!status.isOk(), "cannot use OK status: %s", (Object)status);
        }

        @Nullable
        public Object getConfig() {
            return this.config;
        }

        @Nullable
        public Status getError() {
            return this.status;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ConfigOrError that = (ConfigOrError)o;
            return Objects.equal(this.status, that.status) && Objects.equal(this.config, that.config);
        }

        public int hashCode() {
            return Objects.hashCode(this.status, this.config);
        }

        public String toString() {
            if (this.config != null) {
                return MoreObjects.toStringHelper(this).add("config", this.config).toString();
            }
            assert (this.status != null);
            return MoreObjects.toStringHelper(this).add("error", this.status).toString();
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static final class ResolutionResult {
        private final List<EquivalentAddressGroup> addresses;
        private final Attributes attributes;
        @Nullable
        private final ConfigOrError serviceConfig;

        ResolutionResult(List<EquivalentAddressGroup> addresses, Attributes attributes, ConfigOrError serviceConfig) {
            this.addresses = Collections.unmodifiableList(new ArrayList<EquivalentAddressGroup>(addresses));
            this.attributes = Preconditions.checkNotNull(attributes, "attributes");
            this.serviceConfig = serviceConfig;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return ResolutionResult.newBuilder().setAddresses(this.addresses).setAttributes(this.attributes).setServiceConfig(this.serviceConfig);
        }

        public List<EquivalentAddressGroup> getAddresses() {
            return this.addresses;
        }

        public Attributes getAttributes() {
            return this.attributes;
        }

        @Nullable
        public ConfigOrError getServiceConfig() {
            return this.serviceConfig;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("addresses", this.addresses).add("attributes", this.attributes).add("serviceConfig", this.serviceConfig).toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ResolutionResult)) {
                return false;
            }
            ResolutionResult that = (ResolutionResult)obj;
            return Objects.equal(this.addresses, that.addresses) && Objects.equal(this.attributes, that.attributes) && Objects.equal(this.serviceConfig, that.serviceConfig);
        }

        public int hashCode() {
            return Objects.hashCode(this.addresses, this.attributes, this.serviceConfig);
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
        public static final class Builder {
            private List<EquivalentAddressGroup> addresses = Collections.emptyList();
            private Attributes attributes = Attributes.EMPTY;
            @Nullable
            private ConfigOrError serviceConfig;

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

            public Builder setServiceConfig(@Nullable ConfigOrError serviceConfig) {
                this.serviceConfig = serviceConfig;
                return this;
            }

            public ResolutionResult build() {
                return new ResolutionResult(this.addresses, this.attributes, this.serviceConfig);
            }
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static abstract class ServiceConfigParser {
        public abstract ConfigOrError parseServiceConfig(Map<String, ?> var1);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static final class Args {
        private final int defaultPort;
        private final ProxyDetector proxyDetector;
        private final SynchronizationContext syncContext;
        private final ServiceConfigParser serviceConfigParser;
        @Nullable
        private final ScheduledExecutorService scheduledExecutorService;
        @Nullable
        private final ChannelLogger channelLogger;
        @Nullable
        private final Executor executor;
        @Nullable
        private final String overrideAuthority;

        private Args(Integer defaultPort, ProxyDetector proxyDetector, SynchronizationContext syncContext, ServiceConfigParser serviceConfigParser, @Nullable ScheduledExecutorService scheduledExecutorService, @Nullable ChannelLogger channelLogger, @Nullable Executor executor2, @Nullable String overrideAuthority) {
            this.defaultPort = Preconditions.checkNotNull(defaultPort, "defaultPort not set");
            this.proxyDetector = Preconditions.checkNotNull(proxyDetector, "proxyDetector not set");
            this.syncContext = Preconditions.checkNotNull(syncContext, "syncContext not set");
            this.serviceConfigParser = Preconditions.checkNotNull(serviceConfigParser, "serviceConfigParser not set");
            this.scheduledExecutorService = scheduledExecutorService;
            this.channelLogger = channelLogger;
            this.executor = executor2;
            this.overrideAuthority = overrideAuthority;
        }

        public int getDefaultPort() {
            return this.defaultPort;
        }

        public ProxyDetector getProxyDetector() {
            return this.proxyDetector;
        }

        public SynchronizationContext getSynchronizationContext() {
            return this.syncContext;
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6454")
        public ScheduledExecutorService getScheduledExecutorService() {
            if (this.scheduledExecutorService == null) {
                throw new IllegalStateException("ScheduledExecutorService not set in Builder");
            }
            return this.scheduledExecutorService;
        }

        public ServiceConfigParser getServiceConfigParser() {
            return this.serviceConfigParser;
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6438")
        public ChannelLogger getChannelLogger() {
            if (this.channelLogger == null) {
                throw new IllegalStateException("ChannelLogger is not set in Builder");
            }
            return this.channelLogger;
        }

        @Nullable
        public Executor getOffloadExecutor() {
            return this.executor;
        }

        @Nullable
        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9406")
        public String getOverrideAuthority() {
            return this.overrideAuthority;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("defaultPort", this.defaultPort).add("proxyDetector", this.proxyDetector).add("syncContext", this.syncContext).add("serviceConfigParser", this.serviceConfigParser).add("scheduledExecutorService", this.scheduledExecutorService).add("channelLogger", this.channelLogger).add("executor", this.executor).add("overrideAuthority", this.overrideAuthority).toString();
        }

        public Builder toBuilder() {
            Builder builder = new Builder();
            builder.setDefaultPort(this.defaultPort);
            builder.setProxyDetector(this.proxyDetector);
            builder.setSynchronizationContext(this.syncContext);
            builder.setServiceConfigParser(this.serviceConfigParser);
            builder.setScheduledExecutorService(this.scheduledExecutorService);
            builder.setChannelLogger(this.channelLogger);
            builder.setOffloadExecutor(this.executor);
            builder.setOverrideAuthority(this.overrideAuthority);
            return builder;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private Integer defaultPort;
            private ProxyDetector proxyDetector;
            private SynchronizationContext syncContext;
            private ServiceConfigParser serviceConfigParser;
            private ScheduledExecutorService scheduledExecutorService;
            private ChannelLogger channelLogger;
            private Executor executor;
            private String overrideAuthority;

            Builder() {
            }

            public Builder setDefaultPort(int defaultPort) {
                this.defaultPort = defaultPort;
                return this;
            }

            public Builder setProxyDetector(ProxyDetector proxyDetector) {
                this.proxyDetector = Preconditions.checkNotNull(proxyDetector);
                return this;
            }

            public Builder setSynchronizationContext(SynchronizationContext syncContext) {
                this.syncContext = Preconditions.checkNotNull(syncContext);
                return this;
            }

            @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6454")
            public Builder setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
                this.scheduledExecutorService = Preconditions.checkNotNull(scheduledExecutorService);
                return this;
            }

            public Builder setServiceConfigParser(ServiceConfigParser parser) {
                this.serviceConfigParser = Preconditions.checkNotNull(parser);
                return this;
            }

            @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6438")
            public Builder setChannelLogger(ChannelLogger channelLogger) {
                this.channelLogger = Preconditions.checkNotNull(channelLogger);
                return this;
            }

            public Builder setOffloadExecutor(Executor executor2) {
                this.executor = executor2;
                return this;
            }

            @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9406")
            public Builder setOverrideAuthority(String authority) {
                this.overrideAuthority = authority;
                return this;
            }

            public Args build() {
                return new Args(this.defaultPort, this.proxyDetector, this.syncContext, this.serviceConfigParser, this.scheduledExecutorService, this.channelLogger, this.executor, this.overrideAuthority);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4972")
    public static @interface ResolutionResultAttr {
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static abstract class Listener2
    implements Listener {
        @Override
        @Deprecated
        @InlineMe(replacement="this.onResult(ResolutionResult.newBuilder().setAddresses(servers).setAttributes(attributes).build())", imports={"io.grpc.NameResolver.ResolutionResult"})
        public final void onAddresses(List<EquivalentAddressGroup> servers, Attributes attributes) {
            this.onResult(ResolutionResult.newBuilder().setAddresses(servers).setAttributes(attributes).build());
        }

        public abstract void onResult(ResolutionResult var1);

        @Override
        public abstract void onError(Status var1);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    @ThreadSafe
    public static interface Listener {
        public void onAddresses(List<EquivalentAddressGroup> var1, Attributes var2);

        public void onError(Status var1);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1770")
    public static abstract class Factory {
        public abstract NameResolver newNameResolver(URI var1, Args var2);

        public abstract String getDefaultScheme();
    }
}

