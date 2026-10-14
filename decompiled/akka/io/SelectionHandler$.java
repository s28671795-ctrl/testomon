/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.DeathPactException;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import scala.Option;
import scala.util.control.NonFatal$;

public final class SelectionHandler$ {
    public static final SelectionHandler$ MODULE$ = new SelectionHandler$();
    private static final int MaxSelectMillis = 10000;
    private static final SupervisorStrategy connectionSupervisorStrategy = new OneForOneStrategy(){

        public void logFailure(ActorContext context, ActorRef child, Throwable cause, SupervisorStrategy.Directive decision) {
            if (cause instanceof DeathPactException) {
                try {
                    context.system().eventStream().publish(new Logging.Debug(child.path().toString(), this.getClass(), "Closed after handler termination"));
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    }
                    throw throwable;
                }
                return;
            }
            super.logFailure(context, child, cause, decision);
        }
    };

    public int MaxSelectMillis() {
        return MaxSelectMillis;
    }

    public final SupervisorStrategy connectionSupervisorStrategy() {
        return connectionSupervisorStrategy;
    }

    private SelectionHandler$() {
    }
}

