/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.ClassDefiner;
import com.google.inject.internal.aop.UnsafeGetter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import sun.misc.Unsafe;

final class HiddenClassDefiner
implements ClassDefiner {
    private static final Unsafe THE_UNSAFE;
    private static final Object TRUSTED_LOOKUP_BASE;
    private static final long TRUSTED_LOOKUP_OFFSET;
    private static final Object HIDDEN_CLASS_OPTIONS;
    private static final Method HIDDEN_DEFINE_METHOD;

    HiddenClassDefiner() {
    }

    @Override
    public Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
        MethodHandles.Lookup trustedLookup = (MethodHandles.Lookup)THE_UNSAFE.getObject(TRUSTED_LOOKUP_BASE, TRUSTED_LOOKUP_OFFSET);
        MethodHandles.Lookup definedLookup = (MethodHandles.Lookup)HIDDEN_DEFINE_METHOD.invoke((Object)trustedLookup.in(hostClass), bytecode, false, HIDDEN_CLASS_OPTIONS);
        return definedLookup.lookupClass();
    }

    private static Object classOptions(String ... options) throws ClassNotFoundException {
        Class<?> optionClass = Class.forName(MethodHandles.Lookup.class.getName() + "$ClassOption");
        Object classOptions = Array.newInstance(optionClass, options.length);
        for (int i = 0; i < options.length; ++i) {
            Array.set(classOptions, i, Enum.valueOf(optionClass, options[i]));
        }
        return classOptions;
    }

    static {
        try {
            THE_UNSAFE = UnsafeGetter.getUnsafe();
            Field trustedLookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            TRUSTED_LOOKUP_BASE = THE_UNSAFE.staticFieldBase(trustedLookupField);
            TRUSTED_LOOKUP_OFFSET = THE_UNSAFE.staticFieldOffset(trustedLookupField);
            HIDDEN_CLASS_OPTIONS = HiddenClassDefiner.classOptions("NESTMATE");
            HIDDEN_DEFINE_METHOD = MethodHandles.Lookup.class.getMethod("defineHiddenClass", byte[].class, Boolean.TYPE, HIDDEN_CLASS_OPTIONS.getClass());
        }
        catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}

