/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.redoc;

import io.javalin.Javalin;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocHandler;
import io.javalin.openapi.plugin.redoc.ReDocWebJarHandler;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginLifecycleInit;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/javalin/openapi/plugin/redoc/ReDocPlugin;", "Lio/javalin/plugin/Plugin;", "Lio/javalin/plugin/PluginLifecycleInit;", "configuration", "Lio/javalin/openapi/plugin/redoc/ReDocConfiguration;", "(Lio/javalin/openapi/plugin/redoc/ReDocConfiguration;)V", "apply", "", "app", "Lio/javalin/Javalin;", "init", "javalin-redoc-plugin"})
public class ReDocPlugin
implements Plugin,
PluginLifecycleInit {
    @NotNull
    private final ReDocConfiguration configuration;

    @JvmOverloads
    public ReDocPlugin(@NotNull ReDocConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.configuration = configuration;
    }

    public /* synthetic */ ReDocPlugin(ReDocConfiguration reDocConfiguration, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            reDocConfiguration = new ReDocConfiguration();
        }
        this(reDocConfiguration);
    }

    @Override
    public void init(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        ReDocHandler reDocHandler = new ReDocHandler(this.configuration.getTitle(), this.configuration.getDocumentationPath(), this.configuration.getVersion(), app.cfg.routing.contextPath, this.configuration.getBasePath());
        ReDocWebJarHandler webJarHandler = new ReDocWebJarHandler(this.configuration.getWebJarPath());
        RouteRole[] routeRoleArray = this.configuration.getRoles();
        Javalin javalin = app.get(this.configuration.getUiPath(), reDocHandler, Arrays.copyOf(routeRoleArray, routeRoleArray.length));
        routeRoleArray = this.configuration.getRoles();
        javalin.get(this.configuration.getWebJarPath() + "/*", webJarHandler, Arrays.copyOf(routeRoleArray, routeRoleArray.length));
    }

    @JvmOverloads
    public ReDocPlugin() {
        this(null, 1, null);
    }
}

