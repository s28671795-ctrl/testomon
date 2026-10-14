/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Status;
import akka.actor.Status$Success$;
import akka.dispatch.ExecutionContexts$;
import akka.japi.JavaPartialFunction;
import akka.japi.JavaPartialFunction$;
import akka.japi.Pair;
import akka.japi.Util$;
import akka.japi.function.Creator;
import akka.japi.function.Function;
import akka.japi.function.Procedure;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.BoundedSourceQueue;
import akka.stream.CompletionStrategy;
import akka.stream.CompletionStrategy$Draining$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.SourceShape;
import akka.stream.UniformFanInShape;
import akka.stream.impl.UnfoldAsyncJava;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.SourceQueueWithComplete;
import akka.stream.javadsl.StreamConverters$;
import akka.stream.javadsl.package$;
import akka.stream.scaladsl.SourceQueueWithComplete$;
import akka.stream.scaladsl.SourceQueueWithComplete$QueueOps$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.stream.BaseStream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
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
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Either;

public final class Source$ {
    public static final Source$ MODULE$ = new Source$();
    private static final Source<Object, NotUsed> _empty = new Source(akka.stream.scaladsl.Source$.MODULE$.empty());

    public <O> Source<O, NotUsed> empty() {
        return _empty;
    }

    public <T> Source<T, NotUsed> empty(Class<T> clazz) {
        return this.empty();
    }

    public <T> Source<T, CompletableFuture<Optional<T>>> maybe() {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.maybe().mapMaterializedValue((Function1<Promise, CompletableFuture> & Serializable)scalaOptionPromise -> {
            CompletableFuture javaOptionPromise = new CompletableFuture();
            scalaOptionPromise.completeWith(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(javaOptionPromise)).map((Function1<Optional, Option> & Serializable)x$1 -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(x$1)), ExecutionContexts$.MODULE$.parasitic()));
            return javaOptionPromise;
        }));
    }

    public <O> Source<O, NotUsed> fromPublisher(Publisher<O> publisher) {
        return new Source<O, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.fromPublisher(publisher));
    }

    public <O> Source<O, NotUsed> fromIterator(Creator<java.util.Iterator<O>> f) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.fromIterator((Function0<Iterator> & Serializable)() -> package$JavaConverters$.MODULE$.IteratorHasAsScala((java.util.Iterator)f.create()).asScala()));
    }

    public <O, S extends BaseStream<O, S>> Source<O, NotUsed> fromJavaStream(Creator<BaseStream<O, S>> stream) {
        return StreamConverters$.MODULE$.fromJavaStream(stream);
    }

    public <O> Source<O, NotUsed> cycle(Creator<java.util.Iterator<O>> f) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.cycle((Function0<Iterator> & Serializable)() -> package$JavaConverters$.MODULE$.IteratorHasAsScala((java.util.Iterator)f.create()).asScala()));
    }

    public <O> Source<O, NotUsed> from(java.lang.Iterable<O> iterable) {
        scala.collection.immutable.Iterable scalaIterable = new scala.collection.immutable.Iterable<O>(iterable){
            private final java.lang.Iterable iterable$1;

            public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
                return scala.collection.immutable.Iterable.iterableFactory$(this);
            }

            public final Iterable<O> toIterable() {
                return Iterable.toIterable$(this);
            }

            public final Iterable<O> coll() {
                return Iterable.coll$(this);
            }

            public Iterable<O> seq() {
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

            public String toString() {
                return Iterable.toString$(this);
            }

            public <B> LazyZip2<O, B, Iterable> lazyZip(Iterable<B> that) {
                return Iterable.lazyZip$(this, that);
            }

            public IterableOps fromSpecific(IterableOnce coll) {
                return IterableFactoryDefaults.fromSpecific$(this, coll);
            }

            public Builder<O, scala.collection.immutable.Iterable<O>> newSpecificBuilder() {
                return IterableFactoryDefaults.newSpecificBuilder$(this);
            }

            public IterableOps empty() {
                return IterableFactoryDefaults.empty$(this);
            }

            public final Iterable<O> toTraversable() {
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

            public O head() {
                return (O)IterableOps.head$(this);
            }

            public Option<O> headOption() {
                return IterableOps.headOption$(this);
            }

            public O last() {
                return (O)IterableOps.last$(this);
            }

            public Option<O> lastOption() {
                return IterableOps.lastOption$(this);
            }

            public View<O> view() {
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

            public View<O> view(int from, int until) {
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

            public WithFilter<O, ?> withFilter(Function1<O, Object> p) {
                return IterableOps.withFilter$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<O>, scala.collection.immutable.Iterable<O>> partition(Function1<O, Object> p) {
                return IterableOps.partition$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<O>, scala.collection.immutable.Iterable<O>> splitAt(int n) {
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

            public Tuple2<scala.collection.immutable.Iterable<O>, scala.collection.immutable.Iterable<O>> span(Function1<O, Object> p) {
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

            public Iterator<scala.collection.immutable.Iterable<O>> grouped(int size) {
                return IterableOps.grouped$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<O>> sliding(int size) {
                return IterableOps.sliding$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<O>> sliding(int size, int step) {
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

            public <K> Map<K, scala.collection.immutable.Iterable<O>> groupBy(Function1<O, K> f) {
                return IterableOps.groupBy$(this, f);
            }

            public <K, B> Map<K, scala.collection.immutable.Iterable<B>> groupMap(Function1<O, K> key, Function1<O, B> f) {
                return IterableOps.groupMap$(this, key, f);
            }

            public <K, B> Map<K, B> groupMapReduce(Function1<O, K> key, Function1<O, B> f, Function2<B, B, B> reduce) {
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

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<O, Either<A1, A2>> f) {
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

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<O, Tuple2<A1, A2>> asPair) {
                return IterableOps.unzip$(this, asPair);
            }

            public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<O, Tuple3<A1, A2, A3>> asTriple) {
                return IterableOps.unzip3$(this, asTriple);
            }

            public Iterator<scala.collection.immutable.Iterable<O>> tails() {
                return IterableOps.tails$(this);
            }

            public Iterator<scala.collection.immutable.Iterable<O>> inits() {
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

            public <U> void foreach(Function1<O, U> f) {
                IterableOnceOps.foreach$(this, f);
            }

            public boolean forall(Function1<O, Object> p) {
                return IterableOnceOps.forall$(this, p);
            }

            public boolean exists(Function1<O, Object> p) {
                return IterableOnceOps.exists$(this, p);
            }

            public int count(Function1<O, Object> p) {
                return IterableOnceOps.count$(this, p);
            }

            public Option<O> find(Function1<O, Object> p) {
                return IterableOnceOps.find$(this, p);
            }

            public <B> B foldLeft(B z, Function2<B, O, B> op) {
                return (B)IterableOnceOps.foldLeft$(this, z, op);
            }

            public <B> B foldRight(B z, Function2<O, B, B> op) {
                return (B)IterableOnceOps.foldRight$(this, z, op);
            }

            public final <B> B $div$colon(B z, Function2<B, O, B> op) {
                return (B)IterableOnceOps.$div$colon$(this, z, op);
            }

            public final <B> B $colon$bslash(B z, Function2<O, B, B> op) {
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

            public <B> B reduceLeft(Function2<B, O, B> op) {
                return (B)IterableOnceOps.reduceLeft$(this, op);
            }

            public <B> B reduceRight(Function2<O, B, B> op) {
                return (B)IterableOnceOps.reduceRight$(this, op);
            }

            public <B> Option<B> reduceLeftOption(Function2<B, O, B> op) {
                return IterableOnceOps.reduceLeftOption$(this, op);
            }

            public <B> Option<B> reduceRightOption(Function2<O, B, B> op) {
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

            public <B> O min(Ordering<B> ord) {
                return (O)IterableOnceOps.min$(this, ord);
            }

            public <B> Option<O> minOption(Ordering<B> ord) {
                return IterableOnceOps.minOption$(this, ord);
            }

            public <B> O max(Ordering<B> ord) {
                return (O)IterableOnceOps.max$(this, ord);
            }

            public <B> Option<O> maxOption(Ordering<B> ord) {
                return IterableOnceOps.maxOption$(this, ord);
            }

            public <B> O maxBy(Function1<O, B> f, Ordering<B> cmp) {
                return (O)IterableOnceOps.maxBy$(this, f, cmp);
            }

            public <B> Option<O> maxByOption(Function1<O, B> f, Ordering<B> cmp) {
                return IterableOnceOps.maxByOption$(this, f, cmp);
            }

            public <B> O minBy(Function1<O, B> f, Ordering<B> cmp) {
                return (O)IterableOnceOps.minBy$(this, f, cmp);
            }

            public <B> Option<O> minByOption(Function1<O, B> f, Ordering<B> cmp) {
                return IterableOnceOps.minByOption$(this, f, cmp);
            }

            public <B> Option<B> collectFirst(PartialFunction<O, B> pf) {
                return IterableOnceOps.collectFirst$(this, pf);
            }

            public <B> B aggregate(Function0<B> z, Function2<B, O, B> seqop, Function2<B, B, B> combop) {
                return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
            }

            public <B> boolean corresponds(IterableOnce<B> that, Function2<O, B, Object> p) {
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

            public <C1> C1 to(Factory<O, C1> factory2) {
                return (C1)IterableOnceOps.to$(this, factory2);
            }

            public final Iterator<O> toIterator() {
                return IterableOnceOps.toIterator$(this);
            }

            public List<O> toList() {
                return IterableOnceOps.toList$(this);
            }

            public Vector<O> toVector() {
                return IterableOnceOps.toVector$(this);
            }

            public <K, V> Map<K, V> toMap($less$colon$less<O, Tuple2<K, V>> ev) {
                return IterableOnceOps.toMap$(this, ev);
            }

            public <B> Set<B> toSet() {
                return IterableOnceOps.toSet$(this);
            }

            public Seq<O> toSeq() {
                return IterableOnceOps.toSeq$(this);
            }

            public IndexedSeq<O> toIndexedSeq() {
                return IterableOnceOps.toIndexedSeq$(this);
            }

            public final Stream<O> toStream() {
                return IterableOnceOps.toStream$(this);
            }

            public final <B> Buffer<B> toBuffer() {
                return IterableOnceOps.toBuffer$(this);
            }

            public <B> Object toArray(ClassTag<B> evidence$2) {
                return IterableOnceOps.toArray$(this, evidence$2);
            }

            public Iterable<O> reversed() {
                return IterableOnceOps.reversed$(this);
            }

            public <S extends Stepper<?>> S stepper(StepperShape<O, S> shape) {
                return (S)IterableOnce.stepper$(this, shape);
            }

            public int knownSize() {
                return IterableOnce.knownSize$(this);
            }

            public Iterator<O> iterator() {
                return package$JavaConverters$.MODULE$.IteratorHasAsScala(this.iterable$1.iterator()).asScala();
            }
            {
                this.iterable$1 = iterable$1;
                IterableOnce.$init$(this);
                IterableOnceOps.$init$(this);
                IterableOps.$init$(this);
                IterableFactoryDefaults.$init$(this);
                Iterable.$init$(this);
                scala.collection.immutable.Iterable.$init$(this);
            }
        };
        return new Source(akka.stream.scaladsl.Source$.MODULE$.apply(scalaIterable));
    }

    public Source<Integer, NotUsed> range(int start2, int end) {
        return this.range(start2, end, 1);
    }

    public Source<Integer, NotUsed> range(int start2, int end, int step) {
        return new Source<Object, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.apply(scala.package$.MODULE$.Range().inclusive(start2, end, step)));
    }

    public <O> Source<O, NotUsed> fromFuture(Future<O> future) {
        return new Source<O, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.future(future));
    }

    public <O> Source<O, NotUsed> fromCompletionStage(CompletionStage<O> future) {
        return new Source<O, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.completionStage(future));
    }

    public <T, M> Source<T, Future<M>> fromFutureSource(Future<? extends Graph<SourceShape<T>, M>> future) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.fromFutureSource(future));
    }

    public <T, M> Source<T, CompletionStage<M>> fromSourceCompletionStage(CompletionStage<? extends Graph<SourceShape<T>, M>> completion) {
        return this.completionStageSource(completion.thenApply(g2 -> MODULE$.fromGraph((Graph)g2)));
    }

    public <O> Source<O, Cancellable> tick(Duration initialDelay, Duration interval, O tick) {
        return new Source<O, Cancellable>(akka.stream.scaladsl.Source$.MODULE$.tick(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), tick));
    }

    public <T> Source<T, NotUsed> single(T element) {
        return new Source<T, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.single(element));
    }

    public <T> Source<T, NotUsed> repeat(T element) {
        return new Source<T, NotUsed>(akka.stream.scaladsl.Source$.MODULE$.repeat(element));
    }

    public <S, E> Source<E, NotUsed> unfold(S s3, Function<S, Optional<Pair<S, E>>> f) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.unfold(s3, (Function1<Object, Option> & Serializable)(S s2) -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)f.apply(s2))).map((Function1<Pair, Tuple2> & Serializable)x$2 -> x$2.toScala())));
    }

    public <S, E> Source<E, NotUsed> unfoldAsync(S s2, Function<S, CompletionStage<Optional<Pair<S, E>>>> f) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.fromGraph(new UnfoldAsyncJava<S, E>(s2, f)));
    }

    public <T> Source<T, NotUsed> failed(Throwable cause) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.failed(cause));
    }

    public <T, M> Source<T, CompletionStage<M>> lazily(Creator<Source<T, M>> create) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.lazily((Function0<akka.stream.scaladsl.Source> & Serializable)() -> ((Source)create.create()).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$3 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$3)))).asJava();
    }

    public <T> Source<T, Future<NotUsed>> lazilyAsync(Creator<CompletionStage<T>> create) {
        return akka.stream.scaladsl.Source$.MODULE$.lazilyAsync((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)create.create()))).asJava();
    }

    public <T> Source<T, NotUsed> future(Future<T> futureElement) {
        return akka.stream.scaladsl.Source$.MODULE$.future(futureElement).asJava();
    }

    public <T> Source<T, NotUsed> never() {
        return akka.stream.scaladsl.Source$.MODULE$.never().asJava();
    }

    public <T> Source<T, NotUsed> completionStage(CompletionStage<T> completionStage) {
        return this.future(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(completionStage)));
    }

    public <T, M> Source<T, CompletionStage<M>> completionStageSource(CompletionStage<Source<T, M>> completionStageSource) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.futureSource(FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(completionStageSource)).map((Function1<Source, akka.stream.scaladsl.Source> & Serializable)x$4 -> x$4.asScala(), ExecutionContexts$.MODULE$.parasitic())).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$5 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$5)))).asJava();
    }

    public <T> Source<T, NotUsed> lazySingle(Creator<T> create) {
        return akka.stream.scaladsl.Source$.MODULE$.lazySingle((Function0<Object> & Serializable)() -> create.create()).asJava();
    }

    public <T> Source<T, NotUsed> lazyCompletionStage(Creator<CompletionStage<T>> create) {
        return akka.stream.scaladsl.Source$.MODULE$.lazyFuture((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)create.create()))).asJava();
    }

    public <T, M> Source<T, CompletionStage<M>> lazySource(Creator<Source<T, M>> create) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.lazySource((Function0<akka.stream.scaladsl.Source> & Serializable)() -> ((Source)create.create()).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$6 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$6)))).asJava();
    }

    public <T, M> Source<T, CompletionStage<M>> lazyCompletionStageSource(Creator<CompletionStage<Source<T, M>>> create) {
        return this.lazySource(() -> MODULE$.completionStageSource((CompletionStage)create.create())).mapMaterializedValue(x$7 -> x$7.thenCompose(csm -> csm));
    }

    public <T> Source<T, Subscriber<T>> asSubscriber() {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.asSubscriber());
    }

    public <T> Source<T, ActorRef> actorRef(Function<Object, Optional<CompletionStrategy>> completionMatcher, Function<Object, Optional<Throwable>> failureMatcher, int bufferSize, OverflowStrategy overflowStrategy) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.actorRef((PartialFunction<Object, CompletionStrategy>)new JavaPartialFunction<Object, CompletionStrategy>(completionMatcher){
            private final Function completionMatcher$1;

            public CompletionStrategy apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.completionMatcher$1.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (CompletionStrategy)result.get();
            }
            {
                this.completionMatcher$1 = completionMatcher$1;
            }
        }, (PartialFunction<Object, Throwable>)new JavaPartialFunction<Object, Throwable>(failureMatcher){
            private final Function failureMatcher$1;

            public Throwable apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.failureMatcher$1.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (Throwable)result.get();
            }
            {
                this.failureMatcher$1 = failureMatcher$1;
            }
        }, bufferSize, overflowStrategy));
    }

    @Deprecated
    public <T> Source<T, ActorRef> actorRef(int bufferSize, OverflowStrategy overflowStrategy) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.actorRef((PartialFunction<Object, CompletionStrategy>)((Object)new Serializable(){
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
        }), bufferSize, overflowStrategy));
    }

    public <T> Source<T, ActorRef> actorRefWithBackpressure(Object ackMessage, Function<Object, Optional<CompletionStrategy>> completionMatcher, Function<Object, Optional<Throwable>> failureMatcher) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.actorRefWithBackpressure(ackMessage, (PartialFunction<Object, CompletionStrategy>)new JavaPartialFunction<Object, CompletionStrategy>(completionMatcher){
            private final Function completionMatcher$2;

            public CompletionStrategy apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.completionMatcher$2.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (CompletionStrategy)result.get();
            }
            {
                this.completionMatcher$2 = completionMatcher$2;
            }
        }, (PartialFunction<Object, Throwable>)new JavaPartialFunction<Object, Throwable>(failureMatcher){
            private final Function failureMatcher$2;

            public Throwable apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.failureMatcher$2.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (Throwable)result.get();
            }
            {
                this.failureMatcher$2 = failureMatcher$2;
            }
        }));
    }

    @Deprecated
    public <T> Source<T, ActorRef> actorRefWithAck(Object ackMessage, Function<Object, Optional<CompletionStrategy>> completionMatcher, Function<Object, Optional<Throwable>> failureMatcher) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.actorRefWithBackpressure(ackMessage, (PartialFunction<Object, CompletionStrategy>)new JavaPartialFunction<Object, CompletionStrategy>(completionMatcher){
            private final Function completionMatcher$3;

            public CompletionStrategy apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.completionMatcher$3.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (CompletionStrategy)result.get();
            }
            {
                this.completionMatcher$3 = completionMatcher$3;
            }
        }, (PartialFunction<Object, Throwable>)new JavaPartialFunction<Object, Throwable>(failureMatcher){
            private final Function failureMatcher$3;

            public Throwable apply(Object x, boolean isCheck) {
                Optional result = (Optional)this.failureMatcher$3.apply(x);
                if (!result.isPresent()) {
                    throw JavaPartialFunction$.MODULE$.noMatch();
                }
                return (Throwable)result.get();
            }
            {
                this.failureMatcher$3 = failureMatcher$3;
            }
        }));
    }

    @Deprecated
    public <T> Source<T, ActorRef> actorRefWithAck(Object ackMessage) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.actorRefWithBackpressure(ackMessage, (PartialFunction<Object, CompletionStrategy>)((Object)new Serializable(){
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
        })));
    }

    public <T, M> Source<T, M> fromGraph(Graph<SourceShape<T>, M> g2) {
        Graph<SourceShape<T>, M> graph = g2;
        if (graph instanceof Source) {
            Source source2 = (Source)graph;
            return source2;
        }
        if (graph == akka.stream.scaladsl.Source$.MODULE$.empty()) {
            return this.empty();
        }
        return new Source<T, M>(akka.stream.scaladsl.Source$.MODULE$.fromGraph(graph));
    }

    public <T, M> Source<T, CompletionStage<M>> fromMaterializer(BiFunction<Materializer, Attributes, Source<T, M>> factory2) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.fromMaterializer((Function2<Materializer, Attributes, akka.stream.scaladsl.Source> & Serializable)(Materializer mat, Attributes attr) -> ((Source)factory2.apply((Materializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$8 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$8)))).asJava();
    }

    public <T, M> Source<T, CompletionStage<M>> setup(BiFunction<ActorMaterializer, Attributes, Source<T, M>> factory2) {
        return ((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.setup((Function2<ActorMaterializer, Attributes, akka.stream.scaladsl.Source> & Serializable)(ActorMaterializer mat, Attributes attr) -> ((Source)factory2.apply((ActorMaterializer)mat, (Attributes)attr)).asScala()).mapMaterializedValue((Function1<Future, CompletionStage> & Serializable)x$9 -> FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(x$9)))).asJava();
    }

    public <T, U> Source<U, NotUsed> combine(Source<T, ?> first, Source<T, ?> second, java.util.List<Source<T, ?>> rest, Function<Integer, ? extends Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        Seq seq = rest != null ? (Seq)Util$.MODULE$.immutableSeq(rest).map((Function1<Source, akka.stream.scaladsl.Source> & Serializable)x$10 -> x$10.asScala()) : (Seq)Nil$.MODULE$;
        return new Source(akka.stream.scaladsl.Source$.MODULE$.combine(first.asScala(), second.asScala(), seq, (Function1<Object, Graph> & Serializable)(Object num) -> Source$.$anonfun$combine$2(fanInStrategy, BoxesRunTime.unboxToInt(num))));
    }

    public <T, U, M1, M2, M> Source<U, M> combineMat(Source<T, M1> first, Source<T, M2> second, Function<Integer, ? extends Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy, akka.japi.function.Function2<M1, M2, M> matF) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.combineMat(first.asScala(), second.asScala(), (Function1<Object, Graph> & Serializable)(Object num) -> Source$.$anonfun$combineMat$1(fanInStrategy, BoxesRunTime.unboxToInt(num)), package$.MODULE$.combinerToScala(matF)));
    }

    public <T, U, M> Source<U, java.util.List<M>> combine(java.util.List<? extends Graph<SourceShape<T>, M>> sources, Function<Integer, Graph<UniformFanInShape<T, U>, NotUsed>> fanInStrategy) {
        Seq seq = sources != null ? (Seq)Util$.MODULE$.immutableSeq(sources).collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Graph, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Source) {
                    Source source2 = (Source)A1;
                    return (B1)source2.asScala();
                }
                return (B1)A1;
            }

            public final boolean isDefinedAt(Graph x1) {
                Graph graph = x1;
                if (graph instanceof Source) {
                    return true;
                }
                return true;
            }
        }) : (Seq)Nil$.MODULE$;
        return new Source(akka.stream.scaladsl.Source$.MODULE$.combine(seq, (Function1<Object, Graph> & Serializable)(Object size) -> Source$.$anonfun$combine$3(fanInStrategy, BoxesRunTime.unboxToInt(size))).mapMaterializedValue((Function1<Seq, java.util.List> & Serializable)x$11 -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$11).asJava()));
    }

    public <T> Source<java.util.List<T>, NotUsed> zipN(java.util.List<Source<T, ?>> sources) {
        Seq seq = sources != null ? (Seq)Util$.MODULE$.immutableSeq(sources).map((Function1<Source, akka.stream.scaladsl.Source> & Serializable)x$12 -> x$12.asScala()) : (Seq)Nil$.MODULE$;
        return new Source<java.util.List<T>, NotUsed>((akka.stream.scaladsl.Source)akka.stream.scaladsl.Source$.MODULE$.zipN(seq).map((Function1<Seq, java.util.List> & Serializable)x$13 -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$13).asJava()));
    }

    public <T, O> Source<O, NotUsed> zipWithN(Function<java.util.List<T>, O> zipper, java.util.List<Source<T, ?>> sources) {
        Seq seq2 = sources != null ? (Seq)Util$.MODULE$.immutableSeq(sources).map((Function1<Source, akka.stream.scaladsl.Source> & Serializable)x$14 -> x$14.asScala()) : (Seq)Nil$.MODULE$;
        return new Source(akka.stream.scaladsl.Source$.MODULE$.zipWithN((Function1<Seq, Object> & Serializable)(Seq<T> seq) -> zipper.apply(package$JavaConverters$.MODULE$.SeqHasAsJava(seq).asJava()), seq2));
    }

    public <T> Source<T, BoundedSourceQueue<T>> queue(int bufferSize) {
        return akka.stream.scaladsl.Source$.MODULE$.queue(bufferSize).asJava();
    }

    public <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.queue(bufferSize, overflowStrategy, 1).mapMaterializedValue((Function1<akka.stream.scaladsl.SourceQueueWithComplete, SourceQueueWithComplete> & Serializable)x$15 -> SourceQueueWithComplete$QueueOps$.MODULE$.asJava$extension(SourceQueueWithComplete$.MODULE$.QueueOps(x$15))));
    }

    public <T> Source<T, SourceQueueWithComplete<T>> queue(int bufferSize, OverflowStrategy overflowStrategy, int maxConcurrentOffers) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.queue(bufferSize, overflowStrategy, maxConcurrentOffers).mapMaterializedValue((Function1<akka.stream.scaladsl.SourceQueueWithComplete, SourceQueueWithComplete> & Serializable)x$16 -> SourceQueueWithComplete$QueueOps$.MODULE$.asJava$extension(SourceQueueWithComplete$.MODULE$.QueueOps(x$16))));
    }

    public <T, S> Source<T, NotUsed> unfoldResource(Creator<S> create, Function<S, Optional<T>> read, Procedure<S> close) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.unfoldResource((Function0<Object> & Serializable)() -> create.create(), (Function1<Object, Option> & Serializable)(S s2) -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)read.apply(s2))), (Function1<Object, Object> & Serializable)(S param2) -> {
            close.apply(param2);
            return BoxedUnit.UNIT;
        }));
    }

    public <T, S> Source<T, NotUsed> unfoldResourceAsync(Creator<CompletionStage<S>> create, Function<S, CompletionStage<Optional<T>>> read, Function<S, CompletionStage<Done>> close) {
        return new Source(akka.stream.scaladsl.Source$.MODULE$.unfoldResourceAsync((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)create.create())), (Function1<Object, Future> & Serializable)(S s2) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)read.apply(s2))).map((Function1<Optional, Option> & Serializable)x$17 -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(x$17)), ExecutionContexts$.MODULE$.parasitic()), (Function1<Object, Future> & Serializable)(S s2) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)close.apply(s2)))));
    }

    public <SuperOut, Out extends SuperOut, Mat> Source<SuperOut, Mat> upcast(Source<Out, Mat> source2) {
        return source2;
    }

    public <T> Source<T, NotUsed> mergePrioritizedN(java.util.List<Pair<Source<T, ?>, Integer>> sourcesAndPriorities, boolean eagerComplete) {
        Seq seq = sourcesAndPriorities != null ? (Seq)Util$.MODULE$.immutableSeq(sourcesAndPriorities).map((Function1<Pair, Tuple2> & Serializable)pair -> new Tuple2(((Source)pair.first()).asScala(), BoxesRunTime.boxToInteger((Integer)pair.second()))) : (Seq)Nil$.MODULE$;
        return new Source(akka.stream.scaladsl.Source$.MODULE$.mergePrioritizedN(seq, eagerComplete));
    }

    public static final /* synthetic */ Graph $anonfun$combine$2(Function fanInStrategy$1, int num) {
        return (Graph)fanInStrategy$1.apply(Predef$.MODULE$.int2Integer(num));
    }

    public static final /* synthetic */ Graph $anonfun$combineMat$1(Function fanInStrategy$2, int num) {
        return (Graph)fanInStrategy$2.apply(Predef$.MODULE$.int2Integer(num));
    }

    public static final /* synthetic */ Graph $anonfun$combine$3(Function fanInStrategy$3, int size) {
        return (Graph)fanInStrategy$3.apply(Predef$.MODULE$.int2Integer(size));
    }

    private Source$() {
    }
}

