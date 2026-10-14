/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import java.util.Locale;
import org.apache.commons.lang3.ObjectUtils;

public abstract class AbstractFieldMapEntry<I, K extends Comparable<K>, T>
implements ComplexFieldMapEntry<I, K, T> {
    protected final BeanField<T, K> field;
    protected Locale errorLocale;

    protected AbstractFieldMapEntry(BeanField<T, K> field2, Locale errorLocale) {
        this.field = field2;
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    @Override
    public BeanField<T, K> getBeanField() {
        return this.field;
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }
}

