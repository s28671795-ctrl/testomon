/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.util.Map;
import org.simpleframework.xml.strategy.Value;

class Allocate
implements Value {
    private Value value;
    private String key;
    private Map map;

    public Allocate(Value value, Map map, String key) {
        this.value = value;
        this.map = map;
        this.key = key;
    }

    @Override
    public Object getValue() {
        return this.map.get(this.key);
    }

    @Override
    public void setValue(Object object) {
        if (this.key != null) {
            this.map.put(this.key, object);
        }
        this.value.setValue(object);
    }

    @Override
    public Class getType() {
        return this.value.getType();
    }

    @Override
    public int getLength() {
        return this.value.getLength();
    }

    @Override
    public boolean isReference() {
        return false;
    }
}

