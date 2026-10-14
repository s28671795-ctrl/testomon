/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value={ElementType.ANNOTATION_TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MapKey {
    public boolean unwrapValue() default true;
}

