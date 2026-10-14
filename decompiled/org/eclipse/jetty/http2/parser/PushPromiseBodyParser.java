/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderBlockParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class PushPromiseBodyParser
extends BodyParser {
    private final HeaderBlockParser headerBlockParser;
    private State state = State.PREPARE;
    private int cursor;
    private int length;
    private int paddingLength;
    private int streamId;

    public PushPromiseBodyParser(HeaderParser headerParser, Parser.Listener listener, HeaderBlockParser headerBlockParser) {
        super(headerParser, listener);
        this.headerBlockParser = headerBlockParser;
    }

    private void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.length = 0;
        this.paddingLength = 0;
        this.streamId = 0;
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        boolean loop = false;
        block8: while (buffer.hasRemaining() || loop) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_push_promise_frame");
                    }
                    if (!this.hasFlag(4)) {
                        return this.connectionFailure(buffer, ErrorCode.INTERNAL_ERROR.code, "unsupported_push_promise_frame");
                    }
                    this.length = this.getBodyLength();
                    if (this.isPadding()) {
                        this.state = State.PADDING_LENGTH;
                        continue block8;
                    }
                    this.state = State.STREAM_ID;
                    continue block8;
                }
                case PADDING_LENGTH: {
                    this.paddingLength = buffer.get() & 0xFF;
                    --this.length;
                    this.length -= this.paddingLength;
                    this.state = State.STREAM_ID;
                    if (this.length >= 4) continue block8;
                    return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_push_promise_frame");
                }
                case STREAM_ID: {
                    if (buffer.remaining() >= 4) {
                        this.streamId = buffer.getInt();
                        this.streamId &= Integer.MAX_VALUE;
                        this.length -= 4;
                        this.state = State.HEADERS;
                        loop = this.length == 0;
                        continue block8;
                    }
                    this.state = State.STREAM_ID_BYTES;
                    this.cursor = 4;
                    continue block8;
                }
                case STREAM_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.streamId += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.cursor > 0 && this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_push_promise_frame");
                    }
                    if (this.cursor != 0) continue block8;
                    this.streamId &= Integer.MAX_VALUE;
                    this.state = State.HEADERS;
                    loop = this.length == 0;
                    continue block8;
                }
                case HEADERS: {
                    MetaData.Request metaData = (MetaData.Request)this.headerBlockParser.parse(buffer, this.length);
                    if (metaData == HeaderBlockParser.SESSION_FAILURE) {
                        return false;
                    }
                    if (metaData == null) continue block8;
                    this.state = State.PADDING;
                    boolean bl = loop = this.paddingLength == 0;
                    if (metaData == HeaderBlockParser.STREAM_FAILURE) continue block8;
                    this.onPushPromise(this.streamId, metaData);
                    continue block8;
                }
                case PADDING: {
                    int size = Math.min(buffer.remaining(), this.paddingLength);
                    buffer.position(buffer.position() + size);
                    this.paddingLength -= size;
                    if (this.paddingLength != 0) continue block8;
                    this.reset();
                    return true;
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private void onPushPromise(int streamId, MetaData.Request metaData) {
        PushPromiseFrame frame = new PushPromiseFrame(this.getStreamId(), streamId, metaData);
        this.notifyPushPromise(frame);
    }

    private static enum State {
        PREPARE,
        PADDING_LENGTH,
        STREAM_ID,
        STREAM_ID_BYTES,
        HEADERS,
        PADDING;

    }
}

