/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import java.io.Serializable;
import scala.Function1;

public final class BroadcastHub$ {
    public static final BroadcastHub$ MODULE$ = new BroadcastHub$();

    public <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, int bufferSize) {
        return akka.stream.scaladsl.BroadcastHub$.MODULE$.sink(bufferSize).mapMaterializedValue((Function1<akka.stream.scaladsl.Source, Source> & Serializable)x$2 -> x$2.asJava()).asJava();
    }

    public <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz) {
        return this.of(clazz, akka.stream.scaladsl.BroadcastHub$.MODULE$.defaultBufferSize());
    }

    private BroadcastHub$() {
    }
}

