/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.vue.JavalinVueConfig;
import io.javalin.vue.VueDependencyResolver;
import io.javalin.vue.VueFileInliner;
import io.javalin.vue.VueHandlerKt;
import io.javalin.vue.VuePathMaster;
import io.javalin.vue.VueStateRenderer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0014\u0010\u000e\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010\u00b2\u0006\n\u0010\u0011\u001a\u00020\u0012X\u008a\u0084\u0002"}, d2={"Lio/javalin/vue/VueHandler;", "Lio/javalin/http/Handler;", "componentId", "", "(Ljava/lang/String;)V", "handle", "", "ctx", "Lio/javalin/http/Context;", "postRender", "layout", "preRender", "state", "", "postRenderHook", "preRenderHook", "javalin", "resolver", "Lio/javalin/vue/VueDependencyResolver;"})
@SourceDebugExtension(value={"SMAP\nVueHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VueHandler.kt\nio/javalin/vue/VueHandler\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n643#2,5:62\n1#3:67\n819#4:68\n847#4,2:69\n*S KotlinDebug\n*F\n+ 1 VueHandler.kt\nio/javalin/vue/VueHandler\n*L\n26#1:62,5\n32#1:68\n32#1:69,2\n*E\n"})
public abstract class VueHandler
implements Handler {
    @NotNull
    private final String componentId;

    public VueHandler(@NotNull String componentId) {
        Intrinsics.checkNotNullParameter(componentId, "componentId");
        this.componentId = componentId;
    }

    @Nullable
    public Object state(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return null;
    }

    @NotNull
    public String preRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return layout;
    }

    @NotNull
    public String postRender(@NotNull String layout, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return layout;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull Context ctx) {
        void $this$filterNotTo$iv$iv;
        void $this$filterNot$iv;
        Object var21_29;
        Object v6;
        Context context;
        VueHandler vueHandler;
        VueFileInliner vueFileInliner;
        Object object;
        Iterable $i$f$takeWhile2;
        String dependencies;
        Set<Path> allFiles;
        String routeComponent;
        JavalinVueConfig c;
        block7: {
            String string;
            Lazy resolver$delegate;
            block6: {
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                c = (JavalinVueConfig)ctx.appAttribute("javalin-javalinvue-config");
                Boolean bl = c.isDev$javalin();
                if (bl == null) {
                    bl = c.isDevFunction.invoke(ctx);
                }
                c.setDev$javalin(bl);
                Path path = c.getRootDirectory$javalin();
                if (path == null) {
                    VuePathMaster vuePathMaster = c.getPathMaster$javalin();
                    Boolean bl2 = c.isDev$javalin();
                    Intrinsics.checkNotNull(bl2);
                    path = vuePathMaster.defaultLocation(bl2);
                }
                c.setRootDirectory$javalin(path);
                routeComponent = StringsKt.startsWith$default(this.componentId, "<", false, 2, null) ? this.componentId : "<" + this.componentId + "></" + this.componentId + ">";
                allFiles = Intrinsics.areEqual(c.isDev$javalin(), true) ? c.getPathMaster$javalin().walkPaths() : c.getPathMaster$javalin().getCachedPaths$javalin();
                resolver$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<VueDependencyResolver>(c, allFiles){
                    final /* synthetic */ JavalinVueConfig $c;
                    final /* synthetic */ Set<Path> $allFiles;
                    {
                        this.$c = $c;
                        this.$allFiles = $allFiles;
                        super(0);
                    }

                    @NotNull
                    public final VueDependencyResolver invoke() {
                        return Intrinsics.areEqual(this.$c.isDev$javalin(), true) ? new VueDependencyResolver(this.$allFiles, this.$c.vueAppName) : this.$c.getPathMaster$javalin().getCachedDependencyResolver$javalin();
                    }
                }, 1, null);
                String $this$takeWhile$iv = StringsKt.removePrefix(routeComponent, (CharSequence)"<");
                boolean $i$f$takeWhile2 = false;
                int n = $this$takeWhile$iv.length();
                for (int index$iv = 0; index$iv < n; ++index$iv) {
                    char it22 = $this$takeWhile$iv.charAt(index$iv);
                    int n2 = 0;
                    Character[] characterArray = new Character[]{Character.valueOf('>'), Character.valueOf(' ')};
                    if (!SetsKt.setOf(characterArray).contains(Character.valueOf(it22))) continue;
                    String it22 = $this$takeWhile$iv;
                    n2 = 0;
                    String string2 = it22.substring(n2, index$iv);
                    string = string2;
                    Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    break block6;
                }
                string = $this$takeWhile$iv;
            }
            String componentId = string;
            dependencies = c.optimizeDependencies ? VueHandler.handle$lambda$0(resolver$delegate).resolve(componentId) : VueHandlerKt.access$joinVueFiles(allFiles);
            Intrinsics.checkNotNullExpressionValue(dependencies, "dependencies");
            if (!StringsKt.contains$default((CharSequence)dependencies, componentId, false, 2, null)) {
                throw new InternalServerErrorResponse("Route component not found: " + routeComponent, null, 2, null);
            }
            $i$f$takeWhile2 = allFiles;
            object = this;
            vueFileInliner = VueFileInliner.INSTANCE;
            vueHandler = this;
            context = ctx;
            Iterable index$iv = $i$f$takeWhile2;
            for (Object it22 : index$iv) {
                Path it = (Path)it22;
                boolean bl = false;
                if (!it.endsWith("vue/layout.html")) continue;
                v6 = it22;
                break block7;
            }
            v6 = null;
        }
        Object v7 = var21_29 = v6;
        Intrinsics.checkNotNull(v7);
        String string = ((VueHandler)object).preRenderHook(VueHandlerKt.readText(v7), ctx);
        Boolean bl = c.isDev$javalin();
        Intrinsics.checkNotNull(bl);
        $i$f$takeWhile2 = allFiles;
        boolean bl3 = bl;
        object = string;
        boolean $i$f$filterNot = false;
        Iterator iterator2 = $this$filterNot$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            Path it = (Path)element$iv$iv;
            boolean bl4 = false;
            if (VueHandlerKt.isVueFile(it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List list = (List)destination$iv$iv;
        context.html(vueHandler.postRenderHook(VueHandlerKt.access$insertNoncesAndCspHeader(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(vueFileInliner.inlineFiles((String)object, bl3, list), "@componentRegistration", "@loadableData@componentRegistration@serverState", false, 4, null), "@loadableData", "\n\n<script nonce=\"@internalAddNonce\">\n    class LoadableData {\n        constructor(url, cache = true, errorCallback = null) {\n            this._url = url;\n            this._errorCallback = errorCallback;\n            this.refresh(cache);\n            this.addRefreshListener();\n        }\n        refresh(cache = true) {\n            this.data = null;\n            this.loading = true;\n            this.loaded = false;\n            this.loadError = null;\n            let cacheKey = \"LoadableData:\" + this._url;\n            if (cache) {\n                this.data = JSON.parse(localStorage.getItem(cacheKey)) || null;\n                this.loaded = this.data !== null;\n                this.loading = this.loaded === false;\n            }\n            fetch(this._url).then(res => {\n                if (res.ok) return res.json();\n                throw JSON.stringify({code: res.status, text: res.statusText});\n            }).then(data => {\n                this.data = data;\n                this.loaded = true;\n                if (cache) {\n                    localStorage.setItem(cacheKey, JSON.stringify(data));\n                }\n            }).catch(error => {\n                this.loadError = JSON.parse(error);\n                if (this._errorCallback !== null) { // should probably handle in UI\n                    this._errorCallback(error);\n                }\n            }).finally(() => this.loading = false);\n        }\n        refreshAll() {\n            LoadableData.refreshAll(this._url);\n        }\n        static refreshAll(url) {\n            window.dispatchEvent(new CustomEvent(\"javalinvue-loadable-data-update\", {detail: url}));\n        }\n        addRefreshListener() {\n            window.addEventListener(\"javalinvue-loadable-data-update\", e => {\n                if (this._url === e.detail) {\n                    this.refresh(false);\n                }\n            }, false);\n        }\n    }\n</script>", false, 4, null), "@componentRegistration", dependencies, false, 4, null), "@serverState", VueStateRenderer.INSTANCE.getState(ctx, this.state(ctx)), false, 4, null), "@routeComponent", routeComponent, false, 4, null), "@cdnWebjar/", Intrinsics.areEqual(c.isDev$javalin(), true) ? "/webjars/" : "https://cdn.jsdelivr.net/webjars/org.webjars.npm/", false, 4, null), c.enableCspAndNonces, ctx), ctx)).header("Cache-Control", c.cacheControl);
    }

    private final String preRenderHook(String $this$preRenderHook, Context ctx) {
        return this.preRender($this$preRenderHook, ctx);
    }

    private final String postRenderHook(String $this$postRenderHook, Context ctx) {
        return this.postRender($this$postRenderHook, ctx);
    }

    private static final VueDependencyResolver handle$lambda$0(Lazy<? extends VueDependencyResolver> $resolver$delegate) {
        Lazy<? extends VueDependencyResolver> lazy = $resolver$delegate;
        return lazy.getValue();
    }
}

