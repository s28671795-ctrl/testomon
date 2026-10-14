/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public final class StreamRefs$ {
    public static final StreamRefs$ MODULE$ = new StreamRefs$();

    public <T> Sink<T, SourceRef<T>> sourceRef() {
        return akka.stream.scaladsl.StreamRefs$.MODULE$.sourceRef().asJava();
    }

    public <T> Source<T, SinkRef<T>> sinkRef() {
        return akka.stream.scaladsl.StreamRefs$.MODULE$.sinkRef().asJava();
    }

    private StreamRefs$() {
    }
}

