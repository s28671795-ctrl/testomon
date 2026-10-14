/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

public final class IndexedSeqDefaults$ {
    public static final IndexedSeqDefaults$ MODULE$ = new IndexedSeqDefaults$();
    private static final int defaultApplyPreferredMaxLength = IndexedSeqDefaults$.liftedTree1$1();

    public int defaultApplyPreferredMaxLength() {
        return defaultApplyPreferredMaxLength;
    }

    private static final /* synthetic */ int liftedTree1$1() {
        int n;
        try {
            n = Integer.parseInt(System.getProperty("scala.collection.immutable.IndexedSeq.defaultApplyPreferredMaxLength", "64"));
        }
        catch (SecurityException securityException) {
            n = 64;
        }
        return n;
    }

    private IndexedSeqDefaults$() {
    }
}

