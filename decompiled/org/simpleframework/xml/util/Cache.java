/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

public interface Cache<T> {
    public boolean isEmpty();

    public void cache(Object var1, T var2);

    public T take(Object var1);

    public T fetch(Object var1);

    public boolean contains(Object var1);
}

