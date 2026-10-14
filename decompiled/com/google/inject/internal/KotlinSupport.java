/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Errors;
import com.google.inject.internal.KotlinSupportInterface;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public final class KotlinSupport {
    private KotlinSupport() {
    }

    public static KotlinSupportInterface getInstance() {
        return KotlinSupportHolder.INSTANCE;
    }

    private static KotlinSupportInterface loadKotlinSupport() {
        try {
            Class<?> kotlinSupportClass = Class.forName("com.google.inject.KotlinSupportImpl");
            Field instance = kotlinSupportClass.getField("INSTANCE");
            instance.setAccessible(true);
            return (KotlinSupportInterface)instance.get(null);
        }
        catch (ReflectiveOperationException e) {
            return new KotlinUnsupported();
        }
    }

    static /* synthetic */ KotlinSupportInterface access$000() {
        return KotlinSupport.loadKotlinSupport();
    }

    private static class KotlinUnsupported
    implements KotlinSupportInterface {
        static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
        static final Predicate<Integer> FALSE_PREDICATE = integer -> false;

        private KotlinUnsupported() {
        }

        @Override
        public Annotation[] getAnnotations(Field field2) {
            return NO_ANNOTATIONS;
        }

        @Override
        public boolean isNullable(Field field2) {
            return false;
        }

        @Override
        public Predicate<Integer> getIsParameterKotlinNullablePredicate(Constructor<?> constructor) {
            return FALSE_PREDICATE;
        }

        @Override
        public Predicate<Integer> getIsParameterKotlinNullablePredicate(Method method2) {
            return FALSE_PREDICATE;
        }

        @Override
        public void checkConstructorParameterAnnotations(Constructor<?> constructor, Errors errors2) {
        }

        @Override
        public boolean isLocalClass(Class<?> clazz) {
            return false;
        }
    }

    private static class KotlinSupportHolder {
        static final KotlinSupportInterface INSTANCE = KotlinSupport.access$000();

        private KotlinSupportHolder() {
        }
    }
}

