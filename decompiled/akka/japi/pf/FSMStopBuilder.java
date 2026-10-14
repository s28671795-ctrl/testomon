/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.actor.FSM;
import akka.japi.pf.FI;
import akka.japi.pf.UnitPFBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class FSMStopBuilder<S, D> {
    private UnitPFBuilder<FSM.StopEvent<S, D>> builder = new UnitPFBuilder();

    public FSMStopBuilder<S, D> stop(final FSM.Reason reason, final FI.UnitApply2<S, D> unitApply2) {
        this.builder.match(FSM.StopEvent.class, new FI.TypedPredicate<FSM.StopEvent>(){

            @Override
            public boolean defined(FSM.StopEvent stopEvent) {
                return reason.equals(stopEvent.reason());
            }
        }, new FI.UnitApply<FSM.StopEvent>(){

            @Override
            public void apply(FSM.StopEvent stopEvent) throws Exception {
                Object s2 = stopEvent.currentState();
                Object d = stopEvent.stateData();
                unitApply2.apply(s2, d);
            }
        });
        return this;
    }

    public <P extends FSM.Reason> FSMStopBuilder<S, D> stop(Class<P> clazz, FI.UnitApply3<P, S, D> unitApply3) {
        return this.stop(clazz, new FI.TypedPredicate<P>(){

            @Override
            public boolean defined(P p) {
                return true;
            }
        }, unitApply3);
    }

    public <P extends FSM.Reason> FSMStopBuilder<S, D> stop(final Class<P> clazz, final FI.TypedPredicate<P> typedPredicate, final FI.UnitApply3<P, S, D> unitApply3) {
        this.builder.match(FSM.StopEvent.class, new FI.TypedPredicate<FSM.StopEvent>(){

            @Override
            public boolean defined(FSM.StopEvent stopEvent) {
                if (clazz.isInstance(stopEvent.reason())) {
                    FSM.Reason reason = stopEvent.reason();
                    return typedPredicate.defined(reason);
                }
                return false;
            }
        }, new FI.UnitApply<FSM.StopEvent>(){

            @Override
            public void apply(FSM.StopEvent stopEvent) throws Exception {
                FSM.Reason reason = stopEvent.reason();
                Object s2 = stopEvent.currentState();
                Object d = stopEvent.stateData();
                unitApply3.apply(reason, s2, d);
            }
        });
        return this;
    }

    public PartialFunction<FSM.StopEvent<S, D>, BoxedUnit> build() {
        return this.builder.build();
    }
}

