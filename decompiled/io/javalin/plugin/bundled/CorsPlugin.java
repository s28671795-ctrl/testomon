/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.plugin.bundled.CorsUtils;
import io.javalin.plugin.bundled.OriginParts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0003H\u0002R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0018"}, d2={"Lio/javalin/plugin/bundled/CorsPlugin;", "Lio/javalin/plugin/Plugin;", "userConfigs", "", "Ljava/util/function/Consumer;", "Lio/javalin/plugin/bundled/CorsPluginConfig;", "(Ljava/util/List;)V", "configs", "getConfigs", "()Ljava/util/List;", "apply", "", "app", "Lio/javalin/Javalin;", "applySingleConfig", "cfg", "handleCors", "ctx", "Lio/javalin/http/Context;", "matchOrigin", "", "clientOrigin", "", "origins", "javalin"})
@SourceDebugExtension(value={"SMAP\nCorsPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CorsPlugin.kt\nio/javalin/plugin/bundled/CorsPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,163:1\n1549#2:164\n1620#2,2:165\n1622#2:168\n1855#2,2:169\n1549#2:171\n1620#2,3:172\n1747#2,3:175\n1#3:167\n*S KotlinDebug\n*F\n+ 1 CorsPlugin.kt\nio/javalin/plugin/bundled/CorsPlugin\n*L\n74#1:164\n74#1:165,2\n74#1:168\n78#1:169,2\n146#1:171\n146#1:172,3\n148#1:175,3\n*E\n"})
public final class CorsPlugin
implements Plugin {
    @NotNull
    private final List<CorsPluginConfig> configs;

    /*
     * WARNING - void declaration
     */
    public CorsPlugin(@NotNull List<? extends Consumer<CorsPluginConfig>> userConfigs) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(userConfigs, "userConfigs");
        if (!(!((Collection)userConfigs).isEmpty())) {
            boolean $i$a$-require-CorsPlugin$22 = false;
            String $i$a$-require-CorsPlugin$22 = "At least one cors config has to be provided. Use CorsContainer.add() to add one.";
            throw new IllegalArgumentException($i$a$-require-CorsPlugin$22.toString());
        }
        Iterable iterable = userConfigs;
        CorsPlugin corsPlugin = this;
        boolean $i$f$map = false;
        void var4_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void userConfig;
            CorsPluginConfig corsPluginConfig;
            Consumer consumer = (Consumer)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            CorsPluginConfig it = corsPluginConfig = new CorsPluginConfig(false, false, null, null, 0, null, null, 127, null);
            boolean bl2 = false;
            userConfig.accept(it);
            collection.add(corsPluginConfig);
        }
        corsPlugin.configs = (List)destination$iv$iv;
    }

    @NotNull
    public final List<CorsPluginConfig> getConfigs() {
        return this.configs;
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        Iterable $this$forEach$iv = this.configs;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CorsPluginConfig it = (CorsPluginConfig)element$iv;
            boolean bl = false;
            this.applySingleConfig(app, it);
        }
    }

    private final void applySingleConfig(Javalin app, CorsPluginConfig cfg) {
        List<String> origins = cfg.allowedOrigins();
        if (!(!((Collection)origins).isEmpty() || cfg.reflectClientOrigin)) {
            boolean $i$a$-require-CorsPlugin$applySingleConfig$32 = false;
            String $i$a$-require-CorsPlugin$applySingleConfig$32 = "Origins cannot be empty if `reflectClientOrigin` is false.";
            throw new IllegalArgumentException($i$a$-require-CorsPlugin$applySingleConfig$32.toString());
        }
        if (!(origins.isEmpty() || !cfg.reflectClientOrigin)) {
            boolean bl = false;
            String string = "Cannot set `allowedOrigins` if `reflectClientOrigin` is true";
            throw new IllegalArgumentException(string.toString());
        }
        app.before(cfg.path, arg_0 -> CorsPlugin.applySingleConfig$lambda$6(this, cfg, arg_0));
        HttpStatus[] httpStatusArray = new HttpStatus[]{HttpStatus.NOT_FOUND, HttpStatus.METHOD_NOT_ALLOWED};
        List<HttpStatus> validOptionStatusCodes = CollectionsKt.listOf(httpStatusArray);
        app.after(cfg.path, arg_0 -> CorsPlugin.applySingleConfig$lambda$7(validOptionStatusCodes, arg_0));
    }

    private final void handleCors(Context ctx, CorsPluginConfig cfg) {
        List<String> headersToExpose;
        String string;
        String string2 = ctx.header("Origin");
        if (string2 == null) {
            return;
        }
        String clientOrigin = string2;
        if (!CorsUtils.INSTANCE.isValidOrigin$javalin(clientOrigin)) {
            return;
        }
        if (ctx.method() == HandlerType.OPTIONS) {
            String headerValue;
            String string3;
            boolean requestedHeader = false;
            String string4 = ctx.header("Access-Control-Request-Headers");
            if (string4 != null) {
                headerValue = string3 = string4;
                boolean bl = false;
                ctx.header("Access-Control-Allow-Headers", headerValue);
                requestedHeader = true;
            }
            String string5 = ctx.header("Access-Control-Request-Method");
            if (string5 != null) {
                headerValue = string3 = string5;
                boolean bl = false;
                ctx.header("Access-Control-Allow-Methods", headerValue);
                requestedHeader = true;
            }
            if (requestedHeader && cfg.maxAge >= 0) {
                ctx.header("Access-Control-Max-Age", String.valueOf(cfg.maxAge));
            }
        }
        List<String> origins = cfg.allowedOrigins();
        if (origins.contains("*")) {
            string = "*";
        } else {
            if (Intrinsics.areEqual(clientOrigin, "null")) {
                return;
            }
            if (cfg.reflectClientOrigin) {
                string = clientOrigin;
            } else if (this.matchOrigin(clientOrigin, origins)) {
                string = clientOrigin;
            } else {
                ctx.status(HttpStatus.BAD_REQUEST);
                return;
            }
        }
        String allowOriginValue = string;
        ctx.header("Access-Control-Allow-Origin", allowOriginValue);
        ctx.header("Vary", "Origin");
        if (cfg.allowCredentials) {
            ctx.header("Access-Control-Allow-Credentials", "true");
        }
        if (!((Collection)(headersToExpose = cfg.headersToExpose())).isEmpty()) {
            ctx.header("Access-Control-Expose-Headers", CollectionsKt.joinToString$default(headersToExpose, ",", null, null, 0, null, null, 62, null));
        }
    }

    private final boolean matchOrigin(String clientOrigin, List<String> origins) {
        boolean bl;
        block5: {
            String p0;
            Collection collection;
            Iterable $this$mapTo$iv$iv;
            Iterable $this$map$iv;
            OriginParts clientOriginPart = CorsUtils.INSTANCE.parseAsOriginParts$javalin(CorsUtils.INSTANCE.normalizeOrigin$javalin(clientOrigin));
            Iterable iterable = origins;
            CorsUtils corsUtils = CorsUtils.INSTANCE;
            boolean $i$f$map = false;
            void var8_9 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                String string = (String)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl2 = false;
                collection.add(corsUtils.normalizeOrigin$javalin(p0));
            }
            $this$map$iv = (List)destination$iv$iv;
            corsUtils = CorsUtils.INSTANCE;
            $i$f$map = false;
            $this$mapTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                p0 = (String)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl3 = false;
                collection.add(corsUtils.parseAsOriginParts$javalin(p0));
            }
            List serverOriginParts = (List)destination$iv$iv;
            Iterable $this$any$iv = serverOriginParts;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    OriginParts it = (OriginParts)element$iv;
                    boolean bl4 = false;
                    if (!CorsUtils.INSTANCE.originsMatch$javalin(clientOriginPart, it)) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        return bl;
    }

    private static final void applySingleConfig$lambda$6(CorsPlugin this$0, CorsPluginConfig $cfg, Context ctx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($cfg, "$cfg");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this$0.handleCors(ctx, $cfg);
    }

    private static final void applySingleConfig$lambda$7(List $validOptionStatusCodes, Context ctx) {
        Intrinsics.checkNotNullParameter($validOptionStatusCodes, "$validOptionStatusCodes");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (ctx.method() == HandlerType.OPTIONS && $validOptionStatusCodes.contains((Object)ctx.status())) {
            ctx.result("").status(200);
        }
    }
}

