/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.BadGatewayResponse;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.GatewayTimeoutResponse;
import io.javalin.http.GoneResponse;
import io.javalin.http.HttpResponseException;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.MethodNotAllowedResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.RedirectResponse;
import io.javalin.http.ServiceUnavailableResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.util.JsonEscapeUtil;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000eJ\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\f\u0010\u0012\u001a\u00020\u0004*\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lio/javalin/http/servlet/HttpResponseExceptionMapper;", "", "()V", "docsUrl", "", "canHandle", "", "t", "", "classUrl", "e", "Lio/javalin/http/HttpResponseException;", "getTypeUrl", "handle", "Lio/javalin/http/Context;", "ctx", "jsonResult", "plainResult", "jsonEscape", "javalin"})
@SourceDebugExtension(value={"SMAP\nHttpResponseExceptionMapper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HttpResponseExceptionMapper.kt\nio/javalin/http/servlet/HttpResponseExceptionMapper\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,78:1\n125#2:79\n152#2,3:80\n125#2:83\n152#2,3:84\n*S KotlinDebug\n*F\n+ 1 HttpResponseExceptionMapper.kt\nio/javalin/http/servlet/HttpResponseExceptionMapper\n*L\n45#1:79\n45#1:80,3\n51#1:83\n51#1:84,3\n*E\n"})
public final class HttpResponseExceptionMapper {
    @NotNull
    public static final HttpResponseExceptionMapper INSTANCE = new HttpResponseExceptionMapper();
    @NotNull
    private static final String docsUrl = "https://javalin.io/documentation#";

    private HttpResponseExceptionMapper() {
    }

    public final boolean canHandle(@NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(t2, "t");
        return HttpResponseException.class.isAssignableFrom(t2.getClass());
    }

    @NotNull
    public final Context handle(@NotNull HttpResponseException e, @NotNull Context ctx) {
        Context context;
        Intrinsics.checkNotNullParameter(e, "e");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = ctx.header("Accept");
        boolean bl = string != null ? StringsKt.contains$default((CharSequence)string, "text/html", false, 2, null) : false;
        if (bl || Intrinsics.areEqual(ctx.res().getContentType(), "text/html")) {
            context = ctx.status(e.getStatus()).result(this.plainResult(e)).contentType(ContentType.TEXT_PLAIN);
        } else {
            String string2 = ctx.header("Accept");
            context = (string2 != null ? StringsKt.contains$default((CharSequence)string2, "application/json", false, 2, null) : false) || Intrinsics.areEqual(ctx.res().getContentType(), "application/json") ? ctx.status(e.getStatus()).result(this.jsonResult(e)).contentType(ContentType.APPLICATION_JSON) : ctx.status(e.getStatus()).result(this.plainResult(e)).contentType(ContentType.TEXT_PLAIN);
        }
        return context;
    }

    /*
     * WARNING - void declaration
     */
    private final String jsonResult(HttpResponseException e) {
        Collection<CallSite> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        String string = e.getMessage();
        String string2 = string != null ? this.jsonEscape(string) : null;
        int n = e.getStatus();
        Object object = this.getTypeUrl(e);
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string3 = ((String)object).toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(locale)");
        object = e.getDetails();
        String string4 = string3;
        int n2 = n;
        String string5 = string2;
        boolean $i$f$map = false;
        void var4_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.size());
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            void it;
            Map.Entry item$iv$iv;
            Map.Entry entry = item$iv$iv = iterator2.next();
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add((CallSite)((Object)("\"" + it.getKey() + "\":\"" + INSTANCE.jsonEscape((String)it.getValue()) + "\"")));
        }
        collection = (List)destination$iv$iv;
        return StringsKt.trimMargin$default("|{\n           |    \"title\": \"" + string5 + "\",\n           |    \"status\": " + n2 + ",\n           |    \"type\": \"" + string4 + "\",\n           |    \"details\": {" + CollectionsKt.joinToString$default(collection, ",", null, null, 0, null, null, 62, null) + "}\n           |}", null, 1, null);
    }

    /*
     * WARNING - void declaration
     */
    private final String plainResult(HttpResponseException e) {
        String string;
        if (e.getDetails().isEmpty()) {
            string = String.valueOf(e.getMessage());
        } else {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Map<String, String> map = e.getDetails();
            String string2 = e.getMessage();
            boolean $i$f$map = false;
            void var4_5 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList($this$map$iv.size());
            boolean $i$f$mapTo = false;
            Iterator iterator2 = $this$mapTo$iv$iv.entrySet().iterator();
            while (iterator2.hasNext()) {
                void it;
                Map.Entry item$iv$iv;
                Map.Entry entry = item$iv$iv = iterator2.next();
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add("\n" + it.getKey() + ":\n" + it.getValue());
            }
            string = StringsKt.trimMargin$default("\n                |" + string2 + "\n                |" + CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, null, 62, null) + "\n                |", null, 1, null);
        }
        return string;
    }

    private final String classUrl(HttpResponseException e) {
        return docsUrl + e.getClass().getSimpleName();
    }

    private final String getTypeUrl(HttpResponseException e) {
        HttpResponseException httpResponseException = e;
        return httpResponseException instanceof RedirectResponse ? this.classUrl(e) : (httpResponseException instanceof BadRequestResponse ? this.classUrl(e) : (httpResponseException instanceof UnauthorizedResponse ? this.classUrl(e) : (httpResponseException instanceof ForbiddenResponse ? this.classUrl(e) : (httpResponseException instanceof NotFoundResponse ? this.classUrl(e) : (httpResponseException instanceof MethodNotAllowedResponse ? this.classUrl(e) : (httpResponseException instanceof ConflictResponse ? this.classUrl(e) : (httpResponseException instanceof GoneResponse ? this.classUrl(e) : (httpResponseException instanceof InternalServerErrorResponse ? this.classUrl(e) : (httpResponseException instanceof ServiceUnavailableResponse ? this.classUrl(e) : (httpResponseException instanceof BadGatewayResponse ? this.classUrl(e) : (httpResponseException instanceof GatewayTimeoutResponse ? this.classUrl(e) : "https://javalin.io/documentation#error-responses")))))))))));
    }

    private final String jsonEscape(String $this$jsonEscape) {
        return JsonEscapeUtil.INSTANCE.escape($this$jsonEscape);
    }
}

