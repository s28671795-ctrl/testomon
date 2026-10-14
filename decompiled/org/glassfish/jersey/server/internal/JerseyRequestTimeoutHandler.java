/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;

public class JerseyRequestTimeoutHandler {
    private static final Logger LOGGER = Logger.getLogger(JerseyRequestTimeoutHandler.class.getName());
    private ScheduledFuture<?> timeoutTask = null;
    private ContainerResponseWriter.TimeoutHandler timeoutHandler = null;
    private boolean suspended = false;
    private final Object runtimeLock = new Object();
    private final ContainerResponseWriter containerResponseWriter;
    private final ScheduledExecutorService executor;

    public JerseyRequestTimeoutHandler(ContainerResponseWriter containerResponseWriter, ScheduledExecutorService timeoutTaskExecutor) {
        this.containerResponseWriter = containerResponseWriter;
        this.executor = timeoutTaskExecutor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean suspend(long timeOut, TimeUnit unit, ContainerResponseWriter.TimeoutHandler handler) {
        Object object = this.runtimeLock;
        synchronized (object) {
            if (this.suspended) {
                return false;
            }
            this.suspended = true;
            this.timeoutHandler = handler;
            this.containerResponseWriter.setSuspendTimeout(timeOut, unit);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setSuspendTimeout(long timeOut, TimeUnit unit) throws IllegalStateException {
        Object object = this.runtimeLock;
        synchronized (object) {
            if (!this.suspended) {
                throw new IllegalStateException(LocalizationMessages.SUSPEND_NOT_SUSPENDED());
            }
            this.close(true);
            if (timeOut <= 0L) {
                return;
            }
            try {
                this.timeoutTask = this.executor.schedule(new Runnable(){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    @Override
                    public void run() {
                        try {
                            Object object = JerseyRequestTimeoutHandler.this.runtimeLock;
                            synchronized (object) {
                                JerseyRequestTimeoutHandler.this.timeoutHandler.onTimeout(JerseyRequestTimeoutHandler.this.containerResponseWriter);
                            }
                        }
                        catch (Throwable throwable) {
                            LOGGER.log(Level.WARNING, LocalizationMessages.SUSPEND_HANDLER_EXECUTION_FAILED(), throwable);
                        }
                    }
                }, timeOut, unit);
            }
            catch (IllegalStateException ex) {
                LOGGER.log(Level.WARNING, LocalizationMessages.SUSPEND_SCHEDULING_ERROR(), ex);
            }
        }
    }

    public void close() {
        this.close(false);
    }

    private synchronized void close(boolean interruptIfRunning) {
        if (this.timeoutTask != null) {
            this.timeoutTask.cancel(interruptIfRunning);
            this.timeoutTask = null;
        }
    }
}

