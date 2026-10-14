/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.config.RoutingConfig;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.routing.ParameterNamesNotUniqueException;
import io.javalin.routing.ParserStateKt;
import io.javalin.routing.PathParser;
import io.javalin.routing.PathSegment;
import io.javalin.routing.PathSegmentKt;
import io.javalin.routing.RoutingRegexesKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00192\u0006\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0003R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006\u001c"}, d2={"Lio/javalin/routing/PathParser;", "", "rawPath", "", "routingConfig", "Lio/javalin/config/RoutingConfig;", "(Ljava/lang/String;Lio/javalin/config/RoutingConfig;)V", "matchPathAndEverySubPath", "", "matchRegex", "", "Lkotlin/text/Regex;", "path", "pathParamNames", "getPathParamNames", "()Ljava/util/List;", "pathParamRegex", "regexOptions", "", "Lkotlin/text/RegexOption;", "regexSuffix", "segments", "Lio/javalin/routing/PathSegment;", "getSegments", "extractPathParams", "", "url", "matches", "javalin"})
@SourceDebugExtension(value={"SMAP\nPathParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathParser.kt\nio/javalin/routing/PathParser\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n766#2:72\n857#2,2:73\n1549#2:75\n1620#2,3:76\n1549#2:79\n1620#2,3:80\n1747#2,3:83\n350#2,7:86\n3433#2,7:93\n*S KotlinDebug\n*F\n+ 1 PathParser.kt\nio/javalin/routing/PathParser\n*L\n18#1:72\n18#1:73,2\n19#1:75\n19#1:76,3\n21#1:79\n21#1:80,3\n62#1:83,3\n65#1:86,7\n66#1:93,7\n*E\n"})
public final class PathParser {
    @NotNull
    private final String rawPath;
    private final boolean matchPathAndEverySubPath;
    @NotNull
    private final String path;
    @NotNull
    private final List<PathSegment> segments;
    @NotNull
    private final List<String> pathParamNames;
    @NotNull
    private final String regexSuffix;
    @NotNull
    private final Set<RegexOption> regexOptions;
    @NotNull
    private final List<Regex> matchRegex;
    @NotNull
    private final List<Regex> pathParamRegex;

    /*
     * WARNING - void declaration
     */
    public PathParser(@NotNull String rawPath, @NotNull RoutingConfig routingConfig) {
        void list;
        PathSegment segment;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        String it;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv;
        Intrinsics.checkNotNullParameter(rawPath, "rawPath");
        Intrinsics.checkNotNullParameter(routingConfig, "routingConfig");
        this.rawPath = rawPath;
        if (StringsKt.contains$default((CharSequence)this.rawPath, "/:", false, 2, null)) {
            throw new IllegalArgumentException("Path '" + this.rawPath + "' invalid - Javalin 4 switched from ':param' to '{param}'.");
        }
        this.matchPathAndEverySubPath = StringsKt.endsWith$default(this.rawPath, ">*", false, 2, null) || StringsKt.endsWith$default(this.rawPath, "}*", false, 2, null);
        this.path = this.matchPathAndEverySubPath ? StringsKt.removeSuffix(this.rawPath, (CharSequence)"*") : this.rawPath;
        Object object = new String[]{"/"};
        object = StringsKt.split$default((CharSequence)this.path, object, false, 0, 6, null);
        PathParser pathParser = this;
        boolean $i$f$filter = false;
        void var5_7 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (String)element$iv$iv;
            boolean bl = false;
            boolean bl2 = ((CharSequence)it).length() > 0;
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(ParserStateKt.convertSegment((String)((Object)segment), this.rawPath));
        }
        pathParser.segments = (List)destination$iv$iv;
        $this$map$iv = this.segments;
        pathParser = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            segment = (PathSegment)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(PathSegmentKt.pathParamNames((PathSegment)((Object)it)));
        }
        Object $i$f$map2 = object = CollectionsKt.flatten((List)destination$iv$iv);
        boolean bl = false;
        Set set = CollectionsKt.toSet((Iterable)list);
        if (set.size() != list.size()) {
            throw new ParameterNamesNotUniqueException(this.rawPath);
        }
        pathParser.pathParamNames = object;
        this.regexSuffix = routingConfig.treatMultipleSlashesAsSingleSlash ? (Intrinsics.areEqual(this.rawPath, "/") ? "" : (routingConfig.ignoreTrailingSlashes ? "/*" : (StringsKt.endsWith$default(this.rawPath, "/", false, 2, null) ? "/+" : ""))) : (Intrinsics.areEqual(this.rawPath, "/") ? "" : (routingConfig.ignoreTrailingSlashes ? "/?" : (StringsKt.endsWith$default(this.rawPath, "/", false, 2, null) ? "/" : "")));
        this.regexOptions = routingConfig.caseInsensitiveRoutes ? SetsKt.setOf(RegexOption.IGNORE_CASE) : SetsKt.emptySet();
        this.matchRegex = RoutingRegexesKt.constructRegexList(routingConfig, this.matchPathAndEverySubPath, this.segments, this.regexSuffix, this.regexOptions, matchRegex.1.INSTANCE);
        this.pathParamRegex = RoutingRegexesKt.constructRegexList(routingConfig, this.matchPathAndEverySubPath, this.segments, this.regexSuffix, this.regexOptions, pathParamRegex.1.INSTANCE);
    }

    @NotNull
    public final List<PathSegment> getSegments() {
        return this.segments;
    }

    @NotNull
    public final List<String> getPathParamNames() {
        return this.pathParamNames;
    }

    public final boolean matches(@NotNull String url2) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter(url2, "url");
            Iterable $this$any$iv = this.matchRegex;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Regex it = (Regex)element$iv;
                    boolean bl2 = false;
                    if (!it.matches(url2)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Map<String, String> extractPathParams(@NotNull String url2) {
        void $this$zip$iv;
        int n;
        Iterable<Regex> $this$indexOfFirst$iv;
        block3: {
            Intrinsics.checkNotNullParameter(url2, "url");
            $this$indexOfFirst$iv = this.matchRegex;
            boolean $i$f$indexOfFirst = false;
            int index$iv = 0;
            Iterator<Regex> iterator2 = $this$indexOfFirst$iv.iterator();
            while (iterator2.hasNext()) {
                Regex item$iv;
                Regex it = item$iv = iterator2.next();
                boolean bl = false;
                if (it.matches(url2)) {
                    n = index$iv;
                    break block3;
                }
                ++index$iv;
            }
            n = -1;
        }
        int index = n;
        $this$indexOfFirst$iv = this.pathParamNames;
        Iterable other$iv = RoutingRegexesKt.values(this.pathParamRegex.get(index), url2);
        boolean $i$f$zip = false;
        Iterator first$iv = $this$zip$iv.iterator();
        Iterator second$iv = other$iv.iterator();
        ArrayList<Pair<void, String>> list$iv = new ArrayList<Pair<void, String>>(Math.min(CollectionsKt.collectionSizeOrDefault($this$zip$iv, 10), CollectionsKt.collectionSizeOrDefault(other$iv, 10)));
        while (first$iv.hasNext() && second$iv.hasNext()) {
            void value;
            void name;
            String bl = (String)second$iv.next();
            String string = (String)first$iv.next();
            ArrayList<Pair<void, String>> arrayList = list$iv;
            boolean bl2 = false;
            arrayList.add(TuplesKt.to(name, JavalinServletContextKt.urlDecode((String)value)));
        }
        return MapsKt.toMap((Iterable)list$iv);
    }
}

