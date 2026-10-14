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

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B-\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t\u00a8\u0006\u000f"}, d2={"Lio/javalin/openapi/AuthorizationCodeFlow;", "Lio/javalin/openapi/OAuth2Flow;", "authorizationUrl", "", "tokenUrl", "scopes", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;)V", "getAuthorizationUrl", "()Ljava/lang/String;", "flowType", "getFlowType", "getScopes", "()Ljava/util/Map;", "getTokenUrl", "openapi-specification"})
public final class AuthorizationCodeFlow
implements OAuth2Flow<AuthorizationCodeFlow> {
    @NotNull
    private final String authorizationUrl;
    @NotNull
    private final String tokenUrl;
    @NotNull
    private final Map<String, String> scopes;
    @NotNull
    private final String flowType;

    @JvmOverloads
    public AuthorizationCodeFlow(@NotNull String authorizationUrl, @NotNull String tokenUrl, @NotNull Map<String, String> scopes) {
        Intrinsics.checkNotNullParameter(authorizationUrl, "authorizationUrl");
        Intrinsics.checkNotNullParameter(tokenUrl, "tokenUrl");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.scopes = scopes;
        this.flowType = "authorizationCode";
    }

    public /* synthetic */ AuthorizationCodeFlow(String string, String string2, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            map = new LinkedHashMap();
        }
        this(string, string2, map);
    }

    @NotNull
    public final String getAuthorizationUrl() {
        return this.authorizationUrl;
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
    public AuthorizationCodeFlow(@NotNull String authorizationUrl, @NotNull String tokenUrl) {
        Intrinsics.checkNotNullParameter(authorizationUrl, "authorizationUrl");
        Intrinsics.checkNotNullParameter(tokenUrl, "tokenUrl");
        this(authorizationUrl, tokenUrl, null, 4, null);
    }
}

