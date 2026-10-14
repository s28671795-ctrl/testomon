/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.PreRestartException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.ModuleSerializationProxy;

public final class PreRestartException$
extends AbstractFunction4<ActorRef, Throwable, Throwable, Option<Object>, PreRestartException>
implements Serializable {
    public static final PreRestartException$ MODULE$ = new PreRestartException$();

    @Override
    public final String toString() {
        return "PreRestartException";
    }

    @Override
    public PreRestartException apply(ActorRef actor, Throwable cause, Throwable originalCause, Option<Object> messageOption) {
        return new PreRestartException(actor, cause, originalCause, messageOption);
    }

    public Option<Tuple4<ActorRef, Throwable, Throwable, Option<Object>>> unapply(PreRestartException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<ActorRef, Throwable, Throwable, Option<Object>>>(new Tuple4<ActorRef, Throwable, Throwable, Option<Object>>(x$0.actor(), x$0.cause(), x$0.originalCause(), x$0.messageOption()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PreRestartException$.class);
    }

    private PreRestartException$() {
    }
}

