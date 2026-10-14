/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.AbstractList;

public class InsertionCountList<T>
extends AbstractList<T> {
    int size = 0;

    @Override
    public void add(int index, T element) {
        ++this.size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        return null;
    }
}

