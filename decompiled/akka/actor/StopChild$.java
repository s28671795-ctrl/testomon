/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.StopChild;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class StopChild$
extends AbstractFunction1<ActorRef, StopChild>
implements Serializable {
    public static final StopChild$ MODULE$ = new StopChild$();

    @Override
    public final String toString() {
        return "StopChild";
    }

    @Override
    public StopChild apply(ActorRef child) {
        return new StopChild(child);
    }

    public Option<ActorRef> unapply(StopChild x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.child());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StopChild$.class);
    }

    private StopChild$() {
    }
}

