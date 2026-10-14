/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ClientInterceptor;
import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.Status;
import javax.annotation.Nullable;

@Internal
public abstract class InternalConfigSelector {
    public static final Attributes.Key<InternalConfigSelector> KEY = Attributes.Key.create("internal:io.grpc.config-selector");

    public abstract Result selectConfig(LoadBalancer.PickSubchannelArgs var1);

    public static final class Result {
        private final Status status;
        private final Object config;
        @Nullable
        public ClientInterceptor interceptor;

        private Result(Status status, Object config, ClientInterceptor interceptor) {
            this.status = Preconditions.checkNotNull(status, "status");
            this.config = config;
            this.interceptor = interceptor;
        }

        public static Result forError(Status status) {
            Preconditions.checkArgument(!status.isOk(), "status is OK");
            return new Result(status, null, null);
        }

        public Status getStatus() {
            return this.status;
        }

        public Object getConfig() {
            return this.config;
        }

        @Nullable
        public ClientInterceptor getInterceptor() {
            return this.interceptor;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private Object config;
            private ClientInterceptor interceptor;

            private Builder() {
            }

            public Builder setConfig(Object config) {
                this.config = Preconditions.checkNotNull(config, "config");
                return this;
            }

            public Builder setInterceptor(ClientInterceptor interceptor) {
                this.interceptor = Preconditions.checkNotNull(interceptor, "interceptor");
                return this;
            }

            public Result build() {
                Preconditions.checkState(this.config != null, "config is not set");
                return new Result(Status.OK, this.config, this.interceptor);
            }
        }
    }
}

