/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function1;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.DoubleBinaryTreeStepper;

public final class DoubleBinaryTreeStepper$ {
    public static final DoubleBinaryTreeStepper$ MODULE$ = new DoubleBinaryTreeStepper$();

    public <T> DoubleBinaryTreeStepper<T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, Object> extract) {
        DoubleBinaryTreeStepper<Object> ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        ans.initialize(root, maxLength);
        return ans;
    }

    private DoubleBinaryTreeStepper$() {
    }
}

