/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.PrefaceFrame;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrefaceParser {
    private static final Logger LOG = LoggerFactory.getLogger(PrefaceParser.class);
    private final Parser.Listener listener;
    private int cursor;

    public PrefaceParser(Parser.Listener listener) {
        this.listener = listener;
    }

    protected void directUpgrade() {
        if (this.cursor != 0) {
            throw new IllegalStateException();
        }
        this.cursor = PrefaceFrame.PREFACE_PREAMBLE_BYTES.length;
    }

    public boolean parse(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            byte currByte = buffer.get();
            if (currByte != PrefaceFrame.PREFACE_BYTES[this.cursor]) {
                BufferUtil.clear(buffer);
                this.notifyConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "invalid_preface");
                return false;
            }
            ++this.cursor;
            if (this.cursor != PrefaceFrame.PREFACE_BYTES.length) continue;
            this.cursor = 0;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Parsed preface bytes from {}", (Object)buffer);
            }
            return true;
        }
        return false;
    }

    protected void notifyConnectionFailure(int error, String reason) {
        try {
            this.listener.onConnectionFailure(error, reason);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }
}

