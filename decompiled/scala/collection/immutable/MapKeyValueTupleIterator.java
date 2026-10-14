/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.BufferedIterator;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.immutable.ChampBaseIterator;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2A\u0001B\u0003\u0007\u0019!A1\u0006\u0001B\u0001B\u0003%!\u0003C\u0003-\u0001\u0011\u0005Q\u0006C\u00031\u0001\u0011\u0005\u0011G\u0001\rNCB\\U-\u001f,bYV,G+\u001e9mK&#XM]1u_JT!AB\u0004\u0002\u0013%lW.\u001e;bE2,'B\u0001\u0005\n\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0015\u0005)1oY1mC\u000e\u0001QcA\u0007\u0018EM\u0019\u0001A\u0004\u0013\u0011\u0007=\u0001\"#D\u0001\u0006\u0013\t\tRAA\tDQ\u0006l\u0007OQ1tK&#XM]1u_J\u0004BaD\n\u0016C%\u0011A#\u0002\u0002\b\u001b\u0006\u0004hj\u001c3f!\t1r\u0003\u0004\u0001\u0005\u000ba\u0001!\u0019A\r\u0003\u0003-\u000b\"A\u0007\u0010\u0011\u0005maR\"A\u0005\n\u0005uI!a\u0002(pi\"Lgn\u001a\t\u00037}I!\u0001I\u0005\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0017E\u0011)1\u0005\u0001b\u00013\t\ta\u000bE\u0002&M!j\u0011aB\u0005\u0003O\u001d\u0011\u0001\"\u0013;fe\u0006$xN\u001d\t\u00057%*\u0012%\u0003\u0002+\u0013\t1A+\u001e9mKJ\n\u0001B]8pi:{G-Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00059z\u0003\u0003B\b\u0001+\u0005BQa\u000b\u0002A\u0002I\tAA\\3yiR\t\u0001\u0006")
public final class MapKeyValueTupleIterator<K, V>
extends ChampBaseIterator<MapNode<K, V>>
implements Iterator<Tuple2<K, V>> {
    @Override
    public final boolean hasDefiniteSize() {
        return Iterator.hasDefiniteSize$(this);
    }

    @Override
    public final Iterator<Tuple2<K, V>> iterator() {
        return Iterator.iterator$(this);
    }

    @Override
    public Option<Tuple2<K, V>> nextOption() {
        return Iterator.nextOption$(this);
    }

    @Override
    public boolean contains(Object elem) {
        return Iterator.contains$(this, elem);
    }

    @Override
    public BufferedIterator<Tuple2<K, V>> buffered() {
        return Iterator.buffered$(this);
    }

    @Override
    public <B> Iterator<B> padTo(int len, B elem) {
        return Iterator.padTo$(this, len, elem);
    }

    @Override
    public Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> partition(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.partition$(this, p);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> grouped(int size) {
        return Iterator.grouped$(this, size);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> sliding(int size, int step) {
        return Iterator.sliding$(this, size, step);
    }

    @Override
    public <B> int sliding$default$2() {
        return Iterator.sliding$default$2$(this);
    }

    @Override
    public <B> Iterator<B> scanLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
        return Iterator.scanLeft$(this, z, op);
    }

    @Override
    public <B> Iterator<B> scanRight(B z, Function2<Tuple2<K, V>, B, B> op) {
        return Iterator.scanRight$(this, z, op);
    }

    @Override
    public int indexWhere(Function1<Tuple2<K, V>, Object> p, int from) {
        return Iterator.indexWhere$(this, p, from);
    }

    @Override
    public int indexWhere$default$2() {
        return Iterator.indexWhere$default$2$(this);
    }

    @Override
    public <B> int indexOf(B elem) {
        return Iterator.indexOf$(this, elem);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return Iterator.indexOf$(this, elem, from);
    }

    @Override
    public final int length() {
        return Iterator.length$(this);
    }

    @Override
    public boolean isEmpty() {
        return Iterator.isEmpty$(this);
    }

    @Override
    public Iterator<Tuple2<K, V>> filter(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.filter$(this, p);
    }

    @Override
    public Iterator<Tuple2<K, V>> filterNot(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.filterNot$(this, p);
    }

    @Override
    public Iterator<Tuple2<K, V>> filterImpl(Function1<Tuple2<K, V>, Object> p, boolean isFlipped) {
        return Iterator.filterImpl$(this, p, isFlipped);
    }

    @Override
    public Iterator<Tuple2<K, V>> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.withFilter$(this, p);
    }

    @Override
    public <B> Iterator<B> collect(PartialFunction<Tuple2<K, V>, B> pf) {
        return Iterator.collect$(this, pf);
    }

    @Override
    public Iterator<Tuple2<K, V>> distinct() {
        return Iterator.distinct$(this);
    }

    @Override
    public <B> Iterator<Tuple2<K, V>> distinctBy(Function1<Tuple2<K, V>, B> f) {
        return Iterator.distinctBy$(this, f);
    }

    @Override
    public <B> Iterator<B> map(Function1<Tuple2<K, V>, B> f) {
        return Iterator.map$(this, f);
    }

    @Override
    public <B> Iterator<B> flatMap(Function1<Tuple2<K, V>, IterableOnce<B>> f) {
        return Iterator.flatMap$(this, f);
    }

    @Override
    public <B> Iterator<B> flatten(Function1<Tuple2<K, V>, IterableOnce<B>> ev) {
        return Iterator.flatten$(this, ev);
    }

    @Override
    public <B> Iterator<B> concat(Function0<IterableOnce<B>> xs) {
        return Iterator.concat$(this, xs);
    }

    @Override
    public final <B> Iterator<B> $plus$plus(Function0<IterableOnce<B>> xs) {
        return Iterator.$plus$plus$(this, xs);
    }

    @Override
    public Iterator<Tuple2<K, V>> take(int n) {
        return Iterator.take$(this, n);
    }

    @Override
    public Iterator<Tuple2<K, V>> takeWhile(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.takeWhile$(this, p);
    }

    @Override
    public Iterator<Tuple2<K, V>> drop(int n) {
        return Iterator.drop$(this, n);
    }

    @Override
    public Iterator<Tuple2<K, V>> dropWhile(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.dropWhile$(this, p);
    }

    @Override
    public Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> span(Function1<Tuple2<K, V>, Object> p) {
        return Iterator.span$(this, p);
    }

    @Override
    public Iterator<Tuple2<K, V>> slice(int from, int until) {
        return Iterator.slice$(this, from, until);
    }

    @Override
    public Iterator<Tuple2<K, V>> sliceIterator(int from, int until) {
        return Iterator.sliceIterator$(this, from, until);
    }

    @Override
    public <B> Iterator<Tuple2<Tuple2<K, V>, B>> zip(IterableOnce<B> that) {
        return Iterator.zip$(this, that);
    }

    @Override
    public <A1, B> Iterator<Tuple2<A1, B>> zipAll(IterableOnce<B> that, A1 thisElem, B thatElem) {
        return Iterator.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public Iterator<Tuple2<Tuple2<K, V>, Object>> zipWithIndex() {
        return Iterator.zipWithIndex$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return Iterator.sameElements$(this, that);
    }

    @Override
    public Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> duplicate() {
        return Iterator.duplicate$(this);
    }

    @Override
    public <B> Iterator<B> patch(int from, Iterator<B> patchElems, int replaced) {
        return Iterator.patch$(this, from, patchElems, replaced);
    }

    @Override
    public <U> Iterator<Tuple2<K, V>> tapEach(Function1<Tuple2<K, V>, U> f) {
        return Iterator.tapEach$(this, f);
    }

    @Override
    public String toString() {
        return Iterator.toString$(this);
    }

    @Override
    public Iterator<Tuple2<K, V>> seq() {
        return Iterator.seq$(this);
    }

    @Override
    public Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> splitAt(int n) {
        return IterableOnceOps.splitAt$(this, n);
    }

    @Override
    public boolean isTraversableAgain() {
        return IterableOnceOps.isTraversableAgain$(this);
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        IterableOnceOps.foreach$(this, f);
    }

    @Override
    public boolean forall(Function1<Tuple2<K, V>, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<Tuple2<K, V>, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<Tuple2<K, V>, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<Tuple2<K, V>> find(Function1<Tuple2<K, V>, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, Tuple2<K, V>, B> op) {
        return (B)IterableOnceOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> B foldRight(B z, Function2<Tuple2<K, V>, B, B> op) {
        return (B)IterableOnceOps.foldRight$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, Tuple2<K, V>, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<Tuple2<K, V>, B, B> op) {
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
    public <B> B reduceLeft(Function2<B, Tuple2<K, V>, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<Tuple2<K, V>, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, Tuple2<K, V>, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<Tuple2<K, V>, B, B> op) {
        return IterableOnceOps.reduceRightOption$(this, op);
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
    public Object min(Ordering ord) {
        return IterableOnceOps.min$(this, ord);
    }

    @Override
    public <B> Option<Tuple2<K, V>> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public Object max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<Tuple2<K, V>> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public Object maxBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Tuple2<K, V>> maxByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public Object minBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Tuple2<K, V>> minByOption(Function1<Tuple2<K, V>, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<Tuple2<K, V>, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, Tuple2<K, V>, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<Tuple2<K, V>, B, Object> p) {
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
    public <C1> C1 to(Factory<Tuple2<K, V>, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<Tuple2<K, V>> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<Tuple2<K, V>> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public Vector<Tuple2<K, V>> toVector() {
        return IterableOnceOps.toVector$(this);
    }

    @Override
    public <K$, V$> Map<K$, V$> toMap($less$colon$less<Tuple2<K, V>, Tuple2<K$, V$>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public Seq<Tuple2<K, V>> toSeq() {
        return IterableOnceOps.toSeq$(this);
    }

    @Override
    public IndexedSeq<Tuple2<K, V>> toIndexedSeq() {
        return IterableOnceOps.toIndexedSeq$(this);
    }

    @Override
    public final Stream<Tuple2<K, V>> toStream() {
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
    public Iterable<Tuple2<K, V>> reversed() {
        return IterableOnceOps.reversed$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    @Override
    public int knownSize() {
        return IterableOnce.knownSize$(this);
    }

    @Override
    public Tuple2<K, V> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        Tuple2 payload = ((MapNode)this.currentValueNode()).getPayload(this.currentValueCursor());
        this.currentValueCursor_$eq(this.currentValueCursor() + 1);
        return payload;
    }

    public MapKeyValueTupleIterator(MapNode<K, V> rootNode) {
        super(rootNode);
    }
}

