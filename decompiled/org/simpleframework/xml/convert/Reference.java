/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import org.simpleframework.xml.strategy.Value;

class Reference
implements Value {
    private Value value;
    private Object data;
    private Class actual;

    public Reference(Value value, Object data, Class actual) {
        this.actual = actual;
        this.value = value;
        this.data = data;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public Class getType() {
        if (this.data != null) {
            return this.data.getClass();
        }
        return this.actual;
    }

    @Override
    public Object getValue() {
        return this.data;
    }

    @Override
    public boolean isReference() {
        return true;
    }

    @Override
    public void setValue(Object data) {
        if (this.value != null) {
            this.value.setValue(data);
        }
        this.data = data;
    }
}

