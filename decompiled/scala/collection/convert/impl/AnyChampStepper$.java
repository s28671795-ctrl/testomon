/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function2;
import scala.collection.convert.impl.AnyChampStepper;
import scala.collection.immutable.Node;

public final class AnyChampStepper$ {
    public static final AnyChampStepper$ MODULE$ = new AnyChampStepper$();

    public <A, T extends Node<T>> AnyChampStepper<A, T> from(int maxSize, T root, Function2<T, Object, A> extract) {
        AnyChampStepper<A, T> ans = new AnyChampStepper<A, T>(maxSize, extract);
        ans.initRoot(root);
        return ans;
    }

    private AnyChampStepper$() {
    }
}

