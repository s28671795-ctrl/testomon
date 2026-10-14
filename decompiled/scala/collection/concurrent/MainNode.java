/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import scala.collection.concurrent.BasicNode;

abstract class MainNode<K, V>
extends BasicNode {
    public static final AtomicReferenceFieldUpdater<MainNode<?, ?>, MainNode<?, ?>> updater = AtomicReferenceFieldUpdater.newUpdater(MainNode.class, MainNode.class, "prev");
    public volatile MainNode<K, V> prev = null;

    MainNode() {
    }

    public abstract int cachedSize(Object var1);

    public abstract int knownSize();

    public boolean CAS_PREV(MainNode<K, V> oldval, MainNode<K, V> nval) {
        return updater.compareAndSet(this, oldval, nval);
    }

    public void WRITE_PREV(MainNode<K, V> nval) {
        updater.set(this, nval);
    }

    @Deprecated
    public MainNode<K, V> READ_PREV() {
        return updater.get(this);
    }
}

