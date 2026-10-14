/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import java.util.Map;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class SettingsFrame
extends Frame {
    public static final int DEFAULT_MAX_KEYS = 64;
    public static final int HEADER_TABLE_SIZE = 1;
    public static final int ENABLE_PUSH = 2;
    public static final int MAX_CONCURRENT_STREAMS = 3;
    public static final int INITIAL_WINDOW_SIZE = 4;
    public static final int MAX_FRAME_SIZE = 5;
    public static final int MAX_HEADER_LIST_SIZE = 6;
    public static final int ENABLE_CONNECT_PROTOCOL = 8;
    private final Map<Integer, Integer> settings;
    private final boolean reply;

    public SettingsFrame(Map<Integer, Integer> settings, boolean reply) {
        super(FrameType.SETTINGS);
        this.settings = settings;
        this.reply = reply;
    }

    public Map<Integer, Integer> getSettings() {
        return this.settings;
    }

    public boolean isReply() {
        return this.reply;
    }

    @Override
    public String toString() {
        return String.format("%s,reply=%b,params=%s", super.toString(), this.reply, this.settings);
    }
}

