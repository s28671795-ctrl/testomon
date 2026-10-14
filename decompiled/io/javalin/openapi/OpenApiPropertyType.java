/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets={AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
@Retention(value=AnnotationRetention.SOURCE)
@java.lang.annotation.Retention(value=RetentionPolicy.SOURCE)
@java.lang.annotation.Target(value={ElementType.TYPE, ElementType.METHOD})
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B\f\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003R\u0013\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lio/javalin/openapi/OpenApiPropertyType;", "", "definedBy", "Lkotlin/reflect/KClass;", "()Ljava/lang/Class;", "openapi-specification"})
public @interface OpenApiPropertyType {
    public Class<?> definedBy();
}

