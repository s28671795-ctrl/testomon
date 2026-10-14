/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.Failed;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Failed$
extends AbstractFunction3<ActorRef, Throwable, Object, Failed>
implements Serializable {
    public static final Failed$ MODULE$ = new Failed$();

    @Override
    public final String toString() {
        return "Failed";
    }

    @Override
    public Failed apply(ActorRef child, Throwable cause, int uid) {
        return new Failed(child, cause, uid);
    }

    public Option<Tuple3<ActorRef, Throwable, Object>> unapply(Failed x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorRef, Throwable, Object>>(new Tuple3<ActorRef, Throwable, Integer>(x$0.child(), x$0.cause(), BoxesRunTime.boxToInteger(x$0.uid())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Failed$.class);
    }

    private Failed$() {
    }
}

