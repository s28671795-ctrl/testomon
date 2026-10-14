/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.io.Closeable;
import java.nio.ByteBuffer;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.OpCode;
import org.eclipse.jetty.websocket.core.exception.MessageTooLargeException;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    private final ByteBufferPool bufferPool;
    private final Configuration configuration;
    private State state = State.START;
    private byte firstByte;
    private int cursor;
    private byte[] mask;
    private int payloadLength;
    private ByteBuffer aggregate;

    public Parser(ByteBufferPool bufferPool) {
        this(bufferPool, new Configuration.ConfigurationCustomizer());
    }

    public Parser(ByteBufferPool bufferPool, Configuration configuration) {
        this.bufferPool = bufferPool;
        this.configuration = configuration;
    }

    public int getPayloadLength() {
        return this.payloadLength;
    }

    public void reset() {
        this.state = State.START;
        this.firstByte = 0;
        this.mask = null;
        this.cursor = 0;
        this.aggregate = null;
        this.payloadLength = 0;
    }

    public ParsedFrame parse(ByteBuffer buffer) throws WebSocketException {
        try {
            block18: while (buffer.hasRemaining()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} Parsing {}", (Object)this, (Object)BufferUtil.toDetailString(buffer));
                }
                switch (this.state) {
                    case START: {
                        this.firstByte = buffer.get();
                        this.state = State.PAYLOAD_LEN;
                        continue block18;
                    }
                    case PAYLOAD_LEN: {
                        byte b = buffer.get();
                        if ((b & 0x80) != 0) {
                            this.mask = new byte[4];
                        }
                        this.payloadLength = (byte)(0x7F & b);
                        if (this.payloadLength == 127) {
                            this.payloadLength = 0;
                            this.state = State.PAYLOAD_LEN_BYTES;
                            this.cursor = 8;
                            continue block18;
                        }
                        if (this.payloadLength == 126) {
                            this.payloadLength = 0;
                            this.state = State.PAYLOAD_LEN_BYTES;
                            this.cursor = 2;
                            continue block18;
                        }
                        if (this.mask != null) {
                            this.state = State.MASK;
                            continue block18;
                        }
                        if (this.payloadLength == 0) {
                            this.state = State.START;
                            ParsedFrame parsedFrame = this.newFrame(this.firstByte, this.mask, null, false);
                            return parsedFrame;
                        }
                        this.state = State.PAYLOAD;
                        continue block18;
                    }
                    case PAYLOAD_LEN_BYTES: {
                        byte b = buffer.get();
                        --this.cursor;
                        long longLengthAccumulator = this.payloadLength;
                        if ((longLengthAccumulator |= (long)(b & 0xFF) << 8 * this.cursor) > Integer.MAX_VALUE || longLengthAccumulator < 0L) {
                            throw new MessageTooLargeException("Frame payload exceeded integer max value");
                        }
                        this.payloadLength = Math.toIntExact(longLengthAccumulator);
                        if (this.cursor != 0) continue block18;
                        if (this.mask != null) {
                            this.state = State.MASK;
                            continue block18;
                        }
                        if (this.payloadLength == 0) {
                            this.state = State.START;
                            ParsedFrame parsedFrame = this.newFrame(this.firstByte, this.mask, null, false);
                            return parsedFrame;
                        }
                        this.state = State.PAYLOAD;
                        continue block18;
                    }
                    case MASK: {
                        if (buffer.remaining() >= 4) {
                            buffer.get(this.mask, 0, 4);
                            if (this.payloadLength == 0) {
                                this.state = State.START;
                                ParsedFrame b = this.newFrame(this.firstByte, this.mask, null, false);
                                return b;
                            }
                            this.state = State.PAYLOAD;
                            continue block18;
                        }
                        this.state = State.MASK_BYTES;
                        this.cursor = 4;
                        continue block18;
                    }
                    case MASK_BYTES: {
                        byte b;
                        this.mask[4 - this.cursor] = b = buffer.get();
                        --this.cursor;
                        if (this.cursor != 0) continue block18;
                        if (this.payloadLength == 0) {
                            this.state = State.START;
                            ParsedFrame longLengthAccumulator = this.newFrame(this.firstByte, this.mask, null, false);
                            return longLengthAccumulator;
                        }
                        this.state = State.PAYLOAD;
                        continue block18;
                    }
                    case PAYLOAD: 
                    case FRAGMENT: {
                        if (this.aggregate == null) {
                            this.checkFrameSize(OpCode.getOpCode(this.firstByte), this.payloadLength);
                        }
                        ParsedFrame frame = this.parsePayload(buffer);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("{} parsed {}", (Object)this, (Object)frame);
                        }
                        ParsedFrame longLengthAccumulator = frame;
                        return longLengthAccumulator;
                    }
                }
                throw new IllegalStateException();
            }
        }
        catch (Throwable t2) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} Parse Error {}", this, BufferUtil.toDetailString(buffer), t2);
            }
            buffer.position(buffer.limit());
            WebSocketException wse = t2 instanceof WebSocketException ? (WebSocketException)t2 : new WebSocketException(t2);
            throw wse;
        }
        finally {
            if (this.state == State.START) {
                this.reset();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} Parse exit", (Object)this);
            }
        }
        return null;
    }

    protected void checkFrameSize(byte opcode, int payloadLength) throws MessageTooLargeException, ProtocolException {
        if (payloadLength < 0) {
            throw new IllegalArgumentException("Invalid payloadLength");
        }
        if (OpCode.isControlFrame(opcode)) {
            if (payloadLength > 125) {
                throw new ProtocolException("Invalid control frame payload length, [" + payloadLength + "] cannot exceed [125]");
            }
        } else {
            long maxFrameSize = this.configuration.getMaxFrameSize();
            if (!this.configuration.isAutoFragment() && maxFrameSize > 0L && (long)payloadLength > maxFrameSize) {
                throw new MessageTooLargeException("Cannot handle payload lengths larger than " + maxFrameSize);
            }
        }
    }

    protected ParsedFrame newFrame(byte firstByte, byte[] mask, ByteBuffer payload, boolean releaseable) {
        boolean fin;
        byte opcode = OpCode.getOpCode(firstByte);
        if (!OpCode.isKnown(opcode)) {
            throw new ProtocolException("Unknown opcode: " + opcode);
        }
        boolean bl = fin = (firstByte & 0x80) != 0;
        if (OpCode.isControlFrame(opcode) && !fin) {
            throw new ProtocolException("Fragmented Control Frame [" + OpCode.name(opcode) + "]");
        }
        return new ParsedFrame(firstByte, mask, payload, releaseable);
    }

    private ParsedFrame autoFragment(ByteBuffer buffer, int fragmentSize) {
        this.payloadLength -= fragmentSize;
        byte[] nextMask = null;
        if (this.mask != null) {
            int shift = fragmentSize % 4;
            nextMask = new byte[]{this.mask[shift % 4], this.mask[(1 + shift) % 4], this.mask[(2 + shift) % 4], this.mask[(3 + shift) % 4]};
        }
        ByteBuffer content = buffer.slice();
        content.limit(fragmentSize);
        buffer.position(buffer.position() + fragmentSize);
        ParsedFrame frame = this.newFrame((byte)(this.firstByte & 0x7F), this.mask, content, false);
        this.mask = nextMask;
        this.firstByte = (byte)(this.firstByte & 0x80 | 0);
        this.state = State.FRAGMENT;
        return frame;
    }

    private ParsedFrame parsePayload(ByteBuffer buffer) {
        if (this.payloadLength == 0) {
            return null;
        }
        if (BufferUtil.isEmpty(buffer)) {
            return null;
        }
        int available = buffer.remaining();
        boolean isDataFrame = OpCode.isDataFrame(OpCode.getOpCode(this.firstByte));
        long maxFrameSize = this.configuration.getMaxFrameSize();
        if (maxFrameSize > 0L && isDataFrame && (long)this.payloadLength > maxFrameSize) {
            return this.autoFragment(buffer, (int)Math.min((long)available, maxFrameSize));
        }
        if (this.aggregate == null) {
            if (available < this.payloadLength) {
                if (this.configuration.isAutoFragment() && isDataFrame) {
                    return this.autoFragment(buffer, available);
                }
                this.aggregate = this.bufferPool.acquire(this.payloadLength, false);
                BufferUtil.append(this.aggregate, buffer);
                return null;
            }
            if (available == this.payloadLength) {
                ParsedFrame frame = this.newFrame(this.firstByte, this.mask, buffer.slice(), false);
                buffer.position(buffer.limit());
                this.state = State.START;
                return frame;
            }
            int limit = buffer.limit();
            int end = buffer.position() + this.payloadLength;
            buffer.limit(end);
            ParsedFrame frame = this.newFrame(this.firstByte, this.mask, buffer.slice(), false);
            buffer.position(end);
            buffer.limit(limit);
            this.state = State.START;
            return frame;
        }
        int aggregated = this.aggregate.remaining();
        int expecting = this.payloadLength - aggregated;
        if (available < expecting) {
            BufferUtil.append(this.aggregate, buffer);
            return null;
        }
        if (available == expecting) {
            BufferUtil.append(this.aggregate, buffer);
            this.state = State.START;
            return this.newFrame(this.firstByte, this.mask, this.aggregate, true);
        }
        int limit = buffer.limit();
        buffer.limit(buffer.position() + expecting);
        BufferUtil.append(this.aggregate, buffer);
        buffer.limit(limit);
        this.state = State.START;
        return this.newFrame(this.firstByte, this.mask, this.aggregate, true);
    }

    public String toString() {
        return String.format("Parser@%x[s=%s,c=%d,o=0x%x,m=%s,l=%d]", new Object[]{this.hashCode(), this.state, this.cursor, this.firstByte, this.mask == null ? "-" : TypeUtil.toHexString(this.mask), this.payloadLength});
    }

    private static enum State {
        START,
        PAYLOAD_LEN,
        PAYLOAD_LEN_BYTES,
        MASK,
        MASK_BYTES,
        PAYLOAD,
        FRAGMENT;

    }

    public class ParsedFrame
    extends Frame
    implements Closeable,
    CloseStatus.Supplier {
        final CloseStatus closeStatus;
        final boolean releaseable;

        public ParsedFrame(byte firstByte, byte[] mask, ByteBuffer payload, boolean releaseable) {
            super(firstByte, mask, payload);
            this.demask();
            this.releaseable = releaseable;
            this.closeStatus = this.getOpCode() == 8 ? (this.hasPayload() ? new CloseStatus(payload.duplicate()) : CloseStatus.NO_CODE_STATUS) : null;
        }

        @Override
        public void close() {
            if (this.releaseable) {
                Parser.this.bufferPool.release(this.getPayload());
            }
        }

        @Override
        public CloseStatus getCloseStatus() {
            return this.closeStatus;
        }

        public boolean isReleaseable() {
            return this.releaseable;
        }

        @Override
        public String toString() {
            if (this.closeStatus == null) {
                return super.toString();
            }
            return super.toString() + ":" + this.closeStatus;
        }
    }
}

