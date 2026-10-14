/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.ref.WeakReference;

public class CachedClass {
    private WeakReference<Class<?>> cachedClass;

    public CachedClass(Class<?> cachedClass) {
        this.cachedClass = new WeakReference(cachedClass);
    }

    public Class<?> getCachedClass() {
        return this.cachedClass == null ? null : (Class)this.cachedClass.get();
    }

    public void setCachedClass(Class<?> cachedClass) {
        this.cachedClass = new WeakReference(cachedClass);
    }
}

