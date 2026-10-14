/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.FI;
import akka.japi.pf.UnitPFBuilder;
import scala.PartialFunction;
import scala.Tuple2;
import scala.runtime.BoxedUnit;

public class FSMTransitionHandlerBuilder<S> {
    private final UnitPFBuilder<Tuple2<S, S>> builder = new UnitPFBuilder();

    public FSMTransitionHandlerBuilder<S> state(final S s2, final S s3, final FI.UnitApplyVoid unitApplyVoid) {
        this.builder.match(Tuple2.class, new FI.TypedPredicate<Tuple2>(){

            @Override
            public boolean defined(Tuple2 tuple2) {
                return !(s2 != null && !s2.equals(tuple2._1()) || s3 != null && !s3.equals(tuple2._2()));
            }
        }, new FI.UnitApply<Tuple2>(){

            @Override
            public void apply(Tuple2 tuple2) throws Exception {
                unitApplyVoid.apply();
            }
        });
        return this;
    }

    public FSMTransitionHandlerBuilder<S> state(final S s2, final S s3, final FI.UnitApply2<S, S> unitApply2) {
        this.builder.match(Tuple2.class, new FI.TypedPredicate<Tuple2>(){

            @Override
            public boolean defined(Tuple2 tuple2) {
                return !(s2 != null && !s2.equals(tuple2._1()) || s3 != null && !s3.equals(tuple2._2()));
            }
        }, new FI.UnitApply<Tuple2>(){

            @Override
            public void apply(Tuple2 tuple2) throws Exception {
                Object T1 = tuple2._1();
                Object T2 = tuple2._2();
                unitApply2.apply(T1, T2);
            }
        });
        return this;
    }

    public PartialFunction<Tuple2<S, S>, BoxedUnit> build() {
        return this.builder.build();
    }
}

