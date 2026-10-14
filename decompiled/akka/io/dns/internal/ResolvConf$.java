/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.internal.ResolvConf;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ResolvConf$
extends AbstractFunction2<List<String>, Object, ResolvConf>
implements Serializable {
    public static final ResolvConf$ MODULE$ = new ResolvConf$();

    @Override
    public final String toString() {
        return "ResolvConf";
    }

    @Override
    public ResolvConf apply(List<String> search, int ndots) {
        return new ResolvConf(search, ndots);
    }

    public Option<Tuple2<List<String>, Object>> unapply(ResolvConf x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<List<String>, Object>>(new Tuple2<List<String>, Integer>(x$0.search(), BoxesRunTime.boxToInteger(x$0.ndots())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ResolvConf$.class);
    }

    private ResolvConf$() {
    }
}

