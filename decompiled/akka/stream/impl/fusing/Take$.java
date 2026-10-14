/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Take;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Take$
implements Serializable {
    public static final Take$ MODULE$ = new Take$();

    public final String toString() {
        return "Take";
    }

    public <T> Take<T> apply(long count) {
        return new Take(count);
    }

    public <T> Option<Object> unapply(Take<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToLong(x$0.count()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Take$.class);
    }

    private Take$() {
    }
}

