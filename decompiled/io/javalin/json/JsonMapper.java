/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J'\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ'\u0010\t\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\u001c\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u0016\u00c0\u0006\u0001"}, d2={"Lio/javalin/json/JsonMapper;", "", "fromJsonStream", "T", "json", "Ljava/io/InputStream;", "targetType", "Ljava/lang/reflect/Type;", "(Ljava/io/InputStream;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "fromJsonString", "", "(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "toJsonStream", "obj", "type", "toJsonString", "writeToOutputStream", "", "stream", "Ljava/util/stream/Stream;", "outputStream", "Ljava/io/OutputStream;", "javalin"})
public interface JsonMapper {
    @NotNull
    default public String toJsonString(@NotNull Object obj, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        throw new NotImplementedError("JsonMapper#toJsonString not implemented");
    }

    @NotNull
    default public InputStream toJsonStream(@NotNull Object obj, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        throw new NotImplementedError("JsonMapper#toJsonStream not implemented");
    }

    default public void writeToOutputStream(@NotNull Stream<?> stream, @NotNull OutputStream outputStream2) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        Intrinsics.checkNotNullParameter(outputStream2, "outputStream");
        throw new NotImplementedError("JsonMapper#writeToOutputStream not implemented");
    }

    @NotNull
    default public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        throw new NotImplementedError("JsonMapper#fromJsonString not implemented");
    }

    @NotNull
    default public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Type targetType) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        throw new NotImplementedError("JsonMapper#fromJsonStream not implemented");
    }
}

