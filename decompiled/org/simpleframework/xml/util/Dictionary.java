/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.util.Entry;

public class Dictionary<T extends Entry>
extends AbstractSet<T> {
    protected final Table<T> map = new Table();

    @Override
    public boolean add(T item) {
        return this.map.put(item.getName(), item) != null;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Iterator<T> iterator() {
        return this.map.values().iterator();
    }

    public T get(String name) {
        return (T)((Entry)this.map.get(name));
    }

    public T remove(String name) {
        return (T)((Entry)this.map.remove(name));
    }

    private static class Table<T>
    extends LinkedHashMap<String, T> {
    }
}

