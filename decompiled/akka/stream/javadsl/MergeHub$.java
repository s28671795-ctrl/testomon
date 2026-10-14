/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.javadsl.MergeHub;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.MergeHub;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;

public final class MergeHub$ {
    public static final MergeHub$ MODULE$ = new MergeHub$();

    public <T> Source<T, Sink<T, NotUsed>> of(Class<T> clazz, int perProducerBufferSize) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.MergeHub$.MODULE$.source(perProducerBufferSize).mapMaterializedValue((Function1<akka.stream.scaladsl.Sink, Sink> & Serializable)x$1 -> x$1.asJava())).asJava();
    }

    public <T> Source<T, Pair<Sink<T, NotUsed>, MergeHub.DrainingControl>> withDraining(Class<T> clazz, int perProducerBufferSize) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.MergeHub$.MODULE$.sourceWithDraining(perProducerBufferSize).mapMaterializedValue((Function1<Tuple2, Pair> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                akka.stream.scaladsl.Sink sink2 = (akka.stream.scaladsl.Sink)tuple2._1();
                MergeHub.DrainingControl draining = (MergeHub.DrainingControl)tuple2._2();
                return new Pair(sink2.asJava(), new MergeHub.DrainingControlImpl(draining));
            }
            throw new MatchError(tuple2);
        })).asJava();
    }

    public <T> Source<T, Sink<T, NotUsed>> of(Class<T> clazz) {
        return this.of(clazz, 16);
    }

    public <T> Source<T, Pair<Sink<T, NotUsed>, MergeHub.DrainingControl>> withDraining(Class<T> clazz) {
        return this.withDraining(clazz, 16);
    }

    private MergeHub$() {
    }
}

