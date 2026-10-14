/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.util.Properties;

final class PropUtil {
    private PropUtil() {
    }

    public static boolean getBooleanSystemProperty(String name, boolean def) {
        try {
            return PropUtil.getBoolean(PropUtil.getProp(System.getProperties(), name), def);
        }
        catch (SecurityException securityException) {
            try {
                String value = System.getProperty(name);
                if (value == null) {
                    return def;
                }
                if (def) {
                    return !value.equalsIgnoreCase("false");
                }
                return value.equalsIgnoreCase("true");
            }
            catch (SecurityException sex) {
                return def;
            }
        }
    }

    private static Object getProp(Properties props, String name) {
        Object val = props.get(name);
        if (val != null) {
            return val;
        }
        return props.getProperty(name);
    }

    private static boolean getBoolean(Object value, boolean def) {
        if (value == null) {
            return def;
        }
        if (value instanceof String) {
            if (def) {
                return !((String)value).equalsIgnoreCase("false");
            }
            return ((String)value).equalsIgnoreCase("true");
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        return def;
    }
}

