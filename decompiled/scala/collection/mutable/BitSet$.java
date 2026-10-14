/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import scala.Function0;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.BitSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class BitSet$
implements SpecificIterableFactory<Object, BitSet>,
Serializable {
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

    @Override
    public BitSet fromSpecific(IterableOnce<Object> it) {
        return (BitSet)new BitSet().addAll(it);
    }

    @Override
    public BitSet empty() {
        return new BitSet();
    }

    @Override
    public Builder<Object, BitSet> newBuilder() {
        return new GrowableBuilder<Object, BitSet>(new BitSet());
    }

    public BitSet fromBitMask(long[] elems) {
        int len = elems.length;
        if (len == 0) {
            return new BitSet();
        }
        long[] a = Arrays.copyOf(elems, len);
        return new BitSet(a);
    }

    public BitSet fromBitMaskNoCopy(long[] elems) {
        if (elems.length == 0) {
            return new BitSet();
        }
        return new BitSet(elems);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BitSet$.class);
    }

    private BitSet$() {
    }
}

