/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import io.grpc.StreamTracer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class StatsTraceContext {
    public static final StatsTraceContext NOOP = new StatsTraceContext(new StreamTracer[0]);
    private final StreamTracer[] tracers;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public static StatsTraceContext newClientContext(ClientStreamTracer[] tracers, Attributes transportAtts, Metadata headers) {
        StatsTraceContext ctx = new StatsTraceContext(tracers);
        for (ClientStreamTracer tracer : tracers) {
            tracer.streamCreated(transportAtts, headers);
        }
        return ctx;
    }

    public static StatsTraceContext newServerContext(List<? extends ServerStreamTracer.Factory> factories, String fullMethodName, Metadata headers) {
        if (factories.isEmpty()) {
            return NOOP;
        }
        StreamTracer[] tracers = new StreamTracer[factories.size()];
        for (int i = 0; i < tracers.length; ++i) {
            tracers[i] = factories.get(i).newServerStreamTracer(fullMethodName, headers);
        }
        return new StatsTraceContext(tracers);
    }

    @VisibleForTesting
    StatsTraceContext(StreamTracer[] tracers) {
        this.tracers = tracers;
    }

    @VisibleForTesting
    public List<StreamTracer> getTracersForTest() {
        return new ArrayList<StreamTracer>(Arrays.asList(this.tracers));
    }

    public void clientOutboundHeaders() {
        for (StreamTracer tracer : this.tracers) {
            ((ClientStreamTracer)tracer).outboundHeaders();
        }
    }

    public void clientInboundHeaders(Metadata headers) {
        for (StreamTracer tracer : this.tracers) {
            ((ClientStreamTracer)tracer).inboundHeaders(headers);
        }
    }

    public void clientInboundTrailers(Metadata trailers) {
        for (StreamTracer tracer : this.tracers) {
            ((ClientStreamTracer)tracer).inboundTrailers(trailers);
        }
    }

    public <ReqT, RespT> Context serverFilterContext(Context context) {
        Context ctx = Preconditions.checkNotNull(context, "context");
        for (StreamTracer tracer : this.tracers) {
            ctx = ((ServerStreamTracer)tracer).filterContext(ctx);
            Preconditions.checkNotNull(ctx, "%s returns null context", (Object)tracer);
        }
        return ctx;
    }

    public void serverCallStarted(ServerStreamTracer.ServerCallInfo<?, ?> callInfo) {
        for (StreamTracer tracer : this.tracers) {
            ((ServerStreamTracer)tracer).serverCallStarted(callInfo);
        }
    }

    public void streamClosed(Status status) {
        if (this.closed.compareAndSet(false, true)) {
            for (StreamTracer tracer : this.tracers) {
                tracer.streamClosed(status);
            }
        }
    }

    public void outboundMessage(int seqNo) {
        for (StreamTracer tracer : this.tracers) {
            tracer.outboundMessage(seqNo);
        }
    }

    public void inboundMessage(int seqNo) {
        for (StreamTracer tracer : this.tracers) {
            tracer.inboundMessage(seqNo);
        }
    }

    public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        for (StreamTracer tracer : this.tracers) {
            tracer.outboundMessageSent(seqNo, optionalWireSize, optionalUncompressedSize);
        }
    }

    public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        for (StreamTracer tracer : this.tracers) {
            tracer.inboundMessageRead(seqNo, optionalWireSize, optionalUncompressedSize);
        }
    }

    public void outboundUncompressedSize(long bytes) {
        for (StreamTracer tracer : this.tracers) {
            tracer.outboundUncompressedSize(bytes);
        }
    }

    public void outboundWireSize(long bytes) {
        for (StreamTracer tracer : this.tracers) {
            tracer.outboundWireSize(bytes);
        }
    }

    public void inboundUncompressedSize(long bytes) {
        for (StreamTracer tracer : this.tracers) {
            tracer.inboundUncompressedSize(bytes);
        }
    }

    public void inboundWireSize(long bytes) {
        for (StreamTracer tracer : this.tracers) {
            tracer.inboundWireSize(bytes);
        }
    }
}

