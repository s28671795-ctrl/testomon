/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.util.BufferUtil;

public class DataBodyParser
extends BodyParser {
    private State state = State.PREPARE;
    private int padding;
    private int paddingLength;
    private int length;

    public DataBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    private void reset() {
        this.state = State.PREPARE;
        this.padding = 0;
        this.paddingLength = 0;
        this.length = 0;
    }

    @Override
    protected void emptyBody(ByteBuffer buffer) {
        if (this.isPadding()) {
            this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_data_frame");
        } else {
            DataFrame frame = new DataFrame(this.getStreamId(), BufferUtil.EMPTY_BUFFER, this.isEndStream());
            if (!this.isEndStream() && !this.rateControlOnEvent(frame)) {
                this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_data_frame_rate");
            } else {
                this.onData(frame);
            }
        }
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        boolean loop = false;
        block6: while (buffer.hasRemaining() || loop) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_data_frame");
                    }
                    this.length = this.getBodyLength();
                    this.state = this.isPadding() ? State.PADDING_LENGTH : State.DATA;
                    continue block6;
                }
                case PADDING_LENGTH: {
                    this.padding = 1;
                    this.paddingLength = buffer.get() & 0xFF;
                    --this.length;
                    this.length -= this.paddingLength;
                    this.state = State.DATA;
                    boolean bl = loop = this.length == 0;
                    if (this.length >= 0) continue block6;
                    return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_data_frame_padding");
                }
                case DATA: {
                    int size = Math.min(buffer.remaining(), this.length);
                    int position = buffer.position();
                    int limit = buffer.limit();
                    buffer.limit(position + size);
                    ByteBuffer slice = buffer.slice();
                    buffer.limit(limit);
                    buffer.position(position + size);
                    this.length -= size;
                    if (this.length == 0) {
                        this.state = State.PADDING;
                        loop = this.paddingLength == 0;
                        this.onData(slice, false, this.padding + this.paddingLength);
                        continue block6;
                    }
                    this.onData(slice, true, 0);
                    continue block6;
                }
                case PADDING: {
                    int size = Math.min(buffer.remaining(), this.paddingLength);
                    buffer.position(buffer.position() + size);
                    this.paddingLength -= size;
                    if (this.paddingLength != 0) continue block6;
                    this.reset();
                    return true;
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private void onData(ByteBuffer buffer, boolean fragment, int padding) {
        this.onData(new DataFrame(this.getStreamId(), buffer, !fragment && this.isEndStream(), padding));
    }

    private void onData(DataFrame frame) {
        this.notifyData(frame);
    }

    private static enum State {
        PREPARE,
        PADDING_LENGTH,
        DATA,
        PADDING;

    }
}

