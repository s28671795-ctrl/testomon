/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Set;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.SetOps;
import scala.collection.mutable.Shrinkable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002)AQa\b\u0001\u0005\u0002\u0001\u00121\"\u00112tiJ\f7\r^*fi*\u0011A!B\u0001\b[V$\u0018M\u00197f\u0015\t1q!\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tY\u0011cE\u0002\u0001\u0019m\u00012!\u0004\b\u0010\u001b\u0005)\u0011B\u0001\u0002\u0006!\t\u0001\u0012\u0003\u0004\u0001\u0005\u000bI\u0001!\u0019A\n\u0003\u0003\u0005\u000b\"\u0001\u0006\r\u0011\u0005U1R\"A\u0004\n\u0005]9!a\u0002(pi\"Lgn\u001a\t\u0003+eI!AG\u0004\u0003\u0007\u0005s\u0017\u0010E\u0002\u001d;=i\u0011aA\u0005\u0003=\r\u00111aU3u\u0003\u0019a\u0014N\\5u}Q\t\u0011\u0005E\u0002\u001d\u0001=\u0001")
public abstract class AbstractSet<A>
extends scala.collection.AbstractSet<A>
implements scala.collection.mutable.Set<A> {
    @Override
    public IterableFactory<scala.collection.mutable.Set> iterableFactory() {
        return scala.collection.mutable.Set.iterableFactory$(this);
    }

    @Override
    public SetOps result() {
        return SetOps.result$(this);
    }

    @Override
    public boolean add(A elem) {
        return SetOps.add$(this, elem);
    }

    @Override
    public void update(A elem, boolean included) {
        SetOps.update$(this, elem, included);
    }

    @Override
    public boolean remove(A elem) {
        return SetOps.remove$(this, elem);
    }

    @Override
    public SetOps diff(Set that) {
        return SetOps.diff$(this, that);
    }

    @Override
    public final void retain(Function1<A, Object> p) {
        SetOps.retain$(this, p);
    }

    @Override
    public SetOps<A, scala.collection.mutable.Set, scala.collection.mutable.Set<A>> filterInPlace(Function1<A, Object> p) {
        return SetOps.filterInPlace$(this, p);
    }

    @Override
    public SetOps clone() {
        return SetOps.clone$(this);
    }

    @Override
    public int knownSize() {
        return SetOps.knownSize$(this);
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
    public <NewTo> Builder<A, NewTo> mapResult(Function1<scala.collection.mutable.Set<A>, NewTo> f) {
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

    @Override
    public /* synthetic */ Object scala$collection$mutable$Cloneable$$super$clone() {
        return super.clone();
    }
}

