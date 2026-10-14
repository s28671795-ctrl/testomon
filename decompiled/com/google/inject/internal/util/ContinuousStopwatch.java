/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.util;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class ContinuousStopwatch {
    private final Logger logger = Logger.getLogger(ContinuousStopwatch.class.getName());
    private final Stopwatch stopwatch;

    public ContinuousStopwatch(Stopwatch stopwatch) {
        this.stopwatch = stopwatch;
        this.reset();
    }

    public long reset() {
        long elapsedTimeMs = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.stopwatch.reset();
        this.stopwatch.start();
        return elapsedTimeMs;
    }

    public void resetAndLog(String label) {
        this.logger.fine(label + ": " + this.reset() + "ms");
    }
}

