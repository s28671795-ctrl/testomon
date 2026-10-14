/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.http.Context;
import io.javalin.json.JsonMapperKt;
import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.validation.Validator;
import io.javalin.websocket.WsAutomaticPingKt;
import io.javalin.websocket.WsConnectionKt;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.TypesJVMKt;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0016\u001a\u0004\u0018\u0001H\u0017\"\u0004\b\u0000\u0010\u00172\u0006\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0019J\u0018\u0010\u0016\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001J\u0014\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010\bJ\u0006\u0010\u001d\u001a\u00020\u001aJ\u0018\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0003J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"J\u0010\u0010#\u001a\u0004\u0018\u00010\u00032\u0006\u0010$\u001a\u00020\u0003J\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\bJ\u0006\u0010&\u001a\u00020\u001aJ(\u0010'\u001a\u00020\u001a2\b\b\u0002\u0010(\u001a\u00020)2\b\b\u0002\u0010*\u001a\u00020+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-H\u0007J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00101\u001a\u00020\u001fH\u0016J\u0010\u00102\u001a\u0004\u0018\u00010\u00032\u0006\u00102\u001a\u00020\u0003J(\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001704\"\u0004\b\u0000\u0010\u00172\u0006\u00102\u001a\u00020\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u001706J\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\bJ\u0006\u00108\u001a\u00020\u0003J\u0006\u00109\u001a\u00020\u0003J\u000e\u0010:\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0003J#\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u001704\"\n\b\u0000\u0010\u0017\u0018\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0003H\u0086\bJ(\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u001704\"\u0004\b\u0000\u0010\u00172\u0006\u0010\u0018\u001a\u00020\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u001706J\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\bJ\u0010\u0010=\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0018\u001a\u00020\u0003J#\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u001704\"\n\b\u0000\u0010\u0017\u0018\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0003H\u0086\bJ(\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u001704\"\u0004\b\u0000\u0010\u00172\u0006\u0010\u0018\u001a\u00020\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u001706J\u0018\u0010?\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030@0\bJ\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00030@2\u0006\u0010\u0018\u001a\u00020\u0003J\b\u0010B\u001a\u0004\u0018\u00010\u0003J\u000e\u0010C\u001a\u00020\u001a2\u0006\u0010D\u001a\u00020-J\u000e\u0010C\u001a\u00020\u001a2\u0006\u0010D\u001a\u00020\u0001J\u000e\u0010C\u001a\u00020\u001a2\u0006\u0010D\u001a\u00020\u0003J\"\u0010E\u001a\u00020\u001a\"\n\b\u0000\u0010\u0017\u0018\u0001*\u00020\u00012\u0006\u0010D\u001a\u0002H\u0017H\u0086\b\u00a2\u0006\u0002\u0010FJ\u0016\u0010E\u001a\u00020\u001a2\u0006\u0010D\u001a\u00020\u00012\u0006\u0010G\u001a\u00020HJ\u0014\u0010I\u001a\u00020\u001a2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-H\u0007J\u001b\u0010J\u001a\u0004\u0018\u0001H\u0017\"\u0004\b\u0000\u0010\u00172\u0006\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010K\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010\bR\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R/\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b8BX\u0082\u0084\u0002\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128@X\u0080\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006L"}, d2={"Lio/javalin/websocket/WsContext;", "", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;)V", "sessionAttributes", "", "getSessionAttributes$annotations", "()V", "getSessionAttributes", "()Ljava/util/Map;", "sessionAttributes$delegate", "Lkotlin/Lazy;", "getSessionId", "()Ljava/lang/String;", "upgradeCtx", "Lio/javalin/http/Context;", "getUpgradeCtx$javalin", "()Lio/javalin/http/Context;", "upgradeCtx$delegate", "attribute", "T", "key", "(Ljava/lang/String;)Ljava/lang/Object;", "", "value", "attributeMap", "closeSession", "code", "", "reason", "closeStatus", "Lorg/eclipse/jetty/websocket/api/CloseStatus;", "cookie", "name", "cookieMap", "disableAutomaticPings", "enableAutomaticPings", "interval", "", "unit", "Ljava/util/concurrent/TimeUnit;", "applicationData", "Ljava/nio/ByteBuffer;", "equals", "", "other", "hashCode", "header", "headerAsClass", "Lio/javalin/validation/Validator;", "clazz", "Ljava/lang/Class;", "headerMap", "host", "matchedPath", "pathParam", "pathParamAsClass", "pathParamMap", "queryParam", "queryParamAsClass", "queryParamMap", "", "queryParams", "queryString", "send", "message", "sendAsClass", "(Ljava/lang/Object;)V", "type", "Ljava/lang/reflect/Type;", "sendPing", "sessionAttribute", "sessionAttributeMap", "javalin"})
public abstract class WsContext {
    @NotNull
    private final String sessionId;
    @JvmField
    @NotNull
    public final Session session;
    @NotNull
    private final Lazy upgradeCtx$delegate;
    @NotNull
    private final Lazy sessionAttributes$delegate;

    public WsContext(@NotNull String sessionId, @NotNull Session session) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        this.sessionId = sessionId;
        this.session = session;
        this.upgradeCtx$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Context>(this){
            final /* synthetic */ WsContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Context invoke() {
                Object object = WsConnectionKt.getJettyUpgradeRequest(this.this$0.session).getHttpServletRequest().getAttribute("javalin-ws-upgrade-context");
                Intrinsics.checkNotNull(object, "null cannot be cast to non-null type io.javalin.http.Context");
                return (Context)object;
            }
        }, 1, null);
        this.sessionAttributes$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Map<String, ? extends Object>>(this){
            final /* synthetic */ WsContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @Nullable
            public final Map<String, Object> invoke() {
                Object object = WsConnectionKt.getJettyUpgradeRequest(this.this$0.session).getHttpServletRequest().getAttribute("javalin-ws-upgrade-http-session");
                return object instanceof Map ? (Map)object : null;
            }
        }, 1, null);
    }

    @NotNull
    public final String getSessionId() {
        return this.sessionId;
    }

    @NotNull
    public final Context getUpgradeCtx$javalin() {
        Lazy lazy = this.upgradeCtx$delegate;
        return (Context)lazy.getValue();
    }

    private final Map<String, Object> getSessionAttributes() {
        Lazy lazy = this.sessionAttributes$delegate;
        return (Map)lazy.getValue();
    }

    private static /* synthetic */ void getSessionAttributes$annotations() {
    }

    @NotNull
    public final String matchedPath() {
        return this.getUpgradeCtx$javalin().matchedPath();
    }

    public final /* synthetic */ <T> void sendAsClass(T message) {
        Intrinsics.checkNotNullParameter(message, "message");
        boolean $i$f$sendAsClass = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        this.sendAsClass(message, TypesJVMKt.getJavaType(null));
    }

    public final void send(@NotNull Object message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.sendAsClass(message, message.getClass());
    }

    public final void sendAsClass(@NotNull Object message, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(type, "type");
        this.send(JsonMapperKt.jsonMapper(this.getUpgradeCtx$javalin()).toJsonString(message, type));
    }

    public final void send(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.session.getRemote().sendString(message);
    }

    public final void send(@NotNull ByteBuffer message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.session.getRemote().sendBytes(message);
    }

    @JvmOverloads
    public final void sendPing(@Nullable ByteBuffer applicationData) {
        RemoteEndpoint remoteEndpoint = this.session.getRemote();
        ByteBuffer byteBuffer = applicationData;
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(0);
        }
        remoteEndpoint.sendPing(byteBuffer);
    }

    public static /* synthetic */ void sendPing$default(WsContext wsContext, ByteBuffer byteBuffer, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendPing");
        }
        if ((n & 1) != 0) {
            byteBuffer = null;
        }
        wsContext.sendPing(byteBuffer);
    }

    @JvmOverloads
    public final void enableAutomaticPings(long interval, @NotNull TimeUnit unit, @Nullable ByteBuffer applicationData) {
        Intrinsics.checkNotNullParameter((Object)unit, "unit");
        WsAutomaticPingKt.enableAutomaticPings(this, interval, unit, applicationData);
    }

    public static /* synthetic */ void enableAutomaticPings$default(WsContext wsContext, long l, TimeUnit timeUnit, ByteBuffer byteBuffer, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: enableAutomaticPings");
        }
        if ((n & 1) != 0) {
            l = 1L;
        }
        if ((n & 2) != 0) {
            timeUnit = TimeUnit.MINUTES;
        }
        if ((n & 4) != 0) {
            byteBuffer = null;
        }
        wsContext.enableAutomaticPings(l, timeUnit, byteBuffer);
    }

    public final void disableAutomaticPings() {
        WsAutomaticPingKt.disableAutomaticPings(this);
    }

    @Nullable
    public final String queryString() {
        return this.getUpgradeCtx$javalin().queryString();
    }

    @NotNull
    public final Map<String, List<String>> queryParamMap() {
        return this.getUpgradeCtx$javalin().queryParamMap();
    }

    @NotNull
    public final List<String> queryParams(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.getUpgradeCtx$javalin().queryParams(key);
    }

    @Nullable
    public final String queryParam(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.getUpgradeCtx$javalin().queryParam(key);
    }

    @NotNull
    public final <T> Validator<T> queryParamAsClass(@NotNull String key, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.getUpgradeCtx$javalin().queryParamAsClass(key, clazz);
    }

    public final /* synthetic */ <T> Validator<T> queryParamAsClass(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean $i$f$queryParamAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return this.queryParamAsClass(key, Object.class);
    }

    @NotNull
    public final Map<String, String> pathParamMap() {
        return this.getUpgradeCtx$javalin().pathParamMap();
    }

    @NotNull
    public final String pathParam(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.getUpgradeCtx$javalin().pathParam(key);
    }

    @NotNull
    public final <T> Validator<T> pathParamAsClass(@NotNull String key, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.getUpgradeCtx$javalin().pathParamAsClass(key, clazz);
    }

    public final /* synthetic */ <T> Validator<T> pathParamAsClass(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean $i$f$pathParamAsClass = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        return this.pathParamAsClass(key, Object.class);
    }

    @NotNull
    public final String host() {
        String string = WsConnectionKt.getJettyUpgradeRequest(this.session).getHost();
        Intrinsics.checkNotNullExpressionValue(string, "session.jettyUpgradeRequest.host");
        return string;
    }

    @Nullable
    public final String header(@NotNull String header) {
        Intrinsics.checkNotNullParameter(header, "header");
        return this.getUpgradeCtx$javalin().header(header);
    }

    @NotNull
    public final Map<String, String> headerMap() {
        return this.getUpgradeCtx$javalin().headerMap();
    }

    @NotNull
    public final <T> Validator<T> headerAsClass(@NotNull String header, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.getUpgradeCtx$javalin().headerAsClass(header, clazz);
    }

    @Nullable
    public final String cookie(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.getUpgradeCtx$javalin().cookie(name);
    }

    @NotNull
    public final Map<String, String> cookieMap() {
        return this.getUpgradeCtx$javalin().cookieMap();
    }

    public final void attribute(@NotNull String key, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.getUpgradeCtx$javalin().attribute(key, value);
    }

    @Nullable
    public final <T> T attribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.getUpgradeCtx$javalin().attribute(key);
    }

    @NotNull
    public final Map<String, Object> attributeMap() {
        return this.getUpgradeCtx$javalin().attributeMap();
    }

    @Nullable
    public final <T> T sessionAttribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (T)this.sessionAttributeMap().get(key);
    }

    @NotNull
    public final Map<String, Object> sessionAttributeMap() {
        Map<String, Object> map = this.getSessionAttributes();
        if (map == null) {
            map = MapsKt.emptyMap();
        }
        return map;
    }

    public final void closeSession() {
        this.session.close();
    }

    public final void closeSession(@NotNull CloseStatus closeStatus) {
        Intrinsics.checkNotNullParameter(closeStatus, "closeStatus");
        this.session.close(closeStatus);
    }

    public final void closeSession(int code, @Nullable String reason) {
        this.session.close(code, reason);
    }

    public boolean equals(@Nullable Object other) {
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type io.javalin.websocket.WsContext");
        return Intrinsics.areEqual(this.session, ((WsContext)other).session);
    }

    public int hashCode() {
        return this.session.hashCode();
    }

    @JvmOverloads
    public final void sendPing() {
        WsContext.sendPing$default(this, null, 1, null);
    }

    @JvmOverloads
    public final void enableAutomaticPings(long interval, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter((Object)unit, "unit");
        WsContext.enableAutomaticPings$default(this, interval, unit, null, 4, null);
    }

    @JvmOverloads
    public final void enableAutomaticPings(long interval) {
        WsContext.enableAutomaticPings$default(this, interval, null, null, 6, null);
    }

    @JvmOverloads
    public final void enableAutomaticPings() {
        WsContext.enableAutomaticPings$default(this, 0L, null, null, 7, null);
    }
}

