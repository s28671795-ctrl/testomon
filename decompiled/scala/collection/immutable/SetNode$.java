/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.BitmapIndexedSetNode;
import scala.reflect.ClassTag$;
import scala.runtime.Nothing$;

public final class SetNode$ {
    public static final SetNode$ MODULE$ = new SetNode$();
    private static final BitmapIndexedSetNode<Nothing$> EmptySetNode = new BitmapIndexedSetNode(0, 0, (Object[])ClassTag$.MODULE$.Any().newArray(0), new int[0], 0, 0);

    private final BitmapIndexedSetNode<Nothing$> EmptySetNode() {
        return EmptySetNode;
    }

    public <A> BitmapIndexedSetNode<A> empty() {
        return this.EmptySetNode();
    }

    public final int TupleLength() {
        return 1;
    }

    private SetNode$() {
    }
}

