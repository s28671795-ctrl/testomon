/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.vue.JavalinVueConfig;
import io.javalin.vue.VueDependencyResolver;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0014\u0010\u001d\u001a\u00020\u00142\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0002J\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\fR\u001b\u0010\u0005\u001a\u00020\u00068@X\u0080\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8@X\u0080\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\n\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lio/javalin/vue/VuePathMaster;", "", "cfg", "Lio/javalin/vue/JavalinVueConfig;", "(Lio/javalin/vue/JavalinVueConfig;)V", "cachedDependencyResolver", "Lio/javalin/vue/VueDependencyResolver;", "getCachedDependencyResolver$javalin", "()Lio/javalin/vue/VueDependencyResolver;", "cachedDependencyResolver$delegate", "Lkotlin/Lazy;", "cachedPaths", "", "Ljava/nio/file/Path;", "getCachedPaths$javalin", "()Ljava/util/Set;", "cachedPaths$delegate", "getCfg", "()Lio/javalin/vue/JavalinVueConfig;", "fileSystem", "Ljava/nio/file/FileSystem;", "classpathPath", "path", "", "jarClass", "Ljava/lang/Class;", "defaultLocation", "isDev", "", "getFileSystem", "walkPaths", "javalin"})
@SourceDebugExtension(value={"SMAP\nVuePathMaster.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VuePathMaster.kt\nio/javalin/vue/VuePathMaster\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,47:1\n1#2:48\n*E\n"})
public final class VuePathMaster {
    @NotNull
    private final JavalinVueConfig cfg;
    @NotNull
    private final Lazy cachedPaths$delegate;
    @NotNull
    private final Lazy cachedDependencyResolver$delegate;
    private FileSystem fileSystem;

    public VuePathMaster(@NotNull JavalinVueConfig cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        this.cfg = cfg;
        this.cachedPaths$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Set<? extends Path>>(this){
            final /* synthetic */ VuePathMaster this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Set<Path> invoke() {
                return this.this$0.walkPaths();
            }
        }, 1, null);
        this.cachedDependencyResolver$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<VueDependencyResolver>(this){
            final /* synthetic */ VuePathMaster this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final VueDependencyResolver invoke() {
                return new VueDependencyResolver(this.this$0.getCachedPaths$javalin(), this.this$0.getCfg().vueAppName);
            }
        }, 1, null);
    }

    @NotNull
    public final JavalinVueConfig getCfg() {
        return this.cfg;
    }

    @NotNull
    public final Set<Path> getCachedPaths$javalin() {
        Lazy lazy = this.cachedPaths$delegate;
        return (Set)lazy.getValue();
    }

    @NotNull
    public final VueDependencyResolver getCachedDependencyResolver$javalin() {
        Lazy lazy = this.cachedDependencyResolver$delegate;
        return (VueDependencyResolver)lazy.getValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public final Set<Path> walkPaths() {
        Set<Path> set;
        AutoCloseable autoCloseable = Files.walk(this.cfg.getRootDirectory$javalin(), 20, new FileVisitOption[0]);
        Throwable throwable = null;
        try {
            Stream it = (Stream)autoCloseable;
            boolean bl = false;
            Set<Path> set2 = it.collect(Collectors.toSet());
            set = set2;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally(autoCloseable, throwable);
        }
        Intrinsics.checkNotNullExpressionValue(set, "walk(cfg.rootDirectory, \u2026ect(Collectors.toSet()) }");
        return set;
    }

    @NotNull
    public final Path classpathPath(@NotNull String path, @NotNull Class<?> jarClass) {
        Path path2;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(jarClass, "jarClass");
        URL uRL = jarClass.getResource(path);
        Intrinsics.checkNotNull(uRL);
        if (Intrinsics.areEqual(uRL.toURI().getScheme(), "jar")) {
            Path path3 = this.getFileSystem(jarClass).getPath(path, new String[0]);
            path2 = path3;
            Intrinsics.checkNotNullExpressionValue(path3, "getFileSystem(jarClass).getPath(path)");
        } else {
            URL uRL2 = jarClass.getResource(path);
            Intrinsics.checkNotNull(uRL2);
            Path path4 = Paths.get(uRL2.toURI());
            path2 = path4;
            Intrinsics.checkNotNullExpressionValue(path4, "get(jarClass.getResource(path)!!.toURI())");
        }
        return path2;
    }

    @NotNull
    public final Path defaultLocation(boolean isDev) {
        Path path;
        if (isDev) {
            Path path2 = Paths.get("src/main/resources/vue", new String[0]);
            path = path2;
            Intrinsics.checkNotNullExpressionValue(path2, "get(\"src/main/resources/vue\")");
        } else {
            path = this.classpathPath("/vue", VuePathMaster.class);
        }
        return path;
    }

    private final FileSystem getFileSystem(Class<?> jarClass) {
        FileSystem fileSystem;
        if (this.fileSystem == null) {
            try {
                URL uRL = jarClass.getResource("");
                Intrinsics.checkNotNull(uRL);
                FileSystem fileSystem2 = FileSystems.getFileSystem(uRL.toURI());
                Intrinsics.checkNotNullExpressionValue(fileSystem2, "getFileSystem(jarClass.getResource(\"\")!!.toURI())");
                this.fileSystem = fileSystem2;
            }
            catch (FileSystemNotFoundException e) {
                URL uRL = jarClass.getResource("");
                Intrinsics.checkNotNull(uRL);
                FileSystem fileSystem3 = FileSystems.newFileSystem(uRL.toURI(), MapsKt.emptyMap());
                Intrinsics.checkNotNullExpressionValue(fileSystem3, "newFileSystem(jarClass.g\u2026 emptyMap<String, Any>())");
                this.fileSystem = fileSystem3;
            }
        }
        if ((fileSystem = this.fileSystem) == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileSystem");
            fileSystem = null;
        }
        return fileSystem;
    }
}

