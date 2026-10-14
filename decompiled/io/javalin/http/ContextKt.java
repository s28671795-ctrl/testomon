/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.Context;
import io.javalin.validation.BodyValidator;
import io.javalin.validation.Validator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.TypesJVMKt;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000$\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u001a\u001e\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b\u00a2\u0006\u0002\u0010\u0004\u001a\u001e\u0010\u0005\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b\u00a2\u0006\u0002\u0010\u0004\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b\u001a'\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b\u001a'\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH\u0086\b\u001a&\u0010\u000e\u001a\u00020\u0003\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u000f\u001a\u0002H\u0001H\u0086\b\u00a2\u0006\u0002\u0010\u0010\u001a'\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b\u001a'\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b\u00a8\u0006\u0013"}, d2={"bodyAsClass", "T", "", "Lio/javalin/http/Context;", "(Lio/javalin/http/Context;)Ljava/lang/Object;", "bodyStreamAsClass", "bodyValidator", "Lio/javalin/validation/BodyValidator;", "formParamAsClass", "Lio/javalin/validation/Validator;", "key", "", "headerAsClass", "header", "jsonAsType", "obj", "(Lio/javalin/http/Context;Ljava/lang/Object;)Lio/javalin/http/Context;", "pathParamAsClass", "queryParamAsClass", "javalin"})
public final class ContextKt {
    public static final /* synthetic */ <T> Context jsonAsType(Context $this$jsonAsType, T obj) {
        Intrinsics.checkNotNullParameter($this$jsonAsType, "<this>");
        Intrinsics.checkNotNullParameter(obj, "obj");
        boolean $i$f$jsonAsType = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$jsonAsType.json(obj, TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> T bodyAsClass(Context $this$bodyAsClass) {
        Intrinsics.checkNotNullParameter($this$bodyAsClass, "<this>");
        boolean $i$f$bodyAsClass = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$bodyAsClass.bodyAsClass(TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> T bodyStreamAsClass(Context $this$bodyStreamAsClass) {
        Intrinsics.checkNotNullParameter($this$bodyStreamAsClass, "<this>");
        boolean $i$f$bodyStreamAsClass = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$bodyStreamAsClass.bodyStreamAsClass(TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> BodyValidator<T> bodyValidator(Context $this$bodyValidator) {
        Intrinsics.checkNotNullParameter($this$bodyValidator, "<this>");
        boolean $i$f$bodyValidator = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return $this$bodyValidator.bodyValidator(Object.class);
    }

    public static final /* synthetic */ <T> Validator<T> pathParamAsClass(Context $this$pathParamAsClass, String key) {
        Intrinsics.checkNotNullParameter($this$pathParamAsClass, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        boolean $i$f$pathParamAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return $this$pathParamAsClass.pathParamAsClass(key, Object.class);
    }

    public static final /* synthetic */ <T> Validator<T> headerAsClass(Context $this$headerAsClass, String header) {
        Intrinsics.checkNotNullParameter($this$headerAsClass, "<this>");
        Intrinsics.checkNotNullParameter(header, "header");
        boolean $i$f$headerAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return $this$headerAsClass.headerAsClass(header, Object.class);
    }

    public static final /* synthetic */ <T> Validator<T> queryParamAsClass(Context $this$queryParamAsClass, String key) {
        Intrinsics.checkNotNullParameter($this$queryParamAsClass, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        boolean $i$f$queryParamAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return $this$queryParamAsClass.queryParamAsClass(key, Object.class);
    }

    public static final /* synthetic */ <T> Validator<T> formParamAsClass(Context $this$formParamAsClass, String key) {
        Intrinsics.checkNotNullParameter($this$formParamAsClass, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        boolean $i$f$formParamAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return $this$formParamAsClass.formParamAsClass(key, Object.class);
    }
}

