/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Intersperse;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

public final class Intersperse$
implements Serializable {
    public static final Intersperse$ MODULE$ = new Intersperse$();

    public final String toString() {
        return "Intersperse";
    }

    public <T> Intersperse<T> apply(Option<T> start2, T inject, Option<T> end) {
        return new Intersperse<T>(start2, inject, end);
    }

    public <T> Option<Tuple3<Option<T>, T, Option<T>>> unapply(Intersperse<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Option<T>, T, Option<T>>>(new Tuple3<Option<T>, T, Option<T>>(x$0.start(), x$0.inject(), x$0.end()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Intersperse$.class);
    }

    private Intersperse$() {
    }
}

