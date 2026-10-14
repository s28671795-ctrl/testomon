/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.runtime.ModuleSerializationProxy;

public final class $colon$colon$
implements Serializable {
    public static final $colon$colon$ MODULE$ = new $colon$colon$();

    public final String toString() {
        return "::";
    }

    public <A> $colon$colon<A> apply(A head, List<A> next) {
        return new $colon$colon<A>(head, next);
    }

    public <A> Option<Tuple2<A, List<A>>> unapply($colon$colon<A> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, List<A>>>(new Tuple2<A, List<A>>(x$0.head(), x$0.next()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy($colon$colon$.class);
    }

    private $colon$colon$() {
    }
}

