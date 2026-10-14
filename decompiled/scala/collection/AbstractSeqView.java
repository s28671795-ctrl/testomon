/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.collection.AbstractView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.SeqView;
import scala.collection.View;
import scala.collection.immutable.Range;
import scala.collection.mutable.Map;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u0011q\"\u00112tiJ\f7\r^*fcZKWm\u001e\u0006\u0003\t\u0015\t!bY8mY\u0016\u001cG/[8o\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0003\u0013A\u00192\u0001\u0001\u0006\u001b!\rYABD\u0007\u0002\u0007%\u0011Qb\u0001\u0002\r\u0003\n\u001cHO]1diZKWm\u001e\t\u0003\u001fAa\u0001\u0001\u0002\u0004\u0012\u0001\u0011\u0015\rA\u0005\u0002\u0002\u0003F\u00111c\u0006\t\u0003)Ui\u0011!B\u0005\u0003-\u0015\u0011qAT8uQ&tw\r\u0005\u0002\u00151%\u0011\u0011$\u0002\u0002\u0004\u0003:L\bcA\u0006\u001c\u001d%\u0011Ad\u0001\u0002\b'\u0016\fh+[3x\u0003\u0019a\u0014N\\5u}Q\tq\u0004E\u0002\f\u00019AC\u0001A\u0011%KA\u0011ACI\u0005\u0003G\u0015\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\r\u0001")
public abstract class AbstractSeqView<A>
extends AbstractView<A>
implements SeqView<A> {
    private static final long serialVersionUID = 3L;

    @Override
    public SeqView<A> view() {
        return SeqView.view$(this);
    }

    @Override
    public <B$> SeqView<B$> map(Function1<A, B$> f) {
        return SeqView.map$(this, f);
    }

    @Override
    public <B> SeqView<B> appended(B elem) {
        return SeqView.appended$(this, elem);
    }

    @Override
    public <B> SeqView<B> prepended(B elem) {
        return SeqView.prepended$(this, elem);
    }

    @Override
    public SeqView<A> reverse() {
        return SeqView.reverse$(this);
    }

    @Override
    public SeqView<A> take(int n) {
        return SeqView.take$(this, n);
    }

    @Override
    public SeqView<A> drop(int n) {
        return SeqView.drop$(this, n);
    }

    @Override
    public SeqView<A> takeRight(int n) {
        return SeqView.takeRight$(this, n);
    }

    @Override
    public SeqView<A> dropRight(int n) {
        return SeqView.dropRight$(this, n);
    }

    @Override
    public <U> SeqView<A> tapEach(Function1<A, U> f) {
        return SeqView.tapEach$(this, f);
    }

    @Override
    public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
        return SeqView.concat$(this, suffix);
    }

    @Override
    public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
        return SeqView.appendedAll$(this, suffix);
    }

    @Override
    public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
        return SeqView.prependedAll$(this, prefix);
    }

    @Override
    public <B> SeqView<A> sorted(Ordering<B> ord) {
        return SeqView.sorted$(this, ord);
    }

    @Override
    public String stringPrefix() {
        return SeqView.stringPrefix$(this);
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
    public final Object $plus$colon(Object elem) {
        return SeqOps.$plus$colon$(this, elem);
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
    public Iterator<View<A>> permutations() {
        return SeqOps.permutations$(this);
    }

    @Override
    public Iterator<View<A>> combinations(int n) {
        return SeqOps.combinations$(this, n);
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
}

