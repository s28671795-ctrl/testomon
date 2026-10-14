/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.util.Map;
import org.apache.cxf.message.Message;

public final class PropertyUtils {
    private PropertyUtils() {
    }

    public static boolean isTrue(Map<String, Object> props, String key) {
        if (props == null || key == null) {
            return false;
        }
        return PropertyUtils.isTrue(props.get(key));
    }

    public static boolean isFalse(Map<String, Object> props, String key) {
        if (props == null || key == null) {
            return false;
        }
        return PropertyUtils.isFalse(props.get(key));
    }

    public static boolean isTrue(Object property) {
        if (property == null) {
            return false;
        }
        return Boolean.TRUE.equals(property) || "true".equalsIgnoreCase(property.toString());
    }

    public static boolean isFalse(Object property) {
        if (property == null) {
            return false;
        }
        return Boolean.FALSE.equals(property) || "false".equalsIgnoreCase(property.toString());
    }

    public static Long getLong(Message message, String key) {
        Object o = message.getContextualProperty(key);
        if (o instanceof Long) {
            return (Long)o;
        }
        if (o instanceof Number) {
            return ((Number)o).longValue();
        }
        if (o instanceof String) {
            return Long.valueOf(o.toString());
        }
        return null;
    }

    public static Integer getInteger(Message message, String key) {
        Object o = message.getContextualProperty(key);
        if (o instanceof Integer) {
            return (Integer)o;
        }
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof String) {
            return Integer.valueOf((String)o);
        }
        return null;
    }
}

