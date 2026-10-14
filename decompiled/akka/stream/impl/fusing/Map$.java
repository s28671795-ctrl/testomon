/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Map;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Map$
implements Serializable {
    public static final Map$ MODULE$ = new Map$();

    public final String toString() {
        return "Map";
    }

    public <In, Out> Map<In, Out> apply(Function1<In, Out> f) {
        return new Map<In, Out>(f);
    }

    public <In, Out> Option<Function1<In, Out>> unapply(Map<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Function1<In, Out>>(x$0.f());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Map$.class);
    }

    private Map$() {
    }
}

