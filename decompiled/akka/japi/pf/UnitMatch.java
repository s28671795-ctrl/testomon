/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.AbstractMatch;
import akka.japi.pf.FI;
import akka.japi.pf.UnitPFBuilder;
import scala.MatchError;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class UnitMatch<I>
extends AbstractMatch<I, BoxedUnit> {
    public static <F, P> UnitPFBuilder<F> match(Class<P> clazz, FI.UnitApply<P> unitApply) {
        return new UnitPFBuilder().match(clazz, unitApply);
    }

    public static UnitPFBuilder<Object> matchUnchecked(Class<?> clazz, FI.UnitApply<?> unitApply) {
        return new UnitPFBuilder().matchUnchecked(clazz, unitApply);
    }

    public static <F, P> UnitPFBuilder<F> match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
        return new UnitPFBuilder().match(clazz, typedPredicate, unitApply);
    }

    public static <F, P> UnitPFBuilder<F> matchUnchecked(Class<?> clazz, FI.TypedPredicate<?> typedPredicate, FI.UnitApply<?> unitApply) {
        return new UnitPFBuilder().matchUnchecked(clazz, typedPredicate, unitApply);
    }

    public static <F, P> UnitPFBuilder<F> matchEquals(P p, FI.UnitApply<P> unitApply) {
        return new UnitPFBuilder().matchEquals(p, unitApply);
    }

    public static <F, P> UnitPFBuilder<F> matchEquals(P p, FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
        return new UnitPFBuilder().matchEquals(p, typedPredicate, unitApply);
    }

    public static <F> UnitPFBuilder<F> matchAny(FI.UnitApply<Object> unitApply) {
        return new UnitPFBuilder().matchAny(unitApply);
    }

    public static <F> UnitMatch<F> create(UnitPFBuilder<F> unitPFBuilder) {
        return new UnitMatch(unitPFBuilder.build());
    }

    private UnitMatch(PartialFunction<I, BoxedUnit> partialFunction) {
        super(partialFunction);
    }

    public void match(I i) throws MatchError {
        this.statements.apply(i);
    }
}

