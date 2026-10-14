/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

public interface TimeStampStream {
    public boolean isWriting();

    public long getWriteTimestamp();
}

