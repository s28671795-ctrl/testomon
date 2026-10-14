/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\b\u00a8\u0006\u000f"}, d2={"Lio/javalin/openapi/OpenApiLicense;", "", "()V", "identifier", "", "getIdentifier", "()Ljava/lang/String;", "setIdentifier", "(Ljava/lang/String;)V", "name", "getName", "setName", "url", "getUrl", "setUrl", "openapi-specification"})
public final class OpenApiLicense {
    @NotNull
    private String name = "";
    @Nullable
    private String identifier;
    @Nullable
    private String url;

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.name = string;
    }

    @Nullable
    public final String getIdentifier() {
        return this.identifier;
    }

    public final void setIdentifier(@Nullable String string) {
        this.identifier = string;
    }

    @Nullable
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@Nullable String string) {
        this.url = string;
    }
}

