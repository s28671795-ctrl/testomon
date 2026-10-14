/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.HashMap;
import java.util.Iterator;
import org.h2.message.DbException;

public class SmallMap {
    private final HashMap<Integer, Object> map = new HashMap();
    private Object cache;
    private int cacheId;
    private int lastId;
    private final int maxElements;

    public SmallMap(int n) {
        this.maxElements = n;
    }

    public int addObject(int n, Object object) {
        if (this.map.size() > this.maxElements * 2) {
            Iterator<Integer> iterator2 = this.map.keySet().iterator();
            while (iterator2.hasNext()) {
                Integer n2 = iterator2.next();
                if (n2 + this.maxElements >= this.lastId) continue;
                iterator2.remove();
            }
        }
        if (n > this.lastId) {
            this.lastId = n;
        }
        this.map.put(n, object);
        this.cacheId = n;
        this.cache = object;
        return n;
    }

    public void freeObject(int n) {
        if (this.cacheId == n) {
            this.cacheId = -1;
            this.cache = null;
        }
        this.map.remove(n);
    }

    public Object getObject(int n, boolean bl) {
        if (n == this.cacheId) {
            return this.cache;
        }
        Object object = this.map.get(n);
        if (object == null && !bl) {
            throw DbException.get(90007);
        }
        return object;
    }
}

