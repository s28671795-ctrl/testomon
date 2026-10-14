/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.multibindings.MapKey;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MapKey(unwrapValue=true)
@Documented
@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ClassMapKey {
    public Class<?> value();
}

