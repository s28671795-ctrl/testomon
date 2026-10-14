/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.collection.AbstractIterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SetOps;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u00111\"\u00112tiJ\f7\r^*fi*\u0011A!B\u0001\u000bG>dG.Z2uS>t'\"\u0001\u0004\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0011\u0002E\n\u0004\u0001)Q\u0002cA\u0006\r\u001d5\t1!\u0003\u0002\u000e\u0007\t\u0001\u0012IY:ue\u0006\u001cG/\u0013;fe\u0006\u0014G.\u001a\t\u0003\u001fAa\u0001\u0001B\u0003\u0012\u0001\t\u0007!CA\u0001B#\t\u0019r\u0003\u0005\u0002\u0015+5\tQ!\u0003\u0002\u0017\u000b\t9aj\u001c;iS:<\u0007C\u0001\u000b\u0019\u0013\tIRAA\u0002B]f\u00042aC\u000e\u000f\u0013\ta2AA\u0002TKR\fa\u0001P5oSRtD#A\u0010\u0011\u0007-\u0001a\u0002")
public abstract class AbstractSet<A>
extends AbstractIterable<A>
implements Set<A> {
    @Override
    public boolean canEqual(Object that) {
        return Set.canEqual$(this, that);
    }

    @Override
    public boolean equals(Object that) {
        return Set.equals$(this, that);
    }

    @Override
    public int hashCode() {
        return Set.hashCode$(this);
    }

    @Override
    public IterableFactory<Set> iterableFactory() {
        return Set.iterableFactory$(this);
    }

    @Override
    public String stringPrefix() {
        return Set.stringPrefix$(this);
    }

    @Override
    public String toString() {
        return Set.toString$(this);
    }

    @Override
    public final boolean apply(A elem) {
        return SetOps.apply$(this, elem);
    }

    @Override
    public boolean subsetOf(Set<A> that) {
        return SetOps.subsetOf$(this, that);
    }

    @Override
    public Iterator<Set<A>> subsets(int len) {
        return SetOps.subsets$(this, len);
    }

    @Override
    public Iterator<Set<A>> subsets() {
        return SetOps.subsets$(this);
    }

    @Override
    public SetOps intersect(Set that) {
        return SetOps.intersect$(this, that);
    }

    @Override
    public final SetOps $amp(Set that) {
        return SetOps.$amp$(this, that);
    }

    @Override
    public final SetOps $amp$tilde(Set that) {
        return SetOps.$amp$tilde$(this, that);
    }

    @Override
    public SetOps $minus$minus(IterableOnce that) {
        return SetOps.$minus$minus$(this, that);
    }

    @Override
    public SetOps $minus(Object elem) {
        return SetOps.$minus$(this, elem);
    }

    @Override
    public SetOps $minus(Object elem1, Object elem2, Seq elems) {
        return SetOps.$minus$(this, elem1, elem2, elems);
    }

    @Override
    public SetOps concat(IterableOnce that) {
        return SetOps.concat$(this, that);
    }

    @Override
    public SetOps $plus(Object elem) {
        return SetOps.$plus$(this, elem);
    }

    @Override
    public SetOps $plus(Object elem1, Object elem2, Seq elems) {
        return SetOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public final SetOps $plus$plus(IterableOnce that) {
        return SetOps.$plus$plus$(this, that);
    }

    @Override
    public final SetOps union(Set that) {
        return SetOps.union$(this, that);
    }

    @Override
    public final SetOps $bar(Set that) {
        return SetOps.$bar$(this, that);
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
    public <A$> Function1<A$, Object> compose(Function1<A$, A> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A$> Function1<A, A$> andThen(Function1<Object, A$> g2) {
        return Function1.andThen$(this, g2);
    }
}

