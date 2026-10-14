/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import org.apache.cxf.workqueue.WorkQueue;

public interface AutomaticWorkQueue
extends WorkQueue {
    public String getName();

    public void shutdown(boolean var1);

    public boolean isShutdown();
}

