/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ExposedPublisher;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ExposedPublisher$
extends AbstractFunction1<ActorPublisher<Object>, ExposedPublisher>
implements Serializable {
    public static final ExposedPublisher$ MODULE$ = new ExposedPublisher$();

    @Override
    public final String toString() {
        return "ExposedPublisher";
    }

    @Override
    public ExposedPublisher apply(ActorPublisher<Object> publisher) {
        return new ExposedPublisher(publisher);
    }

    public Option<ActorPublisher<Object>> unapply(ExposedPublisher x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorPublisher<Object>>(x$0.publisher());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ExposedPublisher$.class);
    }

    private ExposedPublisher$() {
    }
}

