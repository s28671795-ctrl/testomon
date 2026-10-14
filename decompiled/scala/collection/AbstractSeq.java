/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.SeqView;
import scala.collection.immutable.Range;
import scala.collection.mutable.Map;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u00111\"\u00112tiJ\f7\r^*fc*\u0011A!B\u0001\u000bG>dG.Z2uS>t'\"\u0001\u0004\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0011\u0002E\n\u0004\u0001)Q\u0002cA\u0006\r\u001d5\t1!\u0003\u0002\u000e\u0007\t\u0001\u0012IY:ue\u0006\u001cG/\u0013;fe\u0006\u0014G.\u001a\t\u0003\u001fAa\u0001\u0001\u0002\u0004\u0012\u0001\u0011\u0015\rA\u0005\u0002\u0002\u0003F\u00111c\u0006\t\u0003)Ui\u0011!B\u0005\u0003-\u0015\u0011qAT8uQ&tw\r\u0005\u0002\u00151%\u0011\u0011$\u0002\u0002\u0004\u0003:L\bcA\u0006\u001c\u001d%\u0011Ad\u0001\u0002\u0004'\u0016\f\u0018A\u0002\u001fj]&$h\bF\u0001 !\rY\u0001A\u0004")
public abstract class AbstractSeq<A>
extends AbstractIterable<A>
implements Seq<A> {
    @Override
    public SeqFactory<Seq> iterableFactory() {
        return Seq.iterableFactory$(this);
    }

    @Override
    public boolean canEqual(Object that) {
        return Seq.canEqual$(this, that);
    }

    @Override
    public boolean equals(Object o) {
        return Seq.equals$(this, o);
    }

    @Override
    public int hashCode() {
        return Seq.hashCode$(this);
    }

    @Override
    public String toString() {
        return Seq.toString$(this);
    }

    @Override
    public String stringPrefix() {
        return Seq.stringPrefix$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
        return IterableOps.concat$(this, suffix);
    }

    @Override
    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
        return IterableOps.sizeCompare$((IterableOps)this, otherSize);
    }

    @Override
    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
        return IterableOps.sizeCompare$((IterableOps)this, that);
    }

    @Override
    public SeqView<A> view() {
        return SeqOps.view$(this);
    }

    @Override
    public Object prepended(Object elem) {
        return SeqOps.prepended$(this, elem);
    }

    @Override
    public final Object $plus$colon(Object elem) {
        return SeqOps.$plus$colon$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return SeqOps.appended$(this, elem);
    }

    @Override
    public final Object $colon$plus(Object elem) {
        return SeqOps.$colon$plus$(this, elem);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return SeqOps.prependedAll$(this, prefix);
    }

    @Override
    public final Object $plus$plus$colon(IterableOnce prefix) {
        return SeqOps.$plus$plus$colon$(this, prefix);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return SeqOps.appendedAll$(this, suffix);
    }

    @Override
    public final Object $colon$plus$plus(IterableOnce suffix) {
        return SeqOps.$colon$plus$plus$(this, suffix);
    }

    @Override
    public final Object concat(IterableOnce suffix) {
        return SeqOps.concat$(this, suffix);
    }

    @Override
    public final Object union(Seq that) {
        return SeqOps.union$(this, that);
    }

    @Override
    public final int size() {
        return SeqOps.size$(this);
    }

    @Override
    public Object distinct() {
        return SeqOps.distinct$(this);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return SeqOps.distinctBy$(this, f);
    }

    @Override
    public Object reverse() {
        return SeqOps.reverse$(this);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return SeqOps.reverseIterator$(this);
    }

    @Override
    public <B> boolean startsWith(IterableOnce<B> that, int offset) {
        return SeqOps.startsWith$(this, that, offset);
    }

    @Override
    public <B> int startsWith$default$2() {
        return SeqOps.startsWith$default$2$(this);
    }

    @Override
    public <B> boolean endsWith(Iterable<B> that) {
        return SeqOps.endsWith$(this, that);
    }

    @Override
    public boolean isDefinedAt(int idx) {
        return SeqOps.isDefinedAt$(this, idx);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return SeqOps.padTo$(this, len, elem);
    }

    @Override
    public final int segmentLength(Function1<A, Object> p) {
        return SeqOps.segmentLength$(this, p);
    }

    @Override
    public int segmentLength(Function1<A, Object> p, int from) {
        return SeqOps.segmentLength$(this, p, from);
    }

    @Override
    public final int prefixLength(Function1<A, Object> p) {
        return SeqOps.prefixLength$(this, p);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
        return SeqOps.indexWhere$(this, p, from);
    }

    @Override
    public int indexWhere(Function1<A, Object> p) {
        return SeqOps.indexWhere$(this, p);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return SeqOps.indexOf$(this, elem, from);
    }

    @Override
    public <B> int indexOf(B elem) {
        return SeqOps.indexOf$(this, elem);
    }

    @Override
    public <B> int lastIndexOf(B elem, int end) {
        return SeqOps.lastIndexOf$(this, elem, end);
    }

    @Override
    public <B> int lastIndexOf$default$2() {
        return SeqOps.lastIndexOf$default$2$(this);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p, int end) {
        return SeqOps.lastIndexWhere$(this, p, end);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p) {
        return SeqOps.lastIndexWhere$(this, p);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that, int from) {
        return SeqOps.indexOfSlice$(this, that, from);
    }

    @Override
    public <B> int indexOfSlice(Seq<B> that) {
        return SeqOps.indexOfSlice$(this, that);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that, int end) {
        return SeqOps.lastIndexOfSlice$(this, that, end);
    }

    @Override
    public <B> int lastIndexOfSlice(Seq<B> that) {
        return SeqOps.lastIndexOfSlice$(this, that);
    }

    @Override
    public Option<A> findLast(Function1<A, Object> p) {
        return SeqOps.findLast$(this, p);
    }

    @Override
    public <B> boolean containsSlice(Seq<B> that) {
        return SeqOps.containsSlice$(this, that);
    }

    @Override
    public <A1> boolean contains(A1 elem) {
        return SeqOps.contains$(this, elem);
    }

    @Override
    public Object reverseMap(Function1 f) {
        return SeqOps.reverseMap$(this, f);
    }

    @Override
    public Iterator<Seq<A>> permutations() {
        return SeqOps.permutations$(this);
    }

    @Override
    public Iterator<Seq<A>> combinations(int n) {
        return SeqOps.combinations$(this, n);
    }

    @Override
    public Object sorted(Ordering ord) {
        return SeqOps.sorted$(this, ord);
    }

    @Override
    public Object sortWith(Function2 lt) {
        return SeqOps.sortWith$(this, lt);
    }

    @Override
    public Object sortBy(Function1 f, Ordering ord) {
        return SeqOps.sortBy$(this, f, ord);
    }

    @Override
    public Range indices() {
        return SeqOps.indices$(this);
    }

    @Override
    public final int sizeCompare(int otherSize) {
        return SeqOps.sizeCompare$((SeqOps)this, otherSize);
    }

    @Override
    public int lengthCompare(int len) {
        return SeqOps.lengthCompare$((SeqOps)this, len);
    }

    @Override
    public final int sizeCompare(Iterable<?> that) {
        return SeqOps.sizeCompare$((SeqOps)this, that);
    }

    @Override
    public int lengthCompare(Iterable<?> that) {
        return SeqOps.lengthCompare$((SeqOps)this, that);
    }

    @Override
    public final IterableOps lengthIs() {
        return SeqOps.lengthIs$(this);
    }

    @Override
    public boolean isEmpty() {
        return SeqOps.isEmpty$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return SeqOps.sameElements$(this, that);
    }

    @Override
    public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
        return SeqOps.corresponds$(this, that, p);
    }

    @Override
    public Object diff(Seq that) {
        return SeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return SeqOps.intersect$(this, that);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return SeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object updated(int index, Object elem) {
        return SeqOps.updated$(this, index, elem);
    }

    @Override
    public <B> Map<B, Object> occCounts(Seq<B> sq) {
        return SeqOps.occCounts$(this, sq);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return SeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return SeqOps.search$(this, elem, from, to, ord);
    }

    @Override
    public Option unapply(Object a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<Object, A> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C$> PartialFunction<Object, C$> andThen(Function1<A, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C$> PartialFunction<Object, C$> andThen(PartialFunction<A, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, A> compose(PartialFunction<R$, Object> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<Object, Option<A>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public Object applyOrElse(Object x, Function1 function1) {
        return PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<Object, Object> runWith(Function1<A, U> action) {
        return PartialFunction.runWith$(this, action);
    }

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
    }

    @Override
    public double apply$mcDD$sp(double v1) {
        return Function1.apply$mcDD$sp$(this, v1);
    }

    @Override
    public float apply$mcFD$sp(double v1) {
        return Function1.apply$mcFD$sp$(this, v1);
    }

    @Override
    public int apply$mcID$sp(double v1) {
        return Function1.apply$mcID$sp$(this, v1);
    }

    @Override
    public long apply$mcJD$sp(double v1) {
        return Function1.apply$mcJD$sp$(this, v1);
    }

    @Override
    public void apply$mcVD$sp(double v1) {
        Function1.apply$mcVD$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZF$sp(float v1) {
        return Function1.apply$mcZF$sp$(this, v1);
    }

    @Override
    public double apply$mcDF$sp(float v1) {
        return Function1.apply$mcDF$sp$(this, v1);
    }

    @Override
    public float apply$mcFF$sp(float v1) {
        return Function1.apply$mcFF$sp$(this, v1);
    }

    @Override
    public int apply$mcIF$sp(float v1) {
        return Function1.apply$mcIF$sp$(this, v1);
    }

    @Override
    public long apply$mcJF$sp(float v1) {
        return Function1.apply$mcJF$sp$(this, v1);
    }

    @Override
    public void apply$mcVF$sp(float v1) {
        Function1.apply$mcVF$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZI$sp(int v1) {
        return Function1.apply$mcZI$sp$(this, v1);
    }

    @Override
    public double apply$mcDI$sp(int v1) {
        return Function1.apply$mcDI$sp$(this, v1);
    }

    @Override
    public float apply$mcFI$sp(int v1) {
        return Function1.apply$mcFI$sp$(this, v1);
    }

    @Override
    public int apply$mcII$sp(int v1) {
        return Function1.apply$mcII$sp$(this, v1);
    }

    @Override
    public long apply$mcJI$sp(int v1) {
        return Function1.apply$mcJI$sp$(this, v1);
    }

    @Override
    public void apply$mcVI$sp(int v1) {
        Function1.apply$mcVI$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZJ$sp(long v1) {
        return Function1.apply$mcZJ$sp$(this, v1);
    }

    @Override
    public double apply$mcDJ$sp(long v1) {
        return Function1.apply$mcDJ$sp$(this, v1);
    }

    @Override
    public float apply$mcFJ$sp(long v1) {
        return Function1.apply$mcFJ$sp$(this, v1);
    }

    @Override
    public int apply$mcIJ$sp(long v1) {
        return Function1.apply$mcIJ$sp$(this, v1);
    }

    @Override
    public long apply$mcJJ$sp(long v1) {
        return Function1.apply$mcJJ$sp$(this, v1);
    }

    @Override
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A$> Function1<A$, A> compose(Function1<A$, Object> g2) {
        return Function1.compose$(this, g2);
    }
}

