/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.grpc.Attributes;
import io.grpc.Status;
import io.grpc.internal.ManagedClientTransport;

final class ClientTransportLifecycleManager {
    private final ManagedClientTransport.Listener listener;
    private boolean transportReady;
    private boolean transportShutdown;
    private boolean transportInUse;
    private Status shutdownStatus;
    private Throwable shutdownThrowable;
    private boolean transportTerminated;

    public ClientTransportLifecycleManager(ManagedClientTransport.Listener listener) {
        this.listener = listener;
    }

    public Attributes filterAttributes(Attributes attributes) {
        if (this.transportReady || this.transportShutdown) {
            return attributes;
        }
        return this.listener.filterTransport(attributes);
    }

    public void notifyReady() {
        if (this.transportReady || this.transportShutdown) {
            return;
        }
        this.transportReady = true;
        this.listener.transportReady();
    }

    public void notifyGracefulShutdown(Status s2) {
        if (this.transportShutdown) {
            return;
        }
        this.transportShutdown = true;
        this.listener.transportShutdown(s2);
    }

    @CanIgnoreReturnValue
    public boolean notifyShutdown(Status s2) {
        this.notifyGracefulShutdown(s2);
        if (this.shutdownStatus != null) {
            return false;
        }
        this.shutdownStatus = s2;
        this.shutdownThrowable = s2.asException();
        return true;
    }

    public void notifyInUse(boolean inUse) {
        if (inUse == this.transportInUse) {
            return;
        }
        this.transportInUse = inUse;
        this.listener.transportInUse(inUse);
    }

    public void notifyTerminated(Status s2) {
        if (this.transportTerminated) {
            return;
        }
        this.transportTerminated = true;
        this.notifyShutdown(s2);
        this.listener.transportTerminated();
    }

    public Status getShutdownStatus() {
        return this.shutdownStatus;
    }

    public Throwable getShutdownThrowable() {
        return this.shutdownThrowable;
    }
}

