/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.WithListeners;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

public final class WithListeners$
extends AbstractFunction1<Function1<ActorRef, BoxedUnit>, WithListeners>
implements Serializable {
    public static final WithListeners$ MODULE$ = new WithListeners$();

    @Override
    public final String toString() {
        return "WithListeners";
    }

    @Override
    public WithListeners apply(Function1<ActorRef, BoxedUnit> f) {
        return new WithListeners(f);
    }

    public Option<Function1<ActorRef, BoxedUnit>> unapply(WithListeners x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Function1<ActorRef, BoxedUnit>>(x$0.f());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WithListeners$.class);
    }

    private WithListeners$() {
    }
}

