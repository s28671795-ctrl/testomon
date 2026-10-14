/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.OutPort;
import akka.stream.impl.ForwardWire;
import akka.stream.impl.PhaseIsland;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ForwardWire$
extends AbstractFunction5<Object, OutPort, Object, Object, PhaseIsland<Object>, ForwardWire>
implements Serializable {
    public static final ForwardWire$ MODULE$ = new ForwardWire$();

    @Override
    public final String toString() {
        return "ForwardWire";
    }

    @Override
    public ForwardWire apply(int islandGlobalOffset, OutPort from, int toGlobalOffset, Object outStage, PhaseIsland<Object> phase) {
        return new ForwardWire(islandGlobalOffset, from, toGlobalOffset, outStage, phase);
    }

    public Option<Tuple5<Object, OutPort, Object, Object, PhaseIsland<Object>>> unapply(ForwardWire x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<Object, OutPort, Object, Object, PhaseIsland<Object>>>(new Tuple5<Integer, OutPort, Integer, Object, PhaseIsland<Object>>(BoxesRunTime.boxToInteger(x$0.islandGlobalOffset()), x$0.from(), BoxesRunTime.boxToInteger(x$0.toGlobalOffset()), x$0.outStage(), x$0.phase()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ForwardWire$.class);
    }

    private ForwardWire$() {
    }
}

