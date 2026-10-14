/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.InternalActorRef;
import akka.annotation.InternalApi;
import akka.pattern.PromiseActorRef;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class InternalActorRef$
implements Serializable {
    public static final InternalActorRef$ MODULE$ = new InternalActorRef$();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isTemporaryRef(ActorRef ref) {
        ActorRef actorRef2 = ref;
        if (!(actorRef2 instanceof InternalActorRef)) throw new IllegalArgumentException(new StringBuilder(26).append("ActorRef is not internal: ").append(actorRef2).toString());
        InternalActorRef internalActorRef = (InternalActorRef)actorRef2;
        if (internalActorRef.isLocal()) {
            if (internalActorRef instanceof PromiseActorRef) return true;
        }
        if (internalActorRef.isLocal()) return false;
        Object a = internalActorRef.path().elements().head();
        String string = "temp";
        if (a != null) {
            if (!a.equals(string)) return false;
            return true;
        }
        if (string == null) return true;
        return false;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InternalActorRef$.class);
    }

    private InternalActorRef$() {
    }
}

