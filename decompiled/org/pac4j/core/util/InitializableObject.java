/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InitializableObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializableObject.class);
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private int maxAttempts = 3;
    private AtomicInteger nbAttempts = new AtomicInteger(0);
    private volatile Long lastAttempt;
    private long minTimeIntervalBetweenAttemptsInMilliseconds = 5000L;

    public void init() {
        this.init(false);
    }

    public void reinit() {
        this.init(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void init(boolean forceReinit) {
        if (this.shouldInitialize(forceReinit)) {
            InitializableObject initializableObject = this;
            synchronized (initializableObject) {
                if (this.shouldInitialize(forceReinit)) {
                    LOGGER.debug("Initializing: {} (nb: {}, last: {})", this.getClass().getSimpleName(), this.nbAttempts, this.lastAttempt);
                    this.nbAttempts.incrementAndGet();
                    this.lastAttempt = System.currentTimeMillis();
                    this.beforeInternalInit(forceReinit);
                    this.internalInit(forceReinit);
                    this.afterInternalInit(forceReinit);
                    this.initialized.set(true);
                }
            }
        }
    }

    public final boolean isInitialized() {
        return this.initialized.get();
    }

    protected boolean shouldInitialize(boolean forceReinit) {
        if (forceReinit) {
            return true;
        }
        boolean notInitialized = !this.initialized.get();
        boolean notTooManyAttempts = this.maxAttempts == -1 || this.nbAttempts.get() < this.maxAttempts;
        boolean enoughTimeSinceLastAttempt = this.lastAttempt == null || System.currentTimeMillis() - this.lastAttempt > this.minTimeIntervalBetweenAttemptsInMilliseconds;
        return notInitialized && notTooManyAttempts && enoughTimeSinceLastAttempt;
    }

    protected abstract void internalInit(boolean var1);

    protected void beforeInternalInit(boolean forceReinit) {
    }

    protected void afterInternalInit(boolean forceReinit) {
    }

    public final int getMaxAttempts() {
        return this.maxAttempts;
    }

    public final void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public final int getNbAttempts() {
        return this.nbAttempts.get();
    }

    public final Long getLastAttempt() {
        return this.lastAttempt;
    }

    public final long getMinTimeIntervalBetweenAttemptsInMilliseconds() {
        return this.minTimeIntervalBetweenAttemptsInMilliseconds;
    }

    public final void setMinTimeIntervalBetweenAttemptsInMilliseconds(long minTimeIntervalBetweenAttemptsInMilliseconds) {
        this.minTimeIntervalBetweenAttemptsInMilliseconds = minTimeIntervalBetweenAttemptsInMilliseconds;
    }
}

