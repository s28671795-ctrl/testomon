/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.scaladsl.BroadcastHub;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;

public final class BroadcastHub$ {
    public static final BroadcastHub$ MODULE$ = new BroadcastHub$();
    @InternalApi
    private static final int defaultBufferSize = 256;

    public int defaultBufferSize() {
        return defaultBufferSize;
    }

    public <T> Sink<T, Source<T, NotUsed>> sink(int bufferSize) {
        return Sink$.MODULE$.fromGraph(new BroadcastHub(bufferSize));
    }

    public <T> Sink<T, Source<T, NotUsed>> sink() {
        return this.sink(this.defaultBufferSize());
    }

    private BroadcastHub$() {
    }
}

