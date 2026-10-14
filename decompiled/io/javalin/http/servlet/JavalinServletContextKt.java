/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.HttpStatus;
import io.javalin.security.BasicAuthCredentials;
import io.javalin.util.JavalinLogger;
import jakarta.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000X\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\u001a\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a7\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0004\b\u0000\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\f\u001a \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u001a7\u0010\u0013\u001a\u0004\u0018\u0001H\b\"\u0004\b\u0000\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\f\u001a\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001\u001a#\u0010\u0017\u001a\u0004\u0018\u0001H\b\"\u0004\b\u0000\u0010\b2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0018\u001a\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a,\u0010\u001a\u001a\u00020\u00012\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001c2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001\u001a\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"\u001a(\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010$0\u001c2\u0006\u0010%\u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u0001\u001a\u000e\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u0001\u001a\n\u0010(\u001a\u00020\u0004*\u00020\u0006\u001a\n\u0010)\u001a\u00020\u000e*\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2={"MAX_REQUEST_SIZE_KEY", "", "SESSION_CACHE_KEY_PREFIX", "acceptsHtml", "", "ctx", "Lio/javalin/http/Context;", "attributeOrCompute", "T", "callback", "Lkotlin/Function1;", "key", "(Lkotlin/jvm/functions/Function1;Ljava/lang/String;Lio/javalin/http/Context;)Ljava/lang/Object;", "cacheAndSetSessionAttribute", "", "value", "", "req", "Ljakarta/servlet/http/HttpServletRequest;", "cachedSessionAttributeOrCompute", "getBasicAuthCredentials", "Lio/javalin/security/BasicAuthCredentials;", "authorizationHeader", "getCachedRequestAttributeOrSessionAttribute", "(Ljava/lang/String;Ljakarta/servlet/http/HttpServletRequest;)Ljava/lang/Object;", "getRequestCharset", "pathParamOrThrow", "pathParams", "", "url", "readAndResetStreamIfPossible", "stream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "splitKeyValueStringAndGroupByKey", "", "string", "urlDecode", "s", "isLocalhost", "throwContentTooLargeIfContentTooLarge", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinServletContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinServletContext.kt\nio/javalin/http/servlet/JavalinServletContextKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,219:1\n1#2:220\n1549#3:221\n1620#3,3:222\n1490#3:225\n1520#3,3:226\n1523#3,3:236\n1238#3,4:241\n361#4,7:229\n442#4:239\n392#4:240\n*S KotlinDebug\n*F\n+ 1 JavalinServletContext.kt\nio/javalin/http/servlet/JavalinServletContextKt\n*L\n143#1:221\n143#1:222,3\n143#1:225\n143#1:226,3\n143#1:236,3\n146#1:241,4\n143#1:229,7\n146#1:239\n146#1:240\n*E\n"})
public final class JavalinServletContextKt {
    @NotNull
    public static final String MAX_REQUEST_SIZE_KEY = "javalin-max-request-size";
    @NotNull
    public static final String SESSION_CACHE_KEY_PREFIX = "javalin-session-attribute-cache-";

    @Nullable
    public static final String getRequestCharset(@NotNull Context ctx) {
        String string;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string2 = ctx.req().getHeader("Content-Type");
        if (string2 != null) {
            Object v1;
            block5: {
                String value = string2;
                boolean bl = false;
                String[] stringArray = new String[]{";"};
                Iterable iterable = StringsKt.split$default((CharSequence)value, stringArray, false, 0, 6, null);
                for (Object object : iterable) {
                    String it = (String)object;
                    boolean bl2 = false;
                    if (!StringsKt.startsWith(((Object)StringsKt.trim((CharSequence)it)).toString(), "charset", true)) continue;
                    v1 = object;
                    break block5;
                }
                v1 = null;
            }
            String string3 = v1;
            if (string3 != null) {
                Object object;
                String it = string3;
                boolean bl = false;
                object = new String[]{"="};
                string = ((Object)StringsKt.trim((CharSequence)((String)StringsKt.split$default((CharSequence)it, object, false, 0, 6, null).get(1)))).toString();
            } else {
                string = null;
            }
        } else {
            string = null;
        }
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Map<String, List<String>> splitKeyValueStringAndGroupByKey(@NotNull String string, @NotNull String charset) {
        Object object;
        Intrinsics.checkNotNullParameter(string, "string");
        Intrinsics.checkNotNullParameter(charset, "charset");
        if (((CharSequence)string).length() == 0) {
            object = MapsKt.emptyMap();
        } else {
            void $this$mapValuesTo$iv$iv;
            Object value$iv$iv$iv;
            Object key$iv$iv;
            Object $this$groupByTo$iv$iv;
            List it;
            Object object2;
            Iterable $this$mapTo$iv$iv;
            String[] stringArray = new String[]{"&"};
            Iterable $this$map$iv = StringsKt.split$default((CharSequence)string, stringArray, false, 0, 6, null);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Object destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                String string2 = (String)item$iv$iv;
                object2 = destination$iv$iv;
                boolean bl = false;
                String[] stringArray2 = new String[]{"="};
                object2.add(StringsKt.split$default((CharSequence)((Object)it), stringArray2, false, 2, 2, null));
            }
            Iterable $this$groupBy$iv = (List)destination$iv$iv;
            boolean $i$f$groupBy = false;
            $this$mapTo$iv$iv = $this$groupBy$iv;
            destination$iv$iv = new LinkedHashMap();
            boolean $i$f$groupByTo = false;
            Iterator iterator2 = $this$groupByTo$iv$iv.iterator();
            while (iterator2.hasNext()) {
                Object object3;
                Object element$iv$iv = iterator2.next();
                it = (List)element$iv$iv;
                boolean bl = false;
                key$iv$iv = URLDecoder.decode((String)it.get(0), charset);
                Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
                boolean $i$f$getOrPut = false;
                value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    boolean bl2 = false;
                    List answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    object3 = answer$iv$iv$iv;
                } else {
                    object3 = value$iv$iv$iv;
                }
                List list$iv$iv = (List)object3;
                it = (List)element$iv$iv;
                object2 = list$iv$iv;
                boolean bl3 = false;
                object2.add(it.size() > 1 ? URLDecoder.decode((String)it.get(1), charset) : "");
            }
            Object $this$mapValues$iv = destination$iv$iv;
            boolean $i$f$mapValues = false;
            $this$groupByTo$iv$iv = $this$mapValues$iv;
            destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
            boolean $i$f$mapValuesTo = false;
            Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
            boolean $i$f$associateByTo = false;
            for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                void it2;
                void it$iv$iv;
                key$iv$iv = (Map.Entry)element$iv$iv$iv;
                Object object4 = destination$iv$iv;
                boolean bl = false;
                value$iv$iv$iv = (Map.Entry)element$iv$iv$iv;
                Object k = it$iv$iv.getKey();
                object2 = object4;
                boolean bl4 = false;
                List list = CollectionsKt.toList((Iterable)it2.getValue());
                object2.put(k, list);
            }
            object = destination$iv$iv;
        }
        return object;
    }

    @NotNull
    public static final String pathParamOrThrow(@NotNull Map<String, String> pathParams, @NotNull String key, @NotNull String url2) {
        Intrinsics.checkNotNullParameter(pathParams, "pathParams");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(url2, "url");
        String string = pathParams.get(StringsKt.replaceFirst$default(StringsKt.replaceFirst$default(key, "{", "", false, 4, null), "}", "", false, 4, null));
        if (string == null) {
            throw new IllegalArgumentException("'" + key + "' is not a valid path-param for '" + url2 + "'.");
        }
        return string;
    }

    @NotNull
    public static final String urlDecode(@NotNull String s2) {
        Intrinsics.checkNotNullParameter(s2, "s");
        String string = URLDecoder.decode(StringsKt.replace$default(s2, "+", "%2B", false, 4, null), "UTF-8");
        Intrinsics.checkNotNullExpressionValue(string, "decode(s.replace(\"+\", \"%2B\"), \"UTF-8\")");
        return StringsKt.replace$default(string, "%2B", "+", false, 4, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static final BasicAuthCredentials getBasicAuthCredentials(@Nullable String authorizationHeader) {
        String string;
        if (authorizationHeader == null) return null;
        String it = string = authorizationHeader;
        boolean bl = false;
        if (!StringsKt.startsWith$default(authorizationHeader, "Basic ", false, 2, null)) return null;
        String string2 = string;
        String string3 = string2;
        if (string3 == null) return null;
        string = StringsKt.removePrefix(string3, (CharSequence)"Basic ");
        if (string == null) return null;
        String it2 = string;
        boolean bl2 = false;
        byte[] byArray = Base64.getDecoder().decode(it2);
        Intrinsics.checkNotNullExpressionValue(byArray, "getDecoder().decode(it)");
        String string4 = StringsKt.decodeToString(byArray);
        if (string4 == null) return null;
        char[] cArray = new char[]{':'};
        List list = StringsKt.split$default((CharSequence)string4, cArray, false, 2, 2, null);
        if (list == null) return null;
        List list2 = list;
        boolean bl3 = false;
        String username = (String)list2.get(0);
        String password = (String)list2.get(1);
        BasicAuthCredentials basicAuthCredentials = new BasicAuthCredentials(username, password);
        return basicAuthCredentials;
    }

    public static final boolean acceptsHtml(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = ctx.header("Accept");
        return string != null ? StringsKt.contains$default((CharSequence)string, "text/html", false, 2, null) : false;
    }

    public static final boolean isLocalhost(@NotNull Context $this$isLocalhost) {
        boolean bl;
        Intrinsics.checkNotNullParameter($this$isLocalhost, "<this>");
        try {
            String it = new URL($this$isLocalhost.url()).getHost();
            boolean bl2 = false;
            bl = Intrinsics.areEqual(it, "localhost") || Intrinsics.areEqual(it, "127.0.0.1");
        }
        catch (Exception e) {
            bl = false;
        }
        return bl;
    }

    public static final void throwContentTooLargeIfContentTooLarge(@NotNull Context $this$throwContentTooLargeIfContentTooLarge) {
        Intrinsics.checkNotNullParameter($this$throwContentTooLargeIfContentTooLarge, "<this>");
        long maxRequestSize = ((Number)$this$throwContentTooLargeIfContentTooLarge.appAttribute(MAX_REQUEST_SIZE_KEY)).longValue();
        if ((long)$this$throwContentTooLargeIfContentTooLarge.req().getContentLength() > maxRequestSize) {
            JavalinLogger.warn$default("Body greater than max size (" + maxRequestSize + " bytes)", null, 2, null);
            throw new HttpResponseException(HttpStatus.CONTENT_TOO_LARGE, HttpStatus.CONTENT_TOO_LARGE.getMessage(), null, 4, null);
        }
    }

    public static final void cacheAndSetSessionAttribute(@NotNull String key, @Nullable Object value, @NotNull HttpServletRequest req) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(req, "req");
        req.setAttribute(SESSION_CACHE_KEY_PREFIX + key, value);
        req.getSession().setAttribute(key, value);
    }

    @Nullable
    public static final <T> T getCachedRequestAttributeOrSessionAttribute(@NotNull String key, @NotNull HttpServletRequest req) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(req, "req");
        Object cachedAttribute = req.getAttribute(SESSION_CACHE_KEY_PREFIX + key);
        if (cachedAttribute == null) {
            req.setAttribute(SESSION_CACHE_KEY_PREFIX + key, req.getSession().getAttribute(key));
        }
        return (T)req.getAttribute(SESSION_CACHE_KEY_PREFIX + key);
    }

    @Nullable
    public static final <T> T cachedSessionAttributeOrCompute(@NotNull Function1<? super Context, ? extends T> callback, @NotNull String key, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (JavalinServletContextKt.getCachedRequestAttributeOrSessionAttribute(key, ctx.req()) == null) {
            JavalinServletContextKt.cacheAndSetSessionAttribute(key, callback.invoke(ctx), ctx.req());
        }
        return JavalinServletContextKt.getCachedRequestAttributeOrSessionAttribute(key, ctx.req());
    }

    @Nullable
    public static final <T> T attributeOrCompute(@NotNull Function1<? super Context, ? extends T> callback, @NotNull String key, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (ctx.attribute(key) == null) {
            ctx.attribute(key, callback.invoke(ctx));
        }
        return ctx.attribute(key);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final String readAndResetStreamIfPossible(@Nullable InputStream stream, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "charset");
        try {
            if (stream == null) ** GOTO lbl-1000
            var3_2 = stream;
            $this$readAndResetStreamIfPossible_u24lambda_u2411 = var3_2;
            $i$a$-apply-JavalinServletContextKt$readAndResetStreamIfPossible$1 = false;
            $this$readAndResetStreamIfPossible_u24lambda_u2411.reset();
            var3_2 = ByteStreamsKt.readBytes((InputStream)var3_2);
            if (var3_2 != null) {
                v0 = new String((byte[])var3_2, charset);
            } else lbl-1000:
            // 2 sources

            {
                v0 = null;
            }
            it = var2_7 = v0;
            $i$a$-also-JavalinServletContextKt$readAndResetStreamIfPossible$2 = false;
            v1 = stream;
            if (v1 != null) {
                v1.reset();
            }
        }
        catch (Exception e) {
            var2_7 = "resultString unavailable (resultStream couldn't be reset)";
        }
        return var2_7;
    }
}

