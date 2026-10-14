/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import io.javalin.json.JsonMapper;
import io.javalin.json.JsonMapperKt;
import io.javalin.vue.JavalinVueConfig;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.TypesJVMKt;
import kotlin.text.StringsKt;
import org.eclipse.jetty.util.URIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001J\u0014\u0010\b\u001a\n \t*\u0004\u0018\u00010\u00040\u0004*\u00020\u0004H\u0002\u00a8\u0006\n"}, d2={"Lio/javalin/vue/VueStateRenderer;", "", "()V", "getState", "", "ctx", "Lio/javalin/http/Context;", "state", "urlEncodeForJavascript", "kotlin.jvm.PlatformType", "javalin"})
@SourceDebugExtension(value={"SMAP\nVueStateRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VueStateRenderer.kt\nio/javalin/vue/VueStateRenderer\n+ 2 JsonMapper.kt\nio/javalin/json/JsonMapperKt\n*L\n1#1,32:1\n73#2:33\n*S KotlinDebug\n*F\n+ 1 VueStateRenderer.kt\nio/javalin/vue/VueStateRenderer\n*L\n12#1:33\n*E\n"})
public final class VueStateRenderer {
    @NotNull
    public static final VueStateRenderer INSTANCE = new VueStateRenderer();

    private VueStateRenderer() {
    }

    @NotNull
    public final String getState(@NotNull Context ctx, @Nullable Object state) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JavalinVueConfig cfg = (JavalinVueConfig)ctx.appAttribute("javalin-javalinvue-config");
        String prototypeOrGlobalConfig = cfg.vueAppName != null ? cfg.vueAppName + ".config.globalProperties" : "Vue.prototype";
        return StringsKt.trimIndent("\n            <script nonce=\"@internalAddNonce\">\n                " + prototypeOrGlobalConfig + ".$javalin = JSON.parse(decodeURIComponent('" + VueStateRenderer.getState$urlEncodedState(ctx, cfg, state) + "'))\n            </script>\n        ");
    }

    private final String urlEncodeForJavascript(String $this$urlEncodeForJavascript) {
        return URIUtil.encodePath($this$urlEncodeForJavascript);
    }

    /*
     * WARNING - void declaration
     */
    private static final String getState$urlEncodedState(Context $ctx, JavalinVueConfig cfg, Object state) {
        void obj$iv;
        void $this$toJsonString$iv;
        JsonMapper jsonMapper = JsonMapperKt.jsonMapper($ctx);
        Object object = new Pair[2];
        object[0] = TuplesKt.to("pathParams", $ctx.pathParamMap());
        Object object2 = state;
        if (object2 == null) {
            object2 = cfg.stateFunction.invoke($ctx);
        }
        object[1] = TuplesKt.to("state", object2);
        object = MapsKt.mapOf(object);
        boolean $i$f$toJsonString = false;
        String string = INSTANCE.urlEncodeForJavascript($this$toJsonString$iv.toJsonString(obj$iv, TypesJVMKt.getJavaType(Reflection.typeOf(Map.class, KTypeProjection.Companion.invariant(Reflection.typeOf(String.class)), KTypeProjection.Companion.invariant(Reflection.typeOf(Object.class))))));
        Intrinsics.checkNotNullExpressionValue(string, "ctx.jsonMapper()\n       \u2026.urlEncodeForJavascript()");
        return string;
    }
}

