/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.rendering;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.util.JavalinLogger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J!\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0011\"\u00020\u0007H\u0007\u00a2\u0006\u0002\u0010\u0012J\r\u0010\u0013\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u0015J)\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\n2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0011\"\u00020\u0007H\u0007\u00a2\u0006\u0002\u0010\u0018J.\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00072\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u0005H\u0007R,\u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00020\u0005\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00060\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\u00020\u0007*\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001e"}, d2={"Lio/javalin/rendering/JavalinRenderer;", "", "()V", "baseModelFunction", "Lkotlin/Function1;", "Lio/javalin/http/Context;", "", "", "extensions", "", "Lio/javalin/rendering/FileRenderer;", "extension", "getExtension", "(Ljava/lang/String;)Ljava/lang/String;", "hasRenderer", "", "ext", "", "([Ljava/lang/String;)Z", "loadFileRenderers", "", "loadFileRenderers$javalin", "register", "fileRenderer", "(Lio/javalin/rendering/FileRenderer;[Ljava/lang/String;)V", "renderBasedOnExtension", "filePath", "model", "ctx", "FileRendererLoader", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinRenderer.kt\nio/javalin/rendering/JavalinRenderer\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,55:1\n13579#2,2:56\n12744#2,2:58\n1855#3,2:60\n*S KotlinDebug\n*F\n+ 1 JavalinRenderer.kt\nio/javalin/rendering/JavalinRenderer\n*L\n37#1:56,2\n45#1:58,2\n49#1:60,2\n*E\n"})
public final class JavalinRenderer {
    @NotNull
    public static final JavalinRenderer INSTANCE = new JavalinRenderer();
    @NotNull
    private static final Map<String, FileRenderer> extensions = new LinkedHashMap();
    @JvmField
    @NotNull
    public static Function1<? super Context, ? extends Map<String, ? extends Object>> baseModelFunction = baseModelFunction.1.INSTANCE;

    private JavalinRenderer() {
    }

    @JvmStatic
    @NotNull
    public static final String renderBasedOnExtension(@NotNull String filePath, @NotNull Map<String, ? extends Object> model, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        FileRenderer fileRenderer = extensions.get(INSTANCE.getExtension(filePath));
        if (fileRenderer == null) {
            throw new IllegalArgumentException("No Renderer registered for extension '" + INSTANCE.getExtension(filePath) + "'.");
        }
        FileRenderer renderer = fileRenderer;
        return renderer.render(filePath, MapsKt.plus(baseModelFunction.invoke(ctx), model), ctx);
    }

    @JvmStatic
    public static final void register(@NotNull FileRenderer fileRenderer, String ... ext) {
        Intrinsics.checkNotNullParameter(fileRenderer, "fileRenderer");
        Intrinsics.checkNotNullParameter(ext, "ext");
        String[] $this$forEach$iv = ext;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            String element$iv;
            String it = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (extensions.get(it) != null) {
                FileRenderer fileRenderer2 = extensions.get(it);
                Intrinsics.checkNotNull(fileRenderer2);
                JavalinLogger.info$default("'" + it + "' is already registered to " + fileRenderer2.getClass() + ". Overriding.", null, 2, null);
            }
            extensions.put(it, fileRenderer);
        }
    }

    @JvmStatic
    public static final boolean hasRenderer(String ... ext) {
        boolean bl;
        block1: {
            Intrinsics.checkNotNullParameter(ext, "ext");
            String[] $this$any$iv = ext;
            boolean $i$f$any = false;
            int n = $this$any$iv.length;
            for (int i = 0; i < n; ++i) {
                String element$iv;
                String it = element$iv = $this$any$iv[i];
                boolean bl2 = false;
                if (!extensions.keySet().contains(it)) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        return bl;
    }

    private final String getExtension(String $this$extension) {
        return StringsKt.replaceBeforeLast$default($this$extension, ".", "", null, 4, null);
    }

    public final void loadFileRenderers$javalin() {
        ServiceLoader<FileRendererLoader> serviceLoader = ServiceLoader.load(FileRendererLoader.class);
        Intrinsics.checkNotNullExpressionValue(serviceLoader, "load(FileRendererLoader::class.java)");
        Iterable $this$forEach$iv = serviceLoader;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            FileRendererLoader it = (FileRendererLoader)element$iv;
            boolean bl = false;
            it.load();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u0004\u00c0\u0006\u0001"}, d2={"Lio/javalin/rendering/JavalinRenderer$FileRendererLoader;", "", "load", "", "javalin"})
    public static interface FileRendererLoader {
        public void load();
    }
}

