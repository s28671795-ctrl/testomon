/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ActorSubscription;
import akka.stream.impl.RequestMore;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class RequestMore$
implements Serializable {
    public static final RequestMore$ MODULE$ = new RequestMore$();

    public final String toString() {
        return "RequestMore";
    }

    public <T> RequestMore<T> apply(ActorSubscription<T> subscription, long demand) {
        return new RequestMore<T>(subscription, demand);
    }

    public <T> Option<Tuple2<ActorSubscription<T>, Object>> unapply(RequestMore<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<ActorSubscription<T>, Object>>(new Tuple2<ActorSubscription<T>, Long>(x$0.subscription(), BoxesRunTime.boxToLong(x$0.demand())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RequestMore$.class);
    }

    private RequestMore$() {
    }
}

