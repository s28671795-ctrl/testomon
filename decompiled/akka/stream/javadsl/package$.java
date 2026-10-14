/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.japi.function.Function2;
import akka.stream.javadsl.Keep$;
import java.io.Serializable;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public <M1, M2, M> scala.Function2<M1, M2, M> combinerToScala(Function2<M1, M2, M> f) {
        Function2 function2 = f;
        if (function2 == Keep$.MODULE$.left()) {
            return akka.stream.scaladsl.Keep$.MODULE$.left();
        }
        if (function2 == Keep$.MODULE$.right()) {
            return akka.stream.scaladsl.Keep$.MODULE$.right();
        }
        if (function2 instanceof scala.Function2) {
            Function2 function22 = function2;
            return (scala.Function2)((Object)function22);
        }
        return (scala.Function2<Object, Object, Object> & Serializable)(arg1, arg2) -> function2.apply(arg1, arg2);
    }

    private package$() {
    }
}

