/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.Pro;
import org.jooq.QueryPart;

@ApiStatus.Experimental
@Pro
public interface Traverser<A, R> {
    public static <R> Traverser<R, R> of(Supplier<R> supplier, BiFunction<? super R, ? super QueryPart, ? extends R> before) {
        return Traverser.of(supplier, a -> false, q -> true, q -> true, before, (a, q) -> a, a -> a);
    }

    public static <R> Traverser<R, R> of(Supplier<R> supplier, BiFunction<? super R, ? super QueryPart, ? extends R> before, BiFunction<? super R, ? super QueryPart, ? extends R> after) {
        return Traverser.of(supplier, a -> false, q -> true, q -> true, before, after, a -> a);
    }

    public static <R> Traverser<R, R> of(Supplier<R> supplier, Predicate<? super R> abort, Predicate<? super QueryPart> recurse, BiFunction<? super R, ? super QueryPart, ? extends R> before) {
        return Traverser.of(supplier, abort, recurse, q -> true, before, (a, q) -> a, a -> a);
    }

    public static <R> Traverser<R, R> of(Supplier<R> supplier, Predicate<? super R> abort, Predicate<? super QueryPart> recurse, BiFunction<? super R, ? super QueryPart, ? extends R> before, BiFunction<? super R, ? super QueryPart, ? extends R> after) {
        return Traverser.of(supplier, abort, recurse, q -> true, before, after, a -> a);
    }

    public static <A, R> Traverser<A, R> of(Supplier<A> supplier, Predicate<? super A> abort, Predicate<? super QueryPart> recurse, BiFunction<? super A, ? super QueryPart, ? extends A> before, BiFunction<? super A, ? super QueryPart, ? extends A> after, Function<? super A, ? extends R> finisher) {
        return Traverser.of(supplier, abort, recurse, q -> true, before, after, finisher);
    }

    public static <A, R> Traverser<A, R> of(final Supplier<A> supplier, final Predicate<? super A> abort, final Predicate<? super QueryPart> recurse, final Predicate<? super QueryPart> recurseChildren, final BiFunction<? super A, ? super QueryPart, ? extends A> before, final BiFunction<? super A, ? super QueryPart, ? extends A> after, final Function<? super A, ? extends R> finisher) {
        return new Traverser<A, R>(){

            @Override
            public Supplier<A> supplier() {
                return supplier;
            }

            @Override
            public Predicate<A> abort() {
                return abort;
            }

            @Override
            public Predicate<QueryPart> recurse() {
                return recurse;
            }

            @Override
            public Predicate<QueryPart> recurseChildren() {
                return recurseChildren;
            }

            @Override
            public BiFunction<A, QueryPart, A> before() {
                return before;
            }

            @Override
            public BiFunction<A, QueryPart, A> after() {
                return after;
            }

            @Override
            public Function<A, R> finisher() {
                return finisher;
            }
        };
    }

    public Supplier<A> supplier();

    public Predicate<A> abort();

    public Predicate<QueryPart> recurse();

    public Predicate<QueryPart> recurseChildren();

    public BiFunction<A, QueryPart, A> before();

    public BiFunction<A, QueryPart, A> after();

    public Function<A, R> finisher();
}

