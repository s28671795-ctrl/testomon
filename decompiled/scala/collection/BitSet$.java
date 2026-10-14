/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.collection.BitSet;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;

public final class BitSet$
implements SpecificIterableFactory<Object, BitSet> {
    public static final BitSet$ MODULE$ = new BitSet$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq xs) {
        return SpecificIterableFactory.apply$(this, xs);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return SpecificIterableFactory.fill$(this, n, elem);
    }

    @Override
    public Factory<Object, BitSet> specificIterableFactory() {
        return SpecificIterableFactory.specificIterableFactory$(this);
    }

    public final String ordMsg() {
        return "No implicit Ordering[${B}] found to build a SortedSet[${B}]. You may want to upcast to a Set[Int] first by calling `unsorted`.";
    }

    public final String zipOrdMsg() {
        return "No implicit Ordering[${B}] found to build a SortedSet[(Int, ${B})]. You may want to upcast to a Set[Int] first by calling `unsorted`.";
    }

    @Override
    public BitSet empty() {
        return scala.collection.immutable.BitSet$.MODULE$.empty();
    }

    @Override
    public Builder<Object, BitSet> newBuilder() {
        return scala.collection.immutable.BitSet$.MODULE$.newBuilder();
    }

    @Override
    public BitSet fromSpecific(IterableOnce<Object> it) {
        return scala.collection.immutable.BitSet$.MODULE$.fromSpecific((IterableOnce)it);
    }

    private BitSet$() {
    }
}

