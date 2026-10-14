/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.MapError;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class MapError$
implements Serializable {
    public static final MapError$ MODULE$ = new MapError$();

    public final String toString() {
        return "MapError";
    }

    public <T> MapError<T> apply(PartialFunction<Throwable, Throwable> f) {
        return new MapError(f);
    }

    public <T> Option<PartialFunction<Throwable, Throwable>> unapply(MapError<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<PartialFunction<Throwable, Throwable>>(x$0.f());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapError$.class);
    }

    private MapError$() {
    }
}

