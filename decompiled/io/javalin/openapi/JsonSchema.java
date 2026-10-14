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

@Target(allowedTargets={AnnotationTarget.CLASS})
@Retention(value=AnnotationRetention.SOURCE)
@java.lang.annotation.Retention(value=RetentionPolicy.SOURCE)
@java.lang.annotation.Target(value={ElementType.TYPE})
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0014\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005R\u000f\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/openapi/JsonSchema;", "", "generateResource", "", "requireNonNulls", "()Z", "openapi-specification"})
public @interface JsonSchema {
    public boolean generateResource() default true;

    public boolean requireNonNulls() default true;
}

