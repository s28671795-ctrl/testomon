/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Map;

public interface StringMap
extends Map<String, Object> {
    public <T> T get(Class<T> var1);

    @Override
    public <T> void put(Class<T> var1, T var2);

    public <T> T remove(Class<T> var1);
}

