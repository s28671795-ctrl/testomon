/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import java.util.concurrent.Executor;

public interface WorkQueue
extends Executor {
    public void execute(Runnable var1, long var2);

    public void schedule(Runnable var1, long var2);
}

