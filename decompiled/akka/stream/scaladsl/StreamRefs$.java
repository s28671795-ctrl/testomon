/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.impl.streamref.SinkRefStageImpl;
import akka.stream.impl.streamref.SourceRefStageImpl;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.util.OptionVal$;

public final class StreamRefs$ {
    public static final StreamRefs$ MODULE$ = new StreamRefs$();

    public <T> Sink<T, SourceRef<T>> sourceRef() {
        OptionVal$.MODULE$.None();
        return Sink$.MODULE$.fromGraph(new SinkRefStageImpl(null));
    }

    public <T> Source<T, SinkRef<T>> sinkRef() {
        OptionVal$.MODULE$.None();
        return Source$.MODULE$.fromGraph(new SourceRefStageImpl(null));
    }

    private StreamRefs$() {
    }
}

