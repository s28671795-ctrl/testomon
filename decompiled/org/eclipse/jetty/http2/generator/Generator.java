/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.generator.DataGenerator;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.GoAwayGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.http2.generator.HeadersGenerator;
import org.eclipse.jetty.http2.generator.NoOpGenerator;
import org.eclipse.jetty.http2.generator.PingGenerator;
import org.eclipse.jetty.http2.generator.PrefaceGenerator;
import org.eclipse.jetty.http2.generator.PriorityGenerator;
import org.eclipse.jetty.http2.generator.PushPromiseGenerator;
import org.eclipse.jetty.http2.generator.ResetGenerator;
import org.eclipse.jetty.http2.generator.SettingsGenerator;
import org.eclipse.jetty.http2.generator.WindowUpdateGenerator;
import org.eclipse.jetty.http2.hpack.HpackEncoder;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.io.ByteBufferPool;

public class Generator {
    private final ByteBufferPool byteBufferPool;
    private final HeaderGenerator headerGenerator;
    private final HpackEncoder hpackEncoder;
    private final FrameGenerator[] generators;
    private final DataGenerator dataGenerator;

    public Generator(ByteBufferPool byteBufferPool) {
        this(byteBufferPool, 4096, 0);
    }

    public Generator(ByteBufferPool byteBufferPool, int maxDynamicTableSize, int maxHeaderBlockFragment) {
        this(byteBufferPool, true, maxDynamicTableSize, maxHeaderBlockFragment);
    }

    public Generator(ByteBufferPool byteBufferPool, boolean useDirectByteBuffers, int maxDynamicTableSize, int maxHeaderBlockFragment) {
        this.byteBufferPool = byteBufferPool;
        this.headerGenerator = new HeaderGenerator(useDirectByteBuffers);
        this.hpackEncoder = new HpackEncoder(maxDynamicTableSize);
        this.generators = new FrameGenerator[FrameType.values().length];
        this.generators[FrameType.HEADERS.getType()] = new HeadersGenerator(this.headerGenerator, this.hpackEncoder, maxHeaderBlockFragment);
        this.generators[FrameType.PRIORITY.getType()] = new PriorityGenerator(this.headerGenerator);
        this.generators[FrameType.RST_STREAM.getType()] = new ResetGenerator(this.headerGenerator);
        this.generators[FrameType.SETTINGS.getType()] = new SettingsGenerator(this.headerGenerator);
        this.generators[FrameType.PUSH_PROMISE.getType()] = new PushPromiseGenerator(this.headerGenerator, this.hpackEncoder);
        this.generators[FrameType.PING.getType()] = new PingGenerator(this.headerGenerator);
        this.generators[FrameType.GO_AWAY.getType()] = new GoAwayGenerator(this.headerGenerator);
        this.generators[FrameType.WINDOW_UPDATE.getType()] = new WindowUpdateGenerator(this.headerGenerator);
        this.generators[FrameType.CONTINUATION.getType()] = null;
        this.generators[FrameType.PREFACE.getType()] = new PrefaceGenerator();
        this.generators[FrameType.DISCONNECT.getType()] = new NoOpGenerator();
        this.dataGenerator = new DataGenerator(this.headerGenerator);
    }

    public ByteBufferPool getByteBufferPool() {
        return this.byteBufferPool;
    }

    public void setValidateHpackEncoding(boolean validateEncoding) {
        this.hpackEncoder.setValidateEncoding(validateEncoding);
    }

    public void setHeaderTableSize(int headerTableSize) {
        this.hpackEncoder.setRemoteMaxDynamicTableSize(headerTableSize);
    }

    public void setMaxFrameSize(int maxFrameSize) {
        this.headerGenerator.setMaxFrameSize(maxFrameSize);
    }

    public int control(ByteBufferPool.Lease lease, Frame frame) throws HpackException {
        return this.generators[frame.getType().getType()].generate(lease, frame);
    }

    public int data(ByteBufferPool.Lease lease, DataFrame frame, int maxLength) {
        return this.dataGenerator.generate(lease, frame, maxLength);
    }

    public void setMaxHeaderListSize(int value) {
        this.hpackEncoder.setMaxHeaderListSize(value);
    }
}

