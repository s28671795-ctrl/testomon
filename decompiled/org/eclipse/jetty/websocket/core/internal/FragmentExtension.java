/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.ByteBuffer;
import java.util.function.LongConsumer;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.AbstractExtension;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.internal.DemandChain;
import org.eclipse.jetty.websocket.core.internal.DemandingFlusher;
import org.eclipse.jetty.websocket.core.internal.FragmentingFlusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FragmentExtension
extends AbstractExtension
implements DemandChain {
    private static final Logger LOG = LoggerFactory.getLogger(FragmentExtension.class);
    private final FragmentingFlusher outgoingFlusher;
    private final DemandingFlusher incomingFlusher;
    private final Configuration configuration = new Configuration.ConfigurationCustomizer();

    public FragmentExtension() {
        this.outgoingFlusher = new FragmentingFlusher(this.configuration){

            @Override
            void forwardFrame(Frame frame, Callback callback, boolean batch) {
                FragmentExtension.this.nextOutgoingFrame(frame, callback, batch);
            }
        };
        this.incomingFlusher = new FragmentingDemandingFlusher();
    }

    @Override
    public void demand(long n) {
        this.incomingFlusher.demand(n);
    }

    @Override
    public void setNextDemand(LongConsumer nextDemand) {
        this.incomingFlusher.setNextDemand(nextDemand);
    }

    @Override
    public String getName() {
        return "fragment";
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        this.incomingFlusher.onFrame(frame, callback);
    }

    @Override
    public void sendFrame(Frame frame, Callback callback, boolean batch) {
        this.outgoingFlusher.sendFrame(frame, callback, batch);
    }

    @Override
    public void init(ExtensionConfig config, WebSocketComponents components) {
        super.init(config, components);
        int maxLength = config.getParameter("maxLength", -1);
        this.configuration.setMaxFrameSize(maxLength);
    }

    public class FragmentingDemandingFlusher
    extends DemandingFlusher {
        public FragmentingDemandingFlusher() {
            super((Frame x$0, Callback x$1) -> FragmentExtension.this.nextIncomingFrame(x$0, x$1));
        }

        @Override
        protected boolean handle(Frame frame, Callback callback, boolean first) {
            if (first && frame.isControlFrame()) {
                this.emitFrame(frame, callback);
                return true;
            }
            ByteBuffer payload = frame.getPayload();
            int remaining = payload.remaining();
            long maxFrameSize = FragmentExtension.this.configuration.getMaxFrameSize();
            int fragmentSize = (int)Math.min((long)remaining, maxFrameSize);
            boolean continuation = frame.getOpCode() == 0 || !first;
            Frame fragment = new Frame(continuation ? (byte)0 : frame.getOpCode());
            boolean finished = maxFrameSize <= 0L || (long)remaining <= maxFrameSize;
            fragment.setFin(frame.isFin() && finished);
            if (finished) {
                fragment.setPayload(payload);
            } else {
                int limit = payload.limit();
                int newLimit = payload.position() + fragmentSize;
                payload.limit(newLimit);
                ByteBuffer payloadFragment = payload.slice();
                payload.limit(limit);
                fragment.setPayload(payloadFragment);
                payload.position(newLimit);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Fragmented {}->{}", (Object)frame, (Object)fragment);
                }
            }
            Callback payloadCallback = Callback.from(() -> {
                if (finished) {
                    callback.succeeded();
                }
            }, (Throwable t2) -> {
                callback.failed((Throwable)t2);
                this.failFlusher((Throwable)t2);
            });
            this.emitFrame(fragment, payloadCallback);
            return finished;
        }
    }
}

