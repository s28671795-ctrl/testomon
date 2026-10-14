/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets={})
@Retention(value=AnnotationRetention.SOURCE)
@java.lang.annotation.Retention(value=RetentionPolicy.SOURCE)
@java.lang.annotation.Target(value={})
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0087\u0002\u0018\u00002\u00020\u0001BR\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\b\u0002\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003R\u000f\u0010\n\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\rR\u000f\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\rR\u000f\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u000eR\u000f\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000f\u0010\u000b\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u000eR\u000f\u0010\t\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\rR\u0013\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lio/javalin/openapi/OpenApiParam;", "", "name", "", "type", "Lkotlin/reflect/KClass;", "description", "deprecated", "", "required", "allowEmptyValue", "isRepeatable", "example", "()Z", "()Ljava/lang/String;", "()Ljava/lang/Class;", "openapi-specification"})
public @interface OpenApiParam {
    public String name();

    public Class<?> type() default String.class;

    public String description() default "-- This string represents a null value and shouldn't be used --";

    public boolean deprecated() default false;

    public boolean required() default false;

    public boolean allowEmptyValue() default false;

    public boolean isRepeatable() default false;

    public String example() default "";
}

