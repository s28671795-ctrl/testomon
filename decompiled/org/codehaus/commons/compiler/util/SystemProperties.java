/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import org.codehaus.commons.nullanalysis.Nullable;

public final class SystemProperties {
    private SystemProperties() {
    }

    public static boolean getBooleanClassProperty(Class<?> targetClass, String classPropertyName) {
        return SystemProperties.getBooleanClassProperty(targetClass, classPropertyName, false);
    }

    public static boolean getBooleanClassProperty(Class<?> targetClass, String classPropertyName, boolean defaultValue) {
        String s2 = SystemProperties.getClassProperty(targetClass, classPropertyName);
        return s2 != null ? Boolean.parseBoolean(s2) : defaultValue;
    }

    public static int getIntegerClassProperty(Class<?> targetClass, String classPropertyName, int defaultValue) {
        String s2 = SystemProperties.getClassProperty(targetClass, classPropertyName);
        return s2 != null ? Integer.parseInt(s2) : defaultValue;
    }

    @Nullable
    public static String getClassProperty(Class<?> targetClass, String classPropertyName) {
        return SystemProperties.getClassProperty(targetClass, classPropertyName, null);
    }

    @Nullable
    public static String getClassProperty(Class<?> targetClass, String classPropertyName, @Nullable String defaultValue) {
        String result = System.getProperty(targetClass.getName() + "." + classPropertyName);
        if (result != null) {
            return result;
        }
        result = System.getProperty(targetClass.getSimpleName() + "." + classPropertyName);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }
}

