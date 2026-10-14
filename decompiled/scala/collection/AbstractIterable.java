/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
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
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u00012QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u0011\u0001#\u00112tiJ\f7\r^%uKJ\f'\r\\3\u000b\u0005\u0011)\u0011AC2pY2,7\r^5p]*\ta!A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005%!2c\u0001\u0001\u000b\u001dA\u00111\u0002D\u0007\u0002\u000b%\u0011Q\"\u0002\u0002\u0007\u0003:L(+\u001a4\u0011\u0007=\u0001\"#D\u0001\u0004\u0013\t\t2A\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\t\u0019B\u0003\u0004\u0001\u0005\rU\u0001AQ1\u0001\u0017\u0005\u0005\t\u0015CA\f\u001b!\tY\u0001$\u0003\u0002\u001a\u000b\t9aj\u001c;iS:<\u0007CA\u0006\u001c\u0013\taRAA\u0002B]f\fa\u0001P5oSRtD#A\u0010\u0011\u0007=\u0001!\u0003")
public abstract class AbstractIterable<A>
implements Iterable<A> {
    @Override
    public final Iterable<A> toIterable() {
        return Iterable.toIterable$(this);
    }

    @Override
    public final Iterable<A> coll() {
        return Iterable.coll$(this);
    }

    @Override
    public IterableFactory<Iterable> iterableFactory() {
        return Iterable.iterableFactory$(this);
    }

    @Override
    public Iterable<A> seq() {
        return Iterable.seq$(this);
    }

    @Override
    public String className() {
        return Iterable.className$(this);
    }

    @Override
    public final String collectionClassName() {
        return Iterable.collectionClassName$(this);
    }

    @Override
    public String stringPrefix() {
        return Iterable.stringPrefix$(this);
    }

    @Override
    public String toString() {
        return Iterable.toString$(this);
    }

    @Override
    public <B> LazyZip2<A, B, Iterable> lazyZip(Iterable<B> that) {
        return Iterable.lazyZip$(this, that);
    }

    @Override
    public IterableOps fromSpecific(IterableOnce coll) {
        return IterableFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<A, Iterable<A>> newSpecificBuilder() {
        return IterableFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public IterableOps empty() {
        return IterableFactoryDefaults.empty$(this);
    }

    @Override
    public final Iterable<A> toTraversable() {
        return IterableOps.toTraversable$(this);
    }

    @Override
    public boolean isTraversableAgain() {
        return IterableOps.isTraversableAgain$(this);
    }

    @Override
    public final Object repr() {
        return IterableOps.repr$(this);
    }

    @Override
    public IterableFactory<Iterable> companion() {
        return IterableOps.companion$(this);
    }

    @Override
    public A head() {
        return (A)IterableOps.head$(this);
    }

    @Override
    public Option<A> headOption() {
        return IterableOps.headOption$(this);
    }

    @Override
    public A last() {
        return (A)IterableOps.last$(this);
    }

    @Override
    public Option<A> lastOption() {
        return IterableOps.lastOption$(this);
    }

    @Override
    public View<A> view() {
        return IterableOps.view$(this);
    }

    @Override
    public int sizeCompare(int otherSize) {
        return IterableOps.sizeCompare$((IterableOps)this, otherSize);
    }

    @Override
    public final IterableOps sizeIs() {
        return IterableOps.sizeIs$(this);
    }

    @Override
    public int sizeCompare(Iterable<?> that) {
        return IterableOps.sizeCompare$((IterableOps)this, that);
    }

    @Override
    public View<A> view(int from, int until) {
        return IterableOps.view$(this, from, until);
    }

    @Override
    public Object transpose(Function1 asIterable) {
        return IterableOps.transpose$(this, asIterable);
    }

    @Override
    public Object filter(Function1 pred) {
        return IterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return IterableOps.filterNot$(this, pred);
    }

    @Override
    public WithFilter<A, Iterable> withFilter(Function1<A, Object> p) {
        return IterableOps.withFilter$(this, p);
    }

    @Override
    public Tuple2<Iterable<A>, Iterable<A>> partition(Function1<A, Object> p) {
        return IterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<Iterable<A>, Iterable<A>> splitAt(int n) {
        return IterableOps.splitAt$(this, n);
    }

    @Override
    public Object take(int n) {
        return IterableOps.take$(this, n);
    }

    @Override
    public Object takeRight(int n) {
        return IterableOps.takeRight$(this, n);
    }

    @Override
    public Object takeWhile(Function1 p) {
        return IterableOps.takeWhile$(this, p);
    }

    @Override
    public Tuple2<Iterable<A>, Iterable<A>> span(Function1<A, Object> p) {
        return IterableOps.span$(this, p);
    }

    @Override
    public Object drop(int n) {
        return IterableOps.drop$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return IterableOps.dropRight$(this, n);
    }

    @Override
    public Object dropWhile(Function1 p) {
        return IterableOps.dropWhile$(this, p);
    }

    @Override
    public Iterator<Iterable<A>> grouped(int size) {
        return IterableOps.grouped$(this, size);
    }

    @Override
    public Iterator<Iterable<A>> sliding(int size) {
        return IterableOps.sliding$(this, size);
    }

    @Override
    public Iterator<Iterable<A>> sliding(int size, int step) {
        return IterableOps.sliding$(this, size, step);
    }

    @Override
    public Object tail() {
        return IterableOps.tail$(this);
    }

    @Override
    public Object init() {
        return IterableOps.init$(this);
    }

    @Override
    public Object slice(int from, int until) {
        return IterableOps.slice$(this, from, until);
    }

    @Override
    public <K$> Map<K$, Iterable<A>> groupBy(Function1<A, K$> f) {
        return IterableOps.groupBy$(this, f);
    }

    @Override
    public <K$, B> Map<K$, Iterable<B>> groupMap(Function1<A, K$> key, Function1<A, B> f) {
        return IterableOps.groupMap$(this, key, f);
    }

    @Override
    public <K$, B> Map<K$, B> groupMapReduce(Function1<A, K$> key, Function1<A, B> f, Function2<B, B, B> reduce) {
        return IterableOps.groupMapReduce$(this, key, f, reduce);
    }

    @Override
    public Object scan(Object z, Function2 op) {
        return IterableOps.scan$(this, z, op);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return IterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object scanRight(Object z, Function2 op) {
        return IterableOps.scanRight$(this, z, op);
    }

    @Override
    public Object map(Function1 f) {
        return IterableOps.map$(this, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return IterableOps.flatMap$(this, f);
    }

    @Override
    public Object flatten(Function1 asIterable) {
        return IterableOps.flatten$(this, asIterable);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return IterableOps.collect$(this, pf);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return IterableOps.partitionMap$(this, f);
    }

    @Override
    public Object concat(IterableOnce suffix) {
        return IterableOps.concat$(this, suffix);
    }

    @Override
    public final Object $plus$plus(IterableOnce suffix) {
        return IterableOps.$plus$plus$(this, suffix);
    }

    @Override
    public Object zip(IterableOnce that) {
        return IterableOps.zip$(this, that);
    }

    @Override
    public Object zipWithIndex() {
        return IterableOps.zipWithIndex$(this);
    }

    @Override
    public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
        return IterableOps.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return IterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return IterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Iterator<Iterable<A>> tails() {
        return IterableOps.tails$(this);
    }

    @Override
    public Iterator<Iterable<A>> inits() {
        return IterableOps.inits$(this);
    }

    @Override
    public Object tapEach(Function1 f) {
        return IterableOps.tapEach$(this, f);
    }

    @Override
    public Object $plus$plus$colon(IterableOnce that) {
        return IterableOps.$plus$plus$colon$(this, that);
    }

    @Override
    public boolean hasDefiniteSize() {
        return IterableOnceOps.hasDefiniteSize$(this);
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        IterableOnceOps.foreach$(this, f);
    }

    @Override
    public boolean forall(Function1<A, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<A, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<A, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<A> find(Function1<A, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> B foldRight(B z, Function2<A, B, B> op) {
        return (B)IterableOnceOps.foldRight$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<A, B, B> op) {
        return (B)IterableOnceOps.$colon$bslash$(this, z, op);
    }

    @Override
    public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return (A1)IterableOnceOps.fold$(this, z, op);
    }

    @Override
    public <B> B reduce(Function2<B, B, B> op) {
        return (B)IterableOnceOps.reduce$(this, op);
    }

    @Override
    public <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return IterableOnceOps.reduceOption$(this, op);
    }

    @Override
    public <B> B reduceLeft(Function2<B, A, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<A, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
        return IterableOnceOps.reduceRightOption$(this, op);
    }

    @Override
    public boolean isEmpty() {
        return IterableOnceOps.isEmpty$(this);
    }

    @Override
    public boolean nonEmpty() {
        return IterableOnceOps.nonEmpty$(this);
    }

    @Override
    public int size() {
        return IterableOnceOps.size$(this);
    }

    @Override
    public final <B> void copyToBuffer(Buffer<B> dest) {
        IterableOnceOps.copyToBuffer$(this, dest);
    }

    @Override
    public <B> int copyToArray(Object xs) {
        return IterableOnceOps.copyToArray$(this, xs);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2) {
        return IterableOnceOps.copyToArray$(this, xs, start2);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        return IterableOnceOps.copyToArray$(this, xs, start2, len);
    }

    @Override
    public <B> B sum(Numeric<B> num) {
        return (B)IterableOnceOps.sum$(this, num);
    }

    @Override
    public <B> B product(Numeric<B> num) {
        return (B)IterableOnceOps.product$(this, num);
    }

    @Override
    public <B> A min(Ordering<B> ord) {
        return (A)IterableOnceOps.min$(this, ord);
    }

    @Override
    public <B> Option<A> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public <B> A max(Ordering<B> ord) {
        return (A)IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<A> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
        return IterableOnceOps.corresponds$(this, that, p);
    }

    @Override
    public final String mkString(String start2, String sep, String end) {
        return IterableOnceOps.mkString$(this, start2, sep, end);
    }

    @Override
    public final String mkString(String sep) {
        return IterableOnceOps.mkString$(this, sep);
    }

    @Override
    public final String mkString() {
        return IterableOnceOps.mkString$(this);
    }

    @Override
    public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return IterableOnceOps.addString$(this, b, start2, sep, end);
    }

    @Override
    public final StringBuilder addString(StringBuilder b, String sep) {
        return IterableOnceOps.addString$(this, b, sep);
    }

    @Override
    public final StringBuilder addString(StringBuilder b) {
        return IterableOnceOps.addString$(this, b);
    }

    @Override
    public <C1> C1 to(Factory<A, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<A> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<A> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public Vector<A> toVector() {
        return IterableOnceOps.toVector$(this);
    }

    @Override
    public <K$, V$> Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public Seq<A> toSeq() {
        return IterableOnceOps.toSeq$(this);
    }

    @Override
    public IndexedSeq<A> toIndexedSeq() {
        return IterableOnceOps.toIndexedSeq$(this);
    }

    @Override
    public final Stream<A> toStream() {
        return IterableOnceOps.toStream$(this);
    }

    @Override
    public final <B> Buffer<B> toBuffer() {
        return IterableOnceOps.toBuffer$(this);
    }

    @Override
    public <B> Object toArray(ClassTag<B> evidence$2) {
        return IterableOnceOps.toArray$(this, evidence$2);
    }

    @Override
    public Iterable<A> reversed() {
        return IterableOnceOps.reversed$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    @Override
    public int knownSize() {
        return IterableOnce.knownSize$(this);
    }
}

