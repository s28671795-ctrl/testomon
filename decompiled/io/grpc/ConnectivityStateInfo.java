/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.ExperimentalApi;
import io.grpc.Status;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
public final class ConnectivityStateInfo {
    private final ConnectivityState state;
    private final Status status;

    public static ConnectivityStateInfo forNonError(ConnectivityState state) {
        Preconditions.checkArgument(state != ConnectivityState.TRANSIENT_FAILURE, "state is TRANSIENT_ERROR. Use forError() instead");
        return new ConnectivityStateInfo(state, Status.OK);
    }

    public static ConnectivityStateInfo forTransientFailure(Status error) {
        Preconditions.checkArgument(!error.isOk(), "The error status must not be OK");
        return new ConnectivityStateInfo(ConnectivityState.TRANSIENT_FAILURE, error);
    }

    public ConnectivityState getState() {
        return this.state;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean equals(Object other) {
        if (!(other instanceof ConnectivityStateInfo)) {
            return false;
        }
        ConnectivityStateInfo o = (ConnectivityStateInfo)other;
        return this.state.equals((Object)o.state) && this.status.equals(o.status);
    }

    public int hashCode() {
        return this.state.hashCode() ^ this.status.hashCode();
    }

    public String toString() {
        if (this.status.isOk()) {
            return this.state.toString();
        }
        return (Object)((Object)this.state) + "(" + this.status + ")";
    }

    private ConnectivityStateInfo(ConnectivityState state, Status status) {
        this.state = Preconditions.checkNotNull(state, "state is null");
        this.status = Preconditions.checkNotNull(status, "status is null");
    }
}

