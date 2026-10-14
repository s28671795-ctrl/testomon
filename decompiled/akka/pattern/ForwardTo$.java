/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.pattern.ForwardTo;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ForwardTo$
extends AbstractFunction1<ActorRef, ForwardTo>
implements Serializable {
    public static final ForwardTo$ MODULE$ = new ForwardTo$();

    @Override
    public final String toString() {
        return "ForwardTo";
    }

    @Override
    public ForwardTo apply(ActorRef handler) {
        return new ForwardTo(handler);
    }

    public Option<ActorRef> unapply(ForwardTo x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.handler());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ForwardTo$.class);
    }

    private ForwardTo$() {
    }
}

