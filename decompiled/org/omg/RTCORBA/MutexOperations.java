/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

public interface MutexOperations {
    public void lock();

    public void unlock();

    public boolean try_lock(long var1);
}

