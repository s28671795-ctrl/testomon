/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipOutputStream;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.RandomAccessStore;
import org.h2.mvstore.SFChunk;

public class OffHeapStore
extends RandomAccessStore {
    private final TreeMap<Long, ByteBuffer> memory = new TreeMap();

    public OffHeapStore() {
        super(new HashMap<String, Object>());
    }

    @Override
    public void open(String string, boolean bl, char[] cArray) {
        this.init();
    }

    public OffHeapStore open(String string, boolean bl) {
        OffHeapStore offHeapStore = new OffHeapStore();
        offHeapStore.init();
        return offHeapStore;
    }

    private void init() {
        this.memory.clear();
    }

    public String toString() {
        return this.memory.toString();
    }

    @Override
    public ByteBuffer readFully(SFChunk sFChunk, long l, int n) {
        Map.Entry<Long, ByteBuffer> entry = this.memory.floorEntry(l);
        if (entry == null) {
            throw DataUtils.newMVStoreException(1, "Could not read from position {0}", l);
        }
        this.readCount.incrementAndGet();
        this.readBytes.addAndGet(n);
        ByteBuffer byteBuffer = entry.getValue();
        ByteBuffer byteBuffer2 = byteBuffer.duplicate();
        int n2 = (int)(l - entry.getKey());
        byteBuffer2.position(n2);
        byteBuffer2.limit(n + n2);
        return byteBuffer2.slice();
    }

    @Override
    public void free(long l, int n) {
        super.free(l, n);
        ByteBuffer byteBuffer = this.memory.remove(l);
        if (byteBuffer != null && byteBuffer.remaining() != n) {
            throw DataUtils.newMVStoreException(1, "Partial remove is not supported at position {0}", l);
        }
    }

    @Override
    public void writeFully(SFChunk sFChunk, long l, ByteBuffer byteBuffer) {
        this.setSize(Math.max(this.size(), l + (long)byteBuffer.remaining()));
        Map.Entry<Long, ByteBuffer> entry = this.memory.floorEntry(l);
        if (entry == null) {
            this.writeNewEntry(l, byteBuffer);
            return;
        }
        long l2 = entry.getKey();
        ByteBuffer byteBuffer2 = entry.getValue();
        int n = byteBuffer2.capacity();
        int n2 = byteBuffer.remaining();
        if (l2 == l) {
            if (n != n2) {
                throw DataUtils.newMVStoreException(1, "Could not write to position {0}; partial overwrite is not supported", l);
            }
            this.writeCount.incrementAndGet();
            this.writeBytes.addAndGet(n2);
            byteBuffer2.rewind();
            byteBuffer2.put(byteBuffer);
            return;
        }
        if (l2 + (long)n > l) {
            throw DataUtils.newMVStoreException(1, "Could not write to position {0}; partial overwrite is not supported", l);
        }
        this.writeNewEntry(l, byteBuffer);
    }

    private void writeNewEntry(long l, ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining();
        this.writeCount.incrementAndGet();
        this.writeBytes.addAndGet(n);
        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(n);
        byteBuffer2.put(byteBuffer);
        byteBuffer2.rewind();
        this.memory.put(l, byteBuffer2);
    }

    @Override
    public void truncate(long l) {
        this.writeCount.incrementAndGet();
        this.setSize(l);
        if (l == 0L) {
            this.memory.clear();
        } else {
            long l2;
            Iterator<Long> iterator2 = this.memory.keySet().iterator();
            while (iterator2.hasNext() && (l2 = iterator2.next().longValue()) >= l) {
                ByteBuffer byteBuffer = this.memory.get(l2);
                if ((long)byteBuffer.capacity() > l) {
                    throw DataUtils.newMVStoreException(1, "Could not truncate to {0}; partial truncate is not supported", l2);
                }
                iterator2.remove();
            }
        }
    }

    @Override
    public int getDefaultRetentionTime() {
        return 0;
    }

    @Override
    public void backup(ZipOutputStream zipOutputStream) {
        throw new UnsupportedOperationException();
    }
}

