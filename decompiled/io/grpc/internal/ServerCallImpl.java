/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Attributes;
import io.grpc.Codec;
import io.grpc.Compressor;
import io.grpc.CompressorRegistry;
import io.grpc.Context;
import io.grpc.DecompressorRegistry;
import io.grpc.InternalDecompressorRegistry;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.ServerCall;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.CallTracer;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ServerStream;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.StreamListener;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ServerCallImpl<ReqT, RespT>
extends ServerCall<ReqT, RespT> {
    private static final Logger log = Logger.getLogger(ServerCallImpl.class.getName());
    @VisibleForTesting
    static final String TOO_MANY_RESPONSES = "Too many responses";
    @VisibleForTesting
    static final String MISSING_RESPONSE = "Completed without a response";
    private final ServerStream stream;
    private final MethodDescriptor<ReqT, RespT> method;
    private final Tag tag;
    private final Context.CancellableContext context;
    private final byte[] messageAcceptEncoding;
    private final DecompressorRegistry decompressorRegistry;
    private final CompressorRegistry compressorRegistry;
    private CallTracer serverCallTracer;
    private volatile boolean cancelled;
    private boolean sendHeadersCalled;
    private boolean closeCalled;
    private Compressor compressor;
    private boolean messageSent;

    ServerCallImpl(ServerStream stream, MethodDescriptor<ReqT, RespT> method2, Metadata inboundHeaders, Context.CancellableContext context, DecompressorRegistry decompressorRegistry, CompressorRegistry compressorRegistry, CallTracer serverCallTracer, Tag tag) {
        this.stream = stream;
        this.method = method2;
        this.context = context;
        this.messageAcceptEncoding = inboundHeaders.get(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY);
        this.decompressorRegistry = decompressorRegistry;
        this.compressorRegistry = compressorRegistry;
        this.serverCallTracer = serverCallTracer;
        this.serverCallTracer.reportCallStarted();
        this.tag = tag;
    }

    @Override
    public void request(int numMessages) {
        try (TaskCloseable ignore = PerfMark.traceTask("ServerCall.request");){
            PerfMark.attachTag(this.tag);
            this.stream.request(numMessages);
        }
    }

    @Override
    public void sendHeaders(Metadata headers) {
        try (TaskCloseable ignore = PerfMark.traceTask("ServerCall.sendHeaders");){
            PerfMark.attachTag(this.tag);
            this.sendHeadersInternal(headers);
        }
    }

    private void sendHeadersInternal(Metadata headers) {
        Preconditions.checkState(!this.sendHeadersCalled, "sendHeaders has already been called");
        Preconditions.checkState(!this.closeCalled, "call is closed");
        headers.discardAll(GrpcUtil.CONTENT_LENGTH_KEY);
        headers.discardAll(GrpcUtil.MESSAGE_ENCODING_KEY);
        if (this.compressor == null) {
            this.compressor = Codec.Identity.NONE;
        } else if (this.messageAcceptEncoding != null) {
            if (!GrpcUtil.iterableContains(GrpcUtil.ACCEPT_ENCODING_SPLITTER.split(new String(this.messageAcceptEncoding, GrpcUtil.US_ASCII)), this.compressor.getMessageEncoding())) {
                this.compressor = Codec.Identity.NONE;
            }
        } else {
            this.compressor = Codec.Identity.NONE;
        }
        headers.put(GrpcUtil.MESSAGE_ENCODING_KEY, this.compressor.getMessageEncoding());
        this.stream.setCompressor(this.compressor);
        headers.discardAll(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY);
        byte[] advertisedEncodings = InternalDecompressorRegistry.getRawAdvertisedMessageEncodings(this.decompressorRegistry);
        if (advertisedEncodings.length != 0) {
            headers.put(GrpcUtil.MESSAGE_ACCEPT_ENCODING_KEY, advertisedEncodings);
        }
        this.sendHeadersCalled = true;
        this.stream.writeHeaders(headers, !this.getMethodDescriptor().getType().serverSendsOneMessage());
    }

    @Override
    public void sendMessage(RespT message) {
        try (TaskCloseable ignore = PerfMark.traceTask("ServerCall.sendMessage");){
            PerfMark.attachTag(this.tag);
            this.sendMessageInternal(message);
        }
    }

    private void sendMessageInternal(RespT message) {
        Preconditions.checkState(this.sendHeadersCalled, "sendHeaders has not been called");
        Preconditions.checkState(!this.closeCalled, "call is closed");
        if (this.method.getType().serverSendsOneMessage() && this.messageSent) {
            this.handleInternalError(Status.INTERNAL.withDescription(TOO_MANY_RESPONSES).asRuntimeException());
            return;
        }
        this.messageSent = true;
        try {
            InputStream resp = this.method.streamResponse(message);
            this.stream.writeMessage(resp);
            if (!this.getMethodDescriptor().getType().serverSendsOneMessage()) {
                this.stream.flush();
            }
        }
        catch (RuntimeException e) {
            this.handleInternalError(e);
        }
        catch (Error e) {
            this.close(Status.CANCELLED.withDescription("Server sendMessage() failed with Error"), new Metadata());
            throw e;
        }
    }

    @Override
    public void setMessageCompression(boolean enable) {
        this.stream.setMessageCompression(enable);
    }

    @Override
    public void setOnReadyThreshold(int numBytes) {
        this.stream.setOnReadyThreshold(numBytes);
    }

    @Override
    public void setCompression(String compressorName) {
        Preconditions.checkState(!this.sendHeadersCalled, "sendHeaders has been called");
        this.compressor = this.compressorRegistry.lookupCompressor(compressorName);
        Preconditions.checkArgument(this.compressor != null, "Unable to find compressor by name %s", (Object)compressorName);
    }

    @Override
    public boolean isReady() {
        if (this.closeCalled) {
            return false;
        }
        return this.stream.isReady();
    }

    @Override
    public void close(Status status, Metadata trailers) {
        try (TaskCloseable ignore = PerfMark.traceTask("ServerCall.close");){
            PerfMark.attachTag(this.tag);
            this.closeInternal(status, trailers);
        }
    }

    private void closeInternal(Status status, Metadata trailers) {
        Preconditions.checkState(!this.closeCalled, "call already closed");
        try {
            this.closeCalled = true;
            if (status.isOk() && this.method.getType().serverSendsOneMessage() && !this.messageSent) {
                this.handleInternalError(Status.INTERNAL.withDescription(MISSING_RESPONSE).asRuntimeException());
                return;
            }
            this.stream.close(status, trailers);
        }
        finally {
            this.serverCallTracer.reportCallEnded(status.isOk());
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    ServerStreamListener newServerStreamListener(ServerCall.Listener<ReqT> listener) {
        return new ServerStreamListenerImpl<ReqT>(this, listener, this.context);
    }

    @Override
    public Attributes getAttributes() {
        return this.stream.getAttributes();
    }

    @Override
    public String getAuthority() {
        return this.stream.getAuthority();
    }

    @Override
    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return this.method;
    }

    @Override
    public SecurityLevel getSecurityLevel() {
        Attributes attributes = this.getAttributes();
        if (attributes == null) {
            return super.getSecurityLevel();
        }
        SecurityLevel securityLevel = attributes.get(GrpcAttributes.ATTR_SECURITY_LEVEL);
        return securityLevel == null ? super.getSecurityLevel() : securityLevel;
    }

    private void handleInternalError(Throwable internalError) {
        log.log(Level.WARNING, "Cancelling the stream because of internal error", internalError);
        Status status = internalError instanceof StatusRuntimeException ? ((StatusRuntimeException)internalError).getStatus() : Status.INTERNAL.withCause(internalError).withDescription("Internal error so cancelling stream.");
        this.stream.cancel(status);
        this.serverCallTracer.reportCallEnded(false);
    }

    @VisibleForTesting
    static final class ServerStreamListenerImpl<ReqT>
    implements ServerStreamListener {
        private final ServerCallImpl<ReqT, ?> call;
        private final ServerCall.Listener<ReqT> listener;
        private final Context.CancellableContext context;

        public ServerStreamListenerImpl(ServerCallImpl<ReqT, ?> call, ServerCall.Listener<ReqT> listener, Context.CancellableContext context) {
            this.call = Preconditions.checkNotNull(call, "call");
            this.listener = Preconditions.checkNotNull(listener, "listener must not be null");
            this.context = Preconditions.checkNotNull(context, "context");
            this.context.addListener(new Context.CancellationListener(){

                @Override
                public void cancelled(Context context) {
                    if (context.cancellationCause() != null) {
                        call.cancelled = true;
                    }
                }
            }, MoreExecutors.directExecutor());
        }

        @Override
        public void messagesAvailable(StreamListener.MessageProducer producer) {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.messagesAvailable");){
                PerfMark.attachTag(((ServerCallImpl)this.call).tag);
                this.messagesAvailableInternal(producer);
            }
        }

        private void messagesAvailableInternal(StreamListener.MessageProducer producer) {
            if (((ServerCallImpl)this.call).cancelled) {
                GrpcUtil.closeQuietly(producer);
                return;
            }
            try {
                InputStream message;
                while ((message = producer.next()) != null) {
                    try {
                        this.listener.onMessage(((ServerCallImpl)this.call).method.parseRequest(message));
                    }
                    catch (Throwable t2) {
                        GrpcUtil.closeQuietly(message);
                        throw t2;
                    }
                    message.close();
                }
            }
            catch (Throwable t3) {
                GrpcUtil.closeQuietly(producer);
                Throwables.throwIfUnchecked(t3);
                throw new RuntimeException(t3);
            }
        }

        @Override
        public void halfClosed() {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.halfClosed");){
                PerfMark.attachTag(((ServerCallImpl)this.call).tag);
                if (((ServerCallImpl)this.call).cancelled) {
                    return;
                }
                this.listener.onHalfClose();
            }
        }

        @Override
        public void closed(Status status) {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.closed");){
                PerfMark.attachTag(((ServerCallImpl)this.call).tag);
                this.closedInternal(status);
            }
        }

        private void closedInternal(Status status) {
            StatusRuntimeException cancelCause = null;
            try {
                if (status.isOk()) {
                    this.listener.onComplete();
                } else {
                    ((ServerCallImpl)this.call).cancelled = true;
                    this.listener.onCancel();
                    cancelCause = InternalStatus.asRuntimeException(Status.CANCELLED.withDescription("RPC cancelled"), null, false);
                }
            }
            finally {
                this.context.cancel(cancelCause);
            }
        }

        @Override
        public void onReady() {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.onReady");){
                PerfMark.attachTag(((ServerCallImpl)this.call).tag);
                if (((ServerCallImpl)this.call).cancelled) {
                    return;
                }
                this.listener.onReady();
            }
        }
    }
}

