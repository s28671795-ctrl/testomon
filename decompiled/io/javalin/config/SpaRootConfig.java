/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.PrivateConfig;
import io.javalin.http.Handler;
import io.javalin.http.staticfiles.Location;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lio/javalin/config/SpaRootConfig;", "", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "addFile", "", "hostedPath", "", "filePath", "location", "Lio/javalin/http/staticfiles/Location;", "addHandler", "customHandler", "Lio/javalin/http/Handler;", "javalin"})
public final class SpaRootConfig {
    @NotNull
    private final PrivateConfig pvt;

    public SpaRootConfig(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
    }

    @JvmOverloads
    public final void addFile(@NotNull String hostedPath, @NotNull String filePath, @NotNull Location location) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        this.pvt.singlePageHandler.add(hostedPath, filePath, location);
    }

    public static /* synthetic */ void addFile$default(SpaRootConfig spaRootConfig, String string, String string2, Location location, int n, Object object) {
        if ((n & 4) != 0) {
            location = Location.CLASSPATH;
        }
        spaRootConfig.addFile(string, string2, location);
    }

    public final void addHandler(@NotNull String hostedPath, @NotNull Handler customHandler) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(customHandler, "customHandler");
        this.pvt.singlePageHandler.add(hostedPath, customHandler);
    }

    @JvmOverloads
    public final void addFile(@NotNull String hostedPath, @NotNull String filePath) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        SpaRootConfig.addFile$default(this, hostedPath, filePath, null, 4, null);
    }
}

