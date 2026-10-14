/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import java.nio.charset.StandardCharsets;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class PrefaceFrame
extends Frame {
    public static final byte[] PREFACE_PREAMBLE_BYTES = "PRI * HTTP/2.0\r\n\r\n".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] PREFACE_BYTES = "PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes(StandardCharsets.US_ASCII);

    public PrefaceFrame() {
        super(FrameType.PREFACE);
    }
}

