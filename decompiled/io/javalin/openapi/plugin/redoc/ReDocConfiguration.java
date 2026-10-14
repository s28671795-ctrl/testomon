/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.redoc;

import io.javalin.security.RouteRole;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\"\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\b\u00a8\u0006 "}, d2={"Lio/javalin/openapi/plugin/redoc/ReDocConfiguration;", "", "()V", "basePath", "", "getBasePath", "()Ljava/lang/String;", "setBasePath", "(Ljava/lang/String;)V", "documentationPath", "getDocumentationPath", "setDocumentationPath", "roles", "", "Lio/javalin/security/RouteRole;", "getRoles", "()[Lio/javalin/security/RouteRole;", "setRoles", "([Lio/javalin/security/RouteRole;)V", "[Lio/javalin/security/RouteRole;", "title", "getTitle", "setTitle", "uiPath", "getUiPath", "setUiPath", "version", "getVersion", "setVersion", "webJarPath", "getWebJarPath", "setWebJarPath", "javalin-redoc-plugin"})
@SourceDebugExtension(value={"SMAP\nReDocPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReDocPlugin.kt\nio/javalin/openapi/plugin/redoc/ReDocConfiguration\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,47:1\n26#2:48\n*S KotlinDebug\n*F\n+ 1 ReDocPlugin.kt\nio/javalin/openapi/plugin/redoc/ReDocConfiguration\n*L\n14#1:48\n*E\n"})
public final class ReDocConfiguration {
    @NotNull
    private String title = "OpenApi documentation";
    @NotNull
    private String uiPath = "/redoc";
    @NotNull
    private RouteRole[] roles;
    @NotNull
    private String documentationPath;
    @Nullable
    private String basePath;
    @NotNull
    private String version;
    @NotNull
    private String webJarPath;

    public ReDocConfiguration() {
        boolean $i$f$emptyArray = false;
        this.roles = new RouteRole[0];
        this.documentationPath = "/openapi";
        this.version = "2.0.0-rc.70";
        this.webJarPath = "/webjars/redoc";
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
    public final String getUiPath() {
        return this.uiPath;
    }

    public final void setUiPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.uiPath = string;
    }

    @NotNull
    public final RouteRole[] getRoles() {
        return this.roles;
    }

    public final void setRoles(@NotNull RouteRole[] routeRoleArray) {
        Intrinsics.checkNotNullParameter(routeRoleArray, "<set-?>");
        this.roles = routeRoleArray;
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
    public final String getBasePath() {
        return this.basePath;
    }

    public final void setBasePath(@Nullable String string) {
        this.basePath = string;
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
    public final String getWebJarPath() {
        return this.webJarPath;
    }

    public final void setWebJarPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.webJarPath = string;
    }
}

