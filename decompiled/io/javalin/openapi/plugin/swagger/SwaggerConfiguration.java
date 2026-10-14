/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.swagger;

import io.javalin.security.RouteRole;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\b\u00a8\u0006 "}, d2={"Lio/javalin/openapi/plugin/swagger/SwaggerConfiguration;", "", "()V", "documentationPath", "", "getDocumentationPath", "()Ljava/lang/String;", "setDocumentationPath", "(Ljava/lang/String;)V", "roles", "", "Lio/javalin/security/RouteRole;", "getRoles", "()[Lio/javalin/security/RouteRole;", "setRoles", "([Lio/javalin/security/RouteRole;)V", "[Lio/javalin/security/RouteRole;", "title", "getTitle", "setTitle", "uiPath", "getUiPath", "setUiPath", "validatorUrl", "getValidatorUrl", "setValidatorUrl", "version", "getVersion", "setVersion", "webJarPath", "getWebJarPath", "setWebJarPath", "javalin-swagger-plugin"})
public final class SwaggerConfiguration {
    @NotNull
    private String title = "OpenApi documentation";
    @NotNull
    private String version = "3.52.5";
    @NotNull
    private String uiPath = "/swagger";
    @NotNull
    private String webJarPath = "/webjars/swagger-ui";
    @NotNull
    private String documentationPath = "/openapi";
    @Nullable
    private String validatorUrl = "https://validator.swagger.io/validator";
    @NotNull
    private RouteRole[] roles;

    public SwaggerConfiguration() {
        boolean $i$f$emptyArray = false;
        this.roles = (RouteRole[])((Object[])new RouteRole[0]);
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.title = string;
    }

    @NotNull
    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.version = string;
    }

    @NotNull
    public final String getUiPath() {
        return this.uiPath;
    }

    public final void setUiPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.uiPath = string;
    }

    @NotNull
    public final String getWebJarPath() {
        return this.webJarPath;
    }

    public final void setWebJarPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.webJarPath = string;
    }

    @NotNull
    public final String getDocumentationPath() {
        return this.documentationPath;
    }

    public final void setDocumentationPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.documentationPath = string;
    }

    @Nullable
    public final String getValidatorUrl() {
        return this.validatorUrl;
    }

    public final void setValidatorUrl(@Nullable String string) {
        this.validatorUrl = string;
    }

    @NotNull
    public final RouteRole[] getRoles() {
        return this.roles;
    }

    public final void setRoles(@NotNull RouteRole[] routeRoleArray) {
        Intrinsics.checkNotNullParameter(routeRoleArray, "<set-?>");
        this.roles = routeRoleArray;
    }
}

