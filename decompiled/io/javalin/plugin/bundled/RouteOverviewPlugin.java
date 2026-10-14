/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginLifecycleInit;
import io.javalin.plugin.bundled.RouteOverviewRenderer;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0014\b\u0002\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2={"Lio/javalin/plugin/bundled/RouteOverviewPlugin;", "Lio/javalin/plugin/Plugin;", "Lio/javalin/plugin/PluginLifecycleInit;", "path", "", "roles", "", "Lio/javalin/security/RouteRole;", "(Ljava/lang/String;[Lio/javalin/security/RouteRole;)V", "getPath", "()Ljava/lang/String;", "renderer", "Lio/javalin/plugin/bundled/RouteOverviewRenderer;", "getRoles", "()[Lio/javalin/security/RouteRole;", "[Lio/javalin/security/RouteRole;", "apply", "", "app", "Lio/javalin/Javalin;", "init", "javalin"})
public final class RouteOverviewPlugin
implements Plugin,
PluginLifecycleInit {
    @NotNull
    private final String path;
    @NotNull
    private final RouteRole[] roles;
    private RouteOverviewRenderer renderer;

    public RouteOverviewPlugin(@NotNull String path, RouteRole ... roles) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(roles, "roles");
        this.path = path;
        this.roles = roles;
    }

    public /* synthetic */ RouteOverviewPlugin(String string, RouteRole[] routeRoleArray, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            routeRoleArray = new RouteRole[]{};
        }
        this(string, routeRoleArray);
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    @NotNull
    public final RouteRole[] getRoles() {
        return this.roles;
    }

    @Override
    public void init(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.renderer = new RouteOverviewRenderer(app);
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        RouteOverviewRenderer routeOverviewRenderer = this.renderer;
        if (routeOverviewRenderer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("renderer");
            routeOverviewRenderer = null;
        }
        RouteRole[] routeRoleArray = this.roles;
        app.get(this.path, routeOverviewRenderer, Arrays.copyOf(routeRoleArray, routeRoleArray.length));
    }
}

