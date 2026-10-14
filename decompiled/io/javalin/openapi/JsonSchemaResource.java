/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c2\u0003J#\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010\u0010\u001a\u00020\u0006J\u0006\u0010\u0011\u001a\u00020\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0015"}, d2={"Lio/javalin/openapi/JsonSchemaResource;", "", "name", "", "content", "Ljava/util/function/Supplier;", "Ljava/io/InputStream;", "(Ljava/lang/String;Ljava/util/function/Supplier;)V", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "getContent", "getContentAsString", "hashCode", "", "toString", "openapi-specification"})
public final class JsonSchemaResource {
    @NotNull
    private final String name;
    @NotNull
    private final Supplier<InputStream> content;

    public JsonSchemaResource(@NotNull String name, @NotNull Supplier<InputStream> content) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(content, "content");
        this.name = name;
        this.content = content;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final InputStream getContent() {
        InputStream inputStream2 = this.content.get();
        Intrinsics.checkNotNullExpressionValue(inputStream2, "content.get()");
        return inputStream2;
    }

    @NotNull
    public final String getContentAsString() {
        InputStream inputStream2 = this.getContent();
        Charset charset = Charsets.UTF_8;
        return TextStreamsKt.readText(new InputStreamReader(inputStream2, charset));
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    private final Supplier<InputStream> component2() {
        return this.content;
    }

    @NotNull
    public final JsonSchemaResource copy(@NotNull String name, @NotNull Supplier<InputStream> content) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(content, "content");
        return new JsonSchemaResource(name, content);
    }

    public static /* synthetic */ JsonSchemaResource copy$default(JsonSchemaResource jsonSchemaResource, String string, Supplier supplier, int n, Object object) {
        if ((n & 1) != 0) {
            string = jsonSchemaResource.name;
        }
        if ((n & 2) != 0) {
            supplier = jsonSchemaResource.content;
        }
        return jsonSchemaResource.copy(string, supplier);
    }

    @NotNull
    public String toString() {
        return "JsonSchemaResource(name=" + this.name + ", content=" + this.content + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.content.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsonSchemaResource)) {
            return false;
        }
        JsonSchemaResource jsonSchemaResource = (JsonSchemaResource)other;
        if (!Intrinsics.areEqual(this.name, jsonSchemaResource.name)) {
            return false;
        }
        return Intrinsics.areEqual(this.content, jsonSchemaResource.content);
    }
}

