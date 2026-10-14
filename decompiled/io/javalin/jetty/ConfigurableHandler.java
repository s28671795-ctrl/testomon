/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.http.staticfiles.Location;
import io.javalin.http.staticfiles.StaticFileConfig;
import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.util.JavalinException;
import io.javalin.util.JavalinLogger;
import java.io.File;
import java.nio.file.AccessDeniedException;
import java.util.Iterator;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.EmptyResource;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000e\u00b2\u0006\u0012\u0010\u000f\u001a\n \u0010*\u0004\u0018\u00010\n0\nX\u008a\u0084\u0002"}, d2={"Lio/javalin/jetty/ConfigurableHandler;", "Lorg/eclipse/jetty/server/handler/ResourceHandler;", "config", "Lio/javalin/http/staticfiles/StaticFileConfig;", "jettyServer", "Lorg/eclipse/jetty/server/Server;", "(Lio/javalin/http/staticfiles/StaticFileConfig;Lorg/eclipse/jetty/server/Server;)V", "getConfig", "()Lio/javalin/http/staticfiles/StaticFileConfig;", "getResource", "Lorg/eclipse/jetty/util/resource/Resource;", "path", "", "getResourceBase", "javalin", "aliasResource", "kotlin.jvm.PlatformType"})
@SourceDebugExtension(value={"SMAP\nJettyResourceHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JettyResourceHandler.kt\nio/javalin/jetty/ConfigurableHandler\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,118:1\n215#2,2:119\n*S KotlinDebug\n*F\n+ 1 JettyResourceHandler.kt\nio/javalin/jetty/ConfigurableHandler\n*L\n83#1:119,2\n*E\n"})
public class ConfigurableHandler
extends ResourceHandler {
    @NotNull
    private final StaticFileConfig config;

    public ConfigurableHandler(@NotNull StaticFileConfig config, @NotNull Server jettyServer) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(jettyServer, "jettyServer");
        this.config = config;
        JavalinLogger.info$default("Static file handler added: " + this.config.refinedToString$javalin() + ". File system location: '" + this.getResourceBase(this.config) + "'", null, 2, null);
        this.setResourceBase(this.getResourceBase(this.config));
        this.setDirAllowed(false);
        this.setEtags(true);
        this.setServer(jettyServer);
        this.setMimeTypes(new MimeTypes());
        Map<String, String> $this$forEach$iv = this.config.mimeTypes.getMapping();
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<String, String>> iterator2 = $this$forEach$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> element$iv;
            Map.Entry<String, String> entry = element$iv = iterator2.next();
            boolean bl = false;
            String ext = entry.getKey();
            String mimeType = entry.getValue();
            this.getMimeTypes().addMimeMapping(ext, mimeType);
        }
        this.start();
    }

    @NotNull
    public final StaticFileConfig getConfig() {
        return this.config;
    }

    @Override
    @NotNull
    public Resource getResource(@NotNull String path) {
        Resource resource;
        Intrinsics.checkNotNullParameter(path, "path");
        Lazy aliasResource$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Resource>(this, path){
            final /* synthetic */ ConfigurableHandler this$0;
            final /* synthetic */ String $path;
            {
                this.this$0 = $receiver;
                this.$path = $path;
                super(0);
            }

            public final Resource invoke() {
                Resource resource = this.this$0.getBaseResource();
                Intrinsics.checkNotNull(resource);
                return resource.addPath(URIUtil.canonicalPath(this.$path));
            }
        }, 1, null);
        if (Intrinsics.areEqual(this.config.directory, "META-INF/resources/webjars")) {
            Resource resource2 = Resource.newClassPathResource("META-INF/resources" + path);
            if (resource2 == null) {
                resource2 = EmptyResource.INSTANCE;
            }
            resource = resource2;
            Intrinsics.checkNotNullExpressionValue(resource2, "Resource.newClassPathRes\u2026?: EmptyResource.INSTANCE");
        } else if (this.config.aliasCheck != null && ConfigurableHandler.getResource$lambda$1(aliasResource$delegate).isAlias()) {
            ContextHandler.AliasCheck aliasCheck = this.config.aliasCheck;
            if (!(aliasCheck != null ? aliasCheck.check(path, ConfigurableHandler.getResource$lambda$1(aliasResource$delegate)) : false)) {
                throw new AccessDeniedException("Failed alias check");
            }
            Resource resource3 = ConfigurableHandler.getResource$lambda$1(aliasResource$delegate);
            resource = resource3;
            Intrinsics.checkNotNullExpressionValue(resource3, "if (config.aliasCheck?.c\u2026ion(\"Failed alias check\")");
        } else if (Intrinsics.areEqual(this.config.hostedPath, "/")) {
            Resource resource4 = super.getResource(path);
            resource = resource4;
            Intrinsics.checkNotNullExpressionValue(resource4, "super.getResource(path)");
        } else if (Intrinsics.areEqual(path, this.config.hostedPath)) {
            Resource resource5 = super.getResource("/");
            resource = resource5;
            Intrinsics.checkNotNullExpressionValue(resource5, "super.getResource(\"/\")");
        } else if (StringsKt.startsWith$default(path, this.config.hostedPath + "/", false, 2, null)) {
            Resource resource6 = super.getResource(StringsKt.removePrefix(path, (CharSequence)this.config.hostedPath));
            resource = resource6;
            Intrinsics.checkNotNullExpressionValue(resource6, "super.getResource(path.r\u2026refix(config.hostedPath))");
        } else {
            Resource resource7 = EmptyResource.INSTANCE;
            resource = resource7;
            Intrinsics.checkNotNullExpressionValue(resource7, "INSTANCE");
        }
        return resource;
    }

    private final String getResourceBase(StaticFileConfig config) {
        String noSuchDirMessage = "Static resource directory with path: '" + config.directory + "' does not exist.";
        String classpathHint = "Depending on your setup, empty folders might not get copied to classpath.";
        if (config.location == Location.CLASSPATH) {
            Object object = Resource.newClassPathResource(config.directory);
            if (object == null || (object = object.toString()) == null) {
                throw new JavalinException(noSuchDirMessage + " " + classpathHint);
            }
            return object;
        }
        if (!new File(config.directory).exists()) {
            throw new JavalinException(noSuchDirMessage);
        }
        return config.directory;
    }

    private static final Resource getResource$lambda$1(Lazy<? extends Resource> $aliasResource$delegate) {
        Lazy<? extends Resource> lazy = $aliasResource$delegate;
        return lazy.getValue();
    }
}

