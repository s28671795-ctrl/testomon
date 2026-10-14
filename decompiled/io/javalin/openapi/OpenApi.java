/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApiParam;
import io.javalin.openapi.OpenApiRequestBody;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiSecurity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;

@kotlin.annotation.Target(allowedTargets={AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION})
@kotlin.annotation.Retention(value=AnnotationRetention.SOURCE)
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0087\u0002\u0018\u00002\u00020\u0001B\u00d2\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\n\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\n\u0012\u0006\u0010\u0017\u001a\u00020\u0005\u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\nR\u0015\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u001aR\u000f\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u001bR\u000f\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u001cR\u0015\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u001aR\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u001aR\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u001bR\u0015\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\n\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u001dR\u000f\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u001cR\u000f\u0010\u0017\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u001cR\u0015\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u001aR\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u001aR\u000f\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u001eR\u0015\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\n\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u001fR\u0015\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\n\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010 R\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u001cR\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u00a2\u0006\u0006\u001a\u0004\b\t\u0010!\u00a8\u0006\""}, d2={"Lio/javalin/openapi/OpenApi;", "", "ignore", "", "summary", "", "description", "operationId", "deprecated", "tags", "", "cookies", "Lio/javalin/openapi/OpenApiParam;", "headers", "pathParams", "queryParams", "formParams", "requestBody", "Lio/javalin/openapi/OpenApiRequestBody;", "responses", "Lio/javalin/openapi/OpenApiResponse;", "security", "Lio/javalin/openapi/OpenApiSecurity;", "path", "methods", "Lio/javalin/openapi/HttpMethod;", "()[Lio/javalin/openapi/OpenApiParam;", "()Z", "()Ljava/lang/String;", "()[Lio/javalin/openapi/HttpMethod;", "()Lio/javalin/openapi/OpenApiRequestBody;", "()[Lio/javalin/openapi/OpenApiResponse;", "()[Lio/javalin/openapi/OpenApiSecurity;", "()[Ljava/lang/String;", "openapi-specification"})
public @interface OpenApi {
    public boolean ignore() default false;

    public String summary() default "-- This string represents a null value and shouldn't be used --";

    public String description() default "-- This string represents a null value and shouldn't be used --";

    public String operationId() default "-- This string represents a null value and shouldn't be used --";

    public boolean deprecated() default false;

    public String[] tags() default {};

    public OpenApiParam[] cookies() default {};

    public OpenApiParam[] headers() default {};

    public OpenApiParam[] pathParams() default {};

    public OpenApiParam[] queryParams() default {};

    public OpenApiParam[] formParams() default {};

    public OpenApiRequestBody requestBody() default @OpenApiRequestBody(content={});

    public OpenApiResponse[] responses() default {};

    public OpenApiSecurity[] security() default {};

    public String path();

    public HttpMethod[] methods();
}

