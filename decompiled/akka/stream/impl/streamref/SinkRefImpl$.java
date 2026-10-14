/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorRef;
import akka.stream.impl.streamref.SinkRefImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class SinkRefImpl$
implements Serializable {
    public static final SinkRefImpl$ MODULE$ = new SinkRefImpl$();

    public final String toString() {
        return "SinkRefImpl";
    }

    public <In> SinkRefImpl<In> apply(ActorRef initialPartnerRef) {
        return new SinkRefImpl(initialPartnerRef);
    }

    public <In> Option<ActorRef> unapply(SinkRefImpl<In> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.initialPartnerRef());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SinkRefImpl$.class);
    }

    private SinkRefImpl$() {
    }
}

