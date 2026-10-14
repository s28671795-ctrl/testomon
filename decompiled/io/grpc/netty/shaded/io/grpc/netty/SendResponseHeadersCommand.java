/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.StreamIdHolder;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;

final class SendResponseHeadersCommand
extends WriteQueue.AbstractQueuedCommand {
    private final StreamIdHolder stream;
    private final Http2Headers headers;
    private final Status status;

    private SendResponseHeadersCommand(StreamIdHolder stream, Http2Headers headers, Status status) {
        this.stream = Preconditions.checkNotNull(stream, "stream");
        this.headers = Preconditions.checkNotNull(headers, "headers");
        this.status = status;
    }

    static SendResponseHeadersCommand createHeaders(StreamIdHolder stream, Http2Headers headers) {
        return new SendResponseHeadersCommand(stream, headers, null);
    }

    static SendResponseHeadersCommand createTrailers(StreamIdHolder stream, Http2Headers headers, Status status) {
        return new SendResponseHeadersCommand(stream, headers, Preconditions.checkNotNull(status, "status"));
    }

    StreamIdHolder stream() {
        return this.stream;
    }

    Http2Headers headers() {
        return this.headers;
    }

    boolean endOfStream() {
        return this.status != null;
    }

    Status status() {
        return this.status;
    }

    public boolean equals(Object that) {
        if (that == null || !that.getClass().equals(SendResponseHeadersCommand.class)) {
            return false;
        }
        SendResponseHeadersCommand thatCmd = (SendResponseHeadersCommand)that;
        return thatCmd.stream.equals(this.stream) && thatCmd.headers.equals(this.headers) && thatCmd.status.equals(this.status);
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(stream=" + this.stream.id() + ", headers=" + this.headers + ", status=" + this.status + ")";
    }

    public int hashCode() {
        return Objects.hashCode(this.stream, this.status);
    }
}

