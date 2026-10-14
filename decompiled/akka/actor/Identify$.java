/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Identify;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Identify$
extends AbstractFunction1<Object, Identify>
implements Serializable {
    public static final Identify$ MODULE$ = new Identify$();

    @Override
    public final String toString() {
        return "Identify";
    }

    @Override
    public Identify apply(Object messageId) {
        return new Identify(messageId);
    }

    public Option<Object> unapply(Identify x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0.messageId());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Identify$.class);
    }

    private Identify$() {
    }
}

