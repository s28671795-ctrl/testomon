/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import scala.collection.concurrent.MainNode;

abstract class CNodeBase<K, V>
extends MainNode<K, V> {
    public static final AtomicIntegerFieldUpdater<CNodeBase<?, ?>> updater = AtomicIntegerFieldUpdater.newUpdater(CNodeBase.class, "csize");
    public volatile int csize = -1;

    CNodeBase() {
    }

    public boolean CAS_SIZE(int oldval, int nval) {
        return updater.compareAndSet(this, oldval, nval);
    }

    public void WRITE_SIZE(int nval) {
        updater.set(this, nval);
    }

    public int READ_SIZE() {
        return updater.get(this);
    }
}

