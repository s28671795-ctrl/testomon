/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.nio.ByteBuffer;
import org.jvnet.mimepull.Data;
import org.jvnet.mimepull.DataHead;

final class Chunk {
    volatile Chunk next;
    volatile Data data;

    public Chunk(Data data) {
        this.data = data;
    }

    public Chunk createNext(DataHead dataHead, ByteBuffer buf) {
        this.next = new Chunk(this.data.createNext(dataHead, buf));
        return this.next;
    }
}

