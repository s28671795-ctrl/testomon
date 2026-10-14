/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.impl.fusing.ActorGraphInterpreter;
import akka.stream.impl.fusing.GraphInterpreterShell;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ClassTag$;

@InternalApi
public final class ActorGraphInterpreter$ {
    public static final ActorGraphInterpreter$ MODULE$ = new ActorGraphInterpreter$();

    public Props props(GraphInterpreterShell shell) {
        return Props$.MODULE$.apply((Function0<ActorGraphInterpreter> & Serializable)() -> new ActorGraphInterpreter(shell), ClassTag$.MODULE$.apply(ActorGraphInterpreter.class)).withDeploy(Deploy$.MODULE$.local());
    }

    private ActorGraphInterpreter$() {
    }
}

