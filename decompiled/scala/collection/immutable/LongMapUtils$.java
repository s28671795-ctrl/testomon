/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.generic.BitOperations;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMap$Nil$;

public final class LongMapUtils$
implements BitOperations.Long {
    public static final LongMapUtils$ MODULE$ = new LongMapUtils$();

    @Override
    public boolean zero(long i, long mask) {
        return BitOperations.Long.zero$(this, i, mask);
    }

    @Override
    public long mask(long i, long mask) {
        return BitOperations.Long.mask$(this, i, mask);
    }

    @Override
    public boolean hasMatch(long key, long prefix, long m4) {
        return BitOperations.Long.hasMatch$(this, key, prefix, m4);
    }

    @Override
    public boolean unsignedCompare(long i, long j) {
        return BitOperations.Long.unsignedCompare$(this, i, j);
    }

    @Override
    public boolean shorter(long m1, long m22) {
        return BitOperations.Long.shorter$(this, m1, m22);
    }

    @Override
    public long complement(long i) {
        return BitOperations.Long.complement$(this, i);
    }

    @Override
    public IndexedSeq<Object> bits(long num) {
        return BitOperations.Long.bits$(this, num);
    }

    @Override
    public String bitString(long num, String sep) {
        return BitOperations.Long.bitString$(this, num, sep);
    }

    @Override
    public String bitString$default$2() {
        return BitOperations.Long.bitString$default$2$(this);
    }

    @Override
    public long highestOneBit(long j) {
        return BitOperations.Long.highestOneBit$(this, j);
    }

    public long branchMask(long i, long j) {
        return Long.highestOneBit(i ^ j);
    }

    public <T> LongMap<T> join(long p1, LongMap<T> t1, long p2, LongMap<T> t2) {
        long m4 = Long.highestOneBit(p1 ^ p2);
        long complement_i = m4 - 1L;
        long p = p1 & (0xFFFFFFFFFFFFFFFFL ^ complement_i ^ m4);
        if (BitOperations.Long.zero$(this, p1, m4)) {
            return new LongMap.Bin<T>(p, m4, t1, t2);
        }
        return new LongMap.Bin<T>(p, m4, t2, t1);
    }

    public <T> LongMap<T> bin(long prefix, long mask, LongMap<T> left, LongMap<T> right) {
        if (((Object)LongMap$Nil$.MODULE$).equals(right)) {
            return left;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(left)) {
            return right;
        }
        return new LongMap.Bin<T>(prefix, mask, left, right);
    }

    private LongMapUtils$() {
    }
}

