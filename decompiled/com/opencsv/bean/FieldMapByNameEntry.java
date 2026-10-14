/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;

public class FieldMapByNameEntry<T> {
    private final String name;
    private final BeanField<T, String> field;
    private final boolean regexPattern;

    public FieldMapByNameEntry(String name, BeanField<T, String> field2, boolean regexPattern) {
        this.name = name;
        this.field = field2;
        this.regexPattern = regexPattern;
    }

    public String getName() {
        return this.name;
    }

    public BeanField<T, String> getField() {
        return this.field;
    }

    public boolean isRegexPattern() {
        return this.regexPattern;
    }
}

