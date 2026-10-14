/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.AbstractPFBuilder;
import akka.japi.pf.FI;
import akka.japi.pf.UnitCaseStatement;
import scala.runtime.BoxedUnit;

public final class UnitPFBuilder<I>
extends AbstractPFBuilder<I, BoxedUnit> {
    public <P> UnitPFBuilder<I> match(Class<P> clazz, FI.UnitApply<P> unitApply) {
        return this.matchUnchecked(clazz, unitApply);
    }

    public UnitPFBuilder<I> matchUnchecked(final Class<?> clazz, FI.UnitApply<?> unitApply) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return clazz.isInstance(object);
            }
        };
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }

    public <P> UnitPFBuilder<I> match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
        return this.matchUnchecked(clazz, typedPredicate, unitApply);
    }

    public UnitPFBuilder<I> matchUnchecked(final Class<?> clazz, final FI.TypedPredicate<?> typedPredicate, FI.UnitApply<?> unitApply) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                if (!clazz.isInstance(object)) {
                    return false;
                }
                return typedPredicate.defined(object);
            }
        };
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }

    public <P> UnitPFBuilder<I> matchEquals(final P p, FI.UnitApply<P> unitApply) {
        this.addStatement(new UnitCaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return p.equals(object);
            }
        }, unitApply));
        return this;
    }

    public <P> UnitPFBuilder<I> matchEquals(final P p, final FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
        this.addStatement(new UnitCaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                if (!p.equals(object)) {
                    return false;
                }
                Object object2 = object;
                return typedPredicate.defined(object2);
            }
        }, unitApply));
        return this;
    }

    public UnitPFBuilder<I> matchAny(FI.UnitApply<Object> unitApply) {
        this.addStatement(new UnitCaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return true;
            }
        }, unitApply));
        return this;
    }
}

