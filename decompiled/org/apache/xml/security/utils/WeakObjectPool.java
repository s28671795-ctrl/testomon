/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Deprecated
public abstract class WeakObjectPool<T, E extends Throwable> {
    private static final Integer MARKER_VALUE = Integer.MAX_VALUE;
    private final BlockingQueue<WeakReference<T>> available = new LinkedBlockingDeque<WeakReference<T>>();
    private final Map<T, Integer> onLoan = Collections.synchronizedMap(new WeakHashMap());

    protected WeakObjectPool() {
    }

    protected abstract T createObject() throws E;

    public T getObject() throws E {
        WeakReference ref;
        T retValue = null;
        while ((ref = (WeakReference)this.available.poll()) != null) {
            Object t2 = ref.get();
            retValue = t2;
            if (t2 == null) continue;
        }
        if (retValue == null) {
            retValue = this.createObject();
        }
        this.onLoan.put(retValue, MARKER_VALUE);
        return retValue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean repool(T obj) {
        if (obj != null && this.onLoan.containsKey(obj)) {
            T t2 = obj;
            synchronized (t2) {
                if (this.onLoan.remove(obj) != null) {
                    return this.available.offer(new WeakReference<T>(obj));
                }
            }
        }
        return false;
    }
}

