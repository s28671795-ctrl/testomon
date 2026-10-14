/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import org.apache.cxf.workqueue.AutomaticWorkQueue;

public interface WorkQueueManager {
    public AutomaticWorkQueue getAutomaticWorkQueue();

    public AutomaticWorkQueue getNamedWorkQueue(String var1);

    public void addNamedWorkQueue(String var1, AutomaticWorkQueue var2);

    public void shutdown(boolean var1);

    public void run();
}

