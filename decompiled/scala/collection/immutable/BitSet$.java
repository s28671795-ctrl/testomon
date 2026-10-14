/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.Arrays;
import scala.Function0;
import scala.Function1;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.BitSet;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.runtime.ModuleSerializationProxy;

public final class BitSet$
implements SpecificIterableFactory<Object, BitSet>,
Serializable {
    public static final BitSet$ MODULE$ = new BitSet$();
    private static final long serialVersionUID = 3L;
    private static final BitSet empty = new BitSet.BitSet1(0L);

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
        if (it instanceof BitSet) {
            return (BitSet)it;
        }
        Builder<Object, BitSet> builder = this.newBuilder();
        if (builder == null) {
            throw null;
        }
        return (BitSet)((Builder)builder.addAll(it)).result();
    }

    @Override
    public final BitSet empty() {
        return empty;
    }

    @Override
    public Builder<Object, BitSet> newBuilder() {
        return scala.collection.mutable.BitSet$.MODULE$.newBuilder().mapResult((Function1<scala.collection.mutable.BitSet, BitSet> & Serializable)bs -> MODULE$.fromBitMaskNoCopy(bs.elems()));
    }

    public BitSet scala$collection$immutable$BitSet$$createSmall(long a, long b) {
        if (b == 0L) {
            return new BitSet.BitSet1(a);
        }
        return new BitSet.BitSet2(a, b);
    }

    public BitSet fromBitMask(long[] elems) {
        int len = elems.length;
        if (len == 0) {
            return this.empty();
        }
        if (len == 1) {
            return new BitSet.BitSet1(elems[0]);
        }
        if (len == 2) {
            return this.scala$collection$immutable$BitSet$$createSmall(elems[0], elems[1]);
        }
        long[] a = Arrays.copyOf(elems, len);
        return new BitSet.BitSetN(a);
    }

    public BitSet fromBitMaskNoCopy(long[] elems) {
        int len = elems.length;
        if (len == 0) {
            return this.empty();
        }
        if (len == 1) {
            return new BitSet.BitSet1(elems[0]);
        }
        if (len == 2) {
            return this.scala$collection$immutable$BitSet$$createSmall(elems[0], elems[1]);
        }
        return new BitSet.BitSetN(elems);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BitSet$.class);
    }

    private BitSet$() {
    }
}

