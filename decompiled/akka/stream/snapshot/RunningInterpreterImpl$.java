/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.RunningInterpreterImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class RunningInterpreterImpl$
extends AbstractFunction5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Object, Seq<LogicSnapshot>, RunningInterpreterImpl>
implements Serializable {
    public static final RunningInterpreterImpl$ MODULE$ = new RunningInterpreterImpl$();

    @Override
    public final String toString() {
        return "RunningInterpreterImpl";
    }

    @Override
    public RunningInterpreterImpl apply(Seq<LogicSnapshot> logics, Seq<ConnectionSnapshot> connections, String queueStatus, int runningLogicsCount, Seq<LogicSnapshot> stoppedLogics) {
        return new RunningInterpreterImpl(logics, connections, queueStatus, runningLogicsCount, stoppedLogics);
    }

    public Option<Tuple5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Object, Seq<LogicSnapshot>>> unapply(RunningInterpreterImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Object, Seq<LogicSnapshot>>>(new Tuple5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Integer, Seq<LogicSnapshot>>(x$0.logics(), x$0.connections(), x$0.queueStatus(), BoxesRunTime.boxToInteger(x$0.runningLogicsCount()), x$0.stoppedLogics()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RunningInterpreterImpl$.class);
    }

    private RunningInterpreterImpl$() {
    }
}

