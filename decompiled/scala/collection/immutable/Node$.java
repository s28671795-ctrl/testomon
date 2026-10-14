/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

public final class Node$ {
    public static final Node$ MODULE$ = new Node$();
    private static final int MaxDepth = (int)Math.ceil((double)32 / (double)5);

    public final int HashCodeLength() {
        return 32;
    }

    public final int BitPartitionSize() {
        return 5;
    }

    public final int BitPartitionMask() {
        return 31;
    }

    public final int MaxDepth() {
        return MaxDepth;
    }

    public final int BranchingFactor() {
        return 32;
    }

    public final int maskFrom(int hash, int shift) {
        return hash >>> shift & 0x1F;
    }

    public final int bitposFrom(int mask) {
        return 1 << mask;
    }

    public final int indexFrom(int bitmap, int bitpos) {
        return Integer.bitCount(bitmap & bitpos - 1);
    }

    public final int indexFrom(int bitmap, int mask, int bitpos) {
        if (bitmap == -1) {
            return mask;
        }
        return Integer.bitCount(bitmap & bitpos - 1);
    }

    private Node$() {
    }
}

