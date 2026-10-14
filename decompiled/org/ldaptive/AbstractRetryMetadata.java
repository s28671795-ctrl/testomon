/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import org.ldaptive.RetryMetadata;

public abstract class AbstractRetryMetadata
implements RetryMetadata {
    protected Instant successTime;
    protected Instant failureTime;
    private final AtomicInteger attempts = new AtomicInteger();

    @Override
    public Instant getSuccessTime() {
        return this.successTime;
    }

    @Override
    public Instant getFailureTime() {
        return this.failureTime;
    }

    @Override
    public int getAttempts() {
        return this.attempts.get();
    }

    @Override
    public void recordSuccess(Instant time) {
        this.successTime = time;
    }

    @Override
    public void recordFailure(Instant time) {
        this.failureTime = time;
        this.attempts.incrementAndGet();
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "attempts=" + this.attempts + ", " + "failureTime=" + this.failureTime;
    }
}

