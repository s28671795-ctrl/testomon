/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;
import org.ldaptive.AbstractRetryMetadata;

public class ClosedRetryMetadata
extends AbstractRetryMetadata {
    protected final Throwable failureException;

    public ClosedRetryMetadata(Instant time, Throwable ex) {
        this.successTime = time;
        this.failureException = ex;
    }

    public Throwable getFailureException() {
        return this.failureException;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "failureException=" + this.failureException;
    }
}

