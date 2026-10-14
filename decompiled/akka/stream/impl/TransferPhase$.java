/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

public final class TransferPhase$
implements Serializable {
    public static final TransferPhase$ MODULE$ = new TransferPhase$();

    public final String toString() {
        return "TransferPhase";
    }

    public TransferPhase apply(TransferState precondition, Function0<BoxedUnit> action) {
        return new TransferPhase(precondition, action);
    }

    public Option<TransferState> unapply(TransferPhase x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<TransferState>(x$0.precondition());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TransferPhase$.class);
    }

    private TransferPhase$() {
    }
}

