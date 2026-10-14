/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderBlockFragments;
import org.eclipse.jetty.http2.parser.HeaderBlockParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.util.BufferUtil;

public class HeadersBodyParser
extends BodyParser {
    private final HeaderBlockParser headerBlockParser;
    private final HeaderBlockFragments headerBlockFragments;
    private State state = State.PREPARE;
    private int cursor;
    private int length;
    private int paddingLength;
    private boolean exclusive;
    private int parentStreamId;
    private int weight;

    public HeadersBodyParser(HeaderParser headerParser, Parser.Listener listener, HeaderBlockParser headerBlockParser, HeaderBlockFragments headerBlockFragments) {
        super(headerParser, listener);
        this.headerBlockParser = headerBlockParser;
        this.headerBlockFragments = headerBlockFragments;
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.length = 0;
        this.paddingLength = 0;
        this.exclusive = false;
        this.parentStreamId = 0;
        this.weight = 0;
    }

    @Override
    protected void emptyBody(ByteBuffer buffer) {
        if (this.hasFlag(32)) {
            this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_headers_priority_frame");
        } else if (this.hasFlag(4)) {
            MetaData metaData = this.headerBlockParser.parse(BufferUtil.EMPTY_BUFFER, 0);
            HeadersFrame frame = new HeadersFrame(this.getStreamId(), metaData, null, this.isEndStream());
            if (!this.rateControlOnEvent(frame)) {
                this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_headers_frame_rate");
            } else {
                this.onHeaders(frame);
            }
        } else {
            this.headerBlockFragments.setStreamId(this.getStreamId());
            this.headerBlockFragments.setEndStream(this.isEndStream());
        }
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        boolean loop = false;
        block10: while (buffer.hasRemaining() || loop) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_headers_frame");
                    }
                    this.length = this.getBodyLength();
                    if (this.isPadding()) {
                        this.state = State.PADDING_LENGTH;
                        continue block10;
                    }
                    if (this.hasFlag(32)) {
                        this.state = State.EXCLUSIVE;
                        continue block10;
                    }
                    this.state = State.HEADERS;
                    continue block10;
                }
                case PADDING_LENGTH: {
                    this.paddingLength = buffer.get() & 0xFF;
                    --this.length;
                    this.length -= this.paddingLength;
                    this.state = this.hasFlag(32) ? State.EXCLUSIVE : State.HEADERS;
                    boolean bl = loop = this.length == 0;
                    if (this.length >= 0) continue block10;
                    return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_headers_frame_padding");
                }
                case EXCLUSIVE: {
                    byte currByte = buffer.get(buffer.position());
                    this.exclusive = (currByte & 0x80) == 128;
                    this.state = State.PARENT_STREAM_ID;
                    continue block10;
                }
                case PARENT_STREAM_ID: {
                    if (buffer.remaining() >= 4) {
                        this.parentStreamId = buffer.getInt();
                        this.parentStreamId &= Integer.MAX_VALUE;
                        this.length -= 4;
                        this.state = State.WEIGHT;
                        if (this.length >= 1) continue block10;
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_headers_frame");
                    }
                    this.state = State.PARENT_STREAM_ID_BYTES;
                    this.cursor = 4;
                    continue block10;
                }
                case PARENT_STREAM_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.parentStreamId += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.cursor > 0 && this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_headers_frame");
                    }
                    if (this.cursor != 0) continue block10;
                    this.parentStreamId &= Integer.MAX_VALUE;
                    this.state = State.WEIGHT;
                    if (this.length >= 1) continue block10;
                    return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_headers_frame");
                }
                case WEIGHT: {
                    if (this.getStreamId() == this.parentStreamId) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_priority_frame");
                    }
                    this.weight = (buffer.get() & 0xFF) + 1;
                    --this.length;
                    this.state = State.HEADERS;
                    loop = this.length == 0;
                    continue block10;
                }
                case HEADERS: {
                    if (this.hasFlag(4)) {
                        MetaData metaData = this.headerBlockParser.parse(buffer, this.length);
                        if (metaData == HeaderBlockParser.SESSION_FAILURE) {
                            return false;
                        }
                        if (metaData == null) continue block10;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Parsed {} frame hpack from {}", (Object)FrameType.HEADERS, (Object)buffer);
                        }
                        this.state = State.PADDING;
                        boolean bl = loop = this.paddingLength == 0;
                        if (metaData != HeaderBlockParser.STREAM_FAILURE) {
                            this.onHeaders(this.parentStreamId, this.weight, this.exclusive, metaData);
                            continue block10;
                        }
                        HeadersFrame frame = new HeadersFrame(this.getStreamId(), metaData, null, this.isEndStream());
                        if (this.rateControlOnEvent(frame)) continue block10;
                        this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_headers_frame_rate");
                        continue block10;
                    }
                    int remaining = buffer.remaining();
                    if (remaining < this.length) {
                        this.headerBlockFragments.storeFragment(buffer, remaining, false);
                        this.length -= remaining;
                        continue block10;
                    }
                    this.headerBlockFragments.setStreamId(this.getStreamId());
                    this.headerBlockFragments.setEndStream(this.isEndStream());
                    if (this.hasFlag(32)) {
                        this.headerBlockFragments.setPriorityFrame(new PriorityFrame(this.getStreamId(), this.parentStreamId, this.weight, this.exclusive));
                    }
                    this.headerBlockFragments.storeFragment(buffer, this.length, false);
                    this.state = State.PADDING;
                    loop = this.paddingLength == 0;
                    continue block10;
                }
                case PADDING: {
                    int size = Math.min(buffer.remaining(), this.paddingLength);
                    buffer.position(buffer.position() + size);
                    this.paddingLength -= size;
                    if (this.paddingLength != 0) continue block10;
                    this.reset();
                    return true;
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private void onHeaders(int parentStreamId, int weight, boolean exclusive, MetaData metaData) {
        PriorityFrame priorityFrame = null;
        if (this.hasFlag(32)) {
            priorityFrame = new PriorityFrame(this.getStreamId(), parentStreamId, weight, exclusive);
        }
        HeadersFrame frame = new HeadersFrame(this.getStreamId(), metaData, priorityFrame, this.isEndStream());
        this.onHeaders(frame);
    }

    private void onHeaders(HeadersFrame frame) {
        this.notifyHeaders(frame);
    }

    private static enum State {
        PREPARE,
        PADDING_LENGTH,
        EXCLUSIVE,
        PARENT_STREAM_ID,
        PARENT_STREAM_ID_BYTES,
        WEIGHT,
        HEADERS,
        PADDING;

    }
}

