/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ElementTypesAreNonnullByDefault;
import com.google.common.util.concurrent.LazyLogger;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Platform;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
@J2ktIncompatible
public abstract class AbstractExecutionThreadService
implements Service {
    private static final LazyLogger logger = new LazyLogger(AbstractExecutionThreadService.class);
    private final Service delegate = new AbstractService(){

        @Override
        protected final void doStart() {
            Executor executor2 = MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), () -> AbstractExecutionThreadService.this.serviceName());
            executor2.execute(() -> {
                try {
                    AbstractExecutionThreadService.this.startUp();
                    this.notifyStarted();
                    if (this.isRunning()) {
                        try {
                            AbstractExecutionThreadService.this.run();
                        }
                        catch (Throwable t2) {
                            Platform.restoreInterruptIfIsInterruptedException(t2);
                            try {
                                AbstractExecutionThreadService.this.shutDown();
                            }
                            catch (Exception ignored) {
                                Platform.restoreInterruptIfIsInterruptedException(ignored);
                                logger.get().log(Level.WARNING, "Error while attempting to shut down the service after failure.", ignored);
                            }
                            this.notifyFailed(t2);
                            return;
                        }
                    }
                    AbstractExecutionThreadService.this.shutDown();
                    this.notifyStopped();
                }
                catch (Throwable t3) {
                    Platform.restoreInterruptIfIsInterruptedException(t3);
                    this.notifyFailed(t3);
                }
            });
        }

        @Override
        protected void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        @Override
        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    };

    protected AbstractExecutionThreadService() {
    }

    protected void startUp() throws Exception {
    }

    protected abstract void run() throws Exception;

    protected void shutDown() throws Exception {
    }

    protected void triggerShutdown() {
    }

    protected Executor executor() {
        return command -> MoreExecutors.newThread(this.serviceName(), command).start();
    }

    public String toString() {
        return this.serviceName() + " [" + (Object)((Object)this.state()) + "]";
    }

    @Override
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor2) {
        this.delegate.addListener(listener, executor2);
    }

    @Override
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    @Override
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override
    public final void awaitRunning(Duration timeout2) throws TimeoutException {
        Service.super.awaitRunning(timeout2);
    }

    @Override
    public final void awaitRunning(long timeout2, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitRunning(timeout2, unit);
    }

    @Override
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override
    public final void awaitTerminated(Duration timeout2) throws TimeoutException {
        Service.super.awaitTerminated(timeout2);
    }

    @Override
    public final void awaitTerminated(long timeout2, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitTerminated(timeout2, unit);
    }

    protected String serviceName() {
        return this.getClass().getSimpleName();
    }
}

