/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.FanOutShape2;
import akka.stream.Graph;
import akka.stream.javadsl.UnzipWith$;
import akka.util.ConstantFun$;

public final class Unzip$ {
    public static final Unzip$ MODULE$ = new Unzip$();

    public <A, B> Graph<FanOutShape2<Pair<A, B>, A, B>, NotUsed> create() {
        return UnzipWith$.MODULE$.create(ConstantFun$.MODULE$.javaIdentityFunction());
    }

    public <A, B> Graph<FanOutShape2<Pair<A, B>, A, B>, NotUsed> create(Class<A> left, Class<B> right) {
        return this.create();
    }

    private Unzip$() {
    }
}

