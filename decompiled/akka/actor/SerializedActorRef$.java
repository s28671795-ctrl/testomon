/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.SerializedActorRef;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class SerializedActorRef$
implements Serializable {
    public static final SerializedActorRef$ MODULE$ = new SerializedActorRef$();

    public SerializedActorRef apply(ActorRef actorRef2) {
        return new SerializedActorRef(actorRef2);
    }

    public SerializedActorRef apply(String path) {
        return new SerializedActorRef(path);
    }

    public Option<String> unapply(SerializedActorRef x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.path());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SerializedActorRef$.class);
    }

    private SerializedActorRef$() {
    }
}

