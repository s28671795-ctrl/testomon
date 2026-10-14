/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.actor.ActorRef;
import akka.stream.impl.fusing.Watch;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Watch$
implements Serializable {
    public static final Watch$ MODULE$ = new Watch$();

    public final String toString() {
        return "Watch";
    }

    public <T> Watch<T> apply(ActorRef targetRef) {
        return new Watch(targetRef);
    }

    public <T> Option<ActorRef> unapply(Watch<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.targetRef());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Watch$.class);
    }

    private Watch$() {
    }
}

