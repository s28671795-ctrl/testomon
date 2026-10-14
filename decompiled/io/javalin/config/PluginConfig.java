/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginManager;
import io.javalin.plugin.bundled.BasicAuthPlugin;
import io.javalin.plugin.bundled.CorsContainer;
import io.javalin.plugin.bundled.CorsPlugin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.plugin.bundled.DevLoggingPlugin;
import io.javalin.plugin.bundled.GlobalHeaderConfig;
import io.javalin.plugin.bundled.GlobalHeadersPlugin;
import io.javalin.plugin.bundled.HttpAllowedMethodsPlugin;
import io.javalin.plugin.bundled.RedirectToLowercasePathPlugin;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import io.javalin.plugin.bundled.SslRedirectPlugin;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ\u0014\u0010\f\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u0006\u0010\u0010\u001a\u00020\bJ\u0016\u0010\u0011\u001a\u00020\b2\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0013J\u0006\u0010\u0015\u001a\u00020\bJ\u0006\u0010\u0016\u001a\u00020\bJ)\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\n2\u0014\b\u0002\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a\"\u00020\u001b\u00a2\u0006\u0002\u0010\u001cJ\u0006\u0010\u001d\u001a\u00020\bJ\u000e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006!"}, d2={"Lio/javalin/config/PluginConfig;", "", "()V", "pluginManager", "Lio/javalin/plugin/PluginManager;", "getPluginManager", "()Lio/javalin/plugin/PluginManager;", "enableBasicAuth", "", "username", "", "password", "enableCors", "userConfig", "Ljava/util/function/Consumer;", "Lio/javalin/plugin/bundled/CorsContainer;", "enableDevLogging", "enableGlobalHeaders", "globalHeaderConfig", "Ljava/util/function/Supplier;", "Lio/javalin/plugin/bundled/GlobalHeaderConfig;", "enableHttpAllowedMethodsOnRoutes", "enableRedirectToLowercasePaths", "enableRouteOverview", "path", "roles", "", "Lio/javalin/security/RouteRole;", "(Ljava/lang/String;[Lio/javalin/security/RouteRole;)V", "enableSslRedirects", "register", "plugin", "Lio/javalin/plugin/Plugin;", "javalin"})
@SourceDebugExtension(value={"SMAP\nPluginConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PluginConfig.kt\nio/javalin/config/PluginConfig\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,39:1\n1#2:40\n*E\n"})
public final class PluginConfig {
    @NotNull
    private final PluginManager pluginManager = new PluginManager();

    @NotNull
    public final PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public final void register(@NotNull Plugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        this.pluginManager.register(plugin);
    }

    public final void enableHttpAllowedMethodsOnRoutes() {
        this.register(new HttpAllowedMethodsPlugin());
    }

    public final void enableDevLogging() {
        this.register(new DevLoggingPlugin());
    }

    public final void enableGlobalHeaders(@NotNull Supplier<GlobalHeaderConfig> globalHeaderConfig) {
        Intrinsics.checkNotNullParameter(globalHeaderConfig, "globalHeaderConfig");
        GlobalHeaderConfig globalHeaderConfig2 = globalHeaderConfig.get();
        Intrinsics.checkNotNull(globalHeaderConfig2);
        this.register(new GlobalHeadersPlugin(globalHeaderConfig2));
    }

    public final void enableRouteOverview(@NotNull String path, RouteRole ... roles) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(roles, "roles");
        this.register(new RouteOverviewPlugin(path, Arrays.copyOf(roles, roles.length)));
    }

    public static /* synthetic */ void enableRouteOverview$default(PluginConfig pluginConfig, String string, RouteRole[] routeRoleArray, int n, Object object) {
        if ((n & 2) != 0) {
            routeRoleArray = new RouteRole[]{};
        }
        pluginConfig.enableRouteOverview(string, routeRoleArray);
    }

    public final void enableRedirectToLowercasePaths() {
        this.register(new RedirectToLowercasePathPlugin());
    }

    public final void enableBasicAuth(@NotNull String username, @NotNull String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        this.register(new BasicAuthPlugin(username, password));
    }

    public final void enableSslRedirects() {
        this.register(new SslRedirectPlugin(false, null, 3, null));
    }

    public final void enableCors(@NotNull Consumer<CorsContainer> userConfig) {
        CorsContainer corsContainer;
        Intrinsics.checkNotNullParameter(userConfig, "userConfig");
        CorsContainer it = corsContainer = new CorsContainer();
        boolean bl = false;
        userConfig.accept(it);
        List<Consumer<CorsPluginConfig>> corsConfigs = corsContainer.corsConfigs();
        this.register(new CorsPlugin(corsConfigs));
    }
}

