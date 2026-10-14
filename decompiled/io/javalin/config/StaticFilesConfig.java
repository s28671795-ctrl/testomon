/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.PrivateConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.staticfiles.StaticFileConfig;
import io.javalin.jetty.JettyResourceHandler;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007J\u0006\u0010\u000e\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lio/javalin/config/StaticFilesConfig;", "", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "add", "", "userConfig", "Ljava/util/function/Consumer;", "Lio/javalin/http/staticfiles/StaticFileConfig;", "directory", "", "location", "Lio/javalin/http/staticfiles/Location;", "enableWebjars", "javalin"})
public final class StaticFilesConfig {
    @NotNull
    private final PrivateConfig pvt;

    public StaticFilesConfig(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
    }

    public final void enableWebjars() {
        this.add(StaticFilesConfig::enableWebjars$lambda$0);
    }

    @JvmOverloads
    public final void add(@NotNull String directory, @NotNull Location location) {
        Intrinsics.checkNotNullParameter(directory, "directory");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        this.add(arg_0 -> StaticFilesConfig.add$lambda$1(directory, location, arg_0));
    }

    public static /* synthetic */ void add$default(StaticFilesConfig staticFilesConfig, String string, Location location, int n, Object object) {
        if ((n & 2) != 0) {
            location = Location.CLASSPATH;
        }
        staticFilesConfig.add(string, location);
    }

    public final void add(@NotNull Consumer<StaticFileConfig> userConfig) {
        Intrinsics.checkNotNullParameter(userConfig, "userConfig");
        if (this.pvt.resourceHandler == null) {
            this.pvt.resourceHandler = new JettyResourceHandler(this.pvt);
        }
        StaticFileConfig finalConfig = new StaticFileConfig(null, null, null, false, null, null, null, null, 255, null);
        userConfig.accept(finalConfig);
        ResourceHandler resourceHandler = this.pvt.resourceHandler;
        Intrinsics.checkNotNull(resourceHandler);
        resourceHandler.addStaticFileConfig(finalConfig);
    }

    @JvmOverloads
    public final void add(@NotNull String directory) {
        Intrinsics.checkNotNullParameter(directory, "directory");
        StaticFilesConfig.add$default(this, directory, null, 2, null);
    }

    private static final void enableWebjars$lambda$0(StaticFileConfig staticFiles) {
        Intrinsics.checkNotNullParameter(staticFiles, "staticFiles");
        staticFiles.directory = "META-INF/resources/webjars";
        staticFiles.headers = MapsKt.mapOf(TuplesKt.to("Cache-Control", "max-age=31622400"));
    }

    private static final void add$lambda$1(String $directory, Location $location, StaticFileConfig staticFiles) {
        Intrinsics.checkNotNullParameter($directory, "$directory");
        Intrinsics.checkNotNullParameter((Object)$location, "$location");
        Intrinsics.checkNotNullParameter(staticFiles, "staticFiles");
        staticFiles.directory = $directory;
        staticFiles.location = $location;
    }
}

