/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Codec;
import io.grpc.Decompressor;
import io.grpc.Status;
import io.grpc.internal.CompositeReadableBuffer;
import io.grpc.internal.Deframer;
import io.grpc.internal.GzipInflatingBuffer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.ReadableBuffers;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.StreamListener;
import io.grpc.internal.TransportTracer;
import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.DataFormatException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class MessageDeframer
implements Closeable,
Deframer {
    private static final int HEADER_LENGTH = 5;
    private static final int COMPRESSED_FLAG_MASK = 1;
    private static final int RESERVED_MASK = 254;
    private static final int MAX_BUFFER_SIZE = 0x200000;
    private Listener listener;
    private int maxInboundMessageSize;
    private final StatsTraceContext statsTraceCtx;
    private final TransportTracer transportTracer;
    private Decompressor decompressor;
    private GzipInflatingBuffer fullStreamDecompressor;
    private byte[] inflatedBuffer;
    private int inflatedIndex;
    private State state = State.HEADER;
    private int requiredLength = 5;
    private boolean compressedFlag;
    private CompositeReadableBuffer nextFrame;
    private CompositeReadableBuffer unprocessed = new CompositeReadableBuffer();
    private long pendingDeliveries;
    private boolean inDelivery = false;
    private int currentMessageSeqNo = -1;
    private int inboundBodyWireSize;
    private boolean closeWhenComplete = false;
    private volatile boolean stopDelivery = false;

    public MessageDeframer(Listener listener, Decompressor decompressor, int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer) {
        this.listener = Preconditions.checkNotNull(listener, "sink");
        this.decompressor = Preconditions.checkNotNull(decompressor, "decompressor");
        this.maxInboundMessageSize = maxMessageSize;
        this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
        this.transportTracer = Preconditions.checkNotNull(transportTracer, "transportTracer");
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void setMaxInboundMessageSize(int messageSize) {
        this.maxInboundMessageSize = messageSize;
    }

    @Override
    public void setDecompressor(Decompressor decompressor) {
        Preconditions.checkState(this.fullStreamDecompressor == null, "Already set full stream decompressor");
        this.decompressor = Preconditions.checkNotNull(decompressor, "Can't pass an empty decompressor");
    }

    @Override
    public void setFullStreamDecompressor(GzipInflatingBuffer fullStreamDecompressor) {
        Preconditions.checkState(this.decompressor == Codec.Identity.NONE, "per-message decompressor already set");
        Preconditions.checkState(this.fullStreamDecompressor == null, "full stream decompressor already set");
        this.fullStreamDecompressor = Preconditions.checkNotNull(fullStreamDecompressor, "Can't pass a null full stream decompressor");
        this.unprocessed = null;
    }

    @Override
    public void request(int numMessages) {
        Preconditions.checkArgument(numMessages > 0, "numMessages must be > 0");
        if (this.isClosed()) {
            return;
        }
        this.pendingDeliveries += (long)numMessages;
        this.deliver();
    }

    @Override
    public void deframe(ReadableBuffer data) {
        Preconditions.checkNotNull(data, "data");
        boolean needToCloseData = true;
        try {
            if (!this.isClosedOrScheduledToClose()) {
                if (this.fullStreamDecompressor != null) {
                    this.fullStreamDecompressor.addGzippedBytes(data);
                } else {
                    this.unprocessed.addBuffer(data);
                }
                needToCloseData = false;
                this.deliver();
            }
        }
        finally {
            if (needToCloseData) {
                data.close();
            }
        }
    }

    @Override
    public void closeWhenComplete() {
        if (this.isClosed()) {
            return;
        }
        if (this.isStalled()) {
            this.close();
        } else {
            this.closeWhenComplete = true;
        }
    }

    void stopDelivery() {
        this.stopDelivery = true;
    }

    boolean hasPendingDeliveries() {
        return this.pendingDeliveries != 0L;
    }

    @Override
    public void close() {
        if (this.isClosed()) {
            return;
        }
        boolean hasPartialMessage = this.nextFrame != null && this.nextFrame.readableBytes() > 0;
        try {
            if (this.fullStreamDecompressor != null) {
                hasPartialMessage = hasPartialMessage || this.fullStreamDecompressor.hasPartialData();
                this.fullStreamDecompressor.close();
            }
            if (this.unprocessed != null) {
                this.unprocessed.close();
            }
            if (this.nextFrame != null) {
                this.nextFrame.close();
            }
        }
        finally {
            this.fullStreamDecompressor = null;
            this.unprocessed = null;
            this.nextFrame = null;
        }
        this.listener.deframerClosed(hasPartialMessage);
    }

    public boolean isClosed() {
        return this.unprocessed == null && this.fullStreamDecompressor == null;
    }

    private boolean isClosedOrScheduledToClose() {
        return this.isClosed() || this.closeWhenComplete;
    }

    private boolean isStalled() {
        if (this.fullStreamDecompressor != null) {
            return this.fullStreamDecompressor.isStalled();
        }
        return this.unprocessed.readableBytes() == 0;
    }

    private void deliver() {
        if (this.inDelivery) {
            return;
        }
        this.inDelivery = true;
        try {
            block8: while (!this.stopDelivery && this.pendingDeliveries > 0L && this.readRequiredBytes()) {
                switch (this.state) {
                    case HEADER: {
                        this.processHeader();
                        continue block8;
                    }
                    case BODY: {
                        this.processBody();
                        --this.pendingDeliveries;
                        continue block8;
                    }
                }
                throw new AssertionError((Object)("Invalid state: " + (Object)((Object)this.state)));
            }
            if (this.stopDelivery) {
                this.close();
                return;
            }
            if (this.closeWhenComplete && this.isStalled()) {
                this.close();
            }
        }
        finally {
            this.inDelivery = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean readRequiredBytes() {
        int totalBytesRead = 0;
        int deflatedBytesRead = 0;
        try {
            int missingBytes;
            if (this.nextFrame == null) {
                this.nextFrame = new CompositeReadableBuffer();
            }
            while ((missingBytes = this.requiredLength - this.nextFrame.readableBytes()) > 0) {
                if (this.fullStreamDecompressor != null) {
                    int n;
                    block18: {
                        if (this.inflatedBuffer == null || this.inflatedIndex == this.inflatedBuffer.length) {
                            this.inflatedBuffer = new byte[Math.min(missingBytes, 0x200000)];
                            this.inflatedIndex = 0;
                        }
                        int bytesToRead = Math.min(missingBytes, this.inflatedBuffer.length - this.inflatedIndex);
                        n = this.fullStreamDecompressor.inflateBytes(this.inflatedBuffer, this.inflatedIndex, bytesToRead);
                        totalBytesRead += this.fullStreamDecompressor.getAndResetBytesConsumed();
                        deflatedBytesRead += this.fullStreamDecompressor.getAndResetDeflatedBytesConsumed();
                        if (n != 0) break block18;
                        boolean bl = false;
                        return bl;
                    }
                    try {
                        this.nextFrame.addBuffer(ReadableBuffers.wrap(this.inflatedBuffer, this.inflatedIndex, n));
                        this.inflatedIndex += n;
                        continue;
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    catch (DataFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (this.unprocessed.readableBytes() == 0) {
                    boolean e = false;
                    return e;
                }
                int toRead = Math.min(missingBytes, this.unprocessed.readableBytes());
                totalBytesRead += toRead;
                this.nextFrame.addBuffer(this.unprocessed.readBytes(toRead));
            }
            boolean bl = true;
            return bl;
        }
        finally {
            if (totalBytesRead > 0) {
                this.listener.bytesRead(totalBytesRead);
                if (this.state == State.BODY) {
                    if (this.fullStreamDecompressor != null) {
                        this.statsTraceCtx.inboundWireSize(deflatedBytesRead);
                        this.inboundBodyWireSize += deflatedBytesRead;
                    } else {
                        this.statsTraceCtx.inboundWireSize(totalBytesRead);
                        this.inboundBodyWireSize += totalBytesRead;
                    }
                }
            }
        }
    }

    private void processHeader() {
        int type = this.nextFrame.readUnsignedByte();
        if ((type & 0xFE) != 0) {
            throw Status.INTERNAL.withDescription("gRPC frame header malformed: reserved bits not zero").asRuntimeException();
        }
        this.compressedFlag = (type & 1) != 0;
        this.requiredLength = this.nextFrame.readInt();
        if (this.requiredLength < 0 || this.requiredLength > this.maxInboundMessageSize) {
            throw Status.RESOURCE_EXHAUSTED.withDescription(String.format(Locale.US, "gRPC message exceeds maximum size %d: %d", this.maxInboundMessageSize, this.requiredLength)).asRuntimeException();
        }
        ++this.currentMessageSeqNo;
        this.statsTraceCtx.inboundMessage(this.currentMessageSeqNo);
        this.transportTracer.reportMessageReceived();
        this.state = State.BODY;
    }

    private void processBody() {
        this.statsTraceCtx.inboundMessageRead(this.currentMessageSeqNo, this.inboundBodyWireSize, -1L);
        this.inboundBodyWireSize = 0;
        InputStream stream = this.compressedFlag ? this.getCompressedBody() : this.getUncompressedBody();
        this.nextFrame.touch();
        this.nextFrame = null;
        this.listener.messagesAvailable(new SingleMessageProducer(stream));
        this.state = State.HEADER;
        this.requiredLength = 5;
    }

    private InputStream getUncompressedBody() {
        this.statsTraceCtx.inboundUncompressedSize(this.nextFrame.readableBytes());
        return ReadableBuffers.openStream(this.nextFrame, true);
    }

    private InputStream getCompressedBody() {
        if (this.decompressor == Codec.Identity.NONE) {
            throw Status.INTERNAL.withDescription("Can't decode compressed gRPC message as compression not configured").asRuntimeException();
        }
        try {
            InputStream unlimitedStream = this.decompressor.decompress(ReadableBuffers.openStream(this.nextFrame, true));
            return new SizeEnforcingInputStream(unlimitedStream, this.maxInboundMessageSize, this.statsTraceCtx);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SingleMessageProducer
    implements StreamListener.MessageProducer {
        private InputStream message;

        private SingleMessageProducer(InputStream message) {
            this.message = message;
        }

        @Override
        @Nullable
        public InputStream next() {
            InputStream messageToReturn = this.message;
            this.message = null;
            return messageToReturn;
        }
    }

    @VisibleForTesting
    static final class SizeEnforcingInputStream
    extends FilterInputStream {
        private final int maxMessageSize;
        private final StatsTraceContext statsTraceCtx;
        private long maxCount;
        private long count;
        private long mark = -1L;

        SizeEnforcingInputStream(InputStream in, int maxMessageSize, StatsTraceContext statsTraceCtx) {
            super(in);
            this.maxMessageSize = maxMessageSize;
            this.statsTraceCtx = statsTraceCtx;
        }

        @Override
        public int read() throws IOException {
            int result = this.in.read();
            if (result != -1) {
                ++this.count;
            }
            this.verifySize();
            this.reportCount();
            return result;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int result = this.in.read(b, off, len);
            if (result != -1) {
                this.count += (long)result;
            }
            this.verifySize();
            this.reportCount();
            return result;
        }

        @Override
        public long skip(long n) throws IOException {
            long result = this.in.skip(n);
            this.count += result;
            this.verifySize();
            this.reportCount();
            return result;
        }

        @Override
        public synchronized void mark(int readlimit) {
            this.in.mark(readlimit);
            this.mark = this.count;
        }

        @Override
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1L) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.count = this.mark;
        }

        private void reportCount() {
            if (this.count > this.maxCount) {
                this.statsTraceCtx.inboundUncompressedSize(this.count - this.maxCount);
                this.maxCount = this.count;
            }
        }

        private void verifySize() {
            if (this.count > (long)this.maxMessageSize) {
                throw Status.RESOURCE_EXHAUSTED.withDescription("Decompressed gRPC message exceeds maximum size " + this.maxMessageSize).asRuntimeException();
            }
        }
    }

    private static enum State {
        HEADER,
        BODY;

    }

    public static interface Listener {
        public void bytesRead(int var1);

        public void messagesAvailable(StreamListener.MessageProducer var1);

        public void deframerClosed(boolean var1);

        public void deframeFailed(Throwable var1);
    }
}

