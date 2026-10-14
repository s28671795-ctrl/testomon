/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.PARAMETER})
public @interface ParameterName {
    @Nonnull
    public String name();
}

