/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.AbstractMatch;
import akka.japi.pf.FI;
import akka.japi.pf.PFBuilder;
import scala.MatchError;
import scala.PartialFunction;

public class Match<I, R>
extends AbstractMatch<I, R> {
    public static <F, T, P> PFBuilder<F, T> match(Class<P> clazz, FI.Apply<P, T> apply2) {
        return new PFBuilder().match(clazz, apply2);
    }

    public static <F, T> PFBuilder<F, T> matchUnchecked(Class<?> clazz, FI.Apply<?, T> apply2) {
        return new PFBuilder().matchUnchecked(clazz, apply2);
    }

    public static <F, T, P> PFBuilder<F, T> match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.Apply<P, T> apply2) {
        return new PFBuilder().match(clazz, typedPredicate, apply2);
    }

    public static <F, T> PFBuilder<F, T> matchUnchecked(Class<?> clazz, FI.TypedPredicate<?> typedPredicate, FI.Apply<?, T> apply2) {
        return new PFBuilder().matchUnchecked(clazz, typedPredicate, apply2);
    }

    public static <F, T, P> PFBuilder<F, T> matchEquals(P p, FI.Apply<P, T> apply2) {
        return new PFBuilder().matchEquals(p, apply2);
    }

    public static <F, T> PFBuilder<F, T> matchAny(FI.Apply<F, T> apply2) {
        return new PFBuilder<F, T>().matchAny(apply2);
    }

    public static final <F, T> Match<F, T> create(PFBuilder<F, T> pFBuilder) {
        return new Match(pFBuilder.build());
    }

    Match(PartialFunction<I, R> partialFunction) {
        super(partialFunction);
    }

    public R match(I i) throws MatchError {
        return this.statements.apply(i);
    }
}

