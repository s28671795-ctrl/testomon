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
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.SetNode;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000512A\u0001B\u0003\u0007\u0019!AQ\u0005\u0001B\u0001B\u0003%!\u0003C\u0003'\u0001\u0011\u0005q\u0005C\u0003+\u0001\u0011\u00051FA\u0006TKRLE/\u001a:bi>\u0014(B\u0001\u0004\b\u0003%IW.\\;uC\ndWM\u0003\u0002\t\u0013\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003)\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u000e/M\u0019\u0001AD\u0011\u0011\u0007=\u0001\"#D\u0001\u0006\u0013\t\tRAA\tDQ\u0006l\u0007OQ1tK&#XM]1u_J\u00042aD\n\u0016\u0013\t!RAA\u0004TKRtu\u000eZ3\u0011\u0005Y9B\u0002\u0001\u0003\u00061\u0001\u0011\r!\u0007\u0002\u0002\u0003F\u0011!D\b\t\u00037qi\u0011!C\u0005\u0003;%\u0011qAT8uQ&tw\r\u0005\u0002\u001c?%\u0011\u0001%\u0003\u0002\u0004\u0003:L\bc\u0001\u0012$+5\tq!\u0003\u0002%\u000f\tA\u0011\n^3sCR|'/\u0001\u0005s_>$hj\u001c3f\u0003\u0019a\u0014N\\5u}Q\u0011\u0001&\u000b\t\u0004\u001f\u0001)\u0002\"B\u0013\u0003\u0001\u0004\u0011\u0012\u0001\u00028fqR$\u0012!\u0006")
public final class SetIterator<A>
extends ChampBaseIterator<SetNode<A>>
implements Iterator<A> {
    @Override
    public final boolean hasDefiniteSize() {
        return Iterator.hasDefiniteSize$(this);
    }

    @Override
    public final Iterator<A> iterator() {
        return Iterator.iterator$(this);
    }

    @Override
    public Option<A> nextOption() {
        return Iterator.nextOption$(this);
    }

    @Override
    public boolean contains(Object elem) {
        return Iterator.contains$(this, elem);
    }

    @Override
    public BufferedIterator<A> buffered() {
        return Iterator.buffered$(this);
    }

    @Override
    public <B> Iterator<B> padTo(int len, B elem) {
        return Iterator.padTo$(this, len, elem);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> partition(Function1<A, Object> p) {
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
    public <B> Iterator<B> scanLeft(B z, Function2<B, A, B> op) {
        return Iterator.scanLeft$(this, z, op);
    }

    @Override
    public <B> Iterator<B> scanRight(B z, Function2<A, B, B> op) {
        return Iterator.scanRight$(this, z, op);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
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
    public Iterator<A> filter(Function1<A, Object> p) {
        return Iterator.filter$(this, p);
    }

    @Override
    public Iterator<A> filterNot(Function1<A, Object> p) {
        return Iterator.filterNot$(this, p);
    }

    @Override
    public Iterator<A> filterImpl(Function1<A, Object> p, boolean isFlipped) {
        return Iterator.filterImpl$(this, p, isFlipped);
    }

    @Override
    public Iterator<A> withFilter(Function1<A, Object> p) {
        return Iterator.withFilter$(this, p);
    }

    @Override
    public <B> Iterator<B> collect(PartialFunction<A, B> pf) {
        return Iterator.collect$(this, pf);
    }

    @Override
    public Iterator<A> distinct() {
        return Iterator.distinct$(this);
    }

    @Override
    public <B> Iterator<A> distinctBy(Function1<A, B> f) {
        return Iterator.distinctBy$(this, f);
    }

    @Override
    public <B> Iterator<B> map(Function1<A, B> f) {
        return Iterator.map$(this, f);
    }

    @Override
    public <B> Iterator<B> flatMap(Function1<A, IterableOnce<B>> f) {
        return Iterator.flatMap$(this, f);
    }

    @Override
    public <B> Iterator<B> flatten(Function1<A, IterableOnce<B>> ev) {
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
    public Iterator<A> take(int n) {
        return Iterator.take$(this, n);
    }

    @Override
    public Iterator<A> takeWhile(Function1<A, Object> p) {
        return Iterator.takeWhile$(this, p);
    }

    @Override
    public Iterator<A> drop(int n) {
        return Iterator.drop$(this, n);
    }

    @Override
    public Iterator<A> dropWhile(Function1<A, Object> p) {
        return Iterator.dropWhile$(this, p);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> span(Function1<A, Object> p) {
        return Iterator.span$(this, p);
    }

    @Override
    public Iterator<A> slice(int from, int until) {
        return Iterator.slice$(this, from, until);
    }

    @Override
    public Iterator<A> sliceIterator(int from, int until) {
        return Iterator.sliceIterator$(this, from, until);
    }

    @Override
    public <B> Iterator<Tuple2<A, B>> zip(IterableOnce<B> that) {
        return Iterator.zip$(this, that);
    }

    @Override
    public <A1, B> Iterator<Tuple2<A1, B>> zipAll(IterableOnce<B> that, A1 thisElem, B thatElem) {
        return Iterator.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public Iterator<Tuple2<A, Object>> zipWithIndex() {
        return Iterator.zipWithIndex$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return Iterator.sameElements$(this, that);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> duplicate() {
        return Iterator.duplicate$(this);
    }

    @Override
    public <B> Iterator<B> patch(int from, Iterator<B> patchElems, int replaced) {
        return Iterator.patch$(this, from, patchElems, replaced);
    }

    @Override
    public <U> Iterator<A> tapEach(Function1<A, U> f) {
        return Iterator.tapEach$(this, f);
    }

    @Override
    public String toString() {
        return Iterator.toString$(this);
    }

    @Override
    public Iterator<A> seq() {
        return Iterator.seq$(this);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> splitAt(int n) {
        return IterableOnceOps.splitAt$(this, n);
    }

    @Override
    public boolean isTraversableAgain() {
        return IterableOnceOps.isTraversableAgain$(this);
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

    @Override
    public A next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        Object payload = ((SetNode)this.currentValueNode()).getPayload(this.currentValueCursor());
        this.currentValueCursor_$eq(this.currentValueCursor() + 1);
        return payload;
    }

    public SetIterator(SetNode<A> rootNode) {
        super(rootNode);
    }
}

