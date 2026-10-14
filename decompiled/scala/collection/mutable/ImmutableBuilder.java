/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3Q!\u0003\u0006\u0002\u0002EA\u0001B\r\u0001\u0003\u0002\u0003\u0006IA\n\u0005\u0006g\u0001!\t\u0001\u000e\u0005\bo\u0001\u0001\r\u0011\"\u00059\u0011\u001dI\u0004\u00011A\u0005\u0012iBa\u0001\u0011\u0001!B\u00131\u0003\"B!\u0001\t\u0003\u0011\u0005\"B\"\u0001\t\u0003!\u0005\"B#\u0001\t\u00032%\u0001E%n[V$\u0018M\u00197f\u0005VLG\u000eZ3s\u0015\tYA\"A\u0004nkR\f'\r\\3\u000b\u00055q\u0011AC2pY2,7\r^5p]*\tq\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007IireE\u0002\u0001']\u0001\"\u0001F\u000b\u000e\u00039I!A\u0006\b\u0003\r\u0005s\u0017PU3g!\u0011A\u0012d\u0007\u0014\u000e\u0003)I!A\u0007\u0006\u0003\u001fI+Wo]1cY\u0016\u0014U/\u001b7eKJ\u0004\"\u0001H\u000f\r\u0001\u00111a\u0004\u0001EC\u0002}\u0011\u0011!Q\t\u0003A\r\u0002\"\u0001F\u0011\n\u0005\tr!a\u0002(pi\"Lgn\u001a\t\u0003)\u0011J!!\n\b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001dO\u0011)\u0001\u0006\u0001b\u0001S\t\t1)\u0005\u0002!UA\u00121\u0006\r\t\u0004Y5zS\"\u0001\u0007\n\u00059b!\u0001D%uKJ\f'\r\\3P]\u000e,\u0007C\u0001\u000f1\t%\tt%!A\u0001\u0002\u000b\u0005qDA\u0002`IE\nQ!Z7qif\fa\u0001P5oSRtDCA\u001b7!\u0011A\u0002a\u0007\u0014\t\u000bI\u0012\u0001\u0019\u0001\u0014\u0002\u000b\u0015dW-\\:\u0016\u0003\u0019\n\u0011\"\u001a7f[N|F%Z9\u0015\u0005mr\u0004C\u0001\u000b=\u0013\tidB\u0001\u0003V]&$\bbB \u0005\u0003\u0003\u0005\rAJ\u0001\u0004q\u0012\n\u0014AB3mK6\u001c\b%A\u0003dY\u0016\f'\u000fF\u0001<\u0003\u0019\u0011Xm];miR\ta%A\u0005l]><hnU5{KV\tq\t\u0005\u0002\u0015\u0011&\u0011\u0011J\u0004\u0002\u0004\u0013:$\b")
public abstract class ImmutableBuilder<A, C extends IterableOnce<?>>
implements ReusableBuilder<A, C> {
    private final C empty;
    private C elems;

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<A, NewTo> mapResult(Function1<C, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable<A> $plus$eq(A elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public Growable<A> addAll(IterableOnce<A> xs) {
        return Growable.addAll$(this, xs);
    }

    @Override
    public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    public C elems() {
        return this.elems;
    }

    public void elems_$eq(C x$1) {
        this.elems = x$1;
    }

    @Override
    public void clear() {
        this.elems_$eq(this.empty);
    }

    @Override
    public C result() {
        return this.elems();
    }

    @Override
    public int knownSize() {
        return this.elems().knownSize();
    }

    public ImmutableBuilder(C empty) {
        this.empty = empty;
        this.elems = empty;
    }
}

