/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.SecurityScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, d2={"Lio/javalin/openapi/OpenID;", "Lio/javalin/openapi/SecurityScheme;", "openIdConnectUrl", "", "(Ljava/lang/String;)V", "getOpenIdConnectUrl", "()Ljava/lang/String;", "type", "getType", "openapi-specification"})
public final class OpenID
implements SecurityScheme {
    @NotNull
    private final String openIdConnectUrl;
    @NotNull
    private final String type;

    public OpenID(@NotNull String openIdConnectUrl) {
        Intrinsics.checkNotNullParameter(openIdConnectUrl, "openIdConnectUrl");
        this.openIdConnectUrl = openIdConnectUrl;
        this.type = "openIdConnect";
    }

    @NotNull
    public final String getOpenIdConnectUrl() {
        return this.openIdConnectUrl;
    }

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }
}

