/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.util.Map;
import org.h2.mvstore.Chunk;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;

final class MFChunk
extends Chunk<MFChunk> {
    private static final String ATTR_VOLUME = "vol";
    public volatile int volumeId;

    MFChunk(int n) {
        super(n);
    }

    MFChunk(String string) {
        super(string);
    }

    MFChunk(Map<String, String> map) {
        super(map, false);
        this.volumeId = DataUtils.readHexInt(map, ATTR_VOLUME, 0);
    }

    @Override
    protected ByteBuffer readFully(FileStore<MFChunk> fileStore, long l, int n) {
        return fileStore.readFully(this, l, n);
    }

    @Override
    protected void dump(StringBuilder stringBuilder) {
        super.dump(stringBuilder);
        if (this.volumeId != 0) {
            DataUtils.appendMap(stringBuilder, ATTR_VOLUME, this.volumeId);
        }
    }
}

