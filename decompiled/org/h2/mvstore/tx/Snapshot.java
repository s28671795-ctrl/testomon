/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.BitSet;
import org.h2.mvstore.RootReference;

final class Snapshot<K, V> {
    final RootReference<K, V> root;
    final BitSet committingTransactions;

    Snapshot(RootReference<K, V> rootReference, BitSet bitSet) {
        this.root = rootReference;
        this.committingTransactions = bitSet;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.committingTransactions.hashCode();
        n = 31 * n + this.root.hashCode();
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Snapshot)) {
            return false;
        }
        Snapshot snapshot = (Snapshot)object;
        return this.committingTransactions == snapshot.committingTransactions && this.root == snapshot.root;
    }
}

