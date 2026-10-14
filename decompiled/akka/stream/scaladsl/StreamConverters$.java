/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes$;
import akka.stream.Graph;
import akka.stream.IOResult;
import akka.stream.SinkShape;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanOutShape;
import akka.stream.impl.CollectorState;
import akka.stream.impl.FirstCollectorState;
import akka.stream.impl.FirstReducerState;
import akka.stream.impl.JavaStreamSource;
import akka.stream.impl.QueueSink;
import akka.stream.impl.ReducerState;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.io.InputStreamSinkStage;
import akka.stream.impl.io.InputStreamSource;
import akka.stream.impl.io.OutputStreamGraphStage;
import akka.stream.impl.io.OutputStreamSourceStage;
import akka.stream.scaladsl.Balance$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Merge$;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.SinkQueueWithCancel;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.util.ByteString;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Predef$;
import scala.concurrent.Await$;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.runtime.RichInt$;

public final class StreamConverters$ {
    public static final StreamConverters$ MODULE$ = new StreamConverters$();

    public Source<ByteString, Future<IOResult>> fromInputStream(Function0<InputStream> in, int chunkSize) {
        return Source$.MODULE$.fromGraph(new InputStreamSource(in, chunkSize));
    }

    public int fromInputStream$default$2() {
        return 8192;
    }

    public Source<ByteString, OutputStream> asOutputStream(FiniteDuration writeTimeout) {
        return Source$.MODULE$.fromGraph(new OutputStreamSourceStage(writeTimeout));
    }

    public FiniteDuration asOutputStream$default$1() {
        return new package.DurationInt(package$.MODULE$.DurationInt(5)).seconds();
    }

    public Sink<ByteString, Future<IOResult>> fromOutputStream(Function0<OutputStream> out, boolean autoFlush) {
        return Sink$.MODULE$.fromGraph(new OutputStreamGraphStage(out, autoFlush));
    }

    public boolean fromOutputStream$default$2() {
        return false;
    }

    public Sink<ByteString, InputStream> asInputStream(FiniteDuration readTimeout) {
        return Sink$.MODULE$.fromGraph(new InputStreamSinkStage(readTimeout));
    }

    public FiniteDuration asInputStream$default$1() {
        return new package.DurationInt(package$.MODULE$.DurationInt(5)).seconds();
    }

    public <T, R> Sink<T, Future<R>> javaCollector(Function0<Collector<T, ?, R>> collectorFactory) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().fold(new FirstCollectorState(collectorFactory), (Function2<CollectorState, Object, CollectorState> & Serializable)(state, elem) -> state.update(elem)).map((Function1<CollectorState, Object> & Serializable)state -> state.finish())).toMat((Graph)Sink$.MODULE$.head(), Keep$.MODULE$.right())).withAttributes(Stages$DefaultAttributes$.MODULE$.javaCollector());
    }

    public <T, R> Sink<T, Future<R>> javaCollectorParallelUnordered(int parallelism, Function0<Collector<T, ?, R>> collectorFactory) {
        if (parallelism == 1) {
            return this.javaCollector(collectorFactory);
        }
        return Sink$.MODULE$.fromGraph(GraphDSL$.MODULE$.createGraph(Sink$.MODULE$.head(), (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<SinkShape, SinkShape> & Serializable)sink2 -> {
            Function0 factory2 = collectorFactory;
            UniformFanOutShape balance = (UniformFanOutShape)b.add(Balance$.MODULE$.apply(parallelism, Balance$.MODULE$.apply$default$2()));
            UniformFanInShape merge = (UniformFanInShape)b.add(Merge$.MODULE$.apply(parallelism, Merge$.MODULE$.apply$default$2()));
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), parallelism).foreach$mVc$sp(i -> {
                FlowOps worker = ((Flow)Flow$.MODULE$.apply().fold(new FirstCollectorState(factory2), (Function2<CollectorState, Object, CollectorState> & Serializable)(state, elem) -> state.update(elem))).async();
                GraphDSL$Implicits$.MODULE$.port2flow(balance.out(i), (GraphDSL.Builder<?>)b).$tilde$greater(worker, (GraphDSL.Builder<?>)b).$tilde$greater(merge.in(i), (GraphDSL.Builder<?>)b);
            });
            ((GraphDSL.Implicits.CombinerBase)((Object)GraphDSL$Implicits$.MODULE$.port2flow(merge.out(), (GraphDSL.Builder<?>)b).fold(new FirstReducerState(factory2), (Function2<ReducerState, CollectorState, ReducerState> & Serializable)(state, elem) -> state.update(elem.accumulated())).map((Function1<ReducerState, Object> & Serializable)state -> state.finish()))).$tilde$greater(sink2.in(), (GraphDSL.Builder<?>)b);
            return new SinkShape(balance.in());
        })).withAttributes(Stages$DefaultAttributes$.MODULE$.javaCollectorParallelUnordered());
    }

    public <T> Sink<T, Stream<T>> asJavaStream() {
        return Sink$.MODULE$.fromGraph(new QueueSink(1).withAttributes(Attributes$.MODULE$.none())).mapMaterializedValue((Function1<SinkQueueWithCancel, Stream> & Serializable)queue -> (Stream)StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>((SinkQueueWithCancel)queue){
            private Future<Option<T>> nextElementFuture;
            private Option<T> nextElement;
            private final SinkQueueWithCancel queue$1;

            public void remove() {
                Iterator.super.remove();
            }

            public void forEachRemaining(Consumer<? super T> x$1) {
                Iterator.super.forEachRemaining(x$1);
            }

            public Future<Option<T>> nextElementFuture() {
                return this.nextElementFuture;
            }

            public void nextElementFuture_$eq(Future<Option<T>> x$1) {
                this.nextElementFuture = x$1;
            }

            public Option<T> nextElement() {
                return this.nextElement;
            }

            public void nextElement_$eq(Option<T> x$1) {
                this.nextElement = x$1;
            }

            public boolean hasNext() {
                this.nextElement_$eq(Await$.MODULE$.result(this.nextElementFuture(), Duration$.MODULE$.Inf()));
                return this.nextElement().isDefined();
            }

            public T next() {
                T next = this.nextElement().get();
                this.nextElementFuture_$eq(this.queue$1.pull());
                return next;
            }
            {
                this.queue$1 = queue$1;
                this.nextElementFuture = queue$1.pull();
            }
        }, 0), false).onClose(new Runnable((SinkQueueWithCancel)queue){
            private final SinkQueueWithCancel queue$1;

            public void run() {
                this.queue$1.cancel();
            }
            {
                this.queue$1 = queue$1;
            }
        })).withAttributes(Stages$DefaultAttributes$.MODULE$.asJavaStream());
    }

    public <T, S extends BaseStream<T, S>> Source<T, NotUsed> fromJavaStream(Function0<BaseStream<T, S>> stream) {
        return Source$.MODULE$.fromGraph(new JavaStreamSource<T, S>(stream)).withAttributes(Stages$DefaultAttributes$.MODULE$.fromJavaStream());
    }

    private StreamConverters$() {
    }
}

