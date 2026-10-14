/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.message.StringMap;

public class StringMapImpl
extends HashMap<String, Object>
implements StringMap {
    private static final long serialVersionUID = -4590903451121887L;

    public StringMapImpl() {
    }

    public StringMapImpl(Map<String, Object> i) {
        super(i);
    }

    @Override
    public <T> T get(Class<T> key) {
        return (T)this.get(key.getName());
    }

    @Override
    public <T> void put(Class<T> key, T value) {
        this.put(key.getName(), value);
    }

    @Override
    public <T> T remove(Class<T> key) {
        return key.cast(this.remove(key.getName()));
    }
}

