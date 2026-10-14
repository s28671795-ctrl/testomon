/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.util.ArrayList;
import org.eclipse.jetty.http2.AbstractFlowControlStrategy;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.util.Callback;

public class SimpleFlowControlStrategy
extends AbstractFlowControlStrategy {
    public SimpleFlowControlStrategy() {
        this(65535);
    }

    public SimpleFlowControlStrategy(int initialStreamSendWindow) {
        super(initialStreamSendWindow);
    }

    @Override
    public void onDataConsumed(ISession session, IStream stream, int length) {
        if (length <= 0) {
            return;
        }
        ArrayList<WindowUpdateFrame> frames = new ArrayList<WindowUpdateFrame>(2);
        WindowUpdateFrame sessionFrame = new WindowUpdateFrame(0, length);
        frames.add(sessionFrame);
        session.updateRecvWindow(length);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Data consumed, increased session recv window by {} for {}", (Object)length, (Object)session);
        }
        if (stream != null) {
            if (stream.isRemotelyClosed()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Data consumed, ignoring update stream recv window by {} for remotely closed {}", (Object)length, (Object)stream);
                }
            } else {
                WindowUpdateFrame streamFrame = new WindowUpdateFrame(stream.getId(), length);
                frames.add(streamFrame);
                stream.updateRecvWindow(length);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Data consumed, increased stream recv window by {} for {}", (Object)length, (Object)stream);
                }
            }
        }
        session.frames(stream, frames, Callback.NOOP);
    }
}

