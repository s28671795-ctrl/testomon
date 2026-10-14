/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.constant.Constable;
import java.util.HashMap;
import java.util.Map;

public final class PrimitiveUtils {
    private static final Map<Class<?>, Class<?>> AUTOBOXED_PRIMITIVES_MAP = new HashMap();

    private PrimitiveUtils() {
    }

    public static boolean canPrimitiveTypeBeAutoboxed(Class<?> primitiveClass, Class<?> type) {
        return primitiveClass.isPrimitive() && type == AUTOBOXED_PRIMITIVES_MAP.get(primitiveClass);
    }

    public static Class<?> getClass(String value) {
        Class<Constable> clz = null;
        if ("int".equals(value)) {
            clz = Integer.TYPE;
        }
        if ("byte".equals(value)) {
            clz = Byte.TYPE;
        }
        if ("short".equals(value)) {
            clz = Short.TYPE;
        }
        if ("long".equals(value)) {
            clz = Long.TYPE;
        }
        if ("float".equals(value)) {
            clz = Float.TYPE;
        }
        if ("double".equals(value)) {
            clz = Double.TYPE;
        }
        if ("boolean".equals(value)) {
            clz = Boolean.TYPE;
        }
        if ("char".equals(value)) {
            clz = Character.TYPE;
        }
        return clz;
    }

    public static <T> Object read(String value, Class<T> type) {
        if (!Character.TYPE.equals(type) && !Character.class.equals(type) && value != null && value.isEmpty()) {
            value = "0";
        }
        Object ret = value;
        if (Integer.TYPE.equals(type) || Integer.class.equals(type)) {
            ret = Integer.valueOf(value);
        }
        if (Byte.TYPE.equals(type) || Byte.class.equals(type)) {
            ret = Byte.valueOf(value);
        }
        if (Short.TYPE.equals(type) || Short.class.equals(type)) {
            ret = Short.valueOf(value);
        }
        if (Long.TYPE.equals(type) || Long.class.equals(type)) {
            ret = Long.valueOf(value);
        }
        if (Float.TYPE.equals(type) || Float.class.equals(type)) {
            ret = Float.valueOf(value);
        }
        if (Double.TYPE.equals(type) || Double.class.equals(type)) {
            ret = Double.valueOf(value);
        }
        if (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) {
            ret = Boolean.valueOf(value);
        }
        if (Character.TYPE.equals(type) || Character.class.equals(type)) {
            ret = Character.valueOf(value.charAt(0));
        }
        return ret;
    }

    static {
        AUTOBOXED_PRIMITIVES_MAP.put(Byte.TYPE, Byte.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Short.TYPE, Short.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Integer.TYPE, Integer.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Long.TYPE, Long.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Float.TYPE, Float.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Double.TYPE, Double.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Boolean.TYPE, Boolean.class);
        AUTOBOXED_PRIMITIVES_MAP.put(Void.TYPE, Void.class);
    }
}

