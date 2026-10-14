/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.util.Map;
import org.h2.mvstore.Chunk;
import org.h2.mvstore.FileStore;

final class SFChunk
extends Chunk<SFChunk> {
    SFChunk(int n) {
        super(n);
    }

    SFChunk(String string) {
        super(string);
    }

    SFChunk(Map<String, String> map) {
        super(map, false);
    }

    @Override
    protected ByteBuffer readFully(FileStore<SFChunk> fileStore, long l, int n) {
        return fileStore.readFully(this, l, n);
    }
}

