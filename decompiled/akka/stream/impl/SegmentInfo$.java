/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.PhaseIsland;
import akka.stream.impl.SegmentInfo;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class SegmentInfo$
extends AbstractFunction5<Object, Object, Object, Object, PhaseIsland<Object>, SegmentInfo>
implements Serializable {
    public static final SegmentInfo$ MODULE$ = new SegmentInfo$();

    @Override
    public final String toString() {
        return "SegmentInfo";
    }

    @Override
    public SegmentInfo apply(int globalislandOffset, int length, int globalBaseOffset, int relativeBaseOffset, PhaseIsland<Object> phase) {
        return new SegmentInfo(globalislandOffset, length, globalBaseOffset, relativeBaseOffset, phase);
    }

    public Option<Tuple5<Object, Object, Object, Object, PhaseIsland<Object>>> unapply(SegmentInfo x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<Object, Object, Object, Object, PhaseIsland<Object>>>(new Tuple5<Integer, Integer, Integer, Integer, PhaseIsland<Object>>(BoxesRunTime.boxToInteger(x$0.globalislandOffset()), BoxesRunTime.boxToInteger(x$0.length()), BoxesRunTime.boxToInteger(x$0.globalBaseOffset()), BoxesRunTime.boxToInteger(x$0.relativeBaseOffset()), x$0.phase()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SegmentInfo$.class);
    }

    private SegmentInfo$() {
    }
}

