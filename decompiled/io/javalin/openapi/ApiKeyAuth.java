/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.SecurityScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lio/javalin/openapi/ApiKeyAuth;", "Lio/javalin/openapi/SecurityScheme;", "in", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "getIn", "()Ljava/lang/String;", "getName", "type", "getType", "openapi-specification"})
public class ApiKeyAuth
implements SecurityScheme {
    @NotNull
    private final String in;
    @NotNull
    private final String name;
    @NotNull
    private final String type;

    public ApiKeyAuth(@NotNull String in, @NotNull String name) {
        Intrinsics.checkNotNullParameter(in, "in");
        Intrinsics.checkNotNullParameter(name, "name");
        this.in = in;
        this.name = name;
        this.type = "apiKey";
    }

    public /* synthetic */ ApiKeyAuth(String string, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "header";
        }
        if ((n & 2) != 0) {
            string2 = "X-API-Key";
        }
        this(string, string2);
    }

    @NotNull
    public String getIn() {
        return this.in;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }

    public ApiKeyAuth() {
        this(null, null, 3, null);
    }
}

