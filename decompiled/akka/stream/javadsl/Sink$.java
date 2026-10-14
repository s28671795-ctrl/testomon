/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.dispatch.ExecutionContexts$;
import akka.japi.Util$;
import akka.japi.function.Creator;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.japi.function.Procedure;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.SinkQueueWithCancel;
import akka.stream.javadsl.StreamConverters$;
import akka.stream.javadsl.package$;
import akka.stream.scaladsl.SinkQueueWithCancel$;
import akka.stream.scaladsl.SinkQueueWithCancel$QueueOps$;
import akka.stream.scaladsl.package$SinkToCompletionStage$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.concurrent.Future;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Try;

public final class Sink$ {
    public static final Sink$ MODULE$ = new Sink$();

    public <U, In> Sink<In, CompletionStage<U>> fold(U zero, Function2<U, In, U> f) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.fold(zero, (scala.Function2<Object, Object, Object> & Serializable)(U arg1, T arg2) -> f.apply(arg1, arg2)))));
    }

    public <U, In> Sink<In, CompletionStage<U>> foldAsync(U zero, Function2<U, In, CompletionStage<U>> f) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.foldAsync(zero, (scala.Function2<Object, Object, Future> & Serializable)(U x$1, T x$2) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(x$1, x$2)))))));
    }

    public <U, In> Sink<In, CompletionStage<U>> collect(Collector<In, ?, U> collector) {
        return StreamConverters$.MODULE$.javaCollector(() -> collector);
    }

    public <In> Sink<In, CompletionStage<In>> reduce(Function2<In, In, In> f) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.reduce((scala.Function2<Object, Object, Object> & Serializable)(T arg1, T arg2) -> f.apply(arg1, arg2)))));
    }

    public <In> Sink<In, NotUsed> fromSubscriber(Subscriber<In> subs) {
        return new Sink<In, NotUsed>(akka.stream.scaladsl.Sink$.MODULE$.fromSubscriber(subs));
    }

    public <T> Sink<T, NotUsed> cancelled() {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.cancelled());
    }

    public <T> Sink<T, CompletionStage<Done>> ignore() {
        return new Sink<Object, CompletionStage<Done>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.ignore())));
    }

    public <T> Sink<T, CompletionStage<Done>> never() {
        return new Sink<Object, CompletionStage<Done>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.never())));
    }

    public <T> Sink<T, Publisher<T>> asPublisher(AsPublisher fanout) {
        AsPublisher asPublisher = fanout;
        AsPublisher asPublisher2 = AsPublisher.WITH_FANOUT;
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.asPublisher(!(asPublisher != null ? !((Object)((Object)asPublisher)).equals((Object)asPublisher2) : asPublisher2 != null)));
    }

    public <T> Sink<T, CompletionStage<Done>> foreach(Procedure<T> f) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.foreach((Function1<Object, Object> & Serializable)(T param2) -> {
            f.apply(param2);
            return BoxedUnit.UNIT;
        }))));
    }

    public <T> Sink<T, CompletionStage<Done>> foreachAsync(int parallelism, Function<T, CompletionStage<Void>> f) {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.foreachAsync(parallelism, (Function1<Object, Future> & Serializable)(T x) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(x))).map((Function1<Void, Object> & Serializable)x$3 -> {
            Sink$.$anonfun$foreachAsync$2(x$3);
            return BoxedUnit.UNIT;
        }, ExecutionContexts$.MODULE$.parasitic())))));
    }

    public <In> Sink<In, NotUsed> onComplete(Procedure<Try<Done>> callback) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.onComplete((Function1<Try<Done>, BoxedUnit>)(Function1<Try, Object> & Serializable)x -> {
            callback.apply(x);
            return BoxedUnit.UNIT;
        }));
    }

    public <In> Sink<In, CompletionStage<In>> head() {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.head())));
    }

    public <In> Sink<In, CompletionStage<Optional<In>>> headOption() {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.headOption().mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$4 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$4.map((Function1<Option, Optional> & Serializable)x$5 -> OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(x$5)), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public <In> Sink<In, CompletionStage<In>> last() {
        return new Sink(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(akka.stream.scaladsl.package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.Sink$.MODULE$.last())));
    }

    public <In> Sink<In, CompletionStage<Optional<In>>> lastOption() {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.lastOption().mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$6 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$6.map((Function1<Option, Optional> & Serializable)x$7 -> OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(x$7)), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public <In> Sink<In, CompletionStage<List<In>>> takeLast(int n) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.takeLast(n).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)fut -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(fut.map((Function1<Seq, List> & Serializable)sq -> package$JavaConverters$.MODULE$.SeqHasAsJava(sq).asJava(), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public <In> Sink<In, CompletionStage<List<In>>> seq() {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.seq().mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)fut -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(fut.map((Function1<Seq, List> & Serializable)sq -> package$JavaConverters$.MODULE$.SeqHasAsJava(sq).asJava(), ExecutionContexts$.MODULE$.parasitic())))));
    }

    public <In> Sink<In, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.actorRef(ref, onCompleteMessage, (Function1<Throwable, Object>)(Function1<Throwable, Status.Failure> & Serializable)t2 -> new Status.Failure((Throwable)t2)));
    }

    public <In> Sink<In, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, ackMessage, onCompleteMessage, (Function1<Throwable, Object> & Serializable)(Throwable t2) -> onFailureMessage.apply((Throwable)t2)));
    }

    public <In> Sink<In, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, onCompleteMessage, (Function1<Throwable, Object> & Serializable)(Throwable t2) -> onFailureMessage.apply((Throwable)t2)));
    }

    @Deprecated
    public <In> Sink<In, NotUsed> actorRefWithAck(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function<Throwable, Object> onFailureMessage) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, ackMessage, onCompleteMessage, (Function1<Throwable, Object> & Serializable)(Throwable t2) -> onFailureMessage.apply((Throwable)t2)));
    }

    public <T, M> Sink<T, M> fromGraph(Graph<SinkShape<T>, M> g2) {
        Graph<SinkShape<T>, M> graph = g2;
        if (graph instanceof Sink) {
            Sink sink2 = (Sink)graph;
            return sink2;
        }
        return new Sink<T, M>(akka.stream.scaladsl.Sink$.MODULE$.fromGraph(graph));
    }

    public <T, M> Sink<T, CompletionStage<M>> fromMaterializer(BiFunction<Materializer, Attributes, Sink<T, M>> factory2) {
        return akka.stream.scaladsl.Sink$.MODULE$.fromMaterializer((scala.Function2<Materializer, Attributes, akka.stream.scaladsl.Sink> & Serializable)(Materializer mat, Attributes attr) -> ((Sink)factory2.apply((Materializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$8 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$8))).asJava();
    }

    public <T, M> Sink<T, CompletionStage<M>> setup(BiFunction<ActorMaterializer, Attributes, Sink<T, M>> factory2) {
        return akka.stream.scaladsl.Sink$.MODULE$.setup((scala.Function2<ActorMaterializer, Attributes, akka.stream.scaladsl.Sink> & Serializable)(ActorMaterializer mat, Attributes attr) -> ((Sink)factory2.apply((ActorMaterializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$9 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$9))).asJava();
    }

    public <T, U> Sink<T, NotUsed> combine(Sink<U, ?> output1, Sink<U, ?> output2, List<Sink<U, ?>> rest, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        Seq seq = rest != null ? ((IterableOnceOps)package$JavaConverters$.MODULE$.ListHasAsScala(rest).asScala().map((Function1<Sink, akka.stream.scaladsl.Sink> & Serializable)x$10 -> x$10.asScala())).toSeq() : (Seq)Nil$.MODULE$;
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.combine(output1.asScala(), output2.asScala(), seq, (Function1<Object, Graph> & Serializable)(Object num) -> Sink$.$anonfun$combine$2(fanOutStrategy, BoxesRunTime.unboxToInt(num))));
    }

    public <T, U, M1, M2, M> Sink<T, M> combineMat(Sink<U, M1> first, Sink<U, M2> second, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy, Function2<M1, M2, M> matF) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.combineMat(first.asScala(), second.asScala(), (Function1<Object, Graph> & Serializable)(Object size) -> Sink$.$anonfun$combineMat$1(fanOutStrategy, BoxesRunTime.unboxToInt(size)), package$.MODULE$.combinerToScala(matF)));
    }

    public <T, U, M> Sink<T, List<M>> combine(List<? extends Graph<SinkShape<U>, M>> sinks, Function<Integer, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        Seq seq = sinks != null ? (Seq)Util$.MODULE$.immutableSeq(sinks).collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Graph, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Sink) {
                    Sink sink2 = (Sink)A1;
                    return (B1)sink2.asScala();
                }
                return (B1)A1;
            }

            public final boolean isDefinedAt(Graph x1) {
                Graph graph = x1;
                if (graph instanceof Sink) {
                    return true;
                }
                return true;
            }
        }) : (Seq)Nil$.MODULE$;
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.combine(seq, (Function1<Object, Graph> & Serializable)(Object size) -> Sink$.$anonfun$combine$3(fanOutStrategy, BoxesRunTime.unboxToInt(size))).mapMaterializedValue((Function1<Seq, List> & Serializable)x$11 -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$11).asJava()));
    }

    public <T> Sink<T, SinkQueueWithCancel<T>> queue(int maxConcurrentPulls) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.queue(maxConcurrentPulls).mapMaterializedValue((Function1<akka.stream.scaladsl.SinkQueueWithCancel, SinkQueueWithCancel> & Serializable)x$12 -> SinkQueueWithCancel$QueueOps$.MODULE$.asJava$extension(SinkQueueWithCancel$.MODULE$.QueueOps(x$12))));
    }

    public <T> Sink<T, SinkQueueWithCancel<T>> queue() {
        return this.queue(1);
    }

    public <T, M> Sink<T, CompletionStage<M>> lazyInit(Function<T, CompletionStage<Sink<T, M>>> sinkFactory, Creator<M> fallback) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.lazyInit((Function1<Object, Future> & Serializable)(T t2) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)sinkFactory.apply(t2))).map((Function1<Sink, akka.stream.scaladsl.Sink> & Serializable)x$13 -> x$13.asScala(), ExecutionContexts$.MODULE$.parasitic()), (Function0<Object> & Serializable)() -> fallback.create()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$14 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$14))));
    }

    public <T, M> Sink<T, CompletionStage<Optional<M>>> lazyInitAsync(Creator<CompletionStage<Sink<T, M>>> sinkFactory) {
        akka.stream.scaladsl.Sink sSink = akka.stream.scaladsl.Sink$.MODULE$.lazyInitAsync((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)sinkFactory.create())).map((Function1<Sink, akka.stream.scaladsl.Sink> & Serializable)x$15 -> x$15.asScala(), ExecutionContexts$.MODULE$.parasitic())).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)fut -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(fut.map((Function1<Option, Optional> & Serializable)x$16 -> (Optional)x$16.fold((Function0<Optional> & Serializable)() -> Optional.empty(), (Function1<Object, Optional> & Serializable)(A m4) -> Optional.ofNullable(m4)), ExecutionContexts$.MODULE$.parasitic()))));
        return new Sink(sSink);
    }

    public <T, M> Sink<T, CompletionStage<M>> completionStageSink(CompletionStage<Sink<T, M>> future) {
        return this.lazyCompletionStageSink(() -> future);
    }

    public <T, M> Sink<T, CompletionStage<M>> lazySink(Creator<Sink<T, M>> create) {
        return this.lazyCompletionStageSink(() -> CompletableFuture.completedFuture(create.create()));
    }

    public <T, M> Sink<T, CompletionStage<M>> lazyCompletionStageSink(Creator<CompletionStage<Sink<T, M>>> create) {
        return new Sink(akka.stream.scaladsl.Sink$.MODULE$.lazyFutureSink((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)create.create())).map((Function1<Sink, akka.stream.scaladsl.Sink> & Serializable)x$17 -> x$17.asScala(), ExecutionContexts$.MODULE$.parasitic()))).mapMaterializedValue(x$18 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$18)));
    }

    public static final /* synthetic */ void $anonfun$foreachAsync$2(Void x$3) {
    }

    public static final /* synthetic */ Graph $anonfun$combine$2(Function fanOutStrategy$1, int num) {
        return (Graph)fanOutStrategy$1.apply(Predef$.MODULE$.int2Integer(num));
    }

    public static final /* synthetic */ Graph $anonfun$combineMat$1(Function fanOutStrategy$2, int size) {
        return (Graph)fanOutStrategy$2.apply(Predef$.MODULE$.int2Integer(size));
    }

    public static final /* synthetic */ Graph $anonfun$combine$3(Function fanOutStrategy$3, int size) {
        return (Graph)fanOutStrategy$3.apply(Predef$.MODULE$.int2Integer(size));
    }

    private Sink$() {
    }
}

