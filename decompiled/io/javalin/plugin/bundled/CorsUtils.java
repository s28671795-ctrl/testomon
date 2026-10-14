/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.plugin.bundled.CorsUtilsKt;
import io.javalin.plugin.bundled.OriginParts;
import io.javalin.plugin.bundled.PortResult;
import io.javalin.plugin.bundled.WildcardResult;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u0007J\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u000bJ\u0015\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\rJ\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u0012J\u0015\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u0014J\u0015\u0010\u0015\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u0016J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\u0019J\u001d\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0000\u00a2\u0006\u0002\b\u001eJ\u0015\u0010\u001f\u001a\u00020\u001c2\u0006\u0010\n\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b \u00a8\u0006!"}, d2={"Lio/javalin/plugin/bundled/CorsUtils;", "", "()V", "addSchemeIfMissing", "", "host", "defaultScheme", "addSchemeIfMissing$javalin", "extractPort", "Lio/javalin/plugin/bundled/PortResult;", "origin", "extractPort$javalin", "extractPortOrSchemeDefault", "extractPortOrSchemeDefault$javalin", "isSchemeValid", "", "scheme", "", "isSchemeValid$javalin", "isValidOrigin", "isValidOrigin$javalin", "normalizeOrigin", "normalizeOrigin$javalin", "originFulfillsWildcardRequirements", "Lio/javalin/plugin/bundled/WildcardResult;", "originFulfillsWildcardRequirements$javalin", "originsMatch", "clientOrigin", "Lio/javalin/plugin/bundled/OriginParts;", "serverOrigin", "originsMatch$javalin", "parseAsOriginParts", "parseAsOriginParts$javalin", "javalin"})
@SourceDebugExtension(value={"SMAP\nCorsUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CorsUtils.kt\nio/javalin/plugin/bundled/CorsUtils\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,193:1\n1064#2,2:194\n1099#2,3:196\n1083#2,2:199\n1099#2,3:202\n1#3:201\n*S KotlinDebug\n*F\n+ 1 CorsUtils.kt\nio/javalin/plugin/bundled/CorsUtils\n*L\n9#1:194,2\n31#1:196,3\n57#1:199,2\n145#1:202,3\n*E\n"})
public final class CorsUtils {
    @NotNull
    public static final CorsUtils INSTANCE = new CorsUtils();

    private CorsUtils() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isSchemeValid$javalin(@NotNull CharSequence scheme2) {
        Intrinsics.checkNotNullParameter(scheme2, "scheme");
        if (scheme2.length() <= 0) return false;
        boolean bl = true;
        if (!bl) return false;
        if (!Character.isLetter(StringsKt.first(scheme2))) return false;
        CharSequence $this$all$iv = scheme2;
        boolean $i$f$all = false;
        int n = 0;
        while (n < $this$all$iv.length()) {
            char element$iv;
            char it = element$iv = $this$all$iv.charAt(n);
            boolean bl2 = false;
            if (!Character.isLetter(it) && !CorsUtilsKt.access$isAsciiDigit(it) && it != '-' && it != '+') {
                if (it != '.') return false;
            }
            boolean bl3 = true;
            if (!bl3) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public final boolean isValidOrigin$javalin(@NotNull String origin) {
        boolean bl;
        Intrinsics.checkNotNullParameter(origin, "origin");
        int schemeAndHostDelimiter = StringsKt.indexOf$default((CharSequence)origin, "://", 0, false, 6, null);
        PortResult portResult = this.extractPort$javalin(origin);
        if (((CharSequence)origin).length() == 0) {
            bl = false;
        } else if (Intrinsics.areEqual(origin, "null")) {
            bl = true;
        } else if (StringsKt.contains$default((CharSequence)origin, "?", false, 2, null)) {
            bl = false;
        } else if (schemeAndHostDelimiter <= 0) {
            bl = false;
        } else if (!this.isSchemeValid$javalin(origin.subSequence(0, schemeAndHostDelimiter))) {
            bl = false;
        } else {
            CharSequence $this$count$iv = origin;
            boolean $i$f$count = false;
            int count$iv = 0;
            for (int i = 0; i < $this$count$iv.length(); ++i) {
                char element$iv;
                char it = element$iv = $this$count$iv.charAt(i);
                boolean bl2 = false;
                if (!(it == '/')) continue;
                ++count$iv;
            }
            bl = count$iv != 2 ? false : !(portResult instanceof PortResult.ErrorState);
        }
        return bl;
    }

    @NotNull
    public final PortResult extractPort$javalin(@NotNull String origin) {
        boolean bl;
        String possiblePortDigits;
        block4: {
            int colonAfterSchemeIndex;
            boolean hasPort;
            Intrinsics.checkNotNullParameter(origin, "origin");
            if (!StringsKt.contains$default((CharSequence)origin, "://", false, 2, null)) {
                return PortResult.ErrorState.InvalidOrigin.INSTANCE;
            }
            int possiblePortIndex = StringsKt.lastIndexOf$default((CharSequence)origin, ':', 0, false, 6, null);
            boolean bl2 = hasPort = possiblePortIndex != (colonAfterSchemeIndex = StringsKt.indexOf$default((CharSequence)origin, ':', 0, false, 6, null));
            if (!hasPort) {
                return PortResult.NoPortSpecified.INSTANCE;
            }
            possiblePortDigits = ((Object)origin.subSequence(possiblePortIndex + 1, origin.length())).toString();
            CharSequence $this$any$iv = possiblePortDigits;
            boolean $i$f$any = false;
            for (int i = 0; i < $this$any$iv.length(); ++i) {
                char element$iv;
                char it = element$iv = $this$any$iv.charAt(i);
                boolean bl3 = false;
                if (!(!CorsUtilsKt.access$isAsciiDigit(it))) continue;
                bl = true;
                break block4;
            }
            bl = false;
        }
        if (bl) {
            return PortResult.ErrorState.InvalidPort.INSTANCE;
        }
        return new PortResult.PortSpecified(Integer.parseInt(possiblePortDigits, CharsKt.checkRadix(10)), false, 2, null);
    }

    @NotNull
    public final PortResult extractPortOrSchemeDefault$javalin(@NotNull String origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        PortResult portResult = this.extractPort$javalin(origin);
        return portResult instanceof PortResult.NoPortSpecified && StringsKt.startsWith(origin, "https://", true) ? (PortResult)new PortResult.PortSpecified(443, true) : (portResult instanceof PortResult.NoPortSpecified && StringsKt.startsWith(origin, "http://", true) ? (PortResult)new PortResult.PortSpecified(80, true) : portResult);
    }

    @NotNull
    public final String addSchemeIfMissing$javalin(@NotNull String host2, @NotNull String defaultScheme) {
        String hostWithScheme;
        Intrinsics.checkNotNullParameter(host2, "host");
        Intrinsics.checkNotNullParameter(defaultScheme, "defaultScheme");
        String string = hostWithScheme = Intrinsics.areEqual(host2, "*") ? host2 : (Intrinsics.areEqual(host2, "null") ? host2 : (StringsKt.contains$default((CharSequence)host2, "://", false, 2, null) ? host2 : defaultScheme + "://" + host2));
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string2 = string.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
        return StringsKt.removeSuffix(string2, (CharSequence)"/");
    }

    @NotNull
    public final String normalizeOrigin$javalin(@NotNull String origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        PortResult portResult = this.extractPortOrSchemeDefault$javalin(origin);
        if (portResult instanceof PortResult.PortSpecified && ((PortResult.PortSpecified)portResult).getFromSchemeDefault()) {
            return origin + ":" + ((PortResult.PortSpecified)portResult).getPort();
        }
        return origin;
    }

    @NotNull
    public final OriginParts parseAsOriginParts$javalin(@NotNull String origin) {
        int n;
        Intrinsics.checkNotNullParameter(origin, "origin");
        int it = n = StringsKt.indexOf$default((CharSequence)origin, "://", 0, false, 6, null);
        boolean bl = false;
        if (!(it > 0)) {
            boolean $i$a$-require-CorsUtils$parseAsOriginParts$schemeAndHostDelimiter$1$22 = false;
            String $i$a$-require-CorsUtils$parseAsOriginParts$schemeAndHostDelimiter$1$22 = "scheme delimiter :// must exist";
            throw new IllegalArgumentException($i$a$-require-CorsUtils$parseAsOriginParts$schemeAndHostDelimiter$1$22.toString());
        }
        int schemeAndHostDelimiter = n;
        String it2 = it = ((Object)origin.subSequence(0, schemeAndHostDelimiter)).toString();
        boolean $i$a$-also-CorsUtils$parseAsOriginParts$scheme$22 = false;
        if (!INSTANCE.isSchemeValid$javalin(it2)) {
            boolean bl2 = false;
            String string = "specified scheme is not valid";
            throw new IllegalArgumentException(string.toString());
        }
        String scheme2 = it;
        PortResult $i$a$-also-CorsUtils$parseAsOriginParts$scheme$22 = this.extractPort$javalin(origin);
        PortResult.PortSpecified portSpecified = $i$a$-also-CorsUtils$parseAsOriginParts$scheme$22 instanceof PortResult.PortSpecified ? (PortResult.PortSpecified)$i$a$-also-CorsUtils$parseAsOriginParts$scheme$22 : null;
        if (portSpecified == null) {
            throw new IllegalArgumentException("explicit port is required");
        }
        int port = portSpecified.getPort();
        String host2 = ((Object)origin.subSequence(schemeAndHostDelimiter + 3, StringsKt.lastIndexOf$default((CharSequence)origin, ':', 0, false, 6, null))).toString();
        String reconstructedOrigin = scheme2 + "://" + host2 + ":" + port;
        if (!Intrinsics.areEqual(reconstructedOrigin, origin)) {
            boolean bl3 = false;
            String string = StringsKt.trimMargin$default("Parsing failed!\n            |reconstructedOrigin '" + reconstructedOrigin + "' did not match the original origin '" + origin + "'.\n            |This is a critical error and should never happen!\n            |\n            |Please report it as a GitHub issue at https://github.com/javalin/javalin/issues/new/choose with the exact error message!\n            ", null, 1, null);
            throw new IllegalArgumentException(string.toString());
        }
        return new OriginParts(scheme2, host2, port);
    }

    public final boolean originsMatch$javalin(@NotNull OriginParts clientOrigin, @NotNull OriginParts serverOrigin) {
        Intrinsics.checkNotNullParameter(clientOrigin, "clientOrigin");
        Intrinsics.checkNotNullParameter(serverOrigin, "serverOrigin");
        if (Intrinsics.areEqual(clientOrigin, serverOrigin)) {
            return true;
        }
        if (!Intrinsics.areEqual(clientOrigin.getScheme(), serverOrigin.getScheme())) {
            return false;
        }
        if (clientOrigin.getPort() != serverOrigin.getPort()) {
            return false;
        }
        if (!StringsKt.startsWith$default(serverOrigin.getHost(), "*.", false, 2, null)) {
            return false;
        }
        String serverHostBase = StringsKt.removePrefix(serverOrigin.getHost(), (CharSequence)"*.");
        char[] cArray = new char[]{'.'};
        String clientHostBase = (String)StringsKt.split$default((CharSequence)clientOrigin.getHost(), cArray, false, 2, 2, null).get(1);
        return Intrinsics.areEqual(serverHostBase, clientHostBase);
    }

    @NotNull
    public final WildcardResult originFulfillsWildcardRequirements$javalin(@NotNull String origin) {
        WildcardResult wildcardResult;
        Intrinsics.checkNotNullParameter(origin, "origin");
        CharSequence $this$count$iv = origin;
        boolean $i$f$count = false;
        int count$iv = 0;
        for (int i = 0; i < $this$count$iv.length(); ++i) {
            char element$iv;
            char it = element$iv = $this$count$iv.charAt(i);
            boolean bl = false;
            if (!(it == '*')) continue;
            ++count$iv;
        }
        switch (count$iv) {
            case 0: {
                wildcardResult = WildcardResult.NoWildcardDetected.INSTANCE;
                break;
            }
            case 1: {
                if (!StringsKt.contains$default((CharSequence)origin, "://*.", false, 2, null)) {
                    wildcardResult = WildcardResult.ErrorState.WildcardNotAtTheStartOfTheHost.INSTANCE;
                    break;
                }
                wildcardResult = WildcardResult.WildcardOkay.INSTANCE;
                break;
            }
            default: {
                wildcardResult = WildcardResult.ErrorState.TooManyWildcards.INSTANCE;
            }
        }
        return wildcardResult;
    }
}

