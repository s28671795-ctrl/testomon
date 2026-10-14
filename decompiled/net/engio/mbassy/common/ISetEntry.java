/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.common;

public interface ISetEntry<T> {
    public T getValue();

    public void remove();

    public ISetEntry<T> next();

    public void clear();
}

