/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.http2.parser.RateControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsBodyParser
extends BodyParser {
    private static final Logger LOG = LoggerFactory.getLogger(SettingsBodyParser.class);
    private final int maxKeys;
    private State state = State.PREPARE;
    private int cursor;
    private int length;
    private int settingId;
    private int settingValue;
    private int keys;
    private Map<Integer, Integer> settings;

    public SettingsBodyParser(HeaderParser headerParser, Parser.Listener listener) {
        this(headerParser, listener, 64);
    }

    public SettingsBodyParser(HeaderParser headerParser, Parser.Listener listener, int maxKeys) {
        super(headerParser, listener);
        this.maxKeys = maxKeys;
    }

    protected void reset() {
        this.state = State.PREPARE;
        this.cursor = 0;
        this.length = 0;
        this.settingId = 0;
        this.settingValue = 0;
        this.settings = null;
    }

    public int getMaxKeys() {
        return this.maxKeys;
    }

    @Override
    protected void emptyBody(ByteBuffer buffer) {
        boolean isReply = this.hasFlag(1);
        SettingsFrame frame = new SettingsFrame(Collections.emptyMap(), isReply);
        if (!isReply && !this.rateControlOnEvent(frame)) {
            this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_settings_frame_rate");
        } else {
            this.onSettings(frame);
        }
    }

    @Override
    public boolean parse(ByteBuffer buffer) {
        return this.parse(buffer, this.getStreamId(), this.getBodyLength());
    }

    private boolean parse(ByteBuffer buffer, int streamId, int bodyLength) {
        block7: while (buffer.hasRemaining()) {
            switch (this.state) {
                case PREPARE: {
                    if (streamId != 0) {
                        return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_settings_frame");
                    }
                    this.length = bodyLength;
                    this.settings = new HashMap<Integer, Integer>();
                    this.state = State.SETTING_ID;
                    continue block7;
                }
                case SETTING_ID: {
                    if (buffer.remaining() >= 2) {
                        this.settingId = buffer.getShort() & 0xFFFF;
                        this.state = State.SETTING_VALUE;
                        this.length -= 2;
                        if (this.length > 0) continue block7;
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_settings_frame");
                    }
                    this.cursor = 2;
                    this.settingId = 0;
                    this.state = State.SETTING_ID_BYTES;
                    continue block7;
                }
                case SETTING_ID_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.settingId += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_settings_frame");
                    }
                    if (this.cursor != 0) continue block7;
                    this.state = State.SETTING_VALUE;
                    continue block7;
                }
                case SETTING_VALUE: {
                    if (buffer.remaining() >= 4) {
                        this.settingValue = buffer.getInt();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(String.format("setting %d=%d", this.settingId, this.settingValue));
                        }
                        if (!this.onSetting(buffer, this.settings, this.settingId, this.settingValue)) {
                            return false;
                        }
                        this.state = State.SETTING_ID;
                        this.length -= 4;
                        if (this.length != 0) continue block7;
                        return this.onSettings(buffer, this.settings);
                    }
                    this.cursor = 4;
                    this.settingValue = 0;
                    this.state = State.SETTING_VALUE_BYTES;
                    continue block7;
                }
                case SETTING_VALUE_BYTES: {
                    int currByte = buffer.get() & 0xFF;
                    --this.cursor;
                    this.settingValue += currByte << 8 * this.cursor;
                    --this.length;
                    if (this.cursor > 0 && this.length <= 0) {
                        return this.connectionFailure(buffer, ErrorCode.FRAME_SIZE_ERROR.code, "invalid_settings_frame");
                    }
                    if (this.cursor != 0) continue block7;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(String.format("setting %d=%d", this.settingId, this.settingValue));
                    }
                    if (!this.onSetting(buffer, this.settings, this.settingId, this.settingValue)) {
                        return false;
                    }
                    this.state = State.SETTING_ID;
                    if (this.length != 0) continue block7;
                    return this.onSettings(buffer, this.settings);
                }
            }
            throw new IllegalStateException();
        }
        return false;
    }

    protected boolean onSetting(ByteBuffer buffer, Map<Integer, Integer> settings, int key, int value) {
        ++this.keys;
        if (this.keys > this.getMaxKeys()) {
            return this.connectionFailure(buffer, ErrorCode.ENHANCE_YOUR_CALM_ERROR.code, "invalid_settings_frame");
        }
        settings.put(key, value);
        return true;
    }

    protected boolean onSettings(ByteBuffer buffer, Map<Integer, Integer> settings) {
        Integer enablePush = settings.get(2);
        if (enablePush != null && enablePush != 0 && enablePush != 1) {
            return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_settings_enable_push");
        }
        Integer initialWindowSize = settings.get(4);
        if (initialWindowSize != null && initialWindowSize < 0) {
            return this.connectionFailure(buffer, ErrorCode.FLOW_CONTROL_ERROR.code, "invalid_settings_initial_window_size");
        }
        Integer maxFrameLength = settings.get(5);
        if (maxFrameLength != null && (maxFrameLength < 16384 || maxFrameLength > 0xFFFFFF)) {
            return this.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_settings_max_frame_size");
        }
        SettingsFrame frame = new SettingsFrame(settings, this.hasFlag(1));
        return this.onSettings(frame);
    }

    private boolean onSettings(SettingsFrame frame) {
        this.reset();
        this.notifySettings(frame);
        return true;
    }

    public static SettingsFrame parseBody(ByteBuffer buffer) {
        final AtomicReference frameRef = new AtomicReference();
        SettingsBodyParser parser = new SettingsBodyParser(new HeaderParser(RateControl.NO_RATE_CONTROL), new Parser.Listener.Adapter(){

            @Override
            public void onSettings(SettingsFrame frame) {
                frameRef.set(frame);
            }

            @Override
            public void onConnectionFailure(int error, String reason) {
                frameRef.set(null);
            }
        });
        if (buffer.hasRemaining()) {
            parser.parse(buffer, 0, buffer.remaining());
        } else {
            parser.emptyBody(buffer);
        }
        return (SettingsFrame)frameRef.get();
    }

    private static enum State {
        PREPARE,
        SETTING_ID,
        SETTING_ID_BYTES,
        SETTING_VALUE,
        SETTING_VALUE_BYTES;

    }
}

