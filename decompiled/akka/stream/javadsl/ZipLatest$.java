/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.japi.function.Function2;
import akka.stream.FanInShape2;
import akka.stream.Graph;
import akka.stream.javadsl.ZipLatestWith$;

public final class ZipLatest$ {
    public static final ZipLatest$ MODULE$ = new ZipLatest$();
    private static final Function2<Object, Object, Pair<Object, Object>> _toPair = new Function2<Object, Object, Pair<Object, Object>>(){

        public Pair<Object, Object> apply(Object a, Object b) {
            return new Pair<Object, Object>(a, b);
        }
    };

    public <A, B> Graph<FanInShape2<A, B, Pair<A, B>>, NotUsed> create() {
        return ZipLatestWith$.MODULE$.create(_toPair);
    }

    private ZipLatest$() {
    }
}

