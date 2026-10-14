/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.util;

public class OrderedObject<E> {
    private final long ordinal;
    private final E element;

    public OrderedObject(long ordinal, E element) {
        this.ordinal = ordinal;
        this.element = element;
    }

    public long getOrdinal() {
        return this.ordinal;
    }

    public E getElement() {
        return this.element;
    }
}

