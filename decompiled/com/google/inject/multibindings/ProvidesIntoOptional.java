/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.errorprone.annotations.Keep;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Keep
public @interface ProvidesIntoOptional {
    public Type value();

    public static enum Type {
        ACTUAL,
        DEFAULT;

    }
}

