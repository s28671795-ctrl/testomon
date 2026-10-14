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
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006R\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0007\u00a8\u0006\b"}, d2={"Lio/javalin/openapi/OpenApiSecurity;", "", "name", "", "scopes", "", "()Ljava/lang/String;", "()[Ljava/lang/String;", "openapi-specification"})
public @interface OpenApiSecurity {
    public String name();

    public String[] scopes() default {};
}

