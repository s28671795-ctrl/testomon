/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.Routees;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.IndexedSeq;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Routees$
extends AbstractFunction1<IndexedSeq<Routee>, Routees>
implements Serializable {
    public static final Routees$ MODULE$ = new Routees$();

    @Override
    public final String toString() {
        return "Routees";
    }

    @Override
    public Routees apply(IndexedSeq<Routee> routees) {
        return new Routees(routees);
    }

    public Option<IndexedSeq<Routee>> unapply(Routees x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<IndexedSeq<Routee>>(x$0.routees());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Routees$.class);
    }

    private Routees$() {
    }
}

