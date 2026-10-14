/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface RestrictedBindingSource {
    public String explanation();

    public Class<? extends Annotation>[] permits();

    public String exemptModules() default "";

    public RestrictionLevel restrictionLevel() default RestrictionLevel.ERROR;

    public static enum RestrictionLevel {
        WARNING,
        ERROR;

    }

    @Documented
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.ANNOTATION_TYPE})
    public static @interface Permit {
    }
}

