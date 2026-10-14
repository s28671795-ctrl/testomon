/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Traverser;

@ApiStatus.Experimental
@Pro
public final class Traversers {
    public static <A, R> Traverser<?, R> recursing(Predicate<? super QueryPart> recurse, Traverser<A, R> downstream) {
        return Traverser.of(downstream.supplier(), downstream.abort(), p -> recurse.test((QueryPart)p) && downstream.recurse().test((QueryPart)p), downstream.recurseChildren(), downstream.before(), downstream.after(), downstream.finisher());
    }

    public static <A, R> Traverser<?, R> recursingChildren(Predicate<? super QueryPart> recurseChildren, Traverser<A, R> downstream) {
        return Traverser.of(downstream.supplier(), downstream.abort(), downstream.recurse(), p -> recurseChildren.test((QueryPart)p) && downstream.recurseChildren().test((QueryPart)p), downstream.before(), downstream.after(), downstream.finisher());
    }

    public static <T, R> Traverser<?, R> collecting(Collector<QueryPart, T, R> collector) {
        return Traverser.of(collector.supplier(), t2 -> false, p -> true, p -> true, (t2, p) -> {
            collector.accumulator().accept(t2, p);
            return t2;
        }, (t2, p) -> t2, collector.finisher());
    }

    @NotNull
    public static Traverser<?, Boolean> containing(QueryPart part) {
        return Traversers.containing(part::equals);
    }

    @NotNull
    public static Traverser<?, Boolean> containing(Predicate<? super QueryPart> predicate) {
        return Traverser.of(() -> false, b -> b, p -> true, (b, p) -> b != false || predicate.test((QueryPart)p));
    }

    @NotNull
    public static Traverser<?, Optional<QueryPart>> findingAny(Predicate<? super QueryPart> predicate) {
        return Traverser.of(() -> null, p -> p != null, p -> true, p -> true, (r, p) -> predicate.test((QueryPart)p) ? p : r, (r, p) -> r, Optional::ofNullable);
    }

    @NotNull
    public static Traverser<?, List<QueryPart>> findingAll(Predicate<? super QueryPart> predicate) {
        return Traverser.of(() -> new ArrayList(), (l, p) -> {
            if (predicate.test((QueryPart)p)) {
                l.add(p);
            }
            return l;
        }, (l, p) -> l);
    }

    @NotNull
    public static Traverser<?, ?> consuming(Consumer<? super QueryPart> consumer) {
        return Traverser.of(() -> null, (n, p) -> {
            consumer.accept((QueryPart)p);
            return n;
        });
    }

    private Traversers() {
    }
}

