/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ConsistentHashingRouter;
import java.io.Serializable;
import scala.Function1;
import scala.PartialFunction;

public final class ConsistentHashingRouter$ {
    public static final ConsistentHashingRouter$ MODULE$ = new ConsistentHashingRouter$();

    public PartialFunction<Object, Object> hashMappingAdapter(ConsistentHashingRouter.ConsistentHashMapper mapper2) {
        return new Serializable(mapper2){
            private static final long serialVersionUID = 0L;
            private final ConsistentHashingRouter.ConsistentHashMapper mapper$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (this.mapper$1.hashKey(A1) != null) {
                    return (B1)this.mapper$1.hashKey(A1);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return this.mapper$1.hashKey(object) != null;
            }
            {
                this.mapper$1 = mapper$1;
            }
        };
    }

    private ConsistentHashingRouter$() {
    }
}

