/*
 * Decompiled with CFR 0.152.
 */
package org.checkerframework.checker.optional.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.checker.optional.qual.Present;
import org.checkerframework.framework.qual.PreconditionAnnotation;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
@PreconditionAnnotation(qualifier=Present.class)
public @interface RequiresPresent {
    public String[] value();

    @Documented
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
    @PreconditionAnnotation(qualifier=Present.class)
    public static @interface List {
        public RequiresPresent[] value();
    }
}

