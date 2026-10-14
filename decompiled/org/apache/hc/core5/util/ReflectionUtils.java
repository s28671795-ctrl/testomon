/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.util;

import java.lang.reflect.Method;
import java.security.AccessController;
import org.apache.hc.core5.annotation.Internal;

@Internal
public final class ReflectionUtils {
    public static void callSetter(Object object, String setterName, Class<?> type, Object value) {
        try {
            Class<?> clazz = object.getClass();
            Method method2 = clazz.getMethod("set" + setterName, type);
            ReflectionUtils.setAccessible(method2);
            method2.invoke(object, value);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static <T> T callGetter(Object object, String getterName, Class<T> resultType) {
        try {
            Class<?> clazz = object.getClass();
            Method method2 = clazz.getMethod("get" + getterName, new Class[0]);
            ReflectionUtils.setAccessible(method2);
            return resultType.cast(method2.invoke(object, new Object[0]));
        }
        catch (Exception ignore) {
            return null;
        }
    }

    private static void setAccessible(Method method2) {
        AccessController.doPrivileged(() -> {
            method2.setAccessible(true);
            return null;
        });
    }

    public static int determineJRELevel() {
        String s2 = System.getProperty("java.version");
        String[] parts = s2.split("\\.");
        if (parts.length > 0) {
            try {
                int majorVersion = Integer.parseInt(parts[0]);
                if (majorVersion > 1) {
                    return majorVersion;
                }
                if (majorVersion == 1 && parts.length > 1) {
                    return Integer.parseInt(parts[1]);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 7;
    }
}

