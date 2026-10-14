/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorSelection;
import akka.routing.ActorSelectionRoutee;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ActorSelectionRoutee$
extends AbstractFunction1<ActorSelection, ActorSelectionRoutee>
implements Serializable {
    public static final ActorSelectionRoutee$ MODULE$ = new ActorSelectionRoutee$();

    @Override
    public final String toString() {
        return "ActorSelectionRoutee";
    }

    @Override
    public ActorSelectionRoutee apply(ActorSelection selection) {
        return new ActorSelectionRoutee(selection);
    }

    public Option<ActorSelection> unapply(ActorSelectionRoutee x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorSelection>(x$0.selection());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorSelectionRoutee$.class);
    }

    private ActorSelectionRoutee$() {
    }
}

