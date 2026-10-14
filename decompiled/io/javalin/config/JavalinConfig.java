/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.Javalin;
import io.javalin.config.CompressionConfig;
import io.javalin.config.ContextResolverConfig;
import io.javalin.config.HttpConfig;
import io.javalin.config.JavalinConfig;
import io.javalin.config.JettyConfig;
import io.javalin.config.PluginConfig;
import io.javalin.config.PrivateConfig;
import io.javalin.config.RequestLoggerConfig;
import io.javalin.config.RoutingConfig;
import io.javalin.config.SpaRootConfig;
import io.javalin.config.StaticFilesConfig;
import io.javalin.json.JsonMapper;
import io.javalin.rendering.FileRenderer;
import io.javalin.security.AccessManager;
import io.javalin.validation.JavalinValidation;
import io.javalin.vue.JavalinVueConfig;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u00188\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u001a8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lio/javalin/config/JavalinConfig;", "", "()V", "compression", "Lio/javalin/config/CompressionConfig;", "contextResolver", "Lio/javalin/config/ContextResolverConfig;", "http", "Lio/javalin/config/HttpConfig;", "jetty", "Lio/javalin/config/JettyConfig;", "plugins", "Lio/javalin/config/PluginConfig;", "pvt", "Lio/javalin/config/PrivateConfig;", "requestLogger", "Lio/javalin/config/RequestLoggerConfig;", "routing", "Lio/javalin/config/RoutingConfig;", "showJavalinBanner", "", "spaRoot", "Lio/javalin/config/SpaRootConfig;", "staticFiles", "Lio/javalin/config/StaticFilesConfig;", "vue", "Lio/javalin/vue/JavalinVueConfig;", "accessManager", "", "Lio/javalin/security/AccessManager;", "fileRenderer", "Lio/javalin/rendering/FileRenderer;", "jsonMapper", "Lio/javalin/json/JsonMapper;", "Companion", "javalin"})
public final class JavalinConfig {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @JvmField
    @NotNull
    public final PrivateConfig pvt = new PrivateConfig();
    @JvmField
    @NotNull
    public final HttpConfig http = new HttpConfig();
    @JvmField
    @NotNull
    public final RoutingConfig routing = new RoutingConfig();
    @JvmField
    @NotNull
    public final JettyConfig jetty = new JettyConfig(this.pvt);
    @JvmField
    @NotNull
    public final StaticFilesConfig staticFiles = new StaticFilesConfig(this.pvt);
    @JvmField
    @NotNull
    public final SpaRootConfig spaRoot = new SpaRootConfig(this.pvt);
    @JvmField
    @NotNull
    public final CompressionConfig compression = new CompressionConfig(this.pvt);
    @JvmField
    @NotNull
    public final RequestLoggerConfig requestLogger = new RequestLoggerConfig(this.pvt);
    @JvmField
    @NotNull
    public final PluginConfig plugins = new PluginConfig();
    @JvmField
    @NotNull
    public final JavalinVueConfig vue = new JavalinVueConfig();
    @JvmField
    @NotNull
    public final ContextResolverConfig contextResolver = new ContextResolverConfig();
    @JvmField
    public boolean showJavalinBanner = true;

    public final void accessManager(@NotNull AccessManager accessManager) {
        Intrinsics.checkNotNullParameter(accessManager, "accessManager");
        this.pvt.accessManager = accessManager;
    }

    public final void jsonMapper(@NotNull JsonMapper jsonMapper) {
        Intrinsics.checkNotNullParameter(jsonMapper, "jsonMapper");
        this.pvt.appAttributes.put("javalin-json-mapper", jsonMapper);
    }

    public final void fileRenderer(@NotNull FileRenderer fileRenderer) {
        Intrinsics.checkNotNullParameter(fileRenderer, "fileRenderer");
        this.pvt.appAttributes.put("javalin-file-renderer", fileRenderer);
    }

    @JvmStatic
    public static final void applyUserConfig(@NotNull Javalin app, @NotNull JavalinConfig cfg, @NotNull Consumer<JavalinConfig> userConfig) {
        Companion.applyUserConfig(app, cfg, userConfig);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nH\u0007\u00a8\u0006\u000b"}, d2={"Lio/javalin/config/JavalinConfig$Companion;", "", "()V", "applyUserConfig", "", "app", "Lio/javalin/Javalin;", "cfg", "Lio/javalin/config/JavalinConfig;", "userConfig", "Ljava/util/function/Consumer;", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        public final void applyUserConfig(@NotNull Javalin app, @NotNull JavalinConfig cfg, @NotNull Consumer<JavalinConfig> userConfig) {
            Intrinsics.checkNotNullParameter(app, "app");
            Intrinsics.checkNotNullParameter(cfg, "cfg");
            Intrinsics.checkNotNullParameter(userConfig, "userConfig");
            JavalinValidation.addValidationExceptionMapper(app);
            userConfig.accept(cfg);
            cfg.plugins.getPluginManager().initializePlugins(app);
            cfg.pvt.appAttributes.computeIfAbsent("javalin-json-mapper", arg_0 -> Companion.applyUserConfig$lambda$0(applyUserConfig.1.INSTANCE, arg_0));
            cfg.pvt.appAttributes.computeIfAbsent("javalin-file-renderer", arg_0 -> Companion.applyUserConfig$lambda$1(applyUserConfig.2.INSTANCE, arg_0));
            cfg.pvt.appAttributes.computeIfAbsent("javalin-context-resolver", arg_0 -> Companion.applyUserConfig$lambda$2(new Function1<String, Object>(cfg){
                final /* synthetic */ JavalinConfig $cfg;
                {
                    this.$cfg = $cfg;
                    super(1);
                }

                @NotNull
                public final Object invoke(@NotNull String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.$cfg.contextResolver;
                }
            }, arg_0));
            cfg.pvt.appAttributes.computeIfAbsent("javalin-max-request-size", arg_0 -> Companion.applyUserConfig$lambda$3(new Function1<String, Object>(cfg){
                final /* synthetic */ JavalinConfig $cfg;
                {
                    this.$cfg = $cfg;
                    super(1);
                }

                @NotNull
                public final Object invoke(@NotNull String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.$cfg.http.maxRequestSize;
                }
            }, arg_0));
            cfg.pvt.appAttributes.computeIfAbsent("javalin-javalinvue-config", arg_0 -> Companion.applyUserConfig$lambda$4(new Function1<String, Object>(cfg){
                final /* synthetic */ JavalinConfig $cfg;
                {
                    this.$cfg = $cfg;
                    super(1);
                }

                @NotNull
                public final Object invoke(@NotNull String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.$cfg.vue;
                }
            }, arg_0));
        }

        private static final Object applyUserConfig$lambda$0(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
            return $tmp0.invoke(p0);
        }

        private static final Object applyUserConfig$lambda$1(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
            return $tmp0.invoke(p0);
        }

        private static final Object applyUserConfig$lambda$2(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
            return $tmp0.invoke(p0);
        }

        private static final Object applyUserConfig$lambda$3(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
            return $tmp0.invoke(p0);
        }

        private static final Object applyUserConfig$lambda$4(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
            return $tmp0.invoke(p0);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

