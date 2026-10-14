/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.AbstractActor;
import akka.actor.ActorContext;
import akka.event.Logging$;
import akka.event.LoggingReceive;
import scala.None$;
import scala.Option$;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public final class LoggingReceive$ {
    public static final LoggingReceive$ MODULE$ = new LoggingReceive$();

    public PartialFunction<Object, BoxedUnit> apply(PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        return this.withLabel(null, r, context);
    }

    public PartialFunction<Object, BoxedUnit> apply(int logLevel, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        return this.withLabel(null, logLevel, r, context);
    }

    public AbstractActor.Receive create(AbstractActor.Receive r, AbstractActor.ActorContext context) {
        return new AbstractActor.Receive(this.apply(r.onMessage(), context));
    }

    public PartialFunction<Object, BoxedUnit> withLabel(String label, int logLevel, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        PartialFunction<Object, BoxedUnit> partialFunction = r;
        if (partialFunction instanceof LoggingReceive) {
            return r;
        }
        if (context.system().settings().AddLoggingReceive()) {
            return new LoggingReceive(None$.MODULE$, r, Option$.MODULE$.apply(label), logLevel, context);
        }
        return r;
    }

    public PartialFunction<Object, BoxedUnit> withLabel(String label, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        return this.withLabel(label, Logging$.MODULE$.DebugLevel(), r, context);
    }

    private LoggingReceive$() {
    }
}

