/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.SeveralRoutees;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.IndexedSeq;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class SeveralRoutees$
extends AbstractFunction1<IndexedSeq<Routee>, SeveralRoutees>
implements Serializable {
    public static final SeveralRoutees$ MODULE$ = new SeveralRoutees$();

    @Override
    public final String toString() {
        return "SeveralRoutees";
    }

    @Override
    public SeveralRoutees apply(IndexedSeq<Routee> routees) {
        return new SeveralRoutees(routees);
    }

    public Option<IndexedSeq<Routee>> unapply(SeveralRoutees x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<IndexedSeq<Routee>>(x$0.routees());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SeveralRoutees$.class);
    }

    private SeveralRoutees$() {
    }
}

