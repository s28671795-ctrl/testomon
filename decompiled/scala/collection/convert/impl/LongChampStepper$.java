/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function2;
import scala.collection.convert.impl.LongChampStepper;
import scala.collection.immutable.Node;

public final class LongChampStepper$ {
    public static final LongChampStepper$ MODULE$ = new LongChampStepper$();

    public <T extends Node<T>> LongChampStepper<T> from(int maxSize, T root, Function2<T, Object, Object> extract) {
        LongChampStepper<T> ans = new LongChampStepper<T>(maxSize, extract);
        ans.initRoot(root);
        return ans;
    }

    private LongChampStepper$() {
    }
}

