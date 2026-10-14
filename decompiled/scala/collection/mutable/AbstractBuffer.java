/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractSeq;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Shrinkable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002)AQa\b\u0001\u0005\u0002\u0001\u0012a\"\u00112tiJ\f7\r\u001e\"vM\u001a,'O\u0003\u0002\u0005\u000b\u00059Q.\u001e;bE2,'B\u0001\u0004\b\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0011\u0005)1oY1mC\u000e\u0001QCA\u0006\u0013'\r\u0001A\u0002\b\t\u0004\u001b9\u0001R\"A\u0002\n\u0005=\u0019!aC!cgR\u0014\u0018m\u0019;TKF\u0004\"!\u0005\n\r\u0001\u0011)1\u0003\u0001b\u0001)\t\t\u0011)\u0005\u0002\u00163A\u0011acF\u0007\u0002\u000f%\u0011\u0001d\u0002\u0002\b\u001d>$\b.\u001b8h!\t1\"$\u0003\u0002\u001c\u000f\t\u0019\u0011I\\=\u0011\u00075i\u0002#\u0003\u0002\u001f\u0007\t1!)\u001e4gKJ\fa\u0001P5oSRtD#A\u0011\u0011\u00075\u0001\u0001\u0003")
public abstract class AbstractBuffer<A>
extends AbstractSeq<A>
implements Buffer<A> {
    @Override
    public SeqFactory<Buffer> iterableFactory() {
        return Buffer.iterableFactory$(this);
    }

    @Override
    public int knownSize() {
        return Buffer.knownSize$(this);
    }

    @Override
    public final Buffer<A> append(A elem) {
        return Buffer.append$((Buffer)this, elem);
    }

    @Override
    public final Buffer<A> append(Seq<A> elems) {
        return Buffer.append$((Buffer)this, elems);
    }

    @Override
    public final Buffer<A> appendAll(IterableOnce<A> xs) {
        return Buffer.appendAll$(this, xs);
    }

    @Override
    public final Buffer<A> $plus$eq$colon(A elem) {
        return Buffer.$plus$eq$colon$(this, elem);
    }

    @Override
    public Buffer<A> prependAll(IterableOnce<A> elems) {
        return Buffer.prependAll$(this, elems);
    }

    @Override
    public final Buffer<A> prepend(Seq<A> elems) {
        return Buffer.prepend$(this, elems);
    }

    @Override
    public final Buffer<A> $plus$plus$eq$colon(IterableOnce<A> elems) {
        return Buffer.$plus$plus$eq$colon$(this, elems);
    }

    @Override
    public Buffer<A> subtractOne(A x) {
        return Buffer.subtractOne$(this, x);
    }

    @Override
    public void trimStart(int n) {
        Buffer.trimStart$(this, n);
    }

    @Override
    public void trimEnd(int n) {
        Buffer.trimEnd$(this, n);
    }

    @Override
    public Buffer<A> dropInPlace(int n) {
        return Buffer.dropInPlace$(this, n);
    }

    @Override
    public Buffer<A> dropRightInPlace(int n) {
        return Buffer.dropRightInPlace$(this, n);
    }

    @Override
    public Buffer<A> takeInPlace(int n) {
        return Buffer.takeInPlace$(this, n);
    }

    @Override
    public Buffer<A> takeRightInPlace(int n) {
        return Buffer.takeRightInPlace$(this, n);
    }

    @Override
    public Buffer<A> sliceInPlace(int start2, int end) {
        return Buffer.sliceInPlace$(this, start2, end);
    }

    @Override
    public Buffer<A> dropWhileInPlace(Function1<A, Object> p) {
        return Buffer.dropWhileInPlace$(this, p);
    }

    @Override
    public Buffer<A> takeWhileInPlace(Function1<A, Object> p) {
        return Buffer.takeWhileInPlace$(this, p);
    }

    @Override
    public Buffer<A> padToInPlace(int len, A elem) {
        return Buffer.padToInPlace$(this, len, elem);
    }

    @Override
    public String stringPrefix() {
        return Buffer.stringPrefix$(this);
    }

    @Override
    public final Shrinkable<A> $minus$eq(A elem) {
        return Shrinkable.$minus$eq$(this, elem);
    }

    @Override
    public Shrinkable<A> $minus$eq(A elem1, A elem2, Seq<A> elems) {
        return Shrinkable.$minus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public Shrinkable<A> subtractAll(IterableOnce<A> xs) {
        return Shrinkable.subtractAll$(this, xs);
    }

    @Override
    public final Shrinkable<A> $minus$minus$eq(IterableOnce<A> xs) {
        return Shrinkable.$minus$minus$eq$(this, xs);
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
}

