/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.routing.HandlerEntry;
import io.javalin.routing.PathMatcher;
import io.javalin.util.JavalinLogger;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u000e\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f\u001a\u001e\u0010\r\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\tH\u0002\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\tH\u0002\u00a8\u0006\u0013"}, d2={"requestDevLogger", "", "matcher", "Lio/javalin/routing/PathMatcher;", "ctx", "Lio/javalin/http/Context;", "time", "", "resBody", "", "wsDevLogger", "ws", "Lio/javalin/websocket/WsConfig;", "logEvent", "Lio/javalin/websocket/WsContext;", "event", "additionalInfo", "probablyFormData", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nDevLoggerPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DevLoggerPlugin.kt\nio/javalin/plugin/bundled/DevLoggerPluginKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,103:1\n1549#2:104\n1620#2,3:105\n1238#2,4:110\n1238#2,4:116\n1238#2,4:122\n442#3:108\n392#3:109\n442#3:114\n392#3:115\n442#3:120\n392#3:121\n*S KotlinDebug\n*F\n+ 1 DevLoggerPlugin.kt\nio/javalin/plugin/bundled/DevLoggerPluginKt\n*L\n40#1:104\n40#1:105,3\n50#1:110,4\n51#1:116,4\n97#1:122,4\n50#1:108\n50#1:109\n51#1:114\n51#1:115\n97#1:120\n97#1:121\n*E\n"})
public final class DevLoggerPluginKt {
    /*
     * WARNING - void declaration
     */
    public static final void requestDevLogger(@NotNull PathMatcher matcher, @NotNull Context ctx, float time) {
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            String string;
            List v;
            Map map;
            Object object;
            Map.Entry it$iv$iv;
            Map.Entry entry;
            boolean bl;
            Map map2;
            Map<Object, Object> $this$mapValuesTo$iv$iv;
            Map<Object, Object> $this$mapValues$iv;
            Object object2;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            String requestUri = ctx.path();
            Context $this$requestDevLogger_u24lambda_u243 = ctx;
            boolean bl2 = false;
            Iterable iterable = CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)matcher.findEntries(HandlerType.BEFORE, requestUri), (Iterable)matcher.findEntries(ctx.method(), requestUri)), (Iterable)matcher.findEntries(HandlerType.AFTER, requestUri));
            boolean $i$f$map22 = false;
            void var8_10 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                HandlerEntry handlerEntry = (HandlerEntry)item$iv$iv;
                object2 = destination$iv$iv;
                boolean bl3 = false;
                object2.add(it.getType().name() + "=" + it.getPath());
            }
            List allMatching = (List)destination$iv$iv;
            Collection<String> collection = $this$requestDevLogger_u24lambda_u243.res().getHeaderNames();
            Intrinsics.checkNotNullExpressionValue(collection, "res().headerNames");
            Map resHeaders2 = MapsKt.toMap(SequencesKt.map(CollectionsKt.asSequence((Iterable)collection), (Function1)new Function1<String, Pair<? extends String, ? extends String>>($this$requestDevLogger_u24lambda_u243){
                final /* synthetic */ Context $this_with;
                {
                    this.$this_with = $receiver;
                    super(1);
                }

                @NotNull
                public final Pair<String, String> invoke(String it) {
                    return TuplesKt.to(it, this.$this_with.res().getHeader(it));
                }
            }));
            Map<String, List<String>> $i$f$map22 = $this$requestDevLogger_u24lambda_u243.queryParamMap();
            String string2 = $this$requestDevLogger_u24lambda_u243.queryString();
            String string3 = $this$requestDevLogger_u24lambda_u243.isMultipart() ? "Multipart data ..." : $this$requestDevLogger_u24lambda_u243.body();
            Map<String, String> map3 = $this$requestDevLogger_u24lambda_u243.cookieMap();
            Map<String, String> map4 = $this$requestDevLogger_u24lambda_u243.headerMap();
            List list = allMatching;
            String string4 = $this$requestDevLogger_u24lambda_u243.path();
            object2 = $this$requestDevLogger_u24lambda_u243.method();
            boolean $i$f$mapValues = false;
            destination$iv$iv = $this$mapValues$iv;
            Map destination$iv$iv2 = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
            boolean $i$f$mapValuesTo = false;
            Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
            boolean $i$f$associateByTo = false;
            for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                Map.Entry entry2 = (Map.Entry)element$iv$iv$iv;
                map2 = destination$iv$iv2;
                bl = false;
                entry = (Map.Entry)element$iv$iv$iv;
                object = it$iv$iv.getKey();
                map = map2;
                boolean bl4 = false;
                v = (List)entry.getValue();
                string = v.toString();
                map.put(object, string);
            }
            map = destination$iv$iv2;
            $this$mapValues$iv = DevLoggerPluginKt.probablyFormData($this$requestDevLogger_u24lambda_u243.body()) ? $this$requestDevLogger_u24lambda_u243.formParamMap() : MapsKt.emptyMap();
            $i$f$mapValues = false;
            $this$mapValuesTo$iv$iv = $this$mapValues$iv;
            destination$iv$iv2 = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
            $i$f$mapValuesTo = false;
            $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
            $i$f$associateByTo = false;
            for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                it$iv$iv = (Map.Entry)element$iv$iv$iv;
                map2 = destination$iv$iv2;
                bl = false;
                entry = (Map.Entry)element$iv$iv$iv;
                string = it$iv$iv.getKey();
                object = map2;
                boolean bl5 = false;
                v = (List)entry.getValue();
                String string5 = v.toString();
                object.put((String)string, (String)string5);
            }
            object = destination$iv$iv2;
            Object[] objectArray = new Object[]{Float.valueOf(time)};
            JavalinLogger.info$default(StringsKt.trimMargin$default("|JAVALIN REQUEST DEBUG LOG:\n               |Request: " + (HandlerType)((Object)object2) + " [" + string4 + "]\n               |    Matching endpoint-handlers: " + list + "\n               |    Headers: " + map4 + "\n               |    Cookies: " + map3 + "\n               |    Body: " + string3 + "\n               |    QueryString: " + string2 + "\n               |    QueryParams: " + map + "\n               |    FormParams: " + object + "\n               |Response: [" + $this$requestDevLogger_u24lambda_u243.status() + "], execution took " + new Formatter(Locale.US).format("%.2f", objectArray) + " ms\n               |    Headers: " + resHeaders2 + "\n               |    " + DevLoggerPluginKt.resBody(ctx) + "\n               |----------------------------------------------------------------------------------", null, 1, null), null, 2, null);
        }
        catch (Exception e) {
            JavalinLogger.info("An exception occurred while logging debug-info", e);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final boolean probablyFormData(String $this$probablyFormData) {
        Character c = StringsKt.firstOrNull(((Object)StringsKt.trim((CharSequence)$this$probablyFormData)).toString());
        if (c == null) return false;
        if (!Character.isLetter(c.charValue())) return false;
        boolean bl = true;
        if (!bl) return false;
        String[] stringArray = new String[]{"="};
        if (StringsKt.split$default((CharSequence)$this$probablyFormData, stringArray, false, 0, 6, null).size() < 2) return false;
        return true;
    }

    private static final String resBody(Context ctx) {
        InputStream inputStream2 = ctx.resultInputStream();
        if (inputStream2 == null) {
            return "No body was set";
        }
        InputStream stream = inputStream2;
        if (!stream.markSupported()) {
            return "Body is binary (not logged)";
        }
        boolean gzipped = Intrinsics.areEqual(ctx.res().getHeader("Content-Encoding"), "gzip");
        boolean brotlied = Intrinsics.areEqual(ctx.res().getHeader("Content-Encoding"), "br");
        String string = ctx.result();
        Intrinsics.checkNotNull(string);
        String resBody = string;
        return gzipped ? "Body is gzipped (" + resBody.length() + " bytes, not logged)" : (brotlied ? "Body is brotlied (" + resBody.length() + " bytes, not logged)" : (StringsKt.contains$default((CharSequence)resBody, "resultString unavailable", false, 2, null) ? "Body is an InputStream which can't be reset, so it can't be logged" : "Body is " + resBody.length() + " bytes (starts on next line):\n    " + resBody));
    }

    public static final void wsDevLogger(@NotNull WsConfig ws) {
        Intrinsics.checkNotNullParameter(ws, "ws");
        ws.onConnect(DevLoggerPluginKt::wsDevLogger$lambda$4);
        ws.onMessage(DevLoggerPluginKt::wsDevLogger$lambda$5);
        ws.onBinaryMessage(DevLoggerPluginKt::wsDevLogger$lambda$6);
        ws.onClose(DevLoggerPluginKt::wsDevLogger$lambda$7);
        ws.onError(DevLoggerPluginKt::wsDevLogger$lambda$8);
    }

    /*
     * WARNING - void declaration
     */
    private static final void logEvent(WsContext $this$logEvent, String event, String additionalInfo) {
        String string;
        String string2 = event;
        String string3 = $this$logEvent.getSessionId();
        String string4 = $this$logEvent.host();
        String string5 = $this$logEvent.matchedPath();
        Map<String, String> map = $this$logEvent.pathParamMap();
        if ($this$logEvent.queryString() != null) {
            Map map2;
            void $this$mapValuesTo$iv$iv;
            void $this$mapValues$iv;
            Map<String, List<String>> map3 = $this$logEvent.queryParamMap();
            Map<String, String> map4 = map;
            String string6 = string5;
            String string7 = string4;
            String string8 = string3;
            String string9 = string2;
            boolean $i$f$mapValues = false;
            void var5_10 = $this$mapValues$iv;
            Map destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
            boolean $i$f$mapValuesTo = false;
            Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
            boolean $i$f$associateByTo = false;
            for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                void it$iv$iv;
                Map.Entry entry = (Map.Entry)element$iv$iv$iv;
                Map map5 = destination$iv$iv;
                boolean bl = false;
                Map.Entry entry2 = (Map.Entry)element$iv$iv$iv;
                Object k = it$iv$iv.getKey();
                map2 = map5;
                boolean bl2 = false;
                List v = (List)entry2.getValue();
                String string10 = v.toString();
                map2.put(k, string10);
            }
            map2 = destination$iv$iv;
            string2 = string9;
            string3 = string8;
            string4 = string7;
            string5 = string6;
            map = map4;
            string = map2.toString();
        } else {
            string = "No query string was provided";
        }
        JavalinLogger.info$default(StringsKt.trimMargin$default("|JAVALIN WEBSOCKET DEBUG LOG\n           |WebSocket Event: " + string2 + "\n           |Session Id: " + string3 + "\n           |Host: " + string4 + "\n           |Matched Path: " + string5 + "\n           |PathParams: " + map + "\n           |QueryParams: " + string + "\n           |" + additionalInfo + "\n           |----------------------------------------------------------------------------------", null, 1, null), null, 2, null);
    }

    static /* synthetic */ void logEvent$default(WsContext wsContext, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "";
        }
        DevLoggerPluginKt.logEvent(wsContext, string, string2);
    }

    private static final void wsDevLogger$lambda$4(WsConnectContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        DevLoggerPluginKt.logEvent$default(ctx, "onConnect", null, 2, null);
    }

    private static final void wsDevLogger$lambda$5(WsMessageContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        DevLoggerPluginKt.logEvent(ctx, "onMessage", "Message (next line):\n" + ctx.message());
    }

    private static final void wsDevLogger$lambda$6(WsBinaryMessageContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        DevLoggerPluginKt.logEvent(ctx, "onBinaryMessage", "Offset: " + ctx.offset() + ", Length: " + ctx.length() + "\nMessage (next line):\n" + ctx.data());
    }

    private static final void wsDevLogger$lambda$7(WsCloseContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        WsContext wsContext = ctx;
        int n = ctx.status();
        String string = ctx.reason();
        if (string == null) {
            string = "No reason was provided";
        }
        DevLoggerPluginKt.logEvent(wsContext, "onClose", "StatusCode: " + n + "\nReason: " + string);
    }

    private static final void wsDevLogger$lambda$8(WsErrorContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Throwable throwable = ctx.error();
        DevLoggerPluginKt.logEvent(ctx, "onError", "Throwable:  " + (throwable != null ? (Serializable)throwable : (Serializable)((Object)"No throwable was provided")));
    }
}

