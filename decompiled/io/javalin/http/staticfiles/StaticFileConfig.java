/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.staticfiles;

import io.javalin.http.staticfiles.Location;
import io.javalin.http.staticfiles.MimeTypesConfig;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\f\u0012\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u0015\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\u0015\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000eH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0011H\u00c6\u0003Js\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\r\u0010 \u001a\u00020\u0003H\u0000\u00a2\u0006\u0002\b!J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\t\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000e8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lio/javalin/http/staticfiles/StaticFileConfig;", "", "hostedPath", "", "directory", "location", "Lio/javalin/http/staticfiles/Location;", "precompress", "", "aliasCheck", "Lorg/eclipse/jetty/server/handler/ContextHandler$AliasCheck;", "headers", "", "skipFileFunction", "Lkotlin/Function1;", "Ljakarta/servlet/http/HttpServletRequest;", "mimeTypes", "Lio/javalin/http/staticfiles/MimeTypesConfig;", "(Ljava/lang/String;Ljava/lang/String;Lio/javalin/http/staticfiles/Location;ZLorg/eclipse/jetty/server/handler/ContextHandler$AliasCheck;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lio/javalin/http/staticfiles/MimeTypesConfig;)V", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "refinedToString", "refinedToString$javalin", "toString", "javalin"})
public final class StaticFileConfig {
    @JvmField
    @NotNull
    public String hostedPath;
    @JvmField
    @NotNull
    public String directory;
    @JvmField
    @NotNull
    public Location location;
    @JvmField
    public boolean precompress;
    @JvmField
    @Nullable
    public ContextHandler.AliasCheck aliasCheck;
    @JvmField
    @NotNull
    public Map<String, String> headers;
    @JvmField
    @NotNull
    public Function1<? super HttpServletRequest, Boolean> skipFileFunction;
    @JvmField
    @NotNull
    public final MimeTypesConfig mimeTypes;

    public StaticFileConfig(@NotNull String hostedPath, @NotNull String directory, @NotNull Location location, boolean precompress, @Nullable ContextHandler.AliasCheck aliasCheck, @NotNull Map<String, String> headers, @NotNull Function1<? super HttpServletRequest, Boolean> skipFileFunction, @NotNull MimeTypesConfig mimeTypes) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(directory, "directory");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        Intrinsics.checkNotNullParameter(headers, "headers");
        Intrinsics.checkNotNullParameter(skipFileFunction, "skipFileFunction");
        Intrinsics.checkNotNullParameter(mimeTypes, "mimeTypes");
        this.hostedPath = hostedPath;
        this.directory = directory;
        this.location = location;
        this.precompress = precompress;
        this.aliasCheck = aliasCheck;
        this.headers = headers;
        this.skipFileFunction = skipFileFunction;
        this.mimeTypes = mimeTypes;
    }

    public /* synthetic */ StaticFileConfig(String string, String string2, Location location, boolean bl, ContextHandler.AliasCheck aliasCheck, Map map, Function1 function1, MimeTypesConfig mimeTypesConfig, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "/";
        }
        if ((n & 2) != 0) {
            string2 = "/public";
        }
        if ((n & 4) != 0) {
            location = Location.CLASSPATH;
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        if ((n & 0x10) != 0) {
            aliasCheck = null;
        }
        if ((n & 0x20) != 0) {
            Pair[] pairArray = new Pair[]{TuplesKt.to("Cache-Control", "max-age=0")};
            map = MapsKt.mutableMapOf(pairArray);
        }
        if ((n & 0x40) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 0x80) != 0) {
            mimeTypesConfig = new MimeTypesConfig();
        }
        this(string, string2, location, bl, aliasCheck, map, function1, mimeTypesConfig);
    }

    @NotNull
    public final String refinedToString$javalin() {
        return StringsKt.replace$default(this.toString(), ", skipFileFunction=(jakarta.servlet.http.HttpServletRequest) -> kotlin.Boolean", "", false, 4, null);
    }

    @NotNull
    public final String component1() {
        return this.hostedPath;
    }

    @NotNull
    public final String component2() {
        return this.directory;
    }

    @NotNull
    public final Location component3() {
        return this.location;
    }

    public final boolean component4() {
        return this.precompress;
    }

    @Nullable
    public final ContextHandler.AliasCheck component5() {
        return this.aliasCheck;
    }

    @NotNull
    public final Map<String, String> component6() {
        return this.headers;
    }

    @NotNull
    public final Function1<HttpServletRequest, Boolean> component7() {
        return this.skipFileFunction;
    }

    @NotNull
    public final MimeTypesConfig component8() {
        return this.mimeTypes;
    }

    @NotNull
    public final StaticFileConfig copy(@NotNull String hostedPath, @NotNull String directory, @NotNull Location location, boolean precompress, @Nullable ContextHandler.AliasCheck aliasCheck, @NotNull Map<String, String> headers, @NotNull Function1<? super HttpServletRequest, Boolean> skipFileFunction, @NotNull MimeTypesConfig mimeTypes) {
        Intrinsics.checkNotNullParameter(hostedPath, "hostedPath");
        Intrinsics.checkNotNullParameter(directory, "directory");
        Intrinsics.checkNotNullParameter((Object)location, "location");
        Intrinsics.checkNotNullParameter(headers, "headers");
        Intrinsics.checkNotNullParameter(skipFileFunction, "skipFileFunction");
        Intrinsics.checkNotNullParameter(mimeTypes, "mimeTypes");
        return new StaticFileConfig(hostedPath, directory, location, precompress, aliasCheck, headers, skipFileFunction, mimeTypes);
    }

    public static /* synthetic */ StaticFileConfig copy$default(StaticFileConfig staticFileConfig, String string, String string2, Location location, boolean bl, ContextHandler.AliasCheck aliasCheck, Map map, Function1 function1, MimeTypesConfig mimeTypesConfig, int n, Object object) {
        if ((n & 1) != 0) {
            string = staticFileConfig.hostedPath;
        }
        if ((n & 2) != 0) {
            string2 = staticFileConfig.directory;
        }
        if ((n & 4) != 0) {
            location = staticFileConfig.location;
        }
        if ((n & 8) != 0) {
            bl = staticFileConfig.precompress;
        }
        if ((n & 0x10) != 0) {
            aliasCheck = staticFileConfig.aliasCheck;
        }
        if ((n & 0x20) != 0) {
            map = staticFileConfig.headers;
        }
        if ((n & 0x40) != 0) {
            function1 = staticFileConfig.skipFileFunction;
        }
        if ((n & 0x80) != 0) {
            mimeTypesConfig = staticFileConfig.mimeTypes;
        }
        return staticFileConfig.copy(string, string2, location, bl, aliasCheck, map, function1, mimeTypesConfig);
    }

    @NotNull
    public String toString() {
        return "StaticFileConfig(hostedPath=" + this.hostedPath + ", directory=" + this.directory + ", location=" + this.location + ", precompress=" + this.precompress + ", aliasCheck=" + this.aliasCheck + ", headers=" + this.headers + ", skipFileFunction=" + this.skipFileFunction + ", mimeTypes=" + this.mimeTypes + ")";
    }

    public int hashCode() {
        int result = this.hostedPath.hashCode();
        result = result * 31 + this.directory.hashCode();
        result = result * 31 + this.location.hashCode();
        int n = this.precompress ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + (this.aliasCheck == null ? 0 : this.aliasCheck.hashCode());
        result = result * 31 + ((Object)this.headers).hashCode();
        result = result * 31 + this.skipFileFunction.hashCode();
        result = result * 31 + this.mimeTypes.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StaticFileConfig)) {
            return false;
        }
        StaticFileConfig staticFileConfig = (StaticFileConfig)other;
        if (!Intrinsics.areEqual(this.hostedPath, staticFileConfig.hostedPath)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.directory, staticFileConfig.directory)) {
            return false;
        }
        if (this.location != staticFileConfig.location) {
            return false;
        }
        if (this.precompress != staticFileConfig.precompress) {
            return false;
        }
        if (!Intrinsics.areEqual(this.aliasCheck, staticFileConfig.aliasCheck)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.headers, staticFileConfig.headers)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.skipFileFunction, staticFileConfig.skipFileFunction)) {
            return false;
        }
        return Intrinsics.areEqual(this.mimeTypes, staticFileConfig.mimeTypes);
    }

    public StaticFileConfig() {
        this(null, null, null, false, null, null, null, null, 255, null);
    }
}

