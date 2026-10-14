/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.generic.BitOperations;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMap$Nil$;

public final class IntMapUtils$
implements BitOperations.Int {
    public static final IntMapUtils$ MODULE$ = new IntMapUtils$();

    @Override
    public boolean zero(int i, int mask) {
        return BitOperations.Int.zero$(this, i, mask);
    }

    @Override
    public int mask(int i, int mask) {
        return BitOperations.Int.mask$(this, i, mask);
    }

    @Override
    public boolean hasMatch(int key, int prefix, int m4) {
        return BitOperations.Int.hasMatch$(this, key, prefix, m4);
    }

    @Override
    public boolean unsignedCompare(int i, int j) {
        return BitOperations.Int.unsignedCompare$(this, i, j);
    }

    @Override
    public boolean shorter(int m1, int m22) {
        return BitOperations.Int.shorter$(this, m1, m22);
    }

    @Override
    public int complement(int i) {
        return BitOperations.Int.complement$(this, i);
    }

    @Override
    public IndexedSeq<Object> bits(int num) {
        return BitOperations.Int.bits$(this, num);
    }

    @Override
    public String bitString(int num, String sep) {
        return BitOperations.Int.bitString$(this, num, sep);
    }

    @Override
    public String bitString$default$2() {
        return BitOperations.Int.bitString$default$2$(this);
    }

    @Override
    public int highestOneBit(int j) {
        return BitOperations.Int.highestOneBit$(this, j);
    }

    public int branchMask(int i, int j) {
        return Integer.highestOneBit(i ^ j);
    }

    public <T> IntMap<T> join(int p1, IntMap<T> t1, int p2, IntMap<T> t2) {
        int m4 = Integer.highestOneBit(p1 ^ p2);
        int complement_i = m4 - 1;
        int p = p1 & (0xFFFFFFFF ^ complement_i ^ m4);
        if (BitOperations.Int.zero$(this, p1, m4)) {
            return new IntMap.Bin<T>(p, m4, t1, t2);
        }
        return new IntMap.Bin<T>(p, m4, t2, t1);
    }

    public <T> IntMap<T> bin(int prefix, int mask, IntMap<T> left, IntMap<T> right) {
        if (((Object)IntMap$Nil$.MODULE$).equals(right)) {
            return left;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(left)) {
            return right;
        }
        return new IntMap.Bin<T>(prefix, mask, left, right);
    }

    private IntMapUtils$() {
    }
}

