/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ChildRestartStats;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ChildRestartStats$
extends AbstractFunction3<ActorRef, Object, Object, ChildRestartStats>
implements Serializable {
    public static final ChildRestartStats$ MODULE$ = new ChildRestartStats$();

    public int $lessinit$greater$default$2() {
        return 0;
    }

    public long $lessinit$greater$default$3() {
        return 0L;
    }

    @Override
    public final String toString() {
        return "ChildRestartStats";
    }

    @Override
    public ChildRestartStats apply(ActorRef child, int maxNrOfRetriesCount, long restartTimeWindowStartNanos) {
        return new ChildRestartStats(child, maxNrOfRetriesCount, restartTimeWindowStartNanos);
    }

    public int apply$default$2() {
        return 0;
    }

    public long apply$default$3() {
        return 0L;
    }

    public Option<Tuple3<ActorRef, Object, Object>> unapply(ChildRestartStats x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorRef, Object, Object>>(new Tuple3<ActorRef, Integer, Long>(x$0.child(), BoxesRunTime.boxToInteger(x$0.maxNrOfRetriesCount()), BoxesRunTime.boxToLong(x$0.restartTimeWindowStartNanos())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ChildRestartStats$.class);
    }

    private ChildRestartStats$() {
    }
}

