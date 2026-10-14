/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.plugin.Plugin;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.eclipse.jetty.server.ServerConnector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\f\u0010\f\u001a\u00020\u0005*\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007\u00a8\u0006\u000e"}, d2={"Lio/javalin/plugin/bundled/SslRedirectPlugin;", "Lio/javalin/plugin/Plugin;", "redirectOnLocalhost", "", "sslPort", "", "(ZLjava/lang/Integer;)V", "Ljava/lang/Integer;", "apply", "", "app", "Lio/javalin/Javalin;", "usedPort", "Lorg/eclipse/jetty/server/ServerConnector;", "javalin"})
@SourceDebugExtension(value={"SMAP\nSslRedirectPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SslRedirectPlugin.kt\nio/javalin/plugin/bundled/SslRedirectPlugin\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n1#2:53\n4098#3,11:54\n288#4,2:65\n*S KotlinDebug\n*F\n+ 1 SslRedirectPlugin.kt\nio/javalin/plugin/bundled/SslRedirectPlugin\n*L\n33#1:54,11\n34#1:65,2\n*E\n"})
public final class SslRedirectPlugin
implements Plugin {
    private final boolean redirectOnLocalhost;
    @Nullable
    private final Integer sslPort;

    @JvmOverloads
    public SslRedirectPlugin(boolean redirectOnLocalhost, @Nullable Integer sslPort) {
        this.redirectOnLocalhost = redirectOnLocalhost;
        this.sslPort = sslPort;
    }

    public /* synthetic */ SslRedirectPlugin(boolean bl, Integer n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            bl = false;
        }
        if ((n2 & 2) != 0) {
            n = null;
        }
        this(bl, n);
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.before(arg_0 -> SslRedirectPlugin.apply$lambda$3(this, app, arg_0));
    }

    private final int usedPort(ServerConnector $this$usedPort) {
        Integer n = $this$usedPort.getPort();
        int it = ((Number)n).intValue();
        boolean bl = false;
        Integer n2 = it != 0 ? n : null;
        return n2 != null ? n2.intValue() : $this$usedPort.getLocalPort();
    }

    @JvmOverloads
    public SslRedirectPlugin(boolean redirectOnLocalhost) {
        this(redirectOnLocalhost, null, 2, null);
    }

    @JvmOverloads
    public SslRedirectPlugin() {
        this(false, null, 3, null);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static final void apply$lambda$3(SslRedirectPlugin this$0, Javalin $app, Context ctx) {
        block6: {
            block5: {
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Intrinsics.checkNotNullParameter($app, "$app");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (!this$0.redirectOnLocalhost && JavalinServletContextKt.isLocalhost(ctx)) {
                    return;
                }
                xForwardedProto = ctx.header("X-Forwarded-Proto");
                if (!Intrinsics.areEqual(xForwardedProto, "http") && (xForwardedProto != null || !Intrinsics.areEqual(ctx.scheme(), "http"))) break block6;
                urlWithHttps = StringsKt.replace$default(ctx.fullUrl(), "http", "https", false, 4, null);
                var6_5 = $app.jettyServer();
                if (var6_5 == null) ** GOTO lbl-1000
                var8_6 = var6_5;
                it = var8_6;
                $i$a$-takeIf-SslRedirectPlugin$apply$1$urlWithHttpsAndPort$1 = false;
                v0 = var7_9 = this$0.sslPort != null != false ? var8_6 : null;
                if (var7_9 == null || (var8_6 = var7_9.server()) == null || (var9_7 = var8_6.getConnectors()) == null) ** GOTO lbl-1000
                var11_10 /* !! */  = var9_7;
                $i$f$filterIsInstance = false;
                var13_13 = $this$filterIsInstance$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterIsInstanceTo = false;
                for (void element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof ServerConnector)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $i$f$filterIsInstance = (List)destination$iv$iv;
                $i$f$firstOrNull = false;
                for (E element$iv : $this$firstOrNull$iv) {
                    it = (ServerConnector)element$iv;
                    $i$a$-firstOrNull-SslRedirectPlugin$apply$1$urlWithHttpsAndPort$2 = false;
                    if (!StringsKt.contains$default((CharSequence)urlWithHttps, ":" + this$0.usedPort(it) + "/", false, 2, null)) continue;
                    v1 = element$iv;
                    break block5;
                }
                v1 = null;
            }
            var11_10 /* !! */  = v1;
            if (var11_10 /* !! */  == null) ** GOTO lbl-1000
            it /* !! */  = var11_10 /* !! */ ;
            $i$a$-let-SslRedirectPlugin$apply$1$urlWithHttpsAndPort$3 = false;
            var12_12 = StringsKt.replaceFirst$default(urlWithHttps, ":" + this$0.usedPort((ServerConnector)it /* !! */ ) + "/", ":" + this$0.sslPort + "/", false, 4, null);
            if (var12_12 != null) {
                v2 = var12_12;
            } else lbl-1000:
            // 4 sources

            {
                v2 = urlWithHttps;
            }
            urlWithHttpsAndPort = v2;
            ctx.redirect(urlWithHttpsAndPort, HttpStatus.MOVED_PERMANENTLY);
        }
    }
}

