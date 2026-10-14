/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ScatterGatherFirstCompletedRoutingLogic;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ScatterGatherFirstCompletedRoutingLogic$
extends AbstractFunction1<FiniteDuration, ScatterGatherFirstCompletedRoutingLogic>
implements Serializable {
    public static final ScatterGatherFirstCompletedRoutingLogic$ MODULE$ = new ScatterGatherFirstCompletedRoutingLogic$();

    @Override
    public final String toString() {
        return "ScatterGatherFirstCompletedRoutingLogic";
    }

    @Override
    public ScatterGatherFirstCompletedRoutingLogic apply(FiniteDuration within) {
        return new ScatterGatherFirstCompletedRoutingLogic(within);
    }

    public Option<FiniteDuration> unapply(ScatterGatherFirstCompletedRoutingLogic x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<FiniteDuration>(x$0.within());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScatterGatherFirstCompletedRoutingLogic$.class);
    }

    private ScatterGatherFirstCompletedRoutingLogic$() {
    }
}

