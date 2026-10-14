/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000$\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0012\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001\u001a\u001c\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001\u001a\u0012\u0010\f\u001a\u00020\t*\u00020\n2\u0006\u0010\r\u001a\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"SAME_SITE", "", "addSameSite", "cookie", "Lio/javalin/http/Cookie;", "getCookie", "Ljakarta/servlet/http/HttpServletRequest;", "name", "removeCookie", "", "Ljakarta/servlet/http/HttpServletResponse;", "path", "setJavalinCookie", "javalinCookie", "javalin"})
@SourceDebugExtension(value={"SMAP\nCookie.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Cookie.kt\nio/javalin/http/CookieKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,66:1\n1#2:67\n1855#3,2:68\n*S KotlinDebug\n*F\n+ 1 Cookie.kt\nio/javalin/http/CookieKt\n*L\n48#1:68,2\n*E\n"})
public final class CookieKt {
    @NotNull
    public static final String SAME_SITE = "SameSite";

    public static final void setJavalinCookie(@NotNull HttpServletResponse $this$setJavalinCookie, @NotNull Cookie javalinCookie) {
        String string;
        Intrinsics.checkNotNullParameter($this$setJavalinCookie, "<this>");
        Intrinsics.checkNotNullParameter(javalinCookie, "javalinCookie");
        Object object = new jakarta.servlet.http.Cookie(javalinCookie.getName(), javalinCookie.getValue());
        jakarta.servlet.http.Cookie $this$setJavalinCookie_u24lambda_u240 = object;
        boolean bl = false;
        $this$setJavalinCookie_u24lambda_u240.setPath(javalinCookie.getPath());
        $this$setJavalinCookie_u24lambda_u240.setMaxAge(javalinCookie.getMaxAge());
        $this$setJavalinCookie_u24lambda_u240.setSecure(javalinCookie.getSecure());
        $this$setJavalinCookie_u24lambda_u240.setVersion(javalinCookie.getVersion());
        $this$setJavalinCookie_u24lambda_u240.setHttpOnly(javalinCookie.isHttpOnly());
        $this$setJavalinCookie_u24lambda_u240.setComment(javalinCookie.getComment());
        if (javalinCookie.getDomain() != null) {
            $this$setJavalinCookie_u24lambda_u240.setDomain(javalinCookie.getDomain());
        }
        jakarta.servlet.http.Cookie cookie = object;
        $this$setJavalinCookie.addCookie(cookie);
        object = $this$setJavalinCookie.getHeaders("Set-Cookie");
        List cookies = CollectionsKt.toMutableList(object == null ? (Collection)CollectionsKt.emptyList() : object);
        boolean bl2 = false;
        cookies.removeIf(arg_0 -> CookieKt.setJavalinCookie$lambda$4$lambda$1(new Function1<String, Boolean>(cookie){
            final /* synthetic */ jakarta.servlet.http.Cookie $cookie;
            {
                this.$cookie = $cookie;
                super(1);
            }

            /*
             * Enabled aggressive block sorting
             */
            @NotNull
            public final Boolean invoke(String it) {
                boolean bl;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (StringsKt.startsWith$default(it, this.$cookie.getName() + "=", false, 2, null)) {
                    CharSequence charSequence = it;
                    String string = this.$cookie.getValue();
                    Intrinsics.checkNotNullExpressionValue(string, "cookie.value");
                    if (!StringsKt.contains$default(charSequence, string, false, 2, null)) {
                        bl = true;
                        return bl;
                    }
                }
                bl = false;
                return bl;
            }
        }, arg_0));
        String string2 = string = (String)CollectionsKt.removeFirst(cookies);
        if (string2 != null) {
            Intrinsics.checkNotNullExpressionValue(string2, "removeFirst()");
            String first = string;
            boolean bl3 = false;
            $this$setJavalinCookie.setHeader("Set-Cookie", CookieKt.addSameSite(first, javalinCookie));
        }
        Iterable $this$forEach$iv = cookies;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String remaining = (String)element$iv;
            boolean bl4 = false;
            Intrinsics.checkNotNullExpressionValue(remaining, "remaining");
            $this$setJavalinCookie.addHeader("Set-Cookie", CookieKt.addSameSite(remaining, javalinCookie));
        }
    }

    /*
     * WARNING - void declaration
     */
    public static final void removeCookie(@NotNull HttpServletResponse $this$removeCookie, @NotNull String name, @Nullable String path) {
        void $this$removeCookie_u24lambda_u245;
        jakarta.servlet.http.Cookie cookie;
        Intrinsics.checkNotNullParameter($this$removeCookie, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        jakarta.servlet.http.Cookie cookie2 = cookie = new jakarta.servlet.http.Cookie(name, "");
        HttpServletResponse httpServletResponse = $this$removeCookie;
        boolean bl = false;
        $this$removeCookie_u24lambda_u245.setPath(path);
        $this$removeCookie_u24lambda_u245.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static final String getCookie(@NotNull HttpServletRequest $this$getCookie, @NotNull String name) {
        jakarta.servlet.http.Cookie cookie;
        jakarta.servlet.http.Cookie[] cookieArray;
        block2: {
            Intrinsics.checkNotNullParameter($this$getCookie, "<this>");
            Intrinsics.checkNotNullParameter(name, "name");
            jakarta.servlet.http.Cookie[] cookieArray2 = $this$getCookie.getCookies();
            cookieArray = cookieArray2;
            if (cookieArray2 == null) return null;
            jakarta.servlet.http.Cookie[] cookieArray3 = cookieArray;
            int n = 0;
            int n2 = cookieArray3.length;
            while (n < n2) {
                jakarta.servlet.http.Cookie it = cookie = cookieArray3[n];
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getName(), name)) {
                    ++n;
                    continue;
                }
                break block2;
            }
            return null;
        }
        jakarta.servlet.http.Cookie cookie2 = cookie;
        cookieArray = cookie2;
        if (cookie2 == null) return null;
        String string = cookieArray.getValue();
        return string;
    }

    @NotNull
    public static final String addSameSite(@NotNull String $this$addSameSite, @NotNull Cookie cookie) {
        Intrinsics.checkNotNullParameter($this$addSameSite, "<this>");
        Intrinsics.checkNotNullParameter(cookie, "cookie");
        if (cookie.getSameSite() == null || StringsKt.contains$default((CharSequence)$this$addSameSite, SAME_SITE, false, 2, null)) {
            return $this$addSameSite;
        }
        return $this$addSameSite + "; " + cookie.getSameSite();
    }

    private static final boolean setJavalinCookie$lambda$4$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

