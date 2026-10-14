/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JsonMapper;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.TypesJVMKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a&\u0010\u0002\u001a\u0002H\u0003\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0086\b\u00a2\u0006\u0002\u0010\b\u001a&\u0010\t\u001a\u0002H\u0003\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001H\u0086\b\u00a2\u0006\u0002\u0010\n\u001a\n\u0010\u000b\u001a\u00020\u0005*\u00020\f\u001a\n\u0010\u000b\u001a\u00020\u0005*\u00020\r\u001a&\u0010\u000e\u001a\u00020\u0007\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004*\u00020\u00052\u0006\u0010\u000f\u001a\u0002H\u0003H\u0086\b\u00a2\u0006\u0002\u0010\u0010\u001a&\u0010\u0011\u001a\u00020\u0001\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004*\u00020\u00052\u0006\u0010\u000f\u001a\u0002H\u0003H\u0086\b\u00a2\u0006\u0002\u0010\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"JSON_MAPPER_KEY", "", "fromJsonStream", "T", "", "Lio/javalin/json/JsonMapper;", "json", "Ljava/io/InputStream;", "(Lio/javalin/json/JsonMapper;Ljava/io/InputStream;)Ljava/lang/Object;", "fromJsonString", "(Lio/javalin/json/JsonMapper;Ljava/lang/String;)Ljava/lang/Object;", "jsonMapper", "Lio/javalin/Javalin;", "Lio/javalin/http/Context;", "toJsonStream", "obj", "(Lio/javalin/json/JsonMapper;Ljava/lang/Object;)Ljava/io/InputStream;", "toJsonString", "(Lio/javalin/json/JsonMapper;Ljava/lang/Object;)Ljava/lang/String;", "javalin"})
public final class JsonMapperKt {
    @NotNull
    public static final String JSON_MAPPER_KEY = "javalin-json-mapper";

    public static final /* synthetic */ <T> String toJsonString(JsonMapper $this$toJsonString, T obj) {
        Intrinsics.checkNotNullParameter($this$toJsonString, "<this>");
        Intrinsics.checkNotNullParameter(obj, "obj");
        boolean $i$f$toJsonString = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$toJsonString.toJsonString(obj, TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> InputStream toJsonStream(JsonMapper $this$toJsonStream, T obj) {
        Intrinsics.checkNotNullParameter($this$toJsonStream, "<this>");
        Intrinsics.checkNotNullParameter(obj, "obj");
        boolean $i$f$toJsonStream = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$toJsonStream.toJsonStream(obj, TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> T fromJsonString(JsonMapper $this$fromJsonString, String json) {
        Intrinsics.checkNotNullParameter($this$fromJsonString, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        boolean $i$f$fromJsonString = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$fromJsonString.fromJsonString(json, TypesJVMKt.getJavaType(null));
    }

    public static final /* synthetic */ <T> T fromJsonStream(JsonMapper $this$fromJsonStream, InputStream json) {
        Intrinsics.checkNotNullParameter($this$fromJsonStream, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        boolean $i$f$fromJsonStream = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return $this$fromJsonStream.fromJsonStream(json, TypesJVMKt.getJavaType(null));
    }

    @NotNull
    public static final JsonMapper jsonMapper(@NotNull Javalin $this$jsonMapper) {
        Intrinsics.checkNotNullParameter($this$jsonMapper, "<this>");
        Object t2 = $this$jsonMapper.attribute(JSON_MAPPER_KEY);
        Intrinsics.checkNotNullExpressionValue(t2, "this.attribute(JSON_MAPPER_KEY)");
        return (JsonMapper)t2;
    }

    @NotNull
    public static final JsonMapper jsonMapper(@NotNull Context $this$jsonMapper) {
        Intrinsics.checkNotNullParameter($this$jsonMapper, "<this>");
        return (JsonMapper)$this$jsonMapper.appAttribute(JSON_MAPPER_KEY);
    }
}

