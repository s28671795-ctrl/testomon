/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function2;
import scala.collection.convert.impl.IntChampStepper;
import scala.collection.immutable.Node;

public final class IntChampStepper$ {
    public static final IntChampStepper$ MODULE$ = new IntChampStepper$();

    public <T extends Node<T>> IntChampStepper<T> from(int maxSize, T root, Function2<T, Object, Object> extract) {
        IntChampStepper<T> ans = new IntChampStepper<T>(maxSize, extract);
        ans.initRoot(root);
        return ans;
    }

    private IntChampStepper$() {
    }
}

