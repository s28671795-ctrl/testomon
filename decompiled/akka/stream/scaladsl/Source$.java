/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.NotUsed;
import akka.NotUsed$;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Status;
import akka.actor.Status$Success$;
import akka.annotation.InternalApi;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerHelper$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.BoundedSourceQueue;
import akka.stream.CompletionStrategy;
import akka.stream.CompletionStrategy$Draining$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.Outlet$;
import akka.stream.OverflowStrategy;
import akka.stream.Shape;
import akka.stream.SourceShape;
import akka.stream.UniformFanInShape;
import akka.stream.impl.ActorRefBackpressureSource;
import akka.stream.impl.ActorRefSource;
import akka.stream.impl.BoundedSourceQueueStage;
import akka.stream.impl.EmptySource$;
import akka.stream.impl.FailedSource;
import akka.stream.impl.LazySource;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.MaybeSource$;
import akka.stream.impl.PublisherSource;
import akka.stream.impl.QueueSource;
import akka.stream.impl.SetupSourceStage;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.SubscriberSource;
import akka.stream.impl.Unfold;
import akka.stream.impl.UnfoldAsync;
import akka.stream.impl.UnfoldResourceSource;
import akka.stream.impl.UnfoldResourceSourceAsync;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.FlowOpsMat;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.MergePrioritized$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.SourceQueueWithComplete;
import akka.stream.scaladsl.StreamConverters$;
import akka.stream.scaladsl.ZipWithN;
import akka.stream.scaladsl.ZipWithN$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.util.ConstantFun$;
import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.stream.BaseStream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.$less$colon$less;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.SeqFactory;
import scala.collection.SeqFactory$UnapplySeqWrapper$;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.concurrent.Future;
import scala.concurrent.Future$never$;
import scala.concurrent.Promise;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.package$;
import scala.reflect.ClassTag;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

public final class Source$ {
    public static final Source$ MODULE$ = new Source$();
    private static final Source<Nothing$, NotUsed> _empty = MODULE$.fromGraph(EmptySource$.MODULE$);
    private static final Source<Nothing$, NotUsed> _never = MODULE$.future(Future$never$.MODULE$).withAttributes(Stages$DefaultAttributes$.MODULE$.neverSource());

    public <T> SourceShape<T> shape(String name) {
        return new SourceShape(Outlet$.MODULE$.apply(new java.lang.StringBuilder(4).append(name).append(".out").toString()));
    }

    public <T> Source<T, NotUsed> fromPublisher(Publisher<T> publisher) {
        return this.fromGraph(new PublisherSource<T>(publisher, Stages$DefaultAttributes$.MODULE$.publisherSource(), this.shape("PublisherSource")));
    }

    public <T> Source<T, NotUsed> fromIterator(Function0<Iterator<T>> f) {
        return this.apply(new scala.collection.immutable.Iterable<T>(f){
            private final Function0 f$2;

            public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
                return scala.collection.immutable.Iterable.iterableFactory$(this);
            }

            public final Iterable<T> toIterable() {
                return Iterable.toIterable$(this);
            }

            public final Iterable<T> coll() {
                return Iterable.coll$(this);
            }

            public Iterable<T> seq() {
                return Iterable.seq$(this);
            }

            public String className() {
                return Iterable.className$(this);
            }

            public final String collectionClassName() {
                return Iterable.collectionClassName$(this);
            }

            public String stringPrefix() {
                return Iterable.stringPrefix$(this);
            }

            public <B> LazyZip2<T, B, Iterable> lazyZip(Iterable<B> that) {
                return Iterable.lazyZip$(this, that);
            }

            public IterableOps fromSpecific(IterableOnce coll) {
                return IterableFactoryDefaults.fromSpecific$(this, coll);
            }

            public Builder<T, scala.collection.immutable.Iterable<T>> newSpecificBuilder() {
                return IterableFactoryDefaults.newSpecificBuilder$(this);
            }

            public IterableOps empty() {
                return IterableFactoryDefaults.empty$(this);
            }

            public final Iterable<T> toTraversable() {
                return IterableOps.toTraversable$(this);
            }

            public boolean isTraversableAgain() {
                return IterableOps.isTraversableAgain$(this);
            }

            public final Object repr() {
                return IterableOps.repr$(this);
            }

            public IterableFactory<?> companion() {
                return IterableOps.companion$(this);
            }

            public T head() {
                return (T)IterableOps.head$(this);
            }

            public Option<T> headOption() {
                return IterableOps.headOption$(this);
            }

            public T last() {
                return (T)IterableOps.last$(this);
            }

            public Option<T> lastOption() {
                return IterableOps.lastOption$(this);
            }

            public View<T> view() {
                return IterableOps.view$(this);
            }

            public int sizeCompare(int otherSize) {
                return IterableOps.sizeCompare$((IterableOps)this, otherSize);
            }

            public final IterableOps sizeIs() {
                return IterableOps.sizeIs$(this);
            }

            public int sizeCompare(Iterable<?> that) {
                return IterableOps.sizeCompare$((IterableOps)this, that);
            }

            public View<T> view(int from, int until) {
                return IterableOps.view$(this, from, until);
            }

            public Object transpose(Function1 asIterable) {
                return IterableOps.transpose$(this, asIterable);
            }

            public Object filter(Function1 pred) {
                return IterableOps.filter$(this, pred);
            }

            public Object filterNot(Function1 pred) {
                return IterableOps.filterNot$(this, pred);
            }

            public WithFilter<T, ?> withFilter(Function1<T, Object> p) {
                return IterableOps.withFilter$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<T>, scala.collection.immutable.Iterable<T>> partition(Function1<T, Object> p) {
                return IterableOps.partition$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<T>, scala.collection.immutable.Iterable<T>> splitAt(int n) {
                return IterableOps.splitAt$(this, n);
            }

            public Object take(int n) {
                return IterableOps.take$(this, n);
            }

            public Object takeRight(int n) {
                return IterableOps.takeRight$(this, n);
            }

            public Object takeWhile(Function1 p) {
                return IterableOps.takeWhile$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<T>, scala.collection.immutable.Iterable<T>> span(Function1<T, Object> p) {
                return IterableOps.span$(this, p);
            }

            public Object drop(int n) {
                return IterableOps.drop$(this, n);
            }

            public Object dropRight(int n) {
                return IterableOps.dropRight$(this, n);
            }

            public Object dropWhile(Function1 p) {
                return IterableOps.dropWhile$(this, p);
            }

            public Iterator<scala.collection.immutable.Iterable<T>> grouped(int size) {
                return IterableOps.grouped$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<T>> sliding(int size) {
                return IterableOps.sliding$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<T>> sliding(int size, int step) {
                return IterableOps.sliding$(this, size, step);
            }

            public Object tail() {
                return IterableOps.tail$(this);
            }

            public Object init() {
                return IterableOps.init$(this);
            }

            public Object slice(int from, int until) {
                return IterableOps.slice$(this, from, until);
            }

            public <K> Map<K, scala.collection.immutable.Iterable<T>> groupBy(Function1<T, K> f) {
                return IterableOps.groupBy$(this, f);
            }

            public <K, B> Map<K, scala.collection.immutable.Iterable<B>> groupMap(Function1<T, K> key, Function1<T, B> f) {
                return IterableOps.groupMap$(this, key, f);
            }

            public <K, B> Map<K, B> groupMapReduce(Function1<T, K> key, Function1<T, B> f, Function2<B, B, B> reduce) {
                return IterableOps.groupMapReduce$(this, key, f, reduce);
            }

            public Object scan(Object z, Function2 op) {
                return IterableOps.scan$(this, z, op);
            }

            public Object scanLeft(Object z, Function2 op) {
                return IterableOps.scanLeft$(this, z, op);
            }

            public Object scanRight(Object z, Function2 op) {
                return IterableOps.scanRight$(this, z, op);
            }

            public Object map(Function1 f) {
                return IterableOps.map$(this, f);
            }

            public Object flatMap(Function1 f) {
                return IterableOps.flatMap$(this, f);
            }

            public Object flatten(Function1 asIterable) {
                return IterableOps.flatten$(this, asIterable);
            }

            public Object collect(PartialFunction pf) {
                return IterableOps.collect$(this, pf);
            }

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<T, Either<A1, A2>> f) {
                return IterableOps.partitionMap$(this, f);
            }

            public Object concat(IterableOnce suffix) {
                return IterableOps.concat$(this, suffix);
            }

            public final Object $plus$plus(IterableOnce suffix) {
                return IterableOps.$plus$plus$(this, suffix);
            }

            public Object zip(IterableOnce that) {
                return IterableOps.zip$(this, that);
            }

            public Object zipWithIndex() {
                return IterableOps.zipWithIndex$(this);
            }

            public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
                return IterableOps.zipAll$(this, that, thisElem, thatElem);
            }

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<T, Tuple2<A1, A2>> asPair) {
                return IterableOps.unzip$(this, asPair);
            }

            public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<T, Tuple3<A1, A2, A3>> asTriple) {
                return IterableOps.unzip3$(this, asTriple);
            }

            public Iterator<scala.collection.immutable.Iterable<T>> tails() {
                return IterableOps.tails$(this);
            }

            public Iterator<scala.collection.immutable.Iterable<T>> inits() {
                return IterableOps.inits$(this);
            }

            public Object tapEach(Function1 f) {
                return IterableOps.tapEach$(this, f);
            }

            public Object $plus$plus$colon(IterableOnce that) {
                return IterableOps.$plus$plus$colon$(this, that);
            }

            public boolean hasDefiniteSize() {
                return IterableOnceOps.hasDefiniteSize$(this);
            }

            public <U> void foreach(Function1<T, U> f) {
                IterableOnceOps.foreach$(this, f);
            }

            public boolean forall(Function1<T, Object> p) {
                return IterableOnceOps.forall$(this, p);
            }

            public boolean exists(Function1<T, Object> p) {
                return IterableOnceOps.exists$(this, p);
            }

            public int count(Function1<T, Object> p) {
                return IterableOnceOps.count$(this, p);
            }

            public Option<T> find(Function1<T, Object> p) {
                return IterableOnceOps.find$(this, p);
            }

            public <B> B foldLeft(B z, Function2<B, T, B> op) {
                return (B)IterableOnceOps.foldLeft$(this, z, op);
            }

            public <B> B foldRight(B z, Function2<T, B, B> op) {
                return (B)IterableOnceOps.foldRight$(this, z, op);
            }

            public final <B> B $div$colon(B z, Function2<B, T, B> op) {
                return (B)IterableOnceOps.$div$colon$(this, z, op);
            }

            public final <B> B $colon$bslash(B z, Function2<T, B, B> op) {
                return (B)IterableOnceOps.$colon$bslash$(this, z, op);
            }

            public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
                return (A1)IterableOnceOps.fold$(this, z, op);
            }

            public <B> B reduce(Function2<B, B, B> op) {
                return (B)IterableOnceOps.reduce$(this, op);
            }

            public <B> Option<B> reduceOption(Function2<B, B, B> op) {
                return IterableOnceOps.reduceOption$(this, op);
            }

            public <B> B reduceLeft(Function2<B, T, B> op) {
                return (B)IterableOnceOps.reduceLeft$(this, op);
            }

            public <B> B reduceRight(Function2<T, B, B> op) {
                return (B)IterableOnceOps.reduceRight$(this, op);
            }

            public <B> Option<B> reduceLeftOption(Function2<B, T, B> op) {
                return IterableOnceOps.reduceLeftOption$(this, op);
            }

            public <B> Option<B> reduceRightOption(Function2<T, B, B> op) {
                return IterableOnceOps.reduceRightOption$(this, op);
            }

            public boolean isEmpty() {
                return IterableOnceOps.isEmpty$(this);
            }

            public boolean nonEmpty() {
                return IterableOnceOps.nonEmpty$(this);
            }

            public int size() {
                return IterableOnceOps.size$(this);
            }

            public final <B> void copyToBuffer(Buffer<B> dest) {
                IterableOnceOps.copyToBuffer$(this, dest);
            }

            public <B> int copyToArray(Object xs) {
                return IterableOnceOps.copyToArray$(this, xs);
            }

            public <B> int copyToArray(Object xs, int start2) {
                return IterableOnceOps.copyToArray$(this, xs, start2);
            }

            public <B> int copyToArray(Object xs, int start2, int len) {
                return IterableOnceOps.copyToArray$(this, xs, start2, len);
            }

            public <B> B sum(Numeric<B> num) {
                return (B)IterableOnceOps.sum$(this, num);
            }

            public <B> B product(Numeric<B> num) {
                return (B)IterableOnceOps.product$(this, num);
            }

            public <B> T min(Ordering<B> ord) {
                return (T)IterableOnceOps.min$(this, ord);
            }

            public <B> Option<T> minOption(Ordering<B> ord) {
                return IterableOnceOps.minOption$(this, ord);
            }

            public <B> T max(Ordering<B> ord) {
                return (T)IterableOnceOps.max$(this, ord);
            }

            public <B> Option<T> maxOption(Ordering<B> ord) {
                return IterableOnceOps.maxOption$(this, ord);
            }

            public <B> T maxBy(Function1<T, B> f, Ordering<B> cmp) {
                return (T)IterableOnceOps.maxBy$(this, f, cmp);
            }

            public <B> Option<T> maxByOption(Function1<T, B> f, Ordering<B> cmp) {
                return IterableOnceOps.maxByOption$(this, f, cmp);
            }

            public <B> T minBy(Function1<T, B> f, Ordering<B> cmp) {
                return (T)IterableOnceOps.minBy$(this, f, cmp);
            }

            public <B> Option<T> minByOption(Function1<T, B> f, Ordering<B> cmp) {
                return IterableOnceOps.minByOption$(this, f, cmp);
            }

            public <B> Option<B> collectFirst(PartialFunction<T, B> pf) {
                return IterableOnceOps.collectFirst$(this, pf);
            }

            public <B> B aggregate(Function0<B> z, Function2<B, T, B> seqop, Function2<B, B, B> combop) {
                return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
            }

            public <B> boolean corresponds(IterableOnce<B> that, Function2<T, B, Object> p) {
                return IterableOnceOps.corresponds$(this, that, p);
            }

            public final String mkString(String start2, String sep, String end) {
                return IterableOnceOps.mkString$(this, start2, sep, end);
            }

            public final String mkString(String sep) {
                return IterableOnceOps.mkString$(this, sep);
            }

            public final String mkString() {
                return IterableOnceOps.mkString$(this);
            }

            public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
                return IterableOnceOps.addString$(this, b, start2, sep, end);
            }

            public final StringBuilder addString(StringBuilder b, String sep) {
                return IterableOnceOps.addString$(this, b, sep);
            }

            public final StringBuilder addString(StringBuilder b) {
                return IterableOnceOps.addString$(this, b);
            }

            public <C1> C1 to(Factory<T, C1> factory2) {
                return (C1)IterableOnceOps.to$(this, factory2);
            }

            public final Iterator<T> toIterator() {
                return IterableOnceOps.toIterator$(this);
            }

            public List<T> toList() {
                return IterableOnceOps.toList$(this);
            }

            public Vector<T> toVector() {
                return IterableOnceOps.toVector$(this);
            }

            public <K, V> Map<K, V> toMap($less$colon$less<T, Tuple2<K, V>> ev) {
                return IterableOnceOps.toMap$(this, ev);
            }

            public <B> Set<B> toSet() {
                return IterableOnceOps.toSet$(this);
            }

            public Seq<T> toSeq() {
                return IterableOnceOps.toSeq$(this);
            }

            public IndexedSeq<T> toIndexedSeq() {
                return IterableOnceOps.toIndexedSeq$(this);
            }

            public final Stream<T> toStream() {
                return IterableOnceOps.toStream$(this);
            }

            public final <B> Buffer<B> toBuffer() {
                return IterableOnceOps.toBuffer$(this);
            }

            public <B> Object toArray(ClassTag<B> evidence$2) {
                return IterableOnceOps.toArray$(this, evidence$2);
            }

            public Iterable<T> reversed() {
                return IterableOnceOps.reversed$(this);
            }

            public <S extends Stepper<?>> S stepper(StepperShape<T, S> shape) {
                return (S)IterableOnce.stepper$(this, shape);
            }

            public int knownSize() {
                return IterableOnce.knownSize$(this);
            }

            public Iterator<T> iterator() {
                return (Iterator)this.f$2.apply();
            }

            public String toString() {
                return "() => Iterator";
            }
            {
                this.f$2 = f$2;
                IterableOnce.$init$(this);
                IterableOnceOps.$init$(this);
                IterableOps.$init$(this);
                IterableFactoryDefaults.$init$(this);
                Iterable.$init$(this);
                scala.collection.immutable.Iterable.$init$(this);
            }
        });
    }

    public <T, S extends BaseStream<T, S>> Source<T, NotUsed> fromJavaStream(Function0<BaseStream<T, S>> stream) {
        return StreamConverters$.MODULE$.fromJavaStream(stream);
    }

    public <T> Source<T, NotUsed> cycle(Function0<Iterator<T>> f) {
        Iterator iterator2 = package$.MODULE$.Iterator().continually((Function0<Iterator> & Serializable)() -> {
            Iterator i = (Iterator)f.apply();
            if (i.isEmpty()) {
                throw new IllegalArgumentException("empty iterator");
            }
            return i;
        }).flatten(Predef$.MODULE$.$conforms());
        return this.fromIterator((Function0<Iterator> & Serializable)() -> iterator2).withAttributes(Stages$DefaultAttributes$.MODULE$.cycledSource());
    }

    public <T, M> Source<T, M> fromGraph(Graph<SourceShape<T>, M> g2) {
        Graph<SourceShape<T>, M> graph = g2;
        if (graph instanceof Source) {
            Source source2 = (Source)graph;
            return source2;
        }
        if (graph instanceof akka.stream.javadsl.Source) {
            akka.stream.javadsl.Source source3 = (akka.stream.javadsl.Source)graph;
            return source3.asScala();
        }
        if (graph instanceof GraphStageWithMaterializedValue) {
            GraphStageWithMaterializedValue graphStageWithMaterializedValue = (GraphStageWithMaterializedValue)graph;
            Attributes attrs = graphStageWithMaterializedValue.traversalBuilder().attributes();
            Graph noAttrStage = graphStageWithMaterializedValue.withAttributes(Attributes$.MODULE$.none());
            return new Source(LinearTraversalBuilder$.MODULE$.fromBuilder(noAttrStage.traversalBuilder(), (Shape)noAttrStage.shape(), Keep$.MODULE$.right()), (SourceShape)noAttrStage.shape()).withAttributes(attrs);
        }
        return new Source(LinearTraversalBuilder$.MODULE$.fromBuilder(graph.traversalBuilder(), graph.shape(), Keep$.MODULE$.right()), graph.shape());
    }

    public <T, M> Source<T, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Source<T, M>> factory2) {
        return this.fromGraph(new SetupSourceStage<T, M>(factory2));
    }

    public <T, M> Source<T, Future<M>> setup(Function2<ActorMaterializer, Attributes, Source<T, M>> factory2) {
        return this.fromGraph(new SetupSourceStage((Function2<Materializer, Attributes, Source> & Serializable)(materializer, attributes) -> (Source)factory2.apply(ActorMaterializerHelper$.MODULE$.downcast((Materializer)materializer), (Attributes)attributes)));
    }

    public <T> Source<T, NotUsed> apply(scala.collection.immutable.Iterable<T> iterable) {
        return this.fromGraph(new GraphStages.IterableSource<T>(iterable));
    }

    public <T> Source<T, NotUsed> fromFuture(Future<T> future) {
        return this.fromGraph(new GraphStages.FutureSource<T>(future));
    }

    public <T> Source<T, NotUsed> fromCompletionStage(CompletionStage<T> future) {
        return this.fromGraph(new GraphStages.FutureSource<T>(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(future))));
    }

    public <T, M> Source<T, Future<M>> fromFutureSource(Future<Graph<SourceShape<T>, M>> future) {
        return this.fromGraph(new GraphStages.FutureFlattenSource<T, M>(future));
    }

    public <T, M> Source<T, CompletionStage<M>> fromSourceCompletionStage(CompletionStage<? extends Graph<SourceShape<T>, M>> completion) {
        return this.fromFutureSource(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(completion))).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$2 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$2)));
    }

    public <T> Source<T, Cancellable> tick(FiniteDuration initialDelay, FiniteDuration interval, T tick) {
        return this.fromGraph(new GraphStages.TickSource<T>(initialDelay, interval, tick));
    }

    public <T> Source<T, NotUsed> single(T element) {
        return this.fromGraph(new GraphStages.SingleSource<T>(element));
    }

    public <T> Source<T, NotUsed> repeat(T element) {
        return this.fromIterator((Function0<Iterator> & Serializable)() -> package$.MODULE$.Iterator().continually((Function0<Object> & Serializable)() -> element)).withAttributes(Stages$DefaultAttributes$.MODULE$.repeat());
    }

    public <S, E> Source<E, NotUsed> unfold(S s2, Function1<S, Option<Tuple2<S, E>>> f) {
        return this.fromGraph(new Unfold<S, E>(s2, f));
    }

    public <S, E> Source<E, NotUsed> unfoldAsync(S s2, Function1<S, Future<Option<Tuple2<S, E>>>> f) {
        return this.fromGraph(new UnfoldAsync<S, E>(s2, f));
    }

    public <T> Source<T, NotUsed> empty() {
        return _empty;
    }

    public <T> Source<T, Promise<Option<T>>> maybe() {
        return this.fromGraph(MaybeSource$.MODULE$);
    }

    public <T> Source<T, NotUsed> failed(Throwable cause) {
        return this.fromGraph(new FailedSource(cause));
    }

    public <T, M> Source<T, Future<M>> lazily(Function0<Source<T, M>> create) {
        return this.fromGraph(new LazySource<T, M>(create));
    }

    public <T> Source<T, Future<NotUsed>> lazilyAsync(Function0<Future<T>> create) {
        return this.lazily((Function0<Source> & Serializable)() -> MODULE$.fromFuture((Future)create.apply()));
    }

    public <T> Source<T, NotUsed> future(Future<T> futureElement) {
        return this.fromGraph(new GraphStages.FutureSource<T>(futureElement));
    }

    public <T> Source<T, NotUsed> never() {
        return _never;
    }

    public <T> Source<T, NotUsed> completionStage(CompletionStage<T> completionStage) {
        return this.future(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(completionStage)));
    }

    public <T, M> Source<T, Future<M>> futureSource(Future<Source<T, M>> futureSource) {
        return this.fromGraph(new GraphStages.FutureFlattenSource(futureSource));
    }

    public <T> Source<T, NotUsed> lazySingle(Function0<T> create) {
        return ((Source)this.single(BoxedUnit.UNIT).map((Function1<BoxedUnit, Object> & Serializable)x$3 -> create.apply())).withAttributes(Stages$DefaultAttributes$.MODULE$.lazySingle());
    }

    public <T> Source<T, NotUsed> lazyFuture(Function0<Future<T>> create) {
        return ((Source)this.single(BoxedUnit.UNIT).mapAsyncUnordered(1, (Function1<BoxedUnit, Future> & Serializable)x$4 -> (Future)create.apply())).withAttributes(Stages$DefaultAttributes$.MODULE$.lazyFuture());
    }

    public <T, M> Source<T, Future<M>> lazySource(Function0<Source<T, M>> create) {
        return this.fromGraph(new LazySource<T, M>(create));
    }

    public <T, M> Source<T, Future<M>> lazyFutureSource(Function0<Future<Source<T, M>>> create) {
        return this.lazySource((Function0<Source> & Serializable)() -> MODULE$.futureSource((Future)create.apply())).mapMaterializedValue((Function1<Future, Future> & Serializable)x$5 -> x$5.flatten($less$colon$less$.MODULE$.refl()));
    }

    public <T> Source<T, Subscriber<T>> asSubscriber() {
        return this.fromGraph(new SubscriberSource<T>(Stages$DefaultAttributes$.MODULE$.subscriberSource(), this.shape("SubscriberSource")));
    }

    public <T> Source<T, ActorRef> actorRef(PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher, int bufferSize, OverflowStrategy overflowStrategy) {
        Predef$.MODULE$.require(bufferSize >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "bufferSize must be greater than or equal to 0");
        Predef$.MODULE$.require(!overflowStrategy.isBackpressure(), (Function0<Object>)(Function0<String> & Serializable)() -> "Backpressure overflowStrategy not supported");
        return this.fromGraph(new ActorRefSource(bufferSize, overflowStrategy, completionMatcher, failureMatcher)).withAttributes(Stages$DefaultAttributes$.MODULE$.actorRefSource());
    }

    public <T> Source<T, ActorRef> actorRef(int bufferSize, OverflowStrategy overflowStrategy) {
        return this.actorRef((PartialFunction<Object, CompletionStrategy>)((Object)new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                Status.Success success = null;
                A1 A1 = x1;
                if (A1 instanceof Status.Success) {
                    bl = true;
                    success = (Status.Success)A1;
                    Object s2 = success.status();
                    if (s2 instanceof CompletionStrategy) {
                        CompletionStrategy completionStrategy = (CompletionStrategy)s2;
                        return (B1)completionStrategy;
                    }
                }
                if (bl) {
                    return (B1)CompletionStrategy$Draining$.MODULE$;
                }
                if (Status$Success$.MODULE$.equals(A1)) {
                    return (B1)CompletionStrategy$Draining$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                Status.Success success = null;
                Object object = x1;
                if (object instanceof Status.Success) {
                    bl = true;
                    success = (Status.Success)object;
                    Object s2 = success.status();
                    if (s2 instanceof CompletionStrategy) {
                        return true;
                    }
                }
                if (bl) {
                    return true;
                }
                return Status$Success$.MODULE$.equals(object);
            }
        }), (PartialFunction<Object, Throwable>)((Object)new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                A1 A1 = x2;
                if (A1 instanceof Status.Failure) {
                    Status.Failure failure = (Status.Failure)A1;
                    Throwable cause = failure.cause();
                    return (B1)cause;
                }
                return function1.apply(x2);
            }

            public final boolean isDefinedAt(Object x2) {
                Object object = x2;
                return object instanceof Status.Failure;
            }
        }), bufferSize, overflowStrategy);
    }

    @InternalApi
    public <T> Source<T, ActorRef> actorRefWithAck(Option<ActorRef> ackTo, Object ackMessage, PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher) {
        return this.fromGraph(new ActorRefBackpressureSource(ackTo, ackMessage, completionMatcher, failureMatcher));
    }

    public <T> Source<T, ActorRef> actorRefWithBackpressure(Object ackMessage, PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher) {
        return this.fromGraph(new ActorRefBackpressureSource(None$.MODULE$, ackMessage, completionMatcher, failureMatcher));
    }

    public <T> Source<T, ActorRef> actorRefWithAck(Object ackMessage) {
        return this.actorRefWithAck(None$.MODULE$, ackMessage, (PartialFunction<Object, CompletionStrategy>)((Object)new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                Status.Success success = null;
                A1 A1 = x1;
                if (A1 instanceof Status.Success) {
                    bl = true;
                    success = (Status.Success)A1;
                    Object s2 = success.status();
                    if (s2 instanceof CompletionStrategy) {
                        CompletionStrategy completionStrategy = (CompletionStrategy)s2;
                        return (B1)completionStrategy;
                    }
                }
                if (bl) {
                    return (B1)CompletionStrategy$Draining$.MODULE$;
                }
                if (Status$Success$.MODULE$.equals(A1)) {
                    return (B1)CompletionStrategy$Draining$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                Status.Success success = null;
                Object object = x1;
                if (object instanceof Status.Success) {
                    bl = true;
                    success = (Status.Success)object;
                    Object s2 = success.status();
                    if (s2 instanceof CompletionStrategy) {
                        return true;
                    }
                }
                if (bl) {
                    return true;
                }
                return Status$Success$.MODULE$.equals(object);
            }
        }), (PartialFunction<Object, Throwable>)((Object)new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                A1 A1 = x2;
                if (A1 instanceof Status.Failure) {
                    Status.Failure failure = (Status.Failure)A1;
                    Throwable cause = failure.cause();
                    return (B1)cause;
                }
                return function1.apply(x2);
            }

            public final boolean isDefinedAt(Object x2) {
                Object object = x2;
                return object instanceof Status.Failure;
            }
        }));
    }

    public <T, U> Source<U, NotUsed> combine(Source<T, ?> first, Source<T, ?> second, Seq<Source<T, ?>> rest, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        return this.fromGraph(GraphDSL$.MODULE$.create((Function1<GraphDSL.Builder, SourceShape> & Serializable)b -> {
            UniformFanInShape c = (UniformFanInShape)b.add((Graph)fanInStrategy.apply(BoxesRunTime.boxToInteger(rest.size() + 2)));
            new GraphDSL.Implicits.SourceArrow(GraphDSL$Implicits$.MODULE$.SourceArrow(first)).$tilde$greater(c.in(0), (GraphDSL.Builder<?>)b);
            new GraphDSL.Implicits.SourceArrow(GraphDSL$Implicits$.MODULE$.SourceArrow(second)).$tilde$greater(c.in(1), (GraphDSL.Builder<?>)b);
            return this.combineRest$1(2, rest.iterator(), c, (GraphDSL.Builder)b);
        }));
    }

    public <T, U, M> Source<U, Seq<M>> combine(Seq<Graph<SourceShape<T>, M>> sources, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        SeqOps seqOps;
        SeqOps seqOps2;
        Seq seq = sources;
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps2 = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2), 0) == 0) {
            return this.empty().mapMaterializedValue((Function1<NotUsed, Nil$> & Serializable)x$6 -> Nil$.MODULE$);
        }
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0) {
            Graph source2 = (Graph)SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0);
            return ((Source)source2).mapMaterializedValue((Function1<Object, List> & Serializable)x$7 -> {
                Object object = x$7;
                return Nil$.MODULE$.$colon$colon(object);
            });
        }
        return this.fromGraph(GraphDSL$.MODULE$.create(sources, (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<Seq, SourceShape> & Serializable)shapes -> {
            UniformFanInShape c = (UniformFanInShape)b.add((Graph)fanInStrategy.apply(BoxesRunTime.boxToInteger(sources.size())));
            ((IterableOps)shapes.zipWithIndex()).withFilter((Function1<Tuple2, Object> & Serializable)check$ifrefutable$1 -> BoxesRunTime.boxToBoolean(Source$.$anonfun$combine$6(check$ifrefutable$1))).foreach((Function1<Tuple2, Object> & Serializable)x$8 -> {
                Source$.$anonfun$combine$7(c, b, x$8);
                return BoxedUnit.UNIT;
            });
            return new SourceShape(c.out());
        }));
    }

    public <T, U, M1, M2, M> Source<U, M> combineMat(Source<T, M1> first, Source<T, M2> second, Function1<Object, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy, Function2<M1, M2, M> matF) {
        return this.fromGraph(GraphDSL$.MODULE$.createGraph(first, second, matF, (Function1<GraphDSL.Builder, Function2> & Serializable)b -> (Function2<SourceShape, SourceShape, SourceShape> & Serializable)(shape1, shape2) -> {
            UniformFanInShape c = (UniformFanInShape)b.add((Graph)fanInStrategy.apply(BoxesRunTime.boxToInteger(2)));
            new GraphDSL.Implicits.SourceShapeArrow(GraphDSL$Implicits$.MODULE$.SourceShapeArrow(shape1)).$tilde$greater(c.in(0), (GraphDSL.Builder<?>)b);
            new GraphDSL.Implicits.SourceShapeArrow(GraphDSL$Implicits$.MODULE$.SourceShapeArrow(shape2)).$tilde$greater(c.in(1), (GraphDSL.Builder<?>)b);
            return new SourceShape(c.out());
        }));
    }

    public <T> Source<Seq<T>, NotUsed> zipN(Seq<Source<T, ?>> sources) {
        return this.zipWithN(ConstantFun$.MODULE$.scalaIdentityFunction(), sources).addAttributes(Stages$DefaultAttributes$.MODULE$.zipN());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <T, O> Source<O, NotUsed> zipWithN(Function1<Seq<T>, O> zipper, Seq<Source<T, ?>> sources) {
        SeqOps seqOps;
        FlowOpsMat<Object, NotUsed> flowOpsMat;
        SeqOps seqOps2;
        Seq<Source<T, ?>> seq = sources;
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps2 = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2), 0) == 0) {
            flowOpsMat = this.empty();
        } else if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0) {
            Source source2 = (Source)SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0);
            flowOpsMat = ((Source)source2.map((Function1<Object, Object> & Serializable)t2 -> zipper.apply((Seq)Seq$.MODULE$.apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{t2}))))).mapMaterializedValue((Function1<Object, NotUsed$> & Serializable)x$9 -> NotUsed$.MODULE$);
        } else {
            Option option;
            Option option2;
            if (seq == null || (option2 = package$.MODULE$.$plus$colon().unapply(seq)).isEmpty()) throw new IllegalArgumentException();
            Source s1 = (Source)option2.get()._1();
            Seq<Source<T, ?>> seq2 = option2.get()._2();
            if (seq2 == null || (option = package$.MODULE$.$plus$colon().unapply(seq2)).isEmpty()) throw new IllegalArgumentException();
            Source s2 = (Source)option.get()._1();
            Seq<Source<T, ?>> ss = option.get()._2();
            flowOpsMat = this.combine(s1, s2, ss, (Function1<Object, ZipWithN> & Serializable)n -> ZipWithN$.MODULE$.apply(zipper, BoxesRunTime.unboxToInt(n)));
        }
        Source<T, NotUsed> source3 = flowOpsMat;
        return source3.addAttributes(Stages$DefaultAttributes$.MODULE$.zipWithN());
    }

    public <T> Source<T, BoundedSourceQueue<T>> queue(int bufferSize) {
        return this.fromGraph(new BoundedSourceQueueStage(bufferSize));
    }

    public <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy) {
        return this.queue(bufferSize, overflowStrategy, 1);
    }

    public <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy, int maxConcurrentOffers) {
        return this.fromGraph(new QueueSource(bufferSize, overflowStrategy, maxConcurrentOffers).withAttributes(Stages$DefaultAttributes$.MODULE$.queueSource()));
    }

    public <T, S> Source<T, NotUsed> unfoldResource(Function0<S> create, Function1<S, Option<T>> read, Function1<S, BoxedUnit> close) {
        return this.fromGraph(new UnfoldResourceSource<S, T>(create, read, close));
    }

    public <T, S> Source<T, NotUsed> unfoldResourceAsync(Function0<Future<S>> create, Function1<S, Future<Option<T>>> read, Function1<S, Future<Done>> close) {
        return this.fromGraph(new UnfoldResourceSourceAsync(create, read, close));
    }

    public <T> Source<T, NotUsed> mergePrioritizedN(Seq<Tuple2<Source<T, ?>, Object>> sourcesAndPriorities, boolean eagerComplete) {
        Tuple2 tuple2;
        SeqOps seqOps;
        SeqOps seqOps2;
        Seq<Tuple2<Source<T, ?>, Object>> seq = sourcesAndPriorities;
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps2 = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps2), 0) == 0) {
            return this.empty();
        }
        if (seq != null && !SeqFactory$UnapplySeqWrapper$.MODULE$.isEmpty$extension(seqOps = Seq$.MODULE$.unapplySeq(seq)) && new SeqFactory.UnapplySeqWrapper(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps)) != null && SeqFactory$UnapplySeqWrapper$.MODULE$.lengthCompare$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 1) == 0 && (tuple2 = (Tuple2)SeqFactory$UnapplySeqWrapper$.MODULE$.apply$extension(SeqFactory$UnapplySeqWrapper$.MODULE$.get$extension(seqOps), 0)) != null) {
            Source source2 = (Source)tuple2._1();
            return source2.mapMaterializedValue((Function1<Object, NotUsed$> & Serializable)x$10 -> NotUsed$.MODULE$);
        }
        Tuple2 tuple22 = seq.unzip(Predef$.MODULE$.$conforms());
        if (tuple22 == null) {
            throw new MatchError(tuple22);
        }
        Seq sources = (Seq)tuple22._1();
        Seq priorities = (Seq)tuple22._2();
        Tuple2<Seq, Seq> tuple23 = new Tuple2<Seq, Seq>(sources, priorities);
        Seq sources2 = tuple23._1();
        Seq priorities2 = tuple23._2();
        return this.combine((Source)sources2.head(), (Source)sources2.apply(true), (Seq)sources2.drop(2), (Function1<Object, GraphStage> & Serializable)x$12 -> MergePrioritized$.MODULE$.apply(priorities2, eagerComplete));
    }

    private final SourceShape combineRest$1(int idx, Iterator i, UniformFanInShape c$1, GraphDSL.Builder b$1) {
        while (i.hasNext()) {
            new GraphDSL.Implicits.SourceArrow(GraphDSL$Implicits$.MODULE$.SourceArrow((Graph)i.next())).$tilde$greater(c$1.in(idx), b$1);
            ++idx;
        }
        return new SourceShape(c$1.out());
    }

    public static final /* synthetic */ boolean $anonfun$combine$6(Tuple2 check$ifrefutable$1) {
        Tuple2 tuple2 = check$ifrefutable$1;
        return tuple2 != null;
    }

    public static final /* synthetic */ void $anonfun$combine$7(UniformFanInShape c$2, GraphDSL.Builder b$2, Tuple2 x$8) {
        Tuple2 tuple2 = x$8;
        if (tuple2 != null) {
            SourceShape shape = (SourceShape)tuple2._1();
            int i = tuple2._2$mcI$sp();
            new GraphDSL.Implicits.SourceShapeArrow(GraphDSL$Implicits$.MODULE$.SourceShapeArrow(shape)).$tilde$greater(c$2.in(i), b$2);
            return;
        }
        throw new MatchError(tuple2);
    }

    private Source$() {
    }
}

