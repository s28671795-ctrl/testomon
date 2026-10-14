/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import io.grpc.Status;
import java.util.Set;
import javax.annotation.concurrent.Immutable;

@Immutable
final class HedgingPolicy {
    final int maxAttempts;
    final long hedgingDelayNanos;
    final Set<Status.Code> nonFatalStatusCodes;

    HedgingPolicy(int maxAttempts, long hedgingDelayNanos, Set<Status.Code> nonFatalStatusCodes) {
        this.maxAttempts = maxAttempts;
        this.hedgingDelayNanos = hedgingDelayNanos;
        this.nonFatalStatusCodes = ImmutableSet.copyOf(nonFatalStatusCodes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        HedgingPolicy that = (HedgingPolicy)other;
        return this.maxAttempts == that.maxAttempts && this.hedgingDelayNanos == that.hedgingDelayNanos && Objects.equal(this.nonFatalStatusCodes, that.nonFatalStatusCodes);
    }

    public int hashCode() {
        return Objects.hashCode(this.maxAttempts, this.hedgingDelayNanos, this.nonFatalStatusCodes);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("maxAttempts", this.maxAttempts).add("hedgingDelayNanos", this.hedgingDelayNanos).add("nonFatalStatusCodes", this.nonFatalStatusCodes).toString();
    }
}

