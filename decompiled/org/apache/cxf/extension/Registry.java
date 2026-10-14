/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.extension;

public interface Registry<K, T> {
    public void register(K var1, T var2);

    public void unregister(K var1);

    public T get(K var1);
}

