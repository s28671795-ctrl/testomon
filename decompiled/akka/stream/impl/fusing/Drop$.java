/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Drop;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Drop$
implements Serializable {
    public static final Drop$ MODULE$ = new Drop$();

    public final String toString() {
        return "Drop";
    }

    public <T> Drop<T> apply(long count) {
        return new Drop(count);
    }

    public <T> Option<Object> unapply(Drop<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToLong(x$0.count()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Drop$.class);
    }

    private Drop$() {
    }
}

