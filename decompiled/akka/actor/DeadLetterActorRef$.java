/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.DeadLetterActorRef;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class DeadLetterActorRef$
implements Serializable {
    public static final DeadLetterActorRef$ MODULE$ = new DeadLetterActorRef$();
    private static final DeadLetterActorRef.SerializedDeadLetterActorRef serialized = new DeadLetterActorRef.SerializedDeadLetterActorRef();

    public DeadLetterActorRef.SerializedDeadLetterActorRef serialized() {
        return serialized;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeadLetterActorRef$.class);
    }

    private DeadLetterActorRef$() {
    }
}

