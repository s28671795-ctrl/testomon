/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.ContinuationFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderBlockFragments;
import org.eclipse.jetty.http2.parser.HeaderBlockParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class ContinuationBodyParser
extends BodyParser {
    private final HeaderBlockParser headerBlockParser;
    private final HeaderBlockFragments headerBlockFragments;
    private State state = State.PREPARE;
    private int length;

    public ContinuationBodyParser(HeaderParser headerParser, Parser.Listener listener, HeaderBlockParser headerBlockParser, HeaderBlockFragments headerBlockFragments) {
        super(headerParser, listener);
        this.headerBlockParser = headerBlockParser;
        this.headerBlockFragments = headerBlockFragments;
    }

    @Override
    protected void emptyBody(ByteBuffer buffer) {
        if (this.hasFlag(4)) {
            this.onHeaders(buffer);
        } else {
            ContinuationFrame frame = new ContinuationFrame(this.getStreamId(), this.hasFlag(4));
            if (!this.rateControlOnEvent(frame)) {
                this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_continuation_frame_rate");
            }
        }
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        block4: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    if (this.getStreamId() == 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_continuation_frame");
                    }
                    if (this.getStreamId() != this.headerBlockFragments.getStreamId()) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_continuation_stream");
                    }
                    this.length = this.getBodyLength();
                    this.state = State.FRAGMENT;
                    continue block4;
                }
                case FRAGMENT: {
                    int remaining = buffer.remaining();
                    if (remaining < this.length) {
                        this.headerBlockFragments.storeFragment(buffer, remaining, false);
                        this.length -= remaining;
                        continue block4;
                    }
                    boolean last = this.hasFlag(4);
                    this.headerBlockFragments.storeFragment(buffer, this.length, last);
                    this.reset();
                    if (last) {
                        return this.onHeaders(buffer);
                    }
                    return true;
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    private boolean onHeaders(ByteBuffer buffer) {
        ByteBuffer headerBlock = this.headerBlockFragments.complete();
        MetaData metaData = this.headerBlockParser.parse(headerBlock, headerBlock.remaining());
        this.headerBlockFragments.getByteBufferPool().release(headerBlock);
        if (metaData == null) {
            return true;
        }
        if (metaData == HeaderBlockParser.SESSION_FAILURE) {
            return false;
        }
        HeadersFrame frame = new HeadersFrame(this.getStreamId(), metaData, this.headerBlockFragments.getPriorityFrame(), this.headerBlockFragments.isEndStream());
        if (metaData == HeaderBlockParser.STREAM_FAILURE && !this.rateControlOnEvent(frame)) {
            return this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_continuation_frame_rate");
        }
        this.notifyHeaders(frame);
        return true;
    }

    private void reset() {
        this.state = State.PREPARE;
        this.length = 0;
    }

    private static enum State {
        PREPARE,
        FRAGMENT;

    }
}

