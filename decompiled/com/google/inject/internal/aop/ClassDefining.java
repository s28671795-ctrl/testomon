/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.aop.ChildClassDefiner;
import com.google.inject.internal.aop.ClassDefiner;
import com.google.inject.internal.aop.UnsafeClassDefiner;
import java.util.logging.Logger;

public final class ClassDefining {
    private static final Logger logger = Logger.getLogger(ClassDefining.class.getName());
    private static final String CLASS_DEFINING_UNSUPPORTED = "Unsafe is not accessible and custom classloading is turned OFF.";

    private ClassDefining() {
    }

    public static Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
        return ClassDefinerHolder.INSTANCE.define(hostClass, bytecode);
    }

    public static boolean hasPackageAccess() {
        return ClassDefinerHolder.IS_UNSAFE;
    }

    public static boolean canLoadProxyByName(Class<?> hostClass) {
        return !ClassDefinerHolder.IS_UNSAFE || UnsafeClassDefiner.canLoadProxyByName(hostClass);
    }

    public static boolean canDowncastToProxy(Class<?> hostClass) {
        return !ClassDefinerHolder.IS_UNSAFE || UnsafeClassDefiner.canDowncastToProxy(hostClass);
    }

    static ClassDefiner bindClassDefiner() {
        InternalFlags.CustomClassLoadingOption loadingOption = InternalFlags.getCustomClassLoadingOption();
        if (loadingOption == InternalFlags.CustomClassLoadingOption.CHILD) {
            return new ChildClassDefiner();
        }
        if (UnsafeClassDefiner.isAccessible()) {
            return new UnsafeClassDefiner();
        }
        if (loadingOption != InternalFlags.CustomClassLoadingOption.OFF) {
            return new ChildClassDefiner();
        }
        logger.warning(CLASS_DEFINING_UNSUPPORTED);
        return (hostClass, bytecode) -> {
            throw new UnsupportedOperationException("Cannot define class, Unsafe is not accessible and custom classloading is turned OFF.");
        };
    }

    private static class ClassDefinerHolder {
        static final ClassDefiner INSTANCE = ClassDefining.bindClassDefiner();
        static final boolean IS_UNSAFE = INSTANCE instanceof UnsafeClassDefiner;

        private ClassDefinerHolder() {
        }
    }
}

