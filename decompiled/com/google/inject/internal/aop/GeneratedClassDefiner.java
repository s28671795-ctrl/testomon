/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.ClassDefiner;
import java.util.function.BiFunction;

final class GeneratedClassDefiner
implements ClassDefiner {
    private final BiFunction<ClassLoader, byte[], Class<?>> defineAccess;

    GeneratedClassDefiner(BiFunction<ClassLoader, byte[], Class<?>> defineAccess) {
        this.defineAccess = defineAccess;
    }

    @Override
    public Class<?> define(Class<?> hostClass, byte[] bytecode) {
        return this.defineAccess.apply(hostClass.getClassLoader(), bytecode);
    }
}

