/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.nonstop.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class OverrideCheck {
    private OverrideCheck() {
    }

    public static void check(Class parent, Class subClass) {
        boolean excludeSuper = parent.isAssignableFrom(subClass);
        Set<String> superMethods = OverrideCheck.methodsFor(parent, false);
        Set<String> subMethods = OverrideCheck.methodsFor(subClass, excludeSuper);
        ArrayList<String> missing = new ArrayList<String>();
        for (String method2 : superMethods) {
            if (subMethods.contains(method2)) continue;
            missing.add(method2);
        }
        if (!missing.isEmpty()) {
            throw new RuntimeException(subClass.getName() + " is missing overrides (defined in " + parent.getName() + "):\n" + missing);
        }
    }

    private static Set<String> methodsFor(Class klass, boolean excludeSuper) {
        HashSet<String> set = new HashSet<String>();
        for (Class currClass = klass; currClass != null && currClass != Object.class; currClass = currClass.getSuperclass()) {
            Method[] methods;
            for (Method m4 : methods = currClass.isInterface() ? currClass.getMethods() : currClass.getDeclaredMethods()) {
                int access = m4.getModifiers();
                if (Modifier.isStatic(access) || Modifier.isPrivate(access)) continue;
                StringBuilder sb = new StringBuilder();
                sb.append(m4.getName()).append('(');
                Class<?>[] parameterTypes = m4.getParameterTypes();
                for (int j = 0; j < parameterTypes.length; ++j) {
                    sb.append(parameterTypes[j].getName());
                    if (j >= parameterTypes.length - 1) continue;
                    sb.append(',');
                }
                sb.append(')');
                set.add(sb.toString());
            }
            if (!excludeSuper) continue;
            return set;
        }
        return set;
    }
}

