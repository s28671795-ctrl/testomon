/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Traverser;

@Pro
final class CachingTraverser<A, R>
implements Traverser<A, R> {
    final Traverser<A, R> delegate;
    boolean supplied;
    boolean aborted;
    boolean finished;
    A a0;
    R r0;

    CachingTraverser(Traverser<A, R> delegate) {
        this.delegate = delegate;
    }

    private A a(A a) {
        this.finished = false;
        this.a0 = a;
        return this.a0;
    }

    @Override
    public Supplier<A> supplier() {
        return () -> {
            if (this.supplied) {
                return this.a0;
            }
            this.supplied = true;
            return this.a(this.delegate.supplier().get());
        };
    }

    @Override
    public Predicate<A> abort() {
        return a -> {
            if (this.aborted) {
                return true;
            }
            this.aborted = this.delegate.abort().test(a);
            return this.aborted;
        };
    }

    @Override
    public Predicate<QueryPart> recurse() {
        return t2 -> this.delegate.recurse().test((QueryPart)t2);
    }

    @Override
    public Predicate<QueryPart> recurseChildren() {
        return t2 -> this.delegate.recurseChildren().test((QueryPart)t2);
    }

    @Override
    public BiFunction<A, QueryPart, A> before() {
        return (a, p) -> this.a(this.delegate.before().apply((A)a, (QueryPart)p));
    }

    @Override
    public BiFunction<A, QueryPart, A> after() {
        return (a, p) -> this.a(this.delegate.after().apply((A)a, (QueryPart)p));
    }

    @Override
    public Function<A, R> finisher() {
        return a -> {
            if (this.finished) {
                return this.r0;
            }
            this.finished = true;
            this.r0 = this.delegate.finisher().apply(a);
            return this.r0;
        };
    }

    final A supplied() {
        return this.supplier().get();
    }

    final R finished() {
        return this.finisher().apply(this.supplied());
    }

    public String toString() {
        return this.supplied ? "CachingTraverser[accumulated: " + String.valueOf(this.a0) + "]" : "CachingTraverser[empty]";
    }
}

