/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

public final class SortedMapOps$ {
    public static final SortedMapOps$ MODULE$ = new SortedMapOps$();

    public final String ordMsg() {
        return "No implicit Ordering[${K2}] found to build a SortedMap[${K2}, ${V2}]. You may want to upcast to a Map[${K}, ${V}] first by calling `unsorted`.";
    }

    private SortedMapOps$() {
    }
}

