/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE, ElementType.TYPE})
@Documented
public @interface Internal {
}

