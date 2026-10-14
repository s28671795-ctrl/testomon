/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.io.Closeable;

public interface ReplayCache
extends Closeable {
    public void add(String var1);

    public void add(String var1, long var2);

    public boolean contains(String var1);
}

