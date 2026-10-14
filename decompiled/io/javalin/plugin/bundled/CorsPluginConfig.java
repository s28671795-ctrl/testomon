/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.plugin.bundled.CorsUtils;
import io.javalin.plugin.bundled.WildcardResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0002\b\u0010\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b\u00a2\u0006\u0002\u0010\rJ'\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0012\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0012\"\u00020\u0006\u00a2\u0006\u0002\u0010\u0013J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014J\u0006\u0010\u0015\u001a\u00020\u000fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u00c2\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u00c2\u0003J[\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u0006J\t\u0010\"\u001a\u00020\tH\u00d6\u0001J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014J\t\u0010#\u001a\u00020\u0006H\u00d6\u0001R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2={"Lio/javalin/plugin/bundled/CorsPluginConfig;", "", "allowCredentials", "", "reflectClientOrigin", "defaultScheme", "", "path", "maxAge", "", "allowedOrigins", "", "headersToExpose", "(ZZLjava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;)V", "allowHost", "", "host", "others", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "", "anyHost", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "exposeHeader", "header", "hashCode", "toString", "javalin"})
@SourceDebugExtension(value={"SMAP\nCorsPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CorsPlugin.kt\nio/javalin/plugin/bundled/CorsPluginConfig\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,163:1\n1549#2:164\n1620#2,3:165\n1864#2,2:168\n1866#2:171\n1#3:170\n*S KotlinDebug\n*F\n+ 1 CorsPlugin.kt\nio/javalin/plugin/bundled/CorsPluginConfig\n*L\n43#1:164\n43#1:165,3\n43#1:168,2\n43#1:171\n*E\n"})
public final class CorsPluginConfig {
    @JvmField
    public boolean allowCredentials;
    @JvmField
    public boolean reflectClientOrigin;
    @JvmField
    @NotNull
    public String defaultScheme;
    @JvmField
    @NotNull
    public String path;
    @JvmField
    public int maxAge;
    @NotNull
    private final List<String> allowedOrigins;
    @NotNull
    private final List<String> headersToExpose;

    public CorsPluginConfig(boolean allowCredentials, boolean reflectClientOrigin, @NotNull String defaultScheme, @NotNull String path, int maxAge, @NotNull List<String> allowedOrigins, @NotNull List<String> headersToExpose) {
        Intrinsics.checkNotNullParameter(defaultScheme, "defaultScheme");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(allowedOrigins, "allowedOrigins");
        Intrinsics.checkNotNullParameter(headersToExpose, "headersToExpose");
        this.allowCredentials = allowCredentials;
        this.reflectClientOrigin = reflectClientOrigin;
        this.defaultScheme = defaultScheme;
        this.path = path;
        this.maxAge = maxAge;
        this.allowedOrigins = allowedOrigins;
        this.headersToExpose = headersToExpose;
    }

    public /* synthetic */ CorsPluginConfig(boolean bl, boolean bl2, String string, String string2, int n, List list, List list2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            bl = false;
        }
        if ((n2 & 2) != 0) {
            bl2 = false;
        }
        if ((n2 & 4) != 0) {
            string = "https";
        }
        if ((n2 & 8) != 0) {
            string2 = "*";
        }
        if ((n2 & 0x10) != 0) {
            n = -1;
        }
        if ((n2 & 0x20) != 0) {
            list = new ArrayList();
        }
        if ((n2 & 0x40) != 0) {
            list2 = new ArrayList();
        }
        this(bl, bl2, string, string2, n, list, list2);
    }

    @NotNull
    public final List<String> allowedOrigins() {
        List<String> list = Collections.unmodifiableList(this.allowedOrigins);
        Intrinsics.checkNotNullExpressionValue(list, "unmodifiableList(allowedOrigins)");
        return list;
    }

    @NotNull
    public final List<String> headersToExpose() {
        List<String> list = Collections.unmodifiableList(this.headersToExpose);
        Intrinsics.checkNotNullExpressionValue(list, "unmodifiableList(headersToExpose)");
        return list;
    }

    public final void anyHost() {
        this.allowedOrigins.add("*");
    }

    /*
     * WARNING - void declaration
     */
    public final void allowHost(@NotNull String host2, String ... others) {
        void $this$forEachIndexed$iv;
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(host2, "host");
        Intrinsics.checkNotNullParameter(others, "others");
        List origins = CollectionsKt.plus((Collection)CollectionsKt.listOf(host2), (Iterable)ArraysKt.toList(others));
        Iterable $this$map$iv = origins;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            String string = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(CorsUtils.INSTANCE.addSchemeIfMissing$javalin((String)it, this.defaultScheme));
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void it;
            int n;
            Object item$iv$iv;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            item$iv$iv = (String)item$iv;
            int idx = n;
            boolean bl = false;
            if (!(!Intrinsics.areEqual(it, "null"))) {
                boolean $i$a$-require-CorsPluginConfig$allowHost$2$42 = false;
                String $i$a$-require-CorsPluginConfig$allowHost$2$42 = "Adding the string null as an allowed host is forbidden. Consider calling anyHost() instead";
                throw new IllegalArgumentException($i$a$-require-CorsPluginConfig$allowHost$2$42.toString());
            }
            if (!CorsUtils.INSTANCE.isValidOrigin$javalin((String)it)) {
                boolean bl2 = false;
                String string = "The given value '" + origins.get(idx) + "' could not be transformed into a valid origin";
                throw new IllegalArgumentException(string.toString());
            }
            WildcardResult wildcardResult = CorsUtils.INSTANCE.originFulfillsWildcardRequirements$javalin((String)it);
            if (!(!(wildcardResult instanceof WildcardResult.ErrorState))) {
                String string;
                boolean bl3 = false;
                WildcardResult wildcardResult2 = wildcardResult;
                if (Intrinsics.areEqual(wildcardResult2, WildcardResult.ErrorState.TooManyWildcards.INSTANCE)) {
                    string = "Too many wildcards detected inside '" + origins.get(idx) + "'. Only one at the start of the host is allowed!";
                } else if (Intrinsics.areEqual(wildcardResult2, WildcardResult.ErrorState.WildcardNotAtTheStartOfTheHost.INSTANCE)) {
                    string = "The wildcard must be at the start of the passed in host. The value '" + origins.get(idx) + "' violates this requirement!";
                } else {
                    throw new IllegalStateException("This code path should never be hit.\n\nPlease report it to the maintainers of Javalin as a GitHub issue at https://github.com/javalin/javalin/issues/new/choose");
                }
                String string2 = string;
                throw new IllegalArgumentException(string2.toString());
            }
            this.allowedOrigins.add((String)it);
        }
    }

    public final void exposeHeader(@NotNull String header) {
        Intrinsics.checkNotNullParameter(header, "header");
        this.headersToExpose.add(header);
    }

    public final boolean component1() {
        return this.allowCredentials;
    }

    public final boolean component2() {
        return this.reflectClientOrigin;
    }

    @NotNull
    public final String component3() {
        return this.defaultScheme;
    }

    @NotNull
    public final String component4() {
        return this.path;
    }

    public final int component5() {
        return this.maxAge;
    }

    private final List<String> component6() {
        return this.allowedOrigins;
    }

    private final List<String> component7() {
        return this.headersToExpose;
    }

    @NotNull
    public final CorsPluginConfig copy(boolean allowCredentials, boolean reflectClientOrigin, @NotNull String defaultScheme, @NotNull String path, int maxAge, @NotNull List<String> allowedOrigins, @NotNull List<String> headersToExpose) {
        Intrinsics.checkNotNullParameter(defaultScheme, "defaultScheme");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(allowedOrigins, "allowedOrigins");
        Intrinsics.checkNotNullParameter(headersToExpose, "headersToExpose");
        return new CorsPluginConfig(allowCredentials, reflectClientOrigin, defaultScheme, path, maxAge, allowedOrigins, headersToExpose);
    }

    public static /* synthetic */ CorsPluginConfig copy$default(CorsPluginConfig corsPluginConfig, boolean bl, boolean bl2, String string, String string2, int n, List list, List list2, int n2, Object object) {
        if ((n2 & 1) != 0) {
            bl = corsPluginConfig.allowCredentials;
        }
        if ((n2 & 2) != 0) {
            bl2 = corsPluginConfig.reflectClientOrigin;
        }
        if ((n2 & 4) != 0) {
            string = corsPluginConfig.defaultScheme;
        }
        if ((n2 & 8) != 0) {
            string2 = corsPluginConfig.path;
        }
        if ((n2 & 0x10) != 0) {
            n = corsPluginConfig.maxAge;
        }
        if ((n2 & 0x20) != 0) {
            list = corsPluginConfig.allowedOrigins;
        }
        if ((n2 & 0x40) != 0) {
            list2 = corsPluginConfig.headersToExpose;
        }
        return corsPluginConfig.copy(bl, bl2, string, string2, n, list, list2);
    }

    @NotNull
    public String toString() {
        return "CorsPluginConfig(allowCredentials=" + this.allowCredentials + ", reflectClientOrigin=" + this.reflectClientOrigin + ", defaultScheme=" + this.defaultScheme + ", path=" + this.path + ", maxAge=" + this.maxAge + ", allowedOrigins=" + this.allowedOrigins + ", headersToExpose=" + this.headersToExpose + ")";
    }

    public int hashCode() {
        int n;
        int result;
        int n2 = this.allowCredentials ? 1 : 0;
        if (n2 != 0) {
            n2 = result = 1;
        }
        if ((n = this.reflectClientOrigin) != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + this.defaultScheme.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + Integer.hashCode(this.maxAge);
        result = result * 31 + ((Object)this.allowedOrigins).hashCode();
        result = result * 31 + ((Object)this.headersToExpose).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CorsPluginConfig)) {
            return false;
        }
        CorsPluginConfig corsPluginConfig = (CorsPluginConfig)other;
        if (this.allowCredentials != corsPluginConfig.allowCredentials) {
            return false;
        }
        if (this.reflectClientOrigin != corsPluginConfig.reflectClientOrigin) {
            return false;
        }
        if (!Intrinsics.areEqual(this.defaultScheme, corsPluginConfig.defaultScheme)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, corsPluginConfig.path)) {
            return false;
        }
        if (this.maxAge != corsPluginConfig.maxAge) {
            return false;
        }
        if (!Intrinsics.areEqual(this.allowedOrigins, corsPluginConfig.allowedOrigins)) {
            return false;
        }
        return Intrinsics.areEqual(this.headersToExpose, corsPluginConfig.headersToExpose);
    }

    public CorsPluginConfig() {
        this(false, false, null, null, 0, null, null, 127, null);
    }
}

