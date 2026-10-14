/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.config.RoutingConfig;
import io.javalin.routing.PathSegment;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001aJ\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\rH\u0000\u001aX\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\rH\u0000\u001a\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\bH\u0000\u00a8\u0006\u0014"}, d2={"constructRegex", "Lkotlin/text/Regex;", "options", "Lio/javalin/config/RoutingConfig;", "segments", "", "Lio/javalin/routing/PathSegment;", "regexSuffix", "", "regexOptions", "", "Lkotlin/text/RegexOption;", "mapper", "Lkotlin/Function1;", "constructRegexList", "matchEverySubPath", "", "values", "regex", "url", "javalin"})
public final class RoutingRegexesKt {
    @NotNull
    public static final List<Regex> constructRegexList(@NotNull RoutingConfig options, boolean matchEverySubPath, @NotNull List<? extends PathSegment> segments, @NotNull String regexSuffix, @NotNull Set<? extends RegexOption> regexOptions, @NotNull Function1<? super PathSegment, String> mapper2) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(regexSuffix, "regexSuffix");
        Intrinsics.checkNotNullParameter(regexOptions, "regexOptions");
        Intrinsics.checkNotNullParameter(mapper2, "mapper");
        return CollectionsKt.plus((Collection)CollectionsKt.listOf(RoutingRegexesKt.constructRegex(options, segments, regexSuffix, regexOptions, mapper2)), (Iterable)RoutingRegexesKt.constructRegexList$addRegexForExtraWildcard(matchEverySubPath, options, segments, regexSuffix, regexOptions, mapper2));
    }

    public static /* synthetic */ List constructRegexList$default(RoutingConfig routingConfig, boolean bl, List list, String string, Set set, Function1 function1, int n, Object object) {
        if ((n & 0x10) != 0) {
            set = SetsKt.emptySet();
        }
        return RoutingRegexesKt.constructRegexList(routingConfig, bl, list, string, set, function1);
    }

    @NotNull
    public static final Regex constructRegex(@NotNull RoutingConfig options, @NotNull List<? extends PathSegment> segments, @NotNull String regexSuffix, @NotNull Set<? extends RegexOption> regexOptions, @NotNull Function1<? super PathSegment, String> mapper2) {
        CharSequence charSequence;
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(regexSuffix, "regexSuffix");
        Intrinsics.checkNotNullParameter(regexOptions, "regexOptions");
        Intrinsics.checkNotNullParameter(mapper2, "mapper");
        String slashRegex = options.treatMultipleSlashesAsSingleSlash ? "/+" : "/";
        StringBuilder $this$constructRegex_u24lambda_u240 = charSequence = new StringBuilder();
        boolean bl = false;
        $this$constructRegex_u24lambda_u240.append("^/");
        if (options.treatMultipleSlashesAsSingleSlash) {
            $this$constructRegex_u24lambda_u240.append("+");
        }
        $this$constructRegex_u24lambda_u240.append(CollectionsKt.joinToString$default(segments, slashRegex, null, null, 0, null, mapper2, 30, null));
        $this$constructRegex_u24lambda_u240.append(regexSuffix);
        $this$constructRegex_u24lambda_u240.append("$");
        String string = charSequence.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        charSequence = string;
        return new Regex((String)charSequence, regexOptions);
    }

    public static /* synthetic */ Regex constructRegex$default(RoutingConfig routingConfig, List list, String string, Set set, Function1 function1, int n, Object object) {
        if ((n & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return RoutingRegexesKt.constructRegex(routingConfig, list, string, set, function1);
    }

    @NotNull
    public static final List<String> values(@NotNull Regex regex2, @NotNull String url2) {
        Intrinsics.checkNotNullParameter(regex2, "regex");
        Intrinsics.checkNotNullParameter(url2, "url");
        Object object = regex2.matchEntire(url2);
        if (object == null || (object = object.getGroupValues()) == null || (object = CollectionsKt.drop((Iterable)object, 1)) == null) {
            object = CollectionsKt.emptyList();
        }
        return object;
    }

    private static final List<Regex> constructRegexList$addRegexForExtraWildcard(boolean $matchEverySubPath, RoutingConfig $options, List<? extends PathSegment> $segments, String $regexSuffix, Set<? extends RegexOption> $regexOptions, Function1<? super PathSegment, String> $mapper) {
        return $matchEverySubPath ? CollectionsKt.listOf(RoutingRegexesKt.constructRegex($options, CollectionsKt.plus((Collection)$segments, PathSegment.Wildcard.INSTANCE), $regexSuffix, $regexOptions, $mapper)) : CollectionsKt.emptyList();
    }
}

