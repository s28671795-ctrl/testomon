/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.UnknownFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;

public class UnknownBodyParser
extends BodyParser {
    private int cursor;

    public UnknownBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        super(headerParser, listener);
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        boolean parsed;
        int length = this.cursor == 0 ? this.getBodyLength() : this.cursor;
        this.cursor = this.consume(buffer, length);
        boolean bl = parsed = this.cursor == 0;
        if (parsed && !this.rateControlOnEvent(new UnknownFrame(this.getFrameType()))) {
            return this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_unknown_frame_rate");
        }
        return parsed;
    }

    private int consume(ByteBuffer buffer, int length) {
        int remaining = buffer.remaining();
        if (remaining >= length) {
            buffer.position(buffer.position() + length);
            return 0;
        }
        buffer.position(buffer.limit());
        return length - remaining;
    }
}

