/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.UninitializedInterpreterImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class UninitializedInterpreterImpl$
extends AbstractFunction1<Seq<LogicSnapshot>, UninitializedInterpreterImpl>
implements Serializable {
    public static final UninitializedInterpreterImpl$ MODULE$ = new UninitializedInterpreterImpl$();

    @Override
    public final String toString() {
        return "UninitializedInterpreterImpl";
    }

    @Override
    public UninitializedInterpreterImpl apply(Seq<LogicSnapshot> logics) {
        return new UninitializedInterpreterImpl(logics);
    }

    public Option<Seq<LogicSnapshot>> unapply(UninitializedInterpreterImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Seq<LogicSnapshot>>(x$0.logics());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UninitializedInterpreterImpl$.class);
    }

    private UninitializedInterpreterImpl$() {
    }
}

