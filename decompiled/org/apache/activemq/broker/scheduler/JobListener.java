/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import org.apache.activemq.util.ByteSequence;

public interface JobListener {
    public void scheduledJob(String var1, ByteSequence var2);
}

