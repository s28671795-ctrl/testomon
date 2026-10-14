/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;

public interface RetryMetadata {
    public Instant getSuccessTime();

    public Instant getFailureTime();

    public int getAttempts();

    public void recordSuccess(Instant var1);

    public void recordFailure(Instant var1);
}

