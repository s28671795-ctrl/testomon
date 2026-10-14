/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.stream.Attributes;
import akka.stream.DelayOverflowStrategy;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.OverflowStrategy;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.SubstreamCancelStrategy;
import akka.stream.ThrottleMode;
import akka.stream.scaladsl.DelayStrategy;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.SubFlow;
import akka.util.Timeout;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001duA\u0002\n\u0014\u0011\u00039\u0012D\u0002\u0004\u001c'!\u0005q\u0003\b\u0005\u0006G\u0005!\t!\n\u0004\bM\u0005\u0001\n1%\u0001(\u0011\u0015I3A\"\u0001+\r\u0015Y2\u0003A\fZ\u0011!aWA!b\u0001\n\u0003i\u0007\u0002C9\u0006\u0005\u0003\u0005\u000b\u0011\u00028\t\u0011I,!\u0011!Q\u0001\nMD\u0001\"^\u0003\u0003\u0002\u0003\u0006IA\u001e\u0005\u0006G\u0015!\t\u0001 \u0005\b\u0003\u0007)A\u0011IA\u0003\u0011\u001d\ti#\u0002C!\u0003_Aq!a\u000f\u0006\t\u0003\ni\u0004C\u0004\u0002B\u0015!\t%a\u0011\t\u000f\u0005}S\u0001\"\u0011\u0002b!9\u0011QM\u0003\u0005B\u0005\u001d\u0004bBA7\u000b\u0011\u0005\u0011qN\u0001\f'V\u0014g\t\\8x\u00136\u0004HN\u0003\u0002\u0015+\u0005!\u0011.\u001c9m\u0015\t1r#\u0001\u0004tiJ,\u0017-\u001c\u0006\u00021\u0005!\u0011m[6b!\tQ\u0012!D\u0001\u0014\u0005-\u0019VO\u0019$m_^LU\u000e\u001d7\u0014\u0005\u0005i\u0002C\u0001\u0010\"\u001b\u0005y\"\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\tz\"AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005I\"!C'fe\u001e,')Y2l+\rAcIL\n\u0003\u0007u\tQ!\u00199qYf,\"aK\u001e\u0015\u00071jD\nE\u0002.]ib\u0001\u0001B\u00030\u0007\t\u0007\u0001GA\u0001G+\t\t\u0004(\u0005\u00023kA\u0011adM\u0005\u0003i}\u0011qAT8uQ&tw\r\u0005\u0002\u001fm%\u0011qg\b\u0002\u0004\u0003:LHAB\u001d/\t\u000b\u0007\u0011G\u0001\u0003`I\u0011\n\u0004CA\u0017<\t\u0015aDA1\u00012\u0005\u0005!\u0006\"\u0002 \u0005\u0001\u0004y\u0014!\u00014\u0011\u000b\u0001\u001bUI\u000f%\u000e\u0003\u0005S!AQ\u000b\u0002\u0011M\u001c\u0017\r\\1eg2L!\u0001R!\u0003\t\u0019cwn\u001e\t\u0003[\u0019#QaR\u0002C\u0002E\u0012!!\u00138\u0011\u0005%SU\"A\f\n\u0005-;\"a\u0002(piV\u001bX\r\u001a\u0005\u0006\u001b\u0012\u0001\rAT\u0001\bEJ,\u0017\r\u001a;i!\tqr*\u0003\u0002Q?\t\u0019\u0011J\u001c;)\u0005\u0005\u0011\u0006CA*W\u001b\u0005!&BA+\u0018\u0003)\tgN\\8uCRLwN\\\u0005\u0003/R\u00131\"\u00138uKJt\u0017\r\\!qS\"\u0012\u0001AU\u000b\u00075B|&-\u001a6\u0014\u0007\u0015i2\f\u0005\u0004A9z\u000bG-[\u0005\u0003;\u0006\u0013qaU;c\r2|w\u000f\u0005\u0002.?\u0012)\u0001-\u0002b\u0001c\t\u0019q*\u001e;\u0011\u00055\u0012G!B2\u0006\u0005\u0004\t$aA'biB\u0011Q&\u001a\u0003\u0006_\u0015\u0011\rAZ\u000b\u0003c\u001d$a\u0001[3\u0005\u0006\u0004\t$\u0001B0%II\u0002\"!\f6\u0005\u000b-,!\u0019A\u0019\u0003\u0003\r\u000bqa];c\r2|w/F\u0001o!\u0015\u00015i\u001c0I!\ti\u0003\u000fB\u0003H\u000b\t\u0007\u0011'\u0001\u0005tk\n4En\\<!\u0003EiWM]4f\u0005\u0006\u001c7NR;oGRLwN\u001c\t\u0005i\u000eyGM\u0004\u0002\u001b\u0001\u0005qa-\u001b8jg\"4UO\\2uS>t\u0007\u0003\u0002\u0010xs&L!\u0001_\u0010\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003\u0002!{_\"K!a_!\u0003\tMKgn\u001b\u000b\u0006{z|\u0018\u0011\u0001\t\b5\u0015yg,\u00193j\u0011\u0015a'\u00021\u0001o\u0011\u0015\u0011(\u00021\u0001t\u0011\u0015)(\u00021\u0001w\u0003\r1\u0018.Y\u000b\u0007\u0003\u000f\t\u0019\"!\u000b\u0015\t\u0005%\u0011Q\u0003\t\u0007\u0003\u0017\ti!!\u0005\u000e\u0003\u0015I1!a\u0004]\u0005\u0011\u0011V\r\u001d:\u0011\u00075\n\u0019\u0002B\u0003=\u0017\t\u0007\u0011\u0007C\u0004\u0002\u0018-\u0001\r!!\u0007\u0002\t\u0019dwn\u001e\t\t\u00037\ti\"!\t\u0002(5\tQ#C\u0002\u0002 U\u0011Qa\u0012:ba\"\u0004r!a\u0007\u0002$y\u000b\t\"C\u0002\u0002&U\u0011\u0011B\u00127poNC\u0017\r]3\u0011\u00075\nI\u0003\u0002\u0004\u0002,-\u0011\r!\r\u0002\u0005\u001b\u0006$('\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0007m\u000b\t\u0004C\u0004\u000241\u0001\r!!\u000e\u0002\t\u0005$HO\u001d\t\u0005\u00037\t9$C\u0002\u0002:U\u0011!\"\u0011;ue&\u0014W\u000f^3t\u00035\tG\rZ!uiJL'-\u001e;fgR\u00191,a\u0010\t\u000f\u0005MR\u00021\u0001\u00026\u0005)a.Y7fIR\u00191,!\u0012\t\u000f\u0005\u001dc\u00021\u0001\u0002J\u0005!a.Y7f!\u0011\tY%!\u0017\u000f\t\u00055\u0013Q\u000b\t\u0004\u0003\u001fzRBAA)\u0015\r\t\u0019\u0006J\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005]s$\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u00037\niF\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003/z\u0012!B1ts:\u001cWCAA2!\u0015\tY!!\u0004_\u0003yiWM]4f'V\u00147\u000f\u001e:fC6\u001cx+\u001b;i!\u0006\u0014\u0018\r\u001c7fY&\u001cX\u000e\u0006\u0003\u0002j\u0005-\u0004cA\u0017f=\")Q\n\u0005a\u0001\u001d\u0006\u0011Ao\\\u000b\u0005\u0003c\n\t\tF\u0002j\u0003gBq!!\u001e\u0012\u0001\u0004\t9(\u0001\u0003tS:\\\u0007\u0003CA\u000e\u0003;\tI(a \u0011\u000b\u0005m\u00111\u00100\n\u0007\u0005uTCA\u0005TS:\\7\u000b[1qKB\u0019Q&!!\u0005\r\u0005\r\u0015C1\u00012\u0005\u0005i\u0005FA\u0003S\u0001")
@InternalApi
public class SubFlowImpl<In, Out, Mat, F, C>
implements SubFlow<Out, Mat, F, C> {
    private final Flow<In, Out, NotUsed> subFlow;
    private final MergeBack<In, F> mergeBackFunction;
    private final Function1<Sink<In, NotUsed>, C> finishFunction;

    @Override
    public F mergeSubstreams() {
        return (F)SubFlow.mergeSubstreams$(this);
    }

    @Override
    public F concatSubstreams() {
        return (F)SubFlow.concatSubstreams$(this);
    }

    @Override
    public <T> FlowOps recover(PartialFunction<Throwable, T> pf) {
        return FlowOps.recover$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWith(PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWith$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWithRetries(int attempts, PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWithRetries$(this, attempts, pf);
    }

    @Override
    public FlowOps mapError(PartialFunction<Throwable, Throwable> pf) {
        return FlowOps.mapError$(this, pf);
    }

    @Override
    public <T> FlowOps map(Function1<Out, T> f) {
        return FlowOps.map$(this, f);
    }

    @Override
    public FlowOps wireTap(Function1<Out, BoxedUnit> f) {
        return FlowOps.wireTap$((FlowOps)this, f);
    }

    @Override
    public <T> FlowOps mapConcat(Function1<Out, IterableOnce<T>> f) {
        return FlowOps.mapConcat$(this, f);
    }

    @Override
    public <S, T> FlowOps statefulMap(Function0<S> create, Function2<S, Out, Tuple2<S, T>> f, Function1<S, Option<T>> onComplete) {
        return FlowOps.statefulMap$(this, create, f, onComplete);
    }

    @Override
    public <R, T> FlowOps mapWithResource(Function0<R> create, Function2<R, Out, T> f, Function1<R, Option<T>> close) {
        return FlowOps.mapWithResource$(this, create, f, close);
    }

    @Override
    public <T> FlowOps statefulMapConcat(Function0<Function1<Out, IterableOnce<T>>> f) {
        return FlowOps.statefulMapConcat$(this, f);
    }

    @Override
    public <T> FlowOps mapAsync(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsync$(this, parallelism, f);
    }

    @Override
    public <T, P> FlowOps mapAsyncPartitioned(int parallelism, int perPartition, Function1<Out, P> partitioner, Function2<Out, P, Future<T>> f) {
        return FlowOps.mapAsyncPartitioned$(this, parallelism, perPartition, partitioner, f);
    }

    @Override
    public <T> FlowOps mapAsyncUnordered(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsyncUnordered$(this, parallelism, f);
    }

    @Override
    public <S> FlowOps ask(ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, ref, timeout2, tag);
    }

    @Override
    public <S> FlowOps ask(int parallelism, ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, parallelism, ref, timeout2, tag);
    }

    @Override
    public FlowOps watch(ActorRef ref) {
        return FlowOps.watch$(this, ref);
    }

    @Override
    public FlowOps filter(Function1<Out, Object> p) {
        return FlowOps.filter$(this, p);
    }

    @Override
    public FlowOps filterNot(Function1<Out, Object> p) {
        return FlowOps.filterNot$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p) {
        return FlowOps.takeWhile$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p, boolean inclusive) {
        return FlowOps.takeWhile$(this, p, inclusive);
    }

    @Override
    public FlowOps dropWhile(Function1<Out, Object> p) {
        return FlowOps.dropWhile$(this, p);
    }

    @Override
    public <T> FlowOps collect(PartialFunction<Out, T> pf) {
        return FlowOps.collect$(this, pf);
    }

    @Override
    public <T> FlowOps collectType(ClassTag<T> tag) {
        return FlowOps.collectType$(this, tag);
    }

    @Override
    public FlowOps grouped(int n) {
        return FlowOps.grouped$(this, n);
    }

    @Override
    public FlowOps groupedWeighted(long minWeight, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeighted$(this, minWeight, costFn);
    }

    @Override
    public FlowOps limit(long max) {
        return FlowOps.limit$(this, max);
    }

    @Override
    public <T> FlowOps limitWeighted(long max, Function1<Out, Object> costFn) {
        return FlowOps.limitWeighted$(this, max, costFn);
    }

    @Override
    public FlowOps sliding(int n, int step) {
        return FlowOps.sliding$(this, n, step);
    }

    @Override
    public int sliding$default$2() {
        return FlowOps.sliding$default$2$(this);
    }

    @Override
    public <T> FlowOps scan(T zero, Function2<T, Out, T> f) {
        return FlowOps.scan$(this, zero, f);
    }

    @Override
    public <T> FlowOps scanAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.scanAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps fold(T zero, Function2<T, Out, T> f) {
        return FlowOps.fold$(this, zero, f);
    }

    @Override
    public <T> FlowOps foldAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.foldAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps reduce(Function2<T, T, T> f) {
        return FlowOps.reduce$(this, f);
    }

    @Override
    public <T> FlowOps intersperse(T start2, T inject, T end) {
        return FlowOps.intersperse$(this, start2, inject, end);
    }

    @Override
    public <T> FlowOps intersperse(T inject) {
        return FlowOps.intersperse$(this, inject);
    }

    @Override
    public FlowOps groupedWithin(int n, FiniteDuration d) {
        return FlowOps.groupedWithin$(this, n, d);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, d, costFn);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, int maxNumber, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, maxNumber, d, costFn);
    }

    @Override
    public FlowOps delay(FiniteDuration of, DelayOverflowStrategy strategy) {
        return FlowOps.delay$(this, of, strategy);
    }

    @Override
    public DelayOverflowStrategy delay$default$2() {
        return FlowOps.delay$default$2$(this);
    }

    @Override
    public FlowOps delayWith(Function0<DelayStrategy<Out>> delayStrategySupplier, DelayOverflowStrategy overFlowStrategy) {
        return FlowOps.delayWith$(this, delayStrategySupplier, overFlowStrategy);
    }

    @Override
    public FlowOps drop(long n) {
        return FlowOps.drop$(this, n);
    }

    @Override
    public FlowOps dropWithin(FiniteDuration d) {
        return FlowOps.dropWithin$(this, d);
    }

    @Override
    public FlowOps take(long n) {
        return FlowOps.take$(this, n);
    }

    @Override
    public FlowOps takeWithin(FiniteDuration d) {
        return FlowOps.takeWithin$(this, d);
    }

    @Override
    public <S> FlowOps conflateWithSeed(Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.conflateWithSeed$(this, seed, aggregate);
    }

    @Override
    public <O2> FlowOps conflate(Function2<O2, O2, O2> aggregate) {
        return FlowOps.conflate$(this, aggregate);
    }

    @Override
    public <S> FlowOps batch(long max, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batch$(this, max, seed, aggregate);
    }

    @Override
    public <S> FlowOps batchWeighted(long max, Function1<Out, Object> costFn, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batchWeighted$(this, max, costFn, seed, aggregate);
    }

    @Override
    public <U> FlowOps expand(Function1<Out, Iterator<U>> expander) {
        return FlowOps.expand$(this, expander);
    }

    @Override
    public <U> FlowOps extrapolate(Function1<U, Iterator<U>> extrapolator, Option<U> initial) {
        return FlowOps.extrapolate$(this, extrapolator, initial);
    }

    @Override
    public <U> None$ extrapolate$default$2() {
        return FlowOps.extrapolate$default$2$(this);
    }

    @Override
    public FlowOps buffer(int size, OverflowStrategy overflowStrategy) {
        return FlowOps.buffer$(this, size, overflowStrategy);
    }

    @Override
    public <U> FlowOps prefixAndTail(int n) {
        return FlowOps.prefixAndTail$(this, n);
    }

    @Override
    public <Out2, Mat2> FlowOps flatMapPrefix(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f) {
        return FlowOps.flatMapPrefix$(this, n, f);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f, boolean allowClosedSubstreamRecreation) {
        return FlowOps.groupBy$(this, maxSubstreams, f, allowClosedSubstreamRecreation);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f) {
        return FlowOps.groupBy$(this, maxSubstreams, f);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, p);
    }

    @Override
    public <T, M> FlowOps flatMapConcat(Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapConcat$(this, f);
    }

    @Override
    public <T, M> FlowOps flatMapMerge(int breadth, Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapMerge$(this, breadth, f);
    }

    @Override
    public FlowOps initialTimeout(FiniteDuration timeout2) {
        return FlowOps.initialTimeout$(this, timeout2);
    }

    @Override
    public FlowOps completionTimeout(FiniteDuration timeout2) {
        return FlowOps.completionTimeout$(this, timeout2);
    }

    @Override
    public FlowOps idleTimeout(FiniteDuration timeout2) {
        return FlowOps.idleTimeout$(this, timeout2);
    }

    @Override
    public FlowOps backpressureTimeout(FiniteDuration timeout2) {
        return FlowOps.backpressureTimeout$(this, timeout2);
    }

    @Override
    public <U> FlowOps keepAlive(FiniteDuration maxIdle, Function0<U> injectedElem) {
        return FlowOps.keepAlive$(this, maxIdle, injectedElem);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per) {
        return FlowOps.throttle$(this, elements, per);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return FlowOps.throttle$(this, elements, per, maximumBurst, mode);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, Function1<Out, Object> costCalculation) {
        return FlowOps.throttle$(this, cost, per, costCalculation);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, int maximumBurst, Function1<Out, Object> costCalculation, ThrottleMode mode) {
        return FlowOps.throttle$(this, cost, per, maximumBurst, costCalculation, mode);
    }

    @Override
    public FlowOps detach() {
        return FlowOps.detach$(this);
    }

    @Override
    public FlowOps initialDelay(FiniteDuration delay) {
        return FlowOps.initialDelay$(this, delay);
    }

    @Override
    public FlowOps log(String name, Function1<Out, Object> extract, LoggingAdapter log) {
        return FlowOps.log$(this, name, extract, log);
    }

    @Override
    public Function1<Out, Object> log$default$2() {
        return FlowOps.log$default$2$(this);
    }

    @Override
    public LoggingAdapter log$default$3(String name, Function1<Out, Object> extract) {
        return FlowOps.log$default$3$(this, name, extract);
    }

    @Override
    public FlowOps logWithMarker(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        return FlowOps.logWithMarker$(this, name, marker, extract, log);
    }

    @Override
    public Function1<Out, Object> logWithMarker$default$3() {
        return FlowOps.logWithMarker$default$3$(this);
    }

    @Override
    public MarkerLoggingAdapter logWithMarker$default$4(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract) {
        return FlowOps.logWithMarker$default$4$(this, name, marker, extract);
    }

    @Override
    public <U> FlowOps zip(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zip$(this, that);
    }

    @Override
    public <U, A> FlowOps zipAll(Graph<SourceShape<U>, ?> that, A thisElem, U thatElem) {
        return FlowOps.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, A, Mat2> Flow<Out, Tuple2<A, U>, Mat2> zipAllFlow(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem) {
        return FlowOps.zipAllFlow$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipGraph$(this, that);
    }

    @Override
    public <U> FlowOps zipLatest(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zipLatest$(this, that);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipLatestGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipLatestGraph$(this, that);
    }

    @Override
    public <Out2, Out3> FlowOps zipWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, eagerComplete, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, eagerComplete, combine);
    }

    @Override
    public FlowOps zipWithIndex() {
        return FlowOps.zipWithIndex$(this);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize) {
        return FlowOps.interleave$(this, that, segmentSize);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleave$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> interleaveGraph(Graph<SourceShape<U>, M> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveGraph$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> boolean interleaveGraph$default$3() {
        return FlowOps.interleaveGraph$default$3$(this);
    }

    @Override
    public <U> FlowOps interleaveAll(Seq<Graph<SourceShape<U>, ?>> those, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveAll$(this, those, segmentSize, eagerClose);
    }

    @Override
    public <U, M> FlowOps merge(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.merge$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean merge$default$2() {
        return FlowOps.merge$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeGraph$(this, that, eagerComplete);
    }

    @Override
    public <U> FlowOps mergeAll(Seq<Graph<SourceShape<U>, ?>> those, boolean eagerComplete) {
        return FlowOps.mergeAll$(this, those, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeLatest(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatest$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean mergeLatest$default$2() {
        return FlowOps.mergeLatest$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Seq<U>>, M> mergeLatestGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatestGraph$(this, that, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePreferred(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferred$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePreferred$default$3() {
        return FlowOps.mergePreferred$default$3$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePreferredGraph(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferredGraph$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePrioritized(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritized$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePrioritized$default$4() {
        return FlowOps.mergePrioritized$default$4$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePrioritizedGraph(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritizedGraph$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeSorted(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSorted$(this, that, ord);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeSortedGraph(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSortedGraph$(this, that, ord);
    }

    @Override
    public <U, Mat2> FlowOps concat(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concat$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> concatGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.concatGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps concatLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concatLazy$(this, that);
    }

    @Override
    public <U> FlowOps concatAllLazy(Seq<Graph<SourceShape<U>, ?>> those) {
        return FlowOps.concatAllLazy$(this, those);
    }

    @Override
    public <U, Mat2> FlowOps prepend(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prepend$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> prependGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.prependGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps prependLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prependLazy$(this, that);
    }

    @Override
    public <U, Mat2> FlowOps orElse(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElse$(this, secondary);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> orElseGraph(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElseGraph$(this, secondary);
    }

    @Override
    public <U, M> FlowOps $plus$plus(Graph<SourceShape<U>, M> that) {
        return FlowOps.$plus$plus$(this, that);
    }

    @Override
    public FlowOps alsoTo(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.alsoTo$(this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> alsoToGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.alsoToGraph$(this, that);
    }

    @Override
    public FlowOps alsoToAll(Seq<Graph<SinkShape<Out>, ?>> those) {
        return FlowOps.alsoToAll$(this, those);
    }

    @Override
    public FlowOps divertTo(Graph<SinkShape<Out>, ?> that, Function1<Out, Object> when) {
        return FlowOps.divertTo$(this, that, when);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> divertToGraph(Graph<SinkShape<Out>, M> that, Function1<Out, Object> when) {
        return FlowOps.divertToGraph$(this, that, when);
    }

    @Override
    public FlowOps wireTap(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.wireTap$((FlowOps)this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> wireTapGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.wireTapGraph$(this, that);
    }

    @Override
    @ApiMayChange
    public <Agg, Emit> FlowOps aggregateWithBoundary(Function0<Agg> allocate, Function2<Agg, Out, Tuple2<Agg, Object>> aggregate, Function1<Agg, Emit> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return FlowOps.aggregateWithBoundary$(this, allocate, aggregate, harvest, emitOnTimer);
    }

    public Flow<In, Out, NotUsed> subFlow() {
        return this.subFlow;
    }

    @Override
    public <T, Mat2> SubFlow<T, Mat, F, C> via(Graph<FlowShape<Out, T>, Mat2> flow) {
        return new SubFlowImpl<In, Out, Mat, F, C>(this.subFlow().via((Graph)flow), this.mergeBackFunction, this.finishFunction);
    }

    @Override
    public SubFlow<Out, Mat, F, C> withAttributes(Attributes attr) {
        return new SubFlowImpl<In, Out, Mat, F, C>(this.subFlow().withAttributes(attr), this.mergeBackFunction, this.finishFunction);
    }

    @Override
    public SubFlow<Out, Mat, F, C> addAttributes(Attributes attr) {
        return new SubFlowImpl<In, Out, Mat, F, C>(this.subFlow().addAttributes(attr), this.mergeBackFunction, this.finishFunction);
    }

    @Override
    public SubFlow<Out, Mat, F, C> named(String name) {
        return new SubFlowImpl<In, Out, Mat, F, C>(this.subFlow().named(name), this.mergeBackFunction, this.finishFunction);
    }

    @Override
    public SubFlow<Out, Mat, F, C> async() {
        return new SubFlowImpl<In, Out, Mat, F, C>(this.subFlow().async(), this.mergeBackFunction, this.finishFunction);
    }

    @Override
    public F mergeSubstreamsWithParallelism(int breadth) {
        return this.mergeBackFunction.apply(this.subFlow(), breadth);
    }

    @Override
    public <M> C to(Graph<SinkShape<Out>, M> sink2) {
        return this.finishFunction.apply((Sink<In, NotUsed>)this.subFlow().to(sink2));
    }

    public SubFlowImpl(Flow<In, Out, NotUsed> subFlow, MergeBack<In, F> mergeBackFunction, Function1<Sink<In, NotUsed>, C> finishFunction) {
        this.subFlow = subFlow;
        this.mergeBackFunction = mergeBackFunction;
        this.finishFunction = finishFunction;
        FlowOps.$init$(this);
        SubFlow.$init$(this);
    }

    public static interface MergeBack<In, F> {
        public <T> F apply(Flow<In, T, NotUsed> var1, int var2);
    }
}

