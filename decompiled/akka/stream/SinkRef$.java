/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.NotUsed;
import akka.stream.SinkRef;
import akka.stream.scaladsl.Sink;

public final class SinkRef$ {
    public static final SinkRef$ MODULE$ = new SinkRef$();

    public <T> Sink<T, NotUsed> convertRefToSink(SinkRef<T> sinkRef) {
        return sinkRef.sink();
    }

    private SinkRef$() {
    }
}

