/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.ApiKeyAuth;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\t"}, d2={"Lio/javalin/openapi/CookieAuth;", "Lio/javalin/openapi/ApiKeyAuth;", "name", "", "in", "(Ljava/lang/String;Ljava/lang/String;)V", "getIn", "()Ljava/lang/String;", "getName", "openapi-specification"})
public final class CookieAuth
extends ApiKeyAuth {
    @NotNull
    private final String name;
    @NotNull
    private final String in;

    @JvmOverloads
    public CookieAuth(@NotNull String name, @NotNull String in) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(in, "in");
        super(null, null, 3, null);
        this.name = name;
        this.in = in;
    }

    public /* synthetic */ CookieAuth(String string, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string2 = "cookie";
        }
        this(string, string2);
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    @NotNull
    public String getIn() {
        return this.in;
    }

    @JvmOverloads
    public CookieAuth(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this(name, null, 2, null);
    }
}

