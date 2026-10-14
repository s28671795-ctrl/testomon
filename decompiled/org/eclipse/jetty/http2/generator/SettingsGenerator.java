/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import java.util.Map;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class SettingsGenerator
extends FrameGenerator {
    public SettingsGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        SettingsFrame settingsFrame = (SettingsFrame)frame;
        return this.generateSettings(lease, settingsFrame.getSettings(), settingsFrame.isReply());
    }

    public int generateSettings(ByteBufferPool.Lease lease, Map<Integer, Integer> settings, boolean reply) {
        int entryLength = 6;
        int length = entryLength * settings.size();
        if (length > this.getMaxFrameSize()) {
            throw new IllegalArgumentException("Invalid settings, too big");
        }
        ByteBuffer header = this.generateHeader(lease, FrameType.SETTINGS, length, reply ? 1 : 0, 0);
        for (Map.Entry<Integer, Integer> entry : settings.entrySet()) {
            header.putShort(entry.getKey().shortValue());
            header.putInt(entry.getValue());
        }
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 9 + length;
    }
}

