/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.support.spring.PropertyPreFilters;

public class FastJsonContainer {
    private Object value;
    private PropertyPreFilters filters;

    FastJsonContainer(Object body2) {
        this.value = body2;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PropertyPreFilters getFilters() {
        return this.filters;
    }

    public void setFilters(PropertyPreFilters filters) {
        this.filters = filters;
    }
}

