/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.ApiStatus;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Deprecated
@Internal
@ApiStatus.Internal
public @interface Internal {
}

