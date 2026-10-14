/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class PrimitiveMapper {
    public static final Map<Class, Class> primitiveToClassMap = PrimitiveMapper.getPrimitiveToClassMap();
    public static final Map<Class, Object> primitiveToDefaultValueMap = PrimitiveMapper.getPrimitiveToDefaultValueMap();

    private static Map<Class, Class> getPrimitiveToClassMap() {
        WeakHashMap<Class<Comparable<Boolean>>, Class> m4 = new WeakHashMap<Class<Comparable<Boolean>>, Class>();
        m4.put(Boolean.TYPE, Boolean.class);
        m4.put(Byte.TYPE, Byte.class);
        m4.put(Character.TYPE, Character.class);
        m4.put(Short.TYPE, Short.class);
        m4.put(Integer.TYPE, Integer.class);
        m4.put(Long.TYPE, Long.class);
        m4.put(Float.TYPE, Float.class);
        m4.put(Double.TYPE, Double.class);
        return Collections.unmodifiableMap(m4);
    }

    private static Map<Class, Object> getPrimitiveToDefaultValueMap() {
        WeakHashMap<Class, Comparable<Boolean>> m4 = new WeakHashMap<Class, Comparable<Boolean>>();
        m4.put(Boolean.class, Boolean.valueOf(false));
        m4.put(Byte.class, Byte.valueOf((byte)0));
        m4.put(Character.class, Character.valueOf('\u0000'));
        m4.put(Short.class, Short.valueOf((short)0));
        m4.put(Integer.class, Integer.valueOf(0));
        m4.put(Long.class, Long.valueOf(0L));
        m4.put(Float.class, Float.valueOf(0.0f));
        m4.put(Double.class, Double.valueOf(0.0));
        return Collections.unmodifiableMap(m4);
    }

    private PrimitiveMapper() {
    }
}

