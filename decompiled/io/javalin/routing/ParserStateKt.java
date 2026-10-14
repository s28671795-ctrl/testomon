/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.routing.MissingBracketsException;
import io.javalin.routing.ParserState;
import io.javalin.routing.PathSegment;
import io.javalin.routing.PathSegmentKt;
import io.javalin.routing.WildcardBracketAdjacentException;
import io.javalin.util.ConcurrencyUtilKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000,\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0000\u001a\u0018\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0002\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\u00022\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002\u001a\u001c\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010\u00b2\u0006\n\u0010\u0011\u001a\u00020\u0012X\u008a\u0084\u0002\u00b2\u0006\n\u0010\u0013\u001a\u00020\u0012X\u008a\u0084\u0002"}, d2={"adjacentViolations", "", "", "allDelimiters", "", "", "convertSegment", "Lio/javalin/routing/PathSegment;", "segment", "rawPath", "parseAsPathSegment", "isEnclosedBy", "", "prefix", "suffix", "stripEnclosing", "javalin", "bracketsCount", "", "wildcardCount"})
@SourceDebugExtension(value={"SMAP\nParserState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParserState.kt\nio/javalin/routing/ParserStateKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,109:1\n1747#2,3:110\n1789#2,3:117\n970#3:113\n1041#3,3:114\n*S KotlinDebug\n*F\n+ 1 ParserState.kt\nio/javalin/routing/ParserStateKt\n*L\n20#1:110,3\n93#1:117,3\n91#1:113\n91#1:114,3\n*E\n"})
public final class ParserStateKt {
    @NotNull
    private static final Set<Character> allDelimiters;
    @NotNull
    private static final List<String> adjacentViolations;

    @NotNull
    public static final PathSegment convertSegment(@NotNull String segment, @NotNull String rawPath) {
        boolean bl;
        Lazy wildcardCount$delegate;
        Lazy bracketsCount$delegate;
        block5: {
            Intrinsics.checkNotNullParameter(segment, "segment");
            Intrinsics.checkNotNullParameter(rawPath, "rawPath");
            bracketsCount$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Integer>(segment){
                final /* synthetic */ String $segment;
                {
                    this.$segment = $segment;
                    super(0);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final Integer invoke() {
                    void var3_3;
                    CharSequence $this$count$iv = this.$segment;
                    boolean $i$f$count = false;
                    int count$iv = 0;
                    for (int i = 0; i < $this$count$iv.length(); ++i) {
                        char element$iv;
                        char it = element$iv = $this$count$iv.charAt(i);
                        boolean bl = false;
                        if (!ParserStateKt.access$getAllDelimiters$p().contains(Character.valueOf(it))) continue;
                        ++count$iv;
                    }
                    return (int)var3_3;
                }
            }, 1, null);
            wildcardCount$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Integer>(segment){
                final /* synthetic */ String $segment;
                {
                    this.$segment = $segment;
                    super(0);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final Integer invoke() {
                    void var3_3;
                    CharSequence $this$count$iv = this.$segment;
                    boolean $i$f$count = false;
                    int count$iv = 0;
                    for (int i = 0; i < $this$count$iv.length(); ++i) {
                        char element$iv;
                        char it = element$iv = $this$count$iv.charAt(i);
                        boolean bl = false;
                        if (!(it == '*')) continue;
                        ++count$iv;
                    }
                    return (int)var3_3;
                }
            }, 1, null);
            if (ParserStateKt.convertSegment$lambda$0(bracketsCount$delegate) % 2 != 0) {
                throw new MissingBracketsException(segment, rawPath);
            }
            Iterable $this$any$iv = adjacentViolations;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    String it = (String)element$iv;
                    boolean bl2 = false;
                    if (!StringsKt.contains$default((CharSequence)segment, it, false, 2, null)) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        if (bl) {
            throw new WildcardBracketAdjacentException(segment, rawPath);
        }
        return Intrinsics.areEqual(segment, "*") ? (PathSegment)PathSegment.Wildcard.INSTANCE : (ParserStateKt.convertSegment$lambda$0(bracketsCount$delegate) == 0 && ParserStateKt.convertSegment$lambda$1(wildcardCount$delegate) == 0 ? (PathSegment)PathSegmentKt.createNormal$default(segment, false, 2, null) : (ParserStateKt.convertSegment$lambda$0(bracketsCount$delegate) == 2 && ParserStateKt.isEnclosedBy(segment, '{', '}') ? (PathSegment)PathSegmentKt.createSlashIgnoringParam(ParserStateKt.stripEnclosing(segment, '{', '}')) : (ParserStateKt.convertSegment$lambda$0(bracketsCount$delegate) == 2 && ParserStateKt.isEnclosedBy(segment, '<', '>') ? (PathSegment)PathSegmentKt.createSlashAcceptingParam(ParserStateKt.stripEnclosing(segment, '<', '>')) : ParserStateKt.parseAsPathSegment(segment, rawPath))));
    }

    /*
     * WARNING - void declaration
     */
    private static final PathSegment parseAsPathSegment(String segment, String rawPath) {
        void $this$fold$iv;
        void $this$mapTo$iv$iv;
        Ref.ObjectRef<ParserState> state = new Ref.ObjectRef<ParserState>();
        state.element = ParserState.NORMAL;
        List pathNameAccumulator = new ArrayList();
        Object $this$map$iv = segment;
        boolean $i$f$map = false;
        CharSequence charSequence = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length());
        boolean $i$f$mapTo = false;
        for (int i = 0; i < $this$mapTo$iv$iv.length(); ++i) {
            void p0;
            char item$iv$iv;
            char c = item$iv$iv = $this$mapTo$iv$iv.charAt(i);
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(ParserStateKt.parseAsPathSegment$mapSingleChar(state, segment, rawPath, pathNameAccumulator, (char)p0));
        }
        $this$map$iv = CollectionsKt.filterNotNull((List)destination$iv$iv);
        PathSegment.MultipleSegments initial$iv = new PathSegment.MultipleSegments(CollectionsKt.emptyList());
        boolean $i$f$fold = false;
        PathSegment.MultipleSegments accumulator$iv = initial$iv;
        for (Object element$iv : $this$fold$iv) {
            void pathSegment;
            PathSegment item$iv$iv = (PathSegment)element$iv;
            PathSegment.MultipleSegments acc = accumulator$iv;
            boolean bl = false;
            PathSegment lastAddition = CollectionsKt.lastOrNull(acc.getInnerSegments());
            accumulator$iv = lastAddition == null ? new PathSegment.MultipleSegments(CollectionsKt.listOf(pathSegment)) : (lastAddition instanceof PathSegment.Wildcard && pathSegment instanceof PathSegment.Wildcard ? acc : (lastAddition instanceof PathSegment.Normal && pathSegment instanceof PathSegment.Normal ? new PathSegment.MultipleSegments(CollectionsKt.plus((Collection)CollectionsKt.dropLast(acc.getInnerSegments(), 1), PathSegmentKt.createNormal$default(((PathSegment.Normal)lastAddition).getContent() + ((PathSegment.Normal)pathSegment).getContent(), false, 2, null))) : new PathSegment.MultipleSegments(CollectionsKt.plus((Collection)acc.getInnerSegments(), pathSegment))));
        }
        return accumulator$iv;
    }

    private static final boolean isEnclosedBy(String $this$isEnclosedBy, char prefix, char suffix) {
        return StringsKt.startsWith$default((CharSequence)$this$isEnclosedBy, prefix, false, 2, null) && StringsKt.endsWith$default((CharSequence)$this$isEnclosedBy, suffix, false, 2, null);
    }

    private static final String stripEnclosing(String $this$stripEnclosing, char prefix, char suffix) {
        return StringsKt.removeSuffix(StringsKt.removePrefix($this$stripEnclosing, (CharSequence)String.valueOf(prefix)), (CharSequence)String.valueOf(suffix));
    }

    private static final int convertSegment$lambda$0(Lazy<Integer> $bracketsCount$delegate) {
        Lazy<Integer> lazy = $bracketsCount$delegate;
        return ((Number)lazy.getValue()).intValue();
    }

    private static final int convertSegment$lambda$1(Lazy<Integer> $wildcardCount$delegate) {
        Lazy<Integer> lazy = $wildcardCount$delegate;
        return ((Number)lazy.getValue()).intValue();
    }

    private static final PathSegment parseAsPathSegment$mapSingleChar(Ref.ObjectRef<ParserState> state, String $segment, String $rawPath, List<Character> pathNameAccumulator, char c) {
        PathSegment pathSegment;
        switch (WhenMappings.$EnumSwitchMapping$0[((ParserState)((Object)state.element)).ordinal()]) {
            case 1: {
                char c2 = c;
                if (c2 == '*') {
                    pathSegment = PathSegment.Wildcard.INSTANCE;
                    break;
                }
                if (c2 == '{') {
                    state.element = ParserState.INSIDE_SLASH_IGNORING_BRACKETS;
                    pathSegment = null;
                    break;
                }
                if (c2 == '<') {
                    state.element = ParserState.INSIDE_SLASH_ACCEPTING_BRACKETS;
                    pathSegment = null;
                    break;
                }
                if (c2 == '}' ? true : c2 == '>') {
                    throw new MissingBracketsException($segment, $rawPath);
                }
                pathSegment = PathSegmentKt.createNormal$default(String.valueOf(c), false, 2, null);
                break;
            }
            case 2: {
                PathSegment.Parameter.SlashIgnoringParameter slashIgnoringParameter;
                char c3 = c;
                if (c3 == '}') {
                    state.element = ParserState.NORMAL;
                    String name = CollectionsKt.joinToString$default(pathNameAccumulator, "", null, null, 0, null, null, 62, null);
                    pathNameAccumulator.clear();
                    slashIgnoringParameter = PathSegmentKt.createSlashIgnoringParam(name);
                } else {
                    if ((c3 == '{' ? true : c3 == '<') ? true : c3 == '>') {
                        throw new MissingBracketsException($segment, $rawPath);
                    }
                    Character c4 = Character.valueOf(c);
                    ((Collection)pathNameAccumulator).add(c4);
                    slashIgnoringParameter = null;
                }
                pathSegment = slashIgnoringParameter;
                break;
            }
            case 3: {
                PathSegment.Parameter.SlashAcceptingParameter slashAcceptingParameter;
                char c5 = c;
                if (c5 == '>') {
                    state.element = ParserState.NORMAL;
                    String name = CollectionsKt.joinToString$default(pathNameAccumulator, "", null, null, 0, null, null, 62, null);
                    pathNameAccumulator.clear();
                    slashAcceptingParameter = PathSegmentKt.createSlashAcceptingParam(name);
                } else {
                    if ((c5 == '{' ? true : c5 == '}') ? true : c5 == '<') {
                        throw new MissingBracketsException($segment, $rawPath);
                    }
                    Character c6 = Character.valueOf(c);
                    ((Collection)pathNameAccumulator).add(c6);
                    slashAcceptingParameter = null;
                }
                pathSegment = slashAcceptingParameter;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return pathSegment;
    }

    public static final /* synthetic */ Set access$getAllDelimiters$p() {
        return allDelimiters;
    }

    static {
        Object[] objectArray = new Character[]{Character.valueOf('{'), Character.valueOf('}'), Character.valueOf('<'), Character.valueOf('>')};
        allDelimiters = SetsKt.setOf(objectArray);
        objectArray = new String[]{"*{", "*<", "}*", ">*"};
        adjacentViolations = CollectionsKt.listOf(objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[ParserState.values().length];
            try {
                nArray[ParserState.NORMAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParserState.INSIDE_SLASH_IGNORING_BRACKETS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParserState.INSIDE_SLASH_ACCEPTING_BRACKETS.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

