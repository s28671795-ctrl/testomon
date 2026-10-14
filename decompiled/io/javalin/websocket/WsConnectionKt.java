/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"jettyUpgradeRequest", "Lorg/eclipse/jetty/websocket/server/JettyServerUpgradeRequest;", "Lorg/eclipse/jetty/websocket/api/Session;", "getJettyUpgradeRequest", "(Lorg/eclipse/jetty/websocket/api/Session;)Lorg/eclipse/jetty/websocket/server/JettyServerUpgradeRequest;", "uriNoContextPath", "", "javalin"})
public final class WsConnectionKt {
    @NotNull
    public static final JettyServerUpgradeRequest getJettyUpgradeRequest(@NotNull Session $this$jettyUpgradeRequest) {
        Intrinsics.checkNotNullParameter($this$jettyUpgradeRequest, "<this>");
        UpgradeRequest upgradeRequest = $this$jettyUpgradeRequest.getUpgradeRequest();
        Intrinsics.checkNotNull(upgradeRequest, "null cannot be cast to non-null type org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest");
        return (JettyServerUpgradeRequest)upgradeRequest;
    }

    private static final String uriNoContextPath(Session $this$uriNoContextPath) {
        String string = $this$uriNoContextPath.getUpgradeRequest().getRequestURI().getPath();
        Intrinsics.checkNotNullExpressionValue(string, "this.upgradeRequest.requestURI.path");
        String string2 = WsConnectionKt.getJettyUpgradeRequest($this$uriNoContextPath).getHttpServletRequest().getContextPath();
        Intrinsics.checkNotNullExpressionValue(string2, "jettyUpgradeRequest.httpServletRequest.contextPath");
        return StringsKt.removePrefix(string, (CharSequence)string2);
    }

    public static final /* synthetic */ String access$uriNoContextPath(Session $receiver) {
        return WsConnectionKt.uriNoContextPath($receiver);
    }
}

