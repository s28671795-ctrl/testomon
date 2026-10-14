/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import org.simpleframework.xml.strategy.Value;

class ObjectValue
implements Value {
    private Object value;
    private Class type;

    public ObjectValue(Class type) {
        this.type = type;
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
        return 0;
    }

    @Override
    public boolean isReference() {
        return false;
    }
}

