/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0007\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u0002J\u001d\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0016\u00a2\u0006\u0002\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00048gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u000f\u00c0\u0006\u0001"}, d2={"Lio/javalin/openapi/OAuth2Flow;", "I", "", "flowType", "", "getFlowType", "()Ljava/lang/String;", "scopes", "", "getScopes", "()Ljava/util/Map;", "withScope", "scope", "description", "(Ljava/lang/String;Ljava/lang/String;)Lio/javalin/openapi/OAuth2Flow;", "openapi-specification"})
public interface OAuth2Flow<I extends OAuth2Flow<I>> {
    @JsonIgnore
    @NotNull
    public String getFlowType();

    @NotNull
    public Map<String, String> getScopes();

    @NotNull
    default public I withScope(@NotNull String scope, @NotNull String description) {
        OAuth2Flow oAuth2Flow;
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(description, "description");
        OAuth2Flow it = oAuth2Flow = this;
        boolean bl = false;
        this.getScopes().put(scope, description);
        OAuth2Flow oAuth2Flow2 = oAuth2Flow;
        Intrinsics.checkNotNull(oAuth2Flow2, "null cannot be cast to non-null type I of io.javalin.openapi.OAuth2Flow");
        return (I)oAuth2Flow2;
    }
}

