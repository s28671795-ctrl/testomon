/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.PhaseIsland;
import akka.stream.impl.SavedIslandData;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class SavedIslandData$
extends AbstractFunction4<Object, Object, Object, PhaseIsland<Object>, SavedIslandData>
implements Serializable {
    public static final SavedIslandData$ MODULE$ = new SavedIslandData$();

    @Override
    public final String toString() {
        return "SavedIslandData";
    }

    @Override
    public SavedIslandData apply(int islandGlobalOffset, int lastVisitedOffset, int skippedSlots, PhaseIsland<Object> phase) {
        return new SavedIslandData(islandGlobalOffset, lastVisitedOffset, skippedSlots, phase);
    }

    public Option<Tuple4<Object, Object, Object, PhaseIsland<Object>>> unapply(SavedIslandData x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, Object, Object, PhaseIsland<Object>>>(new Tuple4<Integer, Integer, Integer, PhaseIsland<Object>>(BoxesRunTime.boxToInteger(x$0.islandGlobalOffset()), BoxesRunTime.boxToInteger(x$0.lastVisitedOffset()), BoxesRunTime.boxToInteger(x$0.skippedSlots()), x$0.phase()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SavedIslandData$.class);
    }

    private SavedIslandData$() {
    }
}

