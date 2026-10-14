/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.BufferedIterator;
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

@ScalaSignature(bytes="\u0006\u0005\u0005}rA\u0002\f\u0018\u0011\u0003I2D\u0002\u0004\u001e/!\u0005\u0011D\b\u0005\u0006K\u0005!\taJ\u0004\u0006Q\u0005A\t)\u000b\u0004\u0006W\u0005A\t\t\f\u0005\u0006K\u0011!\ta\u0012\u0005\u0006\u0011\u0012!)%\u0013\u0005\u0006\u001d\u0012!)e\u0014\u0005\u0006+\u0012!)E\u0016\u0005\b/\u0012\t\t\u0011\"\u0011Y\u0011\u001d\tG!!A\u0005\u0002YCqA\u0019\u0003\u0002\u0002\u0013\u00051\rC\u0004j\t\u0005\u0005I\u0011\t6\t\u000f1$\u0011\u0011!C\u0005[\u001a)\u0011/AA\u0001e\")QE\u0004C\u0001y\"9\u00111\u0001\b\u0007\u0002\u0005\u0015\u0001B\u0002(\u000f\r\u0003\t\t\u0002C\u0004\u0002\u001691\t!a\u0006\t\r!sAQAA\u000f\u0011%\t\tC\u0004EC\u0002\u0013\u0005c\u000bC\u0004\u0002$9!\t%!\n\u0002\u0017\r{G\u000e\\3di&|gn\u001d\u0006\u00031e\tA!\u001e;jY*\t!$\u0001\u0003bW.\f\u0007C\u0001\u000f\u0002\u001b\u00059\"aC\"pY2,7\r^5p]N\u001c\"!A\u0010\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u000e\u0002#\u0015k\u0007\u000f^=J[6,H/\u00192mKN+\u0017\u000f\u0005\u0002+\t5\t\u0011AA\tF[B$\u00180S7nkR\f'\r\\3TKF\u001cR\u0001B\u0010.qm\u00022AL\u001a6\u001b\u0005y#B\u0001\u00192\u0003%IW.\\;uC\ndWM\u0003\u00023C\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Qz#aA*fcB\u0011\u0001EN\u0005\u0003o\u0005\u0012qAT8uQ&tw\r\u0005\u0002!s%\u0011!(\t\u0002\b!J|G-^2u!\taDI\u0004\u0002>\u0005:\u0011a(Q\u0007\u0002\u007f)\u0011\u0001IJ\u0001\u0007yI|w\u000e\u001e \n\u0003\tJ!aQ\u0011\u0002\u000fA\f7m[1hK&\u0011QI\u0012\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u0007\u0006\"\u0012!K\u0001\tSR,'/\u0019;peV\t!\nE\u0002L\u0019Vj\u0011!M\u0005\u0003\u001bF\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\u0006CB\u0004H.\u001f\u000b\u0003kACQ!U\u0004A\u0002I\u000b1!\u001b3y!\t\u00013+\u0003\u0002UC\t\u0019\u0011J\u001c;\u0002\r1,gn\u001a;i+\u0005\u0011\u0016!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001Z!\tQv,D\u0001\\\u0015\taV,\u0001\u0003mC:<'\"\u00010\u0002\t)\fg/Y\u0005\u0003An\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003I\u001e\u0004\"\u0001I3\n\u0005\u0019\f#aA!os\"9\u0001nCA\u0001\u0002\u0004\u0011\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001l!\rYE\nZ\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002]B\u0011!l\\\u0005\u0003an\u0013aa\u00142kK\u000e$(A\b)beRL\u0017\r\\%n[V$\u0018M\u00197f-\u0006dW/Z:Ji\u0016\u0014\u0018M\u00197f+\r\u0019x0_\n\u0004\u001d}!\bc\u0001\u0018vo&\u0011ao\f\u0002\t\u0013R,'/\u00192mKB\u0011\u00010\u001f\u0007\u0001\t\u0015QhB1\u0001|\u0005\t!v.\u0005\u00026IR\tQ\u0010\u0005\u0003+\u001dy<\bC\u0001=\u0000\t\u0019\t\tA\u0004b\u0001w\n!aI]8n\u0003-I7\u000fR3gS:,G-\u0011;\u0015\t\u0005\u001d\u0011Q\u0002\t\u0004A\u0005%\u0011bAA\u0006C\t9!i\\8mK\u0006t\u0007BBA\b!\u0001\u0007a0\u0001\u0003ge>lGcA<\u0002\u0014!1\u0011qB\tA\u0002y\faB^1mk\u0016\u001c\u0018\n^3sCR|'/\u0006\u0002\u0002\u001aA!A(a\u0007\u007f\u0013\tie)\u0006\u0002\u0002 A!A(a\u0007x\u0003\u0011\u0019\u0018N_3\u0002\u000f\u0019|'/Z1dQV!\u0011qEA\u001e)\u0011\tI#a\f\u0011\u0007\u0001\nY#C\u0002\u0002.\u0005\u0012A!\u00168ji\"9\u0011\u0011G\u000bA\u0002\u0005M\u0012!\u00014\u0011\r\u0001\n)d^A\u001d\u0013\r\t9$\t\u0002\n\rVt7\r^5p]F\u00022\u0001_A\u001e\t\u0019\ti$\u0006b\u0001w\n\t1\t")
public final class Collections {

    public static abstract class PartialImmutableValuesIterable<From, To>
    implements scala.collection.immutable.Iterable<To> {
        private int size;
        private volatile boolean bitmap$0;

        @Override
        public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
            return scala.collection.immutable.Iterable.iterableFactory$(this);
        }

        @Override
        public final Iterable<To> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<To> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<To> seq() {
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
        public <B> LazyZip2<To, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return IterableFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<To, scala.collection.immutable.Iterable<To>> newSpecificBuilder() {
            return IterableFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public IterableOps empty() {
            return IterableFactoryDefaults.empty$(this);
        }

        @Override
        public final Iterable<To> toTraversable() {
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
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public To head() {
            return (To)IterableOps.head$(this);
        }

        @Override
        public Option<To> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public To last() {
            return (To)IterableOps.last$(this);
        }

        @Override
        public Option<To> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public View<To> view() {
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
        public View<To> view(int from, int until) {
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
        public WithFilter<To, ?> withFilter(Function1<To, Object> p) {
            return IterableOps.withFilter$(this, p);
        }

        @Override
        public Tuple2<scala.collection.immutable.Iterable<To>, scala.collection.immutable.Iterable<To>> partition(Function1<To, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<scala.collection.immutable.Iterable<To>, scala.collection.immutable.Iterable<To>> splitAt(int n) {
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
        public Tuple2<scala.collection.immutable.Iterable<To>, scala.collection.immutable.Iterable<To>> span(Function1<To, Object> p) {
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
        public Iterator<scala.collection.immutable.Iterable<To>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<scala.collection.immutable.Iterable<To>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<scala.collection.immutable.Iterable<To>> sliding(int size, int step) {
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
        public <K> Map<K, scala.collection.immutable.Iterable<To>> groupBy(Function1<To, K> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K, B> Map<K, scala.collection.immutable.Iterable<B>> groupMap(Function1<To, K> key, Function1<To, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K, B> Map<K, B> groupMapReduce(Function1<To, K> key, Function1<To, B> f, Function2<B, B, B> reduce) {
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
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<To, Either<A1, A2>> f) {
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
        public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<To, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<To, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<scala.collection.immutable.Iterable<To>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<scala.collection.immutable.Iterable<To>> inits() {
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
        public boolean forall(Function1<To, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<To, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<To, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<To> find(Function1<To, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, To, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<To, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, To, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<To, B, B> op) {
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
        public <B> B reduceLeft(Function2<B, To, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<To, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, To, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<To, B, B> op) {
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
        public <B> To min(Ordering<B> ord) {
            return (To)IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<To> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public <B> To max(Ordering<B> ord) {
            return (To)IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<To> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public <B> To maxBy(Function1<To, B> f, Ordering<B> cmp) {
            return (To)IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<To> maxByOption(Function1<To, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public <B> To minBy(Function1<To, B> f, Ordering<B> cmp) {
            return (To)IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<To> minByOption(Function1<To, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<To, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, To, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<To, B, Object> p) {
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
        public <C1> C1 to(Factory<To, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<To> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<To> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<To> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <K, V> Map<K, V> toMap($less$colon$less<To, Tuple2<K, V>> ev) {
            return IterableOnceOps.toMap$(this, ev);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<To> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<To> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<To> toStream() {
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
        public Iterable<To> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<To, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public int knownSize() {
            return IterableOnce.knownSize$(this);
        }

        public abstract boolean isDefinedAt(From var1);

        public abstract To apply(From var1);

        public abstract Iterator<From> valuesIterator();

        @Override
        public final Iterator<To> iterator() {
            Iterator<From> superIterator = this.valuesIterator();
            return new Iterator<To>(this, superIterator){
                private To _next;
                private boolean _hasNext;
                private final /* synthetic */ PartialImmutableValuesIterable $outer;
                private final Iterator superIterator$1;

                public final boolean hasDefiniteSize() {
                    return Iterator.hasDefiniteSize$(this);
                }

                public final Iterator<To> iterator() {
                    return Iterator.iterator$(this);
                }

                public Option<To> nextOption() {
                    return Iterator.nextOption$(this);
                }

                public boolean contains(Object elem) {
                    return Iterator.contains$(this, elem);
                }

                public BufferedIterator<To> buffered() {
                    return Iterator.buffered$(this);
                }

                public <B> Iterator<B> padTo(int len, B elem) {
                    return Iterator.padTo$(this, len, elem);
                }

                public Tuple2<Iterator<To>, Iterator<To>> partition(Function1<To, Object> p) {
                    return Iterator.partition$(this, p);
                }

                public <B> Iterator.GroupedIterator<B> grouped(int size) {
                    return Iterator.grouped$(this, size);
                }

                public <B> Iterator.GroupedIterator<B> sliding(int size, int step) {
                    return Iterator.sliding$(this, size, step);
                }

                public <B> int sliding$default$2() {
                    return Iterator.sliding$default$2$(this);
                }

                public <B> Iterator<B> scanLeft(B z, Function2<B, To, B> op) {
                    return Iterator.scanLeft$(this, z, op);
                }

                public <B> Iterator<B> scanRight(B z, Function2<To, B, B> op) {
                    return Iterator.scanRight$(this, z, op);
                }

                public int indexWhere(Function1<To, Object> p, int from) {
                    return Iterator.indexWhere$(this, p, from);
                }

                public int indexWhere$default$2() {
                    return Iterator.indexWhere$default$2$(this);
                }

                public <B> int indexOf(B elem) {
                    return Iterator.indexOf$(this, elem);
                }

                public <B> int indexOf(B elem, int from) {
                    return Iterator.indexOf$(this, elem, from);
                }

                public final int length() {
                    return Iterator.length$(this);
                }

                public boolean isEmpty() {
                    return Iterator.isEmpty$(this);
                }

                public Iterator<To> filter(Function1<To, Object> p) {
                    return Iterator.filter$(this, p);
                }

                public Iterator<To> filterNot(Function1<To, Object> p) {
                    return Iterator.filterNot$(this, p);
                }

                public Iterator<To> filterImpl(Function1<To, Object> p, boolean isFlipped) {
                    return Iterator.filterImpl$(this, p, isFlipped);
                }

                public Iterator<To> withFilter(Function1<To, Object> p) {
                    return Iterator.withFilter$(this, p);
                }

                public <B> Iterator<B> collect(PartialFunction<To, B> pf) {
                    return Iterator.collect$(this, pf);
                }

                public Iterator<To> distinct() {
                    return Iterator.distinct$(this);
                }

                public <B> Iterator<To> distinctBy(Function1<To, B> f) {
                    return Iterator.distinctBy$(this, f);
                }

                public <B> Iterator<B> map(Function1<To, B> f) {
                    return Iterator.map$(this, f);
                }

                public <B> Iterator<B> flatMap(Function1<To, IterableOnce<B>> f) {
                    return Iterator.flatMap$(this, f);
                }

                public <B> Iterator<B> flatten(Function1<To, IterableOnce<B>> ev) {
                    return Iterator.flatten$(this, ev);
                }

                public <B> Iterator<B> concat(Function0<IterableOnce<B>> xs) {
                    return Iterator.concat$(this, xs);
                }

                public final <B> Iterator<B> $plus$plus(Function0<IterableOnce<B>> xs) {
                    return Iterator.$plus$plus$(this, xs);
                }

                public Iterator<To> take(int n) {
                    return Iterator.take$(this, n);
                }

                public Iterator<To> takeWhile(Function1<To, Object> p) {
                    return Iterator.takeWhile$(this, p);
                }

                public Iterator<To> drop(int n) {
                    return Iterator.drop$(this, n);
                }

                public Iterator<To> dropWhile(Function1<To, Object> p) {
                    return Iterator.dropWhile$(this, p);
                }

                public Tuple2<Iterator<To>, Iterator<To>> span(Function1<To, Object> p) {
                    return Iterator.span$(this, p);
                }

                public Iterator<To> slice(int from, int until) {
                    return Iterator.slice$(this, from, until);
                }

                public Iterator<To> sliceIterator(int from, int until) {
                    return Iterator.sliceIterator$(this, from, until);
                }

                public <B> Iterator<Tuple2<To, B>> zip(IterableOnce<B> that) {
                    return Iterator.zip$(this, that);
                }

                public <A1, B> Iterator<Tuple2<A1, B>> zipAll(IterableOnce<B> that, A1 thisElem, B thatElem) {
                    return Iterator.zipAll$(this, that, thisElem, thatElem);
                }

                public Iterator<Tuple2<To, Object>> zipWithIndex() {
                    return Iterator.zipWithIndex$(this);
                }

                public <B> boolean sameElements(IterableOnce<B> that) {
                    return Iterator.sameElements$(this, that);
                }

                public Tuple2<Iterator<To>, Iterator<To>> duplicate() {
                    return Iterator.duplicate$(this);
                }

                public <B> Iterator<B> patch(int from, Iterator<B> patchElems, int replaced) {
                    return Iterator.patch$(this, from, patchElems, replaced);
                }

                public <U> Iterator<To> tapEach(Function1<To, U> f) {
                    return Iterator.tapEach$(this, f);
                }

                public String toString() {
                    return Iterator.toString$(this);
                }

                public Iterator<To> seq() {
                    return Iterator.seq$(this);
                }

                public Tuple2<Iterator<To>, Iterator<To>> splitAt(int n) {
                    return IterableOnceOps.splitAt$(this, n);
                }

                public boolean isTraversableAgain() {
                    return IterableOnceOps.isTraversableAgain$(this);
                }

                public <U> void foreach(Function1<To, U> f) {
                    IterableOnceOps.foreach$(this, f);
                }

                public boolean forall(Function1<To, Object> p) {
                    return IterableOnceOps.forall$(this, p);
                }

                public boolean exists(Function1<To, Object> p) {
                    return IterableOnceOps.exists$(this, p);
                }

                public int count(Function1<To, Object> p) {
                    return IterableOnceOps.count$(this, p);
                }

                public Option<To> find(Function1<To, Object> p) {
                    return IterableOnceOps.find$(this, p);
                }

                public <B> B foldLeft(B z, Function2<B, To, B> op) {
                    return (B)IterableOnceOps.foldLeft$(this, z, op);
                }

                public <B> B foldRight(B z, Function2<To, B, B> op) {
                    return (B)IterableOnceOps.foldRight$(this, z, op);
                }

                public final <B> B $div$colon(B z, Function2<B, To, B> op) {
                    return (B)IterableOnceOps.$div$colon$(this, z, op);
                }

                public final <B> B $colon$bslash(B z, Function2<To, B, B> op) {
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

                public <B> B reduceLeft(Function2<B, To, B> op) {
                    return (B)IterableOnceOps.reduceLeft$(this, op);
                }

                public <B> B reduceRight(Function2<To, B, B> op) {
                    return (B)IterableOnceOps.reduceRight$(this, op);
                }

                public <B> Option<B> reduceLeftOption(Function2<B, To, B> op) {
                    return IterableOnceOps.reduceLeftOption$(this, op);
                }

                public <B> Option<B> reduceRightOption(Function2<To, B, B> op) {
                    return IterableOnceOps.reduceRightOption$(this, op);
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

                public <B> To min(Ordering<B> ord) {
                    return (To)IterableOnceOps.min$(this, ord);
                }

                public <B> Option<To> minOption(Ordering<B> ord) {
                    return IterableOnceOps.minOption$(this, ord);
                }

                public <B> To max(Ordering<B> ord) {
                    return (To)IterableOnceOps.max$(this, ord);
                }

                public <B> Option<To> maxOption(Ordering<B> ord) {
                    return IterableOnceOps.maxOption$(this, ord);
                }

                public <B> To maxBy(Function1<To, B> f, Ordering<B> cmp) {
                    return (To)IterableOnceOps.maxBy$(this, f, cmp);
                }

                public <B> Option<To> maxByOption(Function1<To, B> f, Ordering<B> cmp) {
                    return IterableOnceOps.maxByOption$(this, f, cmp);
                }

                public <B> To minBy(Function1<To, B> f, Ordering<B> cmp) {
                    return (To)IterableOnceOps.minBy$(this, f, cmp);
                }

                public <B> Option<To> minByOption(Function1<To, B> f, Ordering<B> cmp) {
                    return IterableOnceOps.minByOption$(this, f, cmp);
                }

                public <B> Option<B> collectFirst(PartialFunction<To, B> pf) {
                    return IterableOnceOps.collectFirst$(this, pf);
                }

                public <B> B aggregate(Function0<B> z, Function2<B, To, B> seqop, Function2<B, B, B> combop) {
                    return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
                }

                public <B> boolean corresponds(IterableOnce<B> that, Function2<To, B, Object> p) {
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

                public <C1> C1 to(Factory<To, C1> factory2) {
                    return (C1)IterableOnceOps.to$(this, factory2);
                }

                public final Iterator<To> toIterator() {
                    return IterableOnceOps.toIterator$(this);
                }

                public List<To> toList() {
                    return IterableOnceOps.toList$(this);
                }

                public Vector<To> toVector() {
                    return IterableOnceOps.toVector$(this);
                }

                public <K, V> Map<K, V> toMap($less$colon$less<To, Tuple2<K, V>> ev) {
                    return IterableOnceOps.toMap$(this, ev);
                }

                public <B> Set<B> toSet() {
                    return IterableOnceOps.toSet$(this);
                }

                public Seq<To> toSeq() {
                    return IterableOnceOps.toSeq$(this);
                }

                public IndexedSeq<To> toIndexedSeq() {
                    return IterableOnceOps.toIndexedSeq$(this);
                }

                public final Stream<To> toStream() {
                    return IterableOnceOps.toStream$(this);
                }

                public final <B> Buffer<B> toBuffer() {
                    return IterableOnceOps.toBuffer$(this);
                }

                public <B> Object toArray(ClassTag<B> evidence$2) {
                    return IterableOnceOps.toArray$(this, evidence$2);
                }

                public Iterable<To> reversed() {
                    return IterableOnceOps.reversed$(this);
                }

                public <S extends Stepper<?>> S stepper(StepperShape<To, S> shape) {
                    return (S)IterableOnce.stepper$(this, shape);
                }

                public int knownSize() {
                    return IterableOnce.knownSize$(this);
                }

                public final boolean hasNext() {
                    return this.tailrecHasNext$1();
                }

                public final To next() {
                    if (this.hasNext()) {
                        To ret = this._next;
                        this._next = null;
                        this._hasNext = false;
                        return ret;
                    }
                    throw new NoSuchElementException("next");
                }

                private final boolean tailrecHasNext$1() {
                    while (!this._hasNext && this.superIterator$1.hasNext()) {
                        A potentiallyNext = this.superIterator$1.next();
                        if (!this.$outer.isDefinedAt(potentiallyNext)) continue;
                        this._next = this.$outer.apply(potentiallyNext);
                        this._hasNext = true;
                        return true;
                    }
                    return this._hasNext;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.superIterator$1 = superIterator$1;
                    IterableOnce.$init$(this);
                    IterableOnceOps.$init$(this);
                    Iterator.$init$(this);
                    this._hasNext = false;
                }
            };
        }

        private int size$lzycompute() {
            PartialImmutableValuesIterable partialImmutableValuesIterable = this;
            synchronized (partialImmutableValuesIterable) {
                if (!this.bitmap$0) {
                    this.size = this.iterator().size();
                    this.bitmap$0 = true;
                }
            }
            return this.size;
        }

        @Override
        public int size() {
            if (!this.bitmap$0) {
                return this.size$lzycompute();
            }
            return this.size;
        }

        @Override
        public <C> void foreach(Function1<To, C> f) {
            this.iterator().foreach(f);
        }

        public PartialImmutableValuesIterable() {
            IterableOnce.$init$(this);
            IterableOnceOps.$init$(this);
            IterableOps.$init$(this);
            IterableFactoryDefaults.$init$(this);
            Iterable.$init$(this);
            scala.collection.immutable.Iterable.$init$(this);
        }
    }
}

