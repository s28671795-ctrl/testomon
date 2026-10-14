/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import org.h2.message.Trace;
import org.h2.util.CacheObject;

public interface CacheWriter {
    public void writeBack(CacheObject var1);

    public void flushLog();

    public Trace getTrace();
}

