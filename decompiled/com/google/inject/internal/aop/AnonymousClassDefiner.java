/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.ClassDefiner;
import com.google.inject.internal.aop.UnsafeGetter;
import java.lang.reflect.Method;
import sun.misc.Unsafe;

final class AnonymousClassDefiner
implements ClassDefiner {
    private static final Unsafe THE_UNSAFE;
    private static final Method ANONYMOUS_DEFINE_METHOD;

    AnonymousClassDefiner() {
    }

    @Override
    public Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
        return (Class)ANONYMOUS_DEFINE_METHOD.invoke((Object)THE_UNSAFE, hostClass, bytecode, null);
    }

    static {
        try {
            THE_UNSAFE = UnsafeGetter.getUnsafe();
            ANONYMOUS_DEFINE_METHOD = Unsafe.class.getMethod("defineAnonymousClass", Class.class, byte[].class, Object[].class);
        }
        catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}

