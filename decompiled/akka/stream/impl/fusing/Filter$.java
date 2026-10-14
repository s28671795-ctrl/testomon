/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Filter;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Filter$
implements Serializable {
    public static final Filter$ MODULE$ = new Filter$();

    public final String toString() {
        return "Filter";
    }

    public <T> Filter<T> apply(Function1<T, Object> p) {
        return new Filter<T>(p);
    }

    public <T> Option<Function1<T, Object>> unapply(Filter<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Function1<T, Object>>(x$0.p());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Filter$.class);
    }

    private Filter$() {
    }
}

