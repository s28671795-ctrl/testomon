/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.strategy.Value;

class ObjectInstance
implements Instance {
    private final Context context;
    private final Value value;
    private final Class type;

    public ObjectInstance(Context context, Value value) {
        this.type = value.getType();
        this.context = context;
        this.value = value;
    }

    @Override
    public Object getInstance() throws Exception {
        if (this.value.isReference()) {
            return this.value.getValue();
        }
        Object object = this.getInstance(this.type);
        if (this.value != null) {
            this.value.setValue(object);
        }
        return object;
    }

    public Object getInstance(Class type) throws Exception {
        Instance value = this.context.getInstance(type);
        Object object = value.getInstance();
        return object;
    }

    @Override
    public Object setInstance(Object object) {
        if (this.value != null) {
            this.value.setValue(object);
        }
        return object;
    }

    @Override
    public boolean isReference() {
        return this.value.isReference();
    }

    @Override
    public Class getType() {
        return this.type;
    }
}

