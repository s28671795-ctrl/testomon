/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.BitmapIndexedMapNode;
import scala.reflect.ClassTag$;
import scala.runtime.Nothing$;

public final class MapNode$ {
    public static final MapNode$ MODULE$ = new MapNode$();
    private static final BitmapIndexedMapNode<Nothing$, Nothing$> EmptyMapNode = new BitmapIndexedMapNode(0, 0, (Object[])ClassTag$.MODULE$.Any().newArray(0), new int[0], 0, 0);

    private final BitmapIndexedMapNode<Nothing$, Nothing$> EmptyMapNode() {
        return EmptyMapNode;
    }

    public <K, V> BitmapIndexedMapNode<K, V> empty() {
        return this.EmptyMapNode();
    }

    public final int TupleLength() {
        return 2;
    }

    private MapNode$() {
    }
}

