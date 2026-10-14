/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.TypeLiteral;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

final class MethodPartition {
    private final List<Method> candidates = new ArrayList<Method>();

    public MethodPartition(Method first, Method second) {
        this.candidates.add(first);
        this.candidates.add(second);
    }

    public MethodPartition addCandidate(Method method2) {
        this.candidates.add(method2);
        return this;
    }

    public void collectEnhanceableMethods(TypeLiteral<?> hostType, Consumer<Method> methodVisitor, Map<Method, Method> bridgeDelegates) {
        HashMap<String, Method> leafMethods = new HashMap<String, Method>();
        HashMap<String, Method> bridgeTargets = new HashMap<String, Method>();
        for (Method method2 : this.candidates) {
            String parametersKey = MethodPartition.parametersKey(method2.getParameterTypes());
            Method existingLeafMethod = leafMethods.putIfAbsent(parametersKey, method2);
            if (existingLeafMethod == null) {
                if (!method2.isBridge()) continue;
                bridgeTargets.put(parametersKey, null);
                continue;
            }
            if (!existingLeafMethod.isBridge()) continue;
            if (existingLeafMethod.getDeclaringClass() == method2.getDeclaringClass()) {
                if (!method2.isBridge()) {
                    leafMethods.put(parametersKey, method2);
                    bridgeTargets.remove(parametersKey);
                    continue;
                }
                if (!existingLeafMethod.getReturnType().isAssignableFrom(method2.getReturnType())) continue;
                leafMethods.put(parametersKey, method2);
                continue;
            }
            if (method2.isBridge()) continue;
            bridgeTargets.putIfAbsent(parametersKey, method2);
        }
        for (Map.Entry entry : leafMethods.entrySet()) {
            Method method2 = (Method)entry.getValue();
            if ((method2.getModifiers() & 0x10) != 0) {
                bridgeTargets.remove(entry.getKey());
                continue;
            }
            if (method2.isBridge()) continue;
            methodVisitor.accept(method2);
        }
        for (Map.Entry entry : bridgeTargets.entrySet()) {
            Method originalBridge = (Method)leafMethods.get(entry.getKey());
            Method superTarget = (Method)entry.getValue();
            Method enhanceableMethod = originalBridge;
            for (Method candidate : this.candidates) {
                boolean sameMethod;
                if (candidate.isBridge()) continue;
                boolean bl = sameMethod = candidate == superTarget;
                if (sameMethod) {
                    if (originalBridge.getReturnType() != superTarget.getReturnType() || superTarget.getDeclaringClass().isInterface()) break;
                    enhanceableMethod = superTarget;
                    break;
                }
                if (!MethodPartition.resolvedParametersMatch(originalBridge, hostType, candidate) && (superTarget == null || !MethodPartition.resolvedParametersMatch(candidate, hostType, superTarget))) continue;
                bridgeDelegates.put(originalBridge, candidate);
                break;
            }
            if ((enhanceableMethod.getModifiers() & 0x10) != 0) continue;
            methodVisitor.accept(enhanceableMethod);
        }
    }

    private static String parametersKey(Class<?>[] parameterTypes) {
        StringBuilder key = new StringBuilder();
        int len = parameterTypes.length;
        for (int i = 0; i < len; ++i) {
            if (i > 0) {
                key.append(',');
            }
            key.append(parameterTypes[i].getName());
        }
        return key.toString();
    }

    private static boolean resolvedParametersMatch(Method subMethod, TypeLiteral<?> host2, Method superMethod) {
        Class<?>[] parameterTypes = subMethod.getParameterTypes();
        List<TypeLiteral<?>> resolvedTypes = host2.getParameterTypes(superMethod);
        int len = parameterTypes.length;
        for (int i = 0; i < len; ++i) {
            if (parameterTypes[i] == resolvedTypes.get(i).getRawType()) continue;
            return false;
        }
        return true;
    }
}

