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

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lio/javalin/openapi/ImplicitFlow;", "Lio/javalin/openapi/OAuth2Flow;", "authorizationUrl", "", "scopes", "", "(Ljava/lang/String;Ljava/util/Map;)V", "getAuthorizationUrl", "()Ljava/lang/String;", "flowType", "getFlowType", "getScopes", "()Ljava/util/Map;", "openapi-specification"})
public final class ImplicitFlow
implements OAuth2Flow<ImplicitFlow> {
    @NotNull
    private final String authorizationUrl;
    @NotNull
    private final Map<String, String> scopes;
    @NotNull
    private final String flowType;

    @JvmOverloads
    public ImplicitFlow(@NotNull String authorizationUrl, @NotNull Map<String, String> scopes) {
        Intrinsics.checkNotNullParameter(authorizationUrl, "authorizationUrl");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        this.authorizationUrl = authorizationUrl;
        this.scopes = scopes;
        this.flowType = "implicit";
    }

    public /* synthetic */ ImplicitFlow(String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            map = new LinkedHashMap();
        }
        this(string, map);
    }

    @NotNull
    public final String getAuthorizationUrl() {
        return this.authorizationUrl;
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
    public ImplicitFlow(@NotNull String authorizationUrl) {
        Intrinsics.checkNotNullParameter(authorizationUrl, "authorizationUrl");
        this(authorizationUrl, null, 2, null);
    }
}

