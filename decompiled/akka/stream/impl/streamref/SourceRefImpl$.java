/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorRef;
import akka.stream.impl.streamref.SourceRefImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class SourceRefImpl$
implements Serializable {
    public static final SourceRefImpl$ MODULE$ = new SourceRefImpl$();

    public final String toString() {
        return "SourceRefImpl";
    }

    public <T> SourceRefImpl<T> apply(ActorRef initialPartnerRef) {
        return new SourceRefImpl(initialPartnerRef);
    }

    public <T> Option<ActorRef> unapply(SourceRefImpl<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.initialPartnerRef());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SourceRefImpl$.class);
    }

    private SourceRefImpl$() {
    }
}

