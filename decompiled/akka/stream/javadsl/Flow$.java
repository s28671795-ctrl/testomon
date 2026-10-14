/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.dispatch.ExecutionContexts$;
import akka.japi.Pair;
import akka.japi.function.Creator;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.package$;
import akka.stream.scaladsl.FlowOpsMat;
import akka.stream.scaladsl.Keep$;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import org.reactivestreams.Processor;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.concurrent.Future;

public final class Flow$ {
    public static final Flow$ MODULE$ = new Flow$();

    public <T> Flow<T, T, NotUsed> create() {
        return this.fromGraph(akka.stream.scaladsl.Flow$.MODULE$.apply());
    }

    public <I, O> Flow<I, O, NotUsed> fromProcessor(Creator<Processor<I, O>> processorFactory) {
        return new Flow(akka.stream.scaladsl.Flow$.MODULE$.fromProcessor((Function0<Processor> & Serializable)() -> (Processor)processorFactory.create()));
    }

    public <I, O, Mat> Flow<I, O, Mat> fromProcessorMat(Creator<Pair<Processor<I, O>, Mat>> processorFactory) {
        return new Flow(akka.stream.scaladsl.Flow$.MODULE$.fromProcessorMat((Function0<Tuple2> & Serializable)() -> {
            Pair javaPair = (Pair)processorFactory.create();
            return new Tuple2(javaPair.first(), javaPair.second());
        }));
    }

    public <I, O> Flow<I, O, NotUsed> fromFunction(Function<I, O> f) {
        return this.create().map(f);
    }

    public <T> Flow<T, T, NotUsed> of(Class<T> clazz) {
        return this.create();
    }

    public <I, O, M> Flow<I, O, M> fromGraph(Graph<FlowShape<I, O>, M> g2) {
        Graph<FlowShape<I, O>, M> graph = g2;
        if (graph instanceof Flow) {
            Flow flow = (Flow)graph;
            return flow;
        }
        return new Flow<I, O, M>(akka.stream.scaladsl.Flow$.MODULE$.fromGraph(graph));
    }

    public <I, O, M> Flow<I, O, CompletionStage<M>> fromMaterializer(BiFunction<Materializer, Attributes, Flow<I, O, M>> factory2) {
        return ((akka.stream.scaladsl.Flow)akka.stream.scaladsl.Flow$.MODULE$.fromMaterializer((scala.Function2<Materializer, Attributes, akka.stream.scaladsl.Flow> & Serializable)(Materializer mat, Attributes attr) -> ((Flow)factory2.apply((Materializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$1 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$1)))).asJava();
    }

    public <I, O, M> Flow<I, O, CompletionStage<M>> setup(BiFunction<ActorMaterializer, Attributes, Flow<I, O, M>> factory2) {
        return ((akka.stream.scaladsl.Flow)akka.stream.scaladsl.Flow$.MODULE$.setup((scala.Function2<ActorMaterializer, Attributes, akka.stream.scaladsl.Flow> & Serializable)(ActorMaterializer mat, Attributes attr) -> ((Flow)factory2.apply((ActorMaterializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$2 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$2)))).asJava();
    }

    public <I, O> Flow<I, O, NotUsed> fromSinkAndSource(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return new Flow<I, O, NotUsed>(akka.stream.scaladsl.Flow$.MODULE$.fromSinkAndSourceMat(sink2, source2, Keep$.MODULE$.none()));
    }

    public <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return new Flow<I, O, M>(akka.stream.scaladsl.Flow$.MODULE$.fromSinkAndSourceMat(sink2, source2, package$.MODULE$.combinerToScala(combine)));
    }

    public <I, O> Flow<I, O, NotUsed> fromSinkAndSourceCoupled(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return new Flow<I, O, NotUsed>(akka.stream.scaladsl.Flow$.MODULE$.fromSinkAndSourceCoupled(sink2, source2));
    }

    public <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceCoupledMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return new Flow<I, O, M>(akka.stream.scaladsl.Flow$.MODULE$.fromSinkAndSourceCoupledMat(sink2, source2, package$.MODULE$.combinerToScala(combine)));
    }

    public <I, O, M> Flow<I, O, M> lazyInit(Function<I, CompletionStage<Flow<I, O, M>>> flowFactory, Creator<M> fallback) {
        akka.stream.scaladsl.Flow sflow = akka.stream.scaladsl.Flow$.MODULE$.lazyInit(((Function1<Object, CompletionStage> & Serializable)(Function1<Object, CompletionStage> & Serializable)x$3 -> (CompletionStage)flowFactory.apply(x$3)).andThen((Function1<CompletionStage, Future> & Serializable)x$4 -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(x$4)).map((Function1<Flow, akka.stream.scaladsl.Flow> & Serializable)x$5 -> x$5.asScala(), ExecutionContexts$.MODULE$.parasitic())), (Function0<Object> & Serializable)() -> fallback.create());
        return new Flow(sflow);
    }

    public <I, O, M> Flow<I, O, CompletionStage<Optional<M>>> lazyInitAsync(Creator<CompletionStage<Flow<I, O, M>>> flowFactory) {
        FlowOpsMat sflow = akka.stream.scaladsl.Flow$.MODULE$.lazyInitAsync((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)flowFactory.create())).map((Function1<Flow, akka.stream.scaladsl.Flow> & Serializable)x$6 -> x$6.asScala(), ExecutionContexts$.MODULE$.parasitic())).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)fut -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(fut.map((Function1<Option, Optional> & Serializable)x$7 -> (Optional)x$7.fold((Function0<Optional> & Serializable)() -> Optional.empty(), (Function1<Object, Optional> & Serializable)m4 -> Optional.ofNullable(m4)), ExecutionContexts$.MODULE$.parasitic()))));
        return new Flow(sflow);
    }

    public <I, O, M> Flow<I, O, CompletionStage<M>> completionStageFlow(CompletionStage<Flow<I, O, M>> flow) {
        FlowOpsMat sflow = akka.stream.scaladsl.Flow$.MODULE$.futureFlow(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(flow)).map((Function1<Flow, akka.stream.scaladsl.Flow> & Serializable)x$8 -> x$8.asScala(), ExecutionContexts$.MODULE$.parasitic())).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$9 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$9)));
        return new Flow(sflow);
    }

    public <I, O, M> Flow<I, O, CompletionStage<M>> lazyFlow(Creator<Flow<I, O, M>> create) {
        FlowOpsMat sflow = akka.stream.scaladsl.Flow$.MODULE$.lazyFlow((Function0<akka.stream.scaladsl.Flow> & Serializable)() -> ((Flow)create.create()).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$10 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$10)));
        return new Flow(sflow);
    }

    public <I, O, M> Flow<I, O, CompletionStage<M>> lazyCompletionStageFlow(Creator<CompletionStage<Flow<I, O, M>>> create) {
        return ((akka.stream.scaladsl.Flow)akka.stream.scaladsl.Flow$.MODULE$.lazyFutureFlow((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)create.create())).map((Function1<Flow, akka.stream.scaladsl.Flow> & Serializable)x$11 -> x$11.asScala(), ExecutionContexts$.MODULE$.parasitic())).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$12 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$12)))).asJava();
    }

    public <In, SuperOut, Out extends SuperOut, M> Flow<In, SuperOut, M> upcast(Flow<In, Out, M> flow) {
        return flow;
    }

    public <Out, In extends Optional<Out>> Flow<In, Out, NotUsed> flattenOptional() {
        return new Flow((akka.stream.scaladsl.Flow)akka.stream.scaladsl.Flow$.MODULE$.apply().collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends In, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1;
                A1 A12 = x1;
                if (A12 != null && ((Optional)(A1 = A12)).isPresent()) {
                    return (B1)((Optional)A1).get();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(In x1) {
                In In2;
                In In3 = x1;
                return In3 != null && ((Optional)(In2 = In3)).isPresent();
            }
        }));
    }

    private Flow$() {
    }
}

