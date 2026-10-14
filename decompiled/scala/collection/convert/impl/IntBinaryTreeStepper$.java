/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function1;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.IntBinaryTreeStepper;

public final class IntBinaryTreeStepper$ {
    public static final IntBinaryTreeStepper$ MODULE$ = new IntBinaryTreeStepper$();

    public <T> IntBinaryTreeStepper<T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, Object> extract) {
        IntBinaryTreeStepper<Object> ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        ans.initialize(root, maxLength);
        return ans;
    }

    private IntBinaryTreeStepper$() {
    }
}

