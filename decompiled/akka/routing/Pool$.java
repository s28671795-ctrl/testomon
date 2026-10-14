/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.OneForOneStrategy;
import akka.actor.OneForOneStrategy$;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$Escalate$;
import java.io.Serializable;
import scala.Function1;
import scala.PartialFunction;
import scala.runtime.ModuleSerializationProxy;

public final class Pool$
implements Serializable {
    public static final Pool$ MODULE$ = new Pool$();
    private static final SupervisorStrategy defaultSupervisorStrategy = new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), (PartialFunction<Throwable, SupervisorStrategy.Directive>)((Object)new Serializable(){
        private static final long serialVersionUID = 0L;

        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
            A1 A1 = x1;
            return (B1)SupervisorStrategy$Escalate$.MODULE$;
        }

        public final boolean isDefinedAt(Throwable x1) {
            Throwable throwable = x1;
            return true;
        }
    }));

    public SupervisorStrategy defaultSupervisorStrategy() {
        return defaultSupervisorStrategy;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Pool$.class);
    }

    private Pool$() {
    }
}

