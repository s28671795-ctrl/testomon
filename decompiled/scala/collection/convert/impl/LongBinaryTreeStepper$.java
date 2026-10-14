/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function1;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.LongBinaryTreeStepper;

public final class LongBinaryTreeStepper$ {
    public static final LongBinaryTreeStepper$ MODULE$ = new LongBinaryTreeStepper$();

    public <T> LongBinaryTreeStepper<T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, Object> extract) {
        LongBinaryTreeStepper<Object> ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        ans.initialize(root, maxLength);
        return ans;
    }

    private LongBinaryTreeStepper$() {
    }
}

