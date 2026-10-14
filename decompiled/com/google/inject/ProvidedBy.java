/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject;

import jakarta.inject.Provider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface ProvidedBy {
    public Class<? extends Provider<?>> value();
}

