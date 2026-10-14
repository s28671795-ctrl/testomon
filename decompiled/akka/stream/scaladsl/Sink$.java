/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.Done$;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.AbruptStageTerminationException;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerHelper$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FlowShape;
import akka.stream.FlowShape$;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Materializer;
import akka.stream.NeverMaterializedException;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Shape;
import akka.stream.SinkShape;
import akka.stream.UniformFanOutShape;
import akka.stream.impl.ActorRefBackpressureSinkStage;
import akka.stream.impl.ActorRefSinkStage;
import akka.stream.impl.CancelSink;
import akka.stream.impl.FanoutPublisherSink;
import akka.stream.impl.HeadOptionStage;
import akka.stream.impl.LazySink;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.PublisherSink;
import akka.stream.impl.QueueSink;
import akka.stream.impl.SeqStage;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.SubscriberSink;
import akka.stream.impl.TakeLastStage;
import akka.stream.impl.fusing.GraphStages$IgnoreSink$;
import akka.stream.impl.fusing.GraphStages$NeverSink$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.SinkQueueWithCancel;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.SeqFactory;
import scala.collection.SeqFactory$UnapplySeqWrapper$;
import scala.collection.SeqOps;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.immutable.Vector$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class Sink$ {
    public static final Sink$ MODULE$ = new Sink$();
    private static final Sink<Object, Future<Done>> _never = MODULE$.fromGraph(GraphStages$NeverSink$.MODULE$);

    public <T> SinkShape<T> shape(String name) {
        return new SinkShape(Inlet$.MODULE$.apply(new StringBuilder(3).append(name).append(".in").toString()));
    }

    public <T, M> Sink<T, M> fromGraph(Graph<SinkShape<T>, M> g2) {
        Graph<SinkShape<T>, M> graph = g2;
        if (graph instanceof Sink) {
            Sink sink2 = (Sink)graph;
            return sink2;
        }
        if (graph instanceof akka.stream.javadsl.Sink) {
            akka.stream.javadsl.Sink sink3 = (akka.stream.javadsl.Sink)graph;
            return sink3.asScala();
        }
        if (graph instanceof GraphStageWithMaterializedValue) {
            GraphStageWithMaterializedValue graphStageWithMaterializedValue = (GraphStageWithMaterializedValue)graph;
            Attributes attrs = graphStageWithMaterializedValue.traversalBuilder().attributes();
            Graph noAttrStage = graphStageWithMaterializedValue.withAttributes(Attributes$.MODULE$.none());
            return new Sink(LinearTraversalBuilder$.MODULE$.fromBuilder(noAttrStage.traversalBuilder(), (Shape)noAttrStage.shape(), Keep$.MODULE$.right()), (SinkShape)noAttrStage.shape()).withAttributes(attrs);
        }
        return new Sink(LinearTraversalBuilder$.MODULE$.fromBuilder(graph.traversalBuilder(), graph.shape(), Keep$.MODULE$.right()), graph.shape());
    }

    public <T, M> Sink<T, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Sink<T, M>> factory2) {
        return Flow$.MODULE$.fromMaterializer((Function2<Materializer, Attributes, Flow> & Serializable)(Materializer mat, Attributes attr) -> Flow$.MODULE$.fromGraph(GraphDSL$.MODULE$.createGraph((Graph)factory2.apply((Materializer)mat, (Attributes)attr), (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<SinkShape, FlowShape> & Serializable)sink2 -> new FlowShape(sink2.in(), b.materializedValue().outlet())))).to(this.head());
    }

    public <T, M> Sink<T, Future<M>> setup(Function2<ActorMaterializer, Attributes, Sink<T, M>> factory2) {
        return this.fromMaterializer((Function2<Materializer, Attributes, Sink> & Serializable)(mat, attr) -> (Sink)factory2.apply(ActorMaterializerHelper$.MODULE$.downcast((Materializer)mat), (Attributes)attr));
    }

    public <T> Sink<T, NotUsed> fromSubscriber(Subscriber<T> subscriber) {
        return this.fromGraph(new SubscriberSink<T>(subscriber, Stages$DefaultAttributes$.MODULE$.subscriberSink(), this.shape("SubscriberSink")));
    }

    public <T> Sink<T, NotUsed> cancelled() {
        return this.fromGraph(new CancelSink(Stages$DefaultAttributes$.MODULE$.cancelledSink(), this.shape("CancelledSink")));
    }

    public <T> Sink<T, Future<T>> head() {
        return ((Sink)this.fromGraph(new HeadOptionStage()).withAttributes(Stages$DefaultAttributes$.MODULE$.headSink())).mapMaterializedValue((Function1<Future, Future> & Serializable)e -> e.map((Function1<Option, Object> & Serializable)x$2 -> x$2.getOrElse((Function0<Nothing$> & Serializable)() -> {
            throw new NoSuchElementException("head of empty stream");
        }), ExecutionContexts$.MODULE$.parasitic()));
    }

    public <T> Sink<T, Future<Option<T>>> headOption() {
        return this.fromGraph(new HeadOptionStage()).withAttributes(Stages$DefaultAttributes$.MODULE$.headOptionSink());
    }

    public <T> Sink<T, Future<T>> last() {
        return ((Sink)this.fromGraph(new TakeLastStage(1)).withAttributes(Stages$DefaultAttributes$.MODULE$.lastSink())).mapMaterializedValue((Function1<Future, Future> & Serializable)e -> e.map((Function1<Seq, Object> & Serializable)x$3 -> x$3.headOption().getOrElse((Function0<Nothing$> & Serializable)() -> {
            throw new NoSuchElementException("last of empty stream");
        }), ExecutionContexts$.MODULE$.parasitic()));
    }

    public <T> Sink<T, Future<Option<T>>> lastOption() {
        return ((Sink)this.fromGraph(new TakeLastStage(1)).withAttributes(Stages$DefaultAttributes$.MODULE$.lastOptionSink())).mapMaterializedValue((Function1<Future, Future> & Serializable)e -> e.map((Function1<Seq, Option> & Serializable)x$4 -> x$4.headOption(), ExecutionContexts$.MODULE$.parasitic()));
    }

    public <T> Sink<T, Future<Seq<T>>> takeLast(int n) {
        return this.fromGraph(new TakeLastStage(n)).withAttributes(Stages$DefaultAttributes$.MODULE$.takeLastSink());
    }

    public <T> Sink<T, Future<Seq<T>>> seq() {
        return this.fromGraph(new SeqStage(Vector$.MODULE$.iterableFactory()));
    }

    public <T, That> Sink<T, Future<That>> collection(Factory<T, That> cbf) {
        return this.fromGraph(new SeqStage<T, That>(cbf));
    }

    public <T> Sink<T, Publisher<T>> asPublisher(boolean fanout) {
        return this.fromGraph(fanout ? new FanoutPublisherSink<T>(Stages$DefaultAttributes$.MODULE$.fanoutPublisherSink(), this.shape("FanoutPublisherSink")) : new PublisherSink<T>(Stages$DefaultAttributes$.MODULE$.publisherSink(), this.shape("PublisherSink")));
    }

    public Sink<Object, Future<Done>> ignore() {
        return this.fromGraph(GraphStages$IgnoreSink$.MODULE$);
    }

    public Sink<Object, Future<Done>> never() {
        return _never;
    }

    public <T> Sink<T, Future<Done>> foreach(Function1<T, BoxedUnit> f) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().map(f)).toMat(this.ignore(), Keep$.MODULE$.right())).named("foreachSink");
    }

    public <T> Sink<T, Future<Done>> foreachAsync(int parallelism, Function1<T, Future<BoxedUnit>> f) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().mapAsyncUnordered(parallelism, f)).toMat(this.ignore(), Keep$.MODULE$.right())).named("foreachAsyncSink");
    }

    public <T, U> Sink<T, NotUsed> combine(Sink<U, ?> first, Sink<U, ?> second, Seq<Sink<U, ?>> rest, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        return this.fromGraph(GraphDSL$.MODULE$.create((Function1<GraphDSL.Builder, SinkShape> & Serializable)b -> {
            UniformFanOutShape d = (UniformFanOutShape)b.add((Graph)fanOutStrategy.apply(BoxesRunTime.boxToInteger(rest.size() + 2)));
            GraphDSL$Implicits$.MODULE$.port2flow(d.out(0), (GraphDSL.Builder<?>)b).$tilde$greater(first, (GraphDSL.Builder<?>)b);
            GraphDSL$Implicits$.MODULE$.port2flow(d.out(1), (GraphDSL.Builder<?>)b).$tilde$greater(second, (GraphDSL.Builder<?>)b);
            return this.combineRest$1(2, rest.iterator(), d, (GraphDSL.Builder)b);
        }));
    }

    public <T, U, M1, M2, M> Sink<T, M> combineMat(Sink<U, M1> first, Sink<U, M2> second, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy, Function2<M1, M2, M> matF) {
        return this.fromGraph(GraphDSL$.MODULE$.createGraph(first, second, matF, (Function1<GraphDSL.Builder, Function2> & Serializable)b -> (Function2<SinkShape, SinkShape, SinkShape> & Serializable)(shape1, shape2) -> {
            UniformFanOutShape d = (UniformFanOutShape)b.add((Graph)fanOutStrategy.apply(BoxesRunTime.boxToInteger(2)));
            GraphDSL$Implicits$.MODULE$.port2flow(d.out(0), (GraphDSL.Builder<?>)b).$tilde$greater(shape1, (GraphDSL.Builder<?>)b);
            GraphDSL$Implicits$.MODULE$.port2flow(d.out(1), (GraphDSL.Builder<?>)b).$tilde$greater(shape2, (GraphDSL.Builder<?>)b);
            return new SinkShape(d.in());
        }));
    }

    public <T, U, M> Sink<T, Seq<M>> combine(Seq<Graph<SinkShape<U>, M>> sinks, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        SeqOps seqOps;
        SeqOps seqOps2;
        Seq seq = sinks;
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps2 = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2), 0) == 0) {
            return this.cancelled().mapMaterializedValue((Function1<NotUsed, Nil$> & Serializable)x$5 -> Nil$.MODULE$);
        }
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0) {
            Graph sink2 = (Graph)SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0);
            return ((Sink)sink2).mapMaterializedValue((Function1<Object, List> & Serializable)x$6 -> {
                Object object = x$6;
                return Nil$.MODULE$.$colon$colon(object);
            });
        }
        return this.fromGraph(GraphDSL$.MODULE$.create(sinks, (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<Seq, SinkShape> & Serializable)shapes -> {
            UniformFanOutShape c = (UniformFanOutShape)b.add((Graph)fanOutStrategy.apply(BoxesRunTime.boxToInteger(sinks.size())));
            ((IterableOps)shapes.zipWithIndex()).withFilter((Function1<Tuple2, Object> & Serializable)check$ifrefutable$1 -> BoxesRunTime.boxToBoolean(Sink$.$anonfun$combine$6(check$ifrefutable$1))).foreach((Function1<Tuple2, Object> & Serializable)(A x$7) -> {
                Sink$.$anonfun$combine$7(c, b, x$7);
                return BoxedUnit.UNIT;
            });
            return new SinkShape(c.in());
        }));
    }

    public <U, T> Sink<T, Future<U>> fold(U zero, Function2<U, T, U> f) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().fold(zero, f)).toMat(this.head(), Keep$.MODULE$.right())).named("foldSink");
    }

    public <U, T> Sink<T, Future<U>> foldAsync(U zero, Function2<U, T, Future<U>> f) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().foldAsync(zero, f)).toMat(this.head(), Keep$.MODULE$.right())).named("foldAsyncSink");
    }

    public <T> Sink<T, Future<T>> reduce(Function2<T, T, T> f) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().reduce(f)).toMat(this.head(), Keep$.MODULE$.right())).named("reduceSink");
    }

    public <T> Sink<T, NotUsed> onComplete(Function1<Try<Done>, BoxedUnit> callback) {
        return ((Sink)((Flow)Flow$.MODULE$.apply().via((Graph)Sink$.newOnCompleteStage$1(callback))).to(this.ignore())).named("onCompleteSink");
    }

    public <T> Sink<T, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return this.fromGraph(new ActorRefSinkStage(ref, onCompleteMessage, onFailureMessage));
    }

    public <T> Sink<T, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage) {
        return this.fromGraph(new ActorRefSinkStage(ref, onCompleteMessage, (Function1<Throwable, Object>)(Function1<Throwable, Status.Failure> & Serializable)t2 -> new Status.Failure((Throwable)t2)));
    }

    @InternalApi
    public <T> Sink<T, NotUsed> actorRefWithAck(ActorRef ref, Function1<ActorRef, Function1<T, Object>> messageAdapter, Function1<ActorRef, Object> onInitMessage, Option<Object> ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return this.fromGraph(new ActorRefBackpressureSinkStage(ref, messageAdapter, onInitMessage, ackMessage, onCompleteMessage, onFailureMessage));
    }

    public <T> Sink<T, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return this.actorRefWithAck(ref, (Function1<ActorRef, Function1> & Serializable)x$8 -> (Function1<Object, Object> & Serializable)x -> Predef$.MODULE$.identity(x), (Function1<ActorRef, Object> & Serializable)x$9 -> onInitMessage, new Some<Object>(ackMessage), onCompleteMessage, onFailureMessage);
    }

    public <T> Sink<T, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return this.actorRefWithAck(ref, (Function1<ActorRef, Function1> & Serializable)x$10 -> (Function1<Object, Object> & Serializable)x -> Predef$.MODULE$.identity(x), (Function1<ActorRef, Object> & Serializable)x$11 -> onInitMessage, None$.MODULE$, onCompleteMessage, onFailureMessage);
    }

    public <T> Sink<T, NotUsed> actorRefWithAck(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return this.actorRefWithAck(ref, (Function1<ActorRef, Function1> & Serializable)x$12 -> (Function1<Object, Object> & Serializable)x -> Predef$.MODULE$.identity(x), (Function1<ActorRef, Object> & Serializable)x$13 -> onInitMessage, new Some<Object>(ackMessage), onCompleteMessage, onFailureMessage);
    }

    public <T> Function1<Throwable, Object> actorRefWithAck$default$5() {
        return (Function1<Throwable, Status.Failure> & Serializable)cause -> new Status.Failure((Throwable)cause);
    }

    public <T> Sink<T, SinkQueueWithCancel<T>> queue(int maxConcurrentPulls) {
        return this.fromGraph(new QueueSink(maxConcurrentPulls));
    }

    public <T> Sink<T, SinkQueueWithCancel<T>> queue() {
        return this.queue(1);
    }

    public <T, M> Sink<T, Future<M>> lazyInit(Function1<T, Future<Sink<T, M>>> sinkFactory, Function0<M> fallback) {
        return this.fromGraph(new LazySink<T, M>(sinkFactory)).mapMaterializedValue((Function1<Future, Future> & Serializable)x$14 -> x$14.recover(new Serializable(fallback){
            private static final long serialVersionUID = 0L;
            private final Function0 fallback$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof NeverMaterializedException) {
                    return (B1)this.fallback$1.apply();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof NeverMaterializedException;
            }
            {
                this.fallback$1 = fallback$1;
            }
        }, ExecutionContexts$.MODULE$.parasitic()));
    }

    public <T, M> Sink<T, Future<Option<M>>> lazyInitAsync(Function0<Future<Sink<T, M>>> sinkFactory) {
        return this.fromGraph(new LazySink((Function1<Object, Future> & Serializable)x$15 -> (Future)sinkFactory.apply())).mapMaterializedValue((Function1<Future, Future> & Serializable)m4 -> {
            ExecutionContext ec = ExecutionContexts$.MODULE$.parasitic();
            return m4.map((Function1<Object, Option> & Serializable)x -> Option$.MODULE$.apply(x), ec).recover(new Serializable(){
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
            }, ec);
        });
    }

    public <T, M> Sink<T, Future<M>> futureSink(Future<Sink<T, M>> future) {
        return this.lazyFutureSink((Function0<Future> & Serializable)() -> future);
    }

    public <T, M> Sink<T, Future<M>> lazySink(Function0<Sink<T, M>> create) {
        return this.lazyFutureSink((Function0<Future> & Serializable)() -> Future$.MODULE$.successful(create.apply()));
    }

    public <T, M> Sink<T, Future<M>> lazyFutureSink(Function0<Future<Sink<T, M>>> create) {
        return this.fromGraph(new LazySink((Function1<Object, Future> & Serializable)x$16 -> (Future)create.apply()));
    }

    private final SinkShape combineRest$1(int idx, Iterator i, UniformFanOutShape d$1, GraphDSL.Builder b$2) {
        while (i.hasNext()) {
            GraphDSL$Implicits$.MODULE$.port2flow(d$1.out(idx), b$2).$tilde$greater((Graph)i.next(), (GraphDSL.Builder<?>)b$2);
            ++idx;
        }
        return new SinkShape(d$1.in());
    }

    public static final /* synthetic */ boolean $anonfun$combine$6(Tuple2 check$ifrefutable$1) {
        Tuple2 tuple2 = check$ifrefutable$1;
        return tuple2 != null;
    }

    public static final /* synthetic */ void $anonfun$combine$7(UniformFanOutShape c$1, GraphDSL.Builder b$4, Tuple2 x$7) {
        Tuple2 tuple2 = x$7;
        if (tuple2 != null) {
            SinkShape shape = (SinkShape)tuple2._1();
            int idx = tuple2._2$mcI$sp();
            GraphDSL$Implicits$.MODULE$.port2flow(c$1.out(idx), b$4).$tilde$greater(shape, b$4);
            return;
        }
        throw new MatchError(tuple2);
    }

    private static final GraphStage newOnCompleteStage$1(Function1 callback$1) {
        return new GraphStage<FlowShape<T, NotUsed>>(callback$1){
            private final Inlet<T> akka$stream$scaladsl$Sink$$anon$$in;
            private final Outlet<NotUsed> akka$stream$scaladsl$Sink$$anon$$out;
            private final FlowShape<T, NotUsed> shape;
            public final Function1 callback$1;

            public Inlet<T> akka$stream$scaladsl$Sink$$anon$$in() {
                return this.akka$stream$scaladsl$Sink$$anon$$in;
            }

            public Outlet<NotUsed> akka$stream$scaladsl$Sink$$anon$$out() {
                return this.akka$stream$scaladsl$Sink$$anon$$out;
            }

            public FlowShape<T, NotUsed> shape() {
                return this.shape;
            }

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new InHandler(this){
                    private boolean completionSignalled;
                    private final /* synthetic */ anon.1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    private boolean completionSignalled() {
                        return this.completionSignalled;
                    }

                    private void completionSignalled_$eq(boolean x$1) {
                        this.completionSignalled = x$1;
                    }

                    public void onPush() {
                        this.pull(this.$outer.akka$stream$scaladsl$Sink$$anon$$in());
                    }

                    public void onPull() {
                        this.pull(this.$outer.akka$stream$scaladsl$Sink$$anon$$in());
                    }

                    public void onUpstreamFailure(Throwable cause) {
                        this.$outer.callback$1.apply(new Failure<T>(cause));
                        this.completionSignalled_$eq(true);
                        this.failStage(cause);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.callback$1.apply(new Success<Done$>(Done$.MODULE$));
                        this.completionSignalled_$eq(true);
                        this.completeStage();
                    }

                    public void postStop() {
                        if (!this.completionSignalled()) {
                            this.$outer.callback$1.apply(new Failure<T>(new AbruptStageTerminationException(this)));
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.shape());
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                        this.completionSignalled = false;
                        this.setHandlers($outer.akka$stream$scaladsl$Sink$$anon$$in(), $outer.akka$stream$scaladsl$Sink$$anon$$out(), this);
                    }
                };
            }
            {
                this.callback$1 = callback$1;
                this.akka$stream$scaladsl$Sink$$anon$$in = Inlet$.MODULE$.apply("in");
                this.akka$stream$scaladsl$Sink$$anon$$out = Outlet$.MODULE$.apply("out");
                this.shape = FlowShape$.MODULE$.of(this.akka$stream$scaladsl$Sink$$anon$$in(), this.akka$stream$scaladsl$Sink$$anon$$out());
            }
        };
    }

    private Sink$() {
    }
}

