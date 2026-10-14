/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.config.PrivateConfig;
import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.staticfiles.StaticFileConfig;
import io.javalin.jetty.ConfigurableHandler;
import io.javalin.jetty.JettyPrecompressingResourceHandler;
import io.javalin.jetty.JettyUtil;
import io.javalin.util.JavalinLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0016J \u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0006\u0010\u001b\u001a\u00020\u001cJ\u001e\u0010\u001d\u001a\u00020\r*\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u001e\u001a\u00020\r*\u0004\u0018\u00010\u0010H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001f"}, d2={"Lio/javalin/jetty/JettyResourceHandler;", "Lio/javalin/http/staticfiles/ResourceHandler;", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "handlers", "", "Lio/javalin/jetty/ConfigurableHandler;", "lateInitConfigs", "Lio/javalin/http/staticfiles/StaticFileConfig;", "getPvt", "()Lio/javalin/config/PrivateConfig;", "addStaticFileConfig", "", "config", "getWelcomeFile", "Lorg/eclipse/jetty/util/resource/Resource;", "kotlin.jvm.PlatformType", "handler", "Lorg/eclipse/jetty/server/handler/ResourceHandler;", "target", "", "handle", "httpRequest", "Ljakarta/servlet/http/HttpServletRequest;", "httpResponse", "Ljakarta/servlet/http/HttpServletResponse;", "init", "", "isDirectoryWithWelcomeFile", "isFile", "javalin"})
@SourceDebugExtension(value={"SMAP\nJettyResourceHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JettyResourceHandler.kt\nio/javalin/jetty/JettyResourceHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,118:1\n1549#2:119\n1620#2,3:120\n766#2:123\n857#2,2:124\n1855#2:126\n1856#2:130\n215#3,2:127\n1#4:129\n*S KotlinDebug\n*F\n+ 1 JettyResourceHandler.kt\nio/javalin/jetty/JettyResourceHandler\n*L\n31#1:119\n31#1:120,3\n42#1:123\n42#1:124,2\n42#1:126\n42#1:130\n46#1:127,2\n*E\n"})
public final class JettyResourceHandler
implements ResourceHandler {
    @NotNull
    private final PrivateConfig pvt;
    @NotNull
    private final List<StaticFileConfig> lateInitConfigs;
    @NotNull
    private final List<ConfigurableHandler> handlers;

    public JettyResourceHandler(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
        this.lateInitConfigs = new ArrayList();
        this.handlers = new ArrayList();
    }

    @NotNull
    public final PrivateConfig getPvt() {
        return this.pvt;
    }

    /*
     * WARNING - void declaration
     */
    public final void init() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = this.lateInitConfigs;
        List<ConfigurableHandler> list = this.handlers;
        boolean $i$f$map = false;
        void var3_4 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            StaticFileConfig staticFileConfig = (StaticFileConfig)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Server server = this.pvt.server;
            Intrinsics.checkNotNull(server);
            collection.add(new ConfigurableHandler((StaticFileConfig)it, server));
        }
        list.addAll((List)destination$iv$iv);
    }

    @Override
    public boolean addStaticFileConfig(@NotNull StaticFileConfig config) {
        boolean bl;
        Intrinsics.checkNotNullParameter(config, "config");
        Server server = this.pvt.server;
        boolean bl2 = server != null ? server.isStarted() : false;
        if (bl2) {
            Server server2 = this.pvt.server;
            Intrinsics.checkNotNull(server2);
            bl = this.handlers.add(new ConfigurableHandler(config, server2));
        } else {
            bl = this.lateInitConfigs.add(config);
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean handle(@NotNull HttpServletRequest httpRequest, @NotNull HttpServletResponse httpResponse) {
        void $this$forEach$iv;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(httpRequest, "httpRequest");
        Intrinsics.checkNotNullParameter(httpResponse, "httpResponse");
        Object object = httpRequest.getAttribute("jetty-target-and-request");
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.Pair<kotlin.String, org.eclipse.jetty.server.Request>");
        Pair pair = (Pair)object;
        String target = (String)pair.component1();
        Request baseRequest = (Request)pair.component2();
        Iterable $this$filter$iv = this.handlers;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            ConfigurableHandler it = (ConfigurableHandler)element$iv$iv;
            boolean bl = false;
            if (!(it.getConfig().skipFileFunction.invoke(httpRequest) == false)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ConfigurableHandler handler = (ConfigurableHandler)element$iv;
            boolean bl = false;
            try {
                Object object2;
                Resource resource = handler.getResource(target);
                if (!this.isFile(resource) && !this.isDirectoryWithWelcomeFile(resource, handler, target)) continue;
                Map<String, String> $this$forEach$iv2 = handler.getConfig().headers;
                boolean $i$f$forEach2 = false;
                Iterator<Map.Entry<String, String>> iterator2 = $this$forEach$iv2.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<String, String> element$iv2;
                    Map.Entry<String, String> it = element$iv2 = iterator2.next();
                    boolean bl2 = false;
                    httpResponse.setHeader(it.getKey(), it.getValue());
                }
                if (handler.getConfig().precompress) {
                    boolean bl3;
                    if (this.isDirectoryWithWelcomeFile(resource, handler, target)) {
                        Resource resource2 = this.getWelcomeFile(handler, target);
                        Intrinsics.checkNotNullExpressionValue(resource2, "getWelcomeFile(handler, target)");
                        bl3 = JettyPrecompressingResourceHandler.INSTANCE.handle(target, resource2, httpRequest, httpResponse);
                    } else {
                        bl3 = JettyPrecompressingResourceHandler.INSTANCE.handle(target, resource, httpRequest, httpResponse);
                    }
                    return bl3;
                }
                httpResponse.setContentType(null);
                JettyResourceHandler jettyResourceHandler = this;
                try {
                    JettyResourceHandler $this$handle_u24lambda_u244_u24lambda_u243 = jettyResourceHandler;
                    boolean bl4 = false;
                    handler.handle(target, baseRequest, httpRequest, httpResponse);
                    object2 = Result.constructor-impl(Unit.INSTANCE);
                }
                catch (Throwable throwable) {
                    object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                return Result.isSuccess-impl(object2);
            }
            catch (Exception e) {
                if (JettyUtil.INSTANCE.isClientAbortException(e)) continue;
                JavalinLogger.info("Exception occurred while handling static resource", e);
            }
        }
        return false;
    }

    private final boolean isFile(Resource $this$isFile) {
        return $this$isFile != null && $this$isFile.exists() && !$this$isFile.isDirectory();
    }

    private final Resource getWelcomeFile(org.eclipse.jetty.server.handler.ResourceHandler handler, String target) {
        return handler.getResource(StringsKt.removeSuffix(target, (CharSequence)"/") + "/index.html");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isDirectoryWithWelcomeFile(Resource $this$isDirectoryWithWelcomeFile, org.eclipse.jetty.server.handler.ResourceHandler handler, String target) {
        if ($this$isDirectoryWithWelcomeFile == null) return false;
        if (!$this$isDirectoryWithWelcomeFile.isDirectory()) return false;
        Resource resource = this.getWelcomeFile(handler, target);
        if (resource == null) return false;
        if (!resource.exists()) return false;
        return true;
    }
}

