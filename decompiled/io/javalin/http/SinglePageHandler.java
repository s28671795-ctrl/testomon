/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.SinglePageHandlerKt;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.http.staticfiles.Location;
import io.javalin.util.Util;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0006J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lio/javalin/http/SinglePageHandler;", "", "()V", "pathHandlerMap", "", "", "Lio/javalin/http/Handler;", "pathPageMap", "Lio/javalin/http/SinglePageHandler$Page;", "add", "", "hostedPath", "handler", "filePath", "location", "Lio/javalin/http/staticfiles/Location;", "handle", "", "ctx", "Lio/javalin/http/Context;", "Page", "javalin"})
public final class SinglePageHandler {
    @NotNull
    private final Map<String, Page> pathPageMap = new LinkedHashMap();
    @NotNull
    private final Map<String, Handler> pathHandlerMap = new LinkedHashMap();

    public final void add(@NotNull String hostedPath, @NotNull String filePath, @NotNull Location location) {
        URL uRL;
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        switch (WhenMappings.$EnumSwitchMapping$0[location.ordinal()]) {
            case 1: {
                uRL = Util.getResourceUrl(StringsKt.removePrefix(filePath, (CharSequence)"/"));
                if (uRL != null) break;
                throw new IllegalArgumentException("File at '" + filePath + "' not found. Path should be relative to resource folder.");
            }
            case 2: {
                uRL = Util.INSTANCE.getFileUrl(filePath);
                if (uRL != null) break;
                throw new IllegalArgumentException("External file at '" + filePath + "' not found.");
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        URL url2 = uRL;
        Map<String, Page> map = this.pathPageMap;
        Object object = url2;
        Charset charset = Charsets.UTF_8;
        byte[] byArray = TextStreamsKt.readBytes((URL)object);
        object = new Page(url2, new String(byArray, charset));
        map.put(hostedPath, (Page)object);
    }

    public final void add(@NotNull String hostedPath, @NotNull Handler handler) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.pathHandlerMap.put(hostedPath, handler);
    }

    public final boolean handle(@NotNull Context ctx) {
        String accept;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = ctx.header("Accept");
        if (string == null) {
            string = "";
        }
        if (!(StringsKt.contains$default((CharSequence)(accept = string), "text/html", false, 2, null) || StringsKt.contains$default((CharSequence)accept, "*/*", false, 2, null) || Intrinsics.areEqual(accept, ""))) {
            return false;
        }
        Object object = (Page)SinglePageHandlerKt.access$findByPath(this.pathPageMap, ctx.path());
        if (object != null) {
            Page page = object;
            boolean bl = false;
            ctx.html(page.getHtml(JavalinServletContextKt.isLocalhost(ctx)));
            return true;
        }
        object = (Handler)SinglePageHandlerKt.access$findByPath(this.pathHandlerMap, ctx.path());
        if (object != null) {
            Object handler = object;
            boolean bl = false;
            handler.handle(ctx);
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u000fJ\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2={"Lio/javalin/http/SinglePageHandler$Page;", "", "url", "Ljava/net/URL;", "cachedHtml", "", "(Ljava/net/URL;Ljava/lang/String;)V", "getCachedHtml", "()Ljava/lang/String;", "getUrl", "()Ljava/net/URL;", "component1", "component2", "copy", "equals", "", "other", "getHtml", "reRead", "hashCode", "", "toString", "javalin"})
    public static final class Page {
        @NotNull
        private final URL url;
        @NotNull
        private final String cachedHtml;

        public Page(@NotNull URL url2, @NotNull String cachedHtml) {
            Intrinsics.checkNotNullParameter(url2, "url");
            Intrinsics.checkNotNullParameter(cachedHtml, "cachedHtml");
            this.url = url2;
            this.cachedHtml = cachedHtml;
        }

        @NotNull
        public final URL getUrl() {
            return this.url;
        }

        @NotNull
        public final String getCachedHtml() {
            return this.cachedHtml;
        }

        @NotNull
        public final String getHtml(boolean reRead) {
            String string;
            if (reRead) {
                URL uRL = this.url;
                Charset charset = Charsets.UTF_8;
                byte[] byArray = TextStreamsKt.readBytes(uRL);
                string = new String(byArray, charset);
            } else {
                string = this.cachedHtml;
            }
            return string;
        }

        @NotNull
        public final URL component1() {
            return this.url;
        }

        @NotNull
        public final String component2() {
            return this.cachedHtml;
        }

        @NotNull
        public final Page copy(@NotNull URL url2, @NotNull String cachedHtml) {
            Intrinsics.checkNotNullParameter(url2, "url");
            Intrinsics.checkNotNullParameter(cachedHtml, "cachedHtml");
            return new Page(url2, cachedHtml);
        }

        public static /* synthetic */ Page copy$default(Page page, URL uRL, String string, int n, Object object) {
            if ((n & 1) != 0) {
                uRL = page.url;
            }
            if ((n & 2) != 0) {
                string = page.cachedHtml;
            }
            return page.copy(uRL, string);
        }

        @NotNull
        public String toString() {
            return "Page(url=" + this.url + ", cachedHtml=" + this.cachedHtml + ")";
        }

        public int hashCode() {
            int result = this.url.hashCode();
            result = result * 31 + this.cachedHtml.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Page)) {
                return false;
            }
            Page page = (Page)other;
            if (!Intrinsics.areEqual(this.url, page.url)) {
                return false;
            }
            return Intrinsics.areEqual(this.cachedHtml, page.cachedHtml);
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Location.values().length];
            try {
                nArray[Location.CLASSPATH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Location.EXTERNAL.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

