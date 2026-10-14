/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerHelper$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.NeverMaterializedException;
import akka.stream.Shape;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.impl.CoupledTerminationBidi;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.ProcessorModule;
import akka.stream.impl.ProcessorModule$;
import akka.stream.impl.SetupFlowStage;
import akka.stream.impl.fusing.FutureFlow;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.FlowOpsMat;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import org.reactivestreams.Processor;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.SeqFactory;
import scala.collection.SeqFactory$UnapplySeqWrapper$;
import scala.collection.SeqOps;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.package$;

public final class Flow$ {
    public static final Flow$ MODULE$ = new Flow$();
    private static final LinearTraversalBuilder identityTraversalBuilder = LinearTraversalBuilder$.MODULE$.fromBuilder(GraphStages$.MODULE$.identity().traversalBuilder(), GraphStages$.MODULE$.identity().shape(), Keep$.MODULE$.right());
    private static final Flow<Object, Object, NotUsed> identity = new Flow(MODULE$.identityTraversalBuilder(), GraphStages$.MODULE$.identity().shape());

    public LinearTraversalBuilder identityTraversalBuilder() {
        return identityTraversalBuilder;
    }

    public <I, O> Flow<I, O, NotUsed> fromProcessor(Function0<Processor<I, O>> processorFactory) {
        return this.fromProcessorMat((Function0<Tuple2> & Serializable)() -> new Tuple2(processorFactory.apply(), NotUsed$.MODULE$));
    }

    public <I, O, M> Flow<I, O, M> fromProcessorMat(Function0<Tuple2<Processor<I, O>, M>> processorFactory) {
        return this.fromGraph(new ProcessorModule(processorFactory, ProcessorModule$.MODULE$.apply$default$2()));
    }

    public <T> Flow<T, T, NotUsed> apply() {
        return identity;
    }

    public <A, B> Flow<A, B, NotUsed> fromFunction(Function1<A, B> f) {
        return (Flow)this.apply().map(f);
    }

    public <I, O, M> Flow<I, O, M> fromGraph(Graph<FlowShape<I, O>, M> g2) {
        Graph<FlowShape<I, O>, M> graph = g2;
        if (graph instanceof Flow) {
            Flow flow = (Flow)graph;
            return flow;
        }
        if (graph instanceof akka.stream.javadsl.Flow) {
            akka.stream.javadsl.Flow flow = (akka.stream.javadsl.Flow)graph;
            return flow.asScala();
        }
        if (graph instanceof GraphStageWithMaterializedValue) {
            GraphStageWithMaterializedValue graphStageWithMaterializedValue = (GraphStageWithMaterializedValue)graph;
            Attributes attrs = graphStageWithMaterializedValue.traversalBuilder().attributes();
            Graph noAttrStage = graphStageWithMaterializedValue.withAttributes(Attributes$.MODULE$.none());
            return new Flow(LinearTraversalBuilder$.MODULE$.fromBuilder(noAttrStage.traversalBuilder(), (Shape)noAttrStage.shape(), Keep$.MODULE$.right()), (FlowShape)noAttrStage.shape()).withAttributes(attrs);
        }
        return new Flow(LinearTraversalBuilder$.MODULE$.fromBuilder(g2.traversalBuilder(), g2.shape(), Keep$.MODULE$.right()), g2.shape());
    }

    public <T, U, M> Flow<T, U, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Flow<T, U, M>> factory2) {
        return this.fromGraph(new SetupFlowStage<T, U, M>(factory2));
    }

    public <T, U, M> Flow<T, U, Future<M>> setup(Function2<ActorMaterializer, Attributes, Flow<T, U, M>> factory2) {
        return this.fromGraph(new SetupFlowStage((Function2<Materializer, Attributes, Flow> & Serializable)(materializer, attributes) -> (Flow)factory2.apply(ActorMaterializerHelper$.MODULE$.downcast((Materializer)materializer), (Attributes)attributes)));
    }

    public <I, O> Flow<I, O, NotUsed> fromSinkAndSource(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return this.fromSinkAndSourceMat(sink2, source2, Keep$.MODULE$.none());
    }

    public <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return this.fromGraph(GraphDSL$.MODULE$.createGraph(sink2, source2, combine, (Function1<GraphDSL.Builder, Function2> & Serializable)x$2 -> (Function2<SinkShape, SourceShape, FlowShape> & Serializable)(in, out) -> new FlowShape(in.in(), out.out())));
    }

    public <I, O> Flow<I, O, NotUsed> fromSinkAndSourceCoupled(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return this.fromSinkAndSourceCoupledMat(sink2, source2, Keep$.MODULE$.none());
    }

    public <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceCoupledMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return this.fromGraph(GraphDSL$.MODULE$.createGraph(sink2, source2, combine, (Function1<GraphDSL.Builder, Function2> & Serializable)b -> (Function2<SinkShape, SourceShape, FlowShape> & Serializable)(i, o) -> {
            BidiShape bidi = (BidiShape)b.add(new CoupledTerminationBidi());
            GraphDSL$Implicits$.MODULE$.port2flow(bidi.out1(), (GraphDSL.Builder<?>)b).$tilde$greater(i, (GraphDSL.Builder<?>)b);
            new GraphDSL.Implicits.SourceShapeArrow(GraphDSL$Implicits$.MODULE$.SourceShapeArrow(o)).$tilde$greater(bidi.in2(), (GraphDSL.Builder<?>)b);
            return new FlowShape(bidi.in1(), bidi.out2());
        }));
    }

    public <I, O, M> Flow<I, O, M> lazyInit(Function1<I, Future<Flow<I, O, M>>> flowFactory, Function0<M> fallback) {
        return ((Flow)this.apply().flatMapPrefix(1, (Function1<Seq, Flow> & Serializable)x0$1 -> {
            SeqOps seqOps;
            Seq seq = x0$1;
            if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = package$.MODULE$.Seq().unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0) {
                Object a = SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0);
                return MODULE$.futureFlow((Future)flowFactory.apply(a)).mapMaterializedValue((Function1<Future, NotUsed$> & Serializable)x$3 -> NotUsed$.MODULE$);
            }
            if (((Object)Nil$.MODULE$).equals(seq)) {
                return MODULE$.apply();
            }
            throw new RuntimeException();
        })).mapMaterializedValue((Function1<NotUsed, Object> & Serializable)x$4 -> fallback.apply());
    }

    public <I, O, M> Flow<I, O, Future<Option<M>>> lazyInitAsync(Function0<Future<Flow<I, O, M>>> flowFactory) {
        ExecutionContext ec = ExecutionContexts$.MODULE$.parasitic();
        return this.lazyFutureFlow(flowFactory).mapMaterializedValue((Function1<Future, Future> & Serializable)x$5 -> x$5.map((Function1<Object, Some> & Serializable)value -> new Some<Object>(value), ec).recover(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof NeverMaterializedException) {
                    return (B1)None$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof NeverMaterializedException;
            }
        }, ec));
    }

    public <I, O, M> Flow<I, O, Future<M>> futureFlow(Future<Flow<I, O, M>> flow) {
        return this.fromGraph(new FutureFlow(flow));
    }

    public <I, O, M> Flow<I, O, Future<M>> lazyFlow(Function0<Flow<I, O, M>> create) {
        return this.lazyFutureFlow((Function0<Future> & Serializable)() -> Future$.MODULE$.successful(create.apply())).addAttributes(Attributes$.MODULE$.apply(Attributes$SourceLocation$.MODULE$.forLambda(create)));
    }

    public <I, O, M> Flow<I, O, Future<M>> lazyFutureFlow(Function0<Future<Flow<I, O, M>>> create) {
        return ((Flow)((Flow)this.apply().flatMapPrefixMat(1, (Function1<Seq, Flow> & Serializable)x0$1 -> {
            SeqOps seqOps;
            Seq seq = x0$1;
            if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = package$.MODULE$.Seq().unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0) {
                Object a = SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0);
                Flow f = MODULE$.futureFlow(((Future)create.apply()).map((Function1<Flow, Flow> & Serializable)x$6 -> ((Flow)MODULE$.apply().prepend(Source$.MODULE$.single(a))).viaMat((Graph)x$6, Keep$.MODULE$.right()), ExecutionContexts$.MODULE$.parasitic()));
                return f;
            }
            if (((Object)Nil$.MODULE$).equals(seq)) {
                FlowOpsMat f = MODULE$.apply().mapMaterializedValue((Function1<NotUsed, Future> & Serializable)x$7 -> Future$.MODULE$.failed(new NeverMaterializedException()));
                return f;
            }
            throw new RuntimeException();
        }, Keep$.MODULE$.right())).addAttributes(Attributes$.MODULE$.apply(Attributes$SourceLocation$.MODULE$.forLambda(create)))).mapMaterializedValue((Function1<Future, Future> & Serializable)x$8 -> x$8.flatten($less$colon$less$.MODULE$.refl()));
    }

    private Flow$() {
    }
}

