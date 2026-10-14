/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.OAuth2Flow;
import io.javalin.openapi.SecurityScheme;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\u000e\u001a\u00020\u00002\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\t\u00a8\u0006\u0010"}, d2={"Lio/javalin/openapi/OAuth2;", "Lio/javalin/openapi/SecurityScheme;", "description", "", "flows", "", "Lio/javalin/openapi/OAuth2Flow;", "(Ljava/lang/String;Ljava/util/Map;)V", "getDescription", "()Ljava/lang/String;", "getFlows", "()Ljava/util/Map;", "type", "getType", "withFlow", "flow", "openapi-specification"})
public final class OAuth2
implements SecurityScheme {
    @NotNull
    private final String description;
    @NotNull
    private final Map<String, OAuth2Flow<?>> flows;
    @NotNull
    private final String type;

    @JvmOverloads
    public OAuth2(@NotNull String description, @NotNull Map<String, OAuth2Flow<?>> flows) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(flows, "flows");
        this.description = description;
        this.flows = flows;
        this.type = "oauth2";
    }

    public /* synthetic */ OAuth2(String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            map = new LinkedHashMap();
        }
        this(string, map);
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final Map<String, OAuth2Flow<?>> getFlows() {
        return this.flows;
    }

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }

    @NotNull
    public final OAuth2 withFlow(@NotNull OAuth2Flow<?> flow) {
        OAuth2 oAuth2;
        Intrinsics.checkNotNullParameter(flow, "flow");
        OAuth2 it = oAuth2 = this;
        boolean bl = false;
        this.flows.put(flow.getFlowType(), flow);
        return oAuth2;
    }

    @JvmOverloads
    public OAuth2(@NotNull String description) {
        Intrinsics.checkNotNullParameter(description, "description");
        this(description, null, 2, null);
    }
}

