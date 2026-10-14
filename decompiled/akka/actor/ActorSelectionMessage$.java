/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorSelectionMessage;
import akka.actor.SelectionPathElement;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.Iterable;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ActorSelectionMessage$
extends AbstractFunction3<Object, Iterable<SelectionPathElement>, Object, ActorSelectionMessage>
implements Serializable {
    public static final ActorSelectionMessage$ MODULE$ = new ActorSelectionMessage$();

    @Override
    public final String toString() {
        return "ActorSelectionMessage";
    }

    @Override
    public ActorSelectionMessage apply(Object msg, Iterable<SelectionPathElement> elements, boolean wildcardFanOut) {
        return new ActorSelectionMessage(msg, elements, wildcardFanOut);
    }

    public Option<Tuple3<Object, Iterable<SelectionPathElement>, Object>> unapply(ActorSelectionMessage x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, Iterable<SelectionPathElement>, Object>>(new Tuple3<Object, Iterable<SelectionPathElement>, Boolean>(x$0.msg(), x$0.elements(), BoxesRunTime.boxToBoolean(x$0.wildcardFanOut())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorSelectionMessage$.class);
    }

    private ActorSelectionMessage$() {
    }
}

