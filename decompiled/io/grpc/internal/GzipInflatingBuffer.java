/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.internal.CompositeReadableBuffer;
import io.grpc.internal.ReadableBuffer;
import java.io.Closeable;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class GzipInflatingBuffer
implements Closeable {
    private static final int INFLATE_BUFFER_SIZE = 512;
    private static final int UNSIGNED_SHORT_SIZE = 2;
    private static final int GZIP_MAGIC = 35615;
    private static final int GZIP_HEADER_MIN_SIZE = 10;
    private static final int GZIP_TRAILER_SIZE = 8;
    private static final int HEADER_CRC_FLAG = 2;
    private static final int HEADER_EXTRA_FLAG = 4;
    private static final int HEADER_NAME_FLAG = 8;
    private static final int HEADER_COMMENT_FLAG = 16;
    private final CompositeReadableBuffer gzippedData = new CompositeReadableBuffer();
    private final CRC32 crc = new CRC32();
    private final GzipMetadataReader gzipMetadataReader = new GzipMetadataReader();
    private final byte[] inflaterInput = new byte[512];
    private int inflaterInputStart;
    private int inflaterInputEnd;
    private Inflater inflater;
    private State state = State.HEADER;
    private boolean closed = false;
    private int gzipHeaderFlag;
    private int headerExtraToRead;
    private long expectedGzipTrailerIsize;
    private int bytesConsumed = 0;
    private int deflatedBytesConsumed = 0;
    private boolean isStalled = true;

    GzipInflatingBuffer() {
    }

    boolean isStalled() {
        Preconditions.checkState(!this.closed, "GzipInflatingBuffer is closed");
        return this.isStalled;
    }

    boolean hasPartialData() {
        Preconditions.checkState(!this.closed, "GzipInflatingBuffer is closed");
        return this.gzipMetadataReader.readableBytes() != 0 || this.state != State.HEADER;
    }

    void addGzippedBytes(ReadableBuffer buffer) {
        Preconditions.checkState(!this.closed, "GzipInflatingBuffer is closed");
        this.gzippedData.addBuffer(buffer);
        this.isStalled = false;
    }

    @Override
    public void close() {
        if (!this.closed) {
            this.closed = true;
            this.gzippedData.close();
            if (this.inflater != null) {
                this.inflater.end();
                this.inflater = null;
            }
        }
    }

    int getAndResetBytesConsumed() {
        int savedBytesConsumed = this.bytesConsumed;
        this.bytesConsumed = 0;
        return savedBytesConsumed;
    }

    int getAndResetDeflatedBytesConsumed() {
        int savedDeflatedBytesConsumed = this.deflatedBytesConsumed;
        this.deflatedBytesConsumed = 0;
        return savedDeflatedBytesConsumed;
    }

    int inflateBytes(byte[] b, int offset, int length) throws DataFormatException, ZipException {
        int missingBytes;
        Preconditions.checkState(!this.closed, "GzipInflatingBuffer is closed");
        int bytesRead = 0;
        boolean madeProgress = true;
        block12: while (madeProgress && (missingBytes = length - bytesRead) > 0) {
            switch (this.state) {
                case HEADER: {
                    madeProgress = this.processHeader();
                    continue block12;
                }
                case HEADER_EXTRA_LEN: {
                    madeProgress = this.processHeaderExtraLen();
                    continue block12;
                }
                case HEADER_EXTRA: {
                    madeProgress = this.processHeaderExtra();
                    continue block12;
                }
                case HEADER_NAME: {
                    madeProgress = this.processHeaderName();
                    continue block12;
                }
                case HEADER_COMMENT: {
                    madeProgress = this.processHeaderComment();
                    continue block12;
                }
                case HEADER_CRC: {
                    madeProgress = this.processHeaderCrc();
                    continue block12;
                }
                case INITIALIZE_INFLATER: {
                    madeProgress = this.initializeInflater();
                    continue block12;
                }
                case INFLATING: {
                    bytesRead += this.inflate(b, offset + bytesRead, missingBytes);
                    if (this.state == State.TRAILER) {
                        madeProgress = this.processTrailer();
                        continue block12;
                    }
                    madeProgress = true;
                    continue block12;
                }
                case INFLATER_NEEDS_INPUT: {
                    madeProgress = this.fill();
                    continue block12;
                }
                case TRAILER: {
                    madeProgress = this.processTrailer();
                    continue block12;
                }
            }
            throw new AssertionError((Object)("Invalid state: " + (Object)((Object)this.state)));
        }
        this.isStalled = !madeProgress || this.state == State.HEADER && this.gzipMetadataReader.readableBytes() < 10;
        return bytesRead;
    }

    private boolean processHeader() throws ZipException {
        if (this.gzipMetadataReader.readableBytes() < 10) {
            return false;
        }
        if (this.gzipMetadataReader.readUnsignedShort() != 35615) {
            throw new ZipException("Not in GZIP format");
        }
        if (this.gzipMetadataReader.readUnsignedByte() != 8) {
            throw new ZipException("Unsupported compression method");
        }
        this.gzipHeaderFlag = this.gzipMetadataReader.readUnsignedByte();
        this.gzipMetadataReader.skipBytes(6);
        this.state = State.HEADER_EXTRA_LEN;
        return true;
    }

    private boolean processHeaderExtraLen() {
        if ((this.gzipHeaderFlag & 4) != 4) {
            this.state = State.HEADER_NAME;
            return true;
        }
        if (this.gzipMetadataReader.readableBytes() < 2) {
            return false;
        }
        this.headerExtraToRead = this.gzipMetadataReader.readUnsignedShort();
        this.state = State.HEADER_EXTRA;
        return true;
    }

    private boolean processHeaderExtra() {
        if (this.gzipMetadataReader.readableBytes() < this.headerExtraToRead) {
            return false;
        }
        this.gzipMetadataReader.skipBytes(this.headerExtraToRead);
        this.state = State.HEADER_NAME;
        return true;
    }

    private boolean processHeaderName() {
        if ((this.gzipHeaderFlag & 8) != 8) {
            this.state = State.HEADER_COMMENT;
            return true;
        }
        if (!this.gzipMetadataReader.readBytesUntilZero()) {
            return false;
        }
        this.state = State.HEADER_COMMENT;
        return true;
    }

    private boolean processHeaderComment() {
        if ((this.gzipHeaderFlag & 0x10) != 16) {
            this.state = State.HEADER_CRC;
            return true;
        }
        if (!this.gzipMetadataReader.readBytesUntilZero()) {
            return false;
        }
        this.state = State.HEADER_CRC;
        return true;
    }

    private boolean processHeaderCrc() throws ZipException {
        if ((this.gzipHeaderFlag & 2) != 2) {
            this.state = State.INITIALIZE_INFLATER;
            return true;
        }
        if (this.gzipMetadataReader.readableBytes() < 2) {
            return false;
        }
        int desiredCrc16 = (int)this.crc.getValue() & 0xFFFF;
        if (desiredCrc16 != this.gzipMetadataReader.readUnsignedShort()) {
            throw new ZipException("Corrupt GZIP header");
        }
        this.state = State.INITIALIZE_INFLATER;
        return true;
    }

    private boolean initializeInflater() {
        if (this.inflater == null) {
            this.inflater = new Inflater(true);
        } else {
            this.inflater.reset();
        }
        this.crc.reset();
        int bytesRemainingInInflaterInput = this.inflaterInputEnd - this.inflaterInputStart;
        if (bytesRemainingInInflaterInput > 0) {
            this.inflater.setInput(this.inflaterInput, this.inflaterInputStart, bytesRemainingInInflaterInput);
            this.state = State.INFLATING;
        } else {
            this.state = State.INFLATER_NEEDS_INPUT;
        }
        return true;
    }

    private int inflate(byte[] b, int off, int len) throws DataFormatException, ZipException {
        Preconditions.checkState(this.inflater != null, "inflater is null");
        try {
            int inflaterTotalIn = this.inflater.getTotalIn();
            int n = this.inflater.inflate(b, off, len);
            int bytesConsumedDelta = this.inflater.getTotalIn() - inflaterTotalIn;
            this.bytesConsumed += bytesConsumedDelta;
            this.deflatedBytesConsumed += bytesConsumedDelta;
            this.inflaterInputStart += bytesConsumedDelta;
            this.crc.update(b, off, n);
            if (this.inflater.finished()) {
                this.expectedGzipTrailerIsize = this.inflater.getBytesWritten() & 0xFFFFFFFFL;
                this.state = State.TRAILER;
            } else if (this.inflater.needsInput()) {
                this.state = State.INFLATER_NEEDS_INPUT;
            }
            return n;
        }
        catch (DataFormatException e) {
            throw new DataFormatException("Inflater data format exception: " + e.getMessage());
        }
    }

    private boolean fill() {
        Preconditions.checkState(this.inflater != null, "inflater is null");
        Preconditions.checkState(this.inflaterInputStart == this.inflaterInputEnd, "inflaterInput has unconsumed bytes");
        int bytesToAdd = Math.min(this.gzippedData.readableBytes(), 512);
        if (bytesToAdd == 0) {
            return false;
        }
        this.inflaterInputStart = 0;
        this.inflaterInputEnd = bytesToAdd;
        this.gzippedData.readBytes(this.inflaterInput, this.inflaterInputStart, bytesToAdd);
        this.inflater.setInput(this.inflaterInput, this.inflaterInputStart, bytesToAdd);
        this.state = State.INFLATING;
        return true;
    }

    private boolean processTrailer() throws ZipException {
        if (this.inflater != null && this.gzipMetadataReader.readableBytes() <= 18) {
            this.inflater.end();
            this.inflater = null;
        }
        if (this.gzipMetadataReader.readableBytes() < 8) {
            return false;
        }
        if (this.crc.getValue() != this.gzipMetadataReader.readUnsignedInt() || this.expectedGzipTrailerIsize != this.gzipMetadataReader.readUnsignedInt()) {
            throw new ZipException("Corrupt GZIP trailer");
        }
        this.crc.reset();
        this.state = State.HEADER;
        return true;
    }

    private static enum State {
        HEADER,
        HEADER_EXTRA_LEN,
        HEADER_EXTRA,
        HEADER_NAME,
        HEADER_COMMENT,
        HEADER_CRC,
        INITIALIZE_INFLATER,
        INFLATING,
        INFLATER_NEEDS_INPUT,
        TRAILER;

    }

    private class GzipMetadataReader {
        private GzipMetadataReader() {
        }

        private int readUnsignedByte() {
            int b;
            int bytesRemainingInInflaterInput = GzipInflatingBuffer.this.inflaterInputEnd - GzipInflatingBuffer.this.inflaterInputStart;
            if (bytesRemainingInInflaterInput > 0) {
                b = GzipInflatingBuffer.this.inflaterInput[GzipInflatingBuffer.this.inflaterInputStart] & 0xFF;
                GzipInflatingBuffer.this.inflaterInputStart += 1;
            } else {
                b = GzipInflatingBuffer.this.gzippedData.readUnsignedByte();
            }
            GzipInflatingBuffer.this.crc.update(b);
            GzipInflatingBuffer.this.bytesConsumed += 1;
            return b;
        }

        private void skipBytes(int length) {
            int bytesToSkip = length;
            int bytesRemainingInInflaterInput = GzipInflatingBuffer.this.inflaterInputEnd - GzipInflatingBuffer.this.inflaterInputStart;
            if (bytesRemainingInInflaterInput > 0) {
                int bytesToGetFromInflaterInput = Math.min(bytesRemainingInInflaterInput, bytesToSkip);
                GzipInflatingBuffer.this.crc.update(GzipInflatingBuffer.this.inflaterInput, GzipInflatingBuffer.this.inflaterInputStart, bytesToGetFromInflaterInput);
                GzipInflatingBuffer.this.inflaterInputStart += bytesToGetFromInflaterInput;
                bytesToSkip -= bytesToGetFromInflaterInput;
            }
            if (bytesToSkip > 0) {
                int toRead;
                byte[] buf = new byte[512];
                for (int total = 0; total < bytesToSkip; total += toRead) {
                    toRead = Math.min(bytesToSkip - total, buf.length);
                    GzipInflatingBuffer.this.gzippedData.readBytes(buf, 0, toRead);
                    GzipInflatingBuffer.this.crc.update(buf, 0, toRead);
                }
            }
            GzipInflatingBuffer.this.bytesConsumed += length;
        }

        private int readableBytes() {
            return GzipInflatingBuffer.this.inflaterInputEnd - GzipInflatingBuffer.this.inflaterInputStart + GzipInflatingBuffer.this.gzippedData.readableBytes();
        }

        private boolean readBytesUntilZero() {
            while (this.readableBytes() > 0) {
                if (this.readUnsignedByte() != 0) continue;
                return true;
            }
            return false;
        }

        private int readUnsignedShort() {
            return this.readUnsignedByte() | this.readUnsignedByte() << 8;
        }

        private long readUnsignedInt() {
            long s2 = this.readUnsignedShort();
            return (long)this.readUnsignedShort() << 16 | s2;
        }
    }
}

