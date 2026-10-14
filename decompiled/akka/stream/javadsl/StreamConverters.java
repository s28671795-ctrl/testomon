/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.IOResult;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.StreamConverters$;
import akka.util.ByteString;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rv!B\b\u0011\u0011\u00039b!B\r\u0011\u0011\u0003Q\u0002\"B\u0011\u0002\t\u0003\u0011\u0003\"B\u0012\u0002\t\u0003!\u0003\"B\u0012\u0002\t\u0003Y\u0005\"\u0002*\u0002\t\u0003\u0019\u0006\"\u0002*\u0002\t\u0003A\u0006\"B1\u0002\t\u0003\u0011\u0007\"B1\u0002\t\u0003q\u0007\"\u00029\u0002\t\u0003\t\b\"\u00029\u0002\t\u0003)\b\"\u0002<\u0002\t\u00039\bbBA\u000b\u0003\u0011\u0005\u0011q\u0003\u0005\b\u0003{\tA\u0011AA \u0011\u001d\t\t(\u0001C\u0001\u0003g\n\u0001c\u0015;sK\u0006l7i\u001c8wKJ$XM]:\u000b\u0005E\u0011\u0012a\u00026bm\u0006$7\u000f\u001c\u0006\u0003'Q\taa\u001d;sK\u0006l'\"A\u000b\u0002\t\u0005\\7.Y\u0002\u0001!\tA\u0012!D\u0001\u0011\u0005A\u0019FO]3b[\u000e{gN^3si\u0016\u00148o\u0005\u0002\u00027A\u0011AdH\u0007\u0002;)\ta$A\u0003tG\u0006d\u0017-\u0003\u0002!;\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\f\u0002!\u0019\u0014x.\\(viB,Ho\u0015;sK\u0006lGCA\u0013<!\u0011Ab\u0005\u000b\u0018\n\u0005\u001d\u0002\"\u0001B*j].\u0004\"!\u000b\u0017\u000e\u0003)R!a\u000b\u000b\u0002\tU$\u0018\u000e\\\u0005\u0003[)\u0012!BQ=uKN#(/\u001b8h!\rySgN\u0007\u0002a)\u0011\u0011GM\u0001\u000bG>t7-\u001e:sK:$(BA\u00164\u0015\u0005!\u0014\u0001\u00026bm\u0006L!A\u000e\u0019\u0003\u001f\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\u0004\"\u0001O\u001d\u000e\u0003II!A\u000f\n\u0003\u0011%{%+Z:vYRDQ\u0001P\u0002A\u0002u\n\u0011A\u001a\t\u0004}\r+U\"A \u000b\u0005\u0001\u000b\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\t#\u0012\u0001\u00026ba&L!\u0001R \u0003\u000f\r\u0013X-\u0019;peB\u0011a)S\u0007\u0002\u000f*\u0011\u0001jM\u0001\u0003S>L!AS$\u0003\u0019=+H\u000f];u'R\u0014X-Y7\u0015\u0007\u0015bU\nC\u0003=\t\u0001\u0007Q\bC\u0003O\t\u0001\u0007q*A\u0005bkR|g\t\\;tQB\u0011A\u0004U\u0005\u0003#v\u0011qAQ8pY\u0016\fg.A\u0007bg&s\u0007/\u001e;TiJ,\u0017-\u001c\u000b\u0002)B!\u0001D\n\u0015V!\t1e+\u0003\u0002X\u000f\nY\u0011J\u001c9viN#(/Z1n)\t!\u0016\fC\u0003[\r\u0001\u00071,A\u0006sK\u0006$G+[7f_V$\bC\u0001/`\u001b\u0005i&B\u000104\u0003\u0011!\u0018.\\3\n\u0005\u0001l&\u0001\u0003#ve\u0006$\u0018n\u001c8\u0002\u001f\u0019\u0014x.\\%oaV$8\u000b\u001e:fC6$2a\u00194j!\u0011AB\r\u000b\u0018\n\u0005\u0015\u0004\"AB*pkJ\u001cW\rC\u0003h\u000f\u0001\u0007\u0001.\u0001\u0002j]B\u0019ahQ+\t\u000b)<\u0001\u0019A6\u0002\u0013\rDWO\\6TSj,\u0007C\u0001\u000fm\u0013\tiWDA\u0002J]R$\"aY8\t\u000b\u001dD\u0001\u0019\u00015\u0002\u001d\u0005\u001cx*\u001e;qkR\u001cFO]3b[R\u0011!o\u001d\t\u00051\u0011DS\tC\u0003u\u0013\u0001\u00071,\u0001\u0007xe&$X\rV5nK>,H\u000fF\u0001s\u00031\t7OS1wCN#(/Z1n+\tAH\u0010F\u0001z!\u0015AbE_A\u0006!\tYH\u0010\u0004\u0001\u0005\u000bu\\!\u0019\u0001@\u0003\u0003Q\u000b2a`A\u0003!\ra\u0012\u0011A\u0005\u0004\u0003\u0007i\"a\u0002(pi\"Lgn\u001a\t\u00049\u0005\u001d\u0011bAA\u0005;\t\u0019\u0011I\\=\u0011\u000b\u00055\u0011\u0011\u0003>\u000e\u0005\u0005=!BA\n3\u0013\u0011\t\u0019\"a\u0004\u0003\rM#(/Z1n\u000391'o\\7KCZ\f7\u000b\u001e:fC6,b!!\u0007\u0002 \u0005]B\u0003BA\u000e\u0003W\u0001b\u0001\u00073\u0002\u001e\u0005\r\u0002cA>\u0002 \u00111\u0011\u0011\u0005\u0007C\u0002y\u0014\u0011a\u0014\t\u0005\u0003K\t9#D\u0001\u0015\u0013\r\tI\u0003\u0006\u0002\b\u001d>$Xk]3e\u0011\u0019\u0019B\u00021\u0001\u0002.A!ahQA\u0018!!\ti!!\r\u0002\u001e\u0005U\u0012\u0002BA\u001a\u0003\u001f\u0011!BQ1tKN#(/Z1n!\rY\u0018q\u0007\u0003\b\u0003sa!\u0019AA\u001e\u0005\u0005\u0019\u0016cA@\u00020\u0005i!.\u0019<b\u0007>dG.Z2u_J,b!!\u0011\u0002H\u00055C\u0003BA\"\u0003#\u0002b\u0001\u0007\u0014\u0002F\u0005%\u0003cA>\u0002H\u0011)Q0\u0004b\u0001}B!q&NA&!\rY\u0018Q\n\u0003\u0007\u0003\u001fj!\u0019\u0001@\u0003\u0003ICq!a\u0015\u000e\u0001\u0004\t)&A\u0005d_2dWm\u0019;peB!ahQA,a\u0011\tI&!\u0019\u0011\u0015\u00055\u00111LA#\u0003?\nY%\u0003\u0003\u0002^\u0005=!!C\"pY2,7\r^8s!\rY\u0018\u0011\r\u0003\f\u0003G\n)'!A\u0001\u0002\u000b\u0005aPA\u0002`IEBq!a\u0015\u000e\u0001\u0004\t9\u0007\u0005\u0003?\u0007\u0006%\u0004\u0007BA6\u0003C\u0002\"\"!\u0004\u0002\\\u00055\u0014qLA8!\rY\u0018q\t\t\u0004w\u00065\u0013A\b6bm\u0006\u001cu\u000e\u001c7fGR|'\u000fU1sC2dW\r\\+o_J$WM]3e+\u0019\t)(! \u0002\u0004R!\u0011qOAP)\u0011\tI(!\"\u0011\ra1\u00131PA@!\rY\u0018Q\u0010\u0003\u0006{:\u0011\rA \t\u0005_U\n\t\tE\u0002|\u0003\u0007#a!a\u0014\u000f\u0005\u0004q\bbBA*\u001d\u0001\u0007\u0011q\u0011\t\u0005}\r\u000bI\t\r\u0003\u0002\f\u0006=\u0005CCA\u0007\u00037\nY(!$\u0002\u0002B\u001910a$\u0005\u0017\u0005E\u00151SA\u0001\u0002\u0003\u0015\tA \u0002\u0004?\u0012\u0012\u0004bBA*\u001d\u0001\u0007\u0011Q\u0013\t\u0005}\r\u000b9\n\r\u0003\u0002\u001a\u0006=\u0005CCA\u0007\u00037\nY*!$\u0002\u001eB\u001910! \u0011\u0007m\f\u0019\t\u0003\u0004\u0002\":\u0001\ra[\u0001\fa\u0006\u0014\u0018\r\u001c7fY&\u001cX\u000e")
public final class StreamConverters {
    public static <T, R> Sink<T, CompletionStage<R>> javaCollectorParallelUnordered(int parallelism, Creator<Collector<T, ?, R>> collector) {
        return StreamConverters$.MODULE$.javaCollectorParallelUnordered(parallelism, collector);
    }

    public static <T, R> Sink<T, CompletionStage<R>> javaCollector(Creator<Collector<T, ?, R>> collector) {
        return StreamConverters$.MODULE$.javaCollector(collector);
    }

    public static <O, S extends BaseStream<O, S>> Source<O, NotUsed> fromJavaStream(Creator<BaseStream<O, S>> stream) {
        return StreamConverters$.MODULE$.fromJavaStream(stream);
    }

    public static <T> Sink<T, Stream<T>> asJavaStream() {
        return StreamConverters$.MODULE$.asJavaStream();
    }

    public static Source<ByteString, OutputStream> asOutputStream() {
        return StreamConverters$.MODULE$.asOutputStream();
    }

    public static Source<ByteString, OutputStream> asOutputStream(Duration writeTimeout) {
        return StreamConverters$.MODULE$.asOutputStream(writeTimeout);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromInputStream(Creator<InputStream> in) {
        return StreamConverters$.MODULE$.fromInputStream(in);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromInputStream(Creator<InputStream> in, int chunkSize) {
        return StreamConverters$.MODULE$.fromInputStream(in, chunkSize);
    }

    public static Sink<ByteString, InputStream> asInputStream(Duration readTimeout) {
        return StreamConverters$.MODULE$.asInputStream(readTimeout);
    }

    public static Sink<ByteString, InputStream> asInputStream() {
        return StreamConverters$.MODULE$.asInputStream();
    }

    public static Sink<ByteString, CompletionStage<IOResult>> fromOutputStream(Creator<OutputStream> f, boolean autoFlush) {
        return StreamConverters$.MODULE$.fromOutputStream(f, autoFlush);
    }

    public static Sink<ByteString, CompletionStage<IOResult>> fromOutputStream(Creator<OutputStream> f) {
        return StreamConverters$.MODULE$.fromOutputStream(f);
    }
}

