/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Pair;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class Pair$
implements Serializable {
    public static final Pair$ MODULE$ = new Pair$();

    public <A, B> Pair<A, B> create(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    public <A, B> Pair<A, B> apply(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    public <A, B> Option<Tuple2<A, B>> unapply(Pair<A, B> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, B>>(new Tuple2<A, B>(x$0.first(), x$0.second()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Pair$.class);
    }

    private Pair$() {
    }
}

