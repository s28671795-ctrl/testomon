/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Outlet;
import akka.stream.SourceShape;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class SourceShape$
implements Serializable {
    public static final SourceShape$ MODULE$ = new SourceShape$();

    public <T> SourceShape<T> of(Outlet<T> outlet) {
        return new SourceShape<T>(outlet);
    }

    public <T> SourceShape<T> apply(Outlet<T> out) {
        return new SourceShape<T>(out);
    }

    public <T> Option<Outlet<T>> unapply(SourceShape<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Outlet<T>>(x$0.out());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SourceShape$.class);
    }

    private SourceShape$() {
    }
}

