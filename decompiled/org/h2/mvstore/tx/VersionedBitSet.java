/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.util.BitSet;

final class VersionedBitSet
extends BitSet {
    private static final long serialVersionUID = 1L;
    private long version;

    public long getVersion() {
        return this.version;
    }

    public void setVersion(long l) {
        this.version = l;
    }

    @Override
    public VersionedBitSet clone() {
        return (VersionedBitSet)super.clone();
    }
}

