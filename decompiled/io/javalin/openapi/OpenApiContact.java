/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\b\u00a8\u0006\u000f"}, d2={"Lio/javalin/openapi/OpenApiContact;", "", "()V", "email", "", "getEmail", "()Ljava/lang/String;", "setEmail", "(Ljava/lang/String;)V", "name", "getName", "setName", "url", "getUrl", "setUrl", "openapi-specification"})
public final class OpenApiContact {
    @Nullable
    private String name;
    @Nullable
    private String url;
    @Nullable
    private String email;

    @Nullable
    public final String getName() {
        return this.name;
    }

    public final void setName(@Nullable String string) {
        this.name = string;
    }

    @Nullable
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@Nullable String string) {
        this.url = string;
    }

    @Nullable
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(@Nullable String string) {
        this.email = string;
    }
}

