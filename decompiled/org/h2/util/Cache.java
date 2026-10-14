/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayList;
import org.h2.util.CacheObject;

public interface Cache {
    public ArrayList<CacheObject> getAllChanged();

    public void clear();

    public CacheObject get(int var1);

    public void put(CacheObject var1);

    public CacheObject update(int var1, CacheObject var2);

    public boolean remove(int var1);

    public CacheObject find(int var1);

    public void setMaxMemory(int var1);

    public int getMaxMemory();

    public int getMemory();
}

