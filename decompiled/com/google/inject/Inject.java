/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject;

import com.google.errorprone.annotations.Keep;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Keep
public @interface Inject {
    public boolean optional() default false;
}

