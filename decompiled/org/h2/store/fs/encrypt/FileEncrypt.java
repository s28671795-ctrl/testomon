/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.encrypt;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.h2.mvstore.DataUtils;
import org.h2.security.AES;
import org.h2.security.BlockCipher;
import org.h2.security.SHA256;
import org.h2.store.fs.FileBaseDefault;
import org.h2.store.fs.encrypt.XTS;
import org.h2.util.MathUtils;

public class FileEncrypt
extends FileBaseDefault {
    public static final int BLOCK_SIZE = 4096;
    static final int BLOCK_SIZE_MASK = 4095;
    static final int HEADER_LENGTH = 4096;
    private static final byte[] HEADER = "H2encrypt\n".getBytes(StandardCharsets.ISO_8859_1);
    private static final int SALT_POS = HEADER.length;
    private static final int SALT_LENGTH = 8;
    private static final int HASH_ITERATIONS = 10;
    private final FileChannel base;
    private volatile long size;
    private final String name;
    private volatile XTS xts;
    private byte[] encryptionKey;
    private FileEncrypt source;

    public FileEncrypt(String string, byte[] byArray, FileChannel fileChannel) {
        this.name = string;
        this.base = fileChannel;
        this.encryptionKey = byArray;
    }

    public FileEncrypt(String string, FileEncrypt fileEncrypt, FileChannel fileChannel) {
        this.name = string;
        this.base = fileChannel;
        this.source = fileEncrypt;
        try {
            fileEncrypt.init();
        }
        catch (IOException iOException) {
            throw DataUtils.newMVStoreException(3, "Can not open {0} using encryption of {1}", string, fileEncrypt.name);
        }
    }

    private XTS init() throws IOException {
        XTS xTS = this.xts;
        if (xTS == null) {
            xTS = this.createXTS();
        }
        return xTS;
    }

    private synchronized XTS createXTS() throws IOException {
        boolean bl;
        XTS xTS = this.xts;
        if (xTS != null) {
            return xTS;
        }
        assert (this.size == 0L);
        long l = this.base.size() - 4096L;
        boolean bl2 = bl = l >= 0L;
        if (this.encryptionKey != null) {
            Object object;
            byte[] byArray;
            if (bl) {
                byArray = new byte[8];
                FileEncrypt.readFully(this.base, SALT_POS, ByteBuffer.wrap(byArray));
            } else {
                object = Arrays.copyOf(HEADER, 4096);
                byArray = MathUtils.secureRandomBytes(8);
                System.arraycopy(byArray, 0, object, SALT_POS, byArray.length);
                FileEncrypt.writeFully(this.base, 0L, ByteBuffer.wrap((byte[])object));
            }
            object = new AES();
            ((AES)object).setKey(SHA256.getPBKDF2(this.encryptionKey, byArray, 10, 16));
            this.encryptionKey = null;
            xTS = new XTS((BlockCipher)object);
        } else {
            if (!bl) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
                FileEncrypt.readFully(this.source.base, 0L, byteBuffer);
                byteBuffer.flip();
                FileEncrypt.writeFully(this.base, 0L, byteBuffer);
            }
            xTS = this.source.xts;
            this.source = null;
        }
        if (bl) {
            if ((l & 0xFFFL) != 0L) {
                l -= 4096L;
            }
            this.size = l;
        }
        this.xts = xTS;
        return this.xts;
    }

    @Override
    protected void implCloseChannel() throws IOException {
        this.base.close();
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        int n = byteBuffer.remaining();
        if (n == 0) {
            return 0;
        }
        XTS xTS = this.init();
        n = (int)Math.min((long)n, this.size - l);
        if (l >= this.size) {
            return -1;
        }
        if (l < 0L) {
            throw new IllegalArgumentException("pos: " + l);
        }
        if ((l & 0xFFFL) != 0L || (n & 0xFFF) != 0) {
            long l2 = l / 4096L * 4096L;
            int n2 = (int)(l - l2);
            int n3 = (n + n2 + 4096 - 1) / 4096 * 4096;
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(n3);
            this.readInternal(byteBuffer2, l2, n3, xTS);
            byteBuffer2.flip().limit(n2 + n).position(n2);
            byteBuffer.put(byteBuffer2);
            return n;
        }
        this.readInternal(byteBuffer, l, n, xTS);
        return n;
    }

    private void readInternal(ByteBuffer byteBuffer, long l, int n, XTS xTS) throws IOException {
        int n2 = byteBuffer.position();
        FileEncrypt.readFully(this.base, l + 4096L, byteBuffer);
        long l2 = l / 4096L;
        while (n > 0) {
            xTS.decrypt(l2++, 4096, byteBuffer.array(), byteBuffer.arrayOffset() + n2);
            n2 += 4096;
            n -= 4096;
        }
    }

    private static void readFully(FileChannel fileChannel, long l, ByteBuffer byteBuffer) throws IOException {
        do {
            int n;
            if ((n = fileChannel.read(byteBuffer, l)) < 0) {
                throw new EOFException();
            }
            l += (long)n;
        } while (byteBuffer.remaining() > 0);
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        XTS xTS = this.init();
        int n = byteBuffer.remaining();
        if ((l & 0xFFFL) != 0L || (n & 0xFFF) != 0) {
            long l2 = l / 4096L * 4096L;
            int n2 = (int)(l - l2);
            int n3 = (n + n2 + 4096 - 1) / 4096 * 4096;
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(n3);
            int n4 = (int)(this.size - l2 + 4096L - 1L) / 4096 * 4096;
            int n5 = Math.min(n3, n4);
            if (n5 > 0) {
                this.readInternal(byteBuffer2, l2, n5, xTS);
                byteBuffer2.rewind();
            }
            byteBuffer2.limit(n2 + n).position(n2);
            byteBuffer2.put(byteBuffer).limit(n3).rewind();
            this.writeInternal(byteBuffer2, l2, n3, xTS);
            long l3 = l + (long)n;
            this.size = Math.max(this.size, l3);
            int n6 = (int)(this.size & 0xFFFL);
            if (n6 > 0) {
                byteBuffer2 = ByteBuffer.allocate(n6);
                FileEncrypt.writeFully(this.base, l2 + 4096L + (long)n3, byteBuffer2);
            }
            return n;
        }
        this.writeInternal(byteBuffer, l, n, xTS);
        long l4 = l + (long)n;
        this.size = Math.max(this.size, l4);
        return n;
    }

    private void writeInternal(ByteBuffer byteBuffer, long l, int n, XTS xTS) throws IOException {
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(n).put(byteBuffer);
        byteBuffer2.flip();
        long l2 = l / 4096L;
        int n2 = 0;
        for (int i = n; i > 0; i -= 4096) {
            xTS.encrypt(l2++, 4096, byteBuffer2.array(), byteBuffer2.arrayOffset() + n2);
            n2 += 4096;
        }
        FileEncrypt.writeFully(this.base, l + 4096L, byteBuffer2);
    }

    private static void writeFully(FileChannel fileChannel, long l, ByteBuffer byteBuffer) throws IOException {
        do {
            l += (long)fileChannel.write(byteBuffer, l);
        } while (byteBuffer.remaining() > 0);
    }

    @Override
    public long size() throws IOException {
        this.init();
        return this.size;
    }

    @Override
    protected void implTruncate(long l) throws IOException {
        this.init();
        if (l > this.size) {
            return;
        }
        if (l < 0L) {
            throw new IllegalArgumentException("newSize: " + l);
        }
        int n = (int)(l & 0xFFFL);
        if (n > 0) {
            this.base.truncate(l + 4096L + 4096L);
        } else {
            this.base.truncate(l + 4096L);
        }
        this.size = l;
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
        return this.name;
    }
}

