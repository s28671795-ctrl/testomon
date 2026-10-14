/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;
import com.google.inject.internal.aop.ClassBuilding;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.BitSet;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class BytecodeGen {
    private static final Map<Class<?>, Boolean> circularProxyTypeCache = new MapMaker().weakKeys().makeMap();
    public static final String ENHANCER_BY_GUICE_MARKER = "$$EnhancerByGuice$$";
    public static final String FASTCLASS_BY_GUICE_MARKER = "$$FastClassByGuice$$";
    private static final LoadingCache<Class<?>, EnhancerBuilder> ENHANCER_BUILDERS = CacheBuilder.newBuilder().weakKeys().weakValues().build(CacheLoader.from(ClassBuilding::buildEnhancerBuilder));
    private static final ClassValue<Function<String, BiFunction<Object, Object[], Object>>> FAST_CLASSES = new ClassValue<Function<String, BiFunction<Object, Object[], Object>>>(){

        @Override
        protected Function<String, BiFunction<Object, Object[], Object>> computeValue(Class<?> hostClass) {
            return ClassBuilding.buildFastClass(hostClass);
        }
    };

    public static boolean isCircularProxy(Object object) {
        return object != null && circularProxyTypeCache.containsKey(object.getClass());
    }

    static <T> T newCircularProxy(Class<T> type, InvocationHandler handler) {
        Object proxy = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, handler);
        circularProxyTypeCache.put(proxy.getClass(), Boolean.TRUE);
        return type.cast(proxy);
    }

    static EnhancerBuilder enhancerBuilder(Class<?> hostClass) {
        return ENHANCER_BUILDERS.getUnchecked(hostClass);
    }

    static BiFunction<Object, Object[], Object> enhancedConstructor(Function<String, BiFunction<Object, Object[], Object>> enhancer, Constructor<?> constructor) {
        Preconditions.checkArgument(ClassBuilding.canEnhance(constructor), "Constructor is not visible");
        return enhancer.apply(ClassBuilding.signature(constructor));
    }

    static BiFunction<Object, Object[], Object> superMethod(Function<String, BiFunction<Object, Object[], Object>> enhancer, Method method2) {
        return enhancer.apply(ClassBuilding.signature(method2));
    }

    static BiFunction<Object, Object[], Object> fastConstructor(Constructor<?> constructor) {
        if (ClassBuilding.canFastInvoke(constructor)) {
            return BytecodeGen.fastClass(constructor).apply(ClassBuilding.signature(constructor));
        }
        return null;
    }

    static BiFunction<Object, Object[], Object> fastMethod(Method method2) {
        if (ClassBuilding.canFastInvoke(method2)) {
            return BytecodeGen.fastClass(method2).apply(ClassBuilding.signature(method2));
        }
        return null;
    }

    private static Function<String, BiFunction<Object, Object[], Object>> fastClass(Executable member) {
        return FAST_CLASSES.get(member.getDeclaringClass());
    }

    public static interface EnhancerBuilder {
        public Method[] getEnhanceableMethods();

        public Function<String, BiFunction<Object, Object[], Object>> buildEnhancer(BitSet var1);
    }
}

