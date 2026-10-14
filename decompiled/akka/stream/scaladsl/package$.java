/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import scala.concurrent.Future;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public <Out, T> Source<Out, Future<T>> SourceToCompletionStage(Source<Out, Future<T>> src) {
        return src;
    }

    public <In, T> Sink<In, Future<T>> SinkToCompletionStage(Sink<In, Future<T>> sink2) {
        return sink2;
    }

    private package$() {
    }
}

