/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.TransferPhase;
import akka.stream.impl.WaitingForUpstreamSubscription;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class WaitingForUpstreamSubscription$
extends AbstractFunction2<Object, TransferPhase, WaitingForUpstreamSubscription>
implements Serializable {
    public static final WaitingForUpstreamSubscription$ MODULE$ = new WaitingForUpstreamSubscription$();

    @Override
    public final String toString() {
        return "WaitingForUpstreamSubscription";
    }

    @Override
    public WaitingForUpstreamSubscription apply(int remaining, TransferPhase andThen) {
        return new WaitingForUpstreamSubscription(remaining, andThen);
    }

    public Option<Tuple2<Object, TransferPhase>> unapply(WaitingForUpstreamSubscription x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, TransferPhase>>(new Tuple2<Integer, TransferPhase>(BoxesRunTime.boxToInteger(x$0.remaining()), x$0.andThen()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WaitingForUpstreamSubscription$.class);
    }

    private WaitingForUpstreamSubscription$() {
    }
}

