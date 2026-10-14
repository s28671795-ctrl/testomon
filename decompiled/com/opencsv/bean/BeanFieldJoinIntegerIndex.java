/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanFieldJoin;
import com.opencsv.bean.CsvConverter;
import java.lang.reflect.Field;
import java.util.Locale;
import org.apache.commons.collections4.MultiValuedMap;

public class BeanFieldJoinIntegerIndex<T>
extends BeanFieldJoin<T, Integer> {
    public BeanFieldJoinIntegerIndex(Class<?> type, Field field2, boolean required, Locale errorLocale, CsvConverter converter, Class<? extends MultiValuedMap> mapType, String capture, String format) {
        super(type, field2, required, errorLocale, converter, mapType, capture, format);
    }

    @Override
    protected Object putNewValue(MultiValuedMap<Integer, Object> map, String index, Object newValue) {
        return map.put(Integer.valueOf(index), newValue);
    }
}

