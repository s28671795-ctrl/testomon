/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.swagger;

import io.javalin.Javalin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerHandler;
import io.javalin.openapi.plugin.swagger.SwaggerWebJarHandler;
import io.javalin.plugin.Plugin;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/openapi/plugin/swagger/SwaggerPlugin;", "Lio/javalin/plugin/Plugin;", "configuration", "Lio/javalin/openapi/plugin/swagger/SwaggerConfiguration;", "(Lio/javalin/openapi/plugin/swagger/SwaggerConfiguration;)V", "apply", "", "app", "Lio/javalin/Javalin;", "javalin-swagger-plugin"})
public class SwaggerPlugin
implements Plugin {
    @NotNull
    private final SwaggerConfiguration configuration;

    @JvmOverloads
    public SwaggerPlugin(@NotNull SwaggerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.configuration = configuration;
    }

    public /* synthetic */ SwaggerPlugin(SwaggerConfiguration swaggerConfiguration, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            swaggerConfiguration = new SwaggerConfiguration();
        }
        this(swaggerConfiguration);
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        SwaggerHandler swaggerHandler = new SwaggerHandler(this.configuration.getTitle(), this.configuration.getDocumentationPath(), this.configuration.getVersion(), this.configuration.getValidatorUrl(), app.cfg.routing.contextPath);
        RouteRole[] routeRoleArray = this.configuration.getRoles();
        Javalin javalin = app.get(this.configuration.getUiPath(), swaggerHandler, Arrays.copyOf(routeRoleArray, routeRoleArray.length));
        routeRoleArray = this.configuration.getRoles();
        javalin.get(this.configuration.getWebJarPath() + "/*", new SwaggerWebJarHandler(this.configuration.getWebJarPath()), Arrays.copyOf(routeRoleArray, routeRoleArray.length));
    }

    @JvmOverloads
    public SwaggerPlugin() {
        this(null, 1, null);
    }
}

