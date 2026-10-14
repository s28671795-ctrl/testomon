/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.routing.PathSegment;
import io.javalin.routing.PathSegmentKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\b\t\n\u000bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H \u00a2\u0006\u0002\b\u0005J\r\u0010\u0006\u001a\u00020\u0004H \u00a2\u0006\u0002\b\u0007\u0082\u0001\u0004\f\r\u000e\u000f\u00a8\u0006\u0010"}, d2={"Lio/javalin/routing/PathSegment;", "", "()V", "asGroupedRegexString", "", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "MultipleSegments", "Normal", "Parameter", "Wildcard", "Lio/javalin/routing/PathSegment$MultipleSegments;", "Lio/javalin/routing/PathSegment$Normal;", "Lio/javalin/routing/PathSegment$Parameter;", "Lio/javalin/routing/PathSegment$Wildcard;", "javalin"})
public abstract class PathSegment {
    private PathSegment() {
    }

    @NotNull
    public abstract String asRegexString$javalin();

    @NotNull
    public abstract String asGroupedRegexString$javalin();

    public /* synthetic */ PathSegment(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u000b\u001a\u00020\u0006H\u0010\u00a2\u0006\u0002\b\fJ\r\u0010\r\u001a\u00020\u0006H\u0010\u00a2\u0006\u0002\b\u000eR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lio/javalin/routing/PathSegment$MultipleSegments;", "Lio/javalin/routing/PathSegment;", "segments", "", "(Ljava/util/List;)V", "groupedRegex", "", "innerSegments", "getInnerSegments", "()Ljava/util/List;", "regex", "asGroupedRegexString", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
    @SourceDebugExtension(value={"SMAP\nPathSegment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathSegment.kt\nio/javalin/routing/PathSegment$MultipleSegments\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n800#2,11:75\n819#2:86\n847#2,2:87\n*S KotlinDebug\n*F\n+ 1 PathSegment.kt\nio/javalin/routing/PathSegment$MultipleSegments\n*L\n43#1:75,11\n48#1:86\n48#1:87,2\n*E\n"})
    public static final class MultipleSegments
    extends PathSegment {
        @NotNull
        private final List<PathSegment> innerSegments;
        @NotNull
        private final String regex;
        @NotNull
        private final String groupedRegex;

        /*
         * WARNING - void declaration
         */
        public MultipleSegments(@NotNull List<? extends PathSegment> segments) {
            void $this$filterNotTo$iv$iv;
            void $this$filterNot$iv;
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter(segments, "segments");
            super(null);
            Iterable $this$filterIsInstance$iv = segments;
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof MultipleSegments)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            if (!((Collection)((List)destination$iv$iv)).isEmpty()) {
                throw new IllegalStateException("Found MultipleSegment inside MultipleSegments! This is forbidden");
            }
            $this$filterIsInstance$iv = segments;
            MultipleSegments multipleSegments = this;
            boolean $i$f$filterNot = false;
            $this$filterIsInstanceTo$iv$iv = $this$filterNot$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterNotTo = false;
            for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
                PathSegment it = (PathSegment)element$iv$iv;
                boolean bl = false;
                if (it instanceof MultipleSegments) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            multipleSegments.innerSegments = (List)destination$iv$iv;
            this.regex = CollectionsKt.joinToString$default(this.innerSegments, "", null, null, 0, null, regex.1.INSTANCE, 30, null);
            this.groupedRegex = CollectionsKt.joinToString$default(this.innerSegments, "", null, null, 0, null, groupedRegex.1.INSTANCE, 30, null);
        }

        @NotNull
        public final List<PathSegment> getInnerSegments() {
            return this.innerSegments;
        }

        @Override
        @NotNull
        public String asRegexString$javalin() {
            return this.regex;
        }

        @Override
        @NotNull
        public String asGroupedRegexString$javalin() {
            return this.groupedRegex;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/routing/PathSegment$Normal;", "Lio/javalin/routing/PathSegment;", "content", "", "(Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "RegexAllowed", "RegexEscaped", "Lio/javalin/routing/PathSegment$Normal$RegexAllowed;", "Lio/javalin/routing/PathSegment$Normal$RegexEscaped;", "javalin"})
    public static abstract class Normal
    extends PathSegment {
        @NotNull
        private final String content;

        private Normal(String content) {
            super(null);
            this.content = content;
        }

        @NotNull
        public final String getContent() {
            return this.content;
        }

        public /* synthetic */ Normal(String content, DefaultConstructorMarker $constructor_marker) {
            this(content);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0006J\r\u0010\u0007\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\b\u00a8\u0006\t"}, d2={"Lio/javalin/routing/PathSegment$Normal$RegexAllowed;", "Lio/javalin/routing/PathSegment$Normal;", "content", "", "(Ljava/lang/String;)V", "asGroupedRegexString", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
        public static final class RegexAllowed
        extends Normal {
            public RegexAllowed(@NotNull String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                super(content, null);
            }

            @Override
            @NotNull
            public String asRegexString$javalin() {
                return this.getContent();
            }

            @Override
            @NotNull
            public String asGroupedRegexString$javalin() {
                return this.getContent();
            }
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0006J\r\u0010\u0007\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\b\u00a8\u0006\t"}, d2={"Lio/javalin/routing/PathSegment$Normal$RegexEscaped;", "Lio/javalin/routing/PathSegment$Normal;", "content", "", "(Ljava/lang/String;)V", "asGroupedRegexString", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
        public static final class RegexEscaped
        extends Normal {
            public RegexEscaped(@NotNull String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                super(content, null);
            }

            @Override
            @NotNull
            public String asRegexString$javalin() {
                return Regex.Companion.escape(this.getContent());
            }

            @Override
            @NotNull
            public String asGroupedRegexString$javalin() {
                return Regex.Companion.escape(this.getContent());
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/routing/PathSegment$Parameter;", "Lio/javalin/routing/PathSegment;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "SlashAcceptingParameter", "SlashIgnoringParameter", "Lio/javalin/routing/PathSegment$Parameter$SlashAcceptingParameter;", "Lio/javalin/routing/PathSegment$Parameter$SlashIgnoringParameter;", "javalin"})
    public static abstract class Parameter
    extends PathSegment {
        @NotNull
        private final String name;

        private Parameter(String name) {
            super(null);
            this.name = name;
        }

        @NotNull
        public final String getName() {
            return this.name;
        }

        public /* synthetic */ Parameter(String name, DefaultConstructorMarker $constructor_marker) {
            this(name);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0006J\r\u0010\u0007\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\b\u00a8\u0006\t"}, d2={"Lio/javalin/routing/PathSegment$Parameter$SlashAcceptingParameter;", "Lio/javalin/routing/PathSegment$Parameter;", "name", "", "(Ljava/lang/String;)V", "asGroupedRegexString", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
        public static final class SlashAcceptingParameter
        extends Parameter {
            public SlashAcceptingParameter(@NotNull String name) {
                Intrinsics.checkNotNullParameter(name, "name");
                super(name, null);
            }

            @Override
            @NotNull
            public String asRegexString$javalin() {
                return ".+?";
            }

            @Override
            @NotNull
            public String asGroupedRegexString$javalin() {
                return PathSegmentKt.access$grouped(this.asRegexString$javalin());
            }
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\u0006J\r\u0010\u0007\u001a\u00020\u0003H\u0010\u00a2\u0006\u0002\b\b\u00a8\u0006\t"}, d2={"Lio/javalin/routing/PathSegment$Parameter$SlashIgnoringParameter;", "Lio/javalin/routing/PathSegment$Parameter;", "name", "", "(Ljava/lang/String;)V", "asGroupedRegexString", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
        public static final class SlashIgnoringParameter
        extends Parameter {
            public SlashIgnoringParameter(@NotNull String name) {
                Intrinsics.checkNotNullParameter(name, "name");
                super(name, null);
            }

            @Override
            @NotNull
            public String asRegexString$javalin() {
                return "[^/]+?";
            }

            @Override
            @NotNull
            public String asGroupedRegexString$javalin() {
                return PathSegmentKt.access$grouped(this.asRegexString$javalin());
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H\u0010\u00a2\u0006\u0002\b\u0005J\r\u0010\u0006\u001a\u00020\u0004H\u0010\u00a2\u0006\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lio/javalin/routing/PathSegment$Wildcard;", "Lio/javalin/routing/PathSegment;", "()V", "asGroupedRegexString", "", "asGroupedRegexString$javalin", "asRegexString", "asRegexString$javalin", "javalin"})
    public static final class Wildcard
    extends PathSegment {
        @NotNull
        public static final Wildcard INSTANCE = new Wildcard();

        private Wildcard() {
            super(null);
        }

        @Override
        @NotNull
        public String asRegexString$javalin() {
            return ".*?";
        }

        @Override
        @NotNull
        public String asGroupedRegexString$javalin() {
            return this.asRegexString$javalin();
        }
    }
}

