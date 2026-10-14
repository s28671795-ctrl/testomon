/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import org.simpleframework.xml.strategy.Value;

class ArrayValue
implements Value {
    private Object value;
    private Class type;
    private int size;

    public ArrayValue(Class type, int size) {
        this.type = type;
        this.size = size;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public int getLength() {
        return this.size;
    }

    @Override
    public boolean isReference() {
        return false;
    }
}

