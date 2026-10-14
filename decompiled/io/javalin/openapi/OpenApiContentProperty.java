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
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0087\u0002\u0018\u00002\u00020\u0001B$\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003R\u000f\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\tR\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\bR\u000f\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\b\u00a8\u0006\n"}, d2={"Lio/javalin/openapi/OpenApiContentProperty;", "", "name", "", "isArray", "", "type", "format", "()Ljava/lang/String;", "()Z", "openapi-specification"})
public @interface OpenApiContentProperty {
    public String name();

    public boolean isArray() default false;

    public String type();

    public String format() default "-- This string represents a null value and shouldn't be used --";
}

