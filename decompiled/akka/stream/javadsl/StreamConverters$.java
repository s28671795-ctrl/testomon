/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.IOResult;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.package$;
import akka.stream.scaladsl.package$SinkToCompletionStage$;
import akka.stream.scaladsl.package$SourceToCompletionStage$;
import akka.util.ByteString;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;
import scala.Function0;

public final class StreamConverters$ {
    public static final StreamConverters$ MODULE$ = new StreamConverters$();

    public Sink<ByteString, CompletionStage<IOResult>> fromOutputStream(Creator<OutputStream> f) {
        return this.fromOutputStream(f, false);
    }

    public Sink<ByteString, CompletionStage<IOResult>> fromOutputStream(Creator<OutputStream> f, boolean autoFlush) {
        return new Sink<ByteString, CompletionStage<IOResult>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.StreamConverters$.MODULE$.fromOutputStream((Function0<OutputStream> & Serializable)() -> (OutputStream)f.create(), autoFlush))));
    }

    public Sink<ByteString, InputStream> asInputStream() {
        return new Sink<ByteString, InputStream>(akka.stream.scaladsl.StreamConverters$.MODULE$.asInputStream(akka.stream.scaladsl.StreamConverters$.MODULE$.asInputStream$default$1()));
    }

    public Sink<ByteString, InputStream> asInputStream(Duration readTimeout) {
        return new Sink<ByteString, InputStream>(akka.stream.scaladsl.StreamConverters$.MODULE$.asInputStream(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(readTimeout))));
    }

    public Source<ByteString, CompletionStage<IOResult>> fromInputStream(Creator<InputStream> in, int chunkSize) {
        return new Source<ByteString, CompletionStage<IOResult>>(package$SourceToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SourceToCompletionStage(akka.stream.scaladsl.StreamConverters$.MODULE$.fromInputStream((Function0<InputStream> & Serializable)() -> (InputStream)in.create(), chunkSize))));
    }

    public Source<ByteString, CompletionStage<IOResult>> fromInputStream(Creator<InputStream> in) {
        return this.fromInputStream(in, 8192);
    }

    public Source<ByteString, OutputStream> asOutputStream(Duration writeTimeout) {
        return new Source<ByteString, OutputStream>(akka.stream.scaladsl.StreamConverters$.MODULE$.asOutputStream(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(writeTimeout))));
    }

    public Source<ByteString, OutputStream> asOutputStream() {
        return new Source<ByteString, OutputStream>(akka.stream.scaladsl.StreamConverters$.MODULE$.asOutputStream(akka.stream.scaladsl.StreamConverters$.MODULE$.asOutputStream$default$1()));
    }

    public <T> Sink<T, Stream<T>> asJavaStream() {
        return new Sink(akka.stream.scaladsl.StreamConverters$.MODULE$.asJavaStream());
    }

    public <O, S extends BaseStream<O, S>> Source<O, NotUsed> fromJavaStream(Creator<BaseStream<O, S>> stream) {
        return new Source(akka.stream.scaladsl.StreamConverters$.MODULE$.fromJavaStream((Function0<BaseStream> & Serializable)() -> (BaseStream)stream.create()));
    }

    public <T, R> Sink<T, CompletionStage<R>> javaCollector(Creator<Collector<T, ?, R>> collector) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.StreamConverters$.MODULE$.javaCollector((Function0<Collector> & Serializable)() -> (Collector)collector.create()))));
    }

    public <T, R> Sink<T, CompletionStage<R>> javaCollectorParallelUnordered(int parallelism, Creator<Collector<T, ?, R>> collector) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.StreamConverters$.MODULE$.javaCollectorParallelUnordered(parallelism, (Function0<Collector> & Serializable)() -> (Collector)collector.create()))));
    }

    private StreamConverters$() {
    }
}

