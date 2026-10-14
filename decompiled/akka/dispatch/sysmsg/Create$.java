/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorInitializationException;
import akka.dispatch.sysmsg.Create;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Create$
extends AbstractFunction1<Option<ActorInitializationException>, Create>
implements Serializable {
    public static final Create$ MODULE$ = new Create$();

    @Override
    public final String toString() {
        return "Create";
    }

    @Override
    public Create apply(Option<ActorInitializationException> failure) {
        return new Create(failure);
    }

    public Option<Option<ActorInitializationException>> unapply(Create x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Option<ActorInitializationException>>(x$0.failure());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Create$.class);
    }

    private Create$() {
    }
}

