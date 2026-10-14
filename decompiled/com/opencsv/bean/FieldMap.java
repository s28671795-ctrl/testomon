/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.util.Collection;
import java.util.Locale;

public interface FieldMap<I, K extends Comparable<K>, C extends ComplexFieldMapEntry<I, K, T>, T> {
    public String[] generateHeader(T var1) throws CsvRequiredFieldEmptyException;

    public BeanField<T, K> get(K var1);

    public BeanField<T, K> put(K var1, BeanField<T, K> var2);

    public void putComplex(I var1, BeanField<T, K> var2);

    public void setErrorLocale(Locale var1);

    public Collection<BeanField<T, K>> values();
}

