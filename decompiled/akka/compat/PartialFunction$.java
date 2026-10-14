/*
 * Decompiled with CFR 0.152.
 */
package akka.compat;

import akka.annotation.InternalApi;
import scala.Function1;
import scala.PartialFunction;

@InternalApi
public final class PartialFunction$ {
    public static final PartialFunction$ MODULE$ = new PartialFunction$();

    public <A, B> PartialFunction<A, B> fromFunction(Function1<A, B> f) {
        return scala.PartialFunction$.MODULE$.fromFunction(f);
    }

    private PartialFunction$() {
    }
}

