/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.Function1;
import scala.collection.convert.impl.AnyBinaryTreeStepper;
import scala.collection.convert.impl.BinaryTreeStepper$;

public final class AnyBinaryTreeStepper$ {
    public static final AnyBinaryTreeStepper$ MODULE$ = new AnyBinaryTreeStepper$();

    public <A, T> AnyBinaryTreeStepper<A, T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, A> extract) {
        AnyBinaryTreeStepper<A, Object> ans = new AnyBinaryTreeStepper<A, Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        ans.initialize(root, maxLength);
        return ans;
    }

    private AnyBinaryTreeStepper$() {
    }
}

