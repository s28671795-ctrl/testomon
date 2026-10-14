/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.cache;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.h2.mvstore.cache.CacheLongKeyLIRS;
import org.h2.store.fs.FileBase;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FilePathWrapper;

public class FilePathCache
extends FilePathWrapper {
    public static final FilePathCache INSTANCE = new FilePathCache();

    public static FileChannel wrap(FileChannel fileChannel) {
        return new FileCache(fileChannel);
    }

    @Override
    public FileChannel open(String string) throws IOException {
        return new FileCache(this.getBase().open(string));
    }

    @Override
    public String getScheme() {
        return "cache";
    }

    static {
        FilePath.register(INSTANCE);
    }

    public static class FileCache
    extends FileBase {
        private static final int CACHE_BLOCK_SIZE = 4096;
        private final FileChannel base;
        private final CacheLongKeyLIRS<ByteBuffer> cache;

        FileCache(FileChannel fileChannel) {
            CacheLongKeyLIRS.Config config = new CacheLongKeyLIRS.Config();
            config.maxMemory = 0x100000L;
            this.cache = new CacheLongKeyLIRS(config);
            this.base = fileChannel;
        }

        @Override
        protected void implCloseChannel() throws IOException {
            this.base.close();
        }

        @Override
        public FileChannel position(long l) throws IOException {
            this.base.position(l);
            return this;
        }

        @Override
        public long position() throws IOException {
            return this.base.position();
        }

        @Override
        public int read(ByteBuffer byteBuffer) throws IOException {
            return this.base.read(byteBuffer);
        }

        @Override
        public synchronized int read(ByteBuffer byteBuffer, long l) throws IOException {
            long l2 = FileCache.getCachePos(l);
            int n = (int)(l - l2);
            int n2 = 4096 - n;
            n2 = Math.min(n2, byteBuffer.remaining());
            ByteBuffer byteBuffer2 = this.cache.get(l2);
            if (byteBuffer2 == null) {
                int n3;
                byteBuffer2 = ByteBuffer.allocate(4096);
                long l3 = l2;
                while ((n3 = this.base.read(byteBuffer2, l3)) > 0 && byteBuffer2.remaining() != 0) {
                    l3 += (long)n3;
                }
                n3 = byteBuffer2.position();
                if (n3 == 4096) {
                    this.cache.put(l2, byteBuffer2, 4176L);
                } else {
                    if (n3 <= 0) {
                        return -1;
                    }
                    n2 = Math.min(n2, n3 - n);
                }
            }
            byteBuffer.put(byteBuffer2.array(), n, n2);
            return n2 == 0 ? -1 : n2;
        }

        private static long getCachePos(long l) {
            return l / 4096L * 4096L;
        }

        @Override
        public long size() throws IOException {
            return this.base.size();
        }

        @Override
        public synchronized FileChannel truncate(long l) throws IOException {
            this.cache.clear();
            this.base.truncate(l);
            return this;
        }

        @Override
        public synchronized int write(ByteBuffer byteBuffer, long l) throws IOException {
            this.clearCache(byteBuffer, l);
            return this.base.write(byteBuffer, l);
        }

        @Override
        public synchronized int write(ByteBuffer byteBuffer) throws IOException {
            this.clearCache(byteBuffer, this.position());
            return this.base.write(byteBuffer);
        }

        private void clearCache(ByteBuffer byteBuffer, long l) {
            if (this.cache.size() > 0) {
                long l2 = FileCache.getCachePos(l);
                for (int i = byteBuffer.remaining(); i > 0; i -= 4096) {
                    this.cache.remove(l2);
                    l2 += 4096L;
                }
            }
        }

        @Override
        public void force(boolean bl) throws IOException {
            this.base.force(bl);
        }

        @Override
        public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
            return this.base.tryLock(l, l2, bl);
        }

        public String toString() {
            return "cache:" + this.base.toString();
        }
    }
}

