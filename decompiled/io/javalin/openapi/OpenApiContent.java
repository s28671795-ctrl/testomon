/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.NULL_CLASS;
import io.javalin.openapi.OpenApiContentProperty;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;

@kotlin.annotation.Target(allowedTargets={})
@kotlin.annotation.Retention(value=AnnotationRetention.SOURCE)
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={})
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001B<\u0012\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tR\u000f\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u000bR\u0013\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\fR\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u000bR\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\rR\u000f\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lio/javalin/openapi/OpenApiContent;", "", "from", "Lkotlin/reflect/KClass;", "mimeType", "", "type", "format", "properties", "", "Lio/javalin/openapi/OpenApiContentProperty;", "()Ljava/lang/String;", "()Ljava/lang/Class;", "()[Lio/javalin/openapi/OpenApiContentProperty;", "openapi-specification"})
public @interface OpenApiContent {
    public Class<?> from() default NULL_CLASS.class;

    public String mimeType() default "AUTODETECT - Will be replaced later";

    public String type() default "-- This string represents a null value and shouldn't be used --";

    public String format() default "-- This string represents a null value and shouldn't be used --";

    public OpenApiContentProperty[] properties() default {};
}

