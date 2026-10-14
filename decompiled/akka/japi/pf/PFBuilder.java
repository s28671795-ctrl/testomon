/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.AbstractPFBuilder;
import akka.japi.pf.CaseStatement;
import akka.japi.pf.FI;

public final class PFBuilder<I, R>
extends AbstractPFBuilder<I, R> {
    public <P> PFBuilder<I, R> match(Class<P> clazz, FI.Apply<P, R> apply2) {
        return this.matchUnchecked(clazz, apply2);
    }

    public PFBuilder<I, R> matchUnchecked(final Class<?> clazz, FI.Apply<?, R> apply2) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return clazz.isInstance(object);
            }
        };
        this.addStatement(new CaseStatement(predicate, apply2));
        return this;
    }

    public <P> PFBuilder<I, R> match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.Apply<P, R> apply2) {
        return this.matchUnchecked(clazz, typedPredicate, apply2);
    }

    public PFBuilder<I, R> matchUnchecked(final Class<?> clazz, final FI.TypedPredicate<?> typedPredicate, FI.Apply<?, R> apply2) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                if (!clazz.isInstance(object)) {
                    return false;
                }
                return typedPredicate.defined(object);
            }
        };
        this.addStatement(new CaseStatement(predicate, apply2));
        return this;
    }

    public <P> PFBuilder<I, R> matchEquals(final P p, FI.Apply<P, R> apply2) {
        this.addStatement(new CaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return p.equals(object);
            }
        }, apply2));
        return this;
    }

    public PFBuilder<I, R> matchAny(FI.Apply<I, R> apply2) {
        this.addStatement(new CaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return true;
            }
        }, apply2));
        return this;
    }
}

