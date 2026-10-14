/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.collection.AbstractSeqView;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u0011a#\u00112tiJ\f7\r^%oI\u0016DX\rZ*fcZKWm\u001e\u0006\u0003\t\u0015\t!bY8mY\u0016\u001cG/[8o\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0003\u0013A\u00192\u0001\u0001\u0006\u001b!\rYABD\u0007\u0002\u0007%\u0011Qb\u0001\u0002\u0010\u0003\n\u001cHO]1diN+\u0017OV5foB\u0011q\u0002\u0005\u0007\u0001\t\u0019\t\u0002\u0001\"b\u0001%\t\t\u0011)\u0005\u0002\u0014/A\u0011A#F\u0007\u0002\u000b%\u0011a#\u0002\u0002\b\u001d>$\b.\u001b8h!\t!\u0002$\u0003\u0002\u001a\u000b\t\u0019\u0011I\\=\u0011\u0007-Yb\"\u0003\u0002\u001d\u0007\tq\u0011J\u001c3fq\u0016$7+Z9WS\u0016<\u0018A\u0002\u001fj]&$h\bF\u0001 !\rY\u0001A\u0004\u0015\u0005\u0001\u0005\"S\u0005\u0005\u0002\u0015E%\u00111%\u0002\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012a\u0001")
public abstract class AbstractIndexedSeqView<A>
extends AbstractSeqView<A>
implements IndexedSeqView<A> {
    private static final long serialVersionUID = 3L;

    @Override
    public IndexedSeqView<A> view() {
        return IndexedSeqView.view$(this);
    }

    @Override
    public IndexedSeqView<A> view(int from, int until) {
        return IndexedSeqView.view$(this, from, until);
    }

    @Override
    public Iterator<A> iterator() {
        return IndexedSeqView.iterator$(this);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return IndexedSeqView.reverseIterator$(this);
    }

    @Override
    public <B> IndexedSeqView<B> appended(B elem) {
        return IndexedSeqView.appended$(this, elem);
    }

    @Override
    public <B> IndexedSeqView<B> prepended(B elem) {
        return IndexedSeqView.prepended$(this, elem);
    }

    @Override
    public IndexedSeqView<A> take(int n) {
        return IndexedSeqView.take$(this, n);
    }

    @Override
    public IndexedSeqView<A> takeRight(int n) {
        return IndexedSeqView.takeRight$(this, n);
    }

    @Override
    public IndexedSeqView<A> drop(int n) {
        return IndexedSeqView.drop$(this, n);
    }

    @Override
    public IndexedSeqView<A> dropRight(int n) {
        return IndexedSeqView.dropRight$(this, n);
    }

    @Override
    public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
        return IndexedSeqView.map$(this, f);
    }

    @Override
    public IndexedSeqView<A> reverse() {
        return IndexedSeqView.reverse$(this);
    }

    @Override
    public IndexedSeqView<A> slice(int from, int until) {
        return IndexedSeqView.slice$(this, from, until);
    }

    @Override
    public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
        return IndexedSeqView.tapEach$(this, f);
    }

    @Override
    public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
        return IndexedSeqView.concat$(this, suffix);
    }

    @Override
    public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
        return IndexedSeqView.appendedAll$(this, suffix);
    }

    @Override
    public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
        return IndexedSeqView.prependedAll$(this, prefix);
    }

    @Override
    public String stringPrefix() {
        return IndexedSeqView.stringPrefix$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return (S)IndexedSeqOps.stepper$(this, shape);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
        return (B$)IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public Iterable<A> reversed() {
        return IndexedSeqOps.reversed$(this);
    }

    @Override
    public A head() {
        return (A)IndexedSeqOps.head$(this);
    }

    @Override
    public Option<A> headOption() {
        return IndexedSeqOps.headOption$(this);
    }

    @Override
    public A last() {
        return (A)IndexedSeqOps.last$(this);
    }

    @Override
    public final int lengthCompare(int len) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
    }

    @Override
    public int knownSize() {
        return IndexedSeqOps.knownSize$(this);
    }

    @Override
    public final int lengthCompare(Iterable<?> that) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, from, to, ord);
    }
}

