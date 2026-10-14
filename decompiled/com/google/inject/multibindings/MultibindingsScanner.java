/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.util.Modules;
import java.lang.annotation.Annotation;
import java.util.Set;

@Deprecated
public class MultibindingsScanner {
    private MultibindingsScanner() {
    }

    @Deprecated
    public static Module asModule() {
        return Modules.EMPTY_MODULE;
    }

    @Deprecated
    public static ModuleAnnotatedMethodScanner scanner() {
        return new ModuleAnnotatedMethodScanner(){

            @Override
            public Set<? extends Class<? extends Annotation>> annotationClasses() {
                return ImmutableSet.of();
            }

            @Override
            public <T> Key<T> prepareMethod(Binder binder, Annotation annotation, Key<T> key, InjectionPoint injectionPoint) {
                throw new IllegalStateException("Unexpected annotation: " + annotation);
            }
        };
    }
}

