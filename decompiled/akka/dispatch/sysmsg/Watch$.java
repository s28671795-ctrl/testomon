/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.InternalActorRef;
import akka.dispatch.sysmsg.Watch;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class Watch$
extends AbstractFunction2<InternalActorRef, InternalActorRef, Watch>
implements Serializable {
    public static final Watch$ MODULE$ = new Watch$();

    @Override
    public final String toString() {
        return "Watch";
    }

    @Override
    public Watch apply(InternalActorRef watchee, InternalActorRef watcher) {
        return new Watch(watchee, watcher);
    }

    public Option<Tuple2<InternalActorRef, InternalActorRef>> unapply(Watch x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<InternalActorRef, InternalActorRef>>(new Tuple2<InternalActorRef, InternalActorRef>(x$0.watchee(), x$0.watcher()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Watch$.class);
    }

    private Watch$() {
    }
}

