/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Concat;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.Pop$;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class Concat$
implements Serializable {
    public static final Concat$ MODULE$ = new Concat$();

    public Traversal normalizeConcat(Traversal first, Traversal second) {
        if (second == EmptyTraversal$.MODULE$) {
            return first;
        }
        if (first == PushNotUsed$.MODULE$) {
            Traversal traversal = second;
            if (Pop$.MODULE$.equals(traversal)) {
                return EmptyTraversal$.MODULE$;
            }
            if (traversal instanceof Concat) {
                Concat concat = (Concat)traversal;
                Traversal traversal2 = concat.first();
                Traversal rest = concat.next();
                if (Pop$.MODULE$.equals(traversal2)) {
                    return rest;
                }
            }
            return new Concat(PushNotUsed$.MODULE$, second);
        }
        Traversal traversal = first;
        if (traversal instanceof Concat) {
            Concat concat = (Concat)traversal;
            Traversal firstfirst = concat.first();
            Traversal firstsecond = concat.next();
            return new Concat(firstfirst, new Concat(firstsecond, second));
        }
        return new Concat(first, second);
    }

    public Concat apply(Traversal first, Traversal next) {
        return new Concat(first, next);
    }

    public Option<Tuple2<Traversal, Traversal>> unapply(Concat x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Traversal, Traversal>>(new Tuple2<Traversal, Traversal>(x$0.first(), x$0.next()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Concat$.class);
    }

    private Concat$() {
    }
}

