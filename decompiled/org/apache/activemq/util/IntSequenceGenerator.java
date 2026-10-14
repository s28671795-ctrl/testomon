/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class IntSequenceGenerator {
    private int lastSequenceId;

    public synchronized int getNextSequenceId() {
        return ++this.lastSequenceId;
    }

    public synchronized int getLastSequenceId() {
        return this.lastSequenceId;
    }

    public synchronized void setLastSequenceId(int l) {
        this.lastSequenceId = l;
    }
}

