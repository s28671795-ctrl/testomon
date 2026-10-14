/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.json.JsonMapper;
import io.javalin.json.JsonMapperKt;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.TypesJVMKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u001e\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007H\u0002J\u001c\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\u0006\u0010\u0015\u001a\u00020\u0007H\u0086\u0002\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0002J\u0019\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0001H\u0086\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000e0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lio/javalin/http/util/CookieStore;", "", "ctx", "Lio/javalin/http/Context;", "(Lio/javalin/http/Context;)V", "cookieMap", "", "", "getCtx", "()Lio/javalin/http/Context;", "decoder", "Ljava/util/Base64$Decoder;", "kotlin.jvm.PlatformType", "encoder", "Ljava/util/Base64$Encoder;", "clear", "", "deserialize", "cookie", "get", "T", "key", "(Ljava/lang/String;)Ljava/lang/Object;", "serialize", "map", "set", "value", "Companion", "javalin"})
@SourceDebugExtension(value={"SMAP\nCookieStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CookieStore.kt\nio/javalin/http/util/CookieStore\n+ 2 JsonMapper.kt\nio/javalin/json/JsonMapperKt\n*L\n1#1,61:1\n79#2:62\n73#2:63\n*S KotlinDebug\n*F\n+ 1 CookieStore.kt\nio/javalin/http/util/CookieStore\n*L\n49#1:62\n54#1:63\n*E\n"})
public final class CookieStore {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Context ctx;
    private final Base64.Encoder encoder;
    private final Base64.Decoder decoder;
    @NotNull
    private final Map<String, Object> cookieMap;
    @NotNull
    private static String COOKIE_NAME = "javalin-cookie-store";

    public CookieStore(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.encoder = Base64.getEncoder();
        this.decoder = Base64.getDecoder();
        this.cookieMap = this.deserialize(this.ctx.cookie(COOKIE_NAME));
    }

    @NotNull
    public final Context getCtx() {
        return this.ctx;
    }

    public final <T> T get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (T)this.cookieMap.get(key);
    }

    public final void set(@NotNull String key, @NotNull Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        this.cookieMap.put(key, value);
        this.ctx.cookie(new Cookie(COOKIE_NAME, this.serialize(this.cookieMap), null, 0, false, 0, false, null, null, null, 1020, null));
    }

    public final void clear() {
        this.cookieMap.clear();
        this.ctx.removeCookie(COOKIE_NAME);
    }

    /*
     * WARNING - void declaration
     */
    private final Map<String, Object> deserialize(String cookie) {
        Map map;
        Object object = cookie;
        if (!(object == null || object.length() == 0)) {
            void $this$fromJsonString$iv;
            object = JsonMapperKt.jsonMapper(this.ctx);
            byte[] byArray = this.decoder.decode(cookie);
            Intrinsics.checkNotNullExpressionValue(byArray, "decoder.decode(cookie)");
            byte[] byArray2 = byArray;
            String json$iv = new String(byArray2, Charsets.UTF_8);
            boolean $i$f$fromJsonString = false;
            map = (Map)$this$fromJsonString$iv.fromJsonString(json$iv, TypesJVMKt.getJavaType(Reflection.mutableCollectionType(Reflection.typeOf(Map.class, KTypeProjection.Companion.invariant(Reflection.typeOf(String.class)), KTypeProjection.Companion.invariant(Reflection.typeOf(Object.class))))));
        } else {
            map = new LinkedHashMap();
        }
        return map;
    }

    private final String serialize(Map<String, Object> map) {
        JsonMapper $this$toJsonString$iv = JsonMapperKt.jsonMapper(this.ctx);
        boolean $i$f$toJsonString = false;
        String string = $this$toJsonString$iv.toJsonString(map, TypesJVMKt.getJavaType(Reflection.mutableCollectionType(Reflection.typeOf(Map.class, KTypeProjection.Companion.invariant(Reflection.typeOf(String.class)), KTypeProjection.Companion.invariant(Reflection.typeOf(Object.class))))));
        byte[] byArray = string.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        String string2 = this.encoder.encodeToString(byArray);
        Intrinsics.checkNotNullExpressionValue(string2, "encoder.encodeToString(c\u2026tring(map).toByteArray())");
        return string2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lio/javalin/http/util/CookieStore$Companion;", "", "()V", "COOKIE_NAME", "", "getCOOKIE_NAME", "()Ljava/lang/String;", "setCOOKIE_NAME", "(Ljava/lang/String;)V", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final String getCOOKIE_NAME() {
            return COOKIE_NAME;
        }

        public final void setCOOKIE_NAME(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            COOKIE_NAME = string;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

