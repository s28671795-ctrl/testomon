/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.routing.PathSegment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\f\u0010\n\u001a\u00020\u0003*\u00020\u0003H\u0002\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f*\u00020\rH\u0000\u00a8\u0006\u000e"}, d2={"createNormal", "Lio/javalin/routing/PathSegment$Normal;", "string", "", "enableRegex", "", "createSlashAcceptingParam", "Lio/javalin/routing/PathSegment$Parameter$SlashAcceptingParameter;", "createSlashIgnoringParam", "Lio/javalin/routing/PathSegment$Parameter$SlashIgnoringParameter;", "grouped", "pathParamNames", "", "Lio/javalin/routing/PathSegment;", "javalin"})
@SourceDebugExtension(value={"SMAP\nPathSegment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathSegment.kt\nio/javalin/routing/PathSegmentKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n800#2,11:75\n1549#2:86\n1620#2,3:87\n*S KotlinDebug\n*F\n+ 1 PathSegment.kt\nio/javalin/routing/PathSegmentKt\n*L\n71#1:75,11\n71#1:86\n71#1:87,3\n*E\n"})
public final class PathSegmentKt {
    private static final String grouped(String $this$grouped) {
        return "(" + $this$grouped + ")";
    }

    @NotNull
    public static final PathSegment.Normal createNormal(@NotNull String string, boolean enableRegex) {
        Intrinsics.checkNotNullParameter(string, "string");
        return enableRegex ? (PathSegment.Normal)new PathSegment.Normal.RegexAllowed(string) : (PathSegment.Normal)new PathSegment.Normal.RegexEscaped(string);
    }

    public static /* synthetic */ PathSegment.Normal createNormal$default(String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return PathSegmentKt.createNormal(string, bl);
    }

    @NotNull
    public static final PathSegment.Parameter.SlashIgnoringParameter createSlashIgnoringParam(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return new PathSegment.Parameter.SlashIgnoringParameter(string);
    }

    @NotNull
    public static final PathSegment.Parameter.SlashAcceptingParameter createSlashAcceptingParam(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return new PathSegment.Parameter.SlashAcceptingParameter(string);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<String> pathParamNames(@NotNull PathSegment $this$pathParamNames) {
        List list;
        Intrinsics.checkNotNullParameter($this$pathParamNames, "<this>");
        PathSegment pathSegment = $this$pathParamNames;
        if (pathSegment instanceof PathSegment.Normal ? true : pathSegment instanceof PathSegment.Wildcard) {
            list = CollectionsKt.emptyList();
        } else if (pathSegment instanceof PathSegment.Parameter) {
            list = CollectionsKt.listOf(((PathSegment.Parameter)$this$pathParamNames).getName());
        } else if (pathSegment instanceof PathSegment.MultipleSegments) {
            void $this$mapTo$iv$iv;
            Iterable $this$filterIsInstanceTo$iv$iv;
            Iterable $this$filterIsInstance$iv = ((PathSegment.MultipleSegments)$this$pathParamNames).getInnerSegments();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof PathSegment.Parameter)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$map$iv = (List)destination$iv$iv;
            boolean $i$f$map = false;
            $this$filterIsInstanceTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PathSegment.Parameter parameter = (PathSegment.Parameter)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.getName());
            }
            list = (List)destination$iv$iv;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return list;
    }

    public static final /* synthetic */ String access$grouped(String $receiver) {
        return PathSegmentKt.grouped($receiver);
    }
}

