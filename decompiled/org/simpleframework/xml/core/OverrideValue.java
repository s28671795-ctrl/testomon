/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.strategy.Value;

class OverrideValue
implements Value {
    private final Value value;
    private final Class type;

    public OverrideValue(Value value, Class type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Object getValue() {
        return this.value.getValue();
    }

    @Override
    public void setValue(Object instance) {
        this.value.setValue(instance);
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public int getLength() {
        return this.value.getLength();
    }

    @Override
    public boolean isReference() {
        return this.value.isReference();
    }
}

