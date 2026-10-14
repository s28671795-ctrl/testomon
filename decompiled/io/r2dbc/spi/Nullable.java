/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

@Target(value={ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Nonnull(when=When.MAYBE)
@TypeQualifierNickname
@interface Nullable {
}

