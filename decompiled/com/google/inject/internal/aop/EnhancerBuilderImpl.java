/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.common.collect.ImmutableMap;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.aop.ClassBuilding;
import com.google.inject.internal.aop.ClassDefining;
import com.google.inject.internal.aop.Enhancer;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

final class EnhancerBuilderImpl
implements BytecodeGen.EnhancerBuilder {
    private static final ClassValue<Map<BitSet, Function<String, BiFunction<Object, Object[], Object>>>> ENHANCERS = new ClassValue<Map<BitSet, Function<String, BiFunction<Object, Object[], Object>>>>(){

        @Override
        protected Map<BitSet, Function<String, BiFunction<Object, Object[], Object>>> computeValue(Class<?> hostClass) {
            return new HashMap<BitSet, Function<String, BiFunction<Object, Object[], Object>>>();
        }
    };
    private final Class<?> hostClass;
    private final Method[] enhanceableMethods;
    private final Map<Method, Method> bridgeDelegates;

    EnhancerBuilderImpl(Class<?> hostClass, Collection<Method> enhanceableMethods, Map<Method, Method> bridgeDelegates) {
        this.hostClass = hostClass;
        this.enhanceableMethods = enhanceableMethods.toArray(new Method[0]);
        this.bridgeDelegates = ImmutableMap.copyOf(bridgeDelegates);
    }

    @Override
    public Method[] getEnhanceableMethods() {
        return this.enhanceableMethods;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Function<String, BiFunction<Object, Object[], Object>> buildEnhancer(BitSet methodIndices) {
        Map<BitSet, Function<String, BiFunction<Object, Object[], Object>>> enhancers;
        if ((this.hostClass.getModifiers() & 0x10) != 0) {
            throw new IllegalArgumentException("Cannot subclass final " + this.hostClass);
        }
        Map<BitSet, Function<String, BiFunction<Object, Object[], Object>>> map = enhancers = ENHANCERS.get(this.hostClass);
        synchronized (map) {
            return enhancers.computeIfAbsent(methodIndices, this::doBuildEnhancer);
        }
    }

    private Function<String, BiFunction<Object, Object[], Object>> doBuildEnhancer(BitSet methodIndices) {
        TreeMap<String, Executable> glueMap = new TreeMap<String, Executable>();
        ClassBuilding.visitMembers((Executable[])this.hostClass.getDeclaredConstructors(), (boolean)ClassDefining.hasPackageAccess(), ctor -> glueMap.put(ClassBuilding.signature(ctor), (Executable)ctor));
        int methodIndex = methodIndices.nextSetBit(0);
        while (methodIndex >= 0) {
            Method method2 = this.enhanceableMethods[methodIndex];
            glueMap.put(ClassBuilding.signature(method2), method2);
            methodIndex = methodIndices.nextSetBit(methodIndex + 1);
        }
        return new Enhancer(this.hostClass, this.bridgeDelegates).glue(glueMap);
    }
}

