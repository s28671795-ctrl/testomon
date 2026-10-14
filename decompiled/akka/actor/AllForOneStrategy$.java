/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AllForOneStrategy;
import akka.actor.SupervisorStrategy;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple3;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class AllForOneStrategy$
implements Serializable {
    public static final AllForOneStrategy$ MODULE$ = new AllForOneStrategy$();

    public int $lessinit$greater$default$1() {
        return -1;
    }

    public Duration $lessinit$greater$default$2() {
        return Duration$.MODULE$.Inf();
    }

    public boolean $lessinit$greater$default$3() {
        return true;
    }

    public final String toString() {
        return "AllForOneStrategy";
    }

    public AllForOneStrategy apply(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        return new AllForOneStrategy(maxNrOfRetries, withinTimeRange, loggingEnabled, decider);
    }

    public int apply$default$1() {
        return -1;
    }

    public Duration apply$default$2() {
        return Duration$.MODULE$.Inf();
    }

    public boolean apply$default$3() {
        return true;
    }

    public Option<Tuple3<Object, Duration, Object>> unapply(AllForOneStrategy x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, Duration, Object>>(new Tuple3<Integer, Duration, Boolean>(BoxesRunTime.boxToInteger(x$0.maxNrOfRetries()), x$0.withinTimeRange(), BoxesRunTime.boxToBoolean(x$0.loggingEnabled())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AllForOneStrategy$.class);
    }

    private AllForOneStrategy$() {
    }
}

