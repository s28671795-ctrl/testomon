/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;

public class FieldMapByPositionEntry<T> {
    private final int position;
    private final BeanField<T, Integer> field;

    public FieldMapByPositionEntry(int position, BeanField<T, Integer> field2) {
        this.position = position;
        this.field = field2;
    }

    public int getPosition() {
        return this.position;
    }

    public BeanField<T, Integer> getField() {
        return this.field;
    }
}

