/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.FieldMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang3.ObjectUtils;

public abstract class AbstractFieldMap<I, K extends Comparable<K>, C extends ComplexFieldMapEntry<I, K, T>, T>
implements FieldMap<I, K, C, T> {
    protected Locale errorLocale;
    protected final SortedMap<K, BeanField<T, K>> simpleMap = new TreeMap<K, BeanField<T, K>>();
    protected final List<C> complexMapList = new ArrayList<C>();

    protected AbstractFieldMap(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    @Override
    public BeanField<T, K> get(K key) {
        BeanField f = (BeanField)this.simpleMap.get(key);
        if (f == null) {
            f = this.complexMapList.stream().filter(r -> r.contains(key)).map(ComplexFieldMapEntry::getBeanField).findAny().orElse(null);
        }
        return f;
    }

    @Override
    public BeanField<T, K> put(K key, BeanField<T, K> value) {
        return this.simpleMap.put(key, value);
    }

    @Override
    public Collection<BeanField<T, K>> values() {
        ArrayList l = new ArrayList(this.simpleMap.size() + this.complexMapList.size());
        l.addAll(this.simpleMap.values());
        this.complexMapList.forEach(r -> l.add(r.getBeanField()));
        return l;
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        this.complexMapList.forEach(e -> e.setErrorLocale(this.errorLocale));
    }
}

