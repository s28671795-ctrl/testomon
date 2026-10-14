/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import io.grpc.Codec;
import io.grpc.Compressor;
import io.grpc.Drainable;
import io.grpc.KnownLength;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.Framer;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.WritableBuffer;
import io.grpc.internal.WritableBufferAllocator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;

public class MessageFramer
implements Framer {
    private static final int NO_MAX_OUTBOUND_MESSAGE_SIZE = -1;
    private static final int HEADER_LENGTH = 5;
    private static final byte UNCOMPRESSED = 0;
    private static final byte COMPRESSED = 1;
    private final Sink sink;
    private int maxOutboundMessageSize = -1;
    private WritableBuffer buffer;
    private Compressor compressor = Codec.Identity.NONE;
    private boolean messageCompression = true;
    private final OutputStreamAdapter outputStreamAdapter = new OutputStreamAdapter();
    private final ByteBuffer headerScratch = ByteBuffer.allocate(5);
    private final WritableBufferAllocator bufferAllocator;
    private final StatsTraceContext statsTraceCtx;
    private boolean closed;
    private int messagesBuffered;
    private int currentMessageSeqNo = -1;
    private long currentMessageWireSize;

    public MessageFramer(Sink sink2, WritableBufferAllocator bufferAllocator, StatsTraceContext statsTraceCtx) {
        this.sink = Preconditions.checkNotNull(sink2, "sink");
        this.bufferAllocator = Preconditions.checkNotNull(bufferAllocator, "bufferAllocator");
        this.statsTraceCtx = Preconditions.checkNotNull(statsTraceCtx, "statsTraceCtx");
    }

    @Override
    public MessageFramer setCompressor(Compressor compressor) {
        this.compressor = Preconditions.checkNotNull(compressor, "Can't pass an empty compressor");
        return this;
    }

    @Override
    public MessageFramer setMessageCompression(boolean enable) {
        this.messageCompression = enable;
        return this;
    }

    @Override
    public void setMaxOutboundMessageSize(int maxSize) {
        Preconditions.checkState(this.maxOutboundMessageSize == -1, "max size already set");
        this.maxOutboundMessageSize = maxSize;
    }

    @Override
    public void writePayload(InputStream message) {
        this.verifyNotClosed();
        ++this.messagesBuffered;
        ++this.currentMessageSeqNo;
        this.currentMessageWireSize = 0L;
        this.statsTraceCtx.outboundMessage(this.currentMessageSeqNo);
        boolean compressed = this.messageCompression && this.compressor != Codec.Identity.NONE;
        int written = -1;
        int messageLength = -2;
        try {
            messageLength = this.getKnownLength(message);
            written = messageLength != 0 && compressed ? this.writeCompressed(message, messageLength) : this.writeUncompressed(message, messageLength);
        }
        catch (IOException e) {
            throw Status.INTERNAL.withDescription("Failed to frame message").withCause(e).asRuntimeException();
        }
        catch (StatusRuntimeException e) {
            throw e;
        }
        catch (RuntimeException e) {
            throw Status.INTERNAL.withDescription("Failed to frame message").withCause(e).asRuntimeException();
        }
        if (messageLength != -1 && written != messageLength) {
            String err = String.format("Message length inaccurate %s != %s", written, messageLength);
            throw Status.INTERNAL.withDescription(err).asRuntimeException();
        }
        this.statsTraceCtx.outboundUncompressedSize(written);
        this.statsTraceCtx.outboundWireSize(this.currentMessageWireSize);
        this.statsTraceCtx.outboundMessageSent(this.currentMessageSeqNo, this.currentMessageWireSize, written);
    }

    private int writeUncompressed(InputStream message, int messageLength) throws IOException {
        if (messageLength != -1) {
            this.currentMessageWireSize = messageLength;
            return this.writeKnownLengthUncompressed(message, messageLength);
        }
        BufferChainOutputStream bufferChain = new BufferChainOutputStream();
        int written = MessageFramer.writeToOutputStream(message, bufferChain);
        this.writeBufferChain(bufferChain, false);
        return written;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int writeCompressed(InputStream message, int unusedMessageLength) throws IOException {
        int written;
        BufferChainOutputStream bufferChain = new BufferChainOutputStream();
        try (OutputStream compressingStream = this.compressor.compress(bufferChain);){
            written = MessageFramer.writeToOutputStream(message, compressingStream);
        }
        if (this.maxOutboundMessageSize >= 0 && written > this.maxOutboundMessageSize) {
            throw Status.RESOURCE_EXHAUSTED.withDescription(String.format(Locale.US, "message too large %d > %d", written, this.maxOutboundMessageSize)).asRuntimeException();
        }
        this.writeBufferChain(bufferChain, true);
        return written;
    }

    private int getKnownLength(InputStream inputStream2) throws IOException {
        if (inputStream2 instanceof KnownLength || inputStream2 instanceof ByteArrayInputStream) {
            return inputStream2.available();
        }
        return -1;
    }

    private int writeKnownLengthUncompressed(InputStream message, int messageLength) throws IOException {
        if (this.maxOutboundMessageSize >= 0 && messageLength > this.maxOutboundMessageSize) {
            throw Status.RESOURCE_EXHAUSTED.withDescription(String.format(Locale.US, "message too large %d > %d", messageLength, this.maxOutboundMessageSize)).asRuntimeException();
        }
        this.headerScratch.clear();
        this.headerScratch.put((byte)0).putInt(messageLength);
        if (this.buffer == null) {
            this.buffer = this.bufferAllocator.allocate(this.headerScratch.position() + messageLength);
        }
        this.writeRaw(this.headerScratch.array(), 0, this.headerScratch.position());
        return MessageFramer.writeToOutputStream(message, this.outputStreamAdapter);
    }

    private void writeBufferChain(BufferChainOutputStream bufferChain, boolean compressed) {
        int messageLength = bufferChain.readableBytes();
        if (this.maxOutboundMessageSize >= 0 && messageLength > this.maxOutboundMessageSize) {
            throw Status.RESOURCE_EXHAUSTED.withDescription(String.format(Locale.US, "message too large %d > %d", messageLength, this.maxOutboundMessageSize)).asRuntimeException();
        }
        this.headerScratch.clear();
        this.headerScratch.put(compressed ? (byte)1 : 0).putInt(messageLength);
        WritableBuffer writeableHeader = this.bufferAllocator.allocate(5);
        writeableHeader.write(this.headerScratch.array(), 0, this.headerScratch.position());
        if (messageLength == 0) {
            this.buffer = writeableHeader;
            return;
        }
        this.sink.deliverFrame(writeableHeader, false, false, this.messagesBuffered - 1);
        this.messagesBuffered = 1;
        List bufferList = bufferChain.bufferList;
        for (int i = 0; i < bufferList.size() - 1; ++i) {
            this.sink.deliverFrame((WritableBuffer)bufferList.get(i), false, false, 0);
        }
        this.buffer = (WritableBuffer)bufferList.get(bufferList.size() - 1);
        this.currentMessageWireSize = messageLength;
    }

    private static int writeToOutputStream(InputStream message, OutputStream outputStream2) throws IOException {
        if (message instanceof Drainable) {
            return ((Drainable)((Object)message)).drainTo(outputStream2);
        }
        long written = ByteStreams.copy(message, outputStream2);
        Preconditions.checkArgument(written <= Integer.MAX_VALUE, "Message size overflow: %s", written);
        return (int)written;
    }

    private void writeRaw(byte[] b, int off, int len) {
        while (len > 0) {
            if (this.buffer != null && this.buffer.writableBytes() == 0) {
                this.commitToSink(false, false);
            }
            if (this.buffer == null) {
                this.buffer = this.bufferAllocator.allocate(len);
            }
            int toWrite = Math.min(len, this.buffer.writableBytes());
            this.buffer.write(b, off, toWrite);
            off += toWrite;
            len -= toWrite;
        }
    }

    @Override
    public void flush() {
        if (this.buffer != null && this.buffer.readableBytes() > 0) {
            this.commitToSink(false, true);
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public void close() {
        if (!this.isClosed()) {
            this.closed = true;
            if (this.buffer != null && this.buffer.readableBytes() == 0) {
                this.releaseBuffer();
            }
            this.commitToSink(true, true);
        }
    }

    @Override
    public void dispose() {
        this.closed = true;
        this.releaseBuffer();
    }

    private void releaseBuffer() {
        if (this.buffer != null) {
            this.buffer.release();
            this.buffer = null;
        }
    }

    private void commitToSink(boolean endOfStream, boolean flush) {
        WritableBuffer buf = this.buffer;
        this.buffer = null;
        this.sink.deliverFrame(buf, endOfStream, flush, this.messagesBuffered);
        this.messagesBuffered = 0;
    }

    private void verifyNotClosed() {
        if (this.isClosed()) {
            throw new IllegalStateException("Framer already closed");
        }
    }

    private final class BufferChainOutputStream
    extends OutputStream {
        private final List<WritableBuffer> bufferList = new ArrayList<WritableBuffer>();
        private WritableBuffer current;

        private BufferChainOutputStream() {
        }

        @Override
        public void write(int b) throws IOException {
            if (this.current != null && this.current.writableBytes() > 0) {
                this.current.write((byte)b);
                return;
            }
            byte[] singleByte = new byte[]{(byte)b};
            this.write(singleByte, 0, 1);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            if (this.current == null) {
                this.current = MessageFramer.this.bufferAllocator.allocate(len);
                this.bufferList.add(this.current);
            }
            while (len > 0) {
                int canWrite = Math.min(len, this.current.writableBytes());
                if (canWrite == 0) {
                    int needed = Math.max(len, this.current.readableBytes() * 2);
                    this.current = MessageFramer.this.bufferAllocator.allocate(needed);
                    this.bufferList.add(this.current);
                    continue;
                }
                this.current.write(b, off, canWrite);
                off += canWrite;
                len -= canWrite;
            }
        }

        private int readableBytes() {
            int readable = 0;
            for (WritableBuffer writableBuffer : this.bufferList) {
                readable += writableBuffer.readableBytes();
            }
            return readable;
        }
    }

    private class OutputStreamAdapter
    extends OutputStream {
        private OutputStreamAdapter() {
        }

        @Override
        public void write(int b) {
            byte[] singleByte = new byte[]{(byte)b};
            this.write(singleByte, 0, 1);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            MessageFramer.this.writeRaw(b, off, len);
        }
    }

    public static interface Sink {
        public void deliverFrame(@Nullable WritableBuffer var1, boolean var2, boolean var3, int var4);
    }
}

