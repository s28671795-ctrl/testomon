/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function2;
import scala.collection.convert.impl.DoubleChampStepper;
import scala.collection.immutable.Node;

public final class DoubleChampStepper$ {
    public static final DoubleChampStepper$ MODULE$ = new DoubleChampStepper$();

    public <T extends Node<T>> DoubleChampStepper<T> from(int maxSize, T root, Function2<T, Object, Object> extract) {
        DoubleChampStepper<T> ans = new DoubleChampStepper<T>(maxSize, extract);
        ans.initRoot(root);
        return ans;
    }

    private DoubleChampStepper$() {
    }
}

