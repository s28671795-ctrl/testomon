/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class DisconnectFrame
extends Frame {
    public DisconnectFrame() {
        super(FrameType.DISCONNECT);
    }
}

