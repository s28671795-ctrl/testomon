/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.grpc.CallOptions;
import io.grpc.InternalMetadata;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.AbstractClientStream;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.ReadableBuffers;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

public abstract class Http2ClientStreamTransportState
extends AbstractClientStream.TransportState {
    private static final InternalMetadata.TrustedAsciiMarshaller<Integer> HTTP_STATUS_MARSHALLER = new InternalMetadata.TrustedAsciiMarshaller<Integer>(){

        @Override
        public byte[] toAsciiString(Integer value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer parseAsciiString(byte[] serialized) {
            if (serialized.length >= 3) {
                return (serialized[0] - 48) * 100 + (serialized[1] - 48) * 10 + (serialized[2] - 48);
            }
            throw new NumberFormatException("Malformed status code " + new String(serialized, InternalMetadata.US_ASCII));
        }
    };
    private static final Metadata.Key<Integer> HTTP2_STATUS = InternalMetadata.keyOf(":status", HTTP_STATUS_MARSHALLER);
    private Status transportError;
    private Metadata transportErrorMetadata;
    private Charset errorCharset = Charsets.UTF_8;
    private boolean headersReceived;

    protected Http2ClientStreamTransportState(int maxMessageSize, StatsTraceContext statsTraceCtx, TransportTracer transportTracer, CallOptions options) {
        super(maxMessageSize, statsTraceCtx, transportTracer, options);
    }

    protected abstract void http2ProcessingFailed(Status var1, boolean var2, Metadata var3);

    protected void transportHeadersReceived(Metadata headers) {
        Preconditions.checkNotNull(headers, "headers");
        if (this.transportError != null) {
            this.transportError = this.transportError.augmentDescription("headers: " + headers);
            return;
        }
        try {
            if (this.headersReceived) {
                this.transportError = Status.INTERNAL.withDescription("Received headers twice");
                return;
            }
            Integer httpStatus = headers.get(HTTP2_STATUS);
            if (httpStatus != null && httpStatus >= 100 && httpStatus < 200) {
                return;
            }
            this.headersReceived = true;
            this.transportError = this.validateInitialMetadata(headers);
            if (this.transportError != null) {
                return;
            }
            Http2ClientStreamTransportState.stripTransportDetails(headers);
            this.inboundHeadersReceived(headers);
        }
        finally {
            if (this.transportError != null) {
                this.transportError = this.transportError.augmentDescription("headers: " + headers);
                this.transportErrorMetadata = headers;
                this.errorCharset = Http2ClientStreamTransportState.extractCharset(headers);
            }
        }
    }

    protected void transportDataReceived(ReadableBuffer frame, boolean endOfStream) {
        if (this.transportError != null) {
            this.transportError = this.transportError.augmentDescription("DATA-----------------------------\n" + ReadableBuffers.readAsString(frame, this.errorCharset));
            frame.close();
            if (this.transportError.getDescription().length() > 1000 || endOfStream) {
                this.http2ProcessingFailed(this.transportError, false, this.transportErrorMetadata);
            }
        } else {
            if (!this.headersReceived) {
                this.http2ProcessingFailed(Status.INTERNAL.withDescription("headers not received before payload"), false, new Metadata());
                return;
            }
            int frameSize = frame.readableBytes();
            this.inboundDataReceived(frame);
            if (endOfStream) {
                this.transportError = frameSize > 0 ? Status.INTERNAL.withDescription("Received unexpected EOS on non-empty DATA frame from server") : Status.INTERNAL.withDescription("Received unexpected EOS on empty DATA frame from server");
                this.transportErrorMetadata = new Metadata();
                this.transportReportStatus(this.transportError, false, this.transportErrorMetadata);
            }
        }
    }

    protected void transportTrailersReceived(Metadata trailers) {
        Preconditions.checkNotNull(trailers, "trailers");
        if (this.transportError == null && !this.headersReceived) {
            this.transportError = this.validateInitialMetadata(trailers);
            if (this.transportError != null) {
                this.transportErrorMetadata = trailers;
            }
        }
        if (this.transportError != null) {
            this.transportError = this.transportError.augmentDescription("trailers: " + trailers);
            this.http2ProcessingFailed(this.transportError, false, this.transportErrorMetadata);
        } else {
            Status status = this.statusFromTrailers(trailers);
            Http2ClientStreamTransportState.stripTransportDetails(trailers);
            this.inboundTrailersReceived(trailers, status);
        }
    }

    private Status statusFromTrailers(Metadata trailers) {
        Status status = trailers.get(InternalStatus.CODE_KEY);
        if (status != null) {
            return status.withDescription(trailers.get(InternalStatus.MESSAGE_KEY));
        }
        if (this.headersReceived) {
            return Status.UNKNOWN.withDescription("missing GRPC status in response");
        }
        Integer httpStatus = trailers.get(HTTP2_STATUS);
        status = httpStatus != null ? GrpcUtil.httpStatusToGrpcStatus(httpStatus) : Status.INTERNAL.withDescription("missing HTTP status code");
        return status.augmentDescription("missing GRPC status, inferred error from HTTP status code");
    }

    @Nullable
    private Status validateInitialMetadata(Metadata headers) {
        Integer httpStatus = headers.get(HTTP2_STATUS);
        if (httpStatus == null) {
            return Status.INTERNAL.withDescription("Missing HTTP status code");
        }
        String contentType = headers.get(GrpcUtil.CONTENT_TYPE_KEY);
        if (!GrpcUtil.isGrpcContentType(contentType)) {
            return GrpcUtil.httpStatusToGrpcStatus(httpStatus).augmentDescription("invalid content-type: " + contentType);
        }
        return null;
    }

    private static Charset extractCharset(Metadata headers) {
        String contentType = headers.get(GrpcUtil.CONTENT_TYPE_KEY);
        if (contentType != null) {
            String[] split = contentType.split("charset=", 2);
            try {
                return Charset.forName(split[split.length - 1].trim());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return Charsets.UTF_8;
    }

    private static void stripTransportDetails(Metadata metadata) {
        metadata.discardAll(HTTP2_STATUS);
        metadata.discardAll(InternalStatus.CODE_KEY);
        metadata.discardAll(InternalStatus.MESSAGE_KEY);
    }
}

