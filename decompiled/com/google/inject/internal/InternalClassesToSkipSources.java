/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.internal.ProviderMethodsModule;
import com.google.inject.internal.RealMapBinder;
import com.google.inject.internal.RealMultibinder;
import com.google.inject.internal.RealOptionalBinder;

public final class InternalClassesToSkipSources {
    private InternalClassesToSkipSources() {
    }

    public static ImmutableSet<Class<?>> classesToSkipSources() {
        return ImmutableSet.of(RealMapBinder.class, RealMapBinder.MultimapBinder.class, RealMultibinder.class, RealOptionalBinder.class, ProviderMethodsModule.class);
    }
}

