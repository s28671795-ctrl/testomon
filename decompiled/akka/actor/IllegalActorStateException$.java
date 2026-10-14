/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.IllegalActorStateException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class IllegalActorStateException$
extends AbstractFunction1<String, IllegalActorStateException>
implements Serializable {
    public static final IllegalActorStateException$ MODULE$ = new IllegalActorStateException$();

    @Override
    public final String toString() {
        return "IllegalActorStateException";
    }

    @Override
    public IllegalActorStateException apply(String message) {
        return new IllegalActorStateException(message);
    }

    public Option<String> unapply(IllegalActorStateException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.message());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IllegalActorStateException$.class);
    }

    private IllegalActorStateException$() {
    }
}

