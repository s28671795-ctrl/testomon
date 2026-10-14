/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.vue.JavalinVueConfig;
import io.javalin.vue.VuePathMaster;
import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0019\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0018J(\u0010\u0019\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00042\b\b\u0002\u0010\"\u001a\u00020#2\f\b\u0002\u0010$\u001a\u0006\u0012\u0002\b\u00030%H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R,\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00068@@@X\u0080\u000e\u00a2\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00060\u000f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R&\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00128@@@X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R*\u0010\u0019\u001a\u0004\u0018\u00010\u00182\b\u0010\u0007\u001a\u0004\u0018\u00010\u00188@@@X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lio/javalin/vue/JavalinVueConfig;", "", "()V", "cacheControl", "", "enableCspAndNonces", "", "<set-?>", "isDev", "isDev$javalin", "()Ljava/lang/Boolean;", "setDev$javalin", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "isDevFunction", "Lkotlin/Function1;", "Lio/javalin/http/Context;", "optimizeDependencies", "Lio/javalin/vue/VuePathMaster;", "pathMaster", "getPathMaster$javalin", "()Lio/javalin/vue/VuePathMaster;", "setPathMaster$javalin", "(Lio/javalin/vue/VuePathMaster;)V", "Ljava/nio/file/Path;", "rootDirectory", "getRootDirectory$javalin", "()Ljava/nio/file/Path;", "setRootDirectory$javalin", "(Ljava/nio/file/Path;)V", "stateFunction", "vueAppName", "", "path", "location", "Lio/javalin/http/staticfiles/Location;", "resourcesJarClass", "Ljava/lang/Class;", "javalin"})
public final class JavalinVueConfig {
    @NotNull
    private VuePathMaster pathMaster = new VuePathMaster(this);
    @Nullable
    private Path rootDirectory;
    @JvmField
    @Nullable
    public String vueAppName;
    @Nullable
    private Boolean isDev;
    @JvmField
    @NotNull
    public Function1<? super Context, Boolean> isDevFunction = isDevFunction.1.INSTANCE;
    @JvmField
    public boolean optimizeDependencies = true;
    @JvmField
    @NotNull
    public Function1<? super Context, ? extends Object> stateFunction = stateFunction.1.INSTANCE;
    @JvmField
    @NotNull
    public String cacheControl = "no-cache, no-store, must-revalidate";
    @JvmField
    public boolean enableCspAndNonces;

    public final /* synthetic */ VuePathMaster getPathMaster$javalin() {
        return this.pathMaster;
    }

    public final /* synthetic */ void setPathMaster$javalin(VuePathMaster vuePathMaster) {
        Intrinsics.checkNotNullParameter(vuePathMaster, "<set-?>");
        this.pathMaster = vuePathMaster;
    }

    public final /* synthetic */ Path getRootDirectory$javalin() {
        return this.rootDirectory;
    }

    public final /* synthetic */ void setRootDirectory$javalin(Path path) {
        this.rootDirectory = path;
    }

    public final void rootDirectory(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.rootDirectory = path;
    }

    @JvmOverloads
    public final void rootDirectory(@NotNull String path, @NotNull Location location, @NotNull Class<?> resourcesJarClass) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        Intrinsics.checkNotNullParameter(resourcesJarClass, "resourcesJarClass");
        this.rootDirectory = location == Location.CLASSPATH ? this.pathMaster.classpathPath(path, resourcesJarClass) : Paths.get(path, new String[0]);
    }

    public static /* synthetic */ void rootDirectory$default(JavalinVueConfig javalinVueConfig, String string, Location location, Class clazz, int n, Object object) {
        if ((n & 2) != 0) {
            location = Location.CLASSPATH;
        }
        if ((n & 4) != 0) {
            clazz = VuePathMaster.class;
        }
        javalinVueConfig.rootDirectory(string, location, clazz);
    }

    public final /* synthetic */ Boolean isDev$javalin() {
        return this.isDev;
    }

    public final /* synthetic */ void setDev$javalin(Boolean bl) {
        this.isDev = bl;
    }

    @JvmOverloads
    public final void rootDirectory(@NotNull String path, @NotNull Location location) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        JavalinVueConfig.rootDirectory$default(this, path, location, null, 4, null);
    }

    @JvmOverloads
    public final void rootDirectory(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        JavalinVueConfig.rootDirectory$default(this, path, null, null, 6, null);
    }
}

