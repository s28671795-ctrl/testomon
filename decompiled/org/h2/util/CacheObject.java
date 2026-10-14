/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import org.h2.message.DbException;

public abstract class CacheObject
implements Comparable<CacheObject> {
    public CacheObject cachePrevious;
    public CacheObject cacheNext;
    public CacheObject cacheChained;
    private int pos;
    private boolean changed;

    public abstract boolean canRemove();

    public abstract int getMemory();

    public void setPos(int n) {
        if (this.cachePrevious != null || this.cacheNext != null || this.cacheChained != null) {
            throw DbException.getInternalError("setPos too late");
        }
        this.pos = n;
    }

    public int getPos() {
        return this.pos;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean bl) {
        this.changed = bl;
    }

    @Override
    public int compareTo(CacheObject cacheObject) {
        return Integer.compare(this.getPos(), cacheObject.getPos());
    }

    public boolean isStream() {
        return false;
    }
}

