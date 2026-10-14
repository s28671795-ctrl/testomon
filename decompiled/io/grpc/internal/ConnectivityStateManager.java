/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
final class ConnectivityStateManager {
    private ArrayList<Listener> listeners = new ArrayList();
    private volatile ConnectivityState state = ConnectivityState.IDLE;

    ConnectivityStateManager() {
    }

    void notifyWhenStateChanged(Runnable callback, Executor executor2, ConnectivityState source2) {
        Preconditions.checkNotNull(callback, "callback");
        Preconditions.checkNotNull(executor2, "executor");
        Preconditions.checkNotNull(source2, "source");
        Listener stateChangeListener = new Listener(callback, executor2);
        if (this.state != source2) {
            stateChangeListener.runInExecutor();
        } else {
            this.listeners.add(stateChangeListener);
        }
    }

    void gotoState(@Nonnull ConnectivityState newState) {
        Preconditions.checkNotNull(newState, "newState");
        if (this.state != newState && this.state != ConnectivityState.SHUTDOWN) {
            this.state = newState;
            if (this.listeners.isEmpty()) {
                return;
            }
            ArrayList<Listener> savedListeners = this.listeners;
            this.listeners = new ArrayList();
            for (Listener listener : savedListeners) {
                listener.runInExecutor();
            }
        }
    }

    ConnectivityState getState() {
        ConnectivityState stateCopy = this.state;
        if (stateCopy == null) {
            throw new UnsupportedOperationException("Channel state API is not implemented");
        }
        return stateCopy;
    }

    private static final class Listener {
        final Runnable callback;
        final Executor executor;

        Listener(Runnable callback, Executor executor2) {
            this.callback = callback;
            this.executor = executor2;
        }

        void runInExecutor() {
            this.executor.execute(this.callback);
        }
    }
}

