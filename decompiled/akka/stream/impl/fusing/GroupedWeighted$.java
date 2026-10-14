/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.GroupedWeighted;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class GroupedWeighted$
implements Serializable {
    public static final GroupedWeighted$ MODULE$ = new GroupedWeighted$();

    public final String toString() {
        return "GroupedWeighted";
    }

    public <T> GroupedWeighted<T> apply(long minWeight, Function1<T, Object> costFn) {
        return new GroupedWeighted<T>(minWeight, costFn);
    }

    public <T> Option<Tuple2<Object, Function1<T, Object>>> unapply(GroupedWeighted<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Function1<T, Object>>>(new Tuple2<Long, Function1<T, Object>>(BoxesRunTime.boxToLong(x$0.minWeight()), x$0.costFn()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(GroupedWeighted$.class);
    }

    private GroupedWeighted$() {
    }
}

