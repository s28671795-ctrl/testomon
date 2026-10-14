/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.base;

import java.util.Collection;

public interface ItemManager<T> {
    public T get(Integer var1);

    public Collection<? extends T> getAll();

    public T add(T var1);

    default public void addAll(Collection<? extends T> items) {
        for (T item : items) {
            this.add(item);
        }
    }

    public T update(T var1);

    public T delete(Integer var1);

    public T delete(T var1);
}

