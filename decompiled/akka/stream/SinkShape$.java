/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.SinkShape;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class SinkShape$
implements Serializable {
    public static final SinkShape$ MODULE$ = new SinkShape$();

    public <T> SinkShape<T> of(Inlet<T> inlet) {
        return new SinkShape<T>(inlet);
    }

    public <T> SinkShape<T> apply(Inlet<T> in) {
        return new SinkShape<T>(in);
    }

    public <T> Option<Inlet<T>> unapply(SinkShape<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Inlet<T>>(x$0.in());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SinkShape$.class);
    }

    private SinkShape$() {
    }
}

