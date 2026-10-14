/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.http2.parser.PrefaceParser;
import org.eclipse.jetty.http2.parser.RateControl;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerParser
extends Parser {
    private static final Logger LOG = LoggerFactory.getLogger(ServerParser.class);
    private final Listener listener;
    private final PrefaceParser prefaceParser;
    private State state = State.PREFACE;
    private boolean notifyPreface = true;

    public ServerParser(ByteBufferPool byteBufferPool, Listener listener, int maxDynamicTableSize, int maxHeaderSize, RateControl rateControl) {
        super(byteBufferPool, listener, maxDynamicTableSize, maxHeaderSize, rateControl);
        this.listener = listener;
        this.prefaceParser = new PrefaceParser(listener);
    }

    public void directUpgrade() {
        if (this.state != State.PREFACE) {
            throw new IllegalStateException();
        }
        this.prefaceParser.directUpgrade();
    }

    public void standardUpgrade() {
        if (this.state != State.PREFACE) {
            throw new IllegalStateException();
        }
        this.notifyPreface = false;
    }

    @Override
    public void parse(ByteBuffer buffer) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Parsing {}", (Object)buffer);
            }
            block7: while (true) {
                switch (this.state) {
                    case PREFACE: {
                        if (!this.prefaceParser.parse(buffer)) {
                            return;
                        }
                        if (this.notifyPreface) {
                            this.onPreface();
                        }
                        this.state = State.SETTINGS;
                        continue block7;
                    }
                    case SETTINGS: {
                        if (!this.parseHeader(buffer)) {
                            return;
                        }
                        if (this.getFrameType() != FrameType.SETTINGS.getType() || this.hasFlag(1)) {
                            BufferUtil.clear(buffer);
                            this.notifyConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "invalid_preface");
                            return;
                        }
                        if (!this.parseBody(buffer)) {
                            return;
                        }
                        this.state = State.FRAMES;
                        continue block7;
                    }
                    case FRAMES: {
                        super.parse(buffer);
                        return;
                    }
                }
                break;
            }
            throw new IllegalStateException();
        }
        catch (Throwable x) {
            LOG.debug("Parse error", x);
            BufferUtil.clear(buffer);
            this.notifyConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "parser_error");
            return;
        }
    }

    protected void onPreface() {
        this.notifyPreface();
    }

    private void notifyPreface() {
        try {
            this.listener.onPreface();
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    private static enum State {
        PREFACE,
        SETTINGS,
        FRAMES;

    }

    public static interface Listener
    extends Parser.Listener {
        public void onPreface();

        public static class Wrapper
        extends Parser.Listener.Wrapper
        implements Listener {
            public Wrapper(Listener listener) {
                super(listener);
            }

            @Override
            public Listener getParserListener() {
                return (Listener)super.getParserListener();
            }

            @Override
            public void onPreface() {
                this.getParserListener().onPreface();
            }
        }

        public static class Adapter
        extends Parser.Listener.Adapter
        implements Listener {
            @Override
            public void onPreface() {
            }
        }
    }
}

