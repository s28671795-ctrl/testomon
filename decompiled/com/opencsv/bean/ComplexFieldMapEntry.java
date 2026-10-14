/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;
import java.util.Locale;

public interface ComplexFieldMapEntry<I, K extends Comparable<K>, T> {
    public boolean contains(K var1);

    public BeanField<T, K> getBeanField();

    public I getInitializer();

    public void setErrorLocale(Locale var1);
}

