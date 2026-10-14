/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.OverflowStrategy;
import akka.stream.impl.fusing.Buffer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Buffer$
implements Serializable {
    public static final Buffer$ MODULE$ = new Buffer$();

    public final String toString() {
        return "Buffer";
    }

    public <T> Buffer<T> apply(int size, OverflowStrategy overflowStrategy) {
        return new Buffer(size, overflowStrategy);
    }

    public <T> Option<Tuple2<Object, OverflowStrategy>> unapply(Buffer<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, OverflowStrategy>>(new Tuple2<Integer, OverflowStrategy>(BoxesRunTime.boxToInteger(x$0.size()), x$0.overflowStrategy()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Buffer$.class);
    }

    private Buffer$() {
    }
}

