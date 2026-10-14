/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class LongSequenceGenerator {
    private long lastSequenceId;

    public synchronized long getNextSequenceId() {
        return ++this.lastSequenceId;
    }

    public synchronized long getLastSequenceId() {
        return this.lastSequenceId;
    }

    public synchronized void setLastSequenceId(long l) {
        this.lastSequenceId = l;
    }
}

