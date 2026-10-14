/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

public final class SortedSetOps$ {
    public static final SortedSetOps$ MODULE$ = new SortedSetOps$();

    public final String ordMsg() {
        return "No implicit Ordering[${B}] found to build a SortedSet[${B}]. You may want to upcast to a Set[${A}] first by calling `unsorted`.";
    }

    public final String zipOrdMsg() {
        return "No implicit Ordering[${B}] found to build a SortedSet[(${A}, ${B})]. You may want to upcast to a Set[${A}] first by calling `unsorted`.";
    }

    private SortedSetOps$() {
    }
}

