/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.util.Set;

public final class GetMethodFromGetterNameCandidates
implements PrivilegedAction<Method> {
    private final Class<?> clazz;
    private final Set<String> getterNameCandidates;
    private final boolean lookForMethodsInHierarchy;

    private GetMethodFromGetterNameCandidates(Class<?> clazz, Set<String> getterNameCandidates, boolean lookForMethodsInHierarchy) {
        this.clazz = clazz;
        this.getterNameCandidates = getterNameCandidates;
        this.lookForMethodsInHierarchy = lookForMethodsInHierarchy;
    }

    public static GetMethodFromGetterNameCandidates action(Class<?> clazz, Set<String> getterNameCandidates) {
        return new GetMethodFromGetterNameCandidates(clazz, getterNameCandidates, false);
    }

    public static GetMethodFromGetterNameCandidates action(Class<?> clazz, Set<String> possibleMethodNames, boolean lookForMethodsInHierarchy) {
        return new GetMethodFromGetterNameCandidates(clazz, possibleMethodNames, lookForMethodsInHierarchy);
    }

    @Override
    public Method run() {
        for (String methodName : this.getterNameCandidates) {
            try {
                if (this.lookForMethodsInHierarchy) {
                    return this.clazz.getMethod(methodName, new Class[0]);
                }
                return this.clazz.getDeclaredMethod(methodName, new Class[0]);
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
        }
        return null;
    }
}

