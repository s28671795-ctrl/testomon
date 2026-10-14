/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.OAuth2Flow;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\r"}, d2={"Lio/javalin/openapi/PasswordFlow;", "Lio/javalin/openapi/OAuth2Flow;", "tokenUrl", "", "scopes", "", "(Ljava/lang/String;Ljava/util/Map;)V", "flowType", "getFlowType", "()Ljava/lang/String;", "getScopes", "()Ljava/util/Map;", "getTokenUrl", "openapi-specification"})
public final class PasswordFlow
implements OAuth2Flow<PasswordFlow> {
    @NotNull
    private final String tokenUrl;
    @NotNull
    private final Map<String, String> scopes;
    @NotNull
    private final String flowType;

    @JvmOverloads
    public PasswordFlow(@NotNull String tokenUrl, @NotNull Map<String, String> scopes) {
        Intrinsics.checkNotNullParameter(tokenUrl, "tokenUrl");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        this.tokenUrl = tokenUrl;
        this.scopes = scopes;
        this.flowType = "password";
    }

    public /* synthetic */ PasswordFlow(String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            map = new LinkedHashMap();
        }
        this(string, map);
    }

    @NotNull
    public final String getTokenUrl() {
        return this.tokenUrl;
    }

    @Override
    @NotNull
    public Map<String, String> getScopes() {
        return this.scopes;
    }

    @Override
    @NotNull
    public String getFlowType() {
        return this.flowType;
    }

    @JvmOverloads
    public PasswordFlow(@NotNull String tokenUrl) {
        Intrinsics.checkNotNullParameter(tokenUrl, "tokenUrl");
        this(tokenUrl, null, 2, null);
    }
}

