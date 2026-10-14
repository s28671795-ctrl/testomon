/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Function2;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class FunctionRef$
implements Serializable {
    public static final FunctionRef$ MODULE$ = new FunctionRef$();

    public Function2<ActorRef, Object, BoxedUnit> deadLetterMessageHandler(ActorSystem system) {
        return (Function2<ActorRef, Object, Object> & Serializable)(sender, msg) -> {
            FunctionRef$.$anonfun$deadLetterMessageHandler$1(system, sender, msg);
            return BoxedUnit.UNIT;
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(FunctionRef$.class);
    }

    public static final /* synthetic */ void $anonfun$deadLetterMessageHandler$1(ActorSystem system$1, ActorRef sender, Object msg) {
        if (!(msg instanceof DeadLetter)) {
            system$1.deadLetters().tell(msg, sender);
            return;
        }
    }

    private FunctionRef$() {
    }
}

