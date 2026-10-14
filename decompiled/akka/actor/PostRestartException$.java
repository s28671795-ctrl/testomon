/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.PostRestartException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class PostRestartException$
extends AbstractFunction3<ActorRef, Throwable, Throwable, PostRestartException>
implements Serializable {
    public static final PostRestartException$ MODULE$ = new PostRestartException$();

    @Override
    public final String toString() {
        return "PostRestartException";
    }

    @Override
    public PostRestartException apply(ActorRef actor, Throwable cause, Throwable originalCause) {
        return new PostRestartException(actor, cause, originalCause);
    }

    public Option<Tuple3<ActorRef, Throwable, Throwable>> unapply(PostRestartException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorRef, Throwable, Throwable>>(new Tuple3<ActorRef, Throwable, Throwable>(x$0.actor(), x$0.cause(), x$0.originalCause()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PostRestartException$.class);
    }

    private PostRestartException$() {
    }
}

