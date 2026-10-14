/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.actor.AbstractActor;
import akka.japi.pf.CaseStatement;
import akka.japi.pf.FI;
import akka.japi.pf.UnitCaseStatement;
import java.util.function.BooleanSupplier;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class ReceiveBuilder {
    private PartialFunction<Object, BoxedUnit> statements = null;
    private static final FI.Predicate ALWAYS_TRUE = object -> true;

    protected void addStatement(PartialFunction<Object, BoxedUnit> partialFunction) {
        this.statements = this.statements == null ? partialFunction : this.statements.orElse(partialFunction);
    }

    public AbstractActor.Receive build() {
        PartialFunction<Object, BoxedUnit> partialFunction = CaseStatement.empty();
        if (this.statements == null) {
            return new AbstractActor.Receive(partialFunction);
        }
        return new AbstractActor.Receive(this.statements.orElse(partialFunction));
    }

    public static ReceiveBuilder create() {
        return new ReceiveBuilder();
    }

    public <P> ReceiveBuilder match(Class<P> clazz, FI.UnitApply<P> unitApply) {
        return this.matchUnchecked(clazz, unitApply);
    }

    public ReceiveBuilder matchUnchecked(final Class<?> clazz, FI.UnitApply<?> unitApply) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return clazz.isInstance(object);
            }
        };
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }

    public <P> ReceiveBuilder match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
        return this.matchUnchecked(clazz, typedPredicate, unitApply);
    }

    public <P> ReceiveBuilder match(Class<P> clazz, BooleanSupplier booleanSupplier, FI.UnitApply<P> unitApply) {
        return this.matchUnchecked(clazz, booleanSupplier, unitApply);
    }

    public <P> ReceiveBuilder matchUnchecked(final Class<?> clazz, final FI.TypedPredicate<?> typedPredicate, FI.UnitApply<P> unitApply) {
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

    public <P> ReceiveBuilder matchUnchecked(final Class<?> clazz, final BooleanSupplier booleanSupplier, FI.UnitApply<P> unitApply) {
        FI.Predicate predicate = new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return clazz.isInstance(object) && booleanSupplier.getAsBoolean();
            }
        };
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }

    public <P> ReceiveBuilder matchEquals(final P p, FI.UnitApply<P> unitApply) {
        this.addStatement(new UnitCaseStatement(new FI.Predicate(){

            @Override
            public boolean defined(Object object) {
                return p.equals(object);
            }
        }, unitApply));
        return this;
    }

    public <P> ReceiveBuilder matchEquals(final P p, final FI.TypedPredicate<P> typedPredicate, FI.UnitApply<P> unitApply) {
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

    public <P> ReceiveBuilder matchEquals(P p, BooleanSupplier booleanSupplier, FI.UnitApply<P> unitApply) {
        FI.Predicate predicate = object2 -> p.equals(object2) && booleanSupplier.getAsBoolean();
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }

    public ReceiveBuilder matchAny(FI.UnitApply<Object> unitApply) {
        this.addStatement(new UnitCaseStatement(ALWAYS_TRUE, unitApply));
        return this;
    }

    public ReceiveBuilder matchAny(BooleanSupplier booleanSupplier, FI.UnitApply<Object> unitApply) {
        FI.Predicate predicate = object -> booleanSupplier.getAsBoolean();
        this.addStatement(new UnitCaseStatement(predicate, unitApply));
        return this;
    }
}

