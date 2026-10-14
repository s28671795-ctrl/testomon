/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.io.BaseEncoding;
import io.minio.ByteBufferStream;
import io.minio.PartSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;

class PartReader {
    private static final long CHUNK_SIZE = Integer.MAX_VALUE;
    private byte[] buf16k = new byte[16384];
    private RandomAccessFile file;
    private InputStream stream;
    private long objectSize;
    private long partSize;
    private int partCount;
    private int partNumber;
    private long totalDataRead;
    private ByteBufferStream[] buffers;
    private byte[] oneByte = null;
    boolean eof;

    private PartReader(long objectSize, long partSize, int partCount) {
        this.objectSize = objectSize;
        this.partSize = partSize;
        this.partCount = partCount;
        long bufferCount = partSize / Integer.MAX_VALUE;
        if (partSize - bufferCount * Integer.MAX_VALUE > 0L) {
            ++bufferCount;
        }
        if (bufferCount == 0L) {
            ++bufferCount;
        }
        this.buffers = new ByteBufferStream[(int)bufferCount];
    }

    public PartReader(@Nonnull RandomAccessFile file, long objectSize, long partSize, int partCount) {
        this(objectSize, partSize, partCount);
        this.file = Objects.requireNonNull(file, "file must not be null");
        if (this.objectSize < 0L) {
            throw new IllegalArgumentException("object size must be provided");
        }
    }

    public PartReader(@Nonnull InputStream stream, long objectSize, long partSize, int partCount) {
        this(objectSize, partSize, partCount);
        this.stream = Objects.requireNonNull(stream, "stream must not be null");
        for (int i = 0; i < this.buffers.length; ++i) {
            this.buffers[i] = new ByteBufferStream();
        }
    }

    private long readStreamChunk(ByteBufferStream buffer, long size, MessageDigest sha256) throws IOException {
        long totalBytesRead = 0L;
        if (this.oneByte != null) {
            buffer.write(this.oneByte);
            sha256.update(this.oneByte);
            ++totalBytesRead;
            this.oneByte = null;
        }
        while (totalBytesRead < size) {
            int bytesRead;
            long bytesToRead = size - totalBytesRead;
            if (bytesToRead > (long)this.buf16k.length) {
                bytesToRead = this.buf16k.length;
            }
            boolean bl = this.eof = (bytesRead = this.stream.read(this.buf16k, 0, (int)bytesToRead)) < 0;
            if (this.eof) {
                if (this.objectSize < 0L) break;
                throw new IOException("unexpected EOF");
            }
            buffer.write(this.buf16k, 0, bytesRead);
            sha256.update(this.buf16k, 0, bytesRead);
            totalBytesRead += (long)bytesRead;
        }
        return totalBytesRead;
    }

    private long readStream(long size, MessageDigest sha256) throws IOException {
        long count = size / Integer.MAX_VALUE;
        long lastChunkSize = size - count * Integer.MAX_VALUE;
        if (lastChunkSize > 0L) {
            ++count;
        } else {
            lastChunkSize = Integer.MAX_VALUE;
        }
        long totalBytesRead = 0L;
        for (int i = 0; i < this.buffers.length; ++i) {
            this.buffers[i].reset();
        }
        for (long i = 1L; i <= count && !this.eof; ++i) {
            long chunkSize = i != count ? Integer.MAX_VALUE : lastChunkSize;
            long bytesRead = this.readStreamChunk(this.buffers[(int)(i - 1L)], chunkSize, sha256);
            totalBytesRead += bytesRead;
        }
        if (!this.eof && this.objectSize < 0L) {
            this.oneByte = new byte[1];
            this.eof = this.stream.read(this.oneByte) < 0;
        }
        return totalBytesRead;
    }

    private long readFile(long size, MessageDigest sha256) throws IOException {
        long totalBytesRead;
        int bytesRead;
        long position = this.file.getFilePointer();
        for (totalBytesRead = 0L; totalBytesRead < size; totalBytesRead += (long)bytesRead) {
            long bytesToRead = size - totalBytesRead;
            if (bytesToRead > (long)this.buf16k.length) {
                bytesToRead = this.buf16k.length;
            }
            if ((bytesRead = this.file.read(this.buf16k, 0, (int)bytesToRead)) < 0) {
                throw new IOException("unexpected EOF");
            }
            sha256.update(this.buf16k, 0, bytesRead);
        }
        this.file.seek(position);
        return totalBytesRead;
    }

    private long read(long size, MessageDigest sha256) throws IOException {
        return this.file != null ? this.readFile(size, sha256) : this.readStream(size, sha256);
    }

    public PartSource getPart() throws NoSuchAlgorithmException, IOException {
        if (this.partNumber == this.partCount) {
            return null;
        }
        ++this.partNumber;
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        long partSize = this.partSize;
        if (this.partNumber == this.partCount) {
            partSize = this.objectSize - this.totalDataRead;
        }
        long bytesRead = this.read(partSize, sha256);
        this.totalDataRead += bytesRead;
        if (this.objectSize < 0L && this.eof) {
            this.partCount = this.partNumber;
        }
        String sha256Hash = BaseEncoding.base16().encode(sha256.digest()).toLowerCase(Locale.US);
        if (this.file != null) {
            return new PartSource(this.partNumber, this.file, bytesRead, null, sha256Hash);
        }
        return new PartSource(this.partNumber, this.buffers, bytesRead, null, sha256Hash);
    }

    public int partCount() {
        return this.partCount;
    }
}

