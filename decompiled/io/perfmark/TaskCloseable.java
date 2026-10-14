/*
 * Decompiled with CFR 0.152.
 */
package io.perfmark;

import io.perfmark.PerfMark;
import java.io.Closeable;

public final class TaskCloseable
implements Closeable {
    static final TaskCloseable INSTANCE = new TaskCloseable();

    @Override
    public void close() {
        PerfMark.stopTask();
    }

    private TaskCloseable() {
    }
}

