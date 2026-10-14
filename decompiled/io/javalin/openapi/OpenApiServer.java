/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.OpenApiServerVariable;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u000eR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR&\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2={"Lio/javalin/openapi/OpenApiServer;", "", "()V", "description", "", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "url", "getUrl", "setUrl", "variables", "", "Lio/javalin/openapi/OpenApiServerVariable;", "getVariables", "()Ljava/util/Map;", "setVariables", "(Ljava/util/Map;)V", "addVariable", "", "key", "variable", "openapi-specification"})
public final class OpenApiServer {
    @NotNull
    private String url = "";
    @Nullable
    private String description;
    @NotNull
    private Map<String, OpenApiServerVariable> variables = new LinkedHashMap();

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.url = string;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@Nullable String string) {
        this.description = string;
    }

    @NotNull
    public final Map<String, OpenApiServerVariable> getVariables() {
        return this.variables;
    }

    public final void setVariables(@NotNull Map<String, OpenApiServerVariable> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.variables = map;
    }

    public final void addVariable(@NotNull String key, @NotNull OpenApiServerVariable variable) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.variables.put(key, variable);
    }
}

