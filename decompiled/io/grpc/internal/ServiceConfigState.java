/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.NameResolver;
import io.grpc.internal.ManagedChannelServiceConfig;
import javax.annotation.Nullable;

final class ServiceConfigState {
    @Nullable
    private final NameResolver.ConfigOrError defaultServiceConfig;
    private final boolean lookUpServiceConfig;
    @Nullable
    private NameResolver.ConfigOrError currentServiceConfigOrError;
    private boolean updated;

    ServiceConfigState(@Nullable ManagedChannelServiceConfig defaultServiceConfig, boolean lookUpServiceConfig) {
        this.defaultServiceConfig = defaultServiceConfig == null ? null : NameResolver.ConfigOrError.fromConfig(defaultServiceConfig);
        this.lookUpServiceConfig = lookUpServiceConfig;
        if (!lookUpServiceConfig) {
            this.currentServiceConfigOrError = this.defaultServiceConfig;
        }
    }

    boolean shouldWaitOnServiceConfig() {
        return !this.updated && this.expectUpdates();
    }

    @Nullable
    NameResolver.ConfigOrError getCurrent() {
        Preconditions.checkState(!this.shouldWaitOnServiceConfig(), "still waiting on service config");
        return this.currentServiceConfigOrError;
    }

    void update(@Nullable NameResolver.ConfigOrError coe) {
        Preconditions.checkState(this.expectUpdates(), "unexpected service config update");
        boolean firstUpdate = !this.updated;
        this.updated = true;
        if (firstUpdate) {
            if (coe == null) {
                this.currentServiceConfigOrError = this.defaultServiceConfig;
            } else if (coe.getError() != null) {
                this.currentServiceConfigOrError = this.defaultServiceConfig != null ? this.defaultServiceConfig : coe;
            } else {
                assert (coe.getConfig() != null);
                this.currentServiceConfigOrError = coe;
            }
        } else if (coe == null) {
            this.currentServiceConfigOrError = this.defaultServiceConfig != null ? this.defaultServiceConfig : null;
        } else if (coe.getError() != null) {
            if (this.currentServiceConfigOrError != null && this.currentServiceConfigOrError.getError() != null) {
                this.currentServiceConfigOrError = coe;
            }
        } else {
            assert (coe.getConfig() != null);
            this.currentServiceConfigOrError = coe;
        }
    }

    boolean expectUpdates() {
        return this.lookUpServiceConfig;
    }
}

