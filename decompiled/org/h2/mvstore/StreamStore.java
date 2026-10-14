/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVStoreException;

public final class StreamStore {
    private final Map<Long, byte[]> map;
    private final int minBlockSize;
    private final int maxBlockSize;
    private final AtomicLong nextKey = new AtomicLong();
    private final AtomicReference<byte[]> nextBuffer = new AtomicReference();
    private final IntConsumer onStoreCallback;

    public StreamStore(Map<Long, byte[]> map) {
        this(map, 256, 262144, null);
    }

    public StreamStore(Map<Long, byte[]> map, int n, int n2) {
        this(map, n, n2, null);
    }

    public StreamStore(Map<Long, byte[]> map, IntConsumer intConsumer) {
        this(map, 256, 262144, intConsumer);
    }

    public StreamStore(Map<Long, byte[]> map, int n, int n2, IntConsumer intConsumer) {
        this.map = map;
        this.minBlockSize = n;
        this.maxBlockSize = n2;
        this.onStoreCallback = intConsumer;
    }

    public Map<Long, byte[]> getMap() {
        return this.map;
    }

    public void setNextKey(long l) {
        this.nextKey.set(l);
    }

    public long getNextKey() {
        return this.nextKey.get();
    }

    public int getMinBlockSize() {
        return this.minBlockSize;
    }

    public long getMaxBlockSize() {
        return this.maxBlockSize;
    }

    public byte[] put(InputStream inputStream2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        try {
            while (!this.put(byteArrayOutputStream, inputStream2, n)) {
                if (byteArrayOutputStream.size() <= this.maxBlockSize / 2) continue;
                byteArrayOutputStream = this.putIndirectId(byteArrayOutputStream);
                ++n;
            }
        }
        catch (IOException iOException) {
            this.remove(byteArrayOutputStream.toByteArray());
            throw iOException;
        }
        if (byteArrayOutputStream.size() > this.minBlockSize * 2) {
            byteArrayOutputStream = this.putIndirectId(byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private boolean put(ByteArrayOutputStream byteArrayOutputStream, InputStream inputStream2, int n) throws IOException {
        boolean bl;
        int n2;
        byte[] byArray;
        if (n > 0) {
            boolean bl2;
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            do {
                bl2 = this.put(byteArrayOutputStream2, inputStream2, n - 1);
                if (byteArrayOutputStream2.size() <= this.maxBlockSize / 2) continue;
                byteArrayOutputStream2 = this.putIndirectId(byteArrayOutputStream2);
                byteArrayOutputStream2.writeTo(byteArrayOutputStream);
                return bl2;
            } while (!bl2);
            byteArrayOutputStream2.writeTo(byteArrayOutputStream);
            return true;
        }
        byte[] byArray2 = this.nextBuffer.getAndSet(null);
        if (byArray2 == null) {
            byArray2 = new byte[this.maxBlockSize];
        }
        if ((byArray = StreamStore.read(inputStream2, byArray2)) != byArray2) {
            this.nextBuffer.set(byArray2);
        }
        if ((n2 = byArray.length) == 0) {
            return true;
        }
        boolean bl3 = bl = n2 < this.maxBlockSize;
        if (n2 < this.minBlockSize) {
            byteArrayOutputStream.write(0);
            DataUtils.writeVarInt(byteArrayOutputStream, n2);
            byteArrayOutputStream.write(byArray);
        } else {
            byteArrayOutputStream.write(1);
            DataUtils.writeVarInt(byteArrayOutputStream, n2);
            DataUtils.writeVarLong(byteArrayOutputStream, this.writeBlock(byArray));
        }
        return bl;
    }

    private static byte[] read(InputStream inputStream2, byte[] byArray) throws IOException {
        int n;
        int n2 = 0;
        for (int i = byArray.length; i > 0; i -= n) {
            try {
                n = inputStream2.read(byArray, n2, i);
                if (n < 0) {
                    return Arrays.copyOf(byArray, n2);
                }
                n2 += n;
                continue;
            }
            catch (RuntimeException runtimeException) {
                throw new IOException(runtimeException);
            }
        }
        return byArray;
    }

    private ByteArrayOutputStream putIndirectId(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        byte[] byArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(2);
        DataUtils.writeVarLong(byteArrayOutputStream, this.length(byArray));
        DataUtils.writeVarLong(byteArrayOutputStream, this.writeBlock(byArray));
        return byteArrayOutputStream;
    }

    private long writeBlock(byte[] byArray) {
        long l = this.getAndIncrementNextKey();
        this.map.put(l, byArray);
        if (this.onStoreCallback != null) {
            this.onStoreCallback.accept(byArray.length);
        }
        return l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long getAndIncrementNextKey() {
        long l = this.nextKey.getAndIncrement();
        if (!this.map.containsKey(l)) {
            return l;
        }
        StreamStore streamStore = this;
        synchronized (streamStore) {
            long l2 = l;
            long l3 = Long.MAX_VALUE;
            while (l2 < l3) {
                long l4 = l2 + l3 >>> 1;
                if (this.map.containsKey(l4)) {
                    l2 = l4 + 1L;
                    continue;
                }
                l3 = l4;
            }
            l = l2;
            this.nextKey.set(l + 1L);
            return l;
        }
    }

    public long getMaxBlockKey(byte[] byArray) {
        long l = -1L;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        block5: while (byteBuffer.hasRemaining()) {
            switch (byteBuffer.get()) {
                case 0: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    byteBuffer.position(byteBuffer.position() + n);
                    continue block5;
                }
                case 1: {
                    DataUtils.readVarInt(byteBuffer);
                    long l2 = DataUtils.readVarLong(byteBuffer);
                    l = Math.max(l, l2);
                    continue block5;
                }
                case 2: {
                    long l3;
                    DataUtils.readVarLong(byteBuffer);
                    l = l3 = DataUtils.readVarLong(byteBuffer);
                    byte[] byArray2 = this.map.get(l3);
                    long l4 = this.getMaxBlockKey(byArray2);
                    if (l4 < 0L) continue block5;
                    l = Math.max(l, l4);
                    continue block5;
                }
            }
            throw DataUtils.newIllegalArgumentException("Unsupported id {0}", Arrays.toString(byArray));
        }
        return l;
    }

    public void remove(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        block5: while (byteBuffer.hasRemaining()) {
            switch (byteBuffer.get()) {
                case 0: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    byteBuffer.position(byteBuffer.position() + n);
                    continue block5;
                }
                case 1: {
                    DataUtils.readVarInt(byteBuffer);
                    long l = DataUtils.readVarLong(byteBuffer);
                    this.map.remove(l);
                    continue block5;
                }
                case 2: {
                    DataUtils.readVarLong(byteBuffer);
                    long l = DataUtils.readVarLong(byteBuffer);
                    this.remove(this.map.get(l));
                    this.map.remove(l);
                    continue block5;
                }
            }
            throw DataUtils.newIllegalArgumentException("Unsupported id {0}", Arrays.toString(byArray));
        }
    }

    public static String toString(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        long l = 0L;
        while (byteBuffer.hasRemaining()) {
            switch (byteBuffer.get()) {
                case 0: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    byteBuffer.position(byteBuffer.position() + n);
                    stringBuilder.append("data len=").append(n);
                    l += (long)n;
                    break;
                }
                case 1: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    l += (long)n;
                    long l2 = DataUtils.readVarLong(byteBuffer);
                    stringBuilder.append("block ").append(l2).append(" len=").append(n);
                    break;
                }
                case 2: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    l += DataUtils.readVarLong(byteBuffer);
                    long l2 = DataUtils.readVarLong(byteBuffer);
                    stringBuilder.append("indirect block ").append(l2).append(" len=").append(n);
                    break;
                }
                default: {
                    stringBuilder.append("error");
                }
            }
            stringBuilder.append(", ");
        }
        stringBuilder.append("length=").append(l);
        return stringBuilder.toString();
    }

    public long length(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        long l = 0L;
        block5: while (byteBuffer.hasRemaining()) {
            switch (byteBuffer.get()) {
                case 0: {
                    int n = DataUtils.readVarInt(byteBuffer);
                    byteBuffer.position(byteBuffer.position() + n);
                    l += (long)n;
                    continue block5;
                }
                case 1: {
                    l += (long)DataUtils.readVarInt(byteBuffer);
                    DataUtils.readVarLong(byteBuffer);
                    continue block5;
                }
                case 2: {
                    l += DataUtils.readVarLong(byteBuffer);
                    DataUtils.readVarLong(byteBuffer);
                    continue block5;
                }
            }
            throw DataUtils.newIllegalArgumentException("Unsupported id {0}", Arrays.toString(byArray));
        }
        return l;
    }

    public boolean isInPlace(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        while (byteBuffer.hasRemaining()) {
            if (byteBuffer.get() != 0) {
                return false;
            }
            int n = DataUtils.readVarInt(byteBuffer);
            byteBuffer.position(byteBuffer.position() + n);
        }
        return true;
    }

    public InputStream get(byte[] byArray) {
        return new Stream(this, byArray);
    }

    byte[] getBlock(long l) {
        byte[] byArray = this.map.get(l);
        if (byArray == null) {
            throw DataUtils.newMVStoreException(50, "Block {0} not found", l);
        }
        return byArray;
    }

    static class Stream
    extends InputStream {
        private final StreamStore store;
        private byte[] oneByteBuffer;
        private ByteBuffer idBuffer;
        private ByteArrayInputStream buffer;
        private long skip;
        private final long length;
        private long pos;

        Stream(StreamStore streamStore, byte[] byArray) {
            this.store = streamStore;
            this.length = streamStore.length(byArray);
            this.idBuffer = ByteBuffer.wrap(byArray);
        }

        @Override
        public int read() throws IOException {
            int n;
            byte[] byArray = this.oneByteBuffer;
            if (byArray == null) {
                byArray = this.oneByteBuffer = new byte[1];
            }
            return (n = this.read(byArray, 0, 1)) == -1 ? -1 : byArray[0] & 0xFF;
        }

        @Override
        public long skip(long l) {
            if ((l = Math.min(this.length - this.pos, l)) == 0L) {
                return 0L;
            }
            if (this.buffer != null) {
                long l2 = this.buffer.skip(l);
                if (l2 > 0L) {
                    l = l2;
                } else {
                    this.buffer = null;
                    this.skip += l;
                }
            } else {
                this.skip += l;
            }
            this.pos += l;
            return l;
        }

        @Override
        public void close() {
            this.buffer = null;
            this.idBuffer.position(this.idBuffer.limit());
            this.pos = this.length;
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (n2 <= 0) {
                return 0;
            }
            while (true) {
                int n3;
                if (this.buffer == null) {
                    try {
                        this.buffer = this.nextBuffer();
                    }
                    catch (MVStoreException mVStoreException) {
                        String string = DataUtils.formatMessage(50, "Block not found in id {0}", Arrays.toString(this.idBuffer.array()));
                        throw new IOException(string, mVStoreException);
                    }
                    if (this.buffer == null) {
                        return -1;
                    }
                }
                if ((n3 = this.buffer.read(byArray, n, n2)) > 0) {
                    this.pos += (long)n3;
                    return n3;
                }
                this.buffer = null;
            }
        }

        private ByteArrayInputStream nextBuffer() {
            block5: while (this.idBuffer.hasRemaining()) {
                switch (this.idBuffer.get()) {
                    case 0: {
                        int n = DataUtils.readVarInt(this.idBuffer);
                        if (this.skip >= (long)n) {
                            this.skip -= (long)n;
                            this.idBuffer.position(this.idBuffer.position() + n);
                            continue block5;
                        }
                        int n2 = (int)((long)this.idBuffer.position() + this.skip);
                        int n3 = (int)((long)n - this.skip);
                        this.idBuffer.position(n2 + n3);
                        return new ByteArrayInputStream(this.idBuffer.array(), n2, n3);
                    }
                    case 1: {
                        int n = DataUtils.readVarInt(this.idBuffer);
                        long l = DataUtils.readVarLong(this.idBuffer);
                        if (this.skip >= (long)n) {
                            this.skip -= (long)n;
                            continue block5;
                        }
                        byte[] byArray = this.store.getBlock(l);
                        int n4 = (int)this.skip;
                        this.skip = 0L;
                        return new ByteArrayInputStream(byArray, n4, byArray.length - n4);
                    }
                    case 2: {
                        long l = DataUtils.readVarLong(this.idBuffer);
                        long l2 = DataUtils.readVarLong(this.idBuffer);
                        if (this.skip >= l) {
                            this.skip -= l;
                            continue block5;
                        }
                        byte[] byArray = this.store.getBlock(l2);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray.length + this.idBuffer.limit() - this.idBuffer.position());
                        byteBuffer.put(byArray);
                        byteBuffer.put(this.idBuffer);
                        byteBuffer.flip();
                        this.idBuffer = byteBuffer;
                        return this.nextBuffer();
                    }
                }
                throw DataUtils.newIllegalArgumentException("Unsupported id {0}", Arrays.toString(this.idBuffer.array()));
            }
            return null;
        }
    }
}

